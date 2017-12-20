package com.innolux.R2R.cf_coater.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;
import com.innolux.services.ObjectAnalyzer;

@Entity("CfCoater_point_Setting") // 表名
public class Measure_point_Setting {
	@Id("PPID")
	private String PPID;
	@Column("Start_Col")
	private String Start_Col;
	@Column("Mid_Col")
	private String Mid_Col;
	@Column("End_COl")
	private String End_COl;

	public String getPPID() {
		return PPID;
	}

	public void setPPID(String pPID) {
		PPID = pPID;
	}

	public String getStart_Col() {
		return Start_Col;
	}

	public void setStart_Col(String start_Col) {
		Start_Col = start_Col;
	}

	public String getMid_Col() {
		return Mid_Col;
	}

	public void setMid_Col(String mid_Col) {
		Mid_Col = mid_Col;
	}

	public String getEnd_COl() {
		return End_COl;
	}

	public void setEnd_COl(String end_COl) {
		End_COl = end_COl;
	}

	@Override
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
}
