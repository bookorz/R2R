package com.innolux.R2R.model;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class LogHistory_CRUD {
	private static Logger logger = Logger.getLogger(FeedbackTime_CRUD.class);
	private static GenericDao<LogHistory> LogHistory_Dao = new JdbcGenericDaoImpl<LogHistory>(GlobleVar.R2R_DB);
	
	public static LogHistory create(LogHistory loghisty){
		LogHistory result = null;
		try{
			LogHistory_Dao.save(loghisty);
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
}
