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
										 String MeaRcpID, String MeaStepID, int AdcOrFdc){
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!Product.equals("")){
				sqlWhereMap.put("Product", Product);
			}
			if(!ExpID.equals("")){
				sqlWhereMap.put("ExpID", ExpID);
			}
			if(!ExpRcpID.equals("")){
				sqlWhereMap.put("ExpRcpID", ExpRcpID);
			}
			if(!MeaRcpID.equals("")){
				sqlWhereMap.put("MeaRcpID", MeaRcpID);
			}
			if(!MeaStepID.equals("")){
				sqlWhereMap.put("MeaStepID", MeaStepID);
			}
			if(AdcOrFdc != 0){
				sqlWhereMap.put("AdcOrFdc", MeaStepID);
			}
			List<T_AutoFeedbackSetting> tmp = T_AutoFeedbackSetting_DAO.findAllByConditions(sqlWhereMap, T_AutoFeedbackSetting.class);
			if(tmp.size()!=0) {
				return tmp.get(0);
			}
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return null;
	}
}
