package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;
import com.innolux.services.ObjectAnalyzer;

@Entity("Last_Exp_Time") // 表名
public class T_LastExpTime {
	@Id("PrimaryKey")
	private String PrimaryKey;
	
	@Column("Exp_Id")
	private String ExpId;
	
	@Column("Rcp_Id")
	private String RcpId;
	
	@Column("Exp_Time")
	private String ExpTime;

	public String getPrimaryKey() {
		return PrimaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		PrimaryKey = primaryKey;
	}

	public String getExpId() {
		return ExpId;
	}

	public void setExpId(String expId) {
		ExpId = expId;
	}

	public String getRcpId() {
		return RcpId;
	}

	public void setRcpId(String rcpId) {
		RcpId = rcpId;
	}

	public String getExpTime() {
		return ExpTime;
	}

	public void setExpTime(String expTime) {
		ExpTime = expTime;
	}
	
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
}
