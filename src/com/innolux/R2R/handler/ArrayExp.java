package com.innolux.R2R.handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.innolux.R2R.ArrayExp.model.ExpMeasGlass;
import com.innolux.R2R.ArrayExp.model.T_AutoFeedbackSetting;
import com.innolux.R2R.ArrayExp.model.T_AutoFeedbackSetting_CRUD;
import com.innolux.R2R.ArrayExp.model.T_EqGroup2EqID;
import com.innolux.R2R.ArrayExp.model.T_EqGroup2EqID_CRUD;
import com.innolux.R2R.ArrayExp.model.T_ExpRcpID2Name;
import com.innolux.R2R.ArrayExp.model.T_ExpRcpID2Name_CRUD;
import com.innolux.R2R.ArrayExp.model.Vector2D;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.MeasureFileDataBase;
import com.innolux.R2R.interfaces.IFileData;
import com.innolux.services.MeasureFileReader;

public class ArrayExp implements IFileData{
	private Logger logger = Logger.getLogger(this.getClass());
	public final boolean DEBUG = false;

	public static void main(String [] argv) {
		//System.out.println(MeasureFileReader.class);	
		ArrayExp a = new ArrayExp("C:\\");
	}
	
	public ArrayExp(String csvUpperFilePath) {
		MeasureFileReader fileService = new MeasureFileReader();
		 fileService.setFileHandler(this, csvUpperFilePath);
		fileService.start();

	}

	@Override
	public void onFileData(MeasureFileDataBase csv) {
		// when receive a csv 
		ExpMeasGlass emGlass = csv2ExpMeasGlass(csv);

//		boolean isDataError = checkIsDataError(emGlass);
//		if(isDataError == true) {
//			logger.debug("ArrayExp onFileData: glass data not match");
//			return;
//		}
//		logger.debug("ArrayExp onFileData: glass data matched");
//		
//		boolean isFeedbackData = checkIsFeedbackData(emGlass);
//		if(isFeedbackData == false) {
//			logger.debug("ArrayExp onFileData: isFeedbackData = false");
//			return;
//		}
//		logger.debug("ArrayExp onFileData: isFeedbackData = true");
//
//		boolean isStartFeedback = checkStartFeedback(emGlass);
//		if(isStartFeedback == false) {
//			logger.debug("ArrayExp onFileData: isStartFeedback = false");
//			return;
//		}
//		logger.debug("ArrayExp onFileData: isStartFeedback = true");
//
//		feedbackFile = null; // TODO
//		if(emGlass.expSupplier.equal("Nikon")){
//			feedbackFile = createNikonFeedbackFile(emGlass);
//			sendFdbkFile2Canon(feedbackFile);
//		}else if(emGlass.expSupplier.equal("Canon")){
//			feedbackFile = createCanonFeedbackFile(emGlass);
//			sendFdbkFile2Nanon(feedbackFile);
//		}
		// save feedback history to DB
	}
	
