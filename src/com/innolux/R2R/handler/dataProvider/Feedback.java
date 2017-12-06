package com.innolux.R2R.handler.dataProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.database.DBCollection;

public class Feedback {
	private static Logger logger = Logger.getLogger(Config.class);
	//R2R_ID , UpdateTime , Comment
	public static void Update(String R2R_ID, long updateTime ,String comment) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int rowCount = 0;
		String SQL = "";

		try {
			conn = DBCollection.R2RDB.getConnection();
			stmt = conn.createStatement();

			SQL = "select * from feedback t where t.R2R_ID = '" + R2R_ID + "'";
			logger.debug(SQL);

			rs = stmt.executeQuery(SQL);


			while (rs.next()) {
				rowCount++;
			}
			rs.close();
			if (rowCount != 0) {

				SQL = "update feedback t set t.UpdateTime=" + updateTime + ",t.comment='"+comment+"' where t.R2R_ID = '" + R2R_ID + "'";					

				logger.debug(SQL);
				stmt.executeUpdate(SQL);

			} else {

				SQL = "insert into config (R2R_ID,UpdateTime,t.comment) values('" + R2R_ID + "'," + updateTime + ",'"+comment+"')";

				logger.debug(SQL);

				stmt.executeUpdate(SQL);

			}

		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));

		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error(ToolUtility.StackTrace2String(e));
				}
			}
			if (conn != null) {
				try {

					DBCollection.R2RDB.closeConnection(conn);

				} catch (SQLException e) {
					logger.error(ToolUtility.StackTrace2String(e));
				}
			}
		}
	}
	
	public static long Get(String R2R_ID) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		long result = 0;
	
		String SQL = "";

		try {
			conn = DBCollection.R2RDB.getConnection();
			stmt = conn.createStatement();

			SQL = "select * from feedback t where t.R2R_ID = '" + R2R_ID + "'";
			logger.debug(SQL);

			rs = stmt.executeQuery(SQL);


			while (rs.next()) {
				result = rs.getLong("UpdateTime");
			}
			rs.close();
			

		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));

		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error(ToolUtility.StackTrace2String(e));
				}
			}
			if (conn != null) {
				try {

					DBCollection.R2RDB.closeConnection(conn);

				} catch (SQLException e) {
					logger.error(ToolUtility.StackTrace2String(e));
				}
			}
		}
		return result;
	}
}
