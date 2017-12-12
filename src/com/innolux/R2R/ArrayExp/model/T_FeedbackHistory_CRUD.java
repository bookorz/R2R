 package com.innolux.R2R.ArrayExp.model;

import org.apache.log4j.Logger;
import com.innolux.dao.JdbcGenericDaoImpl;
import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;

public class T_FeedbackHistory_CRUD {
	private static Logger logger = Logger.getLogger(T_FeedbackHistory_CRUD.class);
	private static GenericDao<T_FeedbackHistory> T_FeedbackHistory_DAO = 
			new JdbcGenericDaoImpl <T_FeedbackHistory> (GlobleVar.R2R_DB);
	
	public static boolean create(T_FeedbackHistory feedbackHistory){
		try{
			T_FeedbackHistory_DAO.save(feedbackHistory);
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return false;
		}
		return true;
	}
}
