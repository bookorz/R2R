package com.innolux.R2R.handler;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import com.innolux.R2R.ArrayExp.model.ExpMeasGlass;
import com.innolux.R2R.ArrayExp.model.T_ArrayExpContinueGlassSet;
import com.innolux.R2R.ArrayExp.model.T_ArrayExpContinueGlassSet_CRUD;
import com.innolux.R2R.ArrayExp.model.T_ArrayExpCurrentState;
import com.innolux.R2R.ArrayExp.model.T_ArrayExpCurrentState_CRUD;
import com.innolux.R2R.ArrayExp.model.T_AutoFeedbackSetting;
import com.innolux.R2R.ArrayExp.model.T_AutoFeedbackSetting_CRUD;
import com.innolux.R2R.ArrayExp.model.Utility;
import com.innolux.R2R.ArrayExp.model.Vector2D;
import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.base.MeasureFileDataBase;
import com.innolux.R2R.interfaces.IFileData;
import com.innolux.annotation.Column;
import com.innolux.services.MeasureFileReader;

import oracle.net.aso.l;

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
		// when receive a csv 
		ExpMeasGlass emGlass = ExpMeasGlass.csv2ExpMeasGlass(csv);
		if (emGlass == null) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "onFileData Error: emGlass = null");
			return;
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "onFileData: csv2ExpMeasGlass success");
		
		boolean isDataVaild = emGlass.checkIsDataVaild();
		if(!isDataVaild) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "ArrayExp onFileData fail: glass data not vaild for feedback");
			return;
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "onFileData: glass data is vaild");
		
		// add glass to continue Glass set
		boolean state = T_ArrayExpContinueGlassSet_CRUD.create(emGlass);
		if (state == false) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "onFileData Error: create T_ArrayExpContinueGlassSet_CRUD error");
			return;
		}	

		// count + 1
		T_ArrayExpCurrentState arrayExpCurrentState = T_ArrayExpCurrentState_CRUD.read(emGlass.getProductName(), 
																						emGlass.getExpID(), 
																						emGlass.getExpRcpID(), 
																						emGlass.getMeasRcpID(), 
																						emGlass.getMeasStepID(), 
																						emGlass.getAdcOrFdc());
		if (arrayExpCurrentState == null) {
			// create
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "ArrayExp onFileData fail: cannot read T_ArrayExpCurrentState");
			T_ArrayExpCurrentState newState = new T_ArrayExpCurrentState(emGlass);
			newState.setProductName(emGlass.getProductName());
			newState.setExpID(emGlass.getExpID());
			newState.setExpStepID(emGlass.getExpStepID());
			newState.setMeaStepID(emGlass.getMeasStepID());
			newState.setExpRcpID(emGlass.getExpRcpID());
			newState.setMeaRcpID(emGlass.getMeasRcpID());
			newState.setAdcOrFdc(emGlass.getAdcOrFdc());
			newState.setCount(1);
			
			boolean saveState = T_ArrayExpCurrentState_CRUD.create(newState);
			if (saveState) {
				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "ArrayExp onFileData: create T_ArrayExpCurrentState success");
			}else {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "ArrayExp onFileData Error: cannot create T_ArrayExpCurrentState");
			}
			return;
		}
		// update
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "ArrayExp onFileData: read T_ArrayExpCurrentState success");
		arrayExpCurrentState.setCount (arrayExpCurrentState.getCount() + 1); 
		boolean updateState = T_ArrayExpCurrentState_CRUD.update(arrayExpCurrentState);
		if (updateState) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "ArrayExp onFileData: update T_ArrayExpCurrentState success");
		}else {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "ArrayExp onFileData Error: cannot update T_ArrayExpCurrentState");
		}
		
		// checkStartFeedback
		List<T_ArrayExpContinueGlassSet> matchGlassList = null;
		int startState = checkStartFeedback(emGlass, matchGlassList);
		if (startState == -1) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "ArrayExp onFileData Error: checkStartFeedback Error");
			return;
		}else if (startState == 0) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "ArrayExp onFileData: dont need to start Feedback");
			return;
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "ArrayExp onFileData: need to start Feedback");

		
		//average all the matched glass
		ExpMeasGlass averageGlass = averageMatchGlassList(matchGlassList);
		
		// times the ratio
		T_AutoFeedbackSetting autoFbkSeting = T_AutoFeedbackSetting_CRUD.read(emGlass.getProductName(), 
																				emGlass.getExpID(), 
																				emGlass.getExpRcpID(), 
																				emGlass.getMeasRcpID(), 
																				emGlass.getMeasStepID(), 
																				emGlass.getAdcOrFdc());
		if (autoFbkSeting == null) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "onFileData Error: cannot read T_AutoFeedbackSetting");
			return;
		}
		averageGlass.multipliedRatio(autoFbkSeting.getRatio());
		
		creatFeedbackFile(averageGlass);
		
		// sendFeedbackFile();
		
