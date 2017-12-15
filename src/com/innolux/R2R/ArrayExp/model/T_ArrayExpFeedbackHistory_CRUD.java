package com.innolux.R2R.ArrayExp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class T_ArrayExpFeedbackHistory_CRUD {
	private static Logger logger = Logger.getLogger(T_ArrayExpFeedbackHistory_CRUD.class);
	private static GenericDao<T_ArrayExpFeedbackHistory> T_ArrayExpFeedbackHistory_DAO = 
			new JdbcGenericDaoImpl <T_ArrayExpFeedbackHistory> (GlobleVar.R2R_DB);
	
	public static boolean create(ExpMeasGlass aGlass, long feedbackTime){
		try{
			T_ArrayExpFeedbackHistory feedbackHistory = new T_ArrayExpFeedbackHistory();
			
			String str1 = aGlass.getProductName();
			if (str1 == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "create T_ArrayExpFeedbackHistory_CRUD Error");
			}
			feedbackHistory.setProduct(str1);

			str1 = aGlass.getExpID();
			if (str1 == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "create T_ArrayExpFeedbackHistory_CRUD Error");
			}
			feedbackHistory.setExp_ID(str1);

			str1 = aGlass.getExpRcpID();
			if (str1 == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "create T_ArrayExpFeedbackHistory_CRUD Error");
			}
			feedbackHistory.setExp_Rcp_ID(str1);

			str1 = aGlass.getMeasStepID();
			if (str1 == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "create T_ArrayExpFeedbackHistory_CRUD Error");
			}
			feedbackHistory.setMea_Step_ID(str1);

			str1 = aGlass.getMeasRcpID();
			if (str1 == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "create T_ArrayExpFeedbackHistory_CRUD Error");
			}
			feedbackHistory.setMea_Rcp_ID(str1);

			str1 = aGlass.getAdcOrFdc();
			if (str1 == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "create T_ArrayExpFeedbackHistory_CRUD Error");
			}
			feedbackHistory.setAdc_Or_Fdc(str1);		
			
			feedbackHistory.setFeedback_Time(feedbackTime);
			
			T_ArrayExpFeedbackHistory_DAO.save(feedbackHistory);
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return false;
		}
		return true;
	}
	
	public static T_ArrayExpFeedbackHistory read(ExpMeasGlass aGlass){
		String product = aGlass.getProductName();
		String expId = aGlass.getExpID();
		String expRcpId = aGlass.getExpRcpID();
		String meaRcpId = aGlass.getMeasRcpID();
		String meaStepId = aGlass.getMeasStepID();
		String adcOrFdc = aGlass.getAdcOrFdc();
		return T_ArrayExpFeedbackHistory_CRUD.read(product, expId, expRcpId, meaRcpId, meaStepId, adcOrFdc);
	}	
	
	public static T_ArrayExpFeedbackHistory read(String Product, String ExpID, String ExpRcpID,
			String MeaRcpID, String MeaStepID, String AdcOrFdc){
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!Product.equals("")){
				sqlWhereMap.put("Product", Product);
			}
			if(!ExpID.equals("")){
				sqlWhereMap.put("Exp_ID", ExpID);
			}
			if(!ExpRcpID.equals("")){
				sqlWhereMap.put("Exp_Rcp_ID", ExpRcpID);
			}
			if(!MeaRcpID.equals("")){
				sqlWhereMap.put("Mea_Rcp_ID", MeaRcpID);
			}
			if(!MeaStepID.equals("")){
				sqlWhereMap.put("Mea_Step_ID", MeaStepID);
			}
			if(!AdcOrFdc.equals("")){
				sqlWhereMap.put("Adc_Or_Fdc", AdcOrFdc);
			}
			List<T_ArrayExpFeedbackHistory> tmp = T_ArrayExpFeedbackHistory_DAO.findAllByConditions(sqlWhereMap, T_ArrayExpFeedbackHistory.class);
			if (tmp == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "T_ArrayExpFeedbackHistory read Error: tmp = null");
				return null;
			}else if (tmp.size() == 0) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "T_ArrayExpFeedbackHistory read Error: tmp.size = 0");
				return null;
			}if (tmp.size() != 0) {
				return tmp.get(0);
			}
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return null;
		}
		return null;
	}		
}
