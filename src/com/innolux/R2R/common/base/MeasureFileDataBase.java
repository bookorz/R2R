package com.innolux.R2R.common.base;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;


import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.model.MeasureFileData;
import com.innolux.R2R.model.MeasureFileData_CRUD;

public class MeasureFileDataBase {
	private Logger logger = Logger.getLogger(this.getClass());
	private Hashtable<String, Hashtable<Long,String>> data = new Hashtable<String, Hashtable<Long,String>>();
	private String currentHeader = "";
	private String FileName = "";
	
	
	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public boolean StoreFile(String EqpId, String Recipe){
		try {
			for(String eachheader:data.keySet()){

				Hashtable<Long,String> sectionData = data.get(eachheader);
				
				for(long index:sectionData.keySet()){
					String eachRow = sectionData.get(index);
					MeasureFileData RowData = new MeasureFileData();
					RowData.setEqpId(EqpId);				
					RowData.setRecipe(Recipe);
					RowData.setFileName(this.getFileName());					
					RowData.setHeaderName(eachheader);					
					RowData.setRowData(eachRow);
					RowData.setRowIndex(index);					
					
					MeasureFileData_CRUD.create(RowData);
				}
				
			}

			return true;
		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
			return false;
		}
	}

	public void Store(String headerName,long index, String raw) {
		try {
			if (data.containsKey(headerName)) {
				Hashtable<Long,String> tmp = data.get(headerName);
				tmp.put(index,raw);
			} else {
				Hashtable<Long,String> tmp = new Hashtable<Long,String>();
				tmp.put(index,raw);
				data.put(currentHeader, tmp);
			}
		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
	}

	public void ReadLine(String raw) {
		try {
			if (raw.indexOf("_BEGIN") != -1) {
				currentHeader = raw.replace("_BEGIN", "").replace(",", "");
			} else if (raw.indexOf("_END") != -1) {
				currentHeader = "";
			} else {
				if (!currentHeader.equals("")) {
					if (data.containsKey(currentHeader)) {
						Hashtable<Long,String> tmp = data.get(currentHeader);
						tmp.put((long)tmp.size(),raw);
					} else {
						Hashtable<Long,String> tmp = new Hashtable<Long,String>();
						tmp.put((long)tmp.size(),raw);
						data.put(currentHeader, tmp);
					}
				}
			}
		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
	}

	public String FetchInfo(String headerName, String Name) {
		String result = "";

		try {
			if (data.containsKey(headerName)) {
				Hashtable<Long,String> valueData = data.get(headerName);				
				if (valueData.size() != 0) {
					
					for (int i = 0; i < valueData.size(); i++) {
						String name = "";
						
						String[] rowAry = valueData.get((long)i).split(",");
						String[] rowAry2 = rowAry[0].split(":");
						if (rowAry2.length >= 2) {
							name = rowAry2[0];
							if (name.equals(Name)) {
								result = rowAry2[1];
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

	public String FetchValue(String headerName, String Name) {
		String result = "";
		try { 
			if (data.containsKey(headerName)) {
				Hashtable<Long,String> valueData = data.get(headerName);
				if (valueData.size() >= 2) {
					String[] header = valueData.get((long)0).split(",");
					String[] value = valueData.get((long)1).split(",");
					//if (header.length == value.length) {
						for (int i = 0; i < header.length; i++) {
							if (header[i].equals(Name)) {
								result = value[i];
								break;
							}
						}
					//} else {
					//	logger.error("Header's qty is not match to value's qty.");
					//}
				}
			}
		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}

	public List<String> FetchList(String headerName, String Name) {
		List<String> result = new ArrayList<String>();
		int headerIdx = -1;
		try {
			if (data.containsKey(headerName)) {
				Hashtable<Long,String> valueData = data.get(headerName);
				if (valueData.size() >= 2) {
					for (int i = 0; i < valueData.size(); i++) {
						if (i == 0) {
							String[] header = valueData.get((long)i).split(",");
							for (int x = 0; x < header.length; x++) {
								if (header[x].equals(Name)) {
									headerIdx = x;
									break;
								}
							}
						} else {
							if (headerIdx == -1) {
								break;
							} else {
								String[] value = valueData.get((long)i).split(",");
								try {
									result.add(value[headerIdx]);
								}catch(Exception e1) {
									result.add("");
								}
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
	
	public String getCsvValByRowCol(String Session, String key1Col, String key1Str, String correspondCol){
		List<String> key1List = this.FetchList(Session, key1Col);
		List<String> correspondList = this.FetchList(Session, correspondCol);
		
		if(key1List == null || correspondList == null){
			logger.debug("ArrayExp getCsvValByRowCol: " + key1Col + " List = null || " + correspondCol + " List = null");
			return "";
		}else if(key1List.size() == 0 || correspondList.size() == 0){
			logger.debug("ArrayExp getCsvValByRowCol: " + key1Col + " size = 0 || " + correspondCol + " size = 0");
			return "";
		}
		
		int keyInd = key1List.indexOf(key1Str);
		String correspondStr = correspondList.get(keyInd);
		return correspondStr;
	}
		
	public String getCsvValByRowCol(String Session, String key1Col, String key1Str, 
													String key2Col, String key2Str, String correspondCol){
		List<String> key1List = this.FetchList(Session, key1Col);
		List<String> key2List = this.FetchList(Session, key2Col);
		List<String> correspondList = this.FetchList(Session, correspondCol);
		
		if(key1List == null || key2List == null || correspondList == null){
			logger.debug("ArrayExp getCsvValByRowCol: " + key1Col + " List = null || " + 
							key2Col + " List = null || " + correspondCol + " List = null");
			return "";
		}else if(key1List.size() == 0 || key2List.size() == 0 || correspondList.size() == 0){
			logger.debug("ArrayExp getCsvValByRowCol: " + key1Col + " size = 0 || " + 
						key2Col + " size = 0 || " + 
						correspondCol + " size = 0");
			return "";
		}else if(key1List.size() != key2List.size()){
			logger.debug("ArrayExp getCsvValByRowCol: " + key1Col + " size != 0 " + key2Col + " size");
			return "";
		}else if(key1List.size() != correspondList.size()){
			logger.debug("ArrayExp getCsvValByRowCol: " + key1Col + " size != 0 " + correspondCol + " size");
			return "";
		}

		for(int ind = 0; ind < key1List.size(); ind++) {
			key1List.set(ind, key1List.get(ind) + key2List.get(ind));
		}
		int keyInd = key1List.indexOf(key1Str + key2Str);
		String correspondStr = correspondList.get(keyInd);
		return correspondStr;
	}
	
	public int indexOfFirstValidValueInCol(String Session, String key1Col){
		List<String> key1List = this.FetchList(Session, key1Col);
		if(key1List == null){
			logger.debug("ArrayExp getCsvValByRowCol: " + key1Col + " List = null");
			return -1;
		}else if(key1List.size() == 0){
			logger.debug("ArrayExp getCsvValByRowCol: " + key1Col + " size = 0");
			return -1;
		}
		Iterator<String> iter = key1List.iterator();
		while(iter.hasNext()) {
			String element = (String)iter.next();
			if(!element.equals("") && !element.equals("0")) {
				return key1List.indexOf(element);
			}
		}
		return -1;
	}
	
	
	
}
