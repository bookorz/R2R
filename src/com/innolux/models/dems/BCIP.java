package com.innolux.models.dems;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

@Entity("dems_bcip") // 表名
public class BCIP {
	/** 
     * BC Name
     */  
	@Id("bc_name")  
    private String BC_Name; 
	
	public String getBC_Name() {  
        return BC_Name;  
    }  
  
    public void setBC_Name(String BC_Name) {  
        this.BC_Name = BC_Name;  
    }  
    
    /** 
     * BC IP
     */  
    @Column("ip")  
    private String IP; 
	
	public String getIP() {  
        return IP;  
    }  
  
    public void setIP(String IP) {  
        this.IP = IP;  
    }  
    
    @Override  
    public String toString() {  
        return " BC_Name: " + BC_Name+
        		" IP: " + IP;   		
    } 
}
