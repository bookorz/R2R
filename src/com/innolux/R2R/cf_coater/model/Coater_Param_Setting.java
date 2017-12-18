package com.innolux.R2R.cf_coater.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;
import com.innolux.services.ObjectAnalyzer;

@Entity("CfCoater_False_Range_Setting") // 表名
public class Coater_Param_Setting {
	@Id("PPID")
	private String PPID;
	
	@Column("Recipe_No")
	private int Recipe_No;
	
	@Column("Dry_Thinkness")
	private double Dry_Thinkness;
	
	@Column("Solid_Density")
	private double Solid_Density;
	
	@Column("T3_Cot_parameter")
	private double T3_Cot_parameter;
	
	@Column("Cot_Start_Diff")
	private double Cot_Start_Diff;
	
	@Column("BETA_Cot_parameter")
	private double BETA_Cot_parameter;
	
	@Column("Cot_Mid_Diff")
	private double Cot_Mid_Diff;
	
	@Column("PDT_Cot_parameter")
	private double PDT_Cot_parameter;
	
	@Column("Cot_End_Diff")
	private double Cot_End_Diff;
	
	@Column("Squeegee_L_Cot_parameter")
	private double Squeegee_L_Cot_parameter;
	
//	@Column("Cot_End_Diff")
//	private double Cot_End_Diff;
	
	@Column("Beta_Coff_For_Cot_Start")
	private double Beta_Coff_For_Cot_Start;
	
	@Column("Beta_Coff_For_Cot_End")
	private double Beta_Coff_For_Cot_End;
	
	public String getPPID() {
		return PPID;
	}

	public void setPPID(String pPID) {
		PPID = pPID;
	}

	public int getRecipe_No() {
		return Recipe_No;
	}

	public void setRecipe_No(int recipe_No) {
		Recipe_No = recipe_No;
	}

	public double getDry_Thinkness() {
		return Dry_Thinkness;
	}

	public void setDry_Thinkness(double dry_Thinkness) {
		Dry_Thinkness = dry_Thinkness;
	}

	public double getSolid_Density() {
		return Solid_Density;
	}

	public void setSolid_Density(double solid_Density) {
		Solid_Density = solid_Density;
	}

	public double getT3_Cot_parameter() {
		return T3_Cot_parameter;
	}

	public void setT3_Cot_parameter(double t3_Cot_parameter) {
		T3_Cot_parameter = t3_Cot_parameter;
	}

	public double getCot_Start_Diff() {
		return Cot_Start_Diff;
	}

	public void setCot_Start_Diff(double cot_Start_Diff) {
		Cot_Start_Diff = cot_Start_Diff;
	}

	public double getBETA_Cot_parameter() {
		return BETA_Cot_parameter;
	}

	public void setBETA_Cot_parameter(double bETA_Cot_parameter) {
		BETA_Cot_parameter = bETA_Cot_parameter;
	}

	public double getCot_Mid_Diff() {
		return Cot_Mid_Diff;
	}

	public void setCot_Mid_Diff(double cot_Mid_Diff) {
		Cot_Mid_Diff = cot_Mid_Diff;
	}

	public double getPDT_Cot_parameter() {
		return PDT_Cot_parameter;
	}

	public void setPDT_Cot_parameter(double pDT_Cot_parameter) {
		PDT_Cot_parameter = pDT_Cot_parameter;
	}

	public double getCot_End_Diff() {
		return Cot_End_Diff;
	}

	public void setCot_End_Diff(double cot_End_Diff) {
		Cot_End_Diff = cot_End_Diff;
	}

	public double getSqueegee_L_Cot_parameter() {
		return Squeegee_L_Cot_parameter;
	}

	public void setSqueegee_L_Cot_parameter(double squeegee_L_Cot_parameter) {
		Squeegee_L_Cot_parameter = squeegee_L_Cot_parameter;
	}

	public double getBeta_Coff_For_Cot_Start() {
		return Beta_Coff_For_Cot_Start;
	}

	public void setBeta_Coff_For_Cot_Start(double beta_Coff_For_Cot_Start) {
		Beta_Coff_For_Cot_Start = beta_Coff_For_Cot_Start;
	}

	public double getBeta_Coff_For_Cot_End() {
		return Beta_Coff_For_Cot_End;
	}

	public void setBeta_Coff_For_Cot_End(double beta_Coff_For_Cot_End) {
		Beta_Coff_For_Cot_End = beta_Coff_For_Cot_End;
	}

	@Override
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
}
