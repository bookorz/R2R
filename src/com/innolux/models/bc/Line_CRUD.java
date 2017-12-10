package com.innolux.models.bc;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcDaoHelper;
import com.innolux.dao.JdbcGenericDaoImpl;

public class Line_CRUD {
	private static Logger logger = Logger.getLogger(Line_CRUD.class);
	
	
	public static Line read(String BCName,String BCIP){
		Line result = null;
		
		String tns = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = " + BCIP
				+ ")(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME =ORCL)))";
		JdbcDaoHelper BC_DB = new JdbcDaoHelper(tns, "innolux", "innoluxabc123", 0);
		
		GenericDao<Line> Line_Dao = new JdbcGenericDaoImpl<Line>(BC_DB);
		try{
			
			
			result = Line_Dao.get(BCName, Line.class);
			
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
}
