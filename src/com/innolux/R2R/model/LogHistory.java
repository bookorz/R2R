package com.innolux.R2R.model;

import java.util.Date;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.services.ObjectAnalyzer;

@Entity("Log_History") // 表名
public class LogHistory {
	
	@Column("R2R_Name")
	private String R2R_Name;
	
	@Column("TimeStamp")
	private String Time;

	@Column("LogLevel")
	private String Level;

	@Column("LogString")
	private String LogString;
	
	public String getR2R_Name() {
		return R2R_Name;
	}

	public void setR2R_Name(String r2r_Name) {
		R2R_Name = r2r_Name;
	}

	public String getTime() {
		return Time;
	}
	
	public void setTime(String time) {
		Time = time;
	}

	public String getLevel() {
		return Level;
	}

	public void setLevel(String level) {
		Level = level;
	}

	public String getLogString() {
		return LogString;
	}

	public void setLogString(String logString) {
		LogString = logString;
	}

	@Override
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
}
