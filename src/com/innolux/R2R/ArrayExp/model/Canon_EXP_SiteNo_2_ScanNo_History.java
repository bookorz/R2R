package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;

// P38
@Entity("Canon_EXP_SiteNo_2_ScanNo_Setting_History") // table name
public class Canon_EXP_SiteNo_2_ScanNo_History {
	@Column("Product")
	private String Product;

	@Column("Exp_StepID")
	private String Exp_StepID;

	@Column("SiteNo")
	private int SiteNo;

	@Column("ScanNo_")
	private int ScanNo_;

	@Column("Update_Time")
	private long Update_Time;

	@Column("Update_UserID")
	private String Update_UserID;

	public String getProduct() {
		return Product;
	}

	public void setProduct(String product) {
		Product = product;
	}

	public String getExp_StepID() {
		return Exp_StepID;
	}

	public void setExp_StepID(String exp_StepID) {
		Exp_StepID = exp_StepID;
	}

	public int getSiteNo() {
		return SiteNo;
	}

	public void setSiteNo(int siteNo) {
		SiteNo = siteNo;
	}

	public int getScanNo_() {
		return ScanNo_;
	}

	public void setScanNo_(int scanNo_) {
		ScanNo_ = scanNo_;
	}

	public long getUpdate_Time() {
		return Update_Time;
	}

	public void setUpdate_Time(long update_Time) {
		Update_Time = update_Time;
	}

	public String getUpdate_UserID() {
		return Update_UserID;
	}

	public void setUpdate_UserID(String update_UserID) {
		Update_UserID = update_UserID;
	}

	@Override
	public String toString() {
		return "Canon_EXP_SiteNo_2_ScanNo_History [Product=" + Product + ", Exp_StepID=" + Exp_StepID + ", SiteNo="
				+ SiteNo + ", ScanNo_=" + ScanNo_ + ", Update_Time=" + Update_Time + ", Update_UserID=" + Update_UserID
				+ "]";
	}


}
