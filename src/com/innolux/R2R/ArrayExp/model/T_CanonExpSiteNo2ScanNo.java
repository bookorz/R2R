package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

// P33
@Entity("Canon_Exp_SiteNo_2_ScanNo") // table name
public class T_CanonExpSiteNo2ScanNo{
	@Id("Primarykey")
	private String Primarykey;

	@Column("Product")
	private String Product;

	@Column("Exp_Step_ID")
	private String ExpStepID;

	@Column("SiteNo")
	private String SiteNo;

	@Column("ScanNo")
	private String ScanNo;

	public String getPrimarykey() {
		return Primarykey;
	}

	public void setPrimarykey(String primarykey) {
		Primarykey = primarykey;
	}

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

	public String getSiteNo() {
		return SiteNo;
	}

	public void setSiteNo(String siteNo) {
		SiteNo = siteNo;
	}

	public String getScanNo() {
		return ScanNo;
	}

	public void setScanNo(String scanNo) {
		ScanNo = scanNo;
	}
	
	
	@Override 
	public String toString() {
		return 
		" Primarykey " + Primarykey +
		" Product " + Product +
		" ExpStepID " + ExpStepID +
		" SiteNo " + SiteNo +
		" ScanNo " + ScanNo;
	}
}
