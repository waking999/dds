package au.edu.cdu.problems.dds;

import au.edu.cdu.TestUtil;
import au.edu.cdu.common.io.DBOperation;
import au.edu.cdu.common.io.FileOperation;
import au.edu.cdu.common.util.*;
import au.edu.cdu.problems.IAlgorithm;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static au.edu.cdu.common.util.LogUtil.getLogger;

public class GreedyVoteL2HDDSComp3Test {

    private static final String CLASS_NAME = GreedyVoteL2HDDSComp3Test.class.getSimpleName();
    private Logger log = getLogger(CLASS_NAME);

    public GreedyVoteL2HDDSComp3Test() {
    }

    @Ignore
    public void testIgnore() {

    }

    @Ignore
    @Test
    public void test0() throws IOException {
        String filePath = TestUtil.getBasePath() + "/src/test/resources/sample2.txt";
        int[] expect = new int[]{1, 5};

        GlobalVariable g = FileOperation.readGraphByEdgePair(filePath);

        IAlgorithm algo = new GreedyVoteL2HDDSComp();
        algo.setGlobalVariable(g);
        int k = 3;
        int r = 2;
        int momentRegretThreshold=2;
        algo.setKRM(k, r,momentRegretThreshold);
        algo.compute();
        Assert.assertTrue(AlgoUtil.isValidSolution(g));

        int[] sol = AlgoUtil.getLabSolution(g);

        TestUtil.verifySort(expect, sol);

    }

    @Ignore
    @Test
    public void testKONECTComponents() throws IOException {

        List<Map<String, String>> lst = DBOperation.getInstanceInfo(ConstantValue.DATASET_KONECT);

        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";

        for (Map<String, String> map : lst) {
            String dataSetPath = map.get(ConstantValue.DB_COL_DATASET_PATH_NAME);
            String pathName = map.get(ConstantValue.DB_COL_INS_PATH_NAME);
            String code = map.get(ConstantValue.DB_COL_INS_CODE);

            String inputFile = resourcePath + dataSetPath + pathName;
            GlobalVariable g = FileOperation.readGraphByEdgePair(inputFile);
            ConnectComponents cc = new ConnectComponents();
            cc.setG(g);
            List<Set<Integer>> list = cc.getConnectComponents();
            int listSize = list.size();
            System.out.println("The number of components of " + code + ":" + listSize);
        }
    }

    @Ignore
    @Test
    public void testKONECT_LoopIns() {
        int k = 10;
        int r = 9;
        int momentRegretThreshold=2;

        IAlgorithm algo = new GreedyVoteL2HDDSComp();

        TestUtil.basicFuncLoopIns(CLASS_NAME, ConstantValue.DATASET_KONECT, algo, k, r,momentRegretThreshold, log);
    }

    //@Ignore
    @Test
    public void testDIMACSMIS_LoopIns() {
        int k = 10;
        int r = k-1;
        int momentRegretThreshold=2;
        IAlgorithm algo = new GreedyVoteL2HDDSComp();

        TestUtil.basicFuncLoopIns(CLASS_NAME, ConstantValue.DATASET_DIMACS_MIS, algo, k, r,momentRegretThreshold, log);
    }

    @Ignore
    @Test
    public void testDIMACS_LoopKR()   {

        int kLower = 3;
        int kUpper = 20;
        IAlgorithm algo = new GreedyVoteL2HDDSComp();
        TestUtil.basicFuncLoopInsLoopKR(CLASS_NAME, ConstantValue.DATASET_DIMACS, algo, kLower, kUpper, log);
    }

    //@Ignore
    @Test
    public void testDIMACSMIS_LoopKR()   {

        int kLower = 3;
        int kUpper = 20;
        IAlgorithm algo = new GreedyVoteL2HDDSComp();
        TestUtil.basicFuncLoopInsLoopKR(CLASS_NAME, ConstantValue.DATASET_DIMACS_MIS, algo, kLower, kUpper, log);
    }

    @Ignore
    @Test
    public void testBHOSlIB_LoopKR()  {

        int kLower = 21;
        int kUpper = 40;
        IAlgorithm algo = new GreedyVoteL2HDDSComp();
        TestUtil.basicFuncLoopInsLoopKR(CLASS_NAME, ConstantValue.DATASET_BHOSLIB, algo, kLower, kUpper, log);
    }

    @Ignore
    @Test
    public void testNetrepo_LoopKR()  {

        int kLower = 3;
        int kUpper = 20;
        IAlgorithm algo = new GreedyVoteL2HDDSComp();
        TestUtil.basicFuncLoopInsLoopKR(CLASS_NAME, ConstantValue.DATASET_NTEREPO, algo, kLower, kUpper, log);
    }


    @Ignore
    @Test
    public void testDIMACS_LoopKR_C4000_5() throws IOException {

        int kLower = 51;
        int kUpper = 60;
        String id = "1_12";
        String instanceCode = "C4000.5";
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
        String dataSetPath = "/DIMACS";
        String pathName = "/C4000.5.clq";
        String inputFile = resourcePath + dataSetPath + pathName;
        String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

        IAlgorithm algo = new GreedyVoteL2HDDS();
        String batchNum = Util.getBatchNum();

        TestUtil.basicFuncLoopKR( algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,
                log);
    }

    @Ignore
    @Test
    public void testKONECT_LoopKR_p_hat700_1() throws IOException {

        int kLower = 71;
        int kUpper = 90;
        String id = "1_34";
        String instanceCode = "p_hat700-1";
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
        String dataSetPath = "/DIMACS";
        String pathName = "/p_hat700-1.clq";
        String inputFile = resourcePath + dataSetPath + pathName;
        String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

        IAlgorithm algo = new GreedyVoteL2HDDS();
        String batchNum = Util.getBatchNum();

        TestUtil.basicFuncLoopKR( algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,
                log);
    }


    @Ignore
    @Test
    public void testDIMACS_MIS_LoopKR()  {

        int kLower = 3;
        int kUpper = 20;
        IAlgorithm algo = new GreedyVoteL2HDDSComp();
        TestUtil.basicFuncLoopInsLoopKR(CLASS_NAME, ConstantValue.DATASET_DIMACS_MIS, algo, kLower, kUpper, log);
    }


}
