package au.edu.cdu.problems.ds2;


import au.edu.cdu.TestUtil;
import au.edu.cdu.common.io.FileOperation;
import au.edu.cdu.common.util.AlgoUtil;
import au.edu.cdu.common.util.ConstantValue;
import au.edu.cdu.common.util.GlobalVariable;
import au.edu.cdu.common.util.LogUtil;
import au.edu.cdu.problems.IAlgorithm;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class GreedyVoteGrTest {
    private static final String CLASS_NAME = GreedyVoteGrTest.class.getSimpleName();
    private Logger log = LogUtil.getLogger(CLASS_NAME);

    @Ignore
    public void testIgnore() {
    }

    //@Ignore
    @Test
    public void test0() throws IOException {
        String filePath = TestUtil.getBasePath() + "/src/test/resources/sample1.txt";
        int[] expect = new int[]{4, 5, 2};

        GlobalVariable gv = FileOperation.readGraphByEdgePair(filePath);

        IAlgorithm algo = new GreedyVoteGr();
        algo.setGlobalVariable(gv);
        algo.compute();
        Assert.assertTrue(AlgoUtil.isValidSolution(gv));

        int[] sol = AlgoUtil.getLabSolution(gv);

        TestUtil.verifySort(expect, sol);
    }

    //@Ignore
    @Test
    public void testDIMACSMIS_verify()  {
        IAlgorithm algo = new GreedyVoteGr();
        TestUtil.basicFunc(CLASS_NAME, ConstantValue.DATASET_DIMACS_MIS, algo, log);
    }


    @Test
    public void testKONECT_verify()  {
        IAlgorithm algo = new GreedyVoteGr();
        TestUtil.basicFunc(CLASS_NAME, ConstantValue.DATASET_KONECT, algo, log);
    }
}
