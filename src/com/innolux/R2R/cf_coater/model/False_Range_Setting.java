package com.innolux.R2R.cf_coater.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;
import com.innolux.services.ObjectAnalyzer;

@Entity("CfCoater_False_Range_Setting") // 表名
public class False_Range_Setting {
	@Id("PPID")
	private String PPID;
	@Column("Spec")
	private double Spec;
	@Column("OOS_UpLimit")
	private double OOS_UpLimit;
	@Column("OOS_LoWLimit")
	private double OOS_LoWLimit;
	@Column("Filter_UpLimit")
	private double Filter_UpLimit;
	@Column("Filter_LowLimit")
	private double Filter_LowLimit;
	
	public String getPPID() {
		return PPID;
	}

	public void setPPID(String pPID) {
		PPID = pPID;
	}

	public double getSpec() {
		return Spec;
	}

	public void setSpec(double spec) {
		Spec = spec;
	}

	public double getOOS_UpLimit() {
		return OOS_UpLimit;
	}

	public void setOOS_UpLimit(double oOS_UpLimit) {
		OOS_UpLimit = oOS_UpLimit;
	}

	public double getOOS_LoWLimit() {
		return OOS_LoWLimit;
	}

	public void setOOS_LoWLimit(double oOS_LoWLimit) {
		OOS_LoWLimit = oOS_LoWLimit;
	}

	public double getFilter_UpLimit() {
		return Filter_UpLimit;
	}

	public void setFilter_UpLimit(double filter_UpLimit) {
		Filter_UpLimit = filter_UpLimit;
	}

	public double getFilter_LowLimit() {
		return Filter_LowLimit;
	}

	public void setFilter_LowLimit(double filter_LowLimit) {
		Filter_LowLimit = filter_LowLimit;
	}

	@Override
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
	
}
