package com.innolux.R2R.handler.dataProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.GlassDataBase;
import com.innolux.R2R.common.database.DBCollection;
import com.innolux.R2R.common.database.OracleDB;

public class GlassData {
	private Logger logger = Logger.getLogger(this.getClass());
	private ToolUtility tools = new ToolUtility();
	private OracleDB DB = DBCollection.R2RDB;

	public void DeleteAll(String R2R_ID) {
		Connection conn = null;
		Statement stmt = null;
		String SQL = "";

		try {
			conn = DB.getConnection();
			stmt = conn.createStatement();

			SQL = "delete GlassData t where t.R2R_ID = '" + R2R_ID + "'";
			logger.debug(SQL);
			stmt.executeUpdate(SQL);

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

	public void Append(String R2R_ID, String Glass_ID, String Param_Name, String Param_Value) {
		Connection conn = null;
		Statement stmt = null;

		String SQL = "";

		try {
			conn = DB.getConnection();
			stmt = conn.createStatement();

			SQL = "insert into GlassData (R2R_ID,Glass_ID,Param_Name,Param_Value,TimeStamp) values('" + R2R_ID + "','"
					+ Glass_ID + "','" + Param_Name + "','" + Param_Value + "',sysdate)";

			logger.debug(SQL);

			stmt.executeUpdate(SQL);

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

	public Vector<GlassDataBase> Lookup(String R2R_ID, int RowCount) {
		Vector<GlassDataBase> result = new Vector<GlassDataBase>();
		Hashtable<String, GlassDataBase> tmp = new Hashtable<String, GlassDataBase>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String SQL = "select t.* from glassdata t where t.r2r_id = '" + R2R_ID
				+ "' order by t.timestamp desc,t.glass_id";

		logger.debug(SQL);
		try {

			conn = DB.getConnection();

			stmt = conn.createStatement();

			rs = stmt.executeQuery(SQL);

			while (rs.next()) {

				GlassDataBase eachGlass = null;
				if (tmp.containsKey(rs.getString("Glass_ID"))) {
					eachGlass = tmp.get(rs.getString("Glass_ID"));
					eachGlass.StoreParameter(rs.getString("Param_Name"), rs.getString("Param_Value"));
				} else {
					if (tmp.size() <= RowCount) {
						eachGlass = new GlassDataBase();
						eachGlass.R2R_ID = rs.getString("R2R_ID");
						eachGlass.Glass_ID = rs.getString("Glass_ID");
						eachGlass.StoreParameter(rs.getString("Param_Name"), rs.getString("Param_Value"));
						eachGlass.TimeStamp = rs.getString("TimeStamp");
						tmp.put(eachGlass.Glass_ID, eachGlass);
					}
				}
			}
			rs.close();
			for (GlassDataBase each : tmp.values()) {
				result.add(each);
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
