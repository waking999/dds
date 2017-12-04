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

/**
 * this class is used for database operation including query, update and insert
 * 
 * @author kwang
 */
public class DBOperation {
	/**
	 * to get database connect to a specific db file
	 * 
	 * @return
	 * @throws Exception
	 */
	private static Connection getConnection() throws Exception {
		String clzName = "org.sqlite.JDBC";
		String dbName = "jdbc:sqlite:result/db/result.db";
		return getConnection(clzName, dbName);
	}

	/**
	 * get database connection
	 * 
	 * @param clzName
	 * @param dbName
	 * @return
	 * @throws Exception
	 */
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
	 *            db operation parameters, including table name, fields for select
	 *            clause and conditions in where clause
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
	 * execute delete operation
	 * 
	 * @param tableName,
	 *            the table name
	 * @param dbp,
	 *            conditions in where clauses
	 */
	public static void executeDel(String tableName, DBParameter dbp) {
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			c = getConnection();

			c.setAutoCommit(false);
			stmt = c.createStatement();

			StringBuffer sqlSB = new StringBuffer();
			sqlSB.append("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='").append(tableName)
					.append("';");
			rs = stmt.executeQuery(sqlSB.toString());
			int count = rs.getInt(1);

			if (count > 0) {

				sqlSB.setLength(0);
				sqlSB.append("delete from ").append(tableName);
				String[] colPairNames = dbp.getColPairNames();
				String[] colPairOperators = dbp.getColPairOperators();
				String[] colPairValues = dbp.getColPairValues();

				sqlSB.append(" where 1=1 ");

				for (int i = 0; i < colPairNames.length; i++) {
					String colPairName = colPairNames[i];
					String colPairOperator = colPairOperators[i];
					String colPairValue = colPairValues[i];
					sqlSB.append(" and \"").append(colPairName).append("\" ").append(colPairOperator).append(" \"")
							.append(colPairValue).append("\" ");
				}
				sqlSB.append(";");

				stmt.execute(sqlSB.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				c.commit();
				c.close();
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
	}

	/**
	 * drop table
	 * 
	 * @param tableName
	 */
	public static void executeDrop(String tableName) {
		Connection c = null;
		Statement stmt = null;

		try {
			StringBuffer sqlSB = new StringBuffer();

			sqlSB.append("drop table if exists ").append(tableName);
			sqlSB.append(";");
			c = getConnection();

			c.setAutoCommit(false);
			stmt = c.createStatement();
			stmt.execute(sqlSB.toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				c.commit();
				c.close();
			} catch (Exception e) {

				e.printStackTrace();
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

	@SuppressWarnings({ "finally" })
	/**
	 * create report view for a certain algorithm and data set in a particular
	 * running
	 * 
	 * @param dataSetName
	 * @param algorithm
	 * @param batchNum
	 */
	public static void createReportView(String dataSetName, String algorithm, String batchNum) {

		Connection c = null;
		Statement stmt = null;

		try {
			StringBuffer sbViewName = new StringBuffer();
			String viewName = sbViewName.append("v_").append(algorithm).append("_").append(dataSetName).toString();

			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append("drop view if exists ").append(viewName).append(";");
			// System.out.println(sqlSb.toString());
			c = getConnection();
			c.setAutoCommit(false);
			stmt = c.createStatement();
			stmt.execute(sqlSb.toString());

			// get the info of the instances of a certain dataset such as id, code, path
			List<Map<String, String>> lst = DBOperation.getInstanceInfo(dataSetName);

			sqlSb.setLength(0);
			sqlSb.append("CREATE view IF NOT EXISTS ").append(viewName).append(" as \n");
			for (Map<String, String> map : lst) {
				String instanceCode = map.get(ConstantValue.DB_COL_INS_CODE);
				String id = map.get(ConstantValue.DB_COL_INS_ID);
				String algTableName = ConstantValue.TBL_ALG_PREFIX + id + "_" + instanceCode;

				sqlSb.append("select ");
				sqlSb.append("i.").append(ConstantValue.DB_COL_INS_ID).append(ConstantValue.COMMA).append("a.")
						.append(ConstantValue.DB_COL_ALGORITHM).append(ConstantValue.COMMA).append("i.")
						.append(ConstantValue.DB_COL_INS_NAME).append(ConstantValue.COMMA).append("a.")
						.append(ConstantValue.DB_COL_RESULT_SIZE).append(ConstantValue.COMMA).append("a.")
						.append(ConstantValue.DB_COL_RUNNING_TIME).append("\n");
				sqlSb.append("from ").append(algTableName).append(" a ").append(ConstantValue.COMMA)
						.append(ConstantValue.DB_VNAME_INS).append(" i \n");
				sqlSb.append("where a.").append(ConstantValue.DB_COL_INS_ID).append("=").append("i.")
						.append(ConstantValue.DB_COL_INS_ID).append(" and ").append("a.")
						.append(ConstantValue.DB_COL_BATCH_NUM).append("=\"").append(batchNum).append("\"\n");
				sqlSb.append("and exists (select * from ").append(algTableName).append(")\n");
				sqlSb.append("union \n");

			}
			int sqlSbLen = sqlSb.length();
			String sql = sqlSb.substring(0, sqlSbLen - 7);
			sql += ";";
			System.out.println(sql);
			stmt.execute(sql);

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

	/**
	 * create table for each instance. If a table already exist, ignore.
	 * 
	 * @param instanceCode,
	 *            instance code
	 */

	@SuppressWarnings("finally")
	public static void createTable(String tableName) {
		Connection c = null;
		Statement stmt = null;

		try {
			StringBuffer sb = new StringBuffer();
			// sqlSb.append("drop table if exists
			// ").append(tblPrefix).append(instanceCode).append(";");
			c = getConnection();
			c.setAutoCommit(false);
			stmt = c.createStatement();
			// stmt.execute(sqlSb.toString());

			sb.setLength(0);

			sb.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append("(\n");
			sb.append(ConstantValue.BLANK).append(ConstantValue.DB_COL_ID).append(ConstantValue.BLANK).append("INTEGER PRIMARY KEY AUTOINCREMENT, \n");
			sb.append(ConstantValue.BLANK).append(ConstantValue.DB_COL_INS_ID).append(ConstantValue.BLANK).append("varchar2(10),\n");
			sb.append(ConstantValue.BLANK).append(ConstantValue.DB_COL_K).append(ConstantValue.BLANK).append("INTEGER,\n");
			sb.append(ConstantValue.BLANK).append(ConstantValue.DB_COL_R).append(ConstantValue.BLANK).append("INTEGER,\n");
			sb.append(ConstantValue.BLANK).append(ConstantValue.DB_COL_RESULT_SIZE).append(ConstantValue.BLANK).append("INTEGER,\n");
			sb.append(ConstantValue.BLANK).append(ConstantValue.DB_COL_RUNNING_TIME).append(ConstantValue.BLANK).append("INTEGER,\n");
			sb.append(ConstantValue.BLANK).append(ConstantValue.DB_COL_BATCH_NUM).append(ConstantValue.BLANK).append("varchar2(30),\n");
			sb.append(ConstantValue.BLANK).append(ConstantValue.DB_COL_RESULTS).append(ConstantValue.BLANK).append("text \n");
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

	/**
	 * clean algorithm tables
	 * 
	 * @param mode
	 * @param dataSetName
	 * @param dbp
	 */
	public static void cleanAlgoTables(String mode, String dataSetName, DBParameter dbp) {
		switch (mode) {
		case ConstantValue.CLN_MODE_DROP:
			DBOperation.cleanAlgoTableDrop(dataSetName);
			break;
		case ConstantValue.CLN_MODE_DEL:
			DBOperation.cleanAlgoTableDel(dataSetName, dbp);
			break;
		default:
			break;
		}
	}

	/**
	 * drop algorithm table
	 * 
	 * @param algorithmNam
	 */
	private static void cleanAlgoTableDrop(String algorithmNam) {

		String algTableName = DBOperation.getAlgorithmTableName(algorithmNam);
		executeDrop(algTableName);

	}

	/**
	 * delete data from a algorithm table
	 * 
	 * @param algorithmNam
	 * @param dbp
	 */
	private static void cleanAlgoTableDel(String algorithmNam, DBParameter dbp) {

		String algTableName = DBOperation.getAlgorithmTableName(algorithmNam);
		executeDel(algTableName, dbp);

	}

	/**
	 * @param algorithmNam, algorithm name
	 * @return
	 */
	public static String getAlgorithmTableName(String algorithmNam) {
		String algTableName = ConstantValue.TBL_ALG_PREFIX +  algorithmNam;
		return algTableName;
	}

	/**
	 * get the infomation of instances by data set such as id, code, path and so on
	 * 
	 * @param dataSetName
	 * @return
	 */
	public static List<Map<String, String>> getInstanceInfo(String dataSetName) {
		DBParameter dbpIn = new DBParameter();
		dbpIn.setTableName(ConstantValue.DB_VNAME_INS);
		String[] colNames = { ConstantValue.DB_COL_INS_ID, ConstantValue.DB_COL_INS_CODE, ConstantValue.DB_COL_INS_NAME,
				ConstantValue.DB_COL_DATASET_NAME, ConstantValue.DB_COL_DATASET_PATH_NAME,
				ConstantValue.DB_COL_INS_PATH_NAME };
		String[] colPairNames = { ConstantValue.DB_COL_DATASET_NAME, ConstantValue.DB_COL_TO_BE_TESTED };
		String[] colPairOperators = { "=", "=" };
		String[] colPairValues = { dataSetName, "1" };
		dbpIn.setColNames(colNames);
		dbpIn.setColPairNames(colPairNames);
		dbpIn.setColPairOperators(colPairOperators);
		dbpIn.setColPairValues(colPairValues);

		List<Map<String, String>> lst = executeQuery(dbpIn);
		dbpIn = null;
		return lst;
	}

	/*
	 * private static String singleInstanceQueryStr(String code) { StringBuffer sb =
	 * new StringBuffer(); // String tblPrefix = ConstantValue.TBL_ALG_PREFIX;
	 * String viewPre = "v_"; String viewSuff = "_alg2"; String maxSuff = "_max";
	 * String minSuff = "_min";
	 * 
	 * String tblAbbA = "a"; String tblAbbB = "b"; // v_algRunning_code
	 * sb.append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append(code).
	 * append(" as (\n");
	 * sb.append("select ").append(tblAbbA).append(".* from (\n");
	 * sb.append("select ").append(ConstantValue.DB_COL_INS_ID).append(",");
	 * sb.append(ConstantValue.DB_COL_RESULT_SIZE).append(",");
	 * sb.append("max(").append(ConstantValue.DB_COL_THRESHOLD).append(") max_").
	 * append(ConstantValue.DB_COL_THRESHOLD) .append("\n");
	 * sb.append(" from ").append(ConstantValue.TBL_ALG_PREFIX).append(code).append(
	 * "\n");
	 * sb.append(" group by ").append(ConstantValue.DB_COL_INS_ID).append(",")
	 * .append(ConstantValue.DB_COL_ACCEPT_RESULT_SIZE).append("\n");
	 * sb.append(") ").append(tblAbbB).append(",").append(ConstantValue.
	 * TBL_ALG_PREFIX).append(code).append(" ") .append(tblAbbA).append("\n");
	 * sb.append("where ").append(tblAbbA).append(".").append(ConstantValue.
	 * DB_COL_INS_ID).append("=").append(tblAbbB)
	 * .append(".").append(ConstantValue.DB_COL_INS_ID).append("\n");
	 * sb.append("and ").append(tblAbbA).append(".").append(ConstantValue.
	 * DB_COL_RESULT_SIZE).append("=")
	 * .append(tblAbbB).append(".").append(ConstantValue.DB_COL_RESULT_SIZE).append(
	 * "\n"); sb.append("and ").append(tblAbbA).append(".").append(ConstantValue.
	 * DB_COL_THRESHOLD).append("=").append(tblAbbB)
	 * .append(".max_").append(ConstantValue.DB_COL_THRESHOLD).append("\n");
	 * sb.append("),\n"); // v_algRunning_code_max
	 * sb.append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append(code).append(
	 * maxSuff).append(" as (\n");
	 * sb.append("select ").append(ConstantValue.DB_COL_INS_ID).append(",").append(
	 * ConstantValue.DB_COL_THRESHOLD)
	 * .append(",").append(ConstantValue.DB_COL_RUNNING_TIME).append(",")
	 * .append(ConstantValue.DB_COL_RESULT_SIZE).append("\n");
	 * sb.append("from").append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append
	 * (code).append("\n");
	 * sb.append("where ").append(ConstantValue.DB_COL_THRESHOLD).append("=(").
	 * append("select max(")
	 * .append(ConstantValue.DB_COL_THRESHOLD).append(" from ").append(viewPre)
	 * .append(ConstantValue.TBL_ALG_PREFIX).append(code).append(")\n");
	 * sb.append("),\n"); // v_algRunning_code_min
	 * sb.append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append(code).append(
	 * minSuff).append(" as (\n");
	 * sb.append("select ").append(ConstantValue.DB_COL_INS_ID).append(",").append(
	 * ConstantValue.DB_COL_THRESHOLD)
	 * .append(",").append(ConstantValue.DB_COL_RUNNING_TIME).append(",")
	 * .append(ConstantValue.DB_COL_RESULT_SIZE).append("\n");
	 * sb.append("from").append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append
	 * (code).append("\n");
	 * sb.append("where ").append(ConstantValue.DB_COL_THRESHOLD).append("=(").
	 * append("select min(")
	 * .append(ConstantValue.DB_COL_THRESHOLD).append(" from ").append(viewPre)
	 * .append(ConstantValue.TBL_ALG_PREFIX).append(code).append(")\n");
	 * sb.append("),\n"); // v_algRunning_code_alg2
	 * sb.append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append(code).append(
	 * viewSuff).append(" as (\n");
	 * sb.append("select ").append(tblAbbA).append(".").append(ConstantValue.
	 * DB_COL_INS_ID).append(",");
	 * sb.append(tblAbbA).append(".").append(ConstantValue.DB_COL_THRESHOLD).
	 * append(" ") .append(ConstantValue.DB_COL_THRESHOLD1).append(",");
	 * sb.append(tblAbbA).append(".").append(ConstantValue.DB_COL_RESULT_SIZE).
	 * append(" ") .append(ConstantValue.DB_COL_RESULT_SIZE1).append(",");
	 * sb.append(tblAbbA).append(".").append(ConstantValue.DB_COL_RUNNING_TIME).
	 * append(" ") .append(ConstantValue.DB_COL_RUNNING_TIME1).append(",");
	 * 
	 * sb.append(tblAbbB).append(".").append(ConstantValue.DB_COL_THRESHOLD).
	 * append(" ") .append(ConstantValue.DB_COL_THRESHOLD2).append(",");
	 * sb.append(tblAbbB).append(".").append(ConstantValue.DB_COL_RESULT_SIZE).
	 * append(" ") .append(ConstantValue.DB_COL_RESULT_SIZE2).append(",");
	 * sb.append(tblAbbB).append(".").append(ConstantValue.DB_COL_RUNNING_TIME).
	 * append(" ") .append(ConstantValue.DB_COL_RUNNING_TIME2).append("\n");
	 * 
	 * sb.append("from ").append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).
	 * append(code).append(maxSuff).append(" ") .append(tblAbbA).append(",");
	 * sb.append(viewPre).append(ConstantValue.TBL_ALG_PREFIX).append(code).append(
	 * minSuff).append(" ").append(tblAbbB) .append("\n");
	 * sb.append("where ").append(tblAbbA).append(".").append(ConstantValue.
	 * DB_COL_INS_ID).append("=").append(tblAbbB)
	 * .append(".").append(ConstantValue.DB_COL_INS_ID).append("\n");
	 * sb.append(")");
	 * 
	 * return sb.toString(); }
	 * 
	 * @SuppressWarnings("finally") public static String generateReportSql(String
	 * datasetName) { Connection c = null; Statement stmt = null; ResultSet rs =
	 * null;
	 * 
	 * try { StringBuffer sqlSb = new StringBuffer();
	 * sqlSb.append("select i_code from \"v_instance_opt\" where \"d_name\"=\"").
	 * append(datasetName).append("\";"); c = getConnection();
	 * c.setAutoCommit(false); stmt = c.createStatement(); rs =
	 * stmt.executeQuery(sqlSb.toString());
	 * 
	 * StringBuffer sqlWithSb = new StringBuffer(); StringBuffer sqlQueSb = new
	 * StringBuffer();
	 * 
	 * sqlWithSb.append("with ");
	 * 
	 * while (rs.next()) { String code = rs.getString(1); String insQuStr =
	 * singleInstanceQueryStr(code); sqlWithSb.append(insQuStr).append(",");
	 * sqlQueSb.append("select * from ").append("v_").append(ConstantValue.
	 * TBL_ALG_PREFIX).append(code) .append("_alg2").append("\n");
	 * sqlQueSb.append("union\n"); }
	 * 
	 * String sqlWith = sqlWithSb.substring(0, sqlWithSb.length() - 1); String
	 * sqlQue = sqlQueSb.substring(0, sqlQueSb.length() - "union\n".length());
	 * 
	 * sqlSb.setLength(0); sqlSb.append(sqlWith).append("\n").append(sqlQue);
	 * 
	 * return sqlSb.toString();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } finally { try { stmt.close();
	 * c.commit(); c.close(); } catch (final Exception e) {
	 * 
	 * e.printStackTrace(); } finally { return null; }
	 * 
	 * } }
	 */

}
