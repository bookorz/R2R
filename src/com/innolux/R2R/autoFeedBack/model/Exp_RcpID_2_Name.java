package com.innolux.R2R.autoFeedBack.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

//P32
@Entity("Exp_RcpID_2_RcpName") // table name
public class Exp_RcpID_2_Name {
	/* PrimaryKey, */  
    //@Id("PrimaryKey,")  
    private String PrimaryKey; 
    
   
	/* Product */  
    @Column("Product")  
    private String Product; 
	public String getProduct() {return Product;}
    public void setProduct(String Product) {this.Product = Product;}  
    
     /* Exp_Step_ID */  
    @Column("Exp_Step_ID")  
    private int Exp_Step_ID; 
    public String getExp_Step_ID() {return Exp_Step_ID;}
    public void setExp_Step_ID(String Exp_Step_ID) {this.Exp_Step_ID = Exp_Step_ID;}  

    /* Exp_Rcp_ID */  
    @Column("Exp_Rcp_ID")  
    private String Exp_Rcp_ID; 
	public String getExp_Rcp_ID() {return Exp_Rcp_ID;}
    public void setExp_Rcp_ID(String Exp_Rcp_ID) {this.Exp_Rcp_ID = Exp_Rcp_ID;}  
    
     /* Exp_Rcp_Name */  
    @Column("Exp_Rcp_Name")  
    private String Exp_Rcp_Name; 
    public String getExp_Rcp_Name() {return Exp_Rcp_Name;}
    public void setExp_Rcp_Name(String Exp_Rcp_Name) {this.Exp_Rcp_Name = Exp_Rcp_Name;}  

    @Override  
    public String toString() {  
        return " Product: " + Product +
        		" Exp_Step_ID: " + Exp_Step_ID +
        		" Exp_Rcp_ID: " + Exp_Rcp_ID +
                " Exp_Rcp_Name: " + Exp_Rcp_Name;
    }
}
