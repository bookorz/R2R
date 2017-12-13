package com.innolux.R2R.handler;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import com.innolux.R2R.ArrayExp.model.ExpMeasGlass;
import com.innolux.R2R.ArrayExp.model.T_ArrayExpCurrentState;
import com.innolux.R2R.ArrayExp.model.T_ArrayExpCurrentState_CRUD;
import com.innolux.R2R.ArrayExp.model.T_AutoFeedbackSetting;
import com.innolux.R2R.ArrayExp.model.T_AutoFeedbackSetting_CRUD;
import com.innolux.R2R.ArrayExp.model.Utility;
import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.base.MeasureFileDataBase;
import com.innolux.R2R.interfaces.IFileData;
import com.innolux.services.MeasureFileReader;

public class ArrayExp implements IFileData{
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
		
		boolean isDataVaild = emGlass.checkIsDataVaild(); //#TODO
		if(!isDataVaild) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "ArrayExp onFileData Error: glass data not vaild for feedback");
			return;
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "onFileData: glass data is vaild");
		
		// add glass to target Glass set 
		boolean status = csv.StoreFile( 
									   emGlass.getExpID(), 
							 
									   emGlass.getExpRcpID());
		if (!status) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "ArrayExp onFileData Error: cannot add glass to target Glass set");
			return;
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "onFileData: add glass to target Glass set success");
		
		// count + 1
		T_ArrayExpCurrentState arrayExpCurrentState = T_ArrayExpCurrentState_CRUD.read(emGlass.getProductName(), emGlass.getExpID(), emGlass.getExpRcpID(), emGlass.getMeasRcpID(), emGlass.getMeasStepID(), emGlass.getAdcOrFdc());
		if (arrayExpCurrentState == null) {
			// create
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "ArrayExp onFileData fail: cannot read T_ArrayExpCurrentState");
			T_ArrayExpCurrentState newState = new T_ArrayExpCurrentState(emGlass);
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
		int startState = checkStartFeedback(emGlass);
		if (startState == -1) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "ArrayExp onFileData Error: checkStartFeedback Error");
			return;
		}else if (startState == 0) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "ArrayExp onFileData: dont need to start Feedback");
			return;
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "ArrayExp onFileData: need to start Feedback");
//
//		feedbackFile = null;
//		if(emGlass.expSupplier.equal("Nikon")){
//			feedbackFile = createNikonFeedbackFile(emGlass);
//			sendFdbkFile2Canon(feedbackFile);
//		}else if(emGlass.expSupplier.equal("Canon")){
//			feedbackFile = createCanonFeedbackFile(emGlass);
//			sendFdbkFile2Nanon(feedbackFile);
//		}
		// save feedback history to DB
	}	

	public int checkStartFeedback(ExpMeasGlass aGlass) {
		// get number of glass in target glass set
		T_ArrayExpCurrentState aState = T_ArrayExpCurrentState_CRUD.read(aGlass.getProductName(), aGlass.getExpID(), aGlass.getExpRcpID(), aGlass.getMeasRcpID(), aGlass.getMeasStepID(), aGlass.getAdcOrFdc());
		if (aState == null) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkStartFeedback Error: cannot read T_ArrayExpCurrentState");
			return -1;
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback: read T_ArrayExpCurrentState success");
		aState.getCount();
		
		// get setting
		T_AutoFeedbackSetting aSetting = T_AutoFeedbackSetting_CRUD.read(aGlass.getProductName(), aGlass.getExpID(), aGlass.getExpRcpID(), aGlass.getMeasRcpID(), aGlass.getMeasStepID(), aGlass.getAdcOrFdc());
		if (aSetting == null) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkStartFeedback Error: cannot read T_AutoFeedbackSetting");
			return -1;
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback: read T_AutoFeedbackSetting success");
		aSetting.getPopulationSize();
		
		if (aState.getCount() < aSetting.getPopulationSize()) {
			return 0;
		}
			
		List<MeasureFileDataBase> csvList = MeasureFileReader.GetAllFiles(aGlass.getMeasEqId(), aGlass.getMeasSubEqId(), aGlass.getMeasRcpID(), aGlass.getExpID(), "", aGlass.getExpRcpID());
		if (csvList == null) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "checkStartFeedback Error: get MeasureFileReader file Error");
			return -1;
		}
		Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback: get MeasureFileReader file success");
			
		if (csvList.size() < aSetting.getSampleSize()) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback fail: number of target glass < sample size");
			return 0;
		}
		if (csvList.size() < aSetting.getPopulationSize()) {
			Utility.saveToLogHistoryDB(GlobleVar.LogInfoType, "checkStartFeedback fail: number of target glass < Population size");
			return 0;
		}

		// MeasureFileDataBase csv to ExpMeasGlass
		for(MeasureFileDataBase aCsv: csvList){
			
		}
		//#TODO
		// ordering the glassList by exp time
//		glassList.stream().sorted(Comparator.comparing(ClassName::getFieldName).reversed());
//			
//		// get first population size glass in glassList
//		// check each of them if it needs feedback
//			// save the need feedback glass index into list
//			// sum the glass who needs feedback
//		// if (sum < sample.size()) return false;
//		// checkActiveRule
//		
//		int populationSize = aState.getCount();
//		int sampleSize = aSetting.getSampleSize();
//		boolean isMatch = checkGlassMatchFeedbackCondition(aGlass, aSetting);

		return 1;
	}
	private boolean checkGlassMatchFeedbackCondition(ExpMeasGlass aGlass, T_AutoFeedbackSetting aSetting){
		return false;
	}
}