//		feedbackFile = null;
//		
//		if(emGlass.getExpSupplier().equals("Nikon")){
//			feedbackFile = createNikonFeedbackFile(emGlass);
//			sendFdbkFile2Canon(feedbackFile);
//		}else if(emGlass.getExpSupplier().equals("Canon")){
//			feedbackFile = createCanonFeedbackFile(emGlass);
//			sendFdbkFile2Nanon(feedbackFile);
//		}
		// save feedback history to DB
		
		return;
	}	
	private int creatFeedbackFile(ExpMeasGlass averageGlass) {
		String feedbackFileName = averageGlass.getExpID() + "@" + averageGlass.getExpRcpName() + "@@@" + "01" + ".mv5";
		try {
			PrintWriter writer = new PrintWriter("Z:\\" + feedbackFileName + ".txt", "UTF-8");
			writer.println("MV5Ver,0");
			String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
			writer.println("MV5Date," + timeStamp);
			writer.println("MID," + averageGlass.getExpID());
			writer.println("Recipe," + averageGlass.getExpRcpID() + "," + averageGlass.getExpRcpName());
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
				String NikonX = String.format("%8.2f", point.getyAxis());

				writer.println("MeasResult," + pInd + ",1,"-890463.00,-729562.00,XY,0.028,0.654,XY);
				
			}
			
				
				MeasResult,1,1,-890463.00,-729562.00,XY,0.028,0.654,XY
				MeasResult,2,1,-475927.00,-729567.00,XY,-0.831,0.169,XY
				MeasResult,3,1,-52020.00,-729558.00,XY,-0.203,-0.113,XY
				
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return -1;
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
			xResult /= (vetorListList.size() - 1); // sample std
			yResult /= (vetorListList.size() - 1);
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

	
	public int checkStartFeedback(ExpMeasGlass aGlass, List<T_ArrayExpContinueGlassSet> returnGlassList) {
		// get setting for PopulationSize
		T_AutoFeedbackSetting aSetting = T_AutoFeedbackSetting_CRUD.read(aGlass.getProductName(), 
																		 aGlass.getExpID(), 
																		 aGlass.getExpRcpID(), 
																		 aGlass.getMeasRcpID(), 
																		 aGlass.getMeasStepID(), 
																		 aGlass.getAdcOrFdc());
		if (aSetting == null) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkStartFeedback Error: cannot read T_AutoFeedbackSetting");
			return -1;
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback: read T_AutoFeedbackSetting success");
			
		// get all the glass
		List<T_ArrayExpContinueGlassSet> glassList = T_ArrayExpContinueGlassSet_CRUD.read(aGlass);
		if (glassList == null) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkStartFeedback Error: read T_ArrayExpContinueGlassSet Error");
			return -1;
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback: read T_ArrayExpContinueGlassSet success");
			
		// check enough glass
		if (glassList.size() < aSetting.getSampleSize()) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback fail: number of target glass < sample size");
			return 0;
		}
		if (glassList.size() < aSetting.getPopulationSize()) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback fail: number of target glass < Population size");
			return 0;
		}
			
		// ordering the glassList by exposure time using accending order
		Collections.sort(glassList,
			new Comparator<T_ArrayExpContinueGlassSet>() {
			public int compare(T_ArrayExpContinueGlassSet o1, T_ArrayExpContinueGlassSet o2) {
				return (int)(o1.getExposureTime() - o2.getExposureTime());
			}
		});
		// get first population size glass in glassList
		List<T_ArrayExpContinueGlassSet> newGlassList = glassList.subList(glassList.size() - aSetting.getPopulationSize(), glassList.size());
		
		// delete exceed population size old glass by exposure time #DEBUG
