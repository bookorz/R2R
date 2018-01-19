package com.innolux.R2R.handler;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.innolux.R2R.bc.BC;
import com.innolux.R2R.cf_coater.model.AP_Status;
import com.innolux.R2R.cf_coater.model.AP_Status_CRUD;
import com.innolux.R2R.cf_coater.model.CfCoater_Feedback_History;
import com.innolux.R2R.cf_coater.model.CfCoater_Feedback_History_CRUD;
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
import com.innolux.R2R.common.GlobleVar;
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

	public static void main(String[] args) {

		new CFCoater("C:\\PSH\\", "C:\\NG\\");
		// ToolUtility.demical2Hex(2976.0761, 0.001,32, "CfCoater");
	}

	@Override
	public void onFileData(MeasureFileDataBase data) {
		logger.info("File name:"+data.getFileName());
		Glass_Sammury_Data summary = new Glass_Sammury_Data();

		try {
			String EqpId = data.FetchInfo("HEADER", "EQ_ID").trim();
			if (EqpId.equals("")) {
				return;
			}
			String SubEqpId = data.FetchInfo("HEADER", "SUBEQ_ID").trim();
			if (SubEqpId.equals("")) {
				ToolUtility.saveToLogHistoryDB("CFCoater", GlobleVar.LogErrorType,
						"SubEqpId is null:" + data.getFileName());
				return;
			} else {
				summary.setEqpId(SubEqpId);
			}
			// String PreEqpId1 = EqpId;
			String PreSubEqpId1 = getPreSubEqpId(EqpId, "COT");
			if (PreSubEqpId1.equals("")) {
				ToolUtility.saveToLogHistoryDB("CFCoater", GlobleVar.LogErrorType,
						"PreSubEqpId1 is null:" + data.getFileName());
				return;
			} else {
				summary.setPreEqpId(PreSubEqpId1);
			}
			String PPID = data.FetchValue("GLASS_DATA", "RECIPE_ID").trim();
			if (PPID.equals("")) {
				ToolUtility.saveToLogHistoryDB("CFCoater", GlobleVar.LogErrorType,
						"PPID is null:" + data.getFileName());
				return;
			} else {
				summary.setPPID(PPID);
				summary.setPrePPID(PPID);
			}
			String PreRecipeNo = getPreRecipeNo(EqpId, "COT", PPID);
			if (PreRecipeNo.equals("")) {
				ToolUtility.saveToLogHistoryDB("CFCoater", GlobleVar.LogErrorType,
						"PreRecipeNo is null:" + data.getFileName());
				return;
			} else {
				summary.setPreRecipe_No(PreRecipeNo);
			}
			String RecipeNo = data.FetchValue("EQP_GLASS_DATA", "RECIPE_NO").trim();
			if (RecipeNo.equals("")) {
				ToolUtility.saveToLogHistoryDB("CFCoater", GlobleVar.LogErrorType,
						"RecipeNo is null:" + data.getFileName());
				return;
			} else {
				summary.setRecipe_No(RecipeNo);
			}

			String Glass_ID = data.FetchValue("GLASS_DATA", "GLASS_ID").trim();
			if (Glass_ID.equals("")) {
				ToolUtility.saveToLogHistoryDB("CFCoater", GlobleVar.LogErrorType,
						"Glass_ID is null:" + data.getFileName());
				return;
			} else {
				summary.setGlass_Id(Glass_ID);
			}

			summary.setTimeStamp(System.currentTimeMillis());
			False_Range_Setting filterSetting = False_Range_Setting_CRUD.read(summary.getPPID());

			String layer = PPID.substring(PPID.length() - 3, PPID.length() - 2);
			if (layer.equals("P")) {
				// PSH Measure File
				if (!GetPSHGlassSummary(data, summary)) {
					ToolUtility.saveToLogHistoryDB("CFCoater", GlobleVar.LogErrorType,
							"GetPSHGlassSummary error:" + data.getFileName());
					return;
				}
			} else {
				// BM R B G Measure File
				if (!GetRBGGlassSummary(data, summary, layer, filterSetting)) {
					ToolUtility.saveToLogHistoryDB("CFCoater", GlobleVar.LogErrorType,
							"GetRBGGlassSummary error:" + data.getFileName());

					return;
				}
			}

			Last_Process_Time lastTObj = Last_Process_Time_CRUD.read(SubEqpId, RecipeNo);

			if (lastTObj == null) {
				lastTObj = new Last_Process_Time();
				lastTObj.setSubEqpId(SubEqpId);
				lastTObj.setRecipeNo(RecipeNo);
				lastTObj.setUpdateTime(System.currentTimeMillis());
				Last_Process_Time_CRUD.create(lastTObj);
				lastTObj = Last_Process_Time_CRUD.read(SubEqpId, RecipeNo);
			}

			AP_Status cfg = AP_Status_CRUD.read(PreSubEqpId1, PPID);
			if (cfg == null) {
				cfg = new AP_Status();
				cfg.setPreEqpId(PreSubEqpId1);
				cfg.setPreRecipe(PPID);
				cfg.setLast_Time(System.currentTimeMillis());
				AP_Status_CRUD.create(cfg);
				cfg = AP_Status_CRUD.read(PreSubEqpId1, PPID);
			}

			Measure_point_Setting setting = Measure_point_Setting_CRUD.read(PPID);
			if (setting == null) {
				ToolUtility.saveToLogHistoryDB("CF_Coater", "Error",
						"Measure_point_Setting is not found. PPID:" + PPID);
				return;
			}

			if (System.currentTimeMillis() - lastTObj.getUpdateTime() > setting.getExpire_Interval_Time()) {
				// if expired, reset all

				Glass_Sammury_Data_CRUD.delete(PreSubEqpId1, PPID);
			}

			if (PreFilter(summary, layer, filterSetting)) {
				// store to waiting handle list
				Glass_Sammury_Data_CRUD.create(summary);
				List<Glass_Sammury_Data> glassList = Glass_Sammury_Data_CRUD.read(summary.getPreEqpId(),
						summary.getPPID());
				// cfg.setCurrent_Count(cfg.getCurrent_Count() + 1);
				logger.info("PreSubEqpId1:" + PreSubEqpId1 + " current:" + glassList.size() + " total:"
						+ setting.getTotal_Count());
				if (glassList.size() >= setting.getTotal_Count()) { // calculate
																	// feedback
					RegulationCollection feedbackObj = Calculation(summary, glassList, layer, filterSetting);
					if (feedbackObj.GetRegulationCount() != 0) {
						// feedback to coater
						// BC bc = new BC(EqpId);
						//
						// if (bc.Excute(feedbackObj)) {
						// logger.info("SendToBC successful.");
						// } else {
						// logger.error("SendToBC fail.");
						// }
					}
					// cfg.setCurrent_Count(0);
					Glass_Sammury_Data_CRUD.delete(PreSubEqpId1, PPID);
				}
			}

			// update last process time
			lastTObj.setUpdateTime(System.currentTimeMillis());
			Last_Process_Time_CRUD.update(lastTObj);
			AP_Status_CRUD.update(cfg);
		} catch (Exception e) {

			ToolUtility.saveToLogHistoryDB("CFCoater", GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
		}
	}

	private boolean PreFilter(Glass_Sammury_Data summary, String layer, False_Range_Setting filterSetting) {
		boolean result = false;
		if (!layer.equals("P")) {
			return true;
		}
		try {

			if (summary != null) {

				if (filterSetting != null) {
					if (summary.getTatget() == 0) {
						summary.setTatget(filterSetting.getSpec());
					}
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
					logger.error("CFCoater PreFilter filterSetting is null.");
					ToolUtility.saveToLogHistoryDB("CF_Coater", "Error",
							"filterSetting is not found. PPID:" + summary.getPPID());
				}
			} else {
				logger.error("CFCoater PreFilter summary is null.");
				ToolUtility.saveToLogHistoryDB("CF_Coater", "Error", "PreFilter summary is null.");
			}
			result = true;
		} catch (Exception e) {

			ToolUtility.saveToLogHistoryDB("CFCoater", GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
		}
		return result;
	}

	private RegulationCollection Calculation(Glass_Sammury_Data summary, List<Glass_Sammury_Data> glassList,
			String layer, False_Range_Setting filterSetting) {
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
		String Q2NewHex = "";
		String T3NewHex = "";
		String PDTNewHex = "";
		String SqueegeNewHex = "";

		try {

			summary.setStart_Avg(0);
			summary.setMid_Avg(0);
			summary.setEnd_Avg(0);
			for (Glass_Sammury_Data eachGlass : glassList) {
				logger.debug("EachGlass:" + eachGlass);
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
					&& summary.getStart_Avg() > filterSetting.getOOC_LowLimit()) {
				logger.info("CFCoater Calculation Start_Avg is not pass by OOC limit.");

			} else if (summary.getMid_Avg() < filterSetting.getOOC_UpLimit()
					&& summary.getMid_Avg() > filterSetting.getOOC_LowLimit()) {
				logger.info("CFCoater Calculation Mid_Avg is not pass by OOC limit.");

			} else if (summary.getEnd_Avg() < filterSetting.getOOC_UpLimit()
					&& summary.getEnd_Avg() > filterSetting.getOOC_LowLimit()) {
				logger.info("CFCoater Calculation End_Avg is not pass by OOC limit.");

			} else {
				logger.info("CFCoater Calculation Pass by all limit.");
				Coater_PDS_Data CoaterPds = Coater_PDS_Data_CRUD.read(summary.getPreEqpId(), summary.getPPID());
				Coater_Param_Setting CoaterSetting = Coater_Param_Setting_CRUD.read(summary.getPPID());

				// BetaOffset begin
				BetaOffset = -1.0 * ((summary.getMid_Avg() - summary.getTatget()) / CoaterSetting.getCot_Mid_Diff())
						* CoaterSetting.getBETA_Cot_parameter();
				// 四捨五入到小數點下四位
				// BetaOffset = new BigDecimal(BetaOffset).setScale(4,
				// BigDecimal.ROUND_HALF_UP).doubleValue();
				logger.debug("BetaOffset:" + BetaOffset);
				// BetaOffset end

				// T3Offset begin
				T3Offset = -1.0 * ((summary.getStart_Avg() + (BetaOffset * CoaterSetting.getCot_Mid_Diff()
						* CoaterSetting.getBeta_Coff_For_Cot_Start() / CoaterSetting.getBETA_Cot_parameter())
						- summary.getTatget()) * CoaterSetting.getT3_Cot_parameter()
						/ CoaterSetting.getCot_Start_Diff());
				// 四捨五入到小數點下四位
				// T3Offset = new BigDecimal(T3Offset).setScale(4,
				// BigDecimal.ROUND_HALF_UP).doubleValue();
				logger.debug("T3Offset:" + T3Offset);
				// T3Offset end

				// PDTOffset begin
				PDTOffset = -1.0 * ((summary.getEnd_Avg() + (BetaOffset * CoaterSetting.getCot_Mid_Diff()
						* CoaterSetting.getBeta_Coff_For_Cot_End() / CoaterSetting.getBETA_Cot_parameter())
						- summary.getTatget()) * CoaterSetting.getPDT_Cot_parameter()
						/ CoaterSetting.getPDT_Cot_End_Diff());
				// 四捨五入到小數點下四位
				// PDTOffset = new BigDecimal(PDTOffset).setScale(4,
				// BigDecimal.ROUND_HALF_UP).doubleValue();
				logger.debug("PDTOffset:" + PDTOffset);
				// PDTOffset end

				// SqueegeOffset begin
				if (layer.equals("P")) {
					SqueegeOffset = -1.0 * ((summary.getEnd_Avg() + (BetaOffset * CoaterSetting.getCot_Mid_Diff()
							* CoaterSetting.getBeta_Coff_For_Cot_End() / CoaterSetting.getBETA_Cot_parameter())
							- summary.getTatget()) * CoaterSetting.getSqueegee_L_Cot_parameter()
							/ CoaterSetting.getSQ_Cot_End_Diff());
				}
				// 四捨五入到小數點下四位
				// SqueegeOffset = new BigDecimal(SqueegeOffset).setScale(4,
				// BigDecimal.ROUND_HALF_UP).doubleValue();
				logger.debug("SqueegeOffset:" + SqueegeOffset);
				// SqueegeOffset end

				// BetaOld begin
				BetaOld = 10.0 * CoaterPds.getDis_Spd_Q2() * CoaterSetting.getSolid_Density()
						/ CoaterPds.getCoatSpd_V1() / CoaterPds.getCoating_W() / CoaterSetting.getDry_Thinkness();
				// 四捨五入到小數點下四位
				// BetaOld = new BigDecimal(BetaOld).setScale(4,
				// BigDecimal.ROUND_HALF_UP).doubleValue();
				logger.debug("BetaOld:" + BetaOld);
				// BetaOld end
				// -------------------------------------補植----------------------------------------
				// Q2New begin
				Q2New = (BetaOld + BetaOffset) * CoaterPds.getCoatSpd_V1() * CoaterPds.getCoating_W()
						* CoaterSetting.getDry_Thinkness() / 10 / CoaterSetting.getSolid_Density();
				// 四捨五入到小數點下四位
				Q2New = new BigDecimal(Q2New).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				logger.debug("Q2New:" + Q2New);
				Q2NewHex = ToolUtility.demical2Hex(Q2New, 0.001, 32, "CfCoater");
				logger.debug("Q2NewHex:" + Q2NewHex);
				if (Q2NewHex.length() <= 4) {
					result.StoreRegulation(1, Q2NewHex);
				} else {
					result.StoreRegulation(1, Q2NewHex.substring(4, 8));
					result.StoreRegulation(2, Q2NewHex.substring(0, 4));
				}
				// Q2New end

				// T3New begin
				T3New = CoaterPds.getAfterDis_T3() + T3Offset;
				// 四捨五入到小數點下四位
				T3New = new BigDecimal(T3New).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				logger.debug("T3New:" + T3New);
				T3NewHex = ToolUtility.demical2Hex(T3New, 0.001, 32, "CfCoater");
				logger.debug("T3NewHex:" + T3NewHex);
				if (T3NewHex.length() >= 4) {
					result.StoreRegulation(3, T3NewHex);
				} else {
					result.StoreRegulation(3, T3NewHex.substring(4, 8));
					result.StoreRegulation(4, T3NewHex.substring(0, 4));
				}
				// T3New end

				// PDTNew begin
				PDTNew = CoaterPds.getPumpDecel_T() + PDTOffset;
				// 四捨五入到小數點下四位
				PDTNew = new BigDecimal(PDTNew).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				logger.debug("PDTNew:" + PDTNew);
				PDTNewHex = ToolUtility.demical2Hex(PDTNew, 0.001, 32, "CfCoater");
				logger.debug("PDTNewHex:" + PDTNewHex);
				if (PDTNewHex.length() >= 4) {
					result.StoreRegulation(5, PDTNewHex);
				} else {
					result.StoreRegulation(5, PDTNewHex.substring(4, 8));
					result.StoreRegulation(6, PDTNewHex.substring(0, 4));
				}
				// PDTNew end

				// SqueegeNew begin
				if (layer.equals("P")) {
					SqueegeNew = CoaterPds.getSqueegee_L() + SqueegeOffset;
				} else {
					SqueegeNew = CoaterPds.getSqueegee_L();
				}
				// 四捨五入到小數點下四位
				SqueegeNew = new BigDecimal(SqueegeNew).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				logger.debug("SqueegeNew:" + SqueegeNew);
				SqueegeNewHex = ToolUtility.demical2Hex(SqueegeNew, 0.001, 32, "CfCoater");
				logger.debug("SqueegeNewHex:" + SqueegeNewHex);
				if (SqueegeNewHex.length() >= 4) {
					result.StoreRegulation(7, SqueegeNewHex);
				} else {
					result.StoreRegulation(7, SqueegeNewHex.substring(4, 8));
					result.StoreRegulation(8, SqueegeNewHex.substring(0, 4));
				}
				// SqueegeNew end
				// -------------------------------------補植----------------------------------------

				CfCoater_Feedback_History history = new CfCoater_Feedback_History();
				history.setSubEqpId(summary.getPreEqpId());
				history.setPPID(summary.getPPID());
				history.setUser_Id("Auto");
				history.setFeedBack_Time(Calendar.getInstance().getTime());
				history.setFeedback_Value(
						"Q2New:" + Q2New + " T3New:" + T3New + " PDTNew:" + PDTNew + "SqueegeNew:" + SqueegeNew);

				CfCoater_Feedback_History_CRUD.create(history);

			}

		} catch (Exception e) {
			ToolUtility.saveToLogHistoryDB("CFCoater", GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			result = new RegulationCollection();
		}
		return result;
	}

	private boolean GetRBGGlassSummary(MeasureFileDataBase data, Glass_Sammury_Data summary, String layer,
			False_Range_Setting filterSetting) {
		boolean result = false;
		try {
			double start = 0;
			double mid = 0;
			double end = 0;

			switch (layer) {
			case "M":
				try {
					start = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "03", "OD"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "04", "OD"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "05", "OD"));
					start = start / 3;
					summary.setStart_Avg(start);

					mid = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "02", "OD"));
					summary.setMid_Avg(mid);

					end = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "01", "OD"));
					summary.setEnd_Avg(end);
				} catch (Exception e1) {
					start = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "3", "OD"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "4", "OD"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "5", "OD"));
					start = start / 3;
					summary.setStart_Avg(start);

					mid = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "2", "OD"));
					summary.setMid_Avg(mid);

					end = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "1", "OD"));
					summary.setEnd_Avg(end);
				}

				filterSetting.setSpec(
						Double.parseDouble(data.getCsvValByRowCol("SPEC_DEFINITION", "PARAMETER", "OD", "Target")));
				summary.setTatget(filterSetting.getSpec());
				filterSetting.setOOS_UpLimit(
						Double.parseDouble(data.getCsvValByRowCol("SPEC_DEFINITION", "PARAMETER", "OD", "SPEC_HIGH")));
				filterSetting.setOOS_LowLimit(
						Double.parseDouble(data.getCsvValByRowCol("SPEC_DEFINITION", "PARAMETER", "OD", "SPEC_LOW")));
				filterSetting.setOOC_LowLimit(filterSetting.getSpec() - filterSetting.getOOC_LowLimit());
				filterSetting.setOOC_UpLimit(filterSetting.getSpec() + filterSetting.getOOC_UpLimit());
				break;
			case "R":
				try {
					start = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "03", "Rx"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "04", "Rx"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "05", "Rx"));
					start = start / 3;
					summary.setStart_Avg(start);

					mid = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "02", "Rx"));
					summary.setMid_Avg(mid);

					end = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "01", "Rx"));
					summary.setEnd_Avg(end);
				} catch (Exception e1) {
					start = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "3", "Rx"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "4", "Rx"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "5", "Rx"));
					start = start / 3;
					summary.setStart_Avg(start);

					mid = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "2", "Rx"));
					summary.setMid_Avg(mid);

					end = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "1", "Rx"));
					summary.setEnd_Avg(end);
				}
				filterSetting.setSpec(
						Double.parseDouble(data.getCsvValByRowCol("SPEC_DEFINITION", "PARAMETER", "Rx", "Target")));
				summary.setTatget(filterSetting.getSpec());
				filterSetting.setOOS_UpLimit(
						Double.parseDouble(data.getCsvValByRowCol("SPEC_DEFINITION", "PARAMETER", "Rx", "SPEC_HIGH")));
				filterSetting.setOOS_LowLimit(
						Double.parseDouble(data.getCsvValByRowCol("SPEC_DEFINITION", "PARAMETER", "Rx", "SPEC_LOW")));
				filterSetting.setOOC_LowLimit(filterSetting.getSpec() - filterSetting.getOOC_LowLimit());
				filterSetting.setOOC_UpLimit(filterSetting.getSpec() + filterSetting.getOOC_UpLimit());
				break;
			case "B":
				try {
					start = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "03", "BSY"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "04", "BSY"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "05", "BSY"));
					start = start / 3;
					summary.setStart_Avg(start);

					mid = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "02", "BSY"));
					summary.setMid_Avg(mid);

					end = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "01", "BSY"));
					summary.setEnd_Avg(end);
				} catch (Exception e1) {
					start = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "3", "BSY"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "4", "BSY"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "5", "BSY"));
					start = start / 3;
					summary.setStart_Avg(start);

					mid = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "2", "BSY"));
					summary.setMid_Avg(mid);

					end = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "1", "BSY"));
					summary.setEnd_Avg(end);
				}
				filterSetting.setSpec(
						Double.parseDouble(data.getCsvValByRowCol("SPEC_DEFINITION", "PARAMETER", "By", "Target")));
				summary.setTatget(filterSetting.getSpec());
				filterSetting.setOOS_UpLimit(
						Double.parseDouble(data.getCsvValByRowCol("SPEC_DEFINITION", "PARAMETER", "By", "SPEC_HIGH")));
				filterSetting.setOOS_LowLimit(
						Double.parseDouble(data.getCsvValByRowCol("SPEC_DEFINITION", "PARAMETER", "By", "SPEC_LOW")));
				filterSetting.setOOC_LowLimit(filterSetting.getSpec() - filterSetting.getOOC_LowLimit());
				filterSetting.setOOC_UpLimit(filterSetting.getSpec() + filterSetting.getOOC_UpLimit());
				break;
			case "G":
				try {
					start = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "03", "GSY"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "04", "GSY"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "05", "GSY"));
					start = start / 3;
					summary.setStart_Avg(start);

					mid = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "02", "GSY"));
					summary.setMid_Avg(mid);

					end = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "01", "GSY"));
					summary.setEnd_Avg(end);
				} catch (Exception e1) {
					start = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "3", "GSY"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "4", "GSY"))
							+ Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "5", "GSY"));
					start = start / 3;
					summary.setStart_Avg(start);

					mid = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "2", "GSY"));
					summary.setMid_Avg(mid);

					end = Double.parseDouble(data.getCsvValByRowCol("SITE_DATA", "SITE_NAME", "1", "GSY"));
					summary.setEnd_Avg(end);
				}
				filterSetting.setSpec(
						Double.parseDouble(data.getCsvValByRowCol("SPEC_DEFINITION", "PARAMETER", "Gy", "Target")));
				summary.setTatget(filterSetting.getSpec());
				filterSetting.setOOS_UpLimit(
						Double.parseDouble(data.getCsvValByRowCol("SPEC_DEFINITION", "PARAMETER", "Gy", "SPEC_HIGH")));
				filterSetting.setOOS_LowLimit(
						Double.parseDouble(data.getCsvValByRowCol("SPEC_DEFINITION", "PARAMETER", "Gy", "SPEC_LOW")));
				filterSetting.setOOC_LowLimit(filterSetting.getSpec() - filterSetting.getOOC_LowLimit());
				filterSetting.setOOC_UpLimit(filterSetting.getSpec() + filterSetting.getOOC_UpLimit());
				break;
			}

			result = true;
		} catch (Exception e) {

			ToolUtility.saveToLogHistoryDB("CF_Coater", "Error", "計算單片平均錯誤" + ToolUtility.StackTrace2String(e));
			summary = null;
		}
		return result;
	}

	private boolean GetPSHGlassSummary(MeasureFileDataBase data, Glass_Sammury_Data summary) {
		boolean result = false;
		try {

			Measure_point_Setting mp = Measure_point_Setting_CRUD.read(data.FetchValue("GLASS_DATA", "RECIPE_ID"));
			if (mp != null) {
				String[] start_Col = mp.getStart_Col().split(",");
				String[] mid_Col = mp.getMid_Col().split(",");
				String[] end_Col = mp.getEnd_COl().split(",");

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

			ToolUtility.saveToLogHistoryDB("CF_Coater", "Error", "計算單片平均錯誤" + ToolUtility.StackTrace2String(e));
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
								BCNode.getBCNodeNo(), BCIP);
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
			ToolUtility.saveToLogHistoryDB("CFCoater", GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
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
			ToolUtility.saveToLogHistoryDB("CFCoater", GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
		}
		return result;
	}

}
