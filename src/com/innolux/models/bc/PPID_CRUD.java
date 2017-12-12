package com.innolux.models.bc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.cf_coater.model.Last_Process_Time;
import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.model.MeasureFileData_CRUD;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class PPID_CRUD {
	private static Logger logger = Logger.getLogger(PPID_CRUD.class);
	private static GenericDao<PPID> PPID_Dao = new JdbcGenericDaoImpl<PPID>(GlobleVar.R2R_DB);
	
	public static PPID read(String BCNo,String LineNo,String FabType,String PPID, String NodeNo){
		PPID result = null;
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!BCNo.equals("")){
				sqlWhereMap.put("BCNo", BCNo);
			}
			if(!LineNo.equals("")){
				sqlWhereMap.put("LineNo", LineNo);
			}
			if(!FabType.equals("")){
				sqlWhereMap.put("FabType", FabType);
			}
			if(!PPID.equals("")){
				sqlWhereMap.put("PPID", PPID);
			}
			if(!NodeNo.equals("")){
				sqlWhereMap.put("NodeNo", NodeNo);
			}
			
			
			List<PPID> tmp = PPID_Dao.findAllByConditions(sqlWhereMap, PPID.class);
			if(tmp.size()!=0){
				result = tmp.get(0);
			}
			
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}	
}
