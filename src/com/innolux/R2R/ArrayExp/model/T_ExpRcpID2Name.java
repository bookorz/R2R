package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

//P32
@Entity("Exp_RcpID_2_RcpName") // table name
public class T_ExpRcpID2Name {
	@Id("Primarykey")
	private String Primarykey;
	
	@Column("Product")
	private String Product;
	
	@Column("Exp_Step_ID")
	private String ExpStepID;
	
	@Column("Exp_Rcp_ID")
	private String ExpRcpID;
	
	@Column("Exp_Rcp_Name")
	private String ExpRcpName;

	public String getPrimarykey() {
		return Primarykey;
	}

	public void setPrimarykey(String primarykey) {
		Primarykey = primarykey;
	}

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
	
	
	@Override 
	public String toString() {
	return 
		" Primarykey " + Primarykey +
		" Product " + Product +
		" ExpStepID " + ExpStepID +
		" ExpRcpID " + ExpRcpID +
		" ExpRcpName " + ExpRcpName;
	}
}