	private ExpMeasGlass csv2ExpMeasGlass(MeasureFileDataBase csv){
		ExpMeasGlass aGlass = new ExpMeasGlass();
		try {
			aGlass.setGlassID(csv.FetchValue("GLASS_DATA", "Glass_ID"));
			aGlass.setProductName(csv.FetchValue("GLASS_DATA", "Product"));
			
			aGlass.setExpID(csv.FetchValue("PDS_GLASS_DATA", "PreEQ_ID_1"));
			aGlass.setExpStepID(csv.FetchValue("PDS_GLASS_DATA", "PreStep_Seq_1"));

			T_EqGroup2EqID eqGroup2EqID = T_EqGroup2EqID_CRUD.read(aGlass.getExpID());
			aGlass.setExpSupplier(eqGroup2EqID.getEqGroup()); // Nikon or Canon
			
			aGlass.setExpRcpID(csv.FetchValue("PDS_GLASS_DATA", "PreRecipe_ID_1"));
			
			T_ExpRcpID2Name expRcpID2Name = T_ExpRcpID2Name_CRUD.read(aGlass.getProductName(), 
																		aGlass.getExpRcpID());
			aGlass.setExpRcpName(expRcpID2Name.getExpRcpName());
			
			aGlass.setMeasStepID(csv.FetchValue("GLASS_DATA", "Step_Seq"));
			aGlass.setMeasRcpID(csv.FetchValue("GLASS_DATA", "Recipe_ID"));
			
			Vector2D minOL = new Vector2D;
			minOL.setxValue(getCsvValByRowCol(csv, "Min", "OL01");
			minOL.setyValue(getCsvValByRowCol(csv, "Min", "OL02");

			Vector2D maxOL = new Vector2D;
			maxOL.setxValue(getCsvValByRowCol(csv, "Max", "OL01");
			maxOL.setyValue(getCsvValByRowCol(csv, "Max", "OL02");

			Vector2D minDOL = new Vector2D;
			minDOL.setxValue(getCsvValByRowCol(csv, "Min", "DOL01");
			minDOL.setyValue(getCsvValByRowCol(csv, "Min", "DOL02");

			Vector2D maxDOL = new Vector2D;
			maxDOL.setxValue(getCsvValByRowCol(csv, "Max", "DOL01");
			maxDOL.setyValue(getCsvValByRowCol(csv, "Max", "DOL02");
			
			if(aGlass.getExpSupplier().equals("Canon")) {
				aGlass.setAdcOrFdc(ExpMeasGlass.ADC);
				aGlass.setOlOrDol(ExpMeasGlass.OL); // both ADC & FDC is OL
			}else if(aGlass.getExpSupplier().equals("Nikon")) {
				int olOrDol = checkOlOrDol(minOL, maxOL, minDOL, maxDOL); // TODO
				aGlass.setOlOrDol(olOrDol); // both ADC & FDC is OL
			}

			SimpleDateFormat simDateFormat = new SimpleDateFormat();
			simDateFormat.applyPattern("yyyyMMdd HHmmssSSS");
			Date eTime = simDateFormat.parse( csv.FetchValue("PDS_GLASS_DATA", "PreEnd_Time_1") );		
			aGlass.setExposueTime( eTime.getTime() );
			
			aGlass.setTrackInTime( getCsvTrackInTime(aGlass.getGlassID(), 
												aGlass.getExpStepID(),
												aGlass.getExpID()));

			T_AutoFeedbackSetting autoFeedbackSetting = T_AutoFeedbackSetting_CRUD.read(
															aGlass.getProductName(), 
															aGlass.getExpID(), 
															aGlass.getExpRcpID(), 
															aGlass.getMeasStepID(), 
															aGlass.getMeasRcpID(), 
															aGlass.getAdcOrFdc());
			aGlass.setRatio(autoFeedbackSetting.getRatio());

			aGlass.setMeasPointList( readMeasPointList("Coord_X", "Coord_Y", "OL01", "OL02") );
			
		}catch(Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return aGlass;
	}

	private double getCsvValByRowCol(MeasureFileDataBase csv, String statistic, String key){
		List<String> statisticList = csv.FetchList("GLASS_SUMMARY", Statistic);
		List<String> keyList = csv.FetchList("GLASS_SUMMARY", key);
		
		if(statisticList == null || keyList == null){
			logger.debug("ArrayExp getCsvVector: " + statistic + " List = null || " + key + " List = null");
			return null;
		}else if(statisticList.size() == 0 || keyList.size() == 0){
			logger.debug("ArrayExp getCsvVector: " + statistic + " size = 0 || " + key + " size = 0");
			return null;
		}else if(statisticList.size() != keyList.size()){
			logger.debug("ArrayExp getCsvVector: " + statistic + " size != 0 " + key + " size");
			return null;
		}

		int statisticInd = statisticList.indexOf(statistic);

		//String 


		return null;
	}

	private Vector2D getCsvVector(MeasureFileDataBase csv, String minOrMax, String olOrDol){
		return null;
	}

	private long getCsvTrackInTime(String GlassID, String expStepId, String expID){
		long result = -1;
//		select componentid,eqpid,stepid,max(txntimestamp)
//		from fwamtcomptrackhistory
//		where activity = 'TrackIn'
//		  and componentid = 'FEZL2AH414K231'     -- 填 Glass ID
//		  and stepid = (select distinct nodename 
//		                 from fwflatnode
//		                 where stepseq = '1632') -- 填 Stepseq
//		  and eqpid = '2AEXPB00'                 -- Process EQ
//		group by componentid,eqpid,stepid
		
		return result;
	}
	
	private int checkOlOrDol(Vector2D minOL, Vector2D maxOL, Vector2D minDOL, Vector2D maxDOL){
		return 0;
	}
	
	private List<Vector2D> readMeasPointList (String Coord_X, String Coord_Y, String OL01, String OL02){
		return null;
		
	}
}
