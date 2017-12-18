package com.innolux.R2R.ArrayExp.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;

import com.innolux.R2R.model.LogHistory;
import com.innolux.R2R.model.LogHistory_CRUD;

public class Utility {
	public static final boolean DEBUG = false;
	private static Logger logger = Logger.getLogger(Utility.class);
	
	public static void main(String [] argv) {
		
		try {
			String cmdStr = "cmd.exe /c echo hello" ;
			Process proc = Runtime.getRuntime().exec(cmdStr);
			proc.waitFor();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
			}

			// read any errors from the attempted command
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
			}

			

		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
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
	
	public static void saveToLogHistoryDB(String errLevel, String logString) {
		LogHistory alogHisty = new LogHistory();
		alogHisty.setR2R_Name("ArrayExp");
		alogHisty.setTime(Calendar.getInstance().getTime().getTime());
		alogHisty.setLevel(errLevel);
		alogHisty.setLogString(logString);
		LogHistory_CRUD.create(alogHisty);
		return;
	}
	
}
