package com.innolux.R2R.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.innolux.R2R.ArrayExp.model.ExpMeasGlass;
import com.innolux.R2R.ArrayExp.model.T_AutoFeedbackSetting;
import com.innolux.R2R.ArrayExp.model.T_AutoFeedbackSetting_CRUD;
import com.innolux.R2R.ArrayExp.model.T_EqGroup2EqID;
import com.innolux.R2R.ArrayExp.model.T_EqGroup2EqID_CRUD;
import com.innolux.R2R.ArrayExp.model.T_ExpRcpID2Name;
import com.innolux.R2R.ArrayExp.model.T_ExpRcpID2Name_CRUD;
import com.innolux.R2R.ArrayExp.model.Utility;
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
		ArrayExp a = new ArrayExp("C:\\R2RNikonTest\\workspace\\", "C:\\R2RNikonTest\\ngFile\\");
	}
	
	public ArrayExp(String csvUpperFilePath, String ngFilePath) {
		MeasureFileReader fileService = new MeasureFileReader();
		 fileService.setFileHandler(this, csvUpperFilePath, ngFilePath);
		fileService.start();

	}

	@Override
	public void onFileData(MeasureFileDataBase csv) {
		// when receive a csv 
		ExpMeasGlass emGlass = ExpMeasGlass.csv2ExpMeasGlass(csv);

		//boolean isDataError = checkIsDataVaild(emGlass);
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
//		feedbackFile = null;
//		if(emGlass.expSupplier.equal("Nikon")){
//			feedbackFile = createNikonFeedbackFile(emGlass);
//			sendFdbkFile2Canon(feedbackFile);
//		}else if(emGlass.expSupplier.equal("Canon")){
//			feedbackFile = createCanonFeedbackFile(emGlass);
//			sendFdbkFile2Nanon(feedbackFile);
//		}
		// save feedback history to DB
	}	

	
	
}
