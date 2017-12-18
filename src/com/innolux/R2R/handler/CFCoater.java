package com.innolux.R2R.handler;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.innolux.R2R.bc.BC;
import com.innolux.R2R.cf_coater.model.AP_Status;
import com.innolux.R2R.cf_coater.model.AP_Status_CRUD;
import com.innolux.R2R.cf_coater.model.Coater_PDS_Data;
import com.innolux.R2R.cf_coater.model.Coater_PDS_Data_CRUD;
import com.innolux.R2R.cf_coater.model.Coater_Param_Setting;
import com.innolux.R2R.cf_coater.model.Coater_Param_Setting_CRUD;
import com.innolux.R2R.cf_coater.model.False_Range_Setting;
import com.innolux.R2R.cf_coater.model.False_Range_Setting_CRUD;
import com.innolux.R2R.cf_coater.model.Glass_Sammury_Data;
import com.innolux.R2R.cf_coater.model.Glass_Sammury_Data_CRUD;
import com.innolux.R2R.cf_coater.model.Last_Process_Time;
import com.innolux.R2R.cf_coater.model.Last_Process_Time_CRUD;
import com.innolux.R2R.cf_coater.model.Measure_point_Setting;
import com.innolux.R2R.cf_coater.model.Measure_point_Setting_CRUD;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.MeasureFileDataBase;
import com.innolux.R2R.common.base.RegulationCollection;
import com.innolux.R2R.interfaces.IFileData;
import com.innolux.models.bc.Line;
import com.innolux.models.bc.Line_CRUD;
import com.innolux.models.bc.Node;
import com.innolux.models.bc.Node_CRUD;
import com.innolux.models.bc.PPID;
import com.innolux.models.bc.PPID_CRUD;
import com.innolux.models.dems.BCIP_CRUD;
import com.innolux.services.MeasureFileReader;

public class CFCoater implements IFileData {
	private static Logger logger = Logger.getLogger(CFCoater.class);

	public CFCoater(String _SourcePatch, String _NgPath) {
		MeasureFileReader fileService = new MeasureFileReader();
		fileService.setFileHandler(this, _SourcePatch, _NgPath);
		fileService.start();
	}

