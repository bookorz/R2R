package com.innolux.R2R.cf_coater.model;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class False_Range_Setting_CRUD {
	private static Logger logger = Logger.getLogger(False_Range_Setting_CRUD.class);
	private static GenericDao<False_Range_Setting> False_Range_Setting_Dao = new JdbcGenericDaoImpl<False_Range_Setting>(GlobleVar.R2R_DB);
	
	public static False_Range_Setting read(String PPID){
		False_Range_Setting result = null;
		try{
			
			
			False_Range_Setting_Dao.get(PPID, False_Range_Setting.class);
			
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
}
