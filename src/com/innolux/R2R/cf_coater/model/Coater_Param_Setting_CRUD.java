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
	
	public static Coater_Param_Setting read(String PPID){
		Coater_Param_Setting result = null;
		try{
			
			result = Coater_Param_Setting_Dao.get(PPID, Coater_Param_Setting.class);
			
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
	
	
}
