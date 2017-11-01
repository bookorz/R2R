package com.innolux.R2R.common.base;

public class ED03Base {
	public String ReportType = "";
	public String DataItem = "";
	public String Index = "";

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{ReportType:" + ReportType + "," + "DataItem:" + DataItem + "," + "Index:"
				+ Index + "}";
	}
}