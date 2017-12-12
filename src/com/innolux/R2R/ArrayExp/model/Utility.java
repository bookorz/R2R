package com.innolux.R2R.ArrayExp.model;

import java.util.Calendar;

import org.apache.log4j.Logger;
import com.innolux.R2R.model.LogHistory;
import com.innolux.R2R.model.LogHistory_CRUD;

public class Utility {
	public static final boolean DEBUG = true;
	
	private static Logger logger = Logger.getLogger(Utility.class);
	public static int checkErrorAndLog(Object checkValue, Object errorValue, String errLevel, String errStr){
		if (checkValue == null){
			logger.error(errStr);
			saveToLogHistoryDB(errLevel, errStr);
			return -1;
		}
		else if(checkValue.equals(errorValue)){
			logger.error(errStr);
			saveToLogHistoryDB(errLevel, errStr);
			return -1;
		}
		return 0;
	}
	
	public static void saveToLogHistoryDB(String errLevel, String logString) {
		LogHistory alogHisty = new LogHistory();
		alogHisty.setLevel(errLevel);
		alogHisty.setLogString(logString);
		alogHisty.setTime(Calendar.getInstance().getTime());
		
		LogHistory_CRUD.create(alogHisty);
		return;
	}
	
}
