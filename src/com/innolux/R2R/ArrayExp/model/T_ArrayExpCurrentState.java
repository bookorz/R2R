package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

@Entity("ArrayExp_Current_State") // table name
public class T_ArrayExpCurrentState {
	@Id("Primarykey")
	private String Primarykey;

	@Column("Exp_ID")
	private String ExpID;

	@Column("Exp_Step_ID")
	private String ExpStepID;

	@Column("Mea_Step_ID")
	private String MeaStepID;

	@Column("Exp_Rcp_ID")
	private String ExpRcpID;

	@Column("Exp_Rcp_Name")
	private String ExpRcpName;

	@Column("Mea_Rcp")
	private String MeaRcp;

	@Column("Count")
	private int Count;

	public String getPrimarykey() {
		return Primarykey;
	}

	public void setPrimarykey(String primarykey) {
		Primarykey = primarykey;
	}

	public String getExpID() {
		return ExpID;
	}

	public void setExpID(String expID) {
		ExpID = expID;
	}

	public String getExpStepID() {
		return ExpStepID;
	}

	public void setExpStepID(String expStepID) {
		ExpStepID = expStepID;
	}

	public String getMeaStepID() {
		return MeaStepID;
	}

	public void setMeaStepID(String meaStepID) {
		MeaStepID = meaStepID;
	}

	public String getExpRcpID() {
		return ExpRcpID;
	}

	public void setExpRcpID(String expRcpID) {
		ExpRcpID = expRcpID;
	}

	public String getExpRcpName() {
		return ExpRcpName;
	}

	public void setExpRcpName(String expRcpName) {
		ExpRcpName = expRcpName;
	}

	public String getMeaRcp() {
		return MeaRcp;
	}

	public void setMeaRcp(String meaRcp) {
		MeaRcp = meaRcp;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}

	@Override 
	public String toString() {
		return " Primarykey " + Primarykey +
			" ExpID " + ExpID +
			" ExpStepID " + ExpStepID +
			" MeaStepID " + MeaStepID +
			" ExpRcpID " + ExpRcpID +
			" ExpRcpName " + ExpRcpName +
			" MeaRcp " + MeaRcp;
	}

}
