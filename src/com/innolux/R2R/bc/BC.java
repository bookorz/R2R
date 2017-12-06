package com.innolux.R2R.bc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.innolux.R2R.common.ToolUtility;
import com.innolux.R2R.common.base.BCNodeBase;
import com.innolux.R2R.common.base.ED03Base;
import com.innolux.R2R.common.base.RegulationCollection;
import com.innolux.R2R.common.database.DBCollection;
import com.innolux.R2R.common.database.OracleDB;

public class BC {

	public String IP = "";
	public String Name = "";
	private Logger logger = Logger.getLogger(this.getClass());
	
	private OracleDB BC;

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{IP:" + IP + "," + "Name:" + Name + "}";
	}

	public BC(String Name) {

		OracleDB DEMS = DBCollection.DEMS;

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String SQL = "";
		try {
			conn = DEMS.getConnection();
			stmt = conn.createStatement();

			SQL = "select t.bc_name,t.ip from dems_bcip t where t.bc_name='" + Name + "'";
			logger.debug(SQL);

			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				this.Name = Name;
				this.IP = rs.getString("ip");
				// this.IP = "172.20.75.62";
				BC = new OracleDB(
						"jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = " + this.IP
								+ ")(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME =ORCL)))",
						"innolux", "innoluxabc123", 0);
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

					DEMS.closeConnection(conn);

				} catch (SQLException e) {
					logger.error(ToolUtility.StackTrace2String(e));
				}
			}
		}
	}

	private BCNodeBase GetNode(String EQName) {
		BCNodeBase result = new BCNodeBase();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String SQL = "";
		try {
			conn = BC.getConnection();
			stmt = conn.createStatement();

			SQL = "select node.bcno,node.bclineno,node.fabtype,node.nodeno"
					+ "  from main_bc_node node, main_bc_line line" + " where line.hostlineid = '" + this.Name + "'"
					+ "   and node.bcno = line.bcno" + "   and node.bclineno = line.bclineno"
					+ "   and node.fabtype = line.fabtype" + "   and node.hostsubeqid = '" + EQName + "'";
			logger.debug(SQL);

			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				result.EQName = EQName;
				result.BCNo = rs.getInt("BCNo");
				result.BCLineNo = rs.getInt("BCLineNo");
				result.FabType = rs.getInt("FabType");
				result.NodeNo = rs.getInt("nodeno");
				result.UnitNo = 0;
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

					BC.closeConnection(conn);

				} catch (SQLException e) {
					logger.error(ToolUtility.StackTrace2String(e));
				}
			}
		}
		return result;
	}

	private BCNodeBase GetUnit(String EQName) {
		BCNodeBase result = new BCNodeBase();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String SQL = "";
		try {
			conn = BC.getConnection();
			stmt = conn.createStatement();

			SQL = "select unit.bcno,unit.bclineno,unit.fabtype,unit.nodeno, unit.equnitno"
					+ "  from main_bc_unit unit, main_bc_line line" + " where line.hostlineid = '" + this.Name + "'"
					+ "   and unit.bcno = line.bcno" + "   and unit.bclineno = line.bclineno"
					+ "   and unit.fabtype = line.fabtype" + "   and unit.hostunitid = '" + EQName + "'";
			logger.debug(SQL);

			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				result.EQName = EQName;
				result.BCNo = rs.getInt("BCNo");
				result.BCLineNo = rs.getInt("BCLineNo");
				result.FabType = rs.getInt("FabType");
				result.NodeNo = rs.getInt("nodeno");
				result.UnitNo = rs.getInt("equnitno");
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

					BC.closeConnection(conn);

				} catch (SQLException e) {
					logger.error(ToolUtility.StackTrace2String(e));
				}
			}
		}
		return result;
	}

	private ED03Base GetED03(BCNodeBase node) {
		ED03Base result = new ED03Base();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String SQL = "";
		try {
			conn = BC.getConnection();
			stmt = conn.createStatement();

			SQL = "select node.hostsubeqid," + "        evt.funckey," + "        substr(to_char(substr(plc.outputdata,"
					+ "                              to_number(evt.startadr, 'xxxxxxx') * 4 + 1,"
					+ "                              evt.datalen * 4))," + "               4,"
					+ "               1) reportType," + "        substr(to_char(substr(plc.outputdata,"
					+ "                              to_number(evt.startadr, 'xxxxxxx') * 4 + 1,"
					+ "                              evt.datalen * 4))," + "               evt.datalen * 4 - 3,"
					+ "               4) evtindex," + "        node.recipesplit,"
					+ "        substr(to_char(substr(plc.outputdata,"
					+ "                              to_number(evt.startadr, 'xxxxxxx') * 4 + 1,"
					+ "                              evt.datalen * 4))," + "               17,"
					+ "               800) itemdata"
					+ "   from main_bc_line t, io_event evt, main_bc_node node, plcdata plc"
					+ "  where t.hostlineid = '" + this.Name + "'" + "    and node.hostsubeqid = '" + node.EQName + "'"
					+ "    and t.bcno = evt.bcno" + "    and t.bclineno = evt.bclineno"
					+ "    and t.fabtype = evt.fabtype" + "    and t.bcno = node.bcno"
					+ "    and t.bclineno = node.bclineno" + "    and t.fabtype = node.fabtype"
					+ "    and t.bcno = plc.bcno" + "    and t.bclineno = plc.bclineno"
					+ "    and t.fabtype = plc.fabtype" + "    and plc.devicetype = '2'"
					+ "    and evt.nodeno = node.nodeno" + "    and evt.funckey = 'ED03'"
					+ "    and evt.subfunckey = '01'";
			logger.debug(SQL);

			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				result.ReportType = rs.getString("reportType");
				result.DataItem = rs.getString("itemdata");
				result.Index = rs.getString("evtindex");
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

					BC.closeConnection(conn);

				} catch (SQLException e) {
					logger.error(ToolUtility.StackTrace2String(e));
				}
			}
		}
		return result;
	}

	private String ToCmdData(RegulationCollection regCol) {
		String result = "";

		for (int i = 1; i <= 50; i++) {
			String wd = regCol.GetRegulation(i);
			if (wd.equals("")) {
				wd = "0000";
			}
			result += wd;
		}

		return result;
	}

	private boolean ExcuteCmd(RegulationCollection regCol, BCNodeBase node) {
		boolean result = false;
		Connection conn = null;
		Statement stmt = null;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);

		String SQL = "";

		try {
			conn = BC.getConnection();
			stmt = conn.createStatement();

			SQL = "Insert Into TrxBCGetCmd" + "   (BCNo," + "    BCLineNo," + "    FabType," + "    CmdKey,"
					+ "    NodeNo," + "    EQPortNo," + "    CmdData," + "    CmdType," + "    OPName,"
					+ "    UpdateTime," + "    UpdateOP," + "    SP1," + "    SP2)" + " Values" + "   ('" + node.BCNo
					+ "'," + "    '" + node.BCLineNo + "'," + "    '" + node.FabType + "',"
					+ "    'RUN_TO_RUN_DOWLOAD'," + "    '" + node.NodeNo + "'," + "    0," + "    '"
					+ String.format("%02d", node.NodeNo) + "," + ToCmdData(regCol) + "'," + "    'INSERT',"
					+ "    'R2R'," + "    '" + reportDate + "'," + "    'CIM'," + "    ''," + "    '')";

			logger.debug(SQL);

			stmt.executeUpdate(SQL);
			result = true;
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

					BC.closeConnection(conn);

				} catch (SQLException e) {
					logger.error(ToolUtility.StackTrace2String(e));
				}
			}
		}
		return result;
	}

	public boolean Excute(RegulationCollection regCol) throws InterruptedException {
		boolean result = false;
		BCNodeBase Node = GetNode(regCol.EqpName);
		int retryCount = 0;
		if (Node.NodeNo == 0) {
			Node = GetUnit(regCol.EqpName);
		}
		if (Node.NodeNo == 0) {
			result = false;
			logger.error("Get NodeNo error!");
		} else {
			ED03Base ed03Old = GetED03(Node);
			logger.debug("ed03Old:" + ed03Old.toString());
			if (ExcuteCmd(regCol, Node)) {
				while (true) {

					ED03Base ed03New = GetED03(Node);
					logger.debug("ed03New:" + ed03New.toString());
					if (ed03New.ReportType.equals("5") && !ed03Old.Index.equals(ed03New.Index)) {
						logger.debug("Equipment reply OK");
						result = true;
						break;
					} else {
						if (retryCount >= 12) {
							retryCount = 0;
							logger.error("Equipment reply timeout!");
							break;
						} else {
							retryCount++;
							Thread.sleep(1000);
						}
					}

				}

			} else {
				logger.error("ExcuteCmd error!");
			}

		}
		return result;
	}

}
