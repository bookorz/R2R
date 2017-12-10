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

			T_EqGroup2EqID eqGroup2EqID;
			if(Utility.DEBUG) {
				eqGroup2EqID = new T_EqGroup2EqID();
				eqGroup2EqID.setEqGroup("Nikon");
			}else {
				eqGroup2EqID = T_EqGroup2EqID_CRUD.read(aGlass.getExpID());
			}
			aGlass.setExpSupplier(eqGroup2EqID.getEqGroup()); // Nikon or Canon
			
			aGlass.setExpRcpID(csv.FetchValue("PDS_GLASS_DATA", "PreRecipe_ID_1"));
			
			T_ExpRcpID2Name expRcpID2Name;
			if(Utility.DEBUG) {
				expRcpID2Name = new T_ExpRcpID2Name();
				expRcpID2Name.setExpRcpName("ExpRecipeName");
			}else {
				expRcpID2Name = T_ExpRcpID2Name_CRUD.read(aGlass.getProductName(), 
																		aGlass.getExpRcpID());	
			}
			aGlass.setExpRcpName(expRcpID2Name.getExpRcpName());
			
			aGlass.setMeasStepID(csv.FetchValue("GLASS_DATA", "Step_Seq"));
			aGlass.setMeasRcpID(csv.FetchValue("GLASS_DATA", "Recipe_ID"));
			
			Vector2D minOL = new Vector2D();
			minOL =  ExpMeasGlass.getVectPointInGlass(csv, "Min", "OL");
			Utility.checkErrorAndLog(minOL, "ExpMeasGlass.getVectPointInGlass Error minOL = null", null);

			Vector2D maxOL = new Vector2D();
			maxOL =  ExpMeasGlass.getVectPointInGlass(csv, "Max", "OL");
			Utility.checkErrorAndLog(maxOL, "ExpMeasGlass.getVectPointInGlass Error maxOL = null", null);

			Vector2D minDOL = new Vector2D();
			minDOL =  ExpMeasGlass.getVectPointInGlass(csv, "Min", "DOL");
			
			Vector2D maxDOL = new Vector2D();
			maxDOL =  ExpMeasGlass.getVectPointInGlass(csv, "Max", "DOL");
		
			if(aGlass.getExpSupplier().equals("Canon")) {
				aGlass.setAdcOrFdc(ExpMeasGlass.ADC);
			}

			int olOrDol = checkOlOrDol(aGlass, minOL, maxOL, minDOL, maxDOL);
			Utility.checkErrorAndLog(olOrDol, "checkOlOrDol Error: orOrDol = -1", -1);
			aGlass.setOlOrDol(olOrDol); // both ADC & FDC is OL

			T_AutoFeedbackSetting autoFeedbackSetting;
			if(Utility.DEBUG) {
				autoFeedbackSetting = new T_AutoFeedbackSetting();
				autoFeedbackSetting.setRatio(0.8);
			}else {
				autoFeedbackSetting =  T_AutoFeedbackSetting_CRUD.read(aGlass.getProductName(), 
																		aGlass.getExpID(), 
																		aGlass.getExpRcpID(), 
																		aGlass.getMeasStepID(), 
																		aGlass.getMeasRcpID(), 
																		aGlass.getAdcOrFdc());
			}
			aGlass.setRatio(autoFeedbackSetting.getRatio());

			SimpleDateFormat simDateFormat = new SimpleDateFormat();
			simDateFormat.applyPattern( "yyyyMMdd HHmmssSSS");
			Date eTime = simDateFormat.parse(csv.FetchValue("PDS_GLASS_DATA", "PreEnd_Time_1"));		
			aGlass.setExposureTime(eTime.getTime());
			
			List<Vector2D> csvMeasPotList = getCsvMeasPointList(csv, "Coord_X", "Coord_Y", "OL01", "OL02");
			if (csvMeasPotList.size() == 0) {
				Utility.checkErrorAndLog(null, "cannot find csvMeasPotList", null);
			}
			aGlass.setMeasPointList(csvMeasPotList);
			
		}catch(Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return aGlass;
	}
	
	private static Vector2D getVectPointInGlass(MeasureFileDataBase csv, String minOrMax, String olOrDol) {
		if(!minOrMax.equals("Min") && !minOrMax.equals("Max")){
			String errStr = "getVectPointInGlass Error: " + minOrMax + " is not Min or Max";
			Utility.checkErrorAndLog(null, errStr, null);
			return null;
		}else if(!olOrDol.equals("OL") && !olOrDol.equals("DOL")){
			String errStr = "getVectPointInGlass Error: " + olOrDol + " is not OL or DOL";
			Utility.checkErrorAndLog(null, errStr, null);
			return null;
		}

		int vaildInd = csv.indexOfFirstValidValueInCol("GLASS_SUMMARY", olOrDol + "01");
		if (vaildInd == -1) {
			return null;
		}
		
		List <String> templateList = csv.FetchList("GLASS_SUMMARY", "Template_No");
		String templateStr = templateList.get(vaildInd);
		String valStr;
		Vector2D vet = new Vector2D();
		if(templateStr.equals("T1")){
			valStr = csv.getCsvValByRowCol("GLASS_SUMMARY", "Template_No", "T1", "Statistic", minOrMax, olOrDol + "01");
			vet.setxValue( Double.parseDouble(valStr));
			valStr = csv.getCsvValByRowCol("GLASS_SUMMARY", "Template_No", "T1", "Statistic", minOrMax, olOrDol + "02");
			vet.setyValue( Double.parseDouble(valStr));
			return vet;
		}else if(templateStr.equals("T2")){
			valStr = csv.getCsvValByRowCol("GLASS_SUMMARY", "Template_No", "T2", "Statistic", minOrMax, olOrDol + "01");
			vet.setxValue( Double.parseDouble(valStr));
			valStr = csv.getCsvValByRowCol("GLASS_SUMMARY", "Template_No", "T2", "Statistic", minOrMax, olOrDol + "02");
			vet.setyValue( Double.parseDouble(valStr));
			return vet;
		}else{
			return null;
		}
	}
	
	private static int checkOlOrDol(ExpMeasGlass aGlass, Vector2D minOL, Vector2D maxOL, Vector2D minDOL, Vector2D maxDOL){
		// return the correct feedback type: OL or DOL
		if (maxOL == null && minOL == null) {
			logger.debug("checkOlOrDol Error: maxOL = null or minOL = null");
			return -1;
		}
		
		T_AutoFeedbackSetting autoFbkSeting;
		if (Utility.DEBUG) {
			autoFbkSeting = new T_AutoFeedbackSetting();
			autoFbkSeting.setLUpperLimit(-999);
			autoFbkSeting.setUUpperLimit(999);
			autoFbkSeting.setLLowerLimit(-999);
			autoFbkSeting.setULowerLimit(999);
		}else {
			autoFbkSeting = T_AutoFeedbackSetting_CRUD.read(aGlass.getProductName(), 
															aGlass.getExpID(), 
															aGlass.getExpRcpID(), 
															aGlass.getMeasStepID(), 
															aGlass.getMeasRcpID(), 
															aGlass.getAdcOrFdc());
		}
		if (maxDOL != null && minDOL != null) {
			if(autoFbkSeting.getLUpperLimit() <= maxDOL.getxValue() && maxDOL.getxValue() <= autoFbkSeting.getUUpperLimit() &&
					autoFbkSeting.getLLowerLimit() <= minDOL.getxValue() && minDOL.getxValue() <= autoFbkSeting.getULowerLimit() && 
					autoFbkSeting.getLUpperLimit() <= maxDOL.getyValue() && maxDOL.getyValue() <= autoFbkSeting.getUUpperLimit() &&
					autoFbkSeting.getLLowerLimit() <= minDOL.getyValue() && minDOL.getyValue() <= autoFbkSeting.getULowerLimit()){
						return ExpMeasGlass.DOL;
			}
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
		List<String> ol01List = csv.FetchList("SITE_DATA", ol01Str);
		List<String> ol02List = csv.FetchList("SITE_DATA", ol02Str);
		List<String> coordXList = csv.FetchList("SITE_DATA", coordXStr);
		List<String> coordYList = csv.FetchList("SITE_DATA", coordYStr);

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

	
	public boolean checkIsDataVaild(){
		// check the exposure time length between this glass and previous glass
		long pevsGlassExposureTime = -1;// get from OEE #TODO
		T_AutoFeedbackSetting autoFeedbackSetting = T_AutoFeedbackSetting_CRUD.read(thisGlass.getProductName(), 
																						thisGlass.getExpID(), 
																						thisGlass.getExpRcpID(), 
																						thisGlass.getMeasStepID(), 
																						thisGlass.getMeasRcpID(), 
																						thisGlass.getAdcOrFdc());		
		long expireTime = autoFeedbackSetting.getExpiretime(); 
		if(thisGlass.getExposureTime() - pevsGlassExposureTime > expireTime){
			logger.debug("checkIsDataError: the exposure time length between this glass and previous glass is bigger than setting expireTime");
			return true;
		}

		// check the time length between the track in time of this glass and the last feedback time of this (EXP, recipe)
		long thisGlassTrackInTime = getMesTrackInTime(thisGlass.getGlassID(), 
											thisGlass.getExpStepID(),
											thisGlass.getExpID());
		Utility.checkErrorAndLog(thisGlassTrackInTime, "getCsvTrackInTime Error: cannot find track in time", -1);
		logger.debug("getCsvTrackInTime: find track in time Success");

		long lastFdbkTime = -1; //#TODO

		

		// check if there are any 9999.999 or 8888.888
		
		return false;
	}
	
	private long getMesTrackInTime(String GlassID, String expStepId, String expID){
		long result = -1;
//		select componentid,eqpid,stepid,max(txntimestamp)txntimestamp
//		from fwamtcomptrackhistory
//		where activity = 'TrackIn'
//		  and componentid = 'FEZL2AH414K231'     -- 填 Glass ID
//		  and stepid = (select distinct nodename 
//		                 from fwflatnode
//		                 where stepseq = '1632') -- 填 Stepseq
//		  and eqpid = '2AEXPB00'                 -- Process EQ
//		group by componentid,eqpid,stepid
		
		// ask book #TODO
		// targetGlassSet

		return result;
	}
}
