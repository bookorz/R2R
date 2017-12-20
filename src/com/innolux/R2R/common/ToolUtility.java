package com.innolux.R2R.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.log4j.Logger;

public class ToolUtility {
	private static Logger logger = Logger.getLogger(ToolUtility.class);

	public static String StackTrace2String(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString(); // stack trace as a string
	}

	public static String getTxnID(String eqpID, String functionID) {
		StringBuffer sb = new StringBuffer();
		sb.append((int) (Math.random() * 100)).append("_").append(eqpID).append("_").append(functionID).append("_");
		sb.append(new SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().getTime()));
		return sb.toString();
	}
	
	public static String demical2Hex(double value, double rate , String R2R_ID) {
		
		String result = "";
		int intVal = 0;
		value = value / rate;
		if (value >= 0) {
			intVal = (int) Math.floor(value);
		} else {
			intVal = (int) Math.ceil(value);
		}
		// result = Integer.valueOf(String.valueOf(intVal), 16).toString();

		result = Integer.toHexString(intVal & 0xffff);
		if (result.length() <= 4) {
			while (result.length() < 4) {
				result = "0" + result;
			}
			logger.debug("Run to run ID:" + R2R_ID + " demical2Hex value:" + value + " rate:" + rate + " result:"
					+ result);
		} else {
			while (result.length() < 8) {
				result = "0" + result;
			}
			logger.error("Run to run ID:" + R2R_ID + " demical2Hex return nothing value:" + value + " rate:"
					+ rate + " result:" + result);
			result = "";
		}

		return result;
		
	}

	
}
