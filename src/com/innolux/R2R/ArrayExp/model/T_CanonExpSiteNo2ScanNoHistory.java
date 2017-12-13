package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;

// P38
@Entity("ArrayExp_Canon_EXP_SiteNo_2_ScanNo_Setting_History") // table name
public class T_CanonExpSiteNo2ScanNoHistory {
	@Column("Product")
	private String Product;

	@Column("Exp_StepID")
	private String ExpStepID;

	@Column("SiteNo")
	private int SiteNo;

	@Column("ScanNo_")
	private int ScanNo;

	@Column("Update_Time")
	private long UpdateTime;

	@Column("Update_UserID")
	private String UpdateUserID;

	public String getProduct() {
		return Product;
	}

	public void setProduct(String product) {
		Product = product;
	}

	public String getExpStepID() {
		return ExpStepID;
	}

	public void setExpStepID(String expStepID) {
		ExpStepID = expStepID;
	}

	public int getSiteNo() {
		return SiteNo;
	}

	public void setSiteNo(int siteNo) {
		SiteNo = siteNo;
	}

	public int getScanNo() {
		return ScanNo;
	}

	public void setScanNo(int scanNo) {
		ScanNo = scanNo;
	}

	public long getUpdateTime() {
		return UpdateTime;
	}

	public void setUpdateTime(long updateTime) {
		UpdateTime = updateTime;
	}

	public String getUpdateUserID() {
		return UpdateUserID;
	}

	public void setUpdateUserID(String updateUserID) {
		UpdateUserID = updateUserID;
	}
	
	
	@Override 
	public String toString() {
	return 
		" Product " + Product +
		" ExpStepID " + ExpStepID +
		" SiteNo " + SiteNo +
		" ScanNo " + ScanNo +
		" UpdateTime " + UpdateTime +
		" UpdateUserID " + UpdateUserID;
	}
}
