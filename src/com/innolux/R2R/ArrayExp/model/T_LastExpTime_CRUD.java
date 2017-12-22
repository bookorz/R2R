package com.innolux.R2R.ArrayExp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class T_LastExpTime_CRUD {
	private static Logger logger = Logger.getLogger(T_LastExpTime_CRUD.class);
	private static GenericDao<T_LastExpTime> T_LastExpTime_DAO = 
			new JdbcGenericDaoImpl <T_LastExpTime> (GlobleVar.R2R_DB);
	
	public static boolean create(T_LastExpTime expTimeHisty){
		try{
			T_LastExpTime_DAO.save(expTimeHisty);
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return false;
		}
		return true;
	}
	
	public static T_LastExpTime read(String expId, String rcpId){
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!expId.equals("")){
				sqlWhereMap.put("exp_Id", expId);
			}
			if(!rcpId.equals("")){
				sqlWhereMap.put("rcp_Id", rcpId);
			}
			
			List<T_LastExpTime> tmp = T_LastExpTime_DAO.findAllByConditions(sqlWhereMap, T_LastExpTime.class);
			if (tmp == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "T_LastExpTime read Error: tmp = null");
				return null;
			}else if (tmp.size() == 0) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "T_LastExpTime read Error: tmp.size = 0");
				return null;
			}if (tmp.size() != 0) {
				return tmp.get(0);
			}
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return null;
		}
		return null;
	}
	
	public static boolean update(T_LastExpTime lastExpTime){
		try{
			T_LastExpTime_DAO.update(lastExpTime);
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return false;
		}
		return true;
	}
	
	public static boolean delete(){
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			T_LastExpTime_DAO.deleteAllByConditions(sqlWhereMap, T_LastExpTime.class);
			return true;
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
			return false;
		}

	}
}
