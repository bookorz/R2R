package com.innolux.models.bc;

import com.innolux.annotation.Column;
import com.innolux.annotation.Entity;

@Entity("trxbcgetcmd") // 表名
public class BC_Cmmand {
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
     * CmdKey
     */  
    @Column("cmdkey")  
    private String CmdKey; 
	
	public String getCmdKey() {  
        return CmdKey;  
    }  
  
    public void setCmdKey(String CmdKey) {  
        this.CmdKey = CmdKey;  
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
     * EqPortNo
     */  
    @Column("eqportno")  
    private String EqPortNo; 
	
	public String getEqPortNo() {  
        return EqPortNo;  
    }  
  
    public void setEqPortNo(String EqPortNo) {  
        this.EqPortNo = EqPortNo;  
    }  
    
    /** 
     * CmdData
     */  
    @Column("cmddata")  
    private String CmdData; 
	
	public String getCmdData() {  
        return CmdData;  
    }  
  
    public void setCmdData(String CmdData) {  
        this.CmdData = CmdData;  
    }  
    
    /** 
     * OpName
     */  
    @Column("opname")  
    private String OpName; 
	
	public String getOpName() {  
        return OpName;  
    }  
  
    public void setOpName(String OpName) {  
        this.OpName = OpName;  
    }  
    
    /** 
     * CmdType
     */  
    @Column("cmdtype")  
    private String CmdType; 
	
	public String getCmdType() {  
        return CmdType;  
    }  
  
    public void setCmdType(String CmdType) {  
        this.CmdType = CmdType;  
    }  
    
    /** 
     * UpdateTime
     */  
    @Column("updatetime")  
    private String UpdateTime; 
	
	public String getUpdateTime() {  
        return UpdateTime;  
    }  
  
    public void setUpdateTime(String UpdateTime) {  
        this.UpdateTime = UpdateTime;  
    }  
    
    /** 
     * UpdateOp
     */  
    @Column("updateop")  
    private String UpdateOp; 
	
	public String getUpdateOp() {  
        return UpdateOp;  
    }  
  
    public void setUpdateOp(String UpdateOp) {  
        this.UpdateOp = UpdateOp;  
    }  
    
    @Override  
    public String toString() {  
        return 
        		" BCNo: " + BCNo+
        		" LineNo: " + LineNo+
        		" FabType: " + FabType+
        		" CmdKey: " + CmdKey+
        		" NodeNo: " + NodeNo+
        		" EqPortNo: " + EqPortNo+
        		" CmdData: " + CmdData+
        		" OpName: " + OpName+
        		" CmdType: " + CmdType+
        		" UpdateTime: " + UpdateTime+
        		" UpdateOp: " + UpdateOp;   		
    } 
    
}
