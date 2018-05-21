package au.edu.cdu.problems.dds;

import au.edu.cdu.TestUtil;
import au.edu.cdu.common.io.DBOperation;
import au.edu.cdu.common.io.FileOperation;
import au.edu.cdu.common.util.*;
import au.edu.cdu.problems.IAlgorithm;
import au.edu.cdu.problems.ds2.GreedyNaive;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class GreedyVoteL2HDDS3Test {

    private static final String CLASS_NAME = GreedyVoteL2HDDS3Test.class.getSimpleName();
    private Logger log = LogUtil.getLogger(CLASS_NAME);

    @Ignore
    public void testIgnore() {

    }

    //@Ignore
    @Test
    public void test0() throws IOException {
        String filePath = TestUtil.getBasePath() + "/src/test/resources/sample1.txt";
        int[] expect = new int[]{1, 5};

        GlobalVariable gv = FileOperation.readGraphByEdgePair(filePath);

        IAlgorithm algo = new GreedyVoteL2HDDS3();
        algo.setGlobalVariable(gv);
        int k = 3;
        int r = 2;
        int momentRegretThreshold=2;
        algo.setKRM(k,r,momentRegretThreshold);
        algo.compute();
        Assert.assertTrue(AlgoUtil.isValidSolution(gv));

        int[] sol = AlgoUtil.getLabSolution(gv);

        TestUtil.verifySort(expect, sol);

    }

    @Ignore
    @Test
    public void testKONECT_LoopIns() {
        int k = 10;
        int r = 7;
        int momentRegretThreshold=2;
        IAlgorithm algo = new GreedyVoteL2HDDS3();

        TestUtil.basicFuncLoopIns(CLASS_NAME, ConstantValue.DATASET_KONECT, algo, k, r, momentRegretThreshold, log);
    }


    @Ignore
    @Test
    public void testKONECT_Dolphins() throws IOException {

        int k = 10;
        int r = 7;
        int momentRegretThreshold=2;
        String id = "3_03";
        String instanceCode = "Dolphins";
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
        String dataSetPath = "/KONECT";
        String pathName = "/000062_dolphins.konect";
        String inputFile = resourcePath + dataSetPath + pathName;
        String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

        IAlgorithm algo = new GreedyVoteL2HDDS3();
        String batchNum = Util.getBatchNum();

        TestUtil.basicFunc(algo, batchNum, id, instanceCode, algTableName, inputFile, k, r,momentRegretThreshold, log);
    }

    @Ignore
    @Test
    public void testKONECT_LoopKR_Dolphins() throws IOException {

        int kLower = 3;
        int kUpper = 50;
        String id = "3_03";
        String instanceCode = "Dolphins";
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
        String dataSetPath = "/KONECT";
        String pathName = "/000062_dolphins.konect";
        String inputFile = resourcePath + dataSetPath + pathName;
        String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

        IAlgorithm algo = new GreedyVoteL2HDDS3();
        String batchNum = Util.getBatchNum();

        TestUtil.basicFuncLoopKR(algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,
                log);
    }

    @Ignore
    @Test
    public void testKONECT_Rovira() throws IOException {

        int k = 20;
        int r = 7;
        int momentRegretThreshold=2;
        String id = "3_07";
        String instanceCode = "Rovira";
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
        String dataSetPath = "/KONECT";
        String pathName = "/001133_rovira.konect";
        String inputFile = resourcePath + dataSetPath + pathName;
        String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

        IAlgorithm algo = new GreedyVoteL2HDDS3();
        String batchNum = Util.getBatchNum();

        TestUtil.basicFunc(algo, batchNum, id, instanceCode, algTableName, inputFile, k, r,momentRegretThreshold, log);
    }

    @Ignore
    @Test
    public void testKONECT_LoopKR_Rovira() throws IOException {

        int kLower = 3;
        int kUpper = 50;
        String id = "3_07";
        String instanceCode = "Rovira";
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
        String dataSetPath = "/KONECT";
        String pathName = "/001133_rovira.konect";
        String inputFile = resourcePath + dataSetPath + pathName;
        String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

        IAlgorithm algo = new GreedyVoteL2HDDS3();
        String batchNum = Util.getBatchNum();

        TestUtil.basicFuncLoopKR(algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,
                log);
    }

    @Ignore
    @Test
    public void testKONECT_LoopKR_HamsterFul() throws IOException {

        int kLower = 26;
        int kUpper = 50;
        String id = "3_10";
        String instanceCode = "HamsterFul";
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
        String dataSetPath = "/KONECT";
        String pathName = "/002426_hamster_ful.konect";
        String inputFile = resourcePath + dataSetPath + pathName;
        String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

        IAlgorithm algo = new GreedyVoteL2HDDS3();
        String batchNum = Util.getBatchNum();

        TestUtil.basicFuncLoopKR(algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,
                log);
    }

    @Ignore
    @Test
    public void testKONECT_LoopKR_DavidCopperfield() throws IOException {

        int kLower = 3;
        int kUpper = 20;
        String id = "3_04";
        String instanceCode = "DavidCopperfield";
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
        String dataSetPath = "/KONECT";
        String pathName = "/000112_David_Copperfield.konect";
        String inputFile = resourcePath + dataSetPath + pathName;
        String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

        IAlgorithm algo = new GreedyVoteL2HDDS3();
        String batchNum = Util.getBatchNum();

        TestUtil.basicFuncLoopKR(algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,
                log);
    }

    @Ignore
    @Test
    public void testKONECT_LoopKR_Powergrid() throws IOException {

        int kLower = 3;
        int kUpper = 50;
        String id = "3_13";
        String instanceCode = "Powergrid";
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
        String dataSetPath = "/KONECT";
        String pathName = "/004941_powergrid.konect";
        String inputFile = resourcePath + dataSetPath + pathName;
        String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

        IAlgorithm algo = new GreedyVoteL2HDDS3();
        String batchNum = Util.getBatchNum();

        TestUtil.basicFuncLoopKR(algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,
                log);
    }

    @Ignore
    @Test
    public void testKONECT_LoopKR_Pretty() throws IOException {

        int kLower = 3;
        int kUpper = 50;
        String id = "3_16";
        String instanceCode = "PrettyGoodPrivacy";
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
        String dataSetPath = "/KONECT";
        String pathName = "/010680_Pretty_Good_Privacy.konect";
        String inputFile = resourcePath + dataSetPath + pathName;
        String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

        IAlgorithm algo = new GreedyVoteL2HDDS3();
        String batchNum = Util.getBatchNum();

        TestUtil.basicFuncLoopKR(algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,
                log);
    }

    @Ignore
    @Test
    public void testCreateReportView() {

        String batchNum = "20171103-0056";
        DBOperation.createReportView(ConstantValue.DATASET_KONECT, CLASS_NAME, batchNum);
    }

    @Ignore
    @Test
    public void testBHOSLIB_LoopIns() {
        int k = 10;
        int r = 7;
        int momentRegretThreshold=2;
        IAlgorithm algo = new GreedyVoteL2HDDS3();

        TestUtil.basicFuncLoopIns(CLASS_NAME, ConstantValue.DATASET_BHOSLIB, algo, k, r,momentRegretThreshold, log);
    }

    @Test
    public void testDIMACSMIS_verify()  {
        int k = 10;
        int r = 9;
        int momentRegretThreshold=2;
        IAlgorithm algo = new GreedyVoteL2HDDS3();

        TestUtil.basicFuncLoopIns(CLASS_NAME, ConstantValue.DATASET_DIMACS_MIS, algo, k, r,momentRegretThreshold, log);

    }
}
