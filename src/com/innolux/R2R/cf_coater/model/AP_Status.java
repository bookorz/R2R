package com.innolux.R2R.cf_coater.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;
import com.innolux.services.ObjectAnalyzer;

@Entity("CfCoater_AP_Status") // 表名
public class AP_Status {
	@Id("PrimaryKey")
	private String PrimaryKey;
	@Column("PreEqpId")
	private String PreEqpId;
	@Column("PreRecipe")
	private String PreRecipe;
	@Column("Last_Time")
	private long Last_Time;
	@Column("Current_Count")
	private int Current_Count;
	@Column("Total_Count")
	private int Total_Count;
	@Column("Expire_Interval_Time")
	private long Expire_Interval_Time;
	
	public String getPrimaryKey() {
		return PrimaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		PrimaryKey = primaryKey;
	}

	public String getPreEqpId() {
		return PreEqpId;
	}

	public void setPreEqpId(String preEqpId) {
		PreEqpId = preEqpId;
	}

	public String getPreRecipe() {
		return PreRecipe;
	}

	public void setPreRecipe(String preRecipe) {
		PreRecipe = preRecipe;
	}

	public long getLast_Time() {
		return Last_Time;
	}

	public void setLast_Time(long last_Time) {
		Last_Time = last_Time;
	}

	public int getCurrent_Count() {
		return Current_Count;
	}

	public void setCurrent_Count(int current_Count) {
		Current_Count = current_Count;
	}

	public int getTotal_Count() {
		return Total_Count;
	}

	public void setTotal_Count(int total_Count) {
		Total_Count = total_Count;
	}

	public long getExpire_Interval_Time() {
		return Expire_Interval_Time;
	}

	public void setExpire_Interval_Time(long expire_Interval_Time) {
		Expire_Interval_Time = expire_Interval_Time;
	}

	@Override
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
	
}
