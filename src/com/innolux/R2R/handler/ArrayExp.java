package com.innolux.R2R.handler;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import org.apache.log4j.Logger;
import com.innolux.R2R.ArrayExp.model.ExpMeasGlass;
import com.innolux.R2R.ArrayExp.model.T_ArrayExpContinueGlassSet;
import com.innolux.R2R.ArrayExp.model.T_ArrayExpContinueGlassSet_CRUD;
import com.innolux.R2R.ArrayExp.model.T_ArrayExpFeedbackHistory_CRUD;
import com.innolux.R2R.ArrayExp.model.T_AutoFeedbackSetting;
import com.innolux.R2R.ArrayExp.model.T_AutoFeedbackSetting_CRUD;
import com.innolux.R2R.ArrayExp.model.Utility;
import com.innolux.R2R.ArrayExp.model.Vector2D;
import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.MeasureFileDataBase;
import com.innolux.R2R.interfaces.IFileData;
import com.innolux.services.MeasureFileReader;

public class ArrayExp implements IFileData{
	private static final ExpMeasGlass ExpMeasGlass = null;
	private Logger logger = Logger.getLogger(this.getClass());
	public final boolean DEBUG = false;

	public static void main(String [] argv) {
		//System.out.println(MeasureFileReader.class);	
		ArrayExp a = new ArrayExp("C:\\R2RNikonTest\\workspace\\", "C:\\R2RNikonTest\\ngFile\\");
	}
	
	public ArrayExp(String csvUpperFilePath, String ngFilePath) {
		MeasureFileReader fileService = new MeasureFileReader();
		fileService.setFileHandler(this, csvUpperFilePath, ngFilePath);
		fileService.start();

	}

	@Override
	public void onFileData (MeasureFileDataBase csv) {
		try {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "Start process file " + csv.getFileName());
			
			// when receive a csv 
			ExpMeasGlass emGlass = ExpMeasGlass.csv2ExpMeasGlass(csv);
			if (emGlass == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Error: emGlass = null");
				return;
			}
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "csv2ExpMeasGlass success: GlassID = " + emGlass.getGlassID());
			
