package com.innolux.R2R.cf_coater.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;
import com.innolux.services.ObjectAnalyzer;
@Entity("CfCoater_point_Setting") // 表名
public class Measure_point_Setting {
	@Id("PPID")
	private String PPID;
	@Column("Engage_Col")
	private String Engage_Col;
	@Column("Plunge_Col")
	private String Plunge_Col;
	@Column("Retract_COl")
	private String Retract_COl;
	
	
	public String getPPID() {
		return PPID;
	}
	public void setPPID(String pPID) {
		PPID = pPID;
	}
	public String getEngage_Col() {
		return Engage_Col;
	}
	public void setEngage_Col(String engage_Col) {
		Engage_Col = engage_Col;
	}
	public String getPlunge_Col() {
		return Plunge_Col;
	}
	public void setPlunge_Col(String plunge_Col) {
		Plunge_Col = plunge_Col;
	}
	public String getRetract_COl() {
		return Retract_COl;
	}
	public void setRetract_COl(String retract_COl) {
		Retract_COl = retract_COl;
	}
	@Override
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
}
