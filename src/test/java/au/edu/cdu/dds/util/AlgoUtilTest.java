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
		String filePath = TestUtil.getBasePath() + "/src/test/resources/sample1.txt";

		GlobalVariable g = new FileOperation().readGraphByEdgePair(filePath);
		GlobalVariable gNew = AlgoUtil.copyGloablVariable(g);

		TestUtil.verifyUnsort(g.getIdxDegree(), gNew.getIdxDegree());
		TestUtil.verifyUnsort(g.getIdxLst(), gNew.getIdxLst());
		TestUtil.verifyUnsort(g.getIdxSol(), gNew.getIdxSol());
		TestUtil.verifyUnsort(g.getIdxVote(), gNew.getIdxVote());
		TestUtil.verifyUnsort(g.getIdxWeight(), gNew.getIdxWeight());
		TestUtil.verifyUnsort(g.getLabLst(), gNew.getLabLst());
		TestUtil.verifyUnsort(g.getIdxAdded(), gNew.getIdxAdded());
		TestUtil.verifyUnsort(g.getIdxDomed(), gNew.getIdxDomed());

		Assert.assertEquals(g.getActVerCnt(), gNew.getActVerCnt());
		Assert.assertEquals(g.getIdxSolSize(), gNew.getIdxSolSize());
		Assert.assertEquals(g.getVerCnt(), gNew.getVerCnt());

		TestUtil.verifyUnsort(g.getIdxAL(), gNew.getIdxAL());
		TestUtil.verifyUnsort(g.getIdxIM(), gNew.getIdxIM());
	}
}
