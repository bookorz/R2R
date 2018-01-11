package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.services.ObjectAnalyzer;

@Entity("lwExpR2rSetting") // table name
public class MES_lwExpR2rSetting {
	@Column("eqpId")
	private String eqpId;

	@Column("prodId")
	private String prodId;

	@Column("recipeId")
	private String recipeId;

	@Column("holdFlag")
	private String holdFlag;

	@Column("r2rFeedbackTime")
	private String r2rFeedbackTime;

	public String getEqpId() {
		return eqpId;
	}

	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public String getHoldFlag() {
		return holdFlag;
	}

	public void setHoldFlag(String holdFlag) {
		this.holdFlag = holdFlag;
	}

	public String getR2rFeedbackTime() {
		return r2rFeedbackTime;
	}

	public void setR2rFeedbackTime(String r2rFeedbackTime) {
		this.r2rFeedbackTime = r2rFeedbackTime;
	}
	
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
}
