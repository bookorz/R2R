package com.innolux.R2R.ArrayExp.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.services.ObjectAnalyzer;

@Entity("fwflatnode") // table name
public class MES_fwflatnode {
	@Column("nodename")
	private String nodeName;
	
	@Column("stepseq")
	private String stepSeq;

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getStepSeq() {
		return stepSeq;
	}

	public void setStepSeq(String stepSeq) {
		this.stepSeq = stepSeq;
	}
	
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
}
