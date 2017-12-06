package com.innolux.R2R.autoFeedBack.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

// P33
@Entity("Canon_Exp_SiteNo_2_ScanNo") // table name
public class Canon_Exp_SiteNo_2_ScanNo {
	/* PrimaryKey, */  
    @Id("PrimaryKey,")  
    private String PrimaryKey; 
    public String getPrimaryKey() {return PrimaryKey;}
    public void setPrimaryKey(String PrimaryKey) {this.PrimaryKey = PrimaryKey;}  

    /* Product */  
    @Column("Product")  
    private String Product; 
    public String getProduct() {return Product;}
    public void setProduct(String Product) {this.Product = Product;}  
    
    /* Exp_Step_ID */  
    @Column("Exp_Step_ID")  
    private String Exp_Step_ID; 
    public String getExp_Step_ID() {return Exp_Step_ID;}
    public void setExp_Step_ID(String Exp_Step_ID) {this.Exp_Step_ID = Exp_Step_ID;}  
    
    /* Site_No */  
    @Column("Site_No")  
    private String Site_No; 
    public String getSite_No() {return Site_No;}
    public void setSite_No(String Site_No) {this.Site_No = Site_No;}  
        
    /* Scan_No */  
    @Column("Scan_No")  
    private String Scan_No; 
    public String getScan_No() {return Scan_No;}
    public void setScan_No(String Scan_No) {this.Scan_No = Scan_No;}  
    
    
    @Override  
    public String toString() {  
        return " Product: " + Product +
        		" Exp_Step_ID: " + Exp_Step_ID +
        		" Site_No: " + Site_No +
                " Scan_No: " + Scan_No;
    } 
}
