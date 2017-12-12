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
	
	public static T_CanonExpSiteNo2ScanNo read(String Primarykey, String Product, 
			String Exp_Step_ID, int SiteNo, int ScanNo ){		
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!Primarykey.equals("")){
				sqlWhereMap.put("Primarykey", Primarykey);
			}
			if(!Product.equals("")){
				sqlWhereMap.put("Product", Product);
			}
			if(!Exp_Step_ID.equals("")){
				sqlWhereMap.put("Exp_Step_ID", Exp_Step_ID);
			}
			if(SiteNo != 0){
				sqlWhereMap.put("SiteNo", SiteNo);
			}
			if(ScanNo != 0){
				sqlWhereMap.put("ScanNo", ScanNo);
			}
			List<T_CanonExpSiteNo2ScanNo> tmp = T_CanonExpSiteNo2ScanNo_DAO.findAllByConditions(
				sqlWhereMap, T_CanonExpSiteNo2ScanNo.class);
			if(tmp.size()!= 0) {
				return tmp.get(0);
			}
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return null;
		}
		return null;
	}
}
