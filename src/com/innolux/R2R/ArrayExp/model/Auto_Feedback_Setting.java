package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

//p28
@Entity("Auto_Feedback_Setting") // table name
public class Auto_Feedback_Setting {
	@Id("Primarykey")
	private String Primarykey;

	@Column("Active_Flag")
	private String Active_Flag;

	@Column("Eqp_ID")
	private String Eqp_ID;

	@Column("Exp_Step_Id")
	private String Exp_Step_Id;

	@Column("Mea_Step_Id")
	private String Mea_Step_Id;

	@Column("Exp_Rcp_Id")
	private String Exp_Rcp_Id;

	@Column("Exp_Rcp_Name")
	private String Exp_Rcp_Name;
	
	@Column("Mea_Rcp")
	private String Mea_Rcp;

	@Column("Feedback_Mode")
	private String Feedback_Mode;

	@Column("U_Upper_Limit")
	private double U_Upper_Limit;

	@Column("L_Upper_Limit")
	private double L_Upper_Limit;

	@Column("U_Lower_Limit")
	private double U_Lower_Limit;

	@Column("L_Lower_Limit")
	private double L_Lower_Limit;

	@Column("Ratio")
	private double Ratio;

	@Column("Sigma")
	private double Sigma;

	@Column("Population_Size")
	private int Population_Size;

	@Column("Sample_Size")
	private int Sample_Size;

	@Column("Expire_time")
	private long Expire_time;

	public String getPrimarykey() {
		return Primarykey;
	}

	public void setPrimarykey(String primarykey) {
		Primarykey = primarykey;
	}

	public String getActive_Flag() {
		return Active_Flag;
	}

	public void setActive_Flag(String active_Flag) {
		Active_Flag = active_Flag;
	}

	public String getEqp_ID() {
		return Eqp_ID;
	}

	public void setEqp_ID(String eqp_ID) {
		Eqp_ID = eqp_ID;
	}

	public String getExp_Step_Id() {
		return Exp_Step_Id;
	}

	public void setExp_Step_Id(String exp_Step_Id) {
		Exp_Step_Id = exp_Step_Id;
	}

	public String getMea_Step_Id() {
		return Mea_Step_Id;
	}

	public void setMea_Step_Id(String mea_Step_Id) {
		Mea_Step_Id = mea_Step_Id;
	}

	public String getExp_Rcp_Id() {
		return Exp_Rcp_Id;
	}

	public void setExp_Rcp_Id(String exp_Rcp_Id) {
		Exp_Rcp_Id = exp_Rcp_Id;
	}

	public String getExp_Rcp_Name() {
		return Exp_Rcp_Name;
	}

	public void setExp_Rcp_Name(String exp_Rcp_Name) {
		Exp_Rcp_Name = exp_Rcp_Name;
	}

	public String getMea_Rcp() {
		return Mea_Rcp;
	}

	public void setMea_Rcp(String mea_Rcp) {
		Mea_Rcp = mea_Rcp;
	}

	public String getFeedback_Mode() {
		return Feedback_Mode;
	}

	public void setFeedback_Mode(String feedback_Mode) {
		Feedback_Mode = feedback_Mode;
	}

	public double getU_Upper_Limit() {
		return U_Upper_Limit;
	}

	public void setU_Upper_Limit(double u_Upper_Limit) {
		U_Upper_Limit = u_Upper_Limit;
	}

	public double getL_Upper_Limit() {
		return L_Upper_Limit;
	}

	public void setL_Upper_Limit(double l_Upper_Limit) {
		L_Upper_Limit = l_Upper_Limit;
	}

	public double getU_Lower_Limit() {
		return U_Lower_Limit;
	}

	public void setU_Lower_Limit(double u_Lower_Limit) {
		U_Lower_Limit = u_Lower_Limit;
	}

	public double getL_Lower_Limit() {
		return L_Lower_Limit;
	}

	public void setL_Lower_Limit(double l_Lower_Limit) {
		L_Lower_Limit = l_Lower_Limit;
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

	public long getExpire_time() {
		return Expire_time;
	}

	public void setExpire_time(long expire_time) {
		Expire_time = expire_time;
	}

	public int getPopulation_Size() {
		return Population_Size;
	}

	public void setPopulation_Size(int population_Size) {
		Population_Size = population_Size;
	}

	public int getSample_Size() {
		return Sample_Size;
	}

	public void setSample_Size(int sample_Size) {
		Sample_Size = sample_Size;
	}

	@Override
	public String toString() {
		return "Auto_Feedback_Setting [Primarykey=" + Primarykey + ", Active_Flag=" + Active_Flag + ", Eqp_ID=" + Eqp_ID
				+ ", Exp_Step_Id=" + Exp_Step_Id + ", Mea_Step_Id=" + Mea_Step_Id + ", Exp_Rcp_Id=" + Exp_Rcp_Id
				+ ", Exp_Rcp_Name=" + Exp_Rcp_Name + ", Mea_Rcp=" + Mea_Rcp + ", Feedback_Mode=" + Feedback_Mode
				+ ", U_Upper_Limit=" + U_Upper_Limit + ", L_Upper_Limit=" + L_Upper_Limit + ", U_Lower_Limit="
				+ U_Lower_Limit + ", L_Lower_Limit=" + L_Lower_Limit + ", Ratio=" + Ratio + ", Sigma=" + Sigma
				+ ", Population_Size=" + Population_Size + ", Sample_Size=" + Sample_Size + ", Expire_time="
				+ Expire_time + "]";
	}




}
