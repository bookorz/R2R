package com.innolux.models.bc;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

@Entity("main_bc_unit") // 表名
public class Unit {
	/** 
     * BC UnitEqpID
     */  
	@Id("hostunitid")  
    private String UnitEqpID; 
	
	public String getUnitEqpID() {  
        return UnitEqpID;  
    }  
  
    public void setUnitEqpID(String UnitEqpID) {  
        this.UnitEqpID = UnitEqpID;  
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
     * BC unit no
     */  
    @Column("hostunitid")  
    private String UnitNo; 
	
	public String getUnitNo() {  
        return UnitNo;  
    }  
  
    public void setUnitNo(String UnitNo) {  
        this.UnitNo = UnitNo;  
    }  
    
    /** 
     * BC Unit RecipeSplit
     */  
    @Column("recipesplit")  
    private String RecipeSplit; 
	
	public String getRecipeSplit() {  
        return RecipeSplit;  
    }  
  
    public void setRecipeSplit(String RecipeSplit) {  
        this.RecipeSplit = RecipeSplit;  
    }  
    
    
    
    @Override  
    public String toString() {  
        return " UnitEqpID: " + UnitEqpID+
        		" BCNo: " + BCNo+
        		" LineNo: " + LineNo+
        		" FabType: " + FabType+
        		" NodeNo: " + NodeNo+
        		" UnitNo: " + UnitNo+
        		" RecipeSplit: " + RecipeSplit;   		
    } 
}
