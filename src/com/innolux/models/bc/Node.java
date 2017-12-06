package com.innolux.models.bc;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

@Entity("main_bc_node") // 表名
public class Node {
	/** 
     * BC SubEqpID
     */  
	@Id("hostsubeqid")  
    private String SubEqpID; 
	
	public String getSubEqpID() {  
        return SubEqpID;  
    }  
  
    public void setSubEqpID(String SubEqpID) {  
        this.SubEqpID = SubEqpID;  
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
     * BC node no
     */  
    @Column("nodeno")  
    private String BCNodeNo; 
	
	public String getBCNodeNo() {  
        return BCNodeNo;  
    }  
  
    public void setBCNodeNo(String BCNodeNo) {  
        this.BCNodeNo = BCNodeNo;  
    }  
    
    /** 
     * BC node RecipeSplit
     */  
    @Column("recipesplit")  
    private String RecipeSplit; 
	
	public String getRecipeSplit() {  
        return RecipeSplit;  
    }  
  
    public void setRecipeSplit(String RecipeSplit) {  
        this.RecipeSplit = RecipeSplit;  
    }  
    
    /** 
     * BC LineID
     */  
    @Column("hostlineid1")  
    private String LineID; 
	
	public String getLineID() {  
        return LineID;  
    }  
  
    public void setLineID(String LineID) {  
        this.LineID = LineID;  
    }  
    
    @Override  
    public String toString() {  
        return " SubEqpID: " + SubEqpID+
        		" BCNo: " + BCNo+
        		" BCLineNo: " + LineNo+
        		" BCFabType: " + FabType+
        		" BCNodeNo: " + BCNodeNo+
        		" RecipeSplit: " + RecipeSplit+
        		" LineID: " + LineID;   		
    } 
    
}
