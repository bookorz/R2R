package com.innolux.R2R.ArrayExp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.innolux.dao.JdbcGenericDaoImpl;
import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;

public class T_AutoFeedbackSetting_CRUD {
	private static Logger logger = Logger.getLogger(T_AutoFeedbackSetting_CRUD.class);
	private static GenericDao<T_AutoFeedbackSetting> T_AutoFeedbackSetting_DAO = 
			new JdbcGenericDaoImpl <T_AutoFeedbackSetting> (GlobleVar.R2R_DB);
	
	public static T_AutoFeedbackSetting read(String Product, String ExpID, String ExpRcpID,
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
			List<T_AutoFeedbackSetting> tmp = T_AutoFeedbackSetting_DAO.findAllByConditions(sqlWhereMap, T_AutoFeedbackSetting.class);
			if (tmp == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "T_AutoFeedbackSetting read Error: tmp = null");
				return null;
			}else if (tmp.size() == 0) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "T_AutoFeedbackSetting read Error: tmp.size = 0");
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
