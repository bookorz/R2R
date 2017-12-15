package com.innolux.R2R.ArrayExp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class T_ArrayExpContinueGlassSet_CRUD {
	private static Logger logger = Logger.getLogger(T_ArrayExpContinueGlassSet_CRUD.class);
	private static GenericDao<T_ArrayExpContinueGlassSet> T_ArrayExpContinueGlassSet_DAO = 
			new JdbcGenericDaoImpl <T_ArrayExpContinueGlassSet> (GlobleVar.R2R_DB);

	public static boolean create(ExpMeasGlass aGlass){
		try{
			T_ArrayExpContinueGlassSet aSet = new T_ArrayExpContinueGlassSet();
			
			String str1 = aGlass.getProductName();
			if (str1 == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "create T_ArrayExpContinueGlassSet_CRUD Error");
			}
			aSet.setProductName(str1);

			str1 = aGlass.getExpID();
			if (str1 == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "create T_ArrayExpContinueGlassSet_CRUD Error");
			}
			aSet.setExpID(str1);

			str1 = aGlass.getExpRcpID();
			if (str1 == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "create T_ArrayExpContinueGlassSet_CRUD Error");
			}
			aSet.setExpRcpID(str1);

			str1 = aGlass.getMeasStepID();
			if (str1 == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "create T_ArrayExpContinueGlassSet_CRUD Error");
			}
			aSet.setMeasStepID(str1);

			str1 = aGlass.getMeasRcpID();
			if (str1 == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "create T_ArrayExpContinueGlassSet_CRUD Error");
			}
			aSet.setMeasRcpID(str1);

			str1 = aGlass.getAdcOrFdc();
			if (str1 == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "create T_ArrayExpContinueGlassSet_CRUD Error");
			}
			aSet.setAdcOrFdc(str1);

			str1 = aGlass.getGlassID();
			if (str1 == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "create T_ArrayExpContinueGlassSet_CRUD Error");
			}
			aSet.setGlassID(str1);

			str1 = aGlass.getOlOrDol();
			if (str1 == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "create T_ArrayExpContinueGlassSet_CRUD Error");
			}
			aSet.setFeedbackMode(str1);
			
			aSet.setOl01List(aGlass.getOl01ListStr());
			aSet.setOl02List(aGlass.getOl02ListStr());
			aSet.setCoordXList(aGlass.getCoordXListStr());
			aSet.setCoordYList(aGlass.getCoordYListStr());
			
			aSet.setExposureTime(aGlass.getExposureTime());
			
			T_ArrayExpContinueGlassSet_DAO.save(aSet);
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return false;
		}
		return true;
	}

	public static boolean create(T_ArrayExpContinueGlassSet aSet){
		try{
			T_ArrayExpContinueGlassSet_DAO.save(aSet);
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return false;
		}
		return true;
	}

	public static List<T_ArrayExpContinueGlassSet> read(String Product, String ExpID, String ExpRcpID,
			String MeaRcpID, String MeaStepID, String AdcOrFdc){
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!Product.equals("")){
				sqlWhereMap.put("Product", Product);
			}
			if(!ExpID.equals("")){
				sqlWhereMap.put("Exp_ID", ExpID);
			}
			if(!ExpRcpID.equals("")){
				sqlWhereMap.put("Exp_Rcp_ID", ExpRcpID);
			}
			if(!MeaRcpID.equals("")){
				sqlWhereMap.put("Mea_Rcp_ID", MeaRcpID);
			}
			if(!MeaStepID.equals("")){
				sqlWhereMap.put("Mea_Step_ID", MeaStepID);
			}
			if(!AdcOrFdc.equals("")){
				sqlWhereMap.put("Adc_Or_Fdc", AdcOrFdc);
			}
			List<T_ArrayExpContinueGlassSet> tmp = T_ArrayExpContinueGlassSet_DAO.findAllByConditions(sqlWhereMap, T_ArrayExpContinueGlassSet.class);
			if (tmp == null) {
				Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, "T_ArrayExpContinueGlassSet read Error: tmp = null");
				return null;
			}else{
				return tmp;
			}
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return null;
		}
	}
	
	public static List<T_ArrayExpContinueGlassSet> read(ExpMeasGlass aGlass){
		String product = aGlass.getProductName();
		String expId = aGlass.getExpID();
		String expRcpId = aGlass.getExpRcpID();
		String meaRcpId = aGlass.getMeasRcpID();
		String meaStepId = aGlass.getMeasStepID();
		String adcOrFdc = aGlass.getAdcOrFdc();
		return T_ArrayExpContinueGlassSet_CRUD.read(product, expId, expRcpId, meaRcpId, meaStepId, adcOrFdc);
	}
	public static boolean delete(T_ArrayExpContinueGlassSet aSet){
		String product = aSet.getProductName();
		String expId = aSet.getExpID();
		String expRcpId = aSet.getExpRcpID();
		String meaRcpId = aSet.getMeasRcpID();
		String meaStepId = aSet.getMeasStepID();
		String adcOrFdc = aSet.getAdcOrFdc();
		String glassId = aSet.getGlassID();
		return T_ArrayExpContinueGlassSet_CRUD.delete(product, expId, expRcpId, meaRcpId, meaStepId, adcOrFdc, glassId);
	}
	
	public static boolean delete(ExpMeasGlass aGlass){
		String product = aGlass.getProductName();
		String expId = aGlass.getExpID();
		String expRcpId = aGlass.getExpRcpID();
		String meaRcpId = aGlass.getMeasRcpID();
		String meaStepId = aGlass.getMeasStepID();
		String adcOrFdc = aGlass.getAdcOrFdc();
		String glassId = aGlass.getGlassID();
		
		return T_ArrayExpContinueGlassSet_CRUD.delete(product, expId, expRcpId, meaRcpId, meaStepId, adcOrFdc, glassId);
	}
	public static boolean delete(String productName, 	
								String expID, 
								String expRcpID, 
								String measRcpID,  
								String measStepID, 		
								String adcOrFdc,
								String glassId){
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();

			if(!productName.equals("")){
				sqlWhereMap.put("Product", productName);
			}
			if(!expID.equals("")){
				sqlWhereMap.put("Exp_ID", expID);
			}
			if(!expRcpID.equals("")){
				sqlWhereMap.put("Exp_Rcp_ID", expRcpID);
			}
			if(!measStepID.equals("")){
				sqlWhereMap.put("Mea_Step_ID", measStepID);
			}
			if(!measRcpID.equals("")){
				sqlWhereMap.put("Mea_Rcp_ID", measRcpID);
			}
			if(!adcOrFdc.equals("")){
				sqlWhereMap.put("Adc_Or_Fdc", adcOrFdc);
			}
			if(!glassId.equals("")){
				sqlWhereMap.put("GlassID", glassId);
			}
			
			T_ArrayExpContinueGlassSet_DAO.deleteAllByConditions(sqlWhereMap, T_ArrayExpContinueGlassSet.class);
			return true;
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
			return false;
		}
		
	}
}
