package com.innolux.models.bc;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;

@Entity("io_event") // 表名
public class IO_Event {
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
     * BC node no
     */  
    @Column("nodeno")  
    private String NodeNo; 
	
	public String getNodeNo() {  
        return NodeNo;  
    }  
  
    public void setNodeNo(String NodeNo) {  
        this.NodeNo = NodeNo;  
    }  
    
    /** 
     * Funckey
     */  
    @Column("funckey")  
    private String Funckey; 
	
	public String getFunckey() {  
        return Funckey;  
    }  
  
    public void setFunckey(String Funckey) {  
        this.Funckey = Funckey;  
    }  
    
    /** 
     * Start address
     */  
    @Column("startadr")  
    private String StartAddr; 
	
	public String getStartAddr() {  
        return StartAddr;  
    }  
  
    public void setStartAddr(String StartAddr) {  
        this.StartAddr = StartAddr;  
    }  
    
    /** 
     * Data length
     */  
    @Column("datalen")  
    private String DataLength; 
	
	public String getDataLength() {  
        return DataLength;  
    }  
  
    public void setDataLength(String DataLength) {  
        this.DataLength = DataLength;  
    }  
    
    @Override  
    public String toString() {  
        return 
        		" BCNo: " + BCNo+
        		" LineNo: " + LineNo+
        		" FabType: " + FabType+
        		" NodeNo: " + NodeNo+
        		" Funckey: " + Funckey+
        		" StartAddr: " + StartAddr+
        		" DataLength: " + DataLength;   		
    } 
}
