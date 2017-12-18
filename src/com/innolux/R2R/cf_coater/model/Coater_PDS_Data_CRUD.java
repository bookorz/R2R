package com.innolux.R2R.cf_coater.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class Coater_PDS_Data_CRUD {
	private static Logger logger = Logger.getLogger(Coater_PDS_Data_CRUD.class);
	private static GenericDao<Coater_PDS_Data> Coater_PDS_Data_Dao = new JdbcGenericDaoImpl<Coater_PDS_Data>(GlobleVar.R2R_DB);
	
	public static Coater_PDS_Data create(Coater_PDS_Data ft){
		Coater_PDS_Data result = null;
		try{
			Coater_PDS_Data_Dao.save(ft);
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
	
	public static Coater_PDS_Data read(String EqpId,String PPID){
		Coater_PDS_Data result = null;
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!EqpId.equals("")){
				sqlWhereMap.put("EqpId", EqpId);
			}
			if(!PPID.equals("")){
				sqlWhereMap.put("PPID", PPID);
			}
			
			List<Coater_PDS_Data> tmp = Coater_PDS_Data_Dao.findAllByConditions(sqlWhereMap, Coater_PDS_Data.class);
			
			if(tmp.size()!=0){
				result = tmp.get(0);
			}
			
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}	
	
	public static boolean update(Coater_PDS_Data ft){
		boolean result = false;
		try{
			Coater_PDS_Data_Dao.update(ft);
			result = true;
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
}
