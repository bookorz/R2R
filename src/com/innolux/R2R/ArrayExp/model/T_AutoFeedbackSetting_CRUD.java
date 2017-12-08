package com.innolux.R2R.ArrayExp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.innolux.dao.JdbcGenericDaoImpl;
import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;

public class T_AutoFeedbackSetting_CRUD {
	private static Logger logger = Logger.getLogger(T_AutoFeedbackSetting_CRUD.class);
	private static GenericDao<T_AutoFeedbackSetting> T_AutoFeedbackSetting_DAO = 
			new JdbcGenericDaoImpl <T_AutoFeedbackSetting> (GlobleVar.R2R_DB);
	
	public static T_AutoFeedbackSetting read(String Primarykey, String ActiveFlag, String EqpID,
		String ExpStepId, String MeaStepId, String ExpRcpId, String ExpRcpName, String MeaRcp, 
		String FeedbackMode, double UUpperLimit, double LUpperLimit, double ULowerLimit, 
		double LLowerLimit, double Ratio, double Sigma, int PopulationSize, int SampleSize, 
		long Expiretime){
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
			if(!Exp_Rcp_ID.equals("")){
				sqlWhereMap.put("Exp_Rcp_ID", Exp_Rcp_ID);
			}
			if(!Exp_Rcp_Name.equals("")){
				sqlWhereMap.put("Exp_Rcp_Name", Exp_Rcp_Name);
			}
			List<T_ExpRcpID2Name> tmp = T_ExpRcpID2Name_DAO.findAllByConditions(sqlWhereMap, T_ExpRcpID2Name.class);
			if(tmp.size()!=0) {
				return tmp.get(0);
			}
		}catch(Exception e){
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return null;
	}
}
