package com.innolux.R2R.common.base;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;

public class MeasureFileDataBase {
	private Logger logger = Logger.getLogger(this.getClass());
	private Hashtable<String, List<String>> data = new Hashtable<String, List<String>>();
	private String currentHeader = "";
	
	public void Store(String raw){
		if (raw.indexOf("_BEGIN") != -1) {
			currentHeader = raw.replace("_BEGIN", "");
		} else if (raw.indexOf("_END") != -1) {
			currentHeader = "";
		} else {
			if (!currentHeader.equals("")) {
				if (data.containsKey(currentHeader)) {
					List<String> tmp = data.get(currentHeader);
					tmp.add(raw);
				} else {
					List<String> tmp = new ArrayList<String>();
					tmp.add(raw);
					data.put(currentHeader, tmp);
				}
			}
		}
	}
	
	public String FetchInfo(String headerName,String Name){
		String result = "";
		
		try {
			if (data.containsKey(headerName)) {
				List<String> valueData = data.get(headerName);
				if (valueData.size()!= 0) {
					for(String eachRow:valueData){
						String name = "";
					
						String[] rowAry = eachRow.split(":");
						if(rowAry.length>=2){
							name = rowAry[0];
							if(name.equals(Name)){
								result = rowAry[1];
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}

		return result;
	}
	
	public String FetchValue(String headerName, String Name, String R2R_ID) {
		String result = "";
		try {
			if (data.containsKey(headerName)) {
				List<String> valueData = data.get(headerName);
				if (valueData.size() >= 2) {
					String[] header = valueData.get(0).split(",");
					String[] value = valueData.get(1).split(",");
					if (header.length == value.length) {
						for (int i = 0; i < header.length; i++) {
							if (header[i].equals(Name)) {
								result = value[i];
							}
						}
					} else {
						logger.error("Run to run ID:" + R2R_ID +" Header's qty is not match to value's qty.");
					}
				}
			}
		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}

	public List<String> FetchList(String headerName, String Name, String R2R_ID) {
		List<String> result = new ArrayList<String>();
		int headerIdx = -1;
		try {
			if (data.containsKey(headerName)) {
				List<String> valueData = data.get(headerName);
				if (valueData.size() >= 2) {
					for (int i = 0; i < valueData.size(); i++) {
						if (i == 0) {
							String[] header = valueData.get(i).split(",");
							for (int x = 0; x < header.length; x++) {
								if (header[i].equals(Name)) {
									headerIdx = i;
								}
							}
						} else {
							if (headerIdx == -1) {
								break;
							} else {
								String[] value = valueData.get(i).split(",");
								result.add(value[headerIdx]);
							}
						}

					}
				}
			}
		} catch (Exception e) {
			logger.error("Run to run ID:" + R2R_ID + " " +ToolUtility.StackTrace2String(e));
		}
		return result;
	}
}
