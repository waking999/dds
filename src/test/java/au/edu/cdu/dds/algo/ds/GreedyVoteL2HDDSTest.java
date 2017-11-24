package au.edu.cdu.dds.algo.ds;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import au.edu.cdu.dds.TestUtil;
import au.edu.cdu.dds.io.DBOperation;
import au.edu.cdu.dds.io.FileOperation;
import au.edu.cdu.dds.util.AlgoUtil;
import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.ISGlobalVariable;
import au.edu.cdu.dds.util.LogUtil;
import au.edu.cdu.dds.util.Util;

public class GreedyVoteL2HDDSTest {
	
	private static final String CLASS_NAME = GreedyVoteL2HDDSTest.class.getSimpleName();
	private Logger log = LogUtil.getLogger(CLASS_NAME);
	
	@Ignore
	public void testIgnore() {

	}

	@Ignore
	@Test
	public void test0() throws IOException {
		String filePath = TestUtil.getBasePath() + "/src/test/resources/sample1.txt";
		int[] expect = new int[] { 1, 5 };

		ISGlobalVariable gv = new FileOperation().readGraphByEdgePair(filePath);

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
	public void testKONECT_LoopIns() throws InterruptedException, IOException, FileNotFoundException {
		int k = 10;
		int r = 7;
		IAlgorithm algo = new GreedyVoteL2HDDS();

		TestUtil.basicFuncLoopIns(CLASS_NAME, ConstantValue.DATASET_KONECT, algo, k, r, log);
	}

	@Ignore
	@Test
	public void testKONECT_Dolphins() throws InterruptedException, IOException, FileNotFoundException {

		int k = 10;
		int r = 7;
		String id = "3_03";
		String instanceCode = "Dolphins";
		String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
		String dataSetPath = "/KONECT";
		String pathName = "/000062_dolphins.konect";
		String inputFile = resourcePath + dataSetPath + pathName;
		String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

		IAlgorithm algo = new GreedyVoteL2HDDS();
		String batchNum = Util.getBatchNum();

		TestUtil.basicFunc(CLASS_NAME, algo, batchNum, id, instanceCode, algTableName, inputFile, k, r,log);
	}
	
	@Ignore
	@Test
	public void testKONECT_LoopKR_Dolphins() throws InterruptedException, IOException, FileNotFoundException {

		int kLower = 3;
		int kUpper = 50;
		String id = "3_03";
		String instanceCode = "Dolphins";
		String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
		String dataSetPath = "/KONECT";
		String pathName = "/000062_dolphins.konect";
		String inputFile = resourcePath + dataSetPath + pathName;
		String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

		IAlgorithm algo = new GreedyVoteL2HDDS();
		String batchNum = Util.getBatchNum();

		TestUtil.basicFuncLoopKR(CLASS_NAME, algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,log);
	}

	@Ignore
	@Test
	public void testKONECT_Rovira() throws InterruptedException, IOException, FileNotFoundException {

		int k = 20;
		int r = 7;
		String id = "3_07";
		String instanceCode = "Rovira";
		String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
		String dataSetPath = "/KONECT";
		String pathName = "/001133_rovira.konect";
		String inputFile = resourcePath + dataSetPath + pathName;
		String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

		IAlgorithm algo = new GreedyVoteL2HDDS();
		String batchNum = Util.getBatchNum();

		TestUtil.basicFunc(CLASS_NAME, algo, batchNum, id, instanceCode, algTableName, inputFile, k, r,log);
	}


	@Ignore
	@Test
	public void testKONECT_LoopKR_Rovira() throws InterruptedException, IOException, FileNotFoundException {

		int kLower = 3;
		int kUpper = 50;
		String id = "3_07";
		String instanceCode = "Rovira";
		String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
		String dataSetPath = "/KONECT";
		String pathName = "/001133_rovira.konect";
		String inputFile = resourcePath + dataSetPath + pathName;
		String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

		IAlgorithm algo = new GreedyVoteL2HDDS();
		String batchNum = Util.getBatchNum();

		TestUtil.basicFuncLoopKR(CLASS_NAME, algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,log);
	}


	@Ignore
	@Test
	public void testKONECT_LoopKR_HamsterFul() throws InterruptedException, IOException, FileNotFoundException {

		int kLower = 26;
		int kUpper = 50;
		String id = "3_10";
		String instanceCode = "HamsterFul";
		String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
		String dataSetPath = "/KONECT";
		String pathName = "/002426_hamster_ful.konect";
		String inputFile = resourcePath + dataSetPath + pathName;
		String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

		IAlgorithm algo = new GreedyVoteL2HDDS();
		String batchNum = Util.getBatchNum();

		TestUtil.basicFuncLoopKR(CLASS_NAME, algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,log);
	}

	//@Ignore
	@Test
	public void testKONECT_LoopKR_DavidCopperfield() throws InterruptedException, IOException, FileNotFoundException {

		int kLower = 3;
		int kUpper = 20;
		String id = "3_04";
		String instanceCode = "DavidCopperfield";
		String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
		String dataSetPath = "/KONECT";
		String pathName = "/000112_David_Copperfield.konect";
		String inputFile = resourcePath + dataSetPath + pathName;
		String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

		IAlgorithm algo = new GreedyVoteL2HDDS();
		String batchNum = Util.getBatchNum();

		TestUtil.basicFuncLoopKR(CLASS_NAME, algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,log);
	}

	
	//@Ignore
	@Test
	public void testKONECT_LoopKR_Powergrid() throws InterruptedException, IOException, FileNotFoundException {

		int kLower = 3;
		int kUpper = 50;
		String id = "3_13";
		String instanceCode = "Powergrid";
		String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
		String dataSetPath = "/KONECT";
		String pathName = "/004941_powergrid.konect";
		String inputFile = resourcePath + dataSetPath + pathName;
		String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

		IAlgorithm algo = new GreedyVoteL2HDDS();
		String batchNum = Util.getBatchNum();

		TestUtil.basicFuncLoopKR(CLASS_NAME, algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,log);
	}


	@Ignore
	@Test
	public void testKONECT_LoopKR_Pretty() throws InterruptedException, IOException, FileNotFoundException {

		int kLower = 3;
		int kUpper = 50;
		String id = "3_16";
		String instanceCode = "PrettyGoodPrivacy";
		String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
		String dataSetPath = "/KONECT";
		String pathName = "/010680_Pretty_Good_Privacy.konect";
		String inputFile = resourcePath + dataSetPath + pathName;
		String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

		IAlgorithm algo = new GreedyVoteL2HDDS();
		String batchNum = Util.getBatchNum();

		TestUtil.basicFuncLoopKR(CLASS_NAME, algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,log);
	}

	@Ignore
	@Test
	public void testCreateReportView() {

		String batchNum = "20171103-0056";
		DBOperation.createReportView(ConstantValue.DATASET_KONECT, CLASS_NAME, batchNum);
	}

	@Ignore
	@Test
	public void testBHOSLIB_LoopIns() throws InterruptedException, IOException, FileNotFoundException {
		int k = 10;
		int r = 7;
		IAlgorithm algo = new GreedyVoteL2HDDS();

		TestUtil.basicFuncLoopIns(CLASS_NAME, ConstantValue.DATASET_BHOSLIB, algo, k, r,log);
	}
}
