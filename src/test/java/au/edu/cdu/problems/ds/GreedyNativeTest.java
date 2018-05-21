package au.edu.cdu.problems.ds;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import au.edu.cdu.TestUtil;
import au.edu.cdu.common.control.Result;
import au.edu.cdu.common.io.DBOperation;
import au.edu.cdu.common.io.DBParameter;
import au.edu.cdu.common.io.FileOperation;
import au.edu.cdu.common.io.IOUtil;
import au.edu.cdu.common.util.*;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


import edu.uci.ics.jung.graph.Graph;

public class GreedyNativeTest {


    private static final String CLASS_NAME = GreedyNativeTest.class.getSimpleName();
    private Logger log = LogUtil.getLogger(CLASS_NAME);

    //@Ignore
    @Test
    public void test0() {
        List<String[]> am = TestUtil.simpleAM0();

        Graph<Integer, String> g = AlgoUtil.prepareGenericGraph(am);
        Graph<Integer, String> gCopy = AlgoUtil.copyGraph(g);

        GreedyNative ag = new GreedyNative(g);
        ag.run();

        List<Integer> ds = ag.getDominatingSet();
        Assert.assertTrue(AlgoUtil.isDS(gCopy, ds));

        Result r = ag.getResult();
        log.debug(r.getString());
    }

    //@Ignore
    @Test
    public void testDIMACSMIS_verify() {
        String datasetName = "DIMACS-MIS";


        /*
         * get the info of the instances of a certain dataset such as id, code,
         * path
         */
        List<Map<String, String>> lst = DBOperation.getInstanceInfo(datasetName);

        // loop to run the algorithm with the instance info and write to db
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";

        DBParameter dbpOut;
        String batchNum = Util.getBatchNum();

        for (Map<String, String> map : lst) {
            String dataSetPath = map.get(ConstantValue.DB_COL_DATASET_PATH_NAME);
            String pathName = map.get(ConstantValue.DB_COL_INS_PATH_NAME);
            String id = map.get(ConstantValue.DB_COL_INS_ID);
            String instanceCode = map.get(ConstantValue.DB_COL_INS_CODE);
            String algTableName = DBOperation.getAlgorithmTableName(CLASS_NAME);

            String inputFile = resourcePath + dataSetPath + pathName;
            try {
                // read file


                FileOperation fo = IOUtil.getProblemInfoByEdgePair(inputFile);
                byte[][] am = fo.getAdjacencyMatrix();

                Graph<Integer, String> g = AlgoUtil.prepareGenericGraphByte(am);
                Graph<Integer, String> gCopy = AlgoUtil.copyGraph(g);

                GreedyNative ag = new GreedyNative(g);


                long start = System.nanoTime();
                // run algorithm
                ag.run();
                long end = System.nanoTime();

                List<Integer> ds = ag.getDominatingSet();
                Assert.assertTrue(AlgoUtil.isDS(gCopy, ds));


                // write to db
                DBOperation.createTable(algTableName);
                dbpOut = getDBParamOutput(algTableName, batchNum, id, ds.size(), start, end);
                DBOperation.executeInsert(dbpOut);
                // write to console
                Result r = ag.getResult();
                log.debug(r.getString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * generate the database parameters for being written to db
     * used for greedy
     *
     * @param algTableName, algorithm result table name
     * @param batchNum,     batch processing number
     * @param id,           id
     * @param size,         solution size
     * @param start,        start time
     * @param end,          end time
     * @return a database parameter object
     */
    private static DBParameter getDBParamOutput(String algTableName, String batchNum, String id, int size,
                                                long start, long end) {
        DBParameter dbpOut;
        dbpOut = new DBParameter();
        dbpOut.setTableName(algTableName);
        String[] colPairNamesOut = {ConstantValue.DB_COL_INS_ID, ConstantValue.DB_COL_BATCH_NUM,
                ConstantValue.DB_COL_RESULT_SIZE, ConstantValue.DB_COL_RUNNING_TIME};
        String[] colPairValuesOut = {id, batchNum, Integer.toString(size),
                Long.toString((end - start))};
        dbpOut.setColPairNames(colPairNamesOut);
        dbpOut.setColPairValues(colPairValuesOut);
        return dbpOut;
    }

}
