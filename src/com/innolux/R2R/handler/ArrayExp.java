package com.innolux.R2R.handler;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.innolux.R2R.ArrayExp.model.Arith;
import com.innolux.R2R.ArrayExp.model.ExpMeasGlass;
import com.innolux.R2R.ArrayExp.model.MES_lwExpR2rSetting;
import com.innolux.R2R.ArrayExp.model.MES_lwExpR2rSetting_CRUD;
import com.innolux.R2R.ArrayExp.model.T_ArrayExpContinueGlassSet;
import com.innolux.R2R.ArrayExp.model.T_ArrayExpContinueGlassSet_CRUD;
import com.innolux.R2R.ArrayExp.model.T_ArrayExpFeedbackHistory_CRUD;
import com.innolux.R2R.ArrayExp.model.T_AutoFeedbackSetting;
import com.innolux.R2R.ArrayExp.model.T_AutoFeedbackSetting_CRUD;
import com.innolux.R2R.ArrayExp.model.T_CanonExpSiteNo2ScanNo;
import com.innolux.R2R.ArrayExp.model.T_CanonExpSiteNo2ScanNo_CRUD;
import com.innolux.R2R.ArrayExp.model.T_LastExpTime_CRUD;
import com.innolux.R2R.ArrayExp.model.Utility;
import com.innolux.R2R.ArrayExp.model.Vector2D;
import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.MeasureFileDataBase;
import com.innolux.R2R.interfaces.IFileData;
import com.innolux.R2R.model.LogHistory_CRUD;
import com.innolux.services.MeasureFileReader;

public class ArrayExp implements IFileData{
	public final boolean DEBUG = false;
	private static Logger logger = Logger.getLogger(ArrayExp.class);
	
