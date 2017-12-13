package com.innolux.R2R.common;

import com.innolux.dao.JdbcDaoHelper;

public class GlobleVar {
	// R2R DB
	private static String R2RTNS = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST =      (ADDRESS = (PROTOCOL = TCP)(HOST = 172.20.9.1)(PORT = 1521))      (ADDRESS = (PROTOCOL = TCP)(HOST = 172.20.9.2)(PORT = 1521))      (LOAD_BALANCE = yes)    )    (CONNECT_DATA =      (SERVER = DEDICATED)      (SERVICE_NAME = t2pcft)    ))";
	private static String R2RDBUser = "runprod";
	private static String R2RDBPwd = "runprod";
	public static JdbcDaoHelper R2R_DB = new JdbcDaoHelper(R2RTNS, R2RDBUser, R2RDBPwd, 2);

	// DEMS DB
	private static String DEMSTNS = "jdbc:oracle:thin:@ (DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = 172.20.8.68)(PORT = 1521)) ) (CONNECT_DATA = (SERVER = DEDICATED) (SERVICE_NAME = T2PDEMS) ) ) ";
	private static String DEMSDBUser = "DEMSPROD";
	private static String DEMSDBPwd = "DEMSPROD";

	public static JdbcDaoHelper DEMS_DB = new JdbcDaoHelper(DEMSTNS, DEMSDBUser, DEMSDBPwd, 1);

	public static JdbcDaoHelper ARRAYMesDB = new JdbcDaoHelper(
			"jdbc:oracle:thin:@(DESCRIPTION =    (ADDRESS_LIST =      (ADDRESS = (PROTOCOL = TCP)(HOST = 172.20.9.1)(PORT = 1521))      (ADDRESS = (PROTOCOL = TCP)(HOST = 172.20.9.2)(PORT = 1521))      (LOAD_BALANCE = yes)    )    (CONNECT_DATA =      (SERVER = DEDICATED)      (SERVICE_NAME = t2pary)    )  )",
			"aryprod", "aryprod", 1);
	public static JdbcDaoHelper CFMesDB = new JdbcDaoHelper(
			"jdbc:oracle:thin:@(DESCRIPTION =    (ADDRESS_LIST =      (ADDRESS = (PROTOCOL = TCP)(HOST = 172.20.9.1)(PORT = 1521))      (ADDRESS = (PROTOCOL = TCP)(HOST = 172.20.9.2)(PORT = 1521))      (LOAD_BALANCE = yes)    )    (CONNECT_DATA =      (SERVER = DEDICATED)      (SERVICE_NAME = t2pcft)    )  )",
			"cftprod", "cftprod", 1);

	public static JdbcDaoHelper CELLMesDB = new JdbcDaoHelper(
			"jdbc:oracle:thin:@(DESCRIPTION =    (ADDRESS_LIST =      (ADDRESS = (PROTOCOL = TCP)(HOST = 172.20.9.1)(PORT = 1521))      (ADDRESS = (PROTOCOL = TCP)(HOST = 172.20.9.2)(PORT = 1521))      (LOAD_BALANCE = yes)    )    (CONNECT_DATA =      (SERVER = DEDICATED)      (SERVICE_NAME = t2pcel)    )  )",
			"celprod", "celprod", 1);

	public static final String LogErrorType = "Error";
	public static final String LogDebugType = "Debug";
	public static final String LogInfoType = "Info";
}