//		for (int ind = 0; ind < glassList.size() - aSetting.getPopulationSize(); ind++) {
//			boolean state = T_ArrayExpContinueGlassSet_CRUD.delete(glassList.get(ind));
//			if (state) {
//				// success
//				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback: delete T_ArrayExpContinueGlassSet success");
//			}else {
//				// fail
//				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkStartFeedback Error: delete T_ArrayExpContinueGlassSet fail");
//				return -1;
//			}
//		}
		
		// check each of them if it match feedback condition
		List<Integer> matchGlassList = new ArrayList<>();
		for(int ind = 0; ind < newGlassList.size(); ind++){
			String feedMode = newGlassList.get(ind).getFeedbackMode();
			if (feedMode.equals("OL") || feedMode.equals("DOL")){
				matchGlassList.add(ind);
			}
		}
		if (matchGlassList.size() < aSetting.getSampleSize()) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback: matchGlassList.size < aSetting.getSampleSize");
			return 0;
		}
		if (aSetting.getActiveRule().equals("RANDOM")) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback: need Feedback");
			return 1;
		}
		if (!aSetting.getActiveRule().equals("CONTINUE")) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkStartFeedback Errir: ActiveRule is not CONTINUE or RANDOM");
			return 1;
		}
		// check case of "continue" active rule
		Collections.sort(matchGlassList,
			new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		});
		for (int ind1 = 0; ind1 <= matchGlassList.size() - aSetting.getSampleSize(); ind1++) {
			int ele1 = matchGlassList.get(ind1);
			int ind2, continueCount;
			for (ind2 = 0, continueCount = 0; ind2 < aSetting.getSampleSize(); ind2++) {
				if ((ele1 + ind2) != matchGlassList.get(ind1 + ind2)) {
					break;
				}
				continueCount++;
			}
			if (continueCount == aSetting.getSampleSize()) {
				returnGlassList = newGlassList.subList(ind1, ind1 + aSetting.getSampleSize() - 1);
				int state = checkSigmaSmallerSetting(returnGlassList, aSetting.getRatio());
				if (state == -1) {
					//error
					Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkSigmaSmallerSetting Error");
					return -1;
				}
				if (state == 0) {
					continue; //not ok
				}
				if (state == 1) {
					// OK
					Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkSigmaSmallerSetting: sigma is smaller than setting");
					return 1;
				}
				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkSigmaSmallerSetting: sigma is smaller than setting");
				continue;
			}
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkSigmaSmallerSetting: sigma is not smaller than setting");
		return 0;
	}
	
	private int checkSigmaSmallerSetting(List<T_ArrayExpContinueGlassSet> aGlassSetList, double ratio) {
		List<Vector2D> avgVectorList = new ArrayList();
		
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
			double avgOL02 = avgPointList.get(strInd).getxValue();
			double xSum = 0, ySum = 0;

			for(int glassInd = 0; glassInd < allGlassOl01Arr.size(); glassInd++) {
				double ol01Val = allGlassOl01Arr.get(glassInd).get(strInd);
				double ol02Val = allGlassOl02Arr.get(glassInd).get(strInd);

				xSum += Math.pow(ol01Val - avgOL01, 2);
				ySum += Math.pow(ol02Val - avgOL02, 2);
			}
			xSum /= (aGlassSetList.size() - 1);
			ySum /= (aGlassSetList.size() - 1);
			if(Math.sqrt(xSum) > ratio || Math.sqrt(ySum) > ratio) {
				Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "checkSigmaSmallerSetting Fail: Point" + strInd + "sigma < ratio" + ratio);
				return 0;
			}
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkSigmaSmallerSetting success");
		return 1;
	}
}

