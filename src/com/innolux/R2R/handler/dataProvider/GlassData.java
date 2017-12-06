package com.innolux.R2R.handler.dataProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.GlassDataBase;
import com.innolux.R2R.common.database.DBCollection;
import com.innolux.R2R.common.database.OracleDB;

public class GlassData {
	private static Logger logger = Logger.getLogger(GlassData.class);
	
	private static OracleDB DB = DBCollection.R2RDB;

	public static void DeleteAll(String R2R_ID) {
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

					DB.closeConnection(conn);

				} catch (SQLException e) {
					logger.error(ToolUtility.StackTrace2String(e));
				}
			}
		}
	}

	public static void Append(String R2R_ID, String Glass_ID, String Param_Name, String Param_Value) {
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

					DB.closeConnection(conn);

				} catch (SQLException e) {
					logger.error(ToolUtility.StackTrace2String(e));
				}
			}
		}
	}
	
	public static void StoreFileData(Hashtable<String, List<String>> data, String R2R_ID, String Glass_ID) {
		try {
			
			for (String key : data.keySet()) {
				List<String> values = data.get(key);

				for (int i = 0; i < values.size(); i++) {
					Append(R2R_ID, Glass_ID, key + ":" + i, values.get(i));
				}

			}
		} catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
	}
	
    public static Hashtable<String,Hashtable<String, List<String>>> LookupForFileData(String R2R_ID){
    	Hashtable<String,Hashtable<String, List<String>>> result = new Hashtable<String,Hashtable<String, List<String>>>();
		try{
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			String SQL = "select t.* from glassdata t where t.r2r_id = '" + R2R_ID
					+ "' order by t.timestamp desc,t.glass_id,t,param_name";

			logger.debug(SQL);
			try {

				conn = DB.getConnection();

				stmt = conn.createStatement();

				rs = stmt.executeQuery(SQL);

				while (rs.next()) {
					
					String Glass_ID = rs.getString("Glass_ID");
					String[] paramInfo = rs.getString("Param_Name").split(":");
					String headerName = paramInfo[0];
					//String rowNum = paramInfo[1];
					
					if(result.containsKey(Glass_ID)){
						Hashtable<String, List<String>> GlassData = result.get(Glass_ID);
						if(GlassData.containsKey(headerName)){
							List<String> headerRow = GlassData.get(headerName);
							headerRow.add(rs.getString("Param_Value"));
						}else{
							List<String> headerRow = new ArrayList<String>();
							headerRow.add(rs.getString("Param_Value"));
							GlassData.put(headerName, headerRow);
						}
					}else{
						Hashtable<String, List<String>> newGlass = new Hashtable<String, List<String>>();
						List<String> newRow = new ArrayList<String>();
						newRow.add(rs.getString("Param_Value"));
						newGlass.put(headerName, newRow);
						
						result.put(Glass_ID, newGlass);
					}
					
					
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
						DB.closeConnection(conn);
					} catch (SQLException e) {
						logger.error(ToolUtility.StackTrace2String(e));
					}
				}
			}
		}catch (Exception e) {
			logger.error(ToolUtility.StackTrace2String(e));
		}
		return result;
    }

	public static List<GlassDataBase> LookupForPdsData(String R2R_ID, int RowCount) {
		List<GlassDataBase> result = new ArrayList<GlassDataBase>();
		Hashtable<String, GlassDataBase> tmp = new Hashtable<String, GlassDataBase>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String SQL = "select t.* from glassdata t where t.r2r_id = '" + R2R_ID
				+ "' order by t.timestamp desc,t.glass_id,t.param_name";

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
					if (tmp.size() <= RowCount || RowCount == -1) {
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
					DB.closeConnection(conn);
				} catch (SQLException e) {
					logger.error(ToolUtility.StackTrace2String(e));
				}
			}
		}
		return result;
	}

}
