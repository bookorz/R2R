package com.innolux.R2R.common.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;

public class OracleDB implements DBSource {

	private String url;
	private String user;
	private String passwd;
	private int max; // ???��池中??大Connection?��?��
	private List<ConnectionInfo> connections;
	private Logger logger = Logger.getLogger(this.getClass());
	private ToolUtility tools = new ToolUtility();

	public class ConnectionInfo {
		public long LastTime = 0;
		public Connection conn = null;
	}

	public OracleDB(String connectionStr, String User, String PWD, int maxConn) {
		url = connectionStr;
		user = User;
		passwd = PWD;
		max = maxConn;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		connections = new ArrayList<ConnectionInfo>();
	}

	public synchronized Connection getConnection() throws SQLException {

		ConnectionInfo con = null;
		try {
			if (connections.size() == 0) {
				return DriverManager.getConnection(url, user, passwd);
			} else {
				int lastIndex = connections.size() - 1;
				con = connections.remove(lastIndex);
				if (!con.conn.isValid(5)) {

					con.conn = DriverManager.getConnection(url, user, passwd);
				}
				if (System.currentTimeMillis() - con.LastTime > 3600000) {
					con.conn = DriverManager.getConnection(url, user, passwd);
				}
			}
		} catch (Exception e) {
			logger.error(url);
			logger.error(tools.StackTrace2String(e));
		}
		return con.conn;
	}

	public synchronized void closeConnection(Connection conn) throws SQLException {
		if (connections.size() == max) {
			conn.close();
		} else {
			ConnectionInfo conInfo = new ConnectionInfo();
			conInfo.conn = conn;
			conInfo.LastTime = System.currentTimeMillis();
			connections.add(conInfo);
		}
	}
}
