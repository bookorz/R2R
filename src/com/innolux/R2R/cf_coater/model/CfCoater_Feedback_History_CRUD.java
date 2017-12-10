package com.innolux.R2R.cf_coater.model;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class CfCoater_Feedback_History_CRUD {
	private static Logger logger = Logger.getLogger(CfCoater_Feedback_History_CRUD.class);
	private static GenericDao<CfCoater_Feedback_History> CfCoater_Feedback_History_Dao = new JdbcGenericDaoImpl<CfCoater_Feedback_History>(GlobleVar.R2R_DB);
	
	public static boolean create(CfCoater_Feedback_History loghisty){
		boolean result = false;
		try{
			CfCoater_Feedback_History_Dao.save(loghisty);
			result = true;
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
}
