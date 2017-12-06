package com.innolux.R2R.common.base;

import java.util.Hashtable;

public class GlassDataBase {
	public String R2R_ID = "";
	public String Glass_ID = "";
	private Hashtable<String, String> _ParamCollection = new Hashtable<String, String>();
	public String TimeStamp = "";

	@Override
	public String toString() {
		String info = "";
		info = getClass().getSimpleName() + "{R2R_ID:" + R2R_ID + "," + "Glass_ID:" + Glass_ID + ",Parameters:[";
		for (String name : _ParamCollection.keySet()) {
			info += "(" + name + ":" + _ParamCollection.get(name) + ")";
		}
		info += "],TimeStamp:" + TimeStamp + "}";
		return info;
	}
	
	public void StoreParameter(String name, String value) {
		
		if (!_ParamCollection.containsKey(name)) {
			
			_ParamCollection.put(name, value);
		}
	}

	public String GetParameter(String paramName) {
		String result = "";
		if (_ParamCollection.containsKey(paramName)) {
			result = _ParamCollection.get(paramName);
		}
		return result;
	}

}
