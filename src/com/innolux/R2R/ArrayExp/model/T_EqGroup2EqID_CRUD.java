package com.innolux.R2R.ArrayExp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class T_EqGroup2EqID_CRUD {
	private static Logger logger = Logger.getLogger(T_EqGroup2EqID_CRUD.class);
	private static GenericDao<T_EqGroup2EqID> T_EqGroup2EqID_DAO = 
			new JdbcGenericDaoImpl <T_EqGroup2EqID> (GlobleVar.R2R_DB);
	
	public static T_EqGroup2EqID read(String EqID){
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!EqID.equals("")){
				sqlWhereMap.put("EqID", EqID);
			}
			List<T_EqGroup2EqID> tmp = T_EqGroup2EqID_DAO.findAllByConditions(sqlWhereMap, T_EqGroup2EqID.class);
			if(tmp.size()!=0) {
				return tmp.get(0);
			}
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return null;
	}
}
