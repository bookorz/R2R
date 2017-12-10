package com.innolux.R2R.ArrayExp.model;

import org.apache.log4j.Logger;
import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.model.LogHistory;
import com.innolux.R2R.model.LogHistory_CRUD;

public class Utility {
	private static Logger logger = Logger.getLogger(Utility.class);
	public static void checkErrorAndLog(Object checkValue, String errStr, Object errorValue){
		if(checkValue.equals(errorValue)){
			logger.error(errStr);

			saveToLogHistoryDB(GlobleVar.ErrorType, errStr);
		}
		return;
	}
	
	public static void saveToLogHistoryDB(String errLevel, String logString) {
		LogHistory alogHisty = new LogHistory();
		alogHisty.setLevel(errLevel);
		alogHisty.setLogString(logString);
		alogHisty.setTime(System.currentTimeMillis());
		
		LogHistory_CRUD.create(alogHisty);
		return;
	}
	
}
