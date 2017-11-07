package au.edu.cdu.dds.io;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import au.edu.cdu.dds.util.ConstantValue;

public class DBOperationTest {
	@Ignore
	public void testIgnore() {

	}

	@Ignore
	@Test
	public void testExecuteQuery() throws Exception {
		DBParameter dbp = new DBParameter();
		dbp.setTableName(ConstantValue.DB_TBNAME_INS);
		String[] colNames = { ConstantValue.DB_COL_ID, ConstantValue.DB_COL_INS_NAME,
				ConstantValue.DB_COL_INS_PATH_NAME, ConstantValue.DB_COL_V_COUNT, ConstantValue.DB_COL_E_COUNT };
		String[] colPairNames = { ConstantValue.DB_COL_INS_NAME, ConstantValue.DB_COL_INS_PATH_NAME };
		String[] colPairOperators = { "=", "=" };
		String[] colPairValues = { "cc", "cc.clq" };
		dbp.setColNames(colNames);
		dbp.setColPairNames(colPairNames);
		dbp.setColPairOperators(colPairOperators);
		dbp.setColPairValues(colPairValues);

		List<Map<String, String>> lst = DBOperation.executeQuery(dbp);
		Assert.assertNotNull(lst);
		Assert.assertTrue(lst.size() > 0);
	}

	@Ignore
	@Test
	public void testExecuteQuery2() throws Exception {
		DBParameter dbp = new DBParameter();
		dbp.setTableName(ConstantValue.DB_TBNAME_INS);
		String[] colNames = { ConstantValue.DB_COL_ID, ConstantValue.DB_COL_INS_NAME,
				ConstantValue.DB_COL_INS_PATH_NAME, ConstantValue.DB_COL_V_COUNT, ConstantValue.DB_COL_E_COUNT };
		String[] colPairNames = { ConstantValue.DB_COL_INS_NAME };

		String[] colPairOperators = { "like" };
		String[] colPairValues = { "%ro%" };
		dbp.setColNames(colNames);
		dbp.setColPairNames(colPairNames);
		dbp.setColPairOperators(colPairOperators);
		dbp.setColPairValues(colPairValues);

		List<Map<String, String>> lst = DBOperation.executeQuery(dbp);

		Assert.assertNotNull(lst);
		Assert.assertTrue(lst.size() > 0);

	}

	@Ignore
	@Test
	public void testExecuteQuery3() throws Exception {
		DBParameter dbp = new DBParameter();
		dbp.setTableName(ConstantValue.DB_TBNAME_INS);
		String[] colNames = { ConstantValue.DB_COL_ID, ConstantValue.DB_COL_INS_NAME,
				ConstantValue.DB_COL_INS_PATH_NAME, ConstantValue.DB_COL_V_COUNT, ConstantValue.DB_COL_E_COUNT };
		String[] colPairNames = { ConstantValue.DB_COL_V_COUNT };

		String[] colPairOperators = { "<" };
		String[] colPairValues = { "300" };
		dbp.setColNames(colNames);
		dbp.setColPairNames(colPairNames);
		dbp.setColPairOperators(colPairOperators);
		dbp.setColPairValues(colPairValues);

		List<Map<String, String>> lst = DBOperation.executeQuery(dbp);
		Assert.assertNotNull(lst);
		Assert.assertTrue(lst.size() > 0);
	}

	@Ignore
	@Test
	public void testExecuteInsert() throws Exception {
		DBParameter dbp = new DBParameter();
		dbp.setTableName(ConstantValue.DB_TBNAME_INS);
		String[] colNames = { ConstantValue.DB_COL_INS_NAME, ConstantValue.DB_COL_INS_PATH_NAME };

		String[] colValues = { "cc", "cc.clq" };
		dbp.setColPairNames(colNames);
		dbp.setColPairValues(colValues);

		DBOperation.executeInsert(dbp);

	}
	/*
	 * @Test public void testGenerateReportSql_KONECT() { String datasetName =
	 * "KONECT"; DBOperation.generateReportSql(datasetName);
	 * 
	 * }
	 * 
	 * @Test public void testGenerateReportSql_BHOSLIB() { String datasetName =
	 * "BHOSLIB"; DBOperation.generateReportSql(datasetName);
	 * 
	 * }
	 */

	@Ignore
	@Test
	public void testCleanAlgoTablesDel() {
		DBParameter dbp = new DBParameter();
		String[] colPairNames = { ConstantValue.DB_COL_BATCH_NUM };
		String[] colPairOperators = { "=" };
		String[] colPairValues = { "20171103-0051" };
		dbp.setColPairNames(colPairNames);
		dbp.setColPairOperators(colPairOperators);
		dbp.setColPairValues(colPairValues);
		DBOperation.cleanAlgoTables(ConstantValue.CLN_MODE_DEL, ConstantValue.DATASET_KONECT, dbp);

	}

	// @Ignore
	@Test
	public void testCleanAlgoTablesDrop() {

		DBOperation.cleanAlgoTables(ConstantValue.CLN_MODE_DROP, ConstantValue.DATASET_KONECT, null);
		DBOperation.cleanAlgoTables(ConstantValue.CLN_MODE_DROP, ConstantValue.DATASET_BHOSLIB, null);
	}

}
