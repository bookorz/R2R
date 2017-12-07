package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

// P33
@Entity("Canon_Exp_SiteNo_2_ScanNo") // table name
public class Canon_Exp_SiteNo_2_ScanNo {
	@Id("Primarykey")
	private String Primarykey;

	@Column("Product")
	private String Product;

	@Column("Exp_Step_ID")
	private String Exp_Step_ID;

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

	public String getExp_Step_ID() {
		return Exp_Step_ID;
	}

	public void setExp_Step_ID(String exp_Step_ID) {
		Exp_Step_ID = exp_Step_ID;
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
		return "Canon_Exp_SiteNo_2_ScanNo [Primarykey=" + Primarykey + ", Product=" + Product + ", Exp_Step_ID="
				+ Exp_Step_ID + ", SiteNo=" + SiteNo + ", ScanNo=" + ScanNo + "]";
	}



}
