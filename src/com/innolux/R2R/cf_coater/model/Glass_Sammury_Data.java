package com.innolux.R2R.cf_coater.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;
import com.innolux.services.ObjectAnalyzer;

@Entity("CfCoater_Glass_Sammury_Data") // 表名
public class Glass_Sammury_Data {
	@Id("PrimaryKey")
	private String PrimaryKey;

	@Column("EqpId")
	private String EqpId;

	@Column("Recipe_No")
	private String Recipe_No;

	@Column("PPID")
	private String PPID;

	@Column("PreEqpId")
	private String PreEqpId;

	@Column("PreRecipe_No")
	private String PreRecipe_No;

	@Column("PrePPID")
	private String PrePPID;

	@Column("Glass_Id")
	private String Glass_Id;

	@Column("TimeStamp")
	private long TimeStamp;

	@Column("Start_Avg")
	private double Start_Avg;

	@Column("Mid_Avg")
	private double Mid_Avg;

	@Column("End_Avg")
	private double End_Avg;
	
	@Column("Tatget")
	private double Tatget;

	public double getTatget() {
		return Tatget;
	}

	public void setTatget(double tatget) {
		Tatget = tatget;
	}

	public String getPrimaryKey() {
		return PrimaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		PrimaryKey = primaryKey;
	}

	public String getEqpId() {
		return EqpId;
	}

	public void setEqpId(String eqpId) {
		EqpId = eqpId;
	}

	public String getRecipe_No() {
		return Recipe_No;
	}

	public void setRecipe_No(String recipe_No) {
		Recipe_No = recipe_No;
	}

	public String getPPID() {
		return PPID;
	}

	public void setPPID(String pPID) {
		PPID = pPID;
	}

	public String getPreEqpId() {
		return PreEqpId;
	}

	public void setPreEqpId(String preEqpId) {
		PreEqpId = preEqpId;
	}

	public String getPreRecipe_No() {
		return PreRecipe_No;
	}

	public void setPreRecipe_No(String preRecipe_No) {
		PreRecipe_No = preRecipe_No;
	}

	public String getPrePPID() {
		return PrePPID;
	}

	public void setPrePPID(String prePPID) {
		PrePPID = prePPID;
	}

	public String getGlass_Id() {
		return Glass_Id;
	}

	public void setGlass_Id(String glass_Id) {
		Glass_Id = glass_Id;
	}

	public long getTimeStamp() {
		return TimeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		TimeStamp = timeStamp;
	}

	public double getStart_Avg() {
		return Start_Avg;
	}

	public void setStart_Avg(double start_Avg) {
		Start_Avg = start_Avg;
	}

	public double getMid_Avg() {
		return Mid_Avg;
	}

	public void setMid_Avg(double mid_Avg) {
		Mid_Avg = mid_Avg;
	}

	public double getEnd_Avg() {
		return End_Avg;
	}

	public void setEnd_Avg(double end_Avg) {
		End_Avg = end_Avg;
	}

	@Override
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
}
