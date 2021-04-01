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

public class GreedyVoteL2HDDS3KRMTest {

    private static final String CLASS_NAME = GreedyVoteL2HDDS3KRMTest.class.getSimpleName();
    private Logger log = LogUtil.getLogger(CLASS_NAME);

    @Ignore
    public void testIgnore() {

    }


//   @Ignore
    @Test
    public void testKONECT_LoopKR_Powergrid() throws IOException {

        int kLower = 3;
        int kUpper = 10;
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

    @Test
    public void testGNUTELLA_LoopKR_p2p04() throws IOException {

        int kLower = 3;
        int kUpper = 10;
        String id = "6_1";
        String instanceCode = "10876_p2p-04";
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";
        String dataSetPath = "/Gnutella";
        String pathName = "/10876_p2p-04.Gnutella";
        String inputFile = resourcePath + dataSetPath + pathName;
        String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

        IAlgorithm algo = new GreedyVoteL2HDDS3();
        String batchNum = Util.getBatchNum();

        TestUtil.basicFuncLoopKR(algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,
                log);
    }
}
