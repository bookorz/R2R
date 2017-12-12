package com.innolux.R2R.cf_coater.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.model.MeasureFileData_CRUD;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class Last_Process_Time_CRUD {
	private static Logger logger = Logger.getLogger(MeasureFileData_CRUD.class);
	private static GenericDao<Last_Process_Time> Last_Process_Time_Dao = new JdbcGenericDaoImpl<Last_Process_Time>(GlobleVar.R2R_DB);
	
	public static Last_Process_Time read(String SubEqpId, String RecipeNo){
		Last_Process_Time result = null;
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!SubEqpId.equals("")){
				sqlWhereMap.put("SubEqpId", SubEqpId);
			}
			if(!RecipeNo.equals("")){
				sqlWhereMap.put("RecipeNo", RecipeNo);
			}
			
			List<Last_Process_Time> tmp = Last_Process_Time_Dao.findAllByConditions(sqlWhereMap, Last_Process_Time.class);
			if(tmp.size()!=0){
				result = tmp.get(0);
			}
			
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}	
	
	public static boolean update(Last_Process_Time t){
		boolean result = false;
		try{
			Last_Process_Time_Dao.update(t);
			result = true;
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
}
