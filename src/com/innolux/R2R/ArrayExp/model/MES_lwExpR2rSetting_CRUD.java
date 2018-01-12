package com.innolux.R2R.ArrayExp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class MES_lwExpR2rSetting_CRUD {
	private static Logger logger = Logger.getLogger(MES_fwflatnode_CRUD.class);
	private static GenericDao<MES_lwExpR2rSetting> MES_lwExpR2rSetting_DAO = 
			new JdbcGenericDaoImpl <MES_lwExpR2rSetting> (GlobleVar.arrayMesTestDB);
	
	public static boolean create(MES_lwExpR2rSetting aSet){
		try{
			MES_lwExpR2rSetting_DAO.save(aSet);
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return false;
		}
		return true;
	}

	public static List<MES_lwExpR2rSetting> read(MES_lwExpR2rSetting aMES_lwExpR2rSetting){
		try{
			Map<String, Object> sqlWhereMap = new HashMap<String, Object>();
			
			if (!aMES_lwExpR2rSetting.getEqpId().equals("")) {
				sqlWhereMap.put("eqpId", aMES_lwExpR2rSetting.getEqpId());
			}

			if (!aMES_lwExpR2rSetting.getProdId().equals("")) {
				sqlWhereMap.put("prodId", aMES_lwExpR2rSetting.getProdId());
			}

			if (!aMES_lwExpR2rSetting.getRecipeId().equals("")) {
				sqlWhereMap.put("recipeId", aMES_lwExpR2rSetting.getRecipeId());
			}

			if (!aMES_lwExpR2rSetting.getHoldFlag().equals("")) {
				sqlWhereMap.put("holdFlag", aMES_lwExpR2rSetting.getHoldFlag());
			}

			if (!aMES_lwExpR2rSetting.getR2rFeedbackTime().equals("")) {
				sqlWhereMap.put("r2rFeedbackTime", aMES_lwExpR2rSetting.getR2rFeedbackTime());
			}
			
			List<MES_lwExpR2rSetting> tmp = MES_lwExpR2rSetting_DAO.findAllByConditions(sqlWhereMap, MES_lwExpR2rSetting.class);
			if (tmp.size() != 0) {
				return tmp;
			}
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return null;
		}
		return null;
	}
	
	public static boolean update(MES_lwExpR2rSetting aSetting){
		try{
			MES_lwExpR2rSetting_DAO.update(aSetting);
		}catch(Exception e){
			Utility.saveToLogHistoryDB(GlobleVar.LogErrorType, ToolUtility.StackTrace2String(e));
			return false;
		}
		return true;
	}
}
