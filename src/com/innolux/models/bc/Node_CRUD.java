package com.innolux.models.bc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcDaoHelper;
import com.innolux.dao.JdbcGenericDaoImpl;

public class Node_CRUD {
	private static Logger logger = Logger.getLogger(Node_CRUD.class);
	
	public static Node read(String BCNo,String BCLineNo,String FabType,String EqName,String BCIP){
		Node result = null;
		
		String tns = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = " + BCIP
				+ ")(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME =ORCL)))";
		JdbcDaoHelper BC_DB = new JdbcDaoHelper(tns, "innolux", "innoluxabc123", 0);
		
		GenericDao<Node> Node_Dao = new JdbcGenericDaoImpl<Node>(BC_DB);
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!BCNo.equals("")){
				sqlWhereMap.put("BCNo", BCNo);
			}
			if(!BCLineNo.equals("")){
				sqlWhereMap.put("BCLineNo", BCLineNo);
			}
			if(!FabType.equals("")){
				sqlWhereMap.put("FabType", FabType);
			}
			if(!EqName.equals("")){
				sqlWhereMap.put("EqName", EqName);
			}
			
			List<Node> tmp = Node_Dao.findAllByConditions(sqlWhereMap, Node.class);
			if(tmp.size()!=0){
				result = tmp.get(0);
			}
			
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
}
