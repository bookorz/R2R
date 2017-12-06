package com.innolux.R2R.parser;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.PDSBase;

public class PDS {
	
	private Logger logger = Logger.getLogger(this.getClass());
	public boolean Parse(String msg, PDSBase pds) {
		boolean result = false;
		try {
			int beginIdx = 0;
			int endIdx = 0;
			String searchText = "";
			String ParamStr = "";
			String[] splitAry = null;

			searchText = "class=";
			beginIdx = msg.indexOf(searchText) + searchText.length();
			endIdx = msg.indexOf(" ", beginIdx + 1);
			pds.ClassName = msg.substring(beginIdx, endIdx);

			searchText = "eqpID=\"";
			beginIdx = msg.indexOf(searchText) + searchText.length();
			endIdx = msg.indexOf("\"", beginIdx + 1);
			pds.Equipment_ID = msg.substring(beginIdx, endIdx);

			searchText = "processUnit1=\"";
			beginIdx = msg.indexOf(searchText) + searchText.length();
			endIdx = msg.indexOf("\"", beginIdx + 1);
			splitAry = msg.substring(beginIdx, endIdx).split(",");
			pds.SubEquipment_ID = splitAry[0];
			pds.TimeStamp = splitAry[4];

			searchText = "componentId=\"";
			beginIdx = msg.indexOf(searchText) + searchText.length();
			endIdx = msg.indexOf("\"", beginIdx + 1);
			pds.Component_ID = msg.substring(beginIdx, endIdx);

			searchText = "lotType=\"";
			beginIdx = msg.indexOf(searchText) + searchText.length();
			endIdx = msg.indexOf("\"", beginIdx + 1);
			pds.Lot_Type = msg.substring(beginIdx, endIdx);

			searchText = "processData1=\"";
			beginIdx = msg.indexOf(searchText) + searchText.length();
			endIdx = msg.indexOf("\"", beginIdx + 1);
			ParamStr = msg.substring(beginIdx, endIdx);
			
			
			for (String eachParam : ParamStr.split(",")) {
				eachParam = eachParam.replace("�c", "θ");
				String[] paramAry = eachParam.split("=");
				
				if(paramAry.length >= 2){
					String name = paramAry[0];
					String value = paramAry[1];
					pds.StoreParameter(name, value);
				}else{
					logger.debug("PDS parse error: param="+eachParam);
				}
			}
			result = true;
		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}

	
}
