package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;

//P37
@Entity("Exp_RcpID_2_Name_History") // table name
public class Exp_RcpID_2_Name_History {
	@Column("Product")
	private String Product;
	
	@Column("Exp_Step_ID")
	private String Exp_Step_ID;
	
	@Column("Exp_Rcp_ID")
	private String Exp_Rcp_ID;
	
	@Column("Exp_Rcp_Name")
	private String Exp_Rcp_Name;
	
	@Column("Update_Time")
	private long Update_Time;
	
	@Column("Update_User_ID")
	private String Update_User_ID;

	public String getProduct() {
		return Product;
	}

	public void setProduct(String product) {
		Product = product;
	}

	public String getExp_Step_ID() {
		return Exp_Step_ID;
	}

	public void setExp_Step_ID(String exp_Step_ID) {
		Exp_Step_ID = exp_Step_ID;
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

	public long getUpdate_Time() {
		return Update_Time;
	}

	public void setUpdate_Time(long update_Time) {
		Update_Time = update_Time;
	}

	public String getUpdate_User_ID() {
		return Update_User_ID;
	}

	public void setUpdate_User_ID(String update_User_ID) {
		Update_User_ID = update_User_ID;
	}

	@Override
	public String toString() {
		return "Exp_RcpID_2_Name_History [Product=" + Product + ", Exp_Step_ID=" + Exp_Step_ID + ", Exp_Rcp_ID="
				+ Exp_Rcp_ID + ", Exp_Rcp_Name=" + Exp_Rcp_Name + ", Update_Time=" + Update_Time + ", Update_User_ID="
				+ Update_User_ID + "]";
	}
	
	
}
