package com.innolux.R2R.common.base;

import java.util.Hashtable;
import java.util.Vector;

public class RegulationCollection {
	
	public String BcName = "";
	public String EqpName = "";
			
	private Hashtable<Integer, String> RegulationCollection = new Hashtable<Integer, String>();

	@Override
	public String toString() {
		String info = getClass().getSimpleName() + "{RegulationCollection:[";
		for (int word : RegulationCollection.keySet()) {
			info += "(" + word + ":" + RegulationCollection.get(word) + ")";
		}
		info += " ]}";

		return info;
	}

	public void StoreRegulation(int word, String hexStr) {
		word = word -1;
		if (!RegulationCollection.containsKey(word)) {
			RegulationCollection.put(word, hexStr);
		}else{
			RegulationCollection.replace(word, hexStr);
		}
	}

	public String GetRegulation(int word) {
		word = word -1;
		String result = "";
		if (RegulationCollection.containsKey(word)) {
			result = RegulationCollection.get(word);
		}
		return result;
	}
	
	public int GetRegulationCount(){
		return RegulationCollection.size();
	}

}
