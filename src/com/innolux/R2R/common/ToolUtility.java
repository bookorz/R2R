package com.innolux.R2R.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ToolUtility {
	public String StackTrace2String(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString(); // stack trace as a string
	}
	public String getTxnID(String eqpID,String functionID) {       
        StringBuffer sb = new StringBuffer();
        sb.append((int)(Math.random()*100)).append("_").append(eqpID).append("_").append(functionID).append("_");
        sb.append(new SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().getTime()));
        return sb.toString();
    }
}