	public static void main(String [] argv) {
		//System.out.println(MeasureFileReader.class);	
		// ArrayExp a = new ArrayExp("C:\\R2RNikonTest\\workspace\\", "C:\\R2RNikonTest\\ngFile\\");
		
		MES_lwExpR2rSetting aMES_lwExpR2rSetting = new MES_lwExpR2rSetting();
		aMES_lwExpR2rSetting.setEqpId("r2rTest");
		aMES_lwExpR2rSetting.setHoldFlag("T");
		aMES_lwExpR2rSetting.setProdId("r2rTest");
		aMES_lwExpR2rSetting.setR2rFeedbackTime(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		aMES_lwExpR2rSetting.setRecipeId("r2rTest");
		List<MES_lwExpR2rSetting> mesLwExpR2rSettingList = MES_lwExpR2rSetting_CRUD.read(aMES_lwExpR2rSetting);
		boolean state;
		if (mesLwExpR2rSettingList == null) {
			// create
			state = MES_lwExpR2rSetting_CRUD.create(aMES_lwExpR2rSetting);
			if (state) {
				System.out.println("create success");
			}else {
				System.out.println("create fail");
			}
				
		}else {
			// update
			state = MES_lwExpR2rSetting_CRUD.update(aMES_lwExpR2rSetting);
			if (state) {
				System.out.println("update success");
			}else {
				System.out.println("update fail");
			}
		}
		
	}
	
	public ArrayExp(String csvUpperFilePath, String ngFilePath) {
		MeasureFileReader fileService = new MeasureFileReader();
		fileService.setFileHandler(this, csvUpperFilePath, ngFilePath);
		fileService.start();

	}

	@Override
	public void onFileData (MeasureFileDataBase csv) {
		try {
			
			if (Utility.DEBUG == true) { // DEBUG use: to clean database 
				Utility.DEBUG = false;
				T_ArrayExpContinueGlassSet_CRUD.delete("", "", "", "", "", "", "");
				T_LastExpTime_CRUD.delete();
				T_ArrayExpFeedbackHistory_CRUD.delete();
				LogHistory_CRUD.delete();
			}
			
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "Start process file " + csv.getFileName());
			
			// when receive a csv 
			ExpMeasGlass emGlass = ExpMeasGlass.csv2ExpMeasGlass(csv);
			if (emGlass == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Error: emGlass = null");
				return;
			}
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "csv2ExpMeasGlass success: GlassID = " + emGlass.getGlassID());
			
			// check active flag
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
			if (!autoFbkSeting.getActiveFlag().toUpperCase().equals("ON")) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Fail: Glass ID = " + emGlass.getGlassID() + " active flag != on");
				return;
			}
				
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
			averageGlass.setExpSupplier(emGlass.getExpSupplier());
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "AverageMatchGlassList::");
			for (int ind = 0; ind < matchGlassList.size(); ind ++) {
				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, ind + ". Glass ID = " + matchGlassList.get(ind).getGlassID());
			}

			// times the ratio
			averageGlass.multipliedRatio(autoFbkSeting.getRatio());
			
			// createFeedbackFile
			if(averageGlass.getExpSupplier().toUpperCase().equals("NIKON")){
				int intState = creatNikonFeedbackFile(averageGlass);
				if (intState == -1) {
					Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Error: creatNikonFeedbackFile fail");
					return;
				}else Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "creatNikonFeedbackFile success");
				String cmdStr = "cmd /c start " ;
				String workingDirectory = System.getProperty("user.dir");
				cmdStr += workingDirectory + "\\DPS2.exe ";
				cmdStr += "\"" + averageGlass.getExpID() + "\" \"" + averageGlass.getExpRcpName() + "\"";
				if (averageGlass.getOlOrDol().equals("OL"))
					cmdStr += " \"0\"";
				if (averageGlass.getOlOrDol().equals("DOL"))
					cmdStr += " \"2\"";
				Process process = Runtime.getRuntime().exec(cmdStr);
				process.waitFor();
				int exitVal = process.exitValue();
				if (exitVal != 0) {
					Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "DPS create rv5 file fail");
					return;
				}
				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "DPS create rv5 file success");
			}else if(averageGlass.getExpSupplier().toUpperCase().equals("CANON")){
				int intState = createCanonFeedbackFile(averageGlass);
				if (intState == -1) {
					Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Error: creatNikonFeedbackFile fail");
					return;
				}else Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "creatNikonFeedbackFile success");
			}
			
			// save feedback history to DB
			Date feedbackTime = new Date();
			state = T_ArrayExpFeedbackHistory_CRUD.create(averageGlass, "Automation", "R2R", feedbackTime);
			if (!state) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Error: cannot create T_ArrayExpFeedbackHistory_CRUD");
			}
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "create T_ArrayExpFeedbackHistory_CRUD success");
			
			// clean continue glass set
			state =  T_ArrayExpContinueGlassSet_CRUD.delete(averageGlass.getProductName(),
															averageGlass.getExpID(),
															averageGlass.getExpRcpID(),
															averageGlass.getMeasRcpID(),
															averageGlass.getMeasStepID(),
															averageGlass.getAdcOrFdc());
			if (!state) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Error: cannot delete T_ArrayExpContinueGlassSet_CRUD");
			}
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Success: delete T_ArrayExpContinueGlassSet_CRUD success");
			
			// save table to MES
			MES_lwExpR2rSetting aMES_lwExpR2rSetting = new MES_lwExpR2rSetting();
			aMES_lwExpR2rSetting.setEqpId(averageGlass.getExpID());
			aMES_lwExpR2rSetting.setHoldFlag(autoFbkSeting.getHoldFlag());
			aMES_lwExpR2rSetting.setProdId(averageGlass.getProductName());
			aMES_lwExpR2rSetting.setR2rFeedbackTime(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(feedbackTime));
			aMES_lwExpR2rSetting.setRecipeId(averageGlass.getExpRcpID()); 
			state = MES_lwExpR2rSetting_CRUD.create(aMES_lwExpR2rSetting);// don't deal with old data, just add new data
			if (state) {
				Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "create MES_lwExpR2rSetting_CRUD success");
			}else {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "Error: create MES_lwExpR2rSetting_CRUD error");
				return;
			}
		
			return;
		}catch(Exception e) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return;
		}
	}
	private int createCanonFeedbackFile(ExpMeasGlass emGlass) {
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
		String feedbackFileName = emGlass.getGlassID() + "_" + emGlass.getMeasStepID() + "_" + 
								  emGlass.getExpID() + "_" + emGlass.getMeasEqId() + "_" +
								  emGlass.getMeasRcpID() + "_" + timeStamp;
		try {
			// DEBUG use
			//PrintWriter writer = new PrintWriter("C:\\R2RNikonTest\\" + feedbackFileName + ".csv", "UTF-8");
			PrintWriter writer = new PrintWriter("C:\\R2R-FTP\\" + emGlass.getExpID() + "\\" + feedbackFileName + ".csv", "UTF-8");
			writer.println( "Recipe No.," + emGlass.getExpRcpID() ); //emGlass.getExpRcpName()
			writer.println( "Glass ID," + emGlass.getGlassID() );
			writer.println( "Insp. Date," + timeStamp );
			int pointNum = emGlass.getMeasPointList().size();
			writer.println("Measure Point," + pointNum);
			
			List<T_CanonExpSiteNo2ScanNo> siteNo2ScanNoList = T_CanonExpSiteNo2ScanNo_CRUD.read(emGlass.getProductName(), emGlass.getExpStepID());																		 			
			if (siteNo2ScanNoList == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "createCanonFeedbackFile Error: cannot read T_CanonExpSiteNo2ScanNo");
				return -1; 
			}
			
			for(int pInd = 0; pInd < pointNum; pInd++) {
				// Canon X = MCD X
				// Canon Y = MCD Y
				Vector2D point = emGlass.getMeasPointList().get(pInd);
				int siteNo = point.getIndex();
				String CanonX = String.format("%.3f", point.getxAxis() / 1000);
				String CanonY = String.format("%.3f", point.getyAxis() / 1000);
				String CanonOL01 = String.format("%.3f", point.getxValue());
				String CanonOL02 = String.format("%.3f", point.getyValue());
				
				int scanNo = -1;
				for (T_CanonExpSiteNo2ScanNo siteNo2ScanNo: siteNo2ScanNoList) {
					scanNo = siteNo2ScanNo.findScanNo(siteNo);
					if (scanNo != -1) break;
				}
				if (scanNo == -1){
					Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "createCanonFeedbackFile Error: no siteNo T_CanonExpSiteNo2ScanNo");
					return -1; 
				}
				String CanonScanNo = String.valueOf(scanNo);
				writer.println( (pInd + 1) + "," + CanonX + "," + CanonY + "," + CanonOL01 + "," + CanonOL02 + "," + CanonScanNo);
			}
			writer.close();
			return 1;

		}catch(Exception e) {
			System.out.println(e.getMessage());
			return -1;
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
			String [] siteNoStrArr = aGlassSet.getSiteNoList().split(",");
			String [] ol01StrArr = aGlassSet.getOl01List().split(",");
			String [] ol02StrArr = aGlassSet.getOl02List().split(",");
			String [] coordXArr = aGlassSet.getCoordXList().split(",");
			String [] coordYArr = aGlassSet.getCoordYList().split(",");
			List<Vector2D> vecterList = new ArrayList<>(); 
			pointNum = ol01StrArr.length;
			for(int strInd = 0; strInd < pointNum; strInd ++) {
				int siteNo = Integer.parseInt(siteNoStrArr[strInd]);
				double xAsix = Double.parseDouble(coordXArr[strInd]);
				double yAsix = Double.parseDouble(coordYArr[strInd]);
				double ol01 = Double.parseDouble(ol01StrArr[strInd]);
				double ol02 = Double.parseDouble(ol02StrArr[strInd]);
				Vector2D aPoint = new Vector2D(siteNo, xAsix, yAsix, ol01, ol02);
				vecterList.add(aPoint);
			}
			vetorListList.add(vecterList);
		}
		
		// calculate the average of all glass by each point
		List<Vector2D> avgPointList = new ArrayList<>();
		for(int pInd = 0; pInd < pointNum; pInd ++) {
			double xResult = 0, yResult = 0;
			for(List<Vector2D> vecterList: vetorListList) {
				xResult = Arith.add(xResult, vecterList.get(pInd).getxValue());
				yResult = Arith.add(yResult, vecterList.get(pInd).getyValue());
			}
			xResult = Arith.div(xResult, vetorListList.size(), 4);
			yResult = Arith.div(yResult, vetorListList.size(), 4);
			Vector2D vector2d = vetorListList.get(0).get(pInd);
			
			Vector2D aPoint = new Vector2D(vector2d.getIndex(), vector2d.getxAxis(), vector2d.getyAxis(), xResult, yResult);
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
				int result = (int)(o1.getExposureTime() - o2.getExposureTime()); 
				if ( result == 0) {
					return o1.getGlassID().compareTo(o2.getGlassID());
				}
				else return result;
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
		
		// check each of them if it matches feedback condition
		List<Integer> matchGlassList = new ArrayList<>();
		for(int ind = 0; ind < latestExpGlassList.size(); ind++){
			if (latestExpGlassList.get(ind).getFeedbackMode().toUpperCase().equals( aSetting.getFeedbackMode().toUpperCase() ) &&
				latestExpGlassList.get(ind).getAdcOrFdc().toUpperCase().equals( aSetting.getAdcOrFdc().toUpperCase() )){
				matchGlassList.add(ind);
			}
		}
		if (matchGlassList.size() < aSetting.getSampleSize()) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback: matchGlassList.size < aSetting.getSampleSize");
			return null;
		}
		
		// active rule
		if (!aSetting.getActiveRule().toUpperCase().equals("RANDOM") && 
			!aSetting.getActiveRule().toUpperCase().equals("CONTINUE")) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback: active rule != RANDOM or CONTINUE");
			return null;
		}
		
		// prepare combination
		List<List<Integer>> elementList = new ArrayList<List<Integer>>();
		for (int ind = 0; ind < matchGlassList.size(); ind++) {
			elementList.add(Arrays.asList(matchGlassList.get(ind)));
		}
		elementList.sort(new Comparator<List<Integer>>() {
			public int compare(List<Integer> l1, List<Integer> l2) {
				return l1.get(0) - l2.get(0);
			}
		});

		List<List<Integer>> allCombinList = Utility.combination(elementList, aSetting.getSampleSize());
		for (List<Integer> combinList: allCombinList) {
			if (aSetting.getActiveRule().toUpperCase().equals("CONTINUE")) {
				// check if it is continue;
				int isContinue = 1;
				for (int ind = 0; ind < combinList.size(); ind++) {
					if (combinList.get(0) + ind != combinList.get(ind)) {
						isContinue = 0;
						break;
					}
				}
				if(isContinue == 0) continue;			
			}
			List<T_ArrayExpContinueGlassSet> returnGlassList = new ArrayList<>();
			for (Integer cbtionInt: combinList)	
				returnGlassList.add( latestExpGlassList.get(cbtionInt));
			int state = checkSigmaSmallerSetting(returnGlassList, aSetting.getSigma());
			switch (state) {
				case -1: //error
					Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkSigmaSmallerSetting Error");
					return null;
				case 0: continue; // not ok
				case 1: // OK
					Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkSigmaSmallerSetting: sigma < setting");
					return returnGlassList;	
			}
			
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkSigmaSmallerSetting: sigma > setting");
		return null;
	}
	
	private int checkSigmaSmallerSetting(List<T_ArrayExpContinueGlassSet> aGlassSetList, double sigma) {		
		String ol01Str = aGlassSetList.get(0).getOl01List();
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkSigmaSmallerSetting: Glass ID as following:");
		for (int ind = 0; ind < aGlassSetList.size(); ind ++ ) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkSigmaSmallerSetting: " + aGlassSetList.get(ind).getGlassID());
		}
		
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
				Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "checkSigmaSmallerSetting Fail: Point " + strInd + " sigma > setting sigma " + sigma);
				return 0;
			}
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkSigmaSmallerSetting success");
		return 1;
	}
}

