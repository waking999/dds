package au.edu.cdu.dds.util;

import org.junit.Test;

import au.edu.cdu.dds.io.DBParameter;

public class UtilTest {

	@Test
	public void testGetBatchNum() {

		String batchNum = Util.getBatchNum();
		System.out.println(batchNum);
	}

	@Test
	public void testCleanAlgoTablesDel() {
		DBParameter dbp = new DBParameter();
		String[] colPairNames = { ConstantValue.DB_COL_BATCH_NUM };
		String[] colPairOperators = { "=" };
		String[] colPairValues = { "20171101-045" };
		dbp.setColPairNames(colPairNames);
		dbp.setColPairOperators(colPairOperators);
		dbp.setColPairValues(colPairValues);
		Util.cleanAlgoTables(ConstantValue.CLN_MODE_DEL, ConstantValue.KONECT, dbp);

	}

	@Test
	public void testCleanAlgoTablesDrop() {

		Util.cleanAlgoTables(ConstantValue.CLN_MODE_DROP, ConstantValue.KONECT, null);
		Util.cleanAlgoTables(ConstantValue.CLN_MODE_DROP, ConstantValue.BHOSLIB, null);
	}

}