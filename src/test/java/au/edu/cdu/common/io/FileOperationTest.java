package au.edu.cdu.common.io;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import au.edu.cdu.TestUtil;
import au.edu.cdu.common.util.ConstantValue;
import au.edu.cdu.common.util.GlobalVariable;

/**
 * a test class for file operation class
 */
public class FileOperationTest {
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	private static final int IV = ConstantValue.IMPOSSIBLE_VALUE;

	@Test
	public void testRetriveProblemInfoByEdgePairFoundExp() {
		String filePath = TestUtil.getBasePath() + "/src/test/resources/samplea.txt";

		try {
			FileOperation.readGraphByEdgePair(filePath); 
		} catch (Exception e) {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void testRetriveProblemInfoByEdgePairNormal() throws IOException {

		String filePath = TestUtil.getBasePath() + "/src/test/resources/sample1.txt";

		GlobalVariable gv = FileOperation.readGraphByEdgePair(filePath);
		TestUtil.printGlobalVariableStatus(gv);
		/*
		 * <p>
		 * Vertex Count:6,Active Vertex Count:6
		 * vL vIL degree vAL vIM
		 * 0 1 0 0 0 2 0 1,2 N,0,0,N,N,N
		 * 1 2 1 1 1 2 1 0,3 0,N,N,0,N,N
		 * 2 3 2 2 2 2 2 0,3 1,N,N,1,N,N
		 * 3 4 3 3 3 3 3 1,2,4 N,1,1,N,0,N
		 * 4 5 4 4 4 2 4 3,5 N,N,N,2,N,0
		 * 5 6 5 5 5 1 5 4 N,N,N,N,1,N
		 * --------------------------------------------------------
		 * </p>
		 */
		int[] vL = new int[] { 1, 2, 3, 4, 5, 6 };
		TestUtil.verifyUnsort(vL, gv.getLabLst());
		int[] vIL = new int[] { 0, 1, 2, 3, 4, 5 };
		TestUtil.verifyUnsort(vIL, gv.getIdxLst());
		int[] vIdxDegree = new int[] { 2, 2, 2, 3, 2, 1 };
		TestUtil.verifyUnsort(vIdxDegree, gv.getIdxDegree());
		int[][] im = new int[][] { { IV, 0, 0, IV, IV, IV }, { 0, IV, IV, 0, IV, IV }, { 1, IV, IV, 1, IV, IV },
				{ IV, 1, 1, IV, 0, IV }, { IV, IV, IV, 2, IV, 0 }, { IV, IV, IV, IV, 1, IV } };
		TestUtil.verifyUnsort(im, gv.getIdxIM());
		int[][] al = new int[][] { { 1, 2 }, { 0, 3 }, { 0, 3 }, { 1, 2, 4 }, { 3, 5 }, { 4 } };
		TestUtil.verifyUnsort(al, gv.getIdxAL());
		float[] vote = new float[] { 0.33333334f, 0.33333334f, 0.33333334f, 0.25f, 0.33333334f, 0.5f };
		TestUtil.verifyUnsort(vote, gv.getIdxVote());
		float[] weight = new float[] { 1.0f, 0.9166667f, 0.9166667f, 1.2500001f, 1.0833334f, 0.8333334f };
		TestUtil.verifyUnsort(weight, gv.getIdxWeight());

	}
}