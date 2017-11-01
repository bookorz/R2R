package com.innolux.R2R.common.base;

public class BCNodeBase {
	public int BCNo = 0;
	public int BCLineNo = 0;
	public int FabType = 0;
	public int NodeNo = 0;
	public int UnitNo = 0;
    public String EQName = "";
    
    @Override
	public String toString() {
		return getClass().getSimpleName() + "{BCNo:" + BCNo + "," + "BCLineNo:" + BCLineNo + "," + "FabType:" + FabType + "," + "NodeNo:" + NodeNo + "," + "EQName:" + EQName + "}";
	}
}
