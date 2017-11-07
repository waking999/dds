package au.edu.cdu.dds.algo.ds;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import au.edu.cdu.dds.LogUtil;
import au.edu.cdu.dds.TestUtil;
import au.edu.cdu.dds.io.DBOperation;
import au.edu.cdu.dds.io.FileOperation;
import au.edu.cdu.dds.util.AlgoUtil;
import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;

public class GreedyVoteH2LTest {
	private Logger log = LogUtil.getLogger(GreedyVoteH2LTest.class);
	private static final String CLASS_NAME = GreedyVoteH2LTest.class.getSimpleName();

	@Ignore
	public void testIgnore() {
	}

	@Test
	public void test0() throws IOException {
		String filePath = TestUtil.getBasePath() + "/src/test/resources/sample1.txt";
		int[] expect = new int[] { 4, 5, 2 };

		GlobalVariable gv = new FileOperation().readGraphByEdgePair(filePath);

		IAlgorithm algo = new GreedyVoteH2L();
		algo.setGlobalVariable(gv);
		algo.compute();
		Assert.assertTrue(AlgoUtil.isValidSolution(gv));

		int[] sol = AlgoUtil.getLabSolution(gv);

		TestUtil.verifySort(expect, sol);
	}

	@Test
	public void testKONECT_verify() throws InterruptedException, IOException, FileNotFoundException {
		IAlgorithm algo = new GreedyVoteH2L();
		TestUtil.basicFunc(CLASS_NAME, ConstantValue.DATASET_KONECT, algo, log);
	}

	@Ignore
	@Test
	public void testCreateReportView() {

		String batchNum = "20171103-0059";
		DBOperation.createReportView(ConstantValue.DATASET_KONECT, CLASS_NAME, batchNum);
	}

	@Test
	public void testBHOSLIB_verify() throws InterruptedException, IOException, FileNotFoundException {
		IAlgorithm algo = new GreedyVoteH2L();
		TestUtil.basicFunc(CLASS_NAME, ConstantValue.DATASET_BHOSLIB, algo, log);
	}

}
