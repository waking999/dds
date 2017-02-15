package au.edu.cdu.dds.algo.ds;

import java.io.IOException;

import org.junit.Test;

import au.edu.cdu.dds.TestUtil;
import au.edu.cdu.dds.util.ConstantValue;

public class GreedyMDSTest {

	@Test
	public void testBHOSLIB() throws IOException {
		String algTableName = ConstantValue.DB_TBNAME_ALG1;

		String[] instanceCodes = { "frb30_15_1", "frb30_15_2", "frb30_15_3", "frb30_15_4", "frb30_15_5", "frb35_17_1",
				"frb35_17_2", "frb35_17_3", "frb35_17_4", "frb35_17_5", "frb40_19_1", "frb40_19_2", "frb40_19_3",
				"frb40_19_4", "frb40_19_5", "frb45_21_1", "frb45_21_2", "frb45_21_3", "frb45_21_4", "frb45_21_5", };
		IMDS<String, String> msc = new GreedyMDS<String, String>();

		TestUtil.basicTest(instanceCodes, algTableName, msc);

	}

}
