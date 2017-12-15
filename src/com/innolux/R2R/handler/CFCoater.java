package com.innolux.R2R.handler;

import org.apache.log4j.Logger;

import com.innolux.R2R.bc.BC;
import com.innolux.R2R.cf_coater.model.AP_Status;
import com.innolux.R2R.cf_coater.model.AP_Status_CRUD;
import com.innolux.R2R.cf_coater.model.False_Range_Setting;
import com.innolux.R2R.cf_coater.model.False_Range_Setting_CRUD;
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

	private class GlassSummary {
		public double Engage_Avg = 0;
		public double Plunge_Avg = 0;
		public double Retract_Avg = 0;
	}

	public CFCoater(String _SourcePatch, String _NgPath) {
		MeasureFileReader fileService = new MeasureFileReader();
		fileService.setFileHandler(this, _SourcePatch, _NgPath);
		fileService.start();
	}

	@Override
	public void onFileData(MeasureFileDataBase data) {
		try {
			String EqpId = data.FetchInfo("HEADER", "EQ_ID");
			if (EqpId.equals("")) {
				return;
			}
			String SubEqpId = data.FetchInfo("HEADER", "SUBEQ_ID");
			if (SubEqpId.equals("")) {
				return;
			}
			String PreEqpId1 = EqpId;
			String PreSubEqpId1 = getPreSubEqpId(EqpId, "COT");
			if (PreSubEqpId1.equals("")) {
				return;
			}
			String PPID = data.FetchValue("GLASS_DATA", "RECIPE_ID");
			if (PPID.equals("")) {
				return;
			}
			String PreRecipeNo = getPreRecipeNo(EqpId, "COT", PPID);
			if (PreRecipeNo.equals("")) {
				return;
			}
			String RecipeNo = data.FetchValue("EQP_GLASS_DATA", "RECIPE_NO");
			if (RecipeNo.equals("")) {
				return;
			}
			Last_Process_Time lastTObj = Last_Process_Time_CRUD.read(PreSubEqpId1, RecipeNo);

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
				MeasureFileReader.DeleteAllFiles("", "", "", PreEqpId1, PreSubEqpId1, PreRecipeNo);
			}

			if (PSHPreFilter(data)) {
				// store to waiting handle list
				//data.StoreFile("", "", "", PreEqpId1, PreSubEqpId1, PreRecipeNo);
				cfg.setCurrent_Count(cfg.getCurrent_Count() + 1);

				if (cfg.getCurrent_Count() >= cfg.getTotal_Count()) { // calculate
																		// feedback
					RegulationCollection feedbackObj = PSHCalculation(data);
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
					MeasureFileReader.DeleteAllFiles("", "", "", PreEqpId1, PreSubEqpId1, PreRecipeNo);
				}

			}

			// update last process time
			lastTObj.setUpdateTime(System.currentTimeMillis());
			Last_Process_Time_CRUD.update(lastTObj);

		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
	}

	private boolean PSHPreFilter(MeasureFileDataBase data) {
		boolean result = false;
		try {
			GlassSummary summary = GetPSHGlassSummary(data);
			
			if(summary!=null){
				False_Range_Setting filterSetting = False_Range_Setting_CRUD.read(data.FetchValue("GLASS_DATA", "RECIPE_ID"));
				if(summary.Engage_Avg>filterSetting.getFilter_UpLimit()||summary.Engage_Avg<filterSetting.getFilter_LowLimit()){
					logger.info("CFCoater PreFilter Engage_Avg is not pass by OOS limit.");
					return false;
				}
				
				if(summary.Plunge_Avg>filterSetting.getFilter_UpLimit()||summary.Plunge_Avg<filterSetting.getFilter_LowLimit()){
					logger.info("CFCoater PreFilter Plunge_Avg is not pass by OOS limit.");
					return false;
				}
				
				if(summary.Retract_Avg>filterSetting.getFilter_UpLimit()||summary.Retract_Avg<filterSetting.getFilter_LowLimit()){
					logger.info("CFCoater PreFilter Retract_Avg is not pass by OOS limit.");
					return false;
				}
				
			}else{
				logger.error("CFCoater PreFilter summary is null.");
			}
			

		} catch (Exception e) {
			logger.error("CFCoater PreFilter " + ToolUtility.StackTrace2String(e));
		}
		return result;
	}

	private RegulationCollection PSHCalculation(MeasureFileDataBase data) {
		RegulationCollection result = new RegulationCollection();

		try {

		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}

	private GlassSummary GetPSHGlassSummary(MeasureFileDataBase data) {
		GlassSummary summary = new GlassSummary();
		try {
			Measure_point_Setting mp = Measure_point_Setting_CRUD.read(data.FetchValue("GLASS_DATA", "RECIPE_ID"));
			if (mp != null) {
				String[] Engage_Col = mp.getEngage_Col().split(",");
				String[] Plunge_Col = mp.getPlunge_Col().split(",");
				String[] Retract_Col = mp.getRetract_COl().split(",");

				for (String SiteName : Engage_Col) {
					summary.Engage_Avg += Double
							.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", SiteName, "MPSH"));
				}
				summary.Engage_Avg = summary.Engage_Avg / (double) Engage_Col.length;
				for (String SiteName : Plunge_Col) {
					summary.Plunge_Avg += Double
							.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", SiteName, "MPSH"));
				}
				summary.Plunge_Avg = summary.Plunge_Avg / (double) Plunge_Col.length;
				for (String SiteName : Retract_Col) {
					summary.Retract_Avg += Double
							.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", SiteName, "MPSH"));
				}
				summary.Retract_Avg = summary.Retract_Avg / (double) Retract_Col.length;

			} else {
				logger.error("CFCoater GetGlassSummary Measure_point_Setting is null");
				summary = null;
			}
		} catch (Exception e) {
			logger.error("CFCoater GetGlassSummary " + ToolUtility.StackTrace2String(e));
			summary = null;
		}
		return summary;
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
