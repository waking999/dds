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
import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;
import au.edu.cdu.dds.util.LogUtil;
import au.edu.cdu.dds.util.Util;

public class GreedyVoteL2HDDSTest {
	private Logger log = LogUtil.getLogger(GreedyVoteL2HDDSTest.class);
	private static final String CLASS_NAME = GreedyVoteL2HDDSTest.class.getSimpleName();

	@Ignore
	public void testIgnore() {

	}

	//@Ignore
	@Test
	public void test0() throws IOException {
		String filePath = TestUtil.getCurrentPath() + "/src/test/resources/sample1.txt";
		int[] expect = new int[] { 1, 5 };

		GlobalVariable gv = new FileOperation().readGraphByEdgePair(filePath);

		IAlgorithm algo = new GreedyVoteL2HDDS();
		algo.setGV(gv);
		int k=3;
		int r=2;
		algo.setKR(k,r);
		algo.compute();
		Assert.assertTrue(Util.isValidSolution(gv));

		int[] sol = Util.getVertexSolution(gv);

		TestUtil.verifySort(expect, sol);

	}

	//@Ignore
	@Test
	public void testKONECT_verify() throws InterruptedException, IOException, FileNotFoundException {
		int kLower=10;
		int kUpper=10;
		IAlgorithm algo = new GreedyVoteL2HDDS();
	
		TestUtil.basicFunc(CLASS_NAME, ConstantValue.DATASET_KONECT, algo, log,kLower,kUpper);
	}

	@Ignore
	@Test
	public void testCreateReportView() {
	 
		String batchNum = "20171103-0056";
		DBOperation.createReportView(ConstantValue.DATASET_KONECT, CLASS_NAME, batchNum);
	}

	//@Ignore
	@Test
	public void testBHOSLIB_verify() throws InterruptedException, IOException, FileNotFoundException {
		int kLower=10;
		int kUpper=10;
		IAlgorithm algo = new GreedyVoteL2HDDS();
		TestUtil.basicFunc(CLASS_NAME, ConstantValue.DATASET_BHOSLIB, algo, log,kLower,kUpper);
	}
}