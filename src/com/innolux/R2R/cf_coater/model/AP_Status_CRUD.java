package com.innolux.R2R.cf_coater.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.model.MeasureFileData;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class AP_Status_CRUD {
	private static Logger logger = Logger.getLogger(AP_Status_CRUD.class);
	private static GenericDao<AP_Status> AP_Status_Dao = new JdbcGenericDaoImpl<AP_Status>(GlobleVar.R2R_DB);
	
	public static void create(AP_Status ft){
		
		try{
			AP_Status_Dao.save(ft);
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		
	}
	
	public static AP_Status read(String PreEqpId,String PreRecipe){
		AP_Status result = null;
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!PreEqpId.equals("")){
				sqlWhereMap.put("PreEqpId", PreEqpId);
			}
			if(!PreRecipe.equals("")){
				sqlWhereMap.put("PreRecipe", PreRecipe);
			}
			
			List<AP_Status> tmp = AP_Status_Dao.findAllByConditions(sqlWhereMap, AP_Status.class);
			if(tmp.size()!=0){
				result = tmp.get(0);
			}
			
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
	
	public static void update(AP_Status ft){
		try{
			AP_Status_Dao.update(ft);
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
	}
}
