package au.edu.cdu.problems.dds;


import au.edu.cdu.TestUtil;
import au.edu.cdu.common.io.DBOperation;
import au.edu.cdu.common.io.FileOperation;
import au.edu.cdu.common.util.AlgoUtil;
import au.edu.cdu.common.util.ConstantValue;
import au.edu.cdu.common.util.GlobalVariable;
import au.edu.cdu.common.util.LogUtil;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class GreedyVoteH2LTest {
    private static final String CLASS_NAME = GreedyVoteH2LTest.class.getSimpleName();
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

        IAlgorithm algo = new GreedyVoteH2L();
        algo.setGlobalVariable(gv);
        algo.compute();
        Assert.assertTrue(AlgoUtil.isValidSolution(gv));

        int[] sol = AlgoUtil.getLabSolution(gv);

        TestUtil.verifySort(expect, sol);
    }

    //@Ignore
    @Test
    public void testKONECT_verify()  {
        IAlgorithm algo = new GreedyVoteH2L();
        TestUtil.basicFunc(CLASS_NAME, ConstantValue.DATASET_KONECT, algo, log);
    }

    //@Ignore
    @Test
    public void testCreateReportView() {

        String batchNum = "20171103-0059";
        DBOperation.createReportView(ConstantValue.DATASET_KONECT, CLASS_NAME, batchNum);
    }

    @Ignore
    @Test
    public void testBHOSLIB_verify()   {
        IAlgorithm algo = new GreedyVoteH2L();
        TestUtil.basicFunc(CLASS_NAME, ConstantValue.DATASET_BHOSLIB, algo, log);
    }

}
