package au.edu.cdu.dds.algo.ds;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import au.edu.cdu.dds.TestParameter;
import au.edu.cdu.dds.TestUtil;
import au.edu.cdu.dds.io.FileOperation;
import au.edu.cdu.dds.util.GlobalVariable;
import au.edu.cdu.dds.util.LogUtil;
import au.edu.cdu.dds.util.Util;

public class GreedyVoteL2HAddTest {
	private Logger log = LogUtil.getLogger(GreedyVoteL2HAddTest.class);
	private static final String CLASS_NAME = GreedyVoteL2HAddTest.class.getSimpleName();

	@Ignore
	@Test
	public void test0() throws IOException {
		String filePath = TestUtil.getCurrentPath() + "/src/test/resources/sample1.txt";
		String[] expect = new String[] { "1", "5" };

		GlobalVariable<String> gv = new FileOperation().readGraphByEdgePair(filePath);

		IAlgorithm algo = new GreedyVoteL2HAdd(gv);
		algo.compute();
		Assert.assertTrue(Util.isValidSolution(gv));

		String[] sol = Util.getVertexSolution(gv);

		TestUtil.verifySort(expect, sol);

	}

	@Test
	public void testKONECT_verify() throws InterruptedException, IOException, FileNotFoundException {

		String path = TestUtil.KONECT_PATH;
		basicFunc(path, TestUtil.KONECT_TP, log);
	}

	/**
	 * @param path
	 * @param files
	 * @param destFile
	 * @param iLower
	 * @param iUpper
	 * @param log
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static void basicFunc(String path, TestParameter[] tps, Logger log)
			throws FileNotFoundException, IOException, InterruptedException {

		for (TestParameter tp : tps) {
			if (tp.isBeTest()) {
				StringBuffer sb = new StringBuffer(CLASS_NAME);

				sb.append("-").append(tp.getFile()).append(",");

				String inputFile = path + tp.getFile();
				GlobalVariable<String> gv = new FileOperation().readGraphByEdgePair(inputFile);

				IAlgorithm algo = new GreedyVoteL2HAdd(gv);

				long start = System.nanoTime();
				algo.compute();
				long end = System.nanoTime();

				Assert.assertTrue(Util.isValidSolution(gv));

				sb.append((end - start) + " ns,");
				sb.append(gv.getIdxSolSize());

				System.out.println(sb.toString());
			}

		}

	}

}
