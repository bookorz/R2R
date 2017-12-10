package com.innolux.R2R.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.services.ObjectAnalyzer;

@Entity("Log_History") // 表名
public class LogHistory {
	
	@Column("Time")
	private long Time;

	@Column("Level")
	private String Level;

	@Column("LogString")
	private String LogString;
	
	
	public long getTime() {
		return Time;
	}

	public void setTime(long time) {
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
