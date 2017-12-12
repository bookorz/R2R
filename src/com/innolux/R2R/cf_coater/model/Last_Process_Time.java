package com.innolux.R2R.cf_coater.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;
import com.innolux.services.ObjectAnalyzer;

@Entity("CfCoater_Last_Process_Time") // 表名
public class Last_Process_Time {
	@Id("PrimaryKey")
	private String PrimaryKey;
	@Column("SubEqpId")
	private String SubEqpId;
	@Column("RecipeNo")
	private String RecipeNo;
	@Column("UpdateTime")
	private long UpdateTime;
	public String getPrimaryKey() {
		return PrimaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		PrimaryKey = primaryKey;
	}
	public String getSubEqpId() {
		return SubEqpId;
	}
	public void setSubEqpId(String subEqpId) {
		SubEqpId = subEqpId;
	}
	public String getRecipeNo() {
		return RecipeNo;
	}
	public void setRecipeNo(String recipeNo) {
		RecipeNo = recipeNo;
	}
	public long getUpdateTime() {
		return UpdateTime;
	}
	public void setUpdateTime(long updateTime) {
		UpdateTime = updateTime;
	}
	@Override
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
	
}
