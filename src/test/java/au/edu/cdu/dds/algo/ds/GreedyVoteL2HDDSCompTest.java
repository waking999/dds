package au.edu.cdu.dds.algo.ds;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import au.edu.cdu.dds.TestUtil;
import au.edu.cdu.dds.io.DBOperation;
import au.edu.cdu.dds.io.FileOperation;
import au.edu.cdu.dds.util.AlgoUtil;
import au.edu.cdu.dds.util.ConnectComponents;
import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;
import au.edu.cdu.dds.util.LogUtil;
import au.edu.cdu.dds.util.Util;

public class GreedyVoteL2HDDSCompTest {

	private static final String CLASS_NAME = GreedyVoteL2HDDSCompTest.class.getSimpleName();
	private Logger log = LogUtil.getLogger(CLASS_NAME);

	@Ignore
	public void testIgnore() {

	}

	@Ignore
	@Test
	public void test0() throws IOException {
		String filePath = TestUtil.getBasePath() + "/src/test/resources/sample2.txt";
		int[] expect = new int[] { 1, 5 };

		GlobalVariable g = FileOperation.readGraphByEdgePair(filePath);

		IAlgorithm algo = new GreedyVoteL2HDDSComp();
		algo.setGlobalVariable(g);
		int k = 3;
		int r = 2;
		algo.setKR(k, r);
		algo.compute();
		Assert.assertTrue(AlgoUtil.isValidSolution(g));

		int[] sol = AlgoUtil.getLabSolution(g);

		TestUtil.verifySort(expect, sol);

	}

	@Ignore
	@Test
	public void testKONECTComponents() throws InterruptedException, IOException, FileNotFoundException {

		List<Map<String, String>> lst = DBOperation.getInstanceInfo(ConstantValue.DATASET_KONECT);

		String resourcePath = TestUtil.getBasePath() + "/src/test/resources";

		for (Map<String, String> map : lst) {
			String dataSetPath = map.get(ConstantValue.DB_COL_DATASET_PATH_NAME);
			String pathName = map.get(ConstantValue.DB_COL_INS_PATH_NAME);
			String code = map.get(ConstantValue.DB_COL_INS_CODE);

			String inputFile = resourcePath + dataSetPath + pathName;
			GlobalVariable g = FileOperation.readGraphByEdgePair(inputFile);
			ConnectComponents cc = new ConnectComponents();
			cc.setG(g);
			List<Set<Integer>> list = cc.getConnectComponents();
			int listSize = list.size();
			System.out.println("The number of components of " + code + ":" + listSize);
		}
	}

	@Ignore
	@Test
	public void testKONECT_LoopIns() throws InterruptedException, IOException, FileNotFoundException {
		int k = 10;
		int r = 7;
		IAlgorithm algo = new GreedyVoteL2HDDSComp();

		TestUtil.basicFuncLoopIns(CLASS_NAME, ConstantValue.DATASET_KONECT, algo, k, r, log);
	}
	
	@Ignore
	@Test
	public void testDIMACS_LoopKR() throws InterruptedException, IOException, FileNotFoundException {

		int kLower=3;
		int kUpper=20;
		IAlgorithm algo = new GreedyVoteL2HDDSComp();
		TestUtil.basicFuncLoopInsLoopKR(CLASS_NAME, ConstantValue.DATASET_DIMACS, algo, kLower, kUpper, log);
	}
	
	
	//@Ignore
	@Test
	public void testBHOSlIB_LoopKR() throws InterruptedException, IOException, FileNotFoundException {

		int kLower=41;
		int kUpper=60;
		IAlgorithm algo = new GreedyVoteL2HDDSComp();
		TestUtil.basicFuncLoopInsLoopKR(CLASS_NAME, ConstantValue.DATASET_BHOSLIB, algo, kLower, kUpper, log);
	}
	@Ignore
	@Test
	public void testKONECT_LoopKR_C4000_5() throws InterruptedException, IOException, FileNotFoundException {

		int kLower = 51;
		int kUpper = 60;
		String id = "1_12";
		String instanceCode = "C4000.5";
		String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
		String dataSetPath = "/DIMACS";
		String pathName = "/C4000.5.clq";
		String inputFile = resourcePath + dataSetPath + pathName;
		String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

		IAlgorithm algo = new GreedyVoteL2HDDS();
		String batchNum = Util.getBatchNum();

		TestUtil.basicFuncLoopKR(CLASS_NAME, algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,
				log);
	}
	@Ignore
	@Test
	public void testKONECT_LoopKR_p_hat700_1() throws InterruptedException, IOException, FileNotFoundException {

		int kLower = 71;
		int kUpper = 90;
		String id = "1_34";
		String instanceCode = "p_hat700-1";
		String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
		String dataSetPath = "/DIMACS";
		String pathName = "/p_hat700-1.clq";
		String inputFile = resourcePath + dataSetPath + pathName;
		String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

		IAlgorithm algo = new GreedyVoteL2HDDS();
		String batchNum = Util.getBatchNum();

		TestUtil.basicFuncLoopKR(CLASS_NAME, algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,
				log);
	}
	
	
	@Ignore
	@Test
	public void testDIMACS_MIS_LoopKR() throws InterruptedException, IOException, FileNotFoundException {

		int kLower=3;
		int kUpper=20;
		IAlgorithm algo = new GreedyVoteL2HDDSComp();
		TestUtil.basicFuncLoopInsLoopKR(CLASS_NAME, ConstantValue.DATASET_DIMACS_MIS, algo, kLower, kUpper, log);
	}

}
