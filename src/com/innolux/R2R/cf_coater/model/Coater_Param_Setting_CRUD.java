package com.innolux.R2R.cf_coater.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.ArrayExp.model.Utility;
import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class Coater_Param_Setting_CRUD {
	private static Logger logger = Logger.getLogger(Coater_Param_Setting.class);
	private static GenericDao<Coater_Param_Setting> Coater_Param_Setting_Dao = new JdbcGenericDaoImpl<Coater_Param_Setting>(GlobleVar.R2R_DB);
	
	public static boolean create(Coater_Param_Setting ft){
		boolean result = false;
		try{
			Coater_Param_Setting_Dao.save(ft);
			result = true;
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
	
	public static Coater_Param_Setting read(String EqpId,String Recipe){
		Coater_Param_Setting result = null;
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!EqpId.equals("")){
				sqlWhereMap.put("EqpId", EqpId);
			}
			if(!Recipe.equals("")){
				sqlWhereMap.put("Recipe", Recipe);
			}
			List<Coater_Param_Setting> tmp = Coater_Param_Setting_Dao.findAllByConditions(sqlWhereMap, Coater_Param_Setting.class);
			if (tmp == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "FeedbackTime read Error: tmp = null");
				return null;
			}else if (tmp.size() == 0) {
				Utility.saveToLogHistoryDB(GlobleVar.LogDebugType, "FeedbackTime read: tmp.size = 0");
				return null;
			}if (tmp.size() != 0) {
				return tmp.get(0);
			}
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
	
	
}
