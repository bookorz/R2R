package com.innolux.models.bc;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

@Entity("main_bc_line") // 表名
public class Line {
	
	 /** 
     * BC node RecipeSplit
     */  
    @Id("hostlineid")  
    private String LineID; 
	
	public String getLineID() {  
        return LineID;  
    }  
  
    public void setLineID(String LineID) {  
        this.LineID = LineID;  
    }  
	
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
    private String BCLineNo; 
	
	public String getBCLineNo() {  
        return BCLineNo;  
    }  
  
    public void setBCLineNo(String BCLineNo) {  
        this.BCLineNo = BCLineNo;  
    }  
    
    /** 
     * BC fab type
     */  
    @Column("fabtype")  
    private String BCFabType; 
	
	public String getBCFabType() {  
        return BCFabType;  
    }  
  
    public void setBCFabType(String BCFabType) {  
        this.BCFabType = BCFabType;  
    }  
    
    @Override  
    public String toString() {  
        return " LineID: " + LineID+
        		" BCNo: " + BCNo+
        		" BCLineNo: " + BCLineNo+
        		" BCFabType: " + BCFabType;   		
    } 
}
