package com.innolux.models.bc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcDaoHelper;
import com.innolux.dao.JdbcGenericDaoImpl;

public class PPID_CRUD {
	private static Logger logger = Logger.getLogger(PPID_CRUD.class);

	
	public static PPID read(String BCNo,String LineNo,String FabType,String PPID, String NodeNo,String BCIP){
		PPID result = null;
		
		String tns = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = " + BCIP
				+ ")(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME =ORCL)))";
		JdbcDaoHelper BC_DB = new JdbcDaoHelper(tns, "innolux", "innoluxabc123", 0);
		
		GenericDao<PPID> PPID_Dao = new JdbcGenericDaoImpl<PPID>(BC_DB);
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!BCNo.equals("")){
				sqlWhereMap.put("BCNo", BCNo);
			}
			if(!LineNo.equals("")){
				sqlWhereMap.put("BCLineNo", LineNo);
			}
			if(!FabType.equals("")){
				sqlWhereMap.put("FabType", FabType);
			}
			if(!PPID.equals("")){
				sqlWhereMap.put("PPID", PPID);
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
