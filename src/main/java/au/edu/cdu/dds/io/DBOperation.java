package au.edu.cdu.dds.io;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.edu.cdu.dds.util.ConstantValue;

public class DBOperation {

	private static Connection getConnection() throws Exception {
		String clzName = "org.sqlite.JDBC";
		String dbName = "jdbc:sqlite:result/db/result.db";
		return getConnection(clzName, dbName);
	}

	private static Connection getConnection(String clzName, String dbName) throws Exception {

		Connection c = null;

		Class.forName(clzName);
		c = DriverManager.getConnection(dbName);

		return c;
	}

	/**
	 * execute query by sql statement
	 * 
	 * @param dbp,
	 *            db operation parameters
	 * @return query result map
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public static List<Map<String, String>> executeQuery(DBParameter dbp) {
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
		Map<String, String> map = null;
		List<Map<String, String>> lst = null;

		try {

			StringBuffer sqlSB = new StringBuffer();
			String[] colNames = dbp.getColNames();
			String[] colPairNames = dbp.getColPairNames();
			String[] colPairOperators = dbp.getColPairOperators();
			String[] colPairValues = dbp.getColPairValues();

			sqlSB.append("select ");
			for (String colName : colNames) {
				sqlSB.append("\"").append(colName).append("\",");
			}
			sqlSB.delete(sqlSB.length() - 1, sqlSB.length());

			sqlSB.append(" from ").append("\"").append(dbp.getTableName()).append("\"");

			sqlSB.append(" where 1=1 ");

			for (int i = 0; i < colPairNames.length; i++) {
				String colPairName = colPairNames[i];
				String colPairOperator = colPairOperators[i];
				String colPairValue = colPairValues[i];
				sqlSB.append(" and \"").append(colPairName).append("\" ").append(colPairOperator).append(" \"")
						.append(colPairValue).append("\" ");
			}
			sqlSB.append(";");

			c = getConnection();
			c.setAutoCommit(false);
			stmt = c.createStatement();
			rs = stmt.executeQuery(sqlSB.toString());
			int colNamesLen = colNames.length;

			lst = new ArrayList<Map<String, String>>();

			while (rs.next()) {
				map = new HashMap<String, String>(colNamesLen);
				for (int i = 0; i < colNamesLen; i++) {
					String colName = colNames[i];
					String colVal = rs.getString(i + 1);
					map.put(colName, colVal);
				}
				lst.add(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				return lst;
			}

		}
	}

	/**
	 * insert one record into database table
	 * 
	 * @param dbp,
	 *            database operation parameters
	 * @return inserted record count
	 */
	@SuppressWarnings("finally")
	public static int executeInsert(DBParameter dbp) {
		Connection c = null;
		Statement stmt = null;
		int rtn = ConstantValue.IMPOSSIBLE_VALUE;

		try {

			StringBuffer sqlSB = new StringBuffer();
			sqlSB.append("insert into ").append(dbp.getTableName()).append(" (");
			String[] colNames = dbp.getColPairNames();
			String[] colValues = dbp.getColPairValues();

			for (String colName : colNames) {
				sqlSB.append("\"").append(colName).append("\",");
			}

			sqlSB.delete(sqlSB.length() - 1, sqlSB.length());

			sqlSB.append(") values (");

			for (String colVal : colValues) {
				sqlSB.append("\"").append(colVal).append("\",");
			}
			sqlSB.delete(sqlSB.length() - 1, sqlSB.length());

			sqlSB.append(");");

			c = getConnection();

			c.setAutoCommit(false);
			stmt = c.createStatement();
			rtn = stmt.executeUpdate(sqlSB.toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				c.commit();
				c.close();
			} catch (Exception e) {

				e.printStackTrace();
			} finally {
				return rtn;
			}

		}

	}

	/**
	 * create table for each instance. If a table already exist, ignore.
	 * 
	 * @param instanceCode,
	 *            instance code
	 */
	@SuppressWarnings("finally")
	public static void createTable(String instanceCode) {
		Connection c = null;
		Statement stmt = null;

		String tblPrefix = ConstantValue.TBL_ALG_PREFIX;

		try {
			StringBuffer sb = new StringBuffer();
			// sqlSb.append("drop table if exists
			// ").append(tblPrefix).append(instanceCode).append(";");
			c = getConnection();
			c.setAutoCommit(false);
			stmt = c.createStatement();
			// stmt.execute(sqlSb.toString());

			sb.setLength(0);

			sb.append("CREATE TABLE IF NOT EXISTS ").append(tblPrefix).append(instanceCode).append("(\n");
			sb.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, \n");
			sb.append(" i_id varchar2(10),\n");
			sb.append(" result_size INTEGER,\n");
			sb.append(" running_nano_sec INTEGER,\n");
			sb.append(" batch_num varchar2(30),\n");
			sb.append(" threshold INTEGER,\n");
			sb.append(" model varchar2(30),\n");
			sb.append(" FOREIGN KEY(i_id) REFERENCES \"v_instance\"(i_id)\n");
			sb.append(");");

			stmt.execute(sb.toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				c.commit();
				c.close();
			} catch (final Exception e) {

				e.printStackTrace();
			} finally {
				return;
			}

		}
	}

	private static String singleInstanceQueryStr(String code) {
		StringBuffer sb = new StringBuffer();
		// String tblPrefix = ConstantValue.TBL_ALG_PREFIX;
		String viewPre = "v_";
		String viewSuff = "_alg2";
		String maxSuff = "_max";
		String minSuff = "_min";

		String tblAbbA = "a";
		String tblAbbB = "b";
		// v_algRunning_code
		sb.append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append(code).append(" as (\n");
		sb.append("select ").append(tblAbbA).append(".* from (\n");
		sb.append("select ").append(ConstantValue.DB_COL_INS_ID).append(",");
		sb.append(ConstantValue.DB_COL_RESULT_SIZE).append(",");
		sb.append("max(").append(ConstantValue.DB_COL_THRESHOLD).append(" max_").append(ConstantValue.DB_COL_THRESHOLD)
				.append("\n");
		sb.append(" from ").append(ConstantValue.TBL_ALG_PREFIX).append(code).append("\n");
		sb.append(" group by ").append(ConstantValue.DB_COL_INS_ID).append(",")
				.append(ConstantValue.DB_COL_ACCEPT_RESULT_SIZE).append("\n");
		sb.append(") ").append(tblAbbB).append(",").append(ConstantValue.TBL_ALG_PREFIX).append(code).append(" ")
				.append(tblAbbA).append("\n");
		sb.append("where ").append(tblAbbA).append(".").append(ConstantValue.DB_COL_INS_ID).append("=").append(tblAbbB)
				.append(".").append(ConstantValue.DB_COL_INS_ID).append("\n");
		sb.append("and ").append(tblAbbA).append(".").append(ConstantValue.DB_COL_RESULT_SIZE).append("=")
				.append(tblAbbB).append(".").append(ConstantValue.DB_COL_RESULT_SIZE).append("\n");
		sb.append("and ").append(tblAbbA).append(".").append(ConstantValue.DB_COL_THRESHOLD).append("=").append(tblAbbB)
				.append(".max_").append(ConstantValue.DB_COL_THRESHOLD).append("\n");
		sb.append("),\n");
		// v_algRunning_code_max
		sb.append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append(code).append(maxSuff).append(" as (\n");
		sb.append("select ").append(ConstantValue.DB_COL_INS_ID).append(",").append(ConstantValue.DB_COL_THRESHOLD)
				.append(",").append(ConstantValue.DB_COL_RUNNING_TIME).append(",")
				.append(ConstantValue.DB_COL_RESULT_SIZE).append("\n");
		sb.append("from").append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append(code).append("\n");
		sb.append("where ").append(ConstantValue.DB_COL_THRESHOLD).append("=(").append("select max(")
				.append(ConstantValue.DB_COL_THRESHOLD).append(" from ").append(viewPre)
				.append(ConstantValue.TBL_ALG_PREFIX).append(code).append(")\n");
		sb.append("),\n");
		// v_algRunning_code_min
		sb.append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append(code).append(minSuff).append(" as (\n");
		sb.append("select ").append(ConstantValue.DB_COL_INS_ID).append(",").append(ConstantValue.DB_COL_THRESHOLD)
				.append(",").append(ConstantValue.DB_COL_RUNNING_TIME).append(",")
				.append(ConstantValue.DB_COL_RESULT_SIZE).append("\n");
		sb.append("from").append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append(code).append("\n");
		sb.append("where ").append(ConstantValue.DB_COL_THRESHOLD).append("=(").append("select min(")
				.append(ConstantValue.DB_COL_THRESHOLD).append(" from ").append(viewPre)
				.append(ConstantValue.TBL_ALG_PREFIX).append(code).append(")\n");
		sb.append("),\n");
		// v_algRunning_code_alg2
		sb.append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append(code).append(viewSuff).append(" as (\n");
		sb.append("select ").append(tblAbbA).append(".").append(ConstantValue.DB_COL_INS_ID).append(",");
		sb.append(tblAbbA).append(".").append(ConstantValue.DB_COL_THRESHOLD).append(" ")
				.append(ConstantValue.DB_COL_THRESHOLD1).append(",");
		sb.append(tblAbbA).append(".").append(ConstantValue.DB_COL_RESULT_SIZE).append(" ")
				.append(ConstantValue.DB_COL_RESULT_SIZE1).append(",");
		sb.append(tblAbbA).append(".").append(ConstantValue.DB_COL_RUNNING_TIME).append(" ")
				.append(ConstantValue.DB_COL_RUNNING_TIME1).append(",");

		sb.append(tblAbbB).append(".").append(ConstantValue.DB_COL_THRESHOLD).append(" ")
				.append(ConstantValue.DB_COL_THRESHOLD2).append(",");
		sb.append(tblAbbB).append(".").append(ConstantValue.DB_COL_RESULT_SIZE).append(" ")
				.append(ConstantValue.DB_COL_RESULT_SIZE2).append(",");
		sb.append(tblAbbB).append(".").append(ConstantValue.DB_COL_RUNNING_TIME).append(" ")
				.append(ConstantValue.DB_COL_RUNNING_TIME2).append("\n");

		sb.append("from ").append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append(code).append(maxSuff).append(" ")
				.append(tblAbbA).append(",");
		sb.append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append(code).append(minSuff).append(" ").append(tblAbbB)
				.append("\n");
		sb.append("where ").append(tblAbbA).append(".").append(ConstantValue.DB_COL_INS_ID).append("=").append(tblAbbB)
				.append(".").append(ConstantValue.DB_COL_INS_ID).append("\n");
		sb.append(")");

		return sb.toString();
	}

	@SuppressWarnings("finally")
	public static String generateReportSql(String datasetName) {
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append("select i_code from \"v_instance_opt\" where \"d_name\"=\"").append(datasetName).append("\";");
			c = getConnection();
			c.setAutoCommit(false);
			stmt = c.createStatement();
			rs = stmt.executeQuery(sqlSb.toString());

			StringBuffer sqlWithSb = new StringBuffer();
			StringBuffer sqlQueSb = new StringBuffer();

			sqlWithSb.append("with ");

			while (rs.next()) {
				String code = rs.getString(1);
				String insQuStr = singleInstanceQueryStr(code);
				sqlWithSb.append(insQuStr).append(",");
				sqlQueSb.append("select * from ").append("v_").append(ConstantValue.TBL_ALG_PREFIX).append(code)
						.append("_alg2").append("\n");
				sqlQueSb.append("union\n");
			}

			String sqlWith = sqlWithSb.substring(0, sqlWithSb.length() - 1);
			String sqlQue = sqlQueSb.substring(0, sqlQueSb.length() - "union\n".length());

			sqlSb.setLength(0);
			sqlSb.append(sqlWith).append("\n").append(sqlQue);

			return sqlSb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				c.commit();
				c.close();
			} catch (final Exception e) {

				e.printStackTrace();
			} finally {
				return null;
			}

		}
	}

}
