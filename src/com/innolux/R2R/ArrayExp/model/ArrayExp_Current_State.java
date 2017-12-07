package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

@Entity("ArrayExp_Current_State") // table name
public class ArrayExp_Current_State {
	@Id("Primarykey")
	private String Primarykey;

	@Column("Exp_ID")
	private String Exp_ID;

	@Column("Exp_Step_ID")
	private String Exp_Step_ID;

	@Column("Mea_Step_ID")
	private String Mea_Step_ID;

	@Column("Exp_Rcp_ID")
	private String Exp_Rcp_ID;

	@Column("Exp_Rcp_Name")
	private String Exp_Rcp_Name;

	@Column("Mea_Rcp")
	private String Mea_Rcp;

	@Column("Count")
	private int Count;

	public String getPrimarykey() {
		return Primarykey;
	}

	public void setPrimarykey(String primarykey) {
		Primarykey = primarykey;
	}

	public String getExp_ID() {
		return Exp_ID;
	}

	public void setExp_ID(String exp_ID) {
		Exp_ID = exp_ID;
	}

	public String getExp_Step_ID() {
		return Exp_Step_ID;
	}

	public void setExp_Step_ID(String exp_Step_ID) {
		Exp_Step_ID = exp_Step_ID;
	}

	public String getMea_Step_ID() {
		return Mea_Step_ID;
	}

	public void setMea_Step_ID(String mea_Step_ID) {
		Mea_Step_ID = mea_Step_ID;
	}

	public String getExp_Rcp_ID() {
		return Exp_Rcp_ID;
	}

	public void setExp_Rcp_ID(String exp_Rcp_ID) {
		Exp_Rcp_ID = exp_Rcp_ID;
	}

	public String getExp_Rcp_Name() {
		return Exp_Rcp_Name;
	}

	public void setExp_Rcp_Name(String exp_Rcp_Name) {
		Exp_Rcp_Name = exp_Rcp_Name;
	}

	public String getMea_Rcp() {
		return Mea_Rcp;
	}

	public void setMea_Rcp(String mea_Rcp) {
		Mea_Rcp = mea_Rcp;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}

	@Override
	public String toString() {
		return "ArrayExp_Current_State [Primarykey=" + Primarykey + ", Exp_ID=" + Exp_ID + ", Exp_Step_ID="
				+ Exp_Step_ID + ", Mea_Step_ID=" + Mea_Step_ID + ", Exp_Rcp_ID=" + Exp_Rcp_ID + ", Exp_Rcp_Name="
				+ Exp_Rcp_Name + ", Mea_Rcp=" + Mea_Rcp + ", Count=" + Count + "]";
	}


}
