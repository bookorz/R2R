package com.innlux.models.bc;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;

@Entity("plcdata") // 表名
public class PLC_Data {
	/** 
     * BC number
     */  
    @Column("bcno")  
    private String BCNo; 
	
	public String getBCNo() {  
        return BCNo;  
    }  
  
    public void setBCNo(String BCNo) {  
        this.BCNo = BCNo;  
    }  
    
    /** 
     * BC line number
     */  
    @Column("bclineno")  
    private String LineNo; 
	
	public String getLineNo() {  
        return LineNo;  
    }  
  
    public void setLineNo(String LineNo) {  
        this.LineNo = LineNo;  
    }  
    
    /** 
     * BC fab type
     */  
    @Column("fabtype")  
    private String FabType; 
	
	public String getFabType() {  
        return FabType;  
    }  
  
    public void setFabType(String FabType) {  
        this.FabType = FabType;  
    }  
    
    /** 
     * DeviceType
     */  
    @Column("devicetype")  
    private String DeviceType; 
	
	public String getDeviceType() {  
        return DeviceType;  
    }  
  
    public void setDeviceType(String DeviceType) {  
        this.DeviceType = DeviceType;  
    }  
    
    /** 
     * OutPutData
     */  
    @Column("outputdata")  
    private String OutPutData; 
	
	public String getOutPutData() {  
        return OutPutData;  
    }  
  
    public void setOutPutData(String OutPutData) {  
        this.OutPutData = OutPutData;  
    }  
    
    @Override  
    public String toString() {  
        return 
        		" BCNo: " + BCNo+
        		" LineNo: " + LineNo+
        		" FabType: " + FabType+
        		" DeviceType: " + DeviceType+
        		" OutPutData: " + OutPutData;   		
    } 
    
}
