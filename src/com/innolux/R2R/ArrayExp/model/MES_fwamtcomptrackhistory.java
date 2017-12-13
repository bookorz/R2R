package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.services.ObjectAnalyzer;

@Entity("fwamtcomptrackhistory") // table name
public class MES_fwamtcomptrackhistory {
	
	
	@Column("componentid")
	private String componentId;

	@Column("eqpid")
	private String eqpId;

	@Column("stepid")
	private String stepId;

	@Column("txntimestamp")
	private String txnTimeStamp;

	@Column("activity")
	private String activity;

	

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public String getEqpId() {
		return eqpId;
	}

	public void setEqpId(String eqpId) {
		this.eqpId = eqpId;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getTxnTimeStamp() {
		return txnTimeStamp;
	}

	public void setTxnTimeStamp(String txnTimeStamp) {
		this.txnTimeStamp = txnTimeStamp;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
}
