package com.innolux.R2R.ArrayExp.model;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;
import com.innolux.services.ObjectAnalyzer;

//p28
@Entity("ArrayExp_Auto_Feedback_Setting") // table name
public class T_AutoFeedbackSetting{
	@Id("Primarykey")
	private String Primarykey;

	@Column("Active_Flag")
	private String ActiveFlag;

	@Column("Exp_ID")
	private String ExpId;

	@Column("Exp_Step_Id")
	private String ExpStepId;

	@Column("Mea_Step_Id")
	private String MeaStepId;

	@Column("Exp_Rcp_Id")
	private String ExpRcpId;

	@Column("Mea_Rcp_Id")
	private String MeaRcpId;

	@Column("Adc_Or_Fdc")
	private String adcOrFdc;
	
	@Column("Feedback_Mode")
	private String FeedbackMode;

	@Column("OL_U_Upper_Limit")
	private double Ol_U_UpperLimit;

	@Column("OL_L_Upper_Limit")
	private double Ol_L_UpperLimit;

	@Column("OL_U_Lower_Limit")
	private double Ol_U_LowerLimit;

	@Column("OL_L_Lower_Limit")
	private double Ol_L_LowerLimit;

	@Column("DOL_U_Upper_Limit")
	private double DOl_U_UpperLimit;

	@Column("DOL_L_Upper_Limit")
	private double DOl_L_UpperLimit;

	@Column("DOL_U_Lower_Limit")
	private double DOl_U_LowerLimit;

	@Column("DOL_L_Lower_Limit")
	private double DOl_L_LowerLimit;

	@Column("Ratio")
	private double Ratio;

	@Column("Sigma")
	private double Sigma;
	
	@Column("Active_Rule")
	private String activeRule;
	
	@Column("Population_Size")
	private int PopulationSize;

	@Column("Sample_Size")
	private int SampleSize;

	@Column("Expire_Time")
	private long ExpireTime;

	@Column("Hold_Flag")
	private boolean holdFlag;
	
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

	public String getExpId() {
		return ExpId;
	}

	public void setExpId(String expId) {
		ExpId = expId;
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

	public String getMeaRcpId() {
		return MeaRcpId;
	}

	public void setMeaRcpId(String meaRcpId) {
		MeaRcpId = meaRcpId;
	}

	public long getExpireTime() {
		return ExpireTime;
	}

	public void setExpireTime(long expireTime) {
		ExpireTime = expireTime;
	}
	
	public String getAdcOrFdc() {
		return adcOrFdc;
	}

	public void setAdcOrFdc(String adcOrFdc) {
		this.adcOrFdc = adcOrFdc;
	}

	public String getFeedbackMode() {
		return FeedbackMode;
	}

	public void setFeedbackMode(String feedbackMode) {
		FeedbackMode = feedbackMode;
	}

	public double getOl_U_UpperLimit() {
		return Ol_U_UpperLimit;
	}

	public void setOl_U_UpperLimit(double ol_U_UpperLimit) {
		Ol_U_UpperLimit = ol_U_UpperLimit;
	}

	public double getOl_L_UpperLimit() {
		return Ol_L_UpperLimit;
	}

	public void setOl_L_UpperLimit(double ol_L_UpperLimit) {
		Ol_L_UpperLimit = ol_L_UpperLimit;
	}

	public double getOl_U_LowerLimit() {
		return Ol_U_LowerLimit;
	}

	public void setOl_U_LowerLimit(double ol_U_LowerLimit) {
		Ol_U_LowerLimit = ol_U_LowerLimit;
	}

	public double getOl_L_LowerLimit() {
		return Ol_L_LowerLimit;
	}

	public void setOl_L_LowerLimit(double ol_L_LowerLimit) {
		Ol_L_LowerLimit = ol_L_LowerLimit;
	}

	public double getDOl_U_UpperLimit() {
		return DOl_U_UpperLimit;
	}

	public void setDOl_U_UpperLimit(double dOl_U_UpperLimit) {
		DOl_U_UpperLimit = dOl_U_UpperLimit;
	}

	public double getDOl_L_UpperLimit() {
		return DOl_L_UpperLimit;
	}

	public void setDOl_L_UpperLimit(double dOl_L_UpperLimit) {
		DOl_L_UpperLimit = dOl_L_UpperLimit;
	}

	public double getDOl_U_LowerLimit() {
		return DOl_U_LowerLimit;
	}

	public void setDOl_U_LowerLimit(double dOl_U_LowerLimit) {
		DOl_U_LowerLimit = dOl_U_LowerLimit;
	}

	public double getDOl_L_LowerLimit() {
		return DOl_L_LowerLimit;
	}

	public void setDOl_L_LowerLimit(double dOl_L_LowerLimit) {
		DOl_L_LowerLimit = dOl_L_LowerLimit;
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

	public String getActiveRule() {
		return activeRule;
	}

	public void setActiveRule(String activeRule) {
		this.activeRule = activeRule;
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
		return ExpireTime;
	}

	public void setExpiretime(long ExpireTime) {
		this.ExpireTime = ExpireTime;
	}

	
	public boolean getHoldFlag() {
		return holdFlag;
	}

	public void setHoldFlag(boolean holdFlag) {
		this.holdFlag = holdFlag;
	}

	@Override 
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
	
}
