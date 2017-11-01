package com.innolux.R2R.common.base;

import java.util.Hashtable;

public class ConfigBase {
	
	public String R2R_ID = "";
	private Hashtable<String,String> _ConfigCollection = new Hashtable<String,String>();
	
	@Override
	public String toString() {
		String info = getClass().getSimpleName()
				+ "configCollection:[";
		for (String name : _ConfigCollection.keySet()) {
			info += "(" + name + ":" + _ConfigCollection.get(name) + ")";
		}

		info += " ]}";

		return info;
	}
	
	public String Put(String Name,String Value){
		String result = "";
		if(!this._ConfigCollection.containsKey(Name)){
			this._ConfigCollection.put(Name, Value);
		}else{
			this._ConfigCollection.replace(Name, Value);
		}
		return result;
	}
	
	public String Get(String Name){
		String result = "";
		if(this._ConfigCollection.containsKey(Name)){
			result = this._ConfigCollection.get(Name);
		}
		return result;
	}
}
