package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;
import com.innolux.services.ObjectAnalyzer;

@Entity("ArrayExp_Feedback_History") // table name
public class T_ArrayExpFeedbackHistory {
	@Id("PrimaryKey")
	private String PrimaryKey;

	@Column("Product")
	private String Product;

	@Column("Exp_ID")
	private String Exp_ID;

	@Column("Exp_Step_ID")
	private String Exp_Step_ID;

	@Column("Exp_Rcp_ID")
	private String Exp_Rcp_ID;

//	@Column("Exp_Rcp_Name")
//	private String Exp_Rcp_Name;

	@Column("Mea_Rcp_ID")
	private String Mea_Rcp_ID;

	@Column("Mea_Step_ID")
	private String Mea_Step_ID;

	@Column("Adc_Or_Fdc")
	private String Adc_Or_Fdc;

	@Column("Feedback_Time")
	private long Feedback_Time;

	@Column("Feedback_User_ID")
	private String Feedback_User_ID;

	@Column("Feedback_Mode")
	private String Feedback_Mode;

	@Column("Operation_Mode")
	private String Operation_Mode;

	public String getPrimaryKey() {
		return PrimaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		PrimaryKey = primaryKey;
	}

	public String getProduct() {
		return Product;
	}

	public void setProduct(String product) {
		Product = product;
	}

	public String getExp_ID() {
		return Exp_ID;
	}

	public void setExp_ID(String exp_ID) {
		Exp_ID = exp_ID;
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

	public String getMea_Rcp_ID() {
		return Mea_Rcp_ID;
	}

	public void setMea_Rcp_ID(String mea_Rcp_ID) {
		Mea_Rcp_ID = mea_Rcp_ID;
	}

	public String getMea_Step_ID() {
		return Mea_Step_ID;
	}

	public void setMea_Step_ID(String mea_Step_ID) {
		Mea_Step_ID = mea_Step_ID;
	}

	public String getAdc_Or_Fdc() {
		return Adc_Or_Fdc;
	}

	public void setAdc_Or_Fdc(String adc_Or_Fdc) {
		Adc_Or_Fdc = adc_Or_Fdc;
	}

	public long getFeedback_Time() {
		return Feedback_Time;
	}

	public void setFeedback_Time(long feedback_Time) {
		Feedback_Time = feedback_Time;
	}

	public String getFeedback_User_ID() {
		return Feedback_User_ID;
	}

	public void setFeedback_User_ID(String feedback_User_ID) {
		Feedback_User_ID = feedback_User_ID;
	}

	public String getFeedback_Mode() {
		return Feedback_Mode;
	}

	public void setFeedback_Mode(String feedback_Mode) {
		Feedback_Mode = feedback_Mode;
	}

	public String getOperation_Mode() {
		return Operation_Mode;
	}

	public void setOperation_Mode(String operation_Mode) {
		Operation_Mode = operation_Mode;
	}
	
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}

}
