package com.innolux.R2R.ArrayExp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.innolux.dao.JdbcGenericDaoImpl;
import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;

public class T_CanonExpSiteNo2ScanNo_CRUD {
	private static Logger logger = Logger.getLogger(T_CanonExpSiteNo2ScanNo_CRUD.class);
	private static GenericDao<T_CanonExpSiteNo2ScanNo> T_CanonExpSiteNo2ScanNo_DAO = 
			new JdbcGenericDaoImpl <T_CanonExpSiteNo2ScanNo> (GlobleVar.R2R_DB);
	
	public static List<T_CanonExpSiteNo2ScanNo> read(String Product, String Exp_Step_ID){		
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!Product.equals("")){
				sqlWhereMap.put("Product", Product);
			}
			
			if(!Exp_Step_ID.equals("")){
				sqlWhereMap.put("ExpStepID", Exp_Step_ID);
			}
			
			List<T_CanonExpSiteNo2ScanNo> tmp = T_CanonExpSiteNo2ScanNo_DAO.findAllByConditions(
				sqlWhereMap, T_CanonExpSiteNo2ScanNo.class);
			if(tmp.size()!= 0) return tmp;
			return null;
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return null;
		}
	}

}
