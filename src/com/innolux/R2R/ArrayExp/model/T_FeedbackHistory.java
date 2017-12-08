package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;

//p35
@Entity("Feedback_History") // table name
public class T_FeedbackHistory {
	@Column("Product")
	private String Product;

	@Column("Exp_ID")
	private String ExpID;

	@Column("Exp_Step_ID")
	private String ExpStepID;

	@Column("Exp_Rcp_ID")
	private String ExpRcpID;

	@Column("Exp_Rcp_Name")
	private String ExpRcpName;

	@Column("Feedback_Time")
	private long FeedbackTime;

	@Column("Feedback_User_ID")
	private String FeedbackUserID;

	@Column("Mode")
	private String Mode;

	public String getProduct() {
		return Product;
	}

	public void setProduct(String product) {
		Product = product;
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

	public String getExpRcpID() {
		return ExpRcpID;
	}

	public void setExpRcpID(String expRcpID) {
		ExpRcpID = expRcpID;
	}

	public String getExpRcpName() {
		return ExpRcpName;
	}

	public void setExpRcpName(String expRcpName) {
		ExpRcpName = expRcpName;
	}

	public long getFeedbackTime() {
		return FeedbackTime;
	}

	public void setFeedbackTime(long feedbackTime) {
		FeedbackTime = feedbackTime;
	}

	public String getFeedbackUserID() {
		return FeedbackUserID;
	}

	public void setFeedbackUserID(String feedbackUserID) {
		FeedbackUserID = feedbackUserID;
	}

	public String getMode() {
		return Mode;
	}

	public void setMode(String mode) {
		Mode = mode;
	}


	@Override 
	public String toString() {
	return 
		" Product " + Product +
		" ExpID " + ExpID +
		" ExpStepID " + ExpStepID +
		" ExpRcpID " + ExpRcpID +
		" ExpRcpName " + ExpRcpName +
		" FeedbackTime " + FeedbackTime +
		" FeedbackUserID " + FeedbackUserID +
		" Mode " + Mode;
	}
}
