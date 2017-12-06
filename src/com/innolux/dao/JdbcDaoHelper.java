package com.innolux.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;

/**
 * 提供獲取數據庫連接、釋放資源的接口
 */
public class JdbcDaoHelper {
	private int max; // 連接池中最大Connection數目
	private List<ConnectionInfo> connections;
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 數據庫用戶名
	 */
	private String USER = "";

	/**
	 * 數據庫密碼
	 */
	private String PASSWORD = "";

	/**
	 * 連接數據庫的地址
	 */
	private String URL = "";
	
	private class ConnectionInfo{
		public long LastUseTime = 0;
		public Connection conn = null;
	}

	public JdbcDaoHelper(String connectionStr, String User, String PWD, int maxConn) {
		URL = connectionStr;
		USER = User;
		PASSWORD = PWD;
		max = maxConn;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			logger.error(URL);
			logger.error(ToolUtility.StackTrace2String(e));
		}

		connections = new ArrayList<ConnectionInfo>();
	}

	/**
	 * 獲得一個數據庫連接對像
	 * 
	 * @return java.sql.Connection實例
	 * @throws SQLException 
	 */
	public synchronized Connection getConnection() throws SQLException {
		ConnectionInfo con = null;
		if (connections.size() == 0) {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} else {
			int lastIndex = connections.size() - 1;
			con = connections.remove(lastIndex);
			if(!con.conn.isValid(5)){
				try{
					con.conn.close();
				}catch(Exception e){
					logger.debug("getConnection() con.conn.close() has error, exception:" + ToolUtility.StackTrace2String(e));
				}
				con.conn = DriverManager.getConnection(URL, USER, PASSWORD);
			}
			if(System.currentTimeMillis() - con.LastUseTime > 3600000){
				try{
					con.conn.close();
				}catch(Exception e){
					logger.debug("getConnection() con.conn.close() has error, exception:" + ToolUtility.StackTrace2String(e));
				}
				con.conn = DriverManager.getConnection(URL, USER, PASSWORD);
			}
		}
		return con.conn;
	}

	/**
	 * 釋放數據庫資源
	 */
	public synchronized void release(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (conn != null) {
				if (connections.size() == max) {
					conn.close();
					conn = null;
				} else {
					ConnectionInfo conInfo = new ConnectionInfo();
					conInfo.conn = conn;
					conInfo.LastUseTime = System.currentTimeMillis();
					connections.add(conInfo);
				}
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
	}

}