package au.edu.cdu.dds.util;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import au.edu.cdu.dds.TestUtil;
import au.edu.cdu.dds.io.FileOperation;
import junit.framework.Assert;

public class AlgoUtilTest {
	@Ignore
	public void testIgnore() {

	}
	
	@Test
	public void testCopyGloablVariable() throws IOException {
		String filePath = TestUtil.getCurrentPath() + "/src/test/resources/sample1.txt";

		GlobalVariable  gv = new FileOperation().readGraphByEdgePair(filePath);
		GlobalVariable gvStar=AlgoUtil.copyGloablVariable(gv);
		
		TestUtil.verifyUnsort(gv.getIdxDegree(), gvStar.getIdxDegree());
		TestUtil.verifyUnsort(gv.getIdxLst(), gvStar.getIdxLst());
		TestUtil.verifyUnsort(gv.getIdxSol(), gvStar.getIdxSol());
		TestUtil.verifyUnsort(gv.getIdxUtil(), gvStar.getIdxUtil());
		TestUtil.verifyUnsort(gv.getIdxVote(), gvStar.getIdxVote());
		TestUtil.verifyUnsort(gv.getIdxWeight(), gvStar.getIdxWeight());
		TestUtil.verifyUnsort(gv.getVerLst(), gvStar.getVerLst());
		TestUtil.verifyUnsort(gv.getIdxAdded(), gvStar.getIdxAdded());
		TestUtil.verifyUnsort(gv.getIdxDomed(), gvStar.getIdxDomed());
		
		Assert.assertEquals(gv.getActVerCnt(), gvStar.getActVerCnt());
		Assert.assertEquals(gv.getIdxSolSize(), gvStar.getIdxSolSize());
		Assert.assertEquals(gv.getVerCnt(), gvStar.getVerCnt());
		
		TestUtil.verifyUnsort(gv.getIdxAL(), gvStar.getIdxAL());
		TestUtil.verifyUnsort(gv.getIdxIM(), gvStar.getIdxIM());
	}
}
