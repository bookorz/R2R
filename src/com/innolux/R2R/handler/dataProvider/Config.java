package com.innolux.R2R.handler.dataProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.ConfigBase;
import com.innolux.R2R.common.database.DBCollection;
import com.innolux.R2R.common.database.OracleDB;

public class Config {

	private Logger logger = Logger.getLogger(this.getClass());
	private ToolUtility tools = new ToolUtility();
	private OracleDB DB = DBCollection.R2RDB;

	public void Update(String R2R_ID, String Key, String Value) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int rowCount = 0;
		String SQL = "";

		try {
			conn = DB.getConnection();
			stmt = conn.createStatement();

			SQL = "select * from config t where t.R2R_ID = '" + R2R_ID + "' and t.key = '" + Key + "'";
			logger.debug(SQL);

			rs = stmt.executeQuery(SQL);


			while (rs.next()) {
				rowCount++;
			}
			rs.close();
			if (rowCount != 0) {

				SQL = "update config t set t.value='" + Value + "' where t.R2R_ID = '" + R2R_ID + "' and t.key = '"
						+ Key + "'";

				logger.debug(SQL);
				stmt.executeUpdate(SQL);

			} else {

				SQL = "insert into config (R2R_ID,key,value) values('" + R2R_ID + "','" + Key + "','" + Value + "')";

				logger.debug(SQL);

				stmt.executeUpdate(SQL);

			}

		} catch (Exception e) {
			logger.error(tools.StackTrace2String(e));

		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error(tools.StackTrace2String(e));
				}
			}
			if (conn != null) {
				try {

					DB.closeConnection(conn);

				} catch (SQLException e) {
					logger.error(tools.StackTrace2String(e));
				}
			}
		}
	}

	public ConfigBase Lookup(String R2R_ID) {
		ConfigBase result = new ConfigBase();
		result.R2R_ID = R2R_ID;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String SQL = "select * from config t where t.R2R_ID = '" + R2R_ID + "'";
		logger.debug(SQL);
		try {

			conn = DB.getConnection();

			stmt = conn.createStatement();

			rs = stmt.executeQuery(SQL);

			while (rs.next()) {

				String key = rs.getString("key");
				String value = "";
				if(rs.getString("value")!=null){
					value = rs.getString("value");
				}
				result.Put(key, value);

			}
			logger.debug(result.toString());
		} catch (Exception e) {
			logger.error(tools.StackTrace2String(e));

		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error(tools.StackTrace2String(e));
				}
			}
			if (conn != null) {
				try {
					DB.closeConnection(conn);
				} catch (SQLException e) {
					logger.error(tools.StackTrace2String(e));
				}
			}
		}
		return result;
	}

}
