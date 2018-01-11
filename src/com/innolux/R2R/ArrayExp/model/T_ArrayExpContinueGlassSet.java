package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;
import com.innolux.services.ObjectAnalyzer;

@Entity("ArrayExp_Continue_Glass_Set") // table name
public class T_ArrayExpContinueGlassSet {
	@Id("PrimaryKey")
	private String Primarykey;

	@Column("Product")
	private String productName;

	@Column("Exp_ID")
	private String expID;

	@Column("Exp_Rcp_ID")
	private String expRcpID;

	@Column("Mea_Step_ID")
	private String measStepID;

	@Column("Mea_Rcp_ID")
	private String measRcpID; 

	@Column("Adc_Or_Fdc")
	private String adcOrFdc;

	@Column("GlassID")
	private String glassID;

	@Column("Feedback_Mode")
	private String feedbackMode;
	
	@Column("Site_No_List")
	private String siteNoList;
	
	@Column("OL_01_LIST")
	private String ol01List;
	
	@Column("OL_02_LIST")
	private String ol02List;
	
	@Column("COORD_X_LIST")
	private String coordXList;
	
	@Column("COORD_Y_LIST")
	private String coordYList;
	
	@Column("Exposure_Time")
	private long exposureTime; // for ordering;

	public T_ArrayExpContinueGlassSet() {
	}
	
	public T_ArrayExpContinueGlassSet(ExpMeasGlass aGlass) {
		this.productName = aGlass.getProductName();
		this.expID = aGlass.getExpID();
		this.expRcpID = aGlass.getExpRcpID();
		this.measRcpID = aGlass.getMeasRcpID();
		this.measStepID = aGlass.getMeasStepID();
		this.adcOrFdc = aGlass.getAdcOrFdc();
		this.glassID = aGlass.getGlassID();
		this.feedbackMode = aGlass.getOlOrDol();
		this.ol01List = aGlass.getOl01ListStr();
		this.ol02List = aGlass.getOl02ListStr();
		this.coordXList = aGlass.getCoordXListStr();
		this.coordYList = aGlass.getCoordYListStr();
		this.exposureTime = aGlass.getExposureTime();
	}
	
	public String getPrimarykey() {
		return Primarykey;
	}

	public void setPrimarykey(String primarykey) {
		Primarykey = primarykey;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getExpID() {
		return expID;
	}

	public void setExpID(String expID) {
		this.expID = expID;
	}

	public String getExpRcpID() {
		return expRcpID;
	}

	public void setExpRcpID(String expRcpID) {
		this.expRcpID = expRcpID;
	}

	public String getMeasStepID() {
		return measStepID;
	}

	public void setMeasStepID(String measStepID) {
		this.measStepID = measStepID;
	}

	public String getMeasRcpID() {
		return measRcpID;
	}

	public void setMeasRcpID(String measRcpID) {
		this.measRcpID = measRcpID;
	}

	public String getAdcOrFdc() {
		return adcOrFdc;
	}

	public void setAdcOrFdc(String adcOrFdc) {
		this.adcOrFdc = adcOrFdc;
	}

	public String getGlassID() {
		return glassID;
	}

	public void setGlassID(String glassID) {
		this.glassID = glassID;
	}

	public String getFeedbackMode() {
		return feedbackMode;
	}

	public void setFeedbackMode(String feedbackMode) {
		this.feedbackMode = feedbackMode;
	}

	public String getSiteNoList() {
		return siteNoList;
	}

	public void setSiteNoList(String siteNoList) {
		this.siteNoList = siteNoList;
	}

	public String getOl01List() {
		return ol01List;
	}

	public void setOl01List(String ol01List) {
		this.ol01List = ol01List;
	}

	public String getOl02List() {
		return ol02List;
	}

	public void setOl02List(String ol02List) {
		this.ol02List = ol02List;
	}

	public String getCoordXList() {
		return coordXList;
	}

	public void setCoordXList(String coordXList) {
		this.coordXList = coordXList;
	}

	public String getCoordYList() {
		return coordYList;
	}

	public void setCoordYList(String coordYList) {
		this.coordYList = coordYList;
	}

	public long getExposureTime() {
		return exposureTime;
	}

	public void setExposureTime(long exposureTime) {
		this.exposureTime = exposureTime;
	}

	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
}
