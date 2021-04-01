package au.edu.cdu.problems.dds;

import au.edu.cdu.TestUtil;
import au.edu.cdu.common.io.DBOperation;
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

public class GreedyVoteL2HTest {

    private static final String CLASS_NAME = GreedyVoteL2HTest.class.getSimpleName();
    private Logger log = LogUtil.getLogger(CLASS_NAME);

    @Ignore
    public void testIgnore() {

    }

    @Ignore
    @Test
    public void test0() throws IOException {
        String filePath = TestUtil.getBasePath() + "/src/test/resources/sample1.txt";
        int[] expect = new int[]{1, 5};

        GlobalVariable gv = FileOperation.readGraphByEdgePair(filePath);

        IAlgorithm algo = new GreedyVoteL2H();
        algo.setGlobalVariable(gv);
        algo.compute();
        Assert.assertTrue(AlgoUtil.isValidSolution(gv));

        int[] sol = AlgoUtil.getLabSolution(gv);

        TestUtil.verifySort(expect, sol);

    }

    @Test
    public void testKONECT_verify() {

        IAlgorithm algo = new GreedyVoteL2H();
        TestUtil.basicFunc(CLASS_NAME, ConstantValue.DATASET_KONECT, algo, log);
    }

    @Ignore
    @Test
    public void testCreateReportView() {

        String batchNum = "20171103-0957";
        DBOperation.createReportView(ConstantValue.DATASET_KONECT, CLASS_NAME, batchNum);
    }

    @Ignore
    @Test
    public void testBHOSLIB_verify() {

        IAlgorithm algo = new GreedyVoteL2H();
        TestUtil.basicFunc(CLASS_NAME, ConstantValue.DATASET_BHOSLIB, algo, log);
    }

    @Test
    public void testGNUTELLA_verify() {

        IAlgorithm algo = new GreedyVoteL2H();
        TestUtil.basicFunc(CLASS_NAME, ConstantValue.DATASET_GNUTELLA, algo, log);
    }
}
