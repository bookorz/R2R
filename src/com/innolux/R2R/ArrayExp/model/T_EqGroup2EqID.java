package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;

@Entity("ArrayExp_EqGroup_2_EqID") // table name
public class T_EqGroup2EqID {
	@Column("EqGroup")
	private String EqGroup;
	
	@Column("EqID")
	private String EqID;

	
	public String getEqGroup() {
		return EqGroup;
	}

	public void setEqGroup(String eqGroup) {
		EqGroup = eqGroup;
	}

	public String getEqID() {
		return EqID;
	}

	public void setEqID(String eqID) {
		EqID = eqID;
	}
	
	@Override 
	public String toString() {
	return 
		" EqGroup " + EqGroup +
		" EqID " + EqID;
	}
}
