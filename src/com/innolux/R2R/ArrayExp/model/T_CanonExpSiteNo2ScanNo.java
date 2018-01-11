package com.innolux.R2R.ArrayExp.model;

import javax.sound.midi.Synthesizer;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;
import com.innolux.services.ObjectAnalyzer;

// P33
@Entity("ArrayExp_Canon_SiteNo2ScanNo") // table name
public class T_CanonExpSiteNo2ScanNo{
	@Id("Primarykey")
	private String Primarykey;

	@Column("Product")
	private String Product;

	@Column("ExpStepID")
	private String ExpStepID;

	@Column("SiteNoStart")
	private String SiteNoStart;

	@Column("SiteNoEnd")
	private String SiteNoEnd;
	
	@Column("ScanNo")
	private String ScanNo;
	
	public int findScanNo(int siteNo) {
		int i_siteNoStart = Integer.parseInt(SiteNoStart);
		int i_siteNoEnd = Integer.parseInt(SiteNoEnd);
		if (i_siteNoStart <= siteNo && siteNo <= i_siteNoEnd) {
			return Integer.parseInt(ScanNo);
		}
		return -1;
	}
	
	
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

	

	public String getSiteNoStart() {
		return SiteNoStart;
	}


	public void setSiteNoStart(String siteNoStart) {
		SiteNoStart = siteNoStart;
	}


	public String getSiteNoEnd() {
		return SiteNoEnd;
	}


	public void setSiteNoEnd(String siteNoEnd) {
		SiteNoEnd = siteNoEnd;
	}


	public String getScanNo() {
		return ScanNo;
	}

	public void setScanNo(String scanNo) {
		ScanNo = scanNo;
	}
	
	@Override 
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
}
