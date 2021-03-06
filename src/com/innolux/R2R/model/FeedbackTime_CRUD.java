package com.innolux.R2R.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.ArrayExp.model.T_LastExpTime;
import com.innolux.R2R.ArrayExp.model.Utility;
import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;


public class FeedbackTime_CRUD {
	private static Logger logger = Logger.getLogger(FeedbackTime_CRUD.class);
	private static GenericDao<FeedbackTime> FeedbackTime_Dao = new JdbcGenericDaoImpl<FeedbackTime>(GlobleVar.R2R_DB);
	
	public static FeedbackTime create(FeedbackTime ft){
		FeedbackTime result = null;
		try{
			FeedbackTime_Dao.save(ft);
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
	
	public static FeedbackTime read(String EqpId,String SubEqpId,String Recipe){
		FeedbackTime result = null;
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!EqpId.equals("")){
				sqlWhereMap.put("EqpId", EqpId);
			}
			if(!SubEqpId.equals("")){
				sqlWhereMap.put("SubEqpId", SubEqpId);
			}
			if(!Recipe.equals("")){
				sqlWhereMap.put("Recipe", Recipe);
			}
			List<FeedbackTime> tmp = FeedbackTime_Dao.findAllByConditions(sqlWhereMap, FeedbackTime.class);
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
	
	public static boolean update(FeedbackTime ft){
		boolean result = false;
		try{
			FeedbackTime_Dao.update(ft);
			result = true;
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
	
//	public static FeedbackTime delete(FeedbackTime ft){
//		FeedbackTime result = null;
//		try{
//			FeedbackTime_Dao.delete(ft.getPrimaryKey(), FeedbackTime.class);
//		}catch(Exception e){
//			logger.error(ToolUtility.StackTrace2String(e));
//		}
//		return result;
//	}
}
