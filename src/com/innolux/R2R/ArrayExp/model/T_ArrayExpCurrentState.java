package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;
import com.innolux.services.ObjectAnalyzer;

@Entity("ArrayExp_Current_State") // table name
public class T_ArrayExpCurrentState {
	@Id("Primarykey")
	private String Primarykey;
	
	@Column("Product_Name")
	private String productName;
	
	@Column("Exp_ID")
	private String ExpID;

	@Column("Exp_Step_ID")
	private String ExpStepID;

	@Column("Mea_Step_ID")
	private String MeaStepID;

	@Column("Exp_Rcp_ID")
	private String ExpRcpID;

	@Column("Mea_Rcp_ID")
	private String MeaRcpID;

	@Column("Adc_Or_Fdc")
	private int adcOrFdc;
	
	@Column("Count")
	private int Count;

	public T_ArrayExpCurrentState (ExpMeasGlass aGlass) {
		this.productName = aGlass.getProductName();
		this.ExpID = aGlass.getExpID();
		this.ExpStepID = aGlass.getExpStepID();
		this.MeaStepID = aGlass.getMeasStepID();
		this.ExpRcpID = aGlass.getExpRcpID();
		this.MeaRcpID = aGlass.getMeasRcpID();
		this.adcOrFdc = aGlass.getAdcOrFdc();
		this.Count = 0;
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
		return ExpID;
	}

	public void setExpID(String expID) {
		ExpID = expID;
	}

	public String getExpStepID() {
		return ExpStepID;
	}

	public void setExpStepID(String expStepID) {
		ExpStepID = expStepID;
	}

	public String getMeaStepID() {
		return MeaStepID;
	}

	public void setMeaStepID(String meaStepID) {
		MeaStepID = meaStepID;
	}

	public String getExpRcpID() {
		return ExpRcpID;
	}

	public void setExpRcpID(String expRcpID) {
		ExpRcpID = expRcpID;
	}

	public String getMeaRcpID() {
		return MeaRcpID;
	}

	public void setMeaRcpID(String meaRcpID) {
		MeaRcpID = meaRcpID;
	}

	public int getAdcOrFdc() {
		return adcOrFdc;
	}

	public void setAdcOrFdc(int adcOrFdc) {
		this.adcOrFdc = adcOrFdc;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}

	public String toString() {
		return ObjectAnalyzer.toString(this);
	}

}