	@Override
	public void onFileData(MeasureFileDataBase data) {
		Glass_Sammury_Data summary = new Glass_Sammury_Data();
		try {
			String EqpId = data.FetchInfo("HEADER", "EQ_ID");
			if (EqpId.equals("")) {
				return;
			}
			String SubEqpId = data.FetchInfo("HEADER", "SUBEQ_ID");
			if (SubEqpId.equals("")) {
				return;
			} else {
				summary.setEqpId(SubEqpId);
			}
			String PreEqpId1 = EqpId;
			String PreSubEqpId1 = getPreSubEqpId(EqpId, "COT");
			if (PreSubEqpId1.equals("")) {
				return;
			} else {
				summary.setPreEqpId(PreSubEqpId1);
			}
			String PPID = data.FetchValue("GLASS_DATA", "RECIPE_ID");
			if (PPID.equals("")) {
				return;
			} else {
				summary.setPPID(PPID);
				summary.setPrePPID(PPID);
			}
			String PreRecipeNo = getPreRecipeNo(EqpId, "COT", PPID);
			if (PreRecipeNo.equals("")) {
				return;
			} else {
				summary.setPreRecipe_No(PreRecipeNo);
			}
			String RecipeNo = data.FetchValue("EQP_GLASS_DATA", "RECIPE_NO");
			if (RecipeNo.equals("")) {
				return;
			} else {
				summary.setRecipe_No(RecipeNo);
			}

			String Glass_ID = data.FetchValue("GLASS_DATA", "GLASS_ID");
			if (Glass_ID.equals("")) {
				return;
			} else {
				summary.setGlass_Id(Glass_ID);
			}

			summary.setTimeStamp(System.currentTimeMillis());

			if (!GetPSHGlassSummary(data, summary)) {
				return;
			}

			Last_Process_Time lastTObj = Last_Process_Time_CRUD.read(SubEqpId, RecipeNo);

			if (lastTObj == null) {
				lastTObj = new Last_Process_Time();
				lastTObj.setSubEqpId(SubEqpId);
				lastTObj.setRecipeNo(RecipeNo);
			}

			AP_Status cfg = AP_Status_CRUD.read(PreEqpId1, PreRecipeNo);
			if (cfg == null) {
				return;
			}

			if (System.currentTimeMillis() - lastTObj.getUpdateTime() < cfg.getExpire_Interval_Time()) {
				// if expired, reset all
				cfg.setCurrent_Count(0);
				Glass_Sammury_Data_CRUD.delete(PreSubEqpId1, RecipeNo);
			}

			if (PreFilter(summary)) {
				// store to waiting handle list
				Glass_Sammury_Data_CRUD.create(summary);

				cfg.setCurrent_Count(cfg.getCurrent_Count() + 1);
				logger.info("PreEqpId1:" + PreEqpId1 + " current:" + cfg.getCurrent_Count() + " total:"
						+ cfg.getTotal_Count());
				if (cfg.getCurrent_Count() >= cfg.getTotal_Count()) { // calculate
																		// feedback
					RegulationCollection feedbackObj = Calculation(summary);
					if (feedbackObj.GetRegulationCount() != 0) {
						// feedback to coater
						BC bc = new BC(EqpId);

						if (bc.Excute(feedbackObj)) {
							logger.info("SendToBC successful.");
						} else {
							logger.error("SendToBC fail.");
						}
					}
					cfg.setCurrent_Count(0);
					Glass_Sammury_Data_CRUD.delete(PreSubEqpId1, RecipeNo);
				}
			}

			// update last process time
			lastTObj.setUpdateTime(System.currentTimeMillis());
			Last_Process_Time_CRUD.update(lastTObj);

		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
	}

	private boolean PreFilter(Glass_Sammury_Data summary) {
		boolean result = false;
		try {

			if (summary != null) {
				False_Range_Setting filterSetting = False_Range_Setting_CRUD.read(summary.getPPID());
				String type = summary.getPPID().substring(summary.getPPID().length() - 4,
						summary.getPPID().length() - 3);

				switch (type) {
				case "P":

					break;
				default:

				}

				summary.setTatget(filterSetting.getSpec());

				if (summary.getStart_Avg() > filterSetting.getFilter_UpLimit()
						|| summary.getStart_Avg() < filterSetting.getFilter_LowLimit()) {
					logger.info("CFCoater PreFilter Engage_Avg is not pass by limit.");
					return false;
				}

				if (summary.getMid_Avg() > filterSetting.getFilter_UpLimit()
						|| summary.getMid_Avg() < filterSetting.getFilter_LowLimit()) {
					logger.info("CFCoater PreFilter Plunge_Avg is not pass by limit.");
					return false;
				}

				if (summary.getEnd_Avg() > filterSetting.getFilter_UpLimit()
						|| summary.getEnd_Avg() < filterSetting.getFilter_LowLimit()) {
					logger.info("CFCoater PreFilter Retract_Avg is not pass by limit.");
					return false;
				}

			} else {
				logger.error("CFCoater PreFilter summary is null.");
			}

		} catch (Exception e) {
			logger.error("CFCoater PreFilter " + ToolUtility.StackTrace2String(e));
		}
		return result;
	}

	private RegulationCollection Calculation(Glass_Sammury_Data summary) {
		RegulationCollection result = new RegulationCollection();
		double BetaOffset = 0;
		double T3Offset = 0;
		double PDTOffset = 0;
		double SqueegeOffset = 0;
		
		double BetaOld = 0;
		double Q2New = 0;
		double T3New = 0;
		double PDTNew = 0;
		double SqueegeNew = 0;
		
		try {
			List<Glass_Sammury_Data> glassList = Glass_Sammury_Data_CRUD.read(summary.getPreEqpId(),
					summary.getPreRecipe_No());
			False_Range_Setting filterSetting = False_Range_Setting_CRUD.read(summary.getPPID());
			summary.setStart_Avg(0);
			summary.setMid_Avg(0);
			summary.setEnd_Avg(0);
			for (Glass_Sammury_Data eachGlass : glassList) {
				summary.setStart_Avg(summary.getStart_Avg() + eachGlass.getStart_Avg());
				summary.setMid_Avg(summary.getMid_Avg() + eachGlass.getMid_Avg());
				summary.setEnd_Avg(summary.getEnd_Avg() + eachGlass.getEnd_Avg());
			}
			summary.setStart_Avg(summary.getStart_Avg() / (double) glassList.size());
			summary.setMid_Avg(summary.getMid_Avg() / (double) glassList.size());
			summary.setEnd_Avg(summary.getEnd_Avg() / (double) glassList.size());

			logger.info("summary:" + summary + " filterSetting:" + filterSetting);

			if (summary.getStart_Avg() > filterSetting.getOOS_UpLimit()
					|| summary.getStart_Avg() < filterSetting.getOOS_LowLimit()) {
				logger.info("CFCoater Calculation Start_Avg is not pass by OOS limit.");
				// Stop EQP
			} else if (summary.getMid_Avg() > filterSetting.getOOS_UpLimit()
					|| summary.getMid_Avg() < filterSetting.getOOS_LowLimit()) {
				logger.info("CFCoater Calculation Mid_Avg is not pass by OOS limit.");
				// Stop EQP
			} else if (summary.getEnd_Avg() > filterSetting.getOOS_UpLimit()
					|| summary.getEnd_Avg() < filterSetting.getOOS_LowLimit()) {
				logger.info("CFCoater Calculation End_Avg is not pass by OOS limit.");
				// Stop EQP
			} else if (summary.getStart_Avg() < filterSetting.getOOC_UpLimit()
					|| summary.getStart_Avg() > filterSetting.getOOC_LowLimit()) {
				logger.info("CFCoater Calculation Start_Avg is not pass by OOC limit.");

			} else if (summary.getMid_Avg() < filterSetting.getOOC_UpLimit()
					|| summary.getMid_Avg() > filterSetting.getOOC_LowLimit()) {
				logger.info("CFCoater Calculation Mid_Avg is not pass by OOC limit.");

			} else if (summary.getEnd_Avg() < filterSetting.getOOC_UpLimit()
					|| summary.getEnd_Avg() > filterSetting.getOOC_LowLimit()) {
				logger.info("CFCoater Calculation End_Avg is not pass by OOC limit.");

			} else {
				logger.info("CFCoater Calculation Pass by all limit.");
				Coater_PDS_Data CoaterPds = Coater_PDS_Data_CRUD.read(summary.getPreEqpId(), summary.getPreRecipe_No());
				Coater_Param_Setting CoaterSetting = Coater_Param_Setting_CRUD.read(summary.getPreEqpId(),
						summary.getPreRecipe_No());

				// BetaOffset begin
				BetaOffset = -1.0 * ((summary.getMid_Avg() - summary.getStart_Avg()) / CoaterSetting.getCot_Mid_Diff())
						* CoaterSetting.getBETA_Cot_parameter();
				// 四捨五入到小數點下四位
				BetaOffset = new BigDecimal(BetaOffset).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

				// BetaOffset end

				// T3Offset begin
				T3Offset = -1.0 * (summary.getStart_Avg()
						+ (BetaOffset * CoaterSetting.getCot_Mid_Diff() * CoaterSetting.getBeta_Coff_For_Cot_Start()
								/ CoaterSetting.getBETA_Cot_parameter())
						- summary.getTatget() * CoaterSetting.getT3_Cot_parameter()
								/ CoaterSetting.getCot_Start_Diff());
				// 四捨五入到小數點下四位
				T3Offset = new BigDecimal(T3Offset).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				// T3Offset end

				// PDTOffset begin
				PDTOffset = -1.0 * ((summary.getEnd_Avg() + (BetaOffset * CoaterSetting.getCot_Mid_Diff()
						* CoaterSetting.getBeta_Coff_For_Cot_End() / CoaterSetting.getBETA_Cot_parameter())
						- summary.getTatget()) * CoaterSetting.getPDT_Cot_parameter()
						/ CoaterSetting.getCot_End_Diff());
				// 四捨五入到小數點下四位
				PDTOffset = new BigDecimal(PDTOffset).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				// PDTOffset end

				// SqueegeOffset begin
				SqueegeOffset = -1.0 * ((summary.getEnd_Avg() + (BetaOffset * CoaterSetting.getCot_Mid_Diff()
						* CoaterSetting.getBeta_Coff_For_Cot_End() / CoaterSetting.getBETA_Cot_parameter())
						- summary.getTatget()) * CoaterSetting.getSqueegee_L_Cot_parameter()
						/ CoaterSetting.getCot_End_Diff());
				// 四捨五入到小數點下四位
				SqueegeOffset = new BigDecimal(SqueegeOffset).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				// SqueegeOffset end
				
				//BetaOld begin
				BetaOld=10.0*CoaterPds.getDis_Spd_Q2()*CoaterSetting.getSolid_Density()/CoaterPds.getCoatSpd_V1()/CoaterPds.getCoating_W()/CoaterSetting.getDry_Thinkness();
				// 四捨五入到小數點下四位
				BetaOld = new BigDecimal(BetaOld).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				//BetaOld end
				
				//Q2New begin
				Q2New = (BetaOld+BetaOffset)*CoaterPds.getCoatSpd_V1()*CoaterPds.getCoating_W()*CoaterSetting.getDry_Thinkness()/10/CoaterSetting.getSolid_Density();
				// 四捨五入到小數點下四位
				Q2New = new BigDecimal(Q2New).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				//Q2New end
				
				//T3New begin
				T3New = CoaterPds.getAfterDis_T3()+T3Offset;
				// 四捨五入到小數點下四位
				T3New = new BigDecimal(T3New).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				//T3New end
				
				//PDTNew begin
				PDTNew=CoaterPds.getPumpDecel_T() + PDTOffset;
				// 四捨五入到小數點下四位
				PDTNew = new BigDecimal(PDTNew).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				//PDTNew end
				
				//SqueegeNew begin
				SqueegeNew = CoaterPds.getSqueegee_L()+SqueegeOffset;
				//SqueegeNew end
			}

		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}

	private boolean GetPSHGlassSummary(MeasureFileDataBase data, Glass_Sammury_Data summary) {
		boolean result = false;
		try {

			Measure_point_Setting mp = Measure_point_Setting_CRUD.read(data.FetchValue("GLASS_DATA", "RECIPE_ID"));
			if (mp != null) {
				String[] start_Col = mp.getEngage_Col().split(",");
				String[] mid_Col = mp.getPlunge_Col().split(",");
				String[] end_Col = mp.getRetract_COl().split(",");

				for (String SiteName : start_Col) {
					summary.setStart_Avg(summary.getStart_Avg()
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", SiteName, "MPSH")));
				}
				summary.setStart_Avg(summary.getStart_Avg() / (double) start_Col.length);

				for (String SiteName : mid_Col) {
					summary.setMid_Avg(summary.getMid_Avg()
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", SiteName, "MPSH")));
				}
				summary.setMid_Avg(summary.getMid_Avg() / (double) mid_Col.length);

				for (String SiteName : end_Col) {
					summary.setEnd_Avg(summary.getEnd_Avg()
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", SiteName, "MPSH")));
				}
				summary.setEnd_Avg(summary.getEnd_Avg() / (double) end_Col.length);

			} else {
				logger.error("CFCoater GetPSHGlassSummary Measure_point_Setting is null");
				summary = null;
			}
			result = true;
		} catch (Exception e) {
			logger.error("CFCoater GetPSHGlassSummary " + ToolUtility.StackTrace2String(e));
			summary = null;
		}
		return result;
	}

	private String getPreRecipeNo(String BCName, String EqpType, String PPID) {
		String result = "";
		try {
			String BCIP = BCIP_CRUD.read(BCName).getIP();
			if (!BCIP.equals("")) {
				Line BCLine = Line_CRUD.read(BCName, BCIP);
				if (BCLine != null) {
					Node BCNode = Node_CRUD.read(BCLine.getBCNo(), BCLine.getBCLineNo(), BCLine.getBCFabType(), EqpType,
							BCIP);
					if (BCNode != null) {
						PPID ppid = PPID_CRUD.read(BCLine.getBCNo(), BCLine.getBCLineNo(), BCLine.getBCFabType(), PPID,
								BCNode.getBCNodeNo());
						if (ppid != null) {
							result = ppid.getRecipe(BCNode.getNodeNo());
						} else {
							logger.error("CFCoater getPreRecipeNo ppid is null.");
						}
					} else {
						logger.error("CFCoater getPreRecipeNo BCNode is null.");
					}
				} else {
					logger.error("CFCoater getPreRecipeNo BCLine is null.");
				}
			} else {
				logger.error("CFCoater getPreRecipeNo BCIP is null.");
			}

		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}

	private String getPreSubEqpId(String BCName, String EqpType) {
		String result = "";
		try {
			String BCIP = BCIP_CRUD.read(BCName).getIP();
			if (!BCIP.equals("")) {
				Line BCLine = Line_CRUD.read(BCName, BCIP);
				if (BCLine != null) {
					Node BCNode = Node_CRUD.read(BCLine.getBCNo(), BCLine.getBCLineNo(), BCLine.getBCFabType(), EqpType,
							BCIP);
					if (BCNode != null) {
						result = BCNode.getSubEqpID();
					} else {
						logger.error("CFCoater getPreSubEqpId BCNode is null.");
					}
				} else {
					logger.error("CFCoater getPreSubEqpId BCLine is null.");
				}
			} else {
				logger.error("CFCoater getPreSubEqpId BCIP is null.");
			}

		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}

}
