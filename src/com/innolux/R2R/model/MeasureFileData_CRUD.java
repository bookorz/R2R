package com.innolux.R2R.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class MeasureFileData_CRUD {
	private static Logger logger = Logger.getLogger(MeasureFileData_CRUD.class);
	private static GenericDao<MeasureFileData> MeasureFileData_Dao = new JdbcGenericDaoImpl<MeasureFileData>(GlobleVar.R2R_DB);
	
	public static MeasureFileData create(MeasureFileData ft){
		MeasureFileData result = null;
		try{
			MeasureFileData_Dao.save(ft);
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
	
	public static MeasureFileData read(String EqpId,String SubEqpId,String Recipe){
		MeasureFileData result = null;
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
			MeasureFileData_Dao.findAllByConditions(sqlWhereMap, MeasureFileData.class);
			
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
	
	public static MeasureFileData update(MeasureFileData ft){
		MeasureFileData result = null;
		try{
			MeasureFileData_Dao.update(ft);
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
	
	public static MeasureFileData delete(String PreEqpId,String PreEqpRecipe){
		MeasureFileData result = null;
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!PreEqpId.equals("")){
				sqlWhereMap.put("PreEqpId", PreEqpId);
			}
			if(!PreEqpRecipe.equals("")){
				sqlWhereMap.put("PreEqpRecipe", PreEqpRecipe);
			}
			
			MeasureFileData_Dao.deleteAllByConditions(sqlWhereMap, MeasureFileData.class);
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
}
