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
	private double OOS_LowLimit;
	@Column("OOC_UpLimit")
	private double OOC_UpLimit;
	@Column("OOC_LoWLimit")
	private double OOC_LowLimit;
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

	public double getOOS_LowLimit() {
		return OOS_LowLimit;
	}

	public void setOOS_LowLimit(double oOS_LowLimit) {
		OOS_LowLimit = oOS_LowLimit;
	}

	public double getOOC_UpLimit() {
		return OOC_UpLimit;
	}

	public void setOOC_UpLimit(double oOC_UpLimit) {
		OOC_UpLimit = oOC_UpLimit;
	}

	public double getOOC_LowLimit() {
		return OOC_LowLimit;
	}

	public void setOOC_LowLimit(double oOC_LowLimit) {
		OOC_LowLimit = oOC_LowLimit;
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
