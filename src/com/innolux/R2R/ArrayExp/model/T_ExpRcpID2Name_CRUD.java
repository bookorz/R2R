package com.innolux.R2R.ArrayExp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.innolux.dao.JdbcGenericDaoImpl;
import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.model.MeasureFileData;
import com.innolux.dao.GenericDao;

public class T_ExpRcpID2Name_CRUD {
	private static Logger logger = Logger.getLogger(T_ExpRcpID2Name_CRUD.class);
	private static GenericDao<T_ExpRcpID2Name> T_ExpRcpID2Name_DAO = 
			new JdbcGenericDaoImpl <T_ExpRcpID2Name> (GlobleVar.R2R_DB);
	
	public static T_ExpRcpID2Name read(String Product, String ExpStepID, String ExpRcpID){
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!Product.equals("")){
				sqlWhereMap.put("Product", Product);
			}
			if(!ExpStepID.equals("")){
				sqlWhereMap.put("Exp_Step_ID", ExpStepID);
			}
			if(!ExpRcpID.equals("")){
				sqlWhereMap.put("Exp_Rcp_ID", ExpRcpID);
			}
			
			List<T_ExpRcpID2Name> tmp = T_ExpRcpID2Name_DAO.findAllByConditions(sqlWhereMap, T_ExpRcpID2Name.class);
			
			if (tmp == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "T_ExpRcpID2Name read Error: tmp = null");
				return null;
			}else if (tmp.size() == 0) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "T_ExpRcpID2Name read Error: tmp.size = 0");
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
}
