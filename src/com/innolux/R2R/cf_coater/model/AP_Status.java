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



	@Override
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
	
}
