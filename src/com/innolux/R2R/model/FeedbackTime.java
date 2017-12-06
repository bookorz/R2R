package com.innolux.R2R.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

@Entity("Feedback_Time") // 表名
public class FeedbackTime {

	/**
	 * PrimaryKey
	 */
	@Id("PrimaryKey")
	private String PrimaryKey;

	public String getPrimaryKey() {
		return PrimaryKey;
	}

	public void setPrimaryKey(String PrimaryKey) {
		this.PrimaryKey = PrimaryKey;
	}

	/**
	 * EqpId
	 */
	@Column("EqpId")
	private String EqpId;

	public String getEqpId() {
		return EqpId;
	}

	public void setEqpId(String EqpId) {
		this.EqpId = EqpId;
	}

	/**
	 * SubEqpId
	 */
	@Column("SubEqpId")
	private String SubEqpId;

	public String getSubEqpId() {
		return SubEqpId;
	}

	public void setSubEqpId(String SubEqpId) {
		this.SubEqpId = SubEqpId;
	}

	/**
	 * Recipe
	 */
	@Column("Recipe")
	private String Recipe;

	public String getRecipe() {
		return Recipe;
	}

	public void setRecipe(String Recipe) {
		this.Recipe = Recipe;
	}
	
	/**
	 * UpdateTime
	 */
	@Column("UpdateTime")
	private long UpdateTime;

	public long getUpdateTime() {
		return UpdateTime;
	}

	public void setUpdateTime(long UpdateTime) {
		this.UpdateTime = UpdateTime;
	}
	
	@Override  
    public String toString() {  
        return 
        		" PrimaryKey: " + PrimaryKey+
        		" EqpId: " + EqpId+
        		" SubEqpId: " + SubEqpId+
        		" Recipe: " + Recipe+
        		" UpdateTime: " + UpdateTime;   		
    } 
}
