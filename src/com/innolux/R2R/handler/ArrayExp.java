package com.innolux.R2R.handler;

import org.apache.log4j.Logger;

import com.innolux.R2R.ArrayExp.model.ExpMeasGlass;
import com.innolux.R2R.ArrayExp.model.T_EqGroup2EqID;
import com.innolux.R2R.ArrayExp.model.T_EqGroup2EqID_CRUD;
import com.innolux.R2R.common.base.MeasureFileDataBase;
import com.innolux.R2R.interfaces.IFileData;
import com.innolux.services.MeasureFileReader;

public class ArrayExp implements IFileData{
	private Logger logger = Logger.getLogger(this.getClass());
	public final boolean DEBUG = false;

	public static void main(String [] argv) {
		//System.out.println(MeasureFileReader.class);	

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
	
	ExpMeasGlass csv2ExpMeasGlass(MeasureFileDataBase csv){
		ExpMeasGlass aGlass = new ExpMeasGlass();

		aGlass.setGlassID(csv.FetchValue("GLASS_DATA", "Glass_ID"));
		aGlass.setProductName(csv.FetchValue("GLASS_DATA", "Product"));
		
		aGlass.setExpID(csv.FetchValue("PDS_GLASS_DATA", "PreEQ_ID_1"));
		
		T_EqGroup2EqID eqGroup2EqID = T_EqGroup2EqID_CRUD.read(expID);
		aGlass.setExpSupplier(eqGroup2EqID.getEqGroup()); // Nikon or Canon
		
		aGlass.setExpRcpID(csv.FetchValue("PDS_GLASS_DATA", "PreRecipe_ID_1"));
		
		T_ExpRcpID2Name expRcpID2Name = T_ExpRcpID2Name_CRUD.read(productName, expRcpID);
		aGlass.setExpRcpName(expRcpID2Name.Exp_Rcp_Name);
		
		aGlass.setMeasStepID(csv.FetchValue("GLASS_DATA", "Step_Seq"));
		aGlass.setMeasRcpID(csv.FetchValue("GLASS_DATA", "Recipe_ID"));

		if(expSupplier.equal("Canon")) {
			aGlass.setAdcOrFdc(ExpMeasGlass.ADC);
		}else if(expSupplier.equal("Nikon")) {
			int olOrDol = checkOlOrDol(MeasureFileDataBase csv); // TODO
			aGlass.setOlOrDol(olOrDol); // both ADC & FDC is OL
		}

		public List<String> FetchList("", String Name, String R2R_ID) {
		Vector2D minOL;
		Vector2D maxOL;
		Vector2D minDOL;
		Vector2D maxDOL;

		long exposueTime;
		long trackInTime;

		double ratio;

		List<Vector2D> measResultList;
	}

	
}
