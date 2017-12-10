package com.innolux.R2R.common.base;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;

public class MeasureFileDataBase {
	private Logger logger = Logger.getLogger(this.getClass());
	private Hashtable<String, Hashtable<Long,String>> data = new Hashtable<String, Hashtable<Long,String>>();
	private String currentHeader = "";

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
					
					for (int i = 0;i<valueData.size();i++) {
						String name = "";
						
						String[] rowAry = valueData.get(i).split(":");
						if (rowAry.length >= 2) {
							name = rowAry[0];
							if (name.equals(Name)) {
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
								result.add(value[headerIdx]);
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
	
	public String getCsvValByRowCol(String Session, String keyCol, String keyStr, String correspondCol){
		List<String> keyList = this.FetchList(Session, keyCol);
		List<String> correspondList = this.FetchList(Session, correspondCol);
		
		if(keyList == null || correspondList == null){
			logger.debug("ArrayExp getCsvValByRowCol: " + keyCol + " List = null || " + correspondCol + " List = null");
			return "";
		}else if(keyList.size() == 0 || correspondList.size() == 0){
			logger.debug("ArrayExp getCsvValByRowCol: " + keyCol + " size = 0 || " + correspondCol + " size = 0");
			return "";
		}else if(keyList.size() != correspondList.size()){
			logger.debug("ArrayExp getCsvValByRowCol: " + keyCol + " size != 0 " + correspondCol + " size");
			return "";
		}

		int keyStrInd = keyList.indexOf(keyStr);
		String correspondStr = correspondList.get(keyStrInd);
		return correspondStr;
	}
	
}
