package com.innlux.models.bc;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;

@Entity("setrecipebodyparameter") // 表名
public class SetRecipeBodyParameter {
	/** 
     * RecipeSplit
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
     * SubKey
     */  
    @Column("subkey")  
    private String SubKey; 
	
	public String getSubKey() {  
        return SubKey;  
    }  
  
    public void setSubKey(String SubKey) {  
        this.SubKey = SubKey;  
    }  
    
    /** 
     * ItemOffset
     */  
    @Column("itemoffset")  
    private String ItemOffset; 
	
	public String getItemOffset() {  
        return ItemOffset;  
    }  
  
    public void setItemOffset(String ItemOffset) {  
        this.ItemOffset = ItemOffset;  
    }  
    
    /** 
     * ItemName
     */  
    @Column("itemname")  
    private String ItemName; 
	
	public String getItemName() {  
        return ItemName;  
    }  
  
    public void setItemName(String ItemName) {  
        this.ItemName = ItemName;  
    }  
    
    /** 
     * ItemFormat
     */  
    @Column("itemformat")  
    private String ItemFormat; 
	
	public String getItemFormat() {  
        return ItemFormat;  
    }  
  
    public void setItemFormat(String ItemFormat) {  
        this.ItemFormat = ItemFormat;  
    }  
    
    /** 
     * ItemLength
     */  
    @Column("itemlength")  
    private String ItemLength; 
	
	public String getItemLength() {  
        return ItemLength;  
    }  
  
    public void setItemLength(String ItemLength) {  
        this.ItemLength = ItemLength;  
    }  
    
    /** 
     * ItemRate
     */  
    @Column("itemrate")  
    private String ItemRate; 
	
	public String getItemRate() {  
        return ItemRate;  
    }  
  
    public void setItemRate(String ItemRate) {  
        this.ItemRate = ItemRate;  
    }  
    
    /** 
     * ItemEfflen
     */  
    @Column("itemefflen")  
    private String ItemEfflen; 
	
	public String getItemEfflen() {  
        return ItemEfflen;  
    }  
  
    public void setItemEfflen(String ItemEfflen) {  
        this.ItemEfflen = ItemEfflen;  
    }  
    
    /** 
     * ItemSigned
     */  
    @Column("itemsigned")  
    private String ItemSigned; 
	
	public String getItemSigned() {  
        return ItemSigned;  
    }  
  
    public void setItemSigned(String ItemSigned) {  
        this.ItemSigned = ItemSigned;  
    }  
    
    @Override  
    public String toString() {  
        return 
        		" RecipeSplit: " + RecipeSplit+
        		" SubKey: " + SubKey+
        		" ItemOffset: " + ItemOffset+
        		" ItemName: " + ItemName+
        		" ItemFormat: " + ItemFormat+
        		" ItemLength: " + ItemLength+
        		" ItemRate: " + ItemRate+
        		" ItemEfflen: " + ItemEfflen+
        		" ItemSigned: " + ItemSigned;
    } 
    
}
