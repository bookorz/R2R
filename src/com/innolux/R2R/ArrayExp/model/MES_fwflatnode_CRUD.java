package com.innolux.R2R.ArrayExp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class MES_fwflatnode_CRUD {
	private static Logger logger = Logger.getLogger(MES_fwflatnode_CRUD.class);
	private static GenericDao<MES_fwflatnode> MES_fwflatnode_DAO = 
			new JdbcGenericDaoImpl <MES_fwflatnode> (GlobleVar.ARRAYMesDB);
			
	public static MES_fwflatnode read(String stepSeq){
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!stepSeq.equals("")){
				sqlWhereMap.put("stepSeq", stepSeq);
			}
			
			List<MES_fwflatnode> tmp = MES_fwflatnode_DAO.findAllByConditions(sqlWhereMap, MES_fwflatnode.class);
			if(tmp.size()!=0) {
				return tmp.get(0);
			}
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return null;
		}
		return null;
	}
}
