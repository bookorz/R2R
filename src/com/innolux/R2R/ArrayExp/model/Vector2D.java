package com.innolux.R2R.ArrayExp.model;

import javax.naming.spi.DirStateFactory.Result;

public class Vector2D {
	double xAxis;
	double yAxis;
	double xValue;
	double yValue;
	
	public Vector2D(){
		
	}
	
	public Vector2D(double xAxis, double yAxis, double xValue, double yValue){
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.xValue = xValue;
		this.yValue = yValue;
	}

	public double getxAxis() {
		return xAxis;
	}

	public void setxAxis(double xAxis) {
		this.xAxis = xAxis;
	}

	public double getyAxis() {
		return yAxis;
	}

	public void setyAxis(double yAxis) {
		this.yAxis = yAxis;
	}

	public double getxValue() {
		return xValue;
	}

	public void setxValue(double xValue) {
		this.xValue = xValue;
	}

	public double getyValue() {
		return yValue;
	}

	public void setyValue(double yValue) {
		this.yValue = yValue;
	}
	
	public String toString() {
		String result = "";
		result += String.valueOf(this.xAxis) + "," + String.valueOf(this.yAxis) + ",";
		result += String.valueOf(this.xValue)+ ","  + String.valueOf(this.yValue)+ ";" ;
		return result; 
	}
}
