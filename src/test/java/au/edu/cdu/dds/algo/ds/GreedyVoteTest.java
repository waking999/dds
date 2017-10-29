package au.edu.cdu.dds.algo.ds;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import au.edu.cdu.dds.TestUtil;
import au.edu.cdu.dds.io.FileOperation;
import au.edu.cdu.dds.util.GlobalVariable;
import au.edu.cdu.dds.util.Util;

public class GreedyVoteTest {
	//private Logger log = LogUtil.getLogger(GreedyVoteTest.class);

	@Test
	public void test0() throws IOException {
		String filePath = TestUtil.getCurrentPath() + "/src/test/resources/sample1.txt";
		String[] expect = new String[] { "1", "5" };

		GlobalVariable<String> gv = new FileOperation().readGraphByEdgePair(filePath);

		GreedyVote algo = new GreedyVote(gv);
		algo.compute();
		Assert.assertTrue(Util.isValidSolution(gv));

		String[] sol = Util.getVertexSolution(gv);

		TestUtil.verifySort(expect, sol);

	}

}
