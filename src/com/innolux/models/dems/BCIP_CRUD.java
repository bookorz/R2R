package com.innolux.models.dems;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.GlobleVar;
import com.innolux.R2R.common.ToolUtility;
import com.innolux.dao.GenericDao;
import com.innolux.dao.JdbcGenericDaoImpl;

public class BCIP_CRUD {
	private static Logger logger = Logger.getLogger(BCIP_CRUD.class);
	private static GenericDao<BCIP> BCIP_Dao = new JdbcGenericDaoImpl<BCIP>(GlobleVar.DEMS_DB);

	public static BCIP read(String BCName) {
		BCIP result = null;
		try {

			result = BCIP_Dao.get(BCName, BCIP.class);

		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
	}
}
