package com.innolux.R2R.ArrayExp.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.model.LogHistory;
import com.innolux.R2R.model.LogHistory_CRUD;

public class Utility {
	public static final boolean DEBUG = false;
	private static Logger logger = Logger.getLogger(Utility.class);
	
	public static void main(String [] argv) {
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		sc.close();
		Date d1 = dateStr2Date(str);
		
		System.out.println(d1.getTime());
	}
	
	public static Date dateStr2Date(String dateStr) {
		SimpleDateFormat simDateFormat = new SimpleDateFormat();
		try {
			simDateFormat.applyPattern("yyyyMMdd HHmmssSSS");
			Date date = simDateFormat.parse(dateStr);
			return date;
		}catch(Exception e) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return null;
		}
	}
	
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
