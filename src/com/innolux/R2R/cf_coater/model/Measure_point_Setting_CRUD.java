package com.innolux.R2R.cf_coater.model;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class Measure_point_Setting_CRUD {
	private static Logger logger = Logger.getLogger(Measure_point_Setting_CRUD.class);
	private static GenericDao<Measure_point_Setting> Measure_point_Setting_Dao = new JdbcGenericDaoImpl<Measure_point_Setting>(GlobleVar.R2R_DB);
	
	public static Measure_point_Setting read(String RecipeNo){
		Measure_point_Setting result = null;
		try{
			
			
			Measure_point_Setting_Dao.get(RecipeNo, Measure_point_Setting.class);
			
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
}
