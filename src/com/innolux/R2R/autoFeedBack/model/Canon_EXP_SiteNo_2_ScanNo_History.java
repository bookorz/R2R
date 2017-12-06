package com.innolux.R2R.autoFeedBack.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;

// P38
@Entity("Canon_EXP_SiteNo_2_ScanNo_Setting_History") // table name
public class Canon_EXP_SiteNo_2_ScanNo_History {
	
	@Column("Product")
	private String Product;
	
	@Column("Exp_Step_Id")
	private String Exp_Step_Id;
	
	@Column("Exp_Rcp_Id")
	private String Exp_Rcp_Id;
	
	@Column("Exp_Rcp_Name")
	private String Exp_Rcp_Name;
	
	@Column("Update_Time")
	private long Update_Time;
	
	@Column("Update_User_Id")
	private String Update_User_Id;
	
	public String getProduct() {
		return Product;
	}
	public void setProduct(String product) {
		Product = product;
	}
	public String getExp_Step_Id() {
		return Exp_Step_Id;
	}
	public void setExp_Step_Id(String exp_Step_Id) {
		Exp_Step_Id = exp_Step_Id;
	}
	public String getExp_Rcp_Id() {
		return Exp_Rcp_Id;
	}
	public void setExp_Rcp_Id(String exp_Rcp_Id) {
		Exp_Rcp_Id = exp_Rcp_Id;
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
	public String getUpdate_User_Id() {
		return Update_User_Id;
	}
	public void setUpdate_User_Id(String update_User_Id) {
		Update_User_Id = update_User_Id;
	}
	
	@Override
	public String toString() {
		return "Exp_RcpID_2_Name [Product=" + Product + ", Exp_Step_Id=" + Exp_Step_Id + ", Exp_Rcp_Id=" + Exp_Rcp_Id
				+ ", Exp_Rcp_Name=" + Exp_Rcp_Name + ", Update_Time=" + Update_Time + ", Update_User_Id="
				+ Update_User_Id + "]";
	}
}
