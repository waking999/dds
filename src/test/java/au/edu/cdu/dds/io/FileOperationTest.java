package au.edu.cdu.dds.io;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

import org.junit.Test;

import au.edu.cdu.dds.TestUtil;
import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;

/**
 * a test class for file operation class
 */
public class FileOperationTest {
	private static final int IV = ConstantValue.IMPOSSIBLE_VALUE;

	@Test(expected = IOException.class)
	public void testRetriveProblemInfoByEdgePairIOExp() throws IOException {
		String filePath = "";
		new FileOperation().readGraphByEdgePair(filePath);
	}

	@Test(expected = NoSuchFileException.class)
	public void testRetriveProblemInfoByEdgePairFoundExp() throws IOException {
		String filePath = TestUtil.getCurrentPath() + "/src/test/resources/samplea.txt";

		new FileOperation().readGraphByEdgePair(filePath);
	}

	@Test
	public void testRetriveProblemInfoByEdgePairNormal() throws IOException {

		String filePath = TestUtil.getCurrentPath() + "/src/test/resources/sample1.txt";

		GlobalVariable<String> gv = new FileOperation().readGraphByEdgePair(filePath);
		TestUtil.printGlobalVariableStatus(gv);
		/*
		 *  
		 	vCount:6
			vL     vIL    util   vAL                  vIM                 
			0 1    0 0    0 2    0 1,2                N,0,0,N,N,N         
			1 2    1 1    1 2    1 0,3                0,N,N,0,N,N         
			2 3    2 2    2 2    2 0,3                1,N,N,1,N,N         
			3 4    3 3    3 3    3 1,2,4              N,1,1,N,0,N         
			4 5    4 4    4 2    4 3,5                N,N,N,2,N,0         
			5 6    5 5    5 1    5 4                  N,N,N,N,1,N         
			--------------------------------------------------------
		 */
		String[] vL = new String[] { "1", "2", "3", "4", "5", "6" };
		TestUtil.verifyUnsort(vL, gv.getVerLst());
		int[] vIL = new int[] { 0, 1, 2, 3, 4, 5 };
		TestUtil.verifyUnsort(vIL, gv.getIdxLst());
		int[] vIdxUtils = new int[] { 2, 2, 2, 3, 2, 1 };
		TestUtil.verifyUnsort(vIdxUtils, gv.getIdxUtil());
		int[][] im = new int[][] { { IV, 0, 0, IV, IV, IV }, { 0, IV, IV, 0, IV, IV }, { 1, IV, IV, 1, IV, IV },
				{ IV, 1, 1, IV, 0, IV }, { IV, IV, IV, 2, IV, 0 }, { IV, IV, IV, IV, 1, IV } };
		TestUtil.verifyUnsort(im, gv.getIdxIM());
		int[][] al = new int[][] { { 1, 2 }, { 0, 3 }, { 0, 3 }, { 1, 2, 4 }, { 3, 5 }, { 4 } };
	    TestUtil.verifyUnsort(al, gv.getIdxAL());
	    float[] vote=new float[] {0.33333334f, 0.33333334f, 0.33333334f, 0.25f, 0.33333334f, 0.5f};
	    TestUtil.verifyUnsort(vote, gv.getIdxVote());
	    float[] weight=new float[] {1.0f, 0.9166667f, 0.9166667f, 1.2500001f, 1.0833334f, 0.8333334f};
	    TestUtil.verifyUnsort(weight, gv.getIdxWeight()); 

	}
}