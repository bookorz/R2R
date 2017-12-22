package com.innolux.R2R.ArrayExp.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.model.LogHistory;
import com.innolux.R2R.model.LogHistory_CRUD;

public class Utility {
	public static boolean DEBUG = true;
	private static Logger logger = Logger.getLogger(Utility.class);
	
	public static void main(String [] argv) {
		
		try {
			List<List<Integer>>elementList = new ArrayList<List<Integer>>();
			for (int ind = 5; ind >= 1; ind -= 1) {
				elementList.add(Arrays.asList(ind));
			}
			elementList.sort(new Comparator<List<Integer>>() {
				public int compare(List<Integer> l1, List<Integer> l2) {
					return l1.get(0) - l2.get(0);
				}
			});
			List<List<Integer>> combinationList = combination(elementList, 3);
			
			for (List<Integer> combinList: combinationList) {
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
				
			int a = 1;
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * provided combination 
	 * @param elementList 
	 * @param unfinishLen
	 * @return List<List<T>>
	 */
	public static List<List<Integer>> combination(List<List<Integer>> elementList, int unfinishLen){
		if (elementList == null) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "combination Error: elementList = null");
			return null;
		}
		if (unfinishLen == 1) {
			return elementList;
		}
	
		List<List<Integer>> resultList = new ArrayList<List<Integer>>();
		List<List<Integer>> subCombinationLList = combination(elementList, unfinishLen - 1);
		for (List<Integer> subEleList: subCombinationLList) {
			int lastSubEle = subEleList.get(subEleList.size() - 1); // get last element
			int startInd = elementList.indexOf(Arrays.asList(lastSubEle)); // get corresponding index
			for (int ind = startInd + 1; ind < elementList.size(); ind++) {
				List<Integer> newList = new ArrayList<Integer>(subEleList);
				newList.add(elementList.get(ind).get(0));
				resultList.add(newList);
			} 
		}
		return resultList;
	}
			
	public static Date dateStr2Date(String dateStr) {
		SimpleDateFormat simDateFormat = new SimpleDateFormat();
		try {
			simDateFormat.applyPattern("yyyyMMdd HHmmssSSS");
			Date date = simDateFormat.parse(dateStr);
			return date;
		}catch(Exception e) {
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return null;
		}
	}
	
	public static void saveToLogHistoryDB(String errLevel, String logString) {
		LogHistory alogHisty = new LogHistory();
		alogHisty.setR2R_Name("ArrayExp");
		alogHisty.setTime(String.valueOf(Calendar.getInstance().getTime().getTime()));
		alogHisty.setLevel(errLevel);
		alogHisty.setLogString(logString);
		LogHistory_CRUD.create(alogHisty);
		
		return;
	}
	
}
