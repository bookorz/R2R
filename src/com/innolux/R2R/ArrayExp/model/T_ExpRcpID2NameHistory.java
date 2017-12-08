package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;

//P37
@Entity("Exp_RcpID_2_Name_History") // table name
public class T_ExpRcpID2NameHistory {
	@Column("Product")
	private String Product;
	
	@Column("Exp_Step_ID")
	private String ExpStepID;
	
	@Column("Exp_Rcp_ID")
	private String ExpRcpID;
	
	@Column("Exp_Rcp_Name")
	private String ExpRcpName;
	
	@Column("Update_Time")
	private long UpdateTime;
	
	@Column("Update_User_ID")
	private String UpdateUserID;

	public String getProduct() {
		return Product;
	}

	public void setProduct(String product) {
		Product = product;
	}

	public String getExpStepID() {
		return ExpStepID;
	}

	public void setExpStepID(String expStepID) {
		ExpStepID = expStepID;
	}

	public String getExpRcpID() {
		return ExpRcpID;
	}

	public void setExpRcpID(String expRcpID) {
		ExpRcpID = expRcpID;
	}

	public String getExpRcpName() {
		return ExpRcpName;
	}

	public void setExpRcpName(String expRcpName) {
		ExpRcpName = expRcpName;
	}

	public long getUpdateTime() {
		return UpdateTime;
	}

	public void setUpdateTime(long updateTime) {
		UpdateTime = updateTime;
	}

	public String getUpdateUserID() {
		return UpdateUserID;
	}

	public void setUpdateUserID(String updateUserID) {
		UpdateUserID = updateUserID;
	}

	
	@Override 
	public String toString() {
	return 
		" Product " + Product +
		" ExpStepID " + ExpStepID +
		" ExpRcpID " + ExpRcpID +
		" ExpRcpName " + ExpRcpName +
		" UpdateTime " + UpdateTime +
		" UpdateUserID " + UpdateUserID;
	}
	
}
