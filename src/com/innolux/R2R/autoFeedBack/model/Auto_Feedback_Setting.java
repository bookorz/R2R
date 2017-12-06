package com.innolux.R2R.autoFeedBack.model;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;
import com.innolux.annotation.Id;

//p28
public class Auto_Feedback_Setting {
	/* PrimaryKey, */  
    @Id("PrimaryKey,")  
    private String PrimaryKey; 
    public String getPrimaryKey() {return PrimaryKey;}
    public void setPrimaryKey(String PrimaryKey) {this.PrimaryKey = PrimaryKey;}  

     /* Active_Flag */  
    @Column("Active_Flag")  
    private String Active_Flag; 
    public String getActive_Flag() {return Active_Flag;}
    public void setActive_Flag(String Active_Flag) {this.Active_Flag = Active_Flag;}  

    /* Eqp_ID */  
    @Column("Eqp_ID")  
    private String Eqp_ID; 
    public String getEqp_ID() {return Eqp_ID;}
    public void setEqp_ID(String Eqp_ID) {this.Eqp_ID = Eqp_ID;}  

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

    /* Exp_Step_ID */  
    @Column("Exp_Step_ID")  
    private String Exp_Step_ID; 
    public String getExp_Step_ID() {return Exp_Step_ID;}
    public void setExp_Step_ID(String Exp_Step_ID) {this.Exp_Step_ID = Exp_Step_ID;}  

    /* Mea_Step_ID */  
    @Column("Mea_Step_ID")  
    private String Mea_Step_ID; 
    public String getMea_Step_ID() {return Mea_Step_ID;}
    public void setMea_Step_ID(String Mea_Step_ID) {this.Mea_Step_ID = Mea_Step_ID;}  

 	/* Mea_Rcp */  
    @Column("Mea_Rcp")  
    private String Mea_Rcp; 
    public String getMea_Rcp() {return Mea_Rcp;}
    public void setMea_Rcp(String Mea_Rcp) {this.Mea_Rcp = Mea_Rcp;}  

    /* Feedback_Mode */  
    @Column("Feedback_Mode")  
    private String Feedback_Mode; 
    public String getFeedback_Mode() {return Feedback_Mode;}
    public void setFeedback_Mode(String Feedback_Mode) {this.Feedback_Mode = Feedback_Mode;}  

    /* U_Upper_Limit */  
    @Column("U_Upper_Limit")  
    private String U_Upper_Limit; 
    public String getU_Upper_Limit() {return U_Upper_Limit;}
    public void setU_Upper_Limit(String U_Upper_Limit) {this.U_Upper_Limit = U_Upper_Limit;} 

    /* L_Upper_Limit */  
    @Column("L_Upper_Limit")  
    private String L_Upper_Limit; 
    public String getL_Upper_Limit() {return L_Upper_Limit;}
    public void setL_Upper_Limit(String L_Upper_Limit) {this.L_Upper_Limit = L_Upper_Limit;} 

    /* U_Lower_Limit */  
    @Column("U_Lower_Limit")  
    private String U_Lower_Limit; 
    public String getU_Lower_Limit() {return U_Lower_Limit;}
    public void setU_Lower_Limit(String U_Lower_Limit) {this.U_Lower_Limit = U_Lower_Limit;} 

    /* L_Lower_Limit */  
    @Column("L_Lower_Limit")  
    private String L_Lower_Limit; 
    public String getL_Lower_Limit() {return L_Lower_Limit;}
    public void setL_Lower_Limit(String L_Lower_Limit) {this.L_Lower_Limit = L_Lower_Limit;} 

    /* Ratio */  
    @Column("Ratio")  
    private String Ratio; 
    public String getRatio() {return Ratio;}
    public void setRatio(String Ratio) {this.Ratio = Ratio;} 

    /* Sigma */  
    @Column("Sigma")  
    private String Sigma; 
    public String getSigma() {return Sigma;}
    public void setSigma(String Sigma) {this.Sigma = Sigma;} 

    /* Active_Rule */  
    @Column("Active_Rule")  
    private String Active_Rule; 
    public String getActive_Rule() {return Active_Rule;}
    public void setActive_Rule(String Active_Rule) {this.Active_Rule = Active_Rule;}

    /* Expire_Time */  
    @Column("Expire_Time")  
    private String Expire_Time; 
    public String getExpire_Time() {return Expire_Time;}
    public void setExpire_Time(String Expire_Time) {this.Expire_Time = Expire_Time;}

    @Override  
    public String toString() {  
        return " Product: " + Product +
        		" Exp_Step_ID: " + Exp_Step_ID +
        		" Site_No: " + Site_No +
                " Scan_No: " + Scan_No + 
                " Update_Time: " + Update_Time + 
                " Update_UserID: " + Update_UserID;
    }
}
