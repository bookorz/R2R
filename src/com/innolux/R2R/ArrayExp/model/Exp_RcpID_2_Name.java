package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

//P32
@Entity("Exp_RcpID_2_RcpName") // table name
public class Exp_RcpID_2_Name {
	@Id("Primarykey")
	private String Primarykey;
	
	@Column("Product")
	private String Product;
	
	@Column("Exp_Step_ID")
	private String Exp_Step_ID;
	
	@Column("Exp_Rcp_ID")
	private String Exp_Rcp_ID;
	
	@Column("Exp_Rcp_Name")
	private String Exp_Rcp_Name;

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

	public String getExp_Step_ID() {
		return Exp_Step_ID;
	}

	public void setExp_Step_ID(String exp_Step_ID) {
		Exp_Step_ID = exp_Step_ID;
	}

	public String getExp_Rcp_ID() {
		return Exp_Rcp_ID;
	}

	public void setExp_Rcp_ID(String exp_Rcp_ID) {
		Exp_Rcp_ID = exp_Rcp_ID;
	}

	public String getExp_Rcp_Name() {
		return Exp_Rcp_Name;
	}

	public void setExp_Rcp_Name(String exp_Rcp_Name) {
		Exp_Rcp_Name = exp_Rcp_Name;
	}

	@Override
	public String toString() {
		return "Exp_RcpID_2_Name [Primarykey=" + Primarykey + ", Product=" + Product + ", Exp_Step_ID=" + Exp_Step_ID
				+ ", Exp_Rcp_ID=" + Exp_Rcp_ID + ", Exp_Rcp_Name=" + Exp_Rcp_Name + "]";
	}


	
	
}
