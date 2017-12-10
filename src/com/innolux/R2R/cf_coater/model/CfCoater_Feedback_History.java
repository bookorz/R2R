package com.innolux.R2R.cf_coater.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.services.ObjectAnalyzer;
@Entity("CfCoater_Feedback_History") // 表名
public class CfCoater_Feedback_History {
	@Column("EqpId")
	private String EqpId;
	@Column("SubEqpId")
	private String SubEqpId;
	@Column("PPID")
	private String PPID;
	@Column("User")
	private String User;
	@Column("Feedback_Value")
	private String Feedback_Value;
	@Column("FeedBack_Time")
	private long FeedBack_Time;
	public String getEqpId() {
		return EqpId;
	}
	public void setEqpId(String eqpId) {
		EqpId = eqpId;
	}
	public String getSubEqpId() {
		return SubEqpId;
	}
	public void setSubEqpId(String subEqpId) {
		SubEqpId = subEqpId;
	}
	public String getPPID() {
		return PPID;
	}
	public void setPPID(String pPID) {
		PPID = pPID;
	}
	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	public String getFeedback_Value() {
		return Feedback_Value;
	}
	public void setFeedback_Value(String feedback_Value) {
		Feedback_Value = feedback_Value;
	}
	public long getFeedBack_Time() {
		return FeedBack_Time;
	}
	public void setFeedBack_Time(long feedBack_Time) {
		FeedBack_Time = feedBack_Time;
	}
	@Override
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
	
}
