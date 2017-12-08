package com.innolux.R2R.ArrayExp.model;

import java.util.ArrayList;
import java.util.List;

/* exposure and measure machine glass class
 * 
 */
public class ExpMeasGlass {
	String productName; // mandatory
	String expID; // mandatory
	String expRcpID; // mandatory
	String measStepID; // mandatory
	String measRcpID; // mandatory
	int adcOrFdc; // mandatory

	String glassID;
	
	String expSupplier; // Nikon or Canon
	String expStepID; 
	String expRcpName;
	
	public static final int OL = 1;
	public static final int DOL = 2; 
	public static final int ADC = 1; 
	public static final int FDC = 2; 
	
	int olOrDol;
	
	Vector2D minOL;
	Vector2D maxOL;
	Vector2D minDOL;
	Vector2D maxDOL;

	long exposueTime;
	long trackInTime;

	double ratio;

	List<Vector2D> measPointList;
	
	// 240point ArrayList:  average, sigma
	// method: 座標轉換

	public ExpMeasGlass(){
		this.measPointList = new ArrayList<Vector2D>();
		this.adcOrFdc = 0;
	}

	public String getGlassID() {
		return glassID;
	}

	public void setGlassID(String glassID) {
		this.glassID = glassID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getExpSupplier() {
		return expSupplier;
	}

	public void setExpSupplier(String expSupplier) {
		this.expSupplier = expSupplier;
	}

	public String getExpID() {
		return expID;
	}

	public void setExpID(String expID) {
		this.expID = expID;
	}
	
	public String getExpStepID() {
		return expStepID;
	}

	public void setExpStepID(String expStepID) {
		this.expStepID = expStepID;
	}
	
	public String getExpRcpID() {
		return expRcpID;
	}

	public void setExpRcpID(String expRcpID) {
		this.expRcpID = expRcpID;
	}

	public String getExpRcpName() {
		return expRcpName;
	}

	public void setExpRcpName(String expRcpName) {
		this.expRcpName = expRcpName;
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

	public int getOlOrDol() {
		return olOrDol;
	}

	public void setOlOrDol(int olOrDol) {
		this.olOrDol = olOrDol;
	}

	public int getAdcOrFdc() {
		return this.adcOrFdc;
	}

	public void setAdcOrFdc(int adcOrFdc) {
		this.adcOrFdc = adcOrFdc;
	}

	public Vector2D getMinOL() {
		return minOL;
	}

	public void setMinOL(Vector2D minOL) {
		this.minOL = minOL;
	}

	public Vector2D getMaxOL() {
		return maxOL;
	}

	public void setMaxOL(Vector2D maxOL) {
		this.maxOL = maxOL;
	}

	public Vector2D getMinDOL() {
		return minDOL;
	}

	public void setMinDOL(Vector2D minDOL) {
		this.minDOL = minDOL;
	}

	public Vector2D getMaxDOL() {
		return maxDOL;
	}

	public void setMaxDOL(Vector2D maxDOL) {
		this.maxDOL = maxDOL;
	}

	public long getExposueTime() {
		return exposueTime;
	}

	public void setExposueTime(long exposueTime) {
		this.exposueTime = exposueTime;
	}

	public long getTrackInTime() {
		return trackInTime;
	}

	public void setTrackInTime(long trackInTime) {
		this.trackInTime = trackInTime;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public List<Vector2D> getMeasPointList() {
		return measPointList;
	}

	public void setMeasPointList(List<Vector2D> measPointList) {
		this.measPointList = measPointList;
	}

	
	
	

}
