package com.innolux.R2R.cf_coater.model;

import java.util.Date;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.services.ObjectAnalyzer;
@Entity("CfCoater_Feedback_History") // 表名
public class CfCoater_Feedback_History {

	@Column("SubEqpId")
	private String SubEqpId;
	@Column("PPID")
	private String PPID;
	@Column("User_Id")
	private String User_Id;
	@Column("Feedback_Value")
	private String Feedback_Value;
	@Column("FeedBack_Time")
	private Date FeedBack_Time;
	
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
	public String getUser_Id() {
		return User_Id;
	}
	public void setUser_Id(String user_Id) {
		User_Id = user_Id;
	}
	public String getFeedback_Value() {
		return Feedback_Value;
	}
	public void setFeedback_Value(String feedback_Value) {
		Feedback_Value = feedback_Value;
	}
	public Date getFeedBack_Time() {
		return FeedBack_Time;
	}
	public void setFeedBack_Time(Date feedBack_Time) {
		FeedBack_Time = feedBack_Time;
	}
	@Override
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
	
}
