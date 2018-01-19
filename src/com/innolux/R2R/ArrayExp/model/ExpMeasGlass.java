package com.innolux.R2R.ArrayExp.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.MeasureFileDataBase;
import com.innolux.R2R.model.FeedbackTime;
import com.innolux.R2R.model.FeedbackTime_CRUD;
import com.innolux.services.MeasureFileReader;

/* exposure and measure machine glass class
 * 
 */
public class ExpMeasGlass {
	String productName; // mandatory
	String expID; // mandatory
	String expRcpID; // mandatory
	String measStepID; // mandatory
	String measRcpID; // mandatory
	String adcOrFdc; // mandatory

	String glassID;
	
	String expSupplier; // Nikon or Canon
	String expStepID; 
	String expRcpName;
	
	String measEqId;
	String measSubEqId;
	
	String olOrDol;
	
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

	public String getOlOrDol() {
		return olOrDol;
	}

	public void setOlOrDol(String olOrDol) {
		this.olOrDol = olOrDol;
	}

	public String getMeasEqId() {
		return measEqId;
	}

	public void setMeasEqId(String measEqId) {
		this.measEqId = measEqId;
	}

	public String getMeasSubEqId() {
		return measSubEqId;
	}

	public void setMeasSubEqId(String measSubEqId) {
		this.measSubEqId = measSubEqId;
	}
	
	public String getAdcOrFdc() {
		return this.adcOrFdc;
	}

	public void setAdcOrFdc(String adcOrFdc) {
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
	
	public String getSiteNoList() {
		String result = "";
		for (Vector2D vector2d: this.measPointList) {
			result += String.valueOf(vector2d.index) + ",";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}
	public String getOl01ListStr() {
		String result = "";
		for (Vector2D vector2d: this.measPointList) {
			result += String.valueOf(vector2d.xValue) + ",";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}
	public String getOl02ListStr() {
		String result = "";
		for (Vector2D vector2d: this.measPointList) {
			result += String.valueOf(vector2d.yValue) + ",";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}
	public String getCoordXListStr() {
		String result = "";
		for (Vector2D vector2d: this.measPointList) {
			result += String.valueOf(vector2d.xAxis) + ",";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}
	public String getCoordYListStr() {
		String result = "";
		for (Vector2D vector2d: this.measPointList) {
			result += String.valueOf(vector2d.yAxis) + ",";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}
	
	private static Logger logger = Logger.getLogger(ExpMeasGlass.class);

	public static ExpMeasGlass csv2ExpMeasGlass(MeasureFileDataBase csv){
		ExpMeasGlass aGlass = new ExpMeasGlass();
		try {
			
			String str1 = csv.FetchValue("GLASS_DATA", "Glass_ID");
			if (str1 == null || str1.equals("")) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "csv2ExpMeasGlass Error: FetchValue(GLASS_DATA, Glass_ID)");
				return null;
			}
			aGlass.setGlassID(str1);
			
			str1 = csv.FetchValue("GLASS_DATA", "Product");
			if (str1 == null || str1.equals("")) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "csv2ExpMeasGlass Error: FetchValue(GLASS_DATA, Product)");
				return null;
			}
			aGlass.setProductName(str1);
			
			str1 = csv.FetchValue("PDS_GLASS_DATA", "PreEQ_ID_1");
			if (str1 == null || str1.equals("")) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "csv2ExpMeasGlass Error: FetchValue(PDS_GLASS_DATA, PreEQ_ID_1)");
				return null;
			}
			aGlass.setExpID(str1);
			
			
			str1 = csv.FetchValue("PDS_GLASS_DATA", "PreStep_Seq_1");
			if (str1 == null || str1.equals("")) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "csv2ExpMeasGlass Error: FetchValue(PDS_GLASS_DATA, PreStep_Seq_1)");
				return null;
			}
			aGlass.setExpStepID(str1);
			
