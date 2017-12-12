package com.innolux.R2R.ArrayExp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class T_ArrayExpCurrentState_CRUD {
	private static Logger logger = Logger.getLogger(T_ArrayExpCurrentState_CRUD.class);
	private static GenericDao<T_ArrayExpCurrentState> T_ArrayExpCurrentState_DAO = 
			new JdbcGenericDaoImpl <T_ArrayExpCurrentState> (GlobleVar.R2R_DB);
			
	public static boolean create(T_ArrayExpCurrentState aState){
		try{
			T_ArrayExpCurrentState_DAO.save(aState);
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return false;
		}
		return true;
	}

	public static T_ArrayExpCurrentState read(String Product, String ExpID, String ExpRcpID,
			String MeaRcpID, String MeaStepID, int AdcOrFdc){
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!Product.equals("")){
				sqlWhereMap.put("Product", Product);
			}
			if(!ExpID.equals("")){
				sqlWhereMap.put("ExpID", ExpID);
			}
			if(!ExpRcpID.equals("")){
				sqlWhereMap.put("ExpRcpID", ExpRcpID);
			}
			if(!MeaRcpID.equals("")){
				sqlWhereMap.put("MeaRcpID", MeaRcpID);
			}
			if(!MeaStepID.equals("")){
				sqlWhereMap.put("MeaStepID", MeaStepID);
			}
			if(AdcOrFdc != 0){
				sqlWhereMap.put("AdcOrFdc", AdcOrFdc);
			}
			List<T_ArrayExpCurrentState> tmp = T_ArrayExpCurrentState_DAO.findAllByConditions(sqlWhereMap, T_ArrayExpCurrentState.class);
			if(tmp.size()!=0) {
				return tmp.get(0);
			}
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return null;
		}
		return null;
	}

	public static boolean update(T_ArrayExpCurrentState aState){
		try{
			T_ArrayExpCurrentState_DAO.update(aState);
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return false;
		}
		return true;
	}
}
