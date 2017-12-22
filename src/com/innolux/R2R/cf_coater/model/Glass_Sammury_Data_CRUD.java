package com.innolux.R2R.cf_coater.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class Glass_Sammury_Data_CRUD {
	private static Logger logger = Logger.getLogger(Coater_Param_Setting.class);
	private static GenericDao<Glass_Sammury_Data> Glass_Sammury_Data_Dao = new JdbcGenericDaoImpl<Glass_Sammury_Data>(GlobleVar.R2R_DB);
	
	public static Glass_Sammury_Data create(Glass_Sammury_Data ft){
		Glass_Sammury_Data result = null;
		try{
			Glass_Sammury_Data_Dao.save(ft);
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
	
	public static List<Glass_Sammury_Data> read(String EqpId,String RecipeNo){
		List<Glass_Sammury_Data> result = null;
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!EqpId.equals("")){
				sqlWhereMap.put("EqpId", EqpId);
			}
			if(!RecipeNo.equals("")){
				sqlWhereMap.put("Recipe_No", RecipeNo);
			}
			
			result = Glass_Sammury_Data_Dao.findAllByConditions(sqlWhereMap, Glass_Sammury_Data.class);
			
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}	
	
	public static boolean delete(String EqpId,String RecipeNo){
		
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!EqpId.equals("")){
				sqlWhereMap.put("EqpId", EqpId);
			}
		
			if(!RecipeNo.equals("")){
				sqlWhereMap.put("Recipe_No", RecipeNo);
			}
			
			
			Glass_Sammury_Data_Dao.deleteAllByConditions(sqlWhereMap, Glass_Sammury_Data.class);
			return true;
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
			return false;
		}
		
	}
	
}
