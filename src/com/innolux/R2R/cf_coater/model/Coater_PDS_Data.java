package com.innolux.R2R.cf_coater.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;
import com.innolux.services.ObjectAnalyzer;

@Entity("CfCoater_PDS_Data") // 表名
public class Coater_PDS_Data {
	@Id("PrimaryKey")
	private String PrimaryKey;

	@Column("EqpId")
	private String EqpId;

	@Column("PPID")
	private String PPID;

	@Column("Coating_W")
	private double Coating_W;
	
	@Column("CoatSpd_V1")
	private double CoatSpd_V1;
	
	@Column("Dis_Spd_Q2")
	private double Dis_Spd_Q2;
	
	@Column("AfterDis_T3")
	private double AfterDis_T3;
	
	@Column("PumpDecel_T")
	private double PumpDecel_T;
	
	@Column("Squeegee_L")
	private double Squeegee_L;

	public String getPrimaryKey() {
		return PrimaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		PrimaryKey = primaryKey;
	}

	public String getEqpId() {
		return EqpId;
	}

	public void setEqpId(String eqpId) {
		EqpId = eqpId;
	}


	public String getPPID() {
		return PPID;
	}

	public void setPPID(String pPID) {
		PPID = pPID;
	}

	public double getCoating_W() {
		return Coating_W;
	}

	public void setCoating_W(double coating_W) {
		Coating_W = coating_W;
	}

	public double getCoatSpd_V1() {
		return CoatSpd_V1;
	}

	public void setCoatSpd_V1(double coatSpd_V1) {
		CoatSpd_V1 = coatSpd_V1;
	}

	public double getDis_Spd_Q2() {
		return Dis_Spd_Q2;
	}

	public void setDis_Spd_Q2(double dis_Spd_Q2) {
		Dis_Spd_Q2 = dis_Spd_Q2;
	}

	public double getAfterDis_T3() {
		return AfterDis_T3;
	}

	public void setAfterDis_T3(double afterDis_T3) {
		AfterDis_T3 = afterDis_T3;
	}

	public double getPumpDecel_T() {
		return PumpDecel_T;
	}

	public void setPumpDecel_T(double pumpDecel_T) {
		PumpDecel_T = pumpDecel_T;
	}

	public double getSqueegee_L() {
		return Squeegee_L;
	}

	public void setSqueegee_L(double squeegee_L) {
		Squeegee_L = squeegee_L;
	}

	@Override
	public String toString() {
		return ObjectAnalyzer.toString(this);
	}
}
