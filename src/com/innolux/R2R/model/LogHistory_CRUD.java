package com.innolux.R2R.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.ArrayExp.model.T_ArrayExpFeedbackHistory;
import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class LogHistory_CRUD {
	private static Logger logger = Logger.getLogger(LogHistory_CRUD.class);
	private static GenericDao<LogHistory> LogHistory_Dao = new JdbcGenericDaoImpl<LogHistory>(GlobleVar.R2R_DB);
	
	public static boolean create(LogHistory loghisty){
		boolean result = false;
		try{
			LogHistory_Dao.save(loghisty);
			result = true;
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
	public static boolean delete(){
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			LogHistory_Dao.deleteAllByConditions(sqlWhereMap, LogHistory.class);
			return true;
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
			return false;
		}

	}
}
