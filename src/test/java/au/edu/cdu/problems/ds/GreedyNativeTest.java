package au.edu.cdu.problems.ds;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import au.edu.cdu.TestUtil;
import au.edu.cdu.common.control.Result;
import au.edu.cdu.common.util.AlgoUtil;
import au.edu.cdu.common.util.LogUtil;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


import edu.uci.ics.jung.graph.Graph;

public class GreedyNativeTest {

	//private Logger log = LogUtil.getLogger(GreedyNativeTest.class);
	private static final String CLASS_NAME = GreedyNativeTest.class.getSimpleName();

	//@Ignore
	@Test
	public void test0() throws InterruptedException, IOException, FileNotFoundException {
		List<String[]> am = TestUtil.simpleAM0();

		Graph<Integer, String> g = AlgoUtil.prepareGenericGraph(am);
		Graph<Integer, String> gCopy = AlgoUtil.copyGraph(g);

		GreedyNative ag = new GreedyNative(g);
		ag.run();

		List<Integer> ds = ag.getDominatingSet();
		Assert.assertTrue(AlgoUtil.isDS(gCopy, ds));

		Result r = ag.getResult();
		//log.debug(r.getString());
	}



}
