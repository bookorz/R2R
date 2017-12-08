package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

//p28
@Entity("Auto_Feedback_Setting") // table name
public class T_AutoFeedbackSetting{
	@Id("Primarykey")
	private String Primarykey;

	@Column("Active_Flag")
	private String ActiveFlag;

	@Column("Eqp_ID")
	private String EqpID;

	@Column("Exp_Step_Id")
	private String ExpStepId;

	@Column("Mea_Step_Id")
	private String MeaStepId;

	@Column("Exp_Rcp_Id")
	private String ExpRcpId;

	@Column("Exp_Rcp_Name")
	private String ExpRcpName;
	
	@Column("Mea_Rcp")
	private String MeaRcp;

	@Column("Feedback_Mode")
	private String FeedbackMode;

	@Column("U_Upper_Limit")
	private double UUpperLimit;

	@Column("L_Upper_Limit")
	private double LUpperLimit;

	@Column("U_Lower_Limit")
	private double ULowerLimit;

	@Column("L_Lower_Limit")
	private double LLowerLimit;

	@Column("Ratio")
	private double Ratio;

	@Column("Sigma")
	private double Sigma;

	@Column("Population_Size")
	private int PopulationSize;

	@Column("Sample_Size")
	private int SampleSize;

	@Column("Expire_time")
	private long Expiretime;

	public String getPrimarykey() {
		return Primarykey;
	}

	public void setPrimarykey(String primarykey) {
		Primarykey = primarykey;
	}

	public String getActiveFlag() {
		return ActiveFlag;
	}

	public void setActiveFlag(String activeFlag) {
		ActiveFlag = activeFlag;
	}

	public String getEqpID() {
		return EqpID;
	}

	public void setEqpID(String eqpID) {
		EqpID = eqpID;
	}

	public String getExpStepId() {
		return ExpStepId;
	}

	public void setExpStepId(String expStepId) {
		ExpStepId = expStepId;
	}

	public String getMeaStepId() {
		return MeaStepId;
	}

	public void setMeaStepId(String meaStepId) {
		MeaStepId = meaStepId;
	}

	public String getExpRcpId() {
		return ExpRcpId;
	}

	public void setExpRcpId(String expRcpId) {
		ExpRcpId = expRcpId;
	}

	public String getExpRcpName() {
		return ExpRcpName;
	}

	public void setExpRcpName(String expRcpName) {
		ExpRcpName = expRcpName;
	}

	public String getMeaRcp() {
		return MeaRcp;
	}

	public void setMeaRcp(String meaRcp) {
		MeaRcp = meaRcp;
	}

	public String getFeedbackMode() {
		return FeedbackMode;
	}

	public void setFeedbackMode(String feedbackMode) {
		FeedbackMode = feedbackMode;
	}

	public double getUUpperLimit() {
		return UUpperLimit;
	}

	public void setUUpperLimit(double uUpperLimit) {
		UUpperLimit = uUpperLimit;
	}

	public double getLUpperLimit() {
		return LUpperLimit;
	}

	public void setLUpperLimit(double lUpperLimit) {
		LUpperLimit = lUpperLimit;
	}

	public double getULowerLimit() {
		return ULowerLimit;
	}

	public void setULowerLimit(double uLowerLimit) {
		ULowerLimit = uLowerLimit;
	}

	public double getLLowerLimit() {
		return LLowerLimit;
	}

	public void setLLowerLimit(double lLowerLimit) {
		LLowerLimit = lLowerLimit;
	}

	public double getRatio() {
		return Ratio;
	}

	public void setRatio(double ratio) {
		Ratio = ratio;
	}

	public double getSigma() {
		return Sigma;
	}

	public void setSigma(double sigma) {
		Sigma = sigma;
	}

	public int getPopulationSize() {
		return PopulationSize;
	}

	public void setPopulationSize(int populationSize) {
		PopulationSize = populationSize;
	}

	public int getSampleSize() {
		return SampleSize;
	}

	public void setSampleSize(int sampleSize) {
		SampleSize = sampleSize;
	}

	public long getExpiretime() {
		return Expiretime;
	}

	public void setExpiretime(long expiretime) {
		Expiretime = expiretime;
	}

	@Override 
	public String toString() {
		return " Primarykey " + Primarykey +
			" ActiveFlag " + ActiveFlag +
			" EqpID " + EqpID +
			" ExpStepId " + ExpStepId +
			" MeaStepId " + MeaStepId +
			" ExpRcpId " + ExpRcpId +
			" ExpRcpName " + ExpRcpName +
			" MeaRcp " + MeaRcp +
			" FeedbackMode " + FeedbackMode +
			" UUpperLimit " + UUpperLimit +
			" LUpperLimit " + LUpperLimit +
			" ULowerLimit " + ULowerLimit +
			" LLowerLimit " + LLowerLimit +
			" Ratio " + Ratio +
			" Sigma " + Sigma +
			" PopulationSize " + PopulationSize +
			" SampleSize " + SampleSize +
			" Expiretime " + Expiretime;
	}
	
}
