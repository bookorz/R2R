package com.innolux.R2R.common;

import com.innolux.dao.JdbcDaoHelper;

public class GlobleVar {
	// R2R DB
	private static String R2RTNS = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST =      (ADDRESS = (PROTOCOL = TCP)(HOST = 172.20.9.1)(PORT = 1521))      (ADDRESS = (PROTOCOL = TCP)(HOST = 172.20.9.2)(PORT = 1521))      (LOAD_BALANCE = yes)    )    (CONNECT_DATA =      (SERVER = DEDICATED)      (SERVICE_NAME = t2pcft)    ))";
	private static String R2RDBUser = "runprod";
	private static String R2RDBPwd = "runprod";
	public static JdbcDaoHelper R2R_DB = new JdbcDaoHelper(R2RTNS, R2RDBUser, R2RDBPwd, 2);
}