			T_EqGroup2EqID eqGroup2EqID;
			if (Utility.DEBUG) {
				eqGroup2EqID = new T_EqGroup2EqID();
				eqGroup2EqID.setEqGroup("NIKON");
			}else {
				eqGroup2EqID = T_EqGroup2EqID_CRUD.read(aGlass.getExpID());
				if (eqGroup2EqID == null) {
					Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "csv2ExpMeasGlass Error: cannot read data in T_EqGroup2EqID");
					return null;
				}
			}
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "csv2ExpMeasGlass: read T_EqGroup2EqID: eqGroup = " + eqGroup2EqID.getEqGroup());
			aGlass.setExpSupplier(eqGroup2EqID.getEqGroup()); // Nikon or Canon
			
			str1 = csv.FetchValue("PDS_GLASS_DATA", "PreRecipe_ID_1");
			if (str1 == null || str1.equals("")) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "csv2ExpMeasGlass Error: FetchValue(PDS_GLASS_DATA, PreRecipe_ID_1)");
				return null;
			}
			aGlass.setExpRcpID(str1);
			
			if (aGlass.getExpSupplier().toUpperCase().equals("NIKON")) {
				T_ExpRcpID2Name expRcpID2Name;
				if (Utility.DEBUG) {
					expRcpID2Name = new T_ExpRcpID2Name();
					expRcpID2Name.setExpRcpName("ExpRecipeName");
				}else {
					expRcpID2Name = T_ExpRcpID2Name_CRUD.read(aGlass.getProductName(),
																aGlass.getExpStepID(),
																aGlass.getExpRcpID());	
					if (expRcpID2Name == null) {
						Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "csv2ExpMeasGlass Error: cannot read data in T_ExpRcpID2Name");
						return null;
					}
				}
				aGlass.setExpRcpName(expRcpID2Name.getExpRcpName());
			}else {
				aGlass.setExpRcpName(" ");
			}
			
			
			str1 = csv.FetchValue("GLASS_DATA", "Step_Seq");
			if (str1 == null || str1.equals("")) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "csv2ExpMeasGlass Error: FetchValue(GLASS_DATA, Step_Seq)");
				return null;
			}
			aGlass.setMeasStepID(str1);
			
			str1 = csv.FetchValue("GLASS_DATA", "Recipe_ID");
			if (str1 == null || str1.equals("")) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "csv2ExpMeasGlass Error: FetchValue(GLASS_DATA, Recipe_ID)");
				return null;
			}
			aGlass.setMeasRcpID(str1);
			
			str1 = csv.FetchInfo("HEADER", "EQ_ID");
			if (str1 == null || str1.equals("")) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "csv2ExpMeasGlass Error: FetchValue(GLASS_DATA, EQ_ID)");
				return null;
			}
			aGlass.setMeasEqId(str1);
			
			str1 = csv.FetchInfo("HEADER", "SUBEQ_ID");
			if (str1 == null || str1.equals("")) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "csv2ExpMeasGlass Error: FetchValue(GLASS_DATA, SUBEQ_ID)");
				return null;
			}
			aGlass.setMeasSubEqId(str1);

			Vector2D minOL = new Vector2D();
			minOL =  ExpMeasGlass.getVectPointInGlass(csv, "Min", "OL");
			if (minOL == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "ExpMeasGlass.getVectPointInGlass Error minOL = null");
				return null;
			}
			Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "csv2ExpMeasGlass: get minOL(" + minOL.getxValue() + "," + minOL.getyValue() + ")");
			
			Vector2D maxOL = new Vector2D();
			maxOL =  ExpMeasGlass.getVectPointInGlass(csv, "Max", "OL");
			if (maxOL == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "ExpMeasGlass.getVectPointInGlass Error maxOL = null");
				return null;
			}
			Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "csv2ExpMeasGlass: get maxOL(" + minOL.getxValue() + "," + minOL.getyValue() + ")");
			
			Vector2D minDOL = new Vector2D();
			minDOL =  ExpMeasGlass.getVectPointInGlass(csv, "Min", "DOL");
			if (minDOL == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "ExpMeasGlass.getVectPointInGlass fail: minDOL = null");
			}
			else Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "csv2ExpMeasGlass: get minDOL(" + minOL.getxValue() + "," + minOL.getyValue() + ")");
			
			Vector2D maxDOL = new Vector2D();
			maxDOL =  ExpMeasGlass.getVectPointInGlass(csv, "Max", "DOL");
			if (maxDOL == null)
				Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "ExpMeasGlass.getVectPointInGlass fail: maxDOL = null");
			else Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "csv2ExpMeasGlass: get maxDOL(" + minOL.getxValue() + "," + minOL.getyValue() + ")");
			
			aGlass.setAdcOrFdc("NULL");
			if(aGlass.getExpSupplier().toUpperCase().equals("CANON")) {
				aGlass.setAdcOrFdc("ADC"); // maybe future need to edit
			}

			String olOrDol = checkOlOrDol(aGlass, minOL, maxOL, minDOL, maxDOL);
			if (olOrDol == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkOlOrDol Error: orOrDol = null");
				return null;
			}if (olOrDol.equals("")) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkOlOrDol Error: orOrDol = -1");
				return null;
			}if (!olOrDol.toUpperCase().equals("OL") && !olOrDol.toUpperCase().equals("DOL")){
				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkOlOrDol: not OL or DOL");
				return null;
			}
			aGlass.setOlOrDol(olOrDol); // both ADC & FDC is OL

			T_AutoFeedbackSetting autoFeedbackSetting;
			if (Utility.DEBUG) {
				autoFeedbackSetting = new T_AutoFeedbackSetting();
				autoFeedbackSetting.setRatio(0.8);
			}else {
				autoFeedbackSetting =  T_AutoFeedbackSetting_CRUD.read(aGlass.getProductName(), 
																		aGlass.getExpID(), 
																		aGlass.getExpRcpID(), 
																		aGlass.getMeasRcpID(),
																		aGlass.getMeasStepID(), 
																		aGlass.getAdcOrFdc());
				if (autoFeedbackSetting == null) {
					Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "cannot get data in T_AutoFeedbackSetting_CRUD");
					return null;
				}
			}			
			aGlass.setRatio(autoFeedbackSetting.getRatio());
			
			String dateStr = csv.FetchValue("PDS_GLASS_DATA", "PreEnd_Time_1");
			if (dateStr == null || dateStr.equals("")) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "csv fetch PDS_GLASS_DATA PreEnd_Time_1 Error");
				return null;
			}
			aGlass.setExposureTime(Utility.dateStr2Date(dateStr).getTime());
			
			List<Vector2D> csvMeasPotList = getCsvMeasPointList(csv, "Coord_X", "Coord_Y", "OL01", "OL02");
			if (csvMeasPotList == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "getCsvMeasPointList Error");
				return null;
			}
			aGlass.setMeasPointList(csvMeasPotList);
			
		}catch(Exception e) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
		}
		return aGlass;
	}
	
	private static Vector2D getVectPointInGlass(MeasureFileDataBase csv, String minOrMax, String olOrDol) {
		if(!minOrMax.toUpperCase().equals("MIN") && !minOrMax.toUpperCase().equals("MAX")){
			String errStr = "getVectPointInGlass Error: " + minOrMax + " is not Min or Max";
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, errStr);
			return null;
		}else if(!olOrDol.toUpperCase().equals("OL") && !olOrDol.toUpperCase().equals("DOL")){
			String errStr = "getVectPointInGlass Error: " + olOrDol + " is not OL or DOL";
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, errStr);
			return null;
		}

		int vaildInd = csv.indexOfFirstValidValueInCol("GLASS_SUMMARY", olOrDol + "01");
		if (vaildInd == -1) {
			Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "getVectPointInGlass fail: cannot find vaild value of column" + olOrDol);
			return null;
		}
		
		List <String> templateList = csv.FetchList("GLASS_SUMMARY", "Template_No");
		String templateStr = templateList.get(vaildInd);
		String valStr;
		Vector2D vet = new Vector2D();
		if(templateStr.toUpperCase().equals("T1")){
			valStr = csv.getCsvValByRowCol("GLASS_SUMMARY", "Template_No", "T1", "Statistic", minOrMax, olOrDol + "01");
			vet.setxValue( Double.parseDouble(valStr));
			valStr = csv.getCsvValByRowCol("GLASS_SUMMARY", "Template_No", "T1", "Statistic", minOrMax, olOrDol + "02");
			vet.setyValue( Double.parseDouble(valStr));
			Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "getVectPointInGlass success: return T1-template " + minOrMax + " " + olOrDol + " point");
			return vet;
		}else if(templateStr.toUpperCase().equals("T2")){
			valStr = csv.getCsvValByRowCol("GLASS_SUMMARY", "Template_No", "T2", "Statistic", minOrMax, olOrDol + "01");
			vet.setxValue( Double.parseDouble(valStr));
			valStr = csv.getCsvValByRowCol("GLASS_SUMMARY", "Template_No", "T2", "Statistic", minOrMax, olOrDol + "02");
			vet.setyValue( Double.parseDouble(valStr));
			Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "getVectPointInGlass success: return T2-template " + minOrMax + " " + olOrDol + " point");
			return vet;
		}else{
			return null;
		}
	}
	
	private static String checkOlOrDol(ExpMeasGlass aGlass, Vector2D minOL, Vector2D maxOL, Vector2D minDOL, Vector2D maxDOL){
		// return the correct feedback type: OL or DOL
		if (maxOL == null && minOL == null) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkOlOrDol Error: maxOL = null or minOL = null");
			return null;
		}
		
		T_AutoFeedbackSetting autoFbkSeting;
		if (Utility.DEBUG) {
			autoFbkSeting = new T_AutoFeedbackSetting();
			autoFbkSeting.setOl_L_UpperLimit(-9999);
			autoFbkSeting.setOl_U_UpperLimit(9999);
			autoFbkSeting.setOl_L_LowerLimit(-9999);
			autoFbkSeting.setOl_U_LowerLimit(9999);
			autoFbkSeting.setDOl_L_UpperLimit(-9999);;
			autoFbkSeting.setDOl_U_UpperLimit(9999);
			autoFbkSeting.setDOl_L_LowerLimit(-9999);
			autoFbkSeting.setDOl_U_LowerLimit(9999);
		}else {
			autoFbkSeting = T_AutoFeedbackSetting_CRUD.read(aGlass.getProductName(), 
															aGlass.getExpID(), 
															aGlass.getExpRcpID(), 
															aGlass.getMeasRcpID(), 
															aGlass.getMeasStepID(), 
															aGlass.getAdcOrFdc());
			if (autoFbkSeting == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkOlOrDol Error: cannot read T_AutoFeedbackSetting");
				return null;
			}
		}
		if (maxDOL != null && minDOL != null) {
			if( (autoFbkSeting.getDOl_L_UpperLimit() <= maxDOL.getxValue() && maxDOL.getxValue() <= autoFbkSeting.getDOl_U_UpperLimit()) ||
				(autoFbkSeting.getDOl_L_LowerLimit() <= minDOL.getxValue() && minDOL.getxValue() <= autoFbkSeting.getDOl_U_LowerLimit()) || 
				(autoFbkSeting.getDOl_L_UpperLimit() <= maxDOL.getyValue() && maxDOL.getyValue() <= autoFbkSeting.getDOl_U_UpperLimit()) ||
				(autoFbkSeting.getDOl_L_LowerLimit() <= minDOL.getyValue() && minDOL.getyValue() <= autoFbkSeting.getDOl_U_LowerLimit()) ){
					
				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkOlOrDol: feedback DOL");
				return "DOL";
			}
		}
		if( (autoFbkSeting.getOl_L_UpperLimit() <= maxOL.getxValue() && maxOL.getxValue() <= autoFbkSeting.getOl_U_UpperLimit()) ||
			(autoFbkSeting.getOl_L_LowerLimit() <= minOL.getxValue() && minOL.getxValue() <= autoFbkSeting.getOl_U_LowerLimit()) || 
			(autoFbkSeting.getOl_L_UpperLimit() <= maxOL.getyValue() && maxOL.getyValue() <= autoFbkSeting.getOl_U_UpperLimit()) ||
			(autoFbkSeting.getOl_L_LowerLimit() <= minOL.getyValue() && minOL.getyValue() <= autoFbkSeting.getOl_U_LowerLimit()) ){
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkOlOrDol: feedback OL");
			return "OL";
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkOlOrDol: don't need feedback");
		return "NULL";
	}
	
	private static List<Vector2D> getCsvMeasPointList(MeasureFileDataBase csv, String coordXStr, String coordYStr, 
														String ol01Str, String ol02Str){
		List<String> siteNoList = csv.FetchList("SITE_DATA", "Site_No");
		List<String> ol01List = csv.FetchList("SITE_DATA", ol01Str);
		List<String> ol02List = csv.FetchList("SITE_DATA", ol02Str);
		List<String> coordXList = csv.FetchList("SITE_DATA", coordXStr);
		List<String> coordYList = csv.FetchList("SITE_DATA", coordYStr);

		if(siteNoList == null || ol01List == null || ol02List == null || coordXList == null || coordYList == null){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "ArrayExp getCsvMeasPointList: out of domain");
			return null;
		}else if(ol01List.size() != ol02List.size()){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "ArrayExp getCsvMeasPointList: ol01List.size() != ol02List.size()");
			return null;
		}else if(coordXList.size() != coordYList.size()){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "ArrayExp getCsvMeasPointList: coordXList.size() != coordYList.size()");
			return null;
		}else if(ol01List.size() != coordXList.size()){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "ArrayExp getCsvMeasPointList: ol01List.size() != coordXList.size()");
			return null;
		}

		List<Vector2D> vectorList = new ArrayList<>();
		for(int ind = 0; ind < ol01List.size(); ind++){
			if (ol01List.get(ind).equals(""))
				continue;
			int siteNo = Integer.parseInt( siteNoList.get(ind));
			double xAxis = Double.parseDouble( coordXList.get(ind));
			double yAxis = Double.parseDouble( coordYList.get(ind));
			double xValue = Double.parseDouble( ol01List.get(ind));
			double yValue = Double.parseDouble( ol02List.get(ind));
			vectorList.add(new Vector2D(siteNo, xAxis, yAxis, xValue, yValue));
		}
		return vectorList;
	}

	
	public boolean checkIsDataVaild(){
		// check the exposure time length between this glass and previous glass
		
		// get pevsGlassExposureTime
		long pevsGlassExposureTime = this.getExposureTime();
		
		T_LastExpTime lastExpTime;
		if (Utility.DEBUG) {
			lastExpTime = new T_LastExpTime();
			lastExpTime.setExpTime(1234);
		}else {
			lastExpTime = T_LastExpTime_CRUD.read(this.expID, this.expRcpID);
			if (lastExpTime == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkIsDataVaild: cannot get T_LastExpTime");
				// create
				T_LastExpTime aLastExpTime = new T_LastExpTime();
				aLastExpTime.setExpId(this.getExpID());
				aLastExpTime.setExpTime(this.getExposureTime());
				aLastExpTime.setRcpId(this.getExpRcpID());
				T_LastExpTime_CRUD.create(aLastExpTime);
			}else{
				pevsGlassExposureTime = lastExpTime.getExpTime();
				if (this.getExposureTime() > pevsGlassExposureTime) {
					// update
					lastExpTime.setExpTime(this.getExposureTime());
					T_LastExpTime_CRUD.update(lastExpTime);
				}
			}
		}

		T_AutoFeedbackSetting autoFeedbackSetting; 
		if(Utility.DEBUG) {
			autoFeedbackSetting = new T_AutoFeedbackSetting();
			autoFeedbackSetting.setExpiretime(Long.MAX_VALUE);
		}else {
			autoFeedbackSetting = T_AutoFeedbackSetting_CRUD.read(this.getProductName(), 
																	this.getExpID(), 
																	this.getExpRcpID(), 
																	this.getMeasRcpID(), 
																	this.getMeasStepID(), 
																	this.getAdcOrFdc());	
			if (autoFeedbackSetting == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkIsDataVaild Error: cannot get T_AutoFeedbackSetting");
				return false;
			}
		}
		long expireTime = autoFeedbackSetting.getExpiretime(); 
		if (expireTime > 0) {
			if (this.getExposureTime() - pevsGlassExposureTime > expireTime){
				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkIsDataVaild: (exp time of this glass[" + this.getExposureTime() + 
																		"] - the previous's[" + pevsGlassExposureTime + 
																		"]) > setting expireTime[" + expireTime + "]");
				cleanTargetGlassSet(this);
				return false;
			}
			// case "this.getExposureTime() - pevsGlassExposureTime > expireTime" is good, continue
		}else {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkIsDataVaild Error: T_autoFeedbackSetting expireTime < 0");
		}
		
		// check the time length between the track in time of this glass and the last feedback time of this (EXP, recipe)
		long thisGlassTrackInTime = getMesTrackInTime(this.getGlassID(), this.getExpStepID(), this.getExpID());
		if (thisGlassTrackInTime == -1) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkIsDataVaild Error: cannot find track in time");
			return false;
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "getCsvTrackInTime: find track in time Success");

		long lastFdbkTime = 0;
		T_ArrayExpFeedbackHistory feedbackHistory = T_ArrayExpFeedbackHistory_CRUD.read(this);
		if (feedbackHistory == null) {
			Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "checkIsDataVaild fail: cannot get info from Table T_ArrayExpFeedbackHistory");
		}else {
			lastFdbkTime = feedbackHistory.getFeedback_Time();
		}
		
		if(thisGlassTrackInTime < lastFdbkTime){
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkIsDataVaild fail: trackIn time is before last feedback time of this (EXP, recipe)");
			cleanTargetGlassSet(this);
			return false;
		}

		// check if there are any 9999.999 or 8888.888
		Iterator<Vector2D> iter = this.measPointList.iterator();
		while(iter.hasNext()) {
			Vector2D aVect = iter.next();
			if(aVect.xValue == 9999.999 || aVect.yValue == 9999.999) {
				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkIsDataVaild: there are 9999.999");
				cleanTargetGlassSet(this); 
				return false;
			}else if(aVect.xValue == 8888.888 || aVect.yValue == 8888.888) {
				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkIsDataVaild: there are 8888.888");
				cleanTargetGlassSet(this);
				return false;
			}
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkIsDataVaild success: data is vaild");
		return true;
	}
	private void cleanTargetGlassSet(ExpMeasGlass aGlass){
		boolean err = T_ArrayExpContinueGlassSet_CRUD.delete(aGlass.getProductName(), 	
															aGlass.getExpID(), 
															aGlass.getExpRcpID(), 
															aGlass.getMeasRcpID(), 
															aGlass.getMeasStepID(), 		
															aGlass.getAdcOrFdc(),
															"");
		
		if (err == false) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "cleanTargetGlassSet Error: delete T_ArrayExpContinueGlassSet fail");
		}
		
	}
	
	private long getMesTrackInTime(String GlassID, String expStepId, String expID){
		MES_fwflatnode flatnode = MES_fwflatnode_CRUD.read(expStepId);
		if (flatnode == null) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "getMesTrackInTime Error: cannot read MES_fwflatnode");
			return -1;
		}
		
		List<MES_fwamtcomptrackhistory> trackInHisList = MES_fwamtcomptrackhistory_CRUD.read(GlassID, expID, "TrackIn", flatnode.getNodeName());
		if (trackInHisList == null) { 
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "getMesTrackInTime Error: cannot read MES_fwamtcomptrackhistory");
			return -1;
		}
		Collections.sort(trackInHisList, new Comparator<MES_fwamtcomptrackhistory>() {
			public int compare(MES_fwamtcomptrackhistory o1, MES_fwamtcomptrackhistory o2) {
				SimpleDateFormat simDateFormat = new SimpleDateFormat();
				simDateFormat.applyPattern("yyyyMMdd HHmmssSSS");
				try {
					Date txtTime1 = simDateFormat.parse(o1.getTxnTimeStamp());
					Date txtTime2 = simDateFormat.parse(o2.getTxnTimeStamp());
					return txtTime2.compareTo(txtTime1);
				} catch (Exception e) {
					Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "TrackInTimeComparator Error");
				}
				return 0;
			}
		});
		
		
		// Collections.sort(trackInHisList, new TrackInTimeComparator());
		String trackInTime = trackInHisList.get(0).getTxnTimeStamp();

		SimpleDateFormat simDateFormat = new SimpleDateFormat();
		simDateFormat.applyPattern("yyyyMMdd HHmmssSSS");
		Date trackInTimeLong = null;
		try {
			trackInTimeLong = simDateFormat.parse(trackInTime);
		} catch (Exception e) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "getMesTrackInTime Error");
			return -1;
		}
		return trackInTimeLong.getTime();
	}
	
	public void multipliedRatio(double ratio) {
		
		for(Vector2D vector2d: this.measPointList) {
			vector2d.xValue = Arith.mul(vector2d.xValue, ratio);
			vector2d.yValue = Arith.mul(vector2d.yValue, ratio);
		}
		
		return;
	}
	
}
