package au.edu.cdu.dds.algo.ds;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import au.edu.cdu.dds.TestUtil;
import au.edu.cdu.dds.io.FileOperation;
import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;
import au.edu.cdu.dds.util.LogUtil;
import au.edu.cdu.dds.util.Util;

public class GreedyVoteL2HAddTest {
	private Logger log = LogUtil.getLogger(GreedyVoteL2HAddTest.class);
	private static final String CLASS_NAME = GreedyVoteL2HAddTest.class.getSimpleName();

	@Test
	public void test0() throws IOException {
		String filePath = TestUtil.getCurrentPath() + "/src/test/resources/sample1.txt";
		String[] expect = new String[] { "1", "5" };

		GlobalVariable<String> gv = new FileOperation().readGraphByEdgePair(filePath);

		IAlgorithm<String> algo = new GreedyVoteL2HAdd();
		algo.setGV(gv);
		algo.compute();
		Assert.assertTrue(Util.isValidSolution(gv));

		String[] sol = Util.getVertexSolution(gv);

		TestUtil.verifySort(expect, sol);

	}

	//@Ignore
	@Test
	public void testKONECT_verify() throws InterruptedException, IOException, FileNotFoundException {

	 
		IAlgorithm<String> algo = new GreedyVoteL2HAdd();
		TestUtil.basicFunc(CLASS_NAME, ConstantValue.KONECT, algo, log);
	}

	//@Ignore
	@Test
	public void testBHOSLIB_verify() throws InterruptedException, IOException, FileNotFoundException {

		 
		IAlgorithm<String> algo = new GreedyVoteH2L();
		TestUtil.basicFunc(CLASS_NAME, ConstantValue.BHOSLIB, algo,  log);
	}
}
