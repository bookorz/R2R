package com.innolux.R2R.ArrayExp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class MES_fwamtcomptrackhistory_CRUD {
	private static Logger logger = Logger.getLogger(MES_fwamtcomptrackhistory_CRUD.class);
	private static GenericDao<MES_fwamtcomptrackhistory> MES_fwamtcomptrackhistory_DAO = 
			new JdbcGenericDaoImpl <MES_fwamtcomptrackhistory> (GlobleVar.ARRAYMesDB);
	
	public static List<MES_fwamtcomptrackhistory> read(String componentId,String eqpId, String activity,String stepSeq){
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!componentId.equals("")){
				sqlWhereMap.put("componentId", componentId);
			}
			if(!eqpId.equals("")){
				sqlWhereMap.put("eqpId", eqpId);
			}
			if(!activity.equals("")){
				sqlWhereMap.put("activity", activity);
			}
			if(!stepSeq.equals("")){
				sqlWhereMap.put("stepId", stepSeq);
			}
			List<MES_fwamtcomptrackhistory> tmp = MES_fwamtcomptrackhistory_DAO.findAllByConditions(sqlWhereMap, MES_fwamtcomptrackhistory.class);
			return tmp;
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return null;
		}
	}
}
