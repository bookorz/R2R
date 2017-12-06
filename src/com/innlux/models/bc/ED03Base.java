package com.innlux.models.bc;

public class ED03Base {

	private int ReportType;
	public int getReportType() {  
        return ReportType;  
    }  
  
    public void setReportType(int ReportType) {  
        this.ReportType = ReportType;  
    }  
    private int UnitNo;
	public int getUnitNo() {  
        return UnitNo;  
    }  
  
    public void setUnitNo(int UnitNo) {  
        this.UnitNo = UnitNo;  
    }  
    private int PageNo;
	public int getPageNo() {  
        return PageNo;  
    }  
  
    public void setPageNo(int PageNo) {  
        this.PageNo = PageNo;  
    }  
    private int RecipeNo;
	public int getRecipeNo() {  
        return RecipeNo;  
    }  
  
    public void setRecipeNo(int RecipeNo) {  
        this.RecipeNo = RecipeNo;  
    }  
    private String DataItem;
	public String getDataItem() {  
        return DataItem;  
    }  
  
    public void setDataItem(String DataItem) {  
        this.DataItem = DataItem;  
    }  
    private String Index;
	public String getIndex() {  
        return Index;  
    }  
  
    public void setIndex(String Index) {  
        this.Index = Index;  
    }  

	@Override  
    public String toString() {  
        return 
        		" ReportType: " + ReportType+
        		" UnitNo: " + UnitNo+
        		" PageNo: " + PageNo+
        		" RecipeNo: " + RecipeNo+
        		" DataItem: " + DataItem+
        		" Index: " + Index;   		
    } 

}