			boolean isDataVaild = emGlass.checkIsDataVaild();
			if(!isDataVaild) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Fail: glass(" + emGlass.getGlassID() + ") data not vaild for feedback");
				return;
			}
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "glass(" + emGlass.getGlassID() + ") data is vaild");
			
			// add glass to continue Glass set
			boolean state = T_ArrayExpContinueGlassSet_CRUD.create(emGlass);
			if (state == false) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Error: create T_ArrayExpContinueGlassSet_CRUD error: GlassID = " + emGlass.getGlassID());
				return;
			}	
			
			// checkStartFeedback
			List<T_ArrayExpContinueGlassSet> matchGlassList = checkStartFeedback(emGlass);
			if (matchGlassList == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Fail: Glass ID = " + emGlass.getGlassID() + " checkStartFeedback = null");
				return;
			}
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "Glass ID = (" + emGlass.getGlassID() + ") need to start Feedback");

			
			//average all the matched glass
			ExpMeasGlass averageGlass = averageMatchGlassList(matchGlassList);
			if (averageGlass == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Error: Glass ID = (" + emGlass.getGlassID() + "), averageMatchGlassList = null");
			}
			averageGlass.setExpRcpName(emGlass.getExpRcpName());
			averageGlass.setExpStepID(emGlass.getExpStepID());
			averageGlass.setMeasEqId(emGlass.getMeasEqId());
			averageGlass.setExpRcpName(emGlass.getExpRcpName());
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "AverageMatchGlassList::");
			for (int ind = 0; ind < matchGlassList.size(); ind ++) {
				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, ind + ". Glass ID = " + matchGlassList.get(ind).getGlassID());
			}

			// times the ratio
			T_AutoFeedbackSetting autoFbkSeting = T_AutoFeedbackSetting_CRUD.read(emGlass.getProductName(), 
																					emGlass.getExpID(), 
																					emGlass.getExpRcpID(), 
																					emGlass.getMeasRcpID(), 
																					emGlass.getMeasStepID(), 
																					emGlass.getAdcOrFdc());
			if (autoFbkSeting == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Error: Glass ID = " + emGlass.getGlassID() + " cannot read T_AutoFeedbackSetting");
				return;
			}
			averageGlass.multipliedRatio(autoFbkSeting.getRatio());
			
			// createFeedbackFile
			if(emGlass.getExpSupplier().toUpperCase().equals("NIKON")){
				int intState = creatNikonFeedbackFile(averageGlass);
				if (intState == -1) {
					Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Error: creatNikonFeedbackFile fail");
					return;
				}else Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "creatNikonFeedbackFile success");
				String cmdStr = "cmd /c start " ;
				String workingDirectory = System.getProperty("user.dir");
				cmdStr += workingDirectory;
				cmdStr += "\\DPS2.exe ";
				cmdStr += "\"" + averageGlass.getExpID() + "\" \"" + averageGlass.getExpRcpName() + "\""; 
				Process process = Runtime.getRuntime().exec(cmdStr);
				process.waitFor();
				int exitVal = process.exitValue();
				if (exitVal != 0) {
					Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "DPS create rv5 file fail");
					return;
				}
				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "DPS create rv5 file success");
			}else if(emGlass.getExpSupplier().toUpperCase().equals("CANON")){
//				feedbackFile = createCanonFeedbackFile(emGlass);
//				sendFdbkFile2Nanon(feedbackFile);
			}
			
			// save feedback history to DB
			state = T_ArrayExpFeedbackHistory_CRUD.create(averageGlass, "Automation", "R2R-Nikon");
			if (!state) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Error: cannot create T_ArrayExpFeedbackHistory_CRUD");
			}
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "create T_ArrayExpFeedbackHistory_CRUD success");
			return;
		}catch(Exception e) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return;
		}
	}	
	private int creatNikonFeedbackFile(ExpMeasGlass averageGlass) {
		String feedbackFileName = averageGlass.getExpID() + "@" + averageGlass.getExpRcpName() + "@@@" + "01" + ".mv5";
		try {
			PrintWriter writer = new PrintWriter("Z:\\" + feedbackFileName, "UTF-8");
			writer.println("MV5Ver,0");
			String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
			writer.println("MV5Date," + timeStamp);
			writer.println("MID," + averageGlass.getExpID());
			writer.println("Recipe," + String.format("%04d", Integer.parseInt(averageGlass.getExpRcpID())) + "," + averageGlass.getExpRcpName());
			writer.println("Mask,,");
			writer.println("OffsetID,");
			writer.println("ProcessInfo,,");
			writer.println("MeasSystem," + averageGlass.getMeasEqId());
			writer.println("MeasDate," + timeStamp);
			writer.println("PlateSize,1850.00,1500.00");
			writer.println("PlateDir,0");
			writer.println("PlateResult,OK");
			int pointNum = averageGlass.getMeasPointList().size();
			writer.println("MeasPoint," + pointNum);
			for(int pInd = 1; pInd <= pointNum; pInd++) {
				// Nikon X = - MCD Y
				// Nikon Y = MCD X
				Vector2D point = averageGlass.getMeasPointList().get(pInd - 1);
				String NikonX = String.format("%.2f", -point.getyAxis());
				String NikonY = String.format("%.2f", point.getxAxis());
				String NikonOL01 = String.format("%.3f", -point.getyValue());
				String NikonOL02 = String.format("%.3f", point.getxValue());
				writer.println("MeasResult," + pInd + ",1," + NikonX + "," + NikonY + ",XY," + NikonOL01 + "," + NikonOL02 + ",XY");
			}
			writer.println("<EOF>");
			writer.close();
			return 1;

		}catch(Exception e) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "creatNikonFeedbackFile Error: " + e.getMessage());
			System.out.println(e.getMessage());
			return -1;
		}
	}
	private ExpMeasGlass averageMatchGlassList(List<T_ArrayExpContinueGlassSet> aGlassSetList){
		// save to vector2D
		int pointNum = 0;
		List<List<Vector2D>> vetorListList = new ArrayList<>();
		for(T_ArrayExpContinueGlassSet aGlassSet: aGlassSetList) { // for each glass
			String [] ol01StrArr = aGlassSet.getOl01List().split(",");
			String [] ol02StrArr = aGlassSet.getOl02List().split(",");
			String [] coordXArr = aGlassSet.getCoordXList().split(",");
			String [] coordYArr = aGlassSet.getCoordYList().split(",");
			List<Vector2D> vecterList = new ArrayList<>(); 
			pointNum = ol01StrArr.length;
			for(int strInd = 0; strInd < pointNum; strInd ++) {
				double xAsix = Double.parseDouble(coordXArr[strInd]);
				double yAsix = Double.parseDouble(coordYArr[strInd]);
				double ol01 = Double.parseDouble(ol01StrArr[strInd]);
				double ol02 = Double.parseDouble(ol02StrArr[strInd]);
				Vector2D aPoint = new Vector2D(xAsix, yAsix, ol01, ol02);
				vecterList.add(aPoint);
			}
			vetorListList.add(vecterList);
		}
		
		// calculate the average of all glass by each point
		List<Vector2D> avgPointList = new ArrayList<>();
		for(int pInd = 0; pInd < pointNum; pInd ++) {
			double xResult = 0, yResult = 0;
			for(List<Vector2D> vecterList: vetorListList) {
				xResult += vecterList.get(pInd).getxValue();
				yResult += vecterList.get(pInd).getyValue();
			}
			xResult /= vetorListList.size(); 
			yResult /= vetorListList.size();
			Vector2D vector2d = vetorListList.get(0).get(pInd);
			Vector2D aPoint = new Vector2D(vector2d.getxAxis(), vector2d.getyAxis(), xResult, yResult);
			avgPointList.add(aPoint);
		}

		ExpMeasGlass resultGlass = new ExpMeasGlass();
		T_ArrayExpContinueGlassSet aGlassSet = aGlassSetList.get(0);
		resultGlass.setProductName(aGlassSet.getProductName());
		resultGlass.setExpID(aGlassSet.getExpID());
		resultGlass.setExpRcpID(aGlassSet.getExpRcpID());
		resultGlass.setMeasStepID(aGlassSet.getMeasStepID());
		resultGlass.setMeasRcpID(aGlassSet.getMeasRcpID());
		resultGlass.setAdcOrFdc(aGlassSet.getAdcOrFdc());
		resultGlass.setGlassID(aGlassSet.getGlassID());
		resultGlass.setExposureTime(aGlassSet.getExposureTime());
		resultGlass.setOlOrDol(aGlassSet.getFeedbackMode());
		resultGlass.setMeasPointList(avgPointList);

		return resultGlass;
	}

	
	public List<T_ArrayExpContinueGlassSet> checkStartFeedback(ExpMeasGlass aGlass) {
		// get setting for PopulationSize
		T_AutoFeedbackSetting aSetting = T_AutoFeedbackSetting_CRUD.read(aGlass.getProductName(), 
																		 aGlass.getExpID(), 
																		 aGlass.getExpRcpID(), 
																		 aGlass.getMeasRcpID(), 
																		 aGlass.getMeasStepID(), 
																		 aGlass.getAdcOrFdc());
		if (aSetting == null) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkStartFeedback Error: cannot read T_AutoFeedbackSetting");
			return null;
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "checkStartFeedback: read T_AutoFeedbackSetting success");
			
		// get all the glass
		List<T_ArrayExpContinueGlassSet> continueGlassList = T_ArrayExpContinueGlassSet_CRUD.read(aGlass);
		if (continueGlassList == null) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkStartFeedback Error: read T_ArrayExpContinueGlassSet Error");
			return null;
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "checkStartFeedback: read T_ArrayExpContinueGlassSet success");
			
		// check enough glass
		if (continueGlassList.size() < aSetting.getSampleSize()) {
			Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "checkStartFeedback fail: number of target glass < sample size");
			return null;
		}
		if (continueGlassList.size() < aSetting.getPopulationSize()) {
			Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "checkStartFeedback fail: number of target glass < Population size");
			return null;
		}
			
		// ordering the glassList by exposure time using ascending order
		Collections.sort(continueGlassList,
			new Comparator<T_ArrayExpContinueGlassSet>() {
			public int compare(T_ArrayExpContinueGlassSet o1, T_ArrayExpContinueGlassSet o2) {
				return (int)(o1.getExposureTime() - o2.getExposureTime());
			}
		});
		
		// get the number as population size of latest exposure time glass in glassList
		List<T_ArrayExpContinueGlassSet> latestExpGlassList = continueGlassList.subList(continueGlassList.size() - aSetting.getPopulationSize(), continueGlassList.size());
		
		// delete exceed population size old glass by exposure time #DEBUG
		for (int ind = 0; ind < continueGlassList.size() - aSetting.getPopulationSize(); ind++) {
			boolean state = T_ArrayExpContinueGlassSet_CRUD.delete(continueGlassList.get(ind));
			if (state) {
				// success
				Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "checkStartFeedback: delete T_ArrayExpContinueGlassSet success");
			}else {
				// fail
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkStartFeedback Error: delete T_ArrayExpContinueGlassSet fail");
				return null;
			}
		}
		
		// check each of them if it match feedback condition
		List<Integer> matchGlassList = new ArrayList<>();
		for(int ind = 0; ind < latestExpGlassList.size(); ind++){
			if (latestExpGlassList.get(ind).getFeedbackMode().equals( aGlass.getOlOrDol() ) &&
				latestExpGlassList.get(ind).getAdcOrFdc().equals( aGlass.getAdcOrFdc() )){
				matchGlassList.add(ind);
			}
		}
		if (matchGlassList.size() < aSetting.getSampleSize()) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback: matchGlassList.size < aSetting.getSampleSize");
			return null;
		}
		List<T_ArrayExpContinueGlassSet> returnGlassList = new ArrayList<>();
		if (aSetting.getActiveRule().equals("RANDOM")) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback: need Feedback");
			for (int glassInd = 0; glassInd < aSetting.getSampleSize(); glassInd++) {
				int matchGlassInd = matchGlassList.get(glassInd);
				T_ArrayExpContinueGlassSet matchGlass = latestExpGlassList.get(matchGlassInd);
				returnGlassList.add(matchGlass);
			}
			int state = checkSigmaSmallerSetting(returnGlassList, aSetting.getSigma());
			if (state == -1) {
				//error
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkSigmaSmallerSetting Error");
				return null;
			}
			if (state == 0) {
				 //not ok
				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkSigmaSmallerSetting: sigma >= setting");
				return null;
			}
			if (state == 1) {
				// OK
				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkSigmaSmallerSetting: sigma < setting");
				return returnGlassList;
			}
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkSigmaSmallerSetting fatal Error");
			return null; // should not be here since there are return in three case
		}
		if (!aSetting.getActiveRule().equals("CONTINUE")) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkStartFeedback Error: ActiveRule is not CONTINUE or RANDOM");
			return null;
		}
		// check case of "continue" active rule
		Collections.sort(matchGlassList,
			new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		});
		for (int glassInd = 0; glassInd <= matchGlassList.size() - aSetting.getSampleSize(); glassInd++) {
			int ele1 = matchGlassList.get(glassInd);
			int ind2, continueCount;
			for (ind2 = 0, continueCount = 0; ind2 < aSetting.getSampleSize(); ind2++) {
				if ((ele1 + ind2) != matchGlassList.get(glassInd + ind2)) {
					break;
				}
				continueCount++;
			}
			if (continueCount == aSetting.getSampleSize()) {
				returnGlassList = latestExpGlassList.subList(glassInd, glassInd + aSetting.getSampleSize() - 1);
				int state = checkSigmaSmallerSetting(returnGlassList, aSetting.getSigma());
				if (state == -1) {
					//error
					Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkSigmaSmallerSetting Error");
					return null;
				}
				if (state == 0) {
					continue; //not ok
				}
				if (state == 1) {
					// OK
					Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkSigmaSmallerSetting: sigma < setting");
					return returnGlassList;
				}
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkSigmaSmallerSetting fatal Error");
				continue; // should not be here since there are return in three case
			}
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkSigmaSmallerSetting: sigma >= setting");
		return null;
	}
	
	private int checkSigmaSmallerSetting(List<T_ArrayExpContinueGlassSet> aGlassSetList, double sigma) {		
		String ol01Str = aGlassSetList.get(0).getOl01List();
		int strCount = ol01Str.length() - ol01Str.replace(",", "").length(); // count the number of ","
		strCount++;
		
		List<List <Double>> allGlassOl01Arr = new ArrayList<>();
		List<List <Double>> allGlassOl02Arr = new ArrayList<>();
		// get and save string 
		for (T_ArrayExpContinueGlassSet aGlassSet: aGlassSetList) {
			String [] ol01StrArr = aGlassSet.getOl01List().split(",");
			String [] ol02StrArr = aGlassSet.getOl02List().split(",");
			List<Double> allPointOl01Arr = new ArrayList<>();
			List<Double> allPointOl02Arr = new ArrayList<>();
			for(int strInd = 0; strInd < strCount; strInd ++) {
				double ol01 = Double.parseDouble(ol01StrArr[strInd]);
				double ol02 = Double.parseDouble(ol02StrArr[strInd]);
				allPointOl01Arr.add(ol01);
				allPointOl02Arr.add(ol02);
			}
			allGlassOl01Arr.add(allPointOl01Arr);
			allGlassOl02Arr.add(allPointOl02Arr);
		}
		
		// get average OL01 and OL02
		ExpMeasGlass expMeasGlass = averageMatchGlassList(aGlassSetList);
		List<Vector2D> avgPointList = expMeasGlass.getMeasPointList();
		
		// calculate std of each point
		for(int strInd = 0; strInd < strCount; strInd ++) {
			double avgOL01 = avgPointList.get(strInd).getxValue();
			double avgOL02 = avgPointList.get(strInd).getyValue();
			double xSum = 0, ySum = 0;

			for(int glassInd = 0; glassInd < allGlassOl01Arr.size(); glassInd++) {
				double ol01Val = allGlassOl01Arr.get(glassInd).get(strInd);
				double ol02Val = allGlassOl02Arr.get(glassInd).get(strInd);

				xSum += Math.pow(ol01Val - avgOL01, 2);
				ySum += Math.pow(ol02Val - avgOL02, 2);
			}
			xSum /= (aGlassSetList.size() - 1);
			ySum /= (aGlassSetList.size() - 1);
			if(Math.sqrt(xSum) > sigma || Math.sqrt(ySum) > sigma) {
				Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "checkSigmaSmallerSetting Fail: Point " + strInd + "sigma > setting sigma " + sigma);
				return 0;
			}
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkSigmaSmallerSetting success");
		return 1;
	}
}

