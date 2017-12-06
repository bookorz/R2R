package com.innolux.R2R.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;

@Entity("MeasureFileData") // 表名
public class MeasureFileData {
	/** 
     * EqpId
     */  
    @Column("EqpId")  
    private String EqpId; 
	
	public String getEqpId() {  
        return EqpId;  
    }  
  
    public void setEqpId(String EqpId) {  
        this.EqpId = EqpId;  
    }  
    
    /** 
     * Recipe
     */  
    @Column("Recipe")  
    private String Recipe; 
	
	public String getRecipe() {  
        return Recipe;  
    }  
  
    public void setRecipe(String Recipe) {  
        this.Recipe = Recipe;  
    }  
    
    /** 
     * PreEqpId
     */  
    @Column("PreEqpId")  
    private String PreEqpId; 
	
	public String getPreEqpId() {  
        return PreEqpId;  
    }  
  
    public void setPreEqpId(String PreEqpId) {  
        this.PreEqpId = PreEqpId;  
    }  
    
    /** 
     * PreEqpRecipe
     */  
    @Column("PreEqpRecipe")  
    private String PreEqpRecipe; 
	
	public String getPreEqpRecipe() {  
        return PreEqpRecipe;  
    }  
  
    public void setPreEqpRecipe(String PreEqpRecipe) {  
        this.PreEqpRecipe = PreEqpRecipe;  
    }  
    
    /** 
     * FileName
     */  
    @Column("FileName")  
    private String FileName; 
	
	public String getFileName() {  
        return FileName;  
    }  
  
    public void setFileName(String FileName) {  
        this.FileName = FileName;  
    }  
    
    /** 
     * HeaderName
     */  
    @Column("HeaderName")  
    private String HeaderName; 
	
	public String getHeaderName() {  
        return HeaderName;  
    }  
  
    public void setHeaderName(String HeaderName) {  
        this.HeaderName = HeaderName;  
    }  
    
    /** 
     * RowIndex
     */  
    @Column("RowIndex")  
    private long RowIndex; 
	
	public long getRowIndex() {  
        return RowIndex;  
    }  
  
    public void setRowIndex(long RowIndex) {  
        this.RowIndex = RowIndex;  
    }  
    
    /** 
     * RowData
     */  
    @Column("RowData")  
    private long RowData; 
	
	public long getRowData() {  
        return RowData;  
    }  
  
    public void setRowData(long RowData) {  
        this.RowData = RowData;  
    }  
    @Override  
    public String toString() {  
        return 
        		" EqpId: " + EqpId+
        		" Recipe: " + Recipe+
        		" PreEqpId: " + PreEqpId+
        		" PreEqpRecipe: " + PreEqpRecipe+
        		" FileName: " + FileName+
        		" HeaderName: " + HeaderName+
        		" RowIndex: " + RowIndex+
        		" RowData: " + RowData;   		
    } 
}
