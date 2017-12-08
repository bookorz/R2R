package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;

//p35
@Entity("Feedback_History") // table name
public class Feedback_History {
	@Column("Product")
	private String Product;

	@Column("Exp_ID")
	private String Exp_ID;

	@Column("Exp_Step_ID")
	private String Exp_Step_ID;

	@Column("Exp_Rcp_ID")
	private String Exp_Rcp_ID;

	@Column("Exp_Rcp_Name")
	private String Exp_Rcp_Name;

	@Column("Feedback_Time")
	private long Feedback_Time;

	@Column("Feedback_User_ID")
	private String Feedback_User_ID;

	@Column("Mode")
	private String Mode;

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

	public String getExp_Rcp_Name() {
		return Exp_Rcp_Name;
	}

	public void setExp_Rcp_Name(String exp_Rcp_Name) {
		Exp_Rcp_Name = exp_Rcp_Name;
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

	public String getMode() {
		return Mode;
	}

	public void setMode(String mode) {
		Mode = mode;
	}

	@Override
	public String toString() {
		return "Feedback_History [Product=" + Product + ", Exp_ID=" + Exp_ID + ", Exp_Step_ID=" + Exp_Step_ID
				+ ", Exp_Rcp_ID=" + Exp_Rcp_ID + ", Exp_Rcp_Name=" + Exp_Rcp_Name + ", Feedback_Time=" + Feedback_Time
				+ ", Feedback_User_ID=" + Feedback_User_ID + ", Mode=" + Mode + "]";
	}


}
