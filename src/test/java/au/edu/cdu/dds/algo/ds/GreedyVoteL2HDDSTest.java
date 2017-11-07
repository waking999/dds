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
import au.edu.cdu.dds.util.Util;

public class GreedyVoteL2HDDSTest {
	private Logger log = LogUtil.getLogger(GreedyVoteL2HDDSTest.class);
	private static final String CLASS_NAME = GreedyVoteL2HDDSTest.class.getSimpleName();

	@Ignore
	public void testIgnore() {

	}

	@Ignore
	@Test
	public void test0() throws IOException {
		String filePath = TestUtil.getBasePath() + "/src/test/resources/sample1.txt";
		int[] expect = new int[] { 1, 5 };

		GlobalVariable gv = new FileOperation().readGraphByEdgePair(filePath);

		IAlgorithm algo = new GreedyVoteL2HDDS();
		algo.setGlobalVariable(gv);
		int k = 3;
		int r = 2;
		algo.setKR(k, r);
		algo.compute();
		Assert.assertTrue(AlgoUtil.isValidSolution(gv));

		int[] sol = AlgoUtil.getLabSolution(gv);

		TestUtil.verifySort(expect, sol);

	}

	@Ignore
	@Test
	public void testKONECT_verify() throws InterruptedException, IOException, FileNotFoundException {
		int kLower = 10;
		int kUpper = 10;
		IAlgorithm algo = new GreedyVoteL2HDDS();

		TestUtil.basicLoopFunc(CLASS_NAME, ConstantValue.DATASET_KONECT, algo, log, kLower, kUpper);
	}

	@Test
	public void testKONECT_Dolphins() throws InterruptedException, IOException, FileNotFoundException {

		int k = 20;
		int r =7;
		String id = "3_03";
		String instanceCode = "Dolphins";
		String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
		String dataSetPath = "/KONECT";
		String pathName = "/000062_dolphins.konect";
		String inputFile = resourcePath + dataSetPath + pathName;
		String algTableName = DBOperation.getAlgorithmTableName(instanceCode, id);

		IAlgorithm algo = new GreedyVoteL2HDDS();
		String batchNum = Util.getBatchNum();

		TestUtil.basicFunc(CLASS_NAME, algo, log, batchNum, id, instanceCode, algTableName, inputFile, k, r);
	}

	@Ignore
	@Test
	public void testCreateReportView() {

		String batchNum = "20171103-0056";
		DBOperation.createReportView(ConstantValue.DATASET_KONECT, CLASS_NAME, batchNum);
	}

	@Ignore
	@Test
	public void testBHOSLIB_verify() throws InterruptedException, IOException, FileNotFoundException {
		int kLower = 10;
		int kUpper = 10;
		IAlgorithm algo = new GreedyVoteL2HDDS();
		TestUtil.basicLoopFunc(CLASS_NAME, ConstantValue.DATASET_BHOSLIB, algo, log, kLower, kUpper);
	}
}
