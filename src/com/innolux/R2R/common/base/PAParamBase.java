package com.innolux.R2R.common.base;

public class PAParamBase {
	public String Glass_ID="";
	
	//Regulation param
	public double T_OFFSET_X = 0;
	public double T_OFFSET_Y = 0;
	public double T_OFFSET_θ = 0;
	public double C_OFFSET_X = 0;
	public double C_OFFSET_Y = 0;
	public double C_OFFSET_θ = 0;
	
	
	//monitor param
	public double C_ACC_1X = 0;
	public double C_ACC_2X = 0;
	public double C_ACC_1Y = 0;
	public double C_ACC_3Y = 0;
	public double T_ACC_1X = 0;
	public double T_ACC_2X = 0;
	public double T_ACC_1Y = 0;
	public double T_ACC_3Y = 0;
	
	//Target param
	public double C_ACC_1X_TG = 0;
	public double C_ACC_2X_TG = 0;
	public double C_ACC_1Y_TG = 0;
	public double C_ACC_3Y_TG = 0;
	public double T_ACC_1X_TG = 0;
	public double T_ACC_2X_TG = 0;
	public double T_ACC_1Y_TG = 0;
	public double T_ACC_3Y_TG = 0;
	
	@Override
	public String toString() {
		return getClass().getSimpleName() 
				+ "{C_OFFSET_X:" + C_OFFSET_X + "," 
				+ "C_OFFSET_Y:" + C_OFFSET_Y + "," 
				+ "C_OFFSET_θ:" + C_OFFSET_θ + "," 
				+ "T_OFFSET_X:" + T_OFFSET_X + "," 
				+ "T_OFFSET_Y:" + T_OFFSET_Y + "," 
				+ "T_OFFSET_θ:" + T_OFFSET_θ + "," 
				+ "C_ACC_1X:" + C_ACC_1X + "," 
				+ "C_ACC_2X:" + C_ACC_2X + "," 
				+ "C_ACC_1Y:" + C_ACC_1Y + "," 
				+ "C_ACC_3Y:" + C_ACC_3Y + "," 
				+ "T_ACC_1X:" + T_ACC_1X + "," 
				+ "T_ACC_2X:" + T_ACC_2X + "," 
				+ "T_ACC_1Y:" + T_ACC_1Y + "," 
				+ "T_ACC_3Y:" + T_ACC_3Y + "," 
				+ "C_ACC_1X_TG:" + C_ACC_1X_TG + "," 
				+ "C_ACC_2X_TG:" + C_ACC_2X_TG + "," 
				+ "C_ACC_1Y_TG:" + C_ACC_1Y_TG + "," 
				+ "C_ACC_3Y_TG:" + C_ACC_3Y_TG + "," 
				+ "T_ACC_1X_TG:" + T_ACC_1X_TG + "," 
				+ "T_ACC_2X_TG:" + T_ACC_2X_TG + "," 
				+ "T_ACC_1Y_TG:" + T_ACC_1Y_TG + "," 
				+ "T_ACC_3Y_TG:" + T_ACC_3Y_TG + "}";
	}
}
