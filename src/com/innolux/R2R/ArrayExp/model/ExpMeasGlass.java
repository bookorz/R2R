package com.innolux.R2R.ArrayExp.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.MeasureFileDataBase;

/* exposure and measure machine glass class
 * 
 */
public class ExpMeasGlass {
	String productName; // mandatory
	String expID; // mandatory
	String expRcpID; // mandatory
	String measStepID; // mandatory
	String measRcpID; // mandatory
	int adcOrFdc; // mandatory

	String glassID;
	
	String expSupplier; // Nikon or Canon
	String expStepID; 
	String expRcpName;
	
	public static final int OL = 1;
	public static final int DOL = 2; 
	public static final int ADC = 1; 
	public static final int FDC = 2; 
	
	int olOrDol;
	
	Vector2D minOL;
	Vector2D maxOL;
	Vector2D minDOL;
	Vector2D maxDOL;

	double ratio;

	long exposureTime;
	List<Vector2D> measPointList;
	
	// 240point ArrayList:  average, sigma
	// method: 座標轉換

	public ExpMeasGlass(){
		this.measPointList = new ArrayList<Vector2D>();
		this.adcOrFdc = 0;
	}

	public String getGlassID() {
		return glassID;
	}

	public void setGlassID(String glassID) {
		this.glassID = glassID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getExpSupplier() {
		return expSupplier;
	}

	public long getExposureTime() {
		return exposureTime;
	}

	public void setExposureTime(long exposureTime) {
		this.exposureTime = exposureTime;
	}

	public void setExpSupplier(String expSupplier) {
		this.expSupplier = expSupplier;
	}

	public String getExpID() {
		return expID;
	}

	public void setExpID(String expID) {
		this.expID = expID;
	}
	
	public String getExpStepID() {
		return expStepID;
	}

	public void setExpStepID(String expStepID) {
		this.expStepID = expStepID;
	}
	
	public String getExpRcpID() {
		return expRcpID;
	}

	public void setExpRcpID(String expRcpID) {
		this.expRcpID = expRcpID;
	}

	public String getExpRcpName() {
		return expRcpName;
	}

	public void setExpRcpName(String expRcpName) {
		this.expRcpName = expRcpName;
	}

	public String getMeasStepID() {
		return measStepID;
	}

	public void setMeasStepID(String measStepID) {
		this.measStepID = measStepID;
	}

	public String getMeasRcpID() {
		return measRcpID;
	}

	public void setMeasRcpID(String measRcpID) {
		this.measRcpID = measRcpID;
	}

	public int getOlOrDol() {
		return olOrDol;
	}

	public void setOlOrDol(int olOrDol) {
		this.olOrDol = olOrDol;
	}

	public int getAdcOrFdc() {
		return this.adcOrFdc;
	}

	public void setAdcOrFdc(int adcOrFdc) {
		this.adcOrFdc = adcOrFdc;
	}

	public Vector2D getMinOL() {
		return minOL;
	}

	public void setMinOL(Vector2D minOL) {
		this.minOL = minOL;
	}

	public Vector2D getMaxOL() {
		return maxOL;
	}

	public void setMaxOL(Vector2D maxOL) {
		this.maxOL = maxOL;
	}

	public Vector2D getMinDOL() {
		return minDOL;
	}

	public void setMinDOL(Vector2D minDOL) {
		this.minDOL = minDOL;
	}

	public Vector2D getMaxDOL() {
		return maxDOL;
	}

	public void setMaxDOL(Vector2D maxDOL) {
		this.maxDOL = maxDOL;
	}

	public double getRatio() {
		return ratio;
	}

	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public List<Vector2D> getMeasPointList() {
		return measPointList;
	}

	public void setMeasPointList(List<Vector2D> measPointList) {
		this.measPointList = measPointList;
	}
	
	private static Logger logger = Logger.getLogger(ExpMeasGlass.class);
	public static ExpMeasGlass csv2ExpMeasGlass(MeasureFileDataBase csv){
		ExpMeasGlass aGlass = new ExpMeasGlass();
		try {
			
			aGlass.setGlassID(csv.FetchValue("GLASS_DATA", "Glass_ID"));
			aGlass.setProductName(csv.FetchValue("GLASS_DATA", "Product"));
			
			aGlass.setExpID(csv.FetchValue("PDS_GLASS_DATA", "PreEQ_ID_1"));
			aGlass.setExpStepID(csv.FetchValue("PDS_GLASS_DATA", "PreStep_Seq_1"));

			T_EqGroup2EqID eqGroup2EqID = T_EqGroup2EqID_CRUD.read(aGlass.getExpID());
			aGlass.setExpSupplier(eqGroup2EqID.getEqGroup()); // Nikon or Canon
			
			aGlass.setExpRcpID(csv.FetchValue("PDS_GLASS_DATA", "PreRecipe_ID_1"));
			
			T_ExpRcpID2Name expRcpID2Name = T_ExpRcpID2Name_CRUD.read(aGlass.getProductName(), 
																		aGlass.getExpRcpID());
			aGlass.setExpRcpName(expRcpID2Name.getExpRcpName());
			
			aGlass.setMeasStepID(csv.FetchValue("GLASS_DATA", "Step_Seq"));
			aGlass.setMeasRcpID(csv.FetchValue("GLASS_DATA", "Recipe_ID"));
			
			Vector2D minOL = new Vector2D();

			double value = Double.parseDouble(csv.getCsvValByRowCol("GLASS_SUMMARY", "Statistic", "Min", "OL01"));
			Utility.checkErrorAndLog(value, "getCsvValByRowCol(csv, Min, OL01) = -1", -1);
			minOL.setxValue(value);

			value = Double.parseDouble(csv.getCsvValByRowCol("GLASS_SUMMARY", "Statistic", "Min", "OL02"));
			Utility.checkErrorAndLog(value, "getCsvValByRowCol(csv, Min, OL02) = -1", -1);
			minOL.setyValue(value);

			Vector2D maxOL = new Vector2D();
			value = Double.parseDouble(csv.getCsvValByRowCol("GLASS_SUMMARY", "Statistic", "Max", "OL01"));
			Utility.checkErrorAndLog(value, "getCsvValByRowCol(csv, Max, OL01) = -1", -1);
			maxOL.setxValue(value);
			
			value = Double.parseDouble(csv.getCsvValByRowCol("GLASS_SUMMARY", "Statistic", "Max", "OL02"));
			Utility.checkErrorAndLog(value, "getCsvValByRowCol(csv, Max, OL02) = -1", -1);
			maxOL.setyValue(value);

			Vector2D minDOL = new Vector2D();
			value = Double.parseDouble(csv.getCsvValByRowCol("GLASS_SUMMARY", "Statistic", "Min", "DOL01"));
			Utility.checkErrorAndLog(value, "getCsvValByRowCol(csv, Min, DOL01) = -1", -1);
			minDOL.setxValue(value);
			
			value = Double.parseDouble(csv.getCsvValByRowCol("GLASS_SUMMARY", "Statistic", "Min", "DOL02"));
			Utility.checkErrorAndLog(value, "getCsvValByRowCol(csv, Min, DOL02) = -1", -1);
			minDOL.setyValue(value);

			Vector2D maxDOL = new Vector2D();
			value = Double.parseDouble(csv.getCsvValByRowCol("GLASS_SUMMARY", "Statistic", "Max", "DOL01"));
			Utility.checkErrorAndLog(value, "getCsvValByRowCol(csv, Max, DOL01) = -1", -1);
			maxDOL.setxValue(value);
			
			value = Double.parseDouble(csv.getCsvValByRowCol("GLASS_SUMMARY", "Statistic", "Max", "DOL02"));
			Utility.checkErrorAndLog(value, "getCsvValByRowCol(csv, Max, DOL02) = -1", -1);
			maxDOL.setyValue(value);
			
			if(aGlass.getExpSupplier().equals("Canon")) {
				aGlass.setAdcOrFdc(ExpMeasGlass.ADC);
			}

			int olOrDol = checkOlOrDol(aGlass, minOL, maxOL, minDOL, maxDOL);
			aGlass.setOlOrDol(olOrDol); // both ADC & FDC is OL

			T_AutoFeedbackSetting autoFeedbackSetting = T_AutoFeedbackSetting_CRUD.read(aGlass.getProductName(), 
																						aGlass.getExpID(), 
																						aGlass.getExpRcpID(), 
																						aGlass.getMeasStepID(), 
																						aGlass.getMeasRcpID(), 
																						aGlass.getAdcOrFdc());
			aGlass.setRatio(autoFeedbackSetting.getRatio());

			SimpleDateFormat simDateFormat = new SimpleDateFormat();
			simDateFormat.applyPattern( "yyyyMMdd HHmmssSSS");
			Date eTime = simDateFormat.parse(csv.FetchValue("PDS_GLASS_DATA", "PreEnd_Time_1"));		
			aGlass.setExposureTime(eTime.getTime());
			
			List<Vector2D> csvMeasPotList = getCsvMeasPointList(csv, "Coord_X", "Coord_Y", "OL01", "OL02");
			Utility.checkErrorAndLog(csvMeasPotList, "cannot find csvMeasPotList", null);
			aGlass.setMeasPointList(csvMeasPotList);
			
		}catch(Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return aGlass;
	}
	
	private static int checkOlOrDol(ExpMeasGlass aGlass, Vector2D minOL, Vector2D maxOL, Vector2D minDOL, Vector2D maxDOL){
		// return the correct feedback type: OL or DOL
		T_AutoFeedbackSetting autoFbkSeting = T_AutoFeedbackSetting_CRUD.read(aGlass.getProductName(), 
																						aGlass.getExpID(), 
																						aGlass.getExpRcpID(), 
																						aGlass.getMeasStepID(), 
																						aGlass.getMeasRcpID(), 
																						aGlass.getAdcOrFdc());
		if(autoFbkSeting.getLUpperLimit() <= maxDOL.getxValue() && maxDOL.getxValue() <= autoFbkSeting.getUUpperLimit() &&
			autoFbkSeting.getLLowerLimit() <= minDOL.getxValue() && minDOL.getxValue() <= autoFbkSeting.getULowerLimit() && 
			autoFbkSeting.getLUpperLimit() <= maxDOL.getyValue() && maxDOL.getyValue() <= autoFbkSeting.getUUpperLimit() &&
			autoFbkSeting.getLLowerLimit() <= minDOL.getyValue() && minDOL.getyValue() <= autoFbkSeting.getULowerLimit()){
				return ExpMeasGlass.DOL;
		}else if(autoFbkSeting.getLUpperLimit() <= maxOL.getxValue() && maxOL.getxValue() <= autoFbkSeting.getUUpperLimit() &&
				autoFbkSeting.getLLowerLimit() <= minOL.getxValue() && minOL.getxValue() <= autoFbkSeting.getULowerLimit() && 
				autoFbkSeting.getLUpperLimit() <= maxOL.getyValue() && maxOL.getyValue() <= autoFbkSeting.getUUpperLimit() &&
				autoFbkSeting.getLLowerLimit() <= minOL.getyValue() && minOL.getyValue() <= autoFbkSeting.getULowerLimit()){
				return ExpMeasGlass.OL;
			}
		return 0;
	}
	
	private static List<Vector2D> getCsvMeasPointList(MeasureFileDataBase csv, String coordXStr, String coordYStr, 
			String ol01Str, String ol02Str){
			List<String> ol01List = csv.FetchList("SITE_DAT", ol01Str);
			List<String> ol02List = csv.FetchList("SITE_DAT", ol02Str);
			List<String> coordXList = csv.FetchList("SITE_DAT", coordXStr);
			List<String> coordYList = csv.FetchList("SITE_DAT", coordYStr);

			if(ol01List == null || ol02List == null || coordXList == null || coordYList == null){
				logger.error("ArrayExp getCsvMeasPointList: ol01List = null or ol02List = null or coordXList = null or coordYList = null");
				return null;
			}else if(ol01List.size() != ol02List.size()){
				logger.error("ArrayExp getCsvMeasPointList: ol01List.size() != ol02List.size()");
				return null;
			}else if(coordXList.size() != coordYList.size()){
				logger.error("ArrayExp getCsvMeasPointList: coordXList.size() != coordYList.size()");
				return null;
			}else if(ol01List.size() != coordXList.size()){
				logger.error("ArrayExp getCsvMeasPointList: ol01List.size() != coordXList.size()");
				return null;
			}

			List<Vector2D> vectorList = new ArrayList<>();
			Iterator iter = ol01List.listIterator();
			while(iter.hasNext()){
				Object valStr = iter.next();
				if(valStr.equals(""))
					continue;
				int ind = ol01List.indexOf(valStr);

				double xAxis = Double.parseDouble( coordXList.get(ind) );
				double yAxis = Double.parseDouble( coordYList.get(ind) );
				double xValue = Double.parseDouble( ol01List.get(ind) );
				double yValue = Double.parseDouble( ol02List.get(ind) );
				vectorList.add(new Vector2D(xAxis, yAxis, xValue, yValue));
			}
			return vectorList;
		}
	

}
