package com.innolux.R2R.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.ConfigBase;
import com.innolux.R2R.common.base.MeasureFileDataBase;
import com.innolux.R2R.common.base.RegulationCollection;
import com.innolux.R2R.handler.dataProvider.Config;
import com.innolux.R2R.interfaces.IFileData;
import com.innolux.services.MeasureFileReader;

public class CF_CoaterSource implements IFileData {
	private static Logger logger = Logger.getLogger(CF_CoaterSource.class);

	public CF_CoaterSource(String _FilePatch) {
		MeasureFileReader fileService = new MeasureFileReader();
		fileService.setFileHandler(this, _FilePatch);
		fileService.start();
	}

	@Override
	public void onFileData(MeasureFileDataBase data) {
		try {
			String EqpId = data.FetchInfo("HEADER", "EQ_ID");
			String SubEqpId = data.FetchInfo("HEADER", "SUBEQ_ID");
			
			String PreEqpId1 = data.FetchValue("PDS_GLASS_DATA", "PreEQ_ID_1");
			String PreSubEqpId1 = data.FetchValue("PDS_GLASS_DATA", "PreSubEQ_ID_1");
			String Recipe = data.FetchValue("GLASS_DATA", "RECIPE_ID");

//			String Engage_Col = Status.Get(R2R_ID, Recipe, "Engage_Col");
//			String Plunge_Col = Status.Get(R2R_ID, Recipe, "Plunge_Col");
//			String Retract_Col = Status.Get(R2R_ID, Recipe, "Retract_Col");

//			String StartTime = data.FetchValue("EQP_GLASS_DATA", "START_TIME", this.R2R_ID);
//			if (!StartTime.equals("")) {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
//				Date StartDate = sdf.parse(StartTime);
//
//				long processTime = StartDate.getTime();
//				long preProcessModifyTime = Feedback.Get(getPreProcess());
//				if (processTime != 0) {
//
//					if (processTime >= preProcessModifyTime) {
//
//						RegulationCollection amendment = Caculation(data);
//						if (amendment != null) {
//							logger.info("Run to run ID:" + this.R2R_ID + " Amendment is " + amendment.toString());
//							logger.info("Run to run ID:" + this.R2R_ID + " Start to amendment.");
//
//						} else {
//							logger.info("Run to run ID:" + this.R2R_ID + " Amendment is empty, do nothing.");
//						}
//
//					} else {
//						logger.info("Run to run ID:" + this.R2R_ID + " onFileData This file's process time is "
//								+ processTime + ". Wait for the measure file, which processed after "
//								+ preProcessModifyTime + ".");
//					}
//				} else {
//					logger.error("Run to run ID:" + this.R2R_ID + " onFileData preProcessModifyTime is empty.");
//				}
//
//			} else {
//				logger.error("Run to run ID:" + this.R2R_ID + " onFileData StartTime is empty.");
//			}
		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
	}

	private RegulationCollection Caculation(MeasureFileDataBase data) {
		RegulationCollection result = new RegulationCollection();

		try {

//			List<String> MPSHList = data.FetchList("SITE_DATA", "MPSH", this.R2R_ID);
//			if (MPSHList.size() != 0) {
//
//				String[] Engage_Ary = this.Engage_Col.split(",");
//				String[] Plunge_Ary = this.Plunge_Col.split(",");
//				String[] Retract_Ary = this.Retract_Col.split(",");
//				double Engage_Avg = 0;
//				double Plunge_Avg = 0;
//				double Retract_Avg = 0;
//
//				for (String eachEngage : Engage_Ary) {
//					int site = Integer.parseInt(eachEngage) - 1; // List's index
//																	// is begin
//																	// from 0.
//					Engage_Avg += Double.parseDouble(MPSHList.get(site));
//				}
//				Engage_Avg = Engage_Avg / (double) Engage_Ary.length;
//
//				for (String eachPlunge : Plunge_Ary) {
//					int site = Integer.parseInt(eachPlunge) - 1; // List's index
//																	// is begin
//																	// from 0.
//					Plunge_Avg += Double.parseDouble(MPSHList.get(site));
//				}
//				Plunge_Avg = Plunge_Avg / (double) Plunge_Ary.length;
//
//				for (String eachRetract : Retract_Ary) {
//					int site = Integer.parseInt(eachRetract) - 1; // List's
//																	// index is
//																	// begin
//																	// from 0.
//					Retract_Avg += Double.parseDouble(MPSHList.get(site));
//				}
//				Retract_Avg = Retract_Avg / (double) Retract_Ary.length;
//
//				logger.info("Run to run ID:" + this.R2R_ID + " Engage_Avg:" + Engage_Avg + " Plunge_Avg:" + Plunge_Avg
//						+ " Retract_Avg:" + Retract_Avg);
//
//			}

		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}

	private String getPreProcess() {
		String R2RID = "";
//		try {
//
//		} catch (Exception e) {
//			logger.error("Run to run ID:" + this.R2R_ID + " " + ToolUtility.StackTrace2String(e));
//		}
		return R2RID;
	}

}
