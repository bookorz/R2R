package com.innolux.R2R.common.base;

import java.util.Hashtable;

public class PDSBase {
	public String ClassName = "";
	public String Equipment_ID = "";
	public String SubEquipment_ID = "";
	public String PPID = "";
	public String Component_ID = "";
	public String Lot_Type = "";
	public String TimeStamp = "";
	private Hashtable<String, String> Parameters = new Hashtable<String, String>();
	
	@Override
	public String toString() {
		String info = getClass().getSimpleName() + "{ClassName:" + ClassName + "," + "Equipment_ID:" + Equipment_ID
				+ "," + "SubEquipmwnt_ID:" + SubEquipment_ID + ","+ "PPID:" + PPID + "," + "Component_ID:" + Component_ID + "," + "Lot_Type:"
				+ Lot_Type + "," + "Parameters:[";
		for (String name : Parameters.keySet()) {
			info += "(" + name + ":" + Parameters.get(name) + ")";
		}

		info += " ]}";

		return info;
	}
	
	public void StoreParameter(String name, String value) {
		if (!Parameters.containsKey(name)) {
			Parameters.put(name, value);
		}
	}

	public String GetParameter(String paramName) {
		String result = "";
		if (Parameters.containsKey(paramName)) {
			result = Parameters.get(paramName);
		}
		return result;
	}
}
