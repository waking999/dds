package au.edu.cdu;

import au.edu.cdu.problems.dds.IAlgorithm;
import au.edu.cdu.common.io.DBOperation;
import au.edu.cdu.common.io.DBParameter;
import au.edu.cdu.common.io.FileOperation;
import au.edu.cdu.common.util.AlgoUtil;
import au.edu.cdu.common.util.ConstantValue;
import au.edu.cdu.common.util.GlobalVariable;
import au.edu.cdu.common.util.Util;
import org.apache.log4j.Logger;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * an util class for test purpose
 */
public class TestUtil {

    //public static final String FUNCTION_SEP = "***********************************************************";

    /**
     * get the base path of the project
     *
     * @return the base path of the project
     */
    public static String getBasePath() {
        return Paths.get(".").toAbsolutePath().normalize().toString();
    }

    /**
     * the basic structure to run algorithms and write to db
     * loop to get dataset instances to run the algorithm on them
     * which is used for greedy algorithms
     *
     * @param className,   class name
     * @param dataSetName, dataset name
     * @param algo,        algorithm object
     * @param log,         log
     */
    public static void basicFunc(String className, String dataSetName, IAlgorithm algo, Logger log) {
        /*
         * get the info of the instances of a certain dataset such as id, code,
         * path
         */
        List<Map<String, String>> lst = DBOperation.getInstanceInfo(dataSetName);

        // loop to run the algorithm with the instance info and write to db
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";

        DBParameter dbpOut;
        String batchNum = Util.getBatchNum();

        for (Map<String, String> map : lst) {
            String dataSetPath = map.get(ConstantValue.DB_COL_DATASET_PATH_NAME);
            String pathName = map.get(ConstantValue.DB_COL_INS_PATH_NAME);
            String id = map.get(ConstantValue.DB_COL_INS_ID);
            String instanceCode = map.get(ConstantValue.DB_COL_INS_CODE);
            String algTableName = DBOperation.getAlgorithmTableName(className);

            String inputFile = resourcePath + dataSetPath + pathName;
            try {
                // read file
                GlobalVariable g = FileOperation.readGraphByEdgePair(inputFile);
                algo.setGlobalVariable(g);

                long start = System.nanoTime();
                // run algorithm
                algo.compute();
                long end = System.nanoTime();

                // ensure the solution is valid
                Assert.assertTrue(AlgoUtil.isValidSolution(g));

                // write to db
                DBOperation.createTable(algTableName);
                dbpOut = getDBParamOutput(algTableName, batchNum, id, g, start, end);
                DBOperation.executeInsert(dbpOut);
                // write to console
                String sb = instanceCode + ":" + g.getIdxSolSize() + ":" +
                        String.format("%.3f", ((end - start) / 1000000000.0)) + " s.";
                log.debug(sb);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * the basic structure to run algorithms and write to db
     * loop to get dataset instances to run the algorithm on them
     * which is used for greedy dds algorithms
     *
     * @param className,   class name
     * @param dataSetName, dataset name
     * @param algo,        algorithm object
     * @param log,         log
     */
    public static void basicFuncLoopInsLoopKR(String className, String dataSetName, IAlgorithm algo, int kLower,
                                              int kUpper, Logger log) {
        /*
         * get the info of the instances of a certain dataset such as id, code,
         * path
         */
        List<Map<String, String>> lst = DBOperation.getInstanceInfo(dataSetName);

        // loop to run the algorithm with the instance info and write to db
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";

        String batchNum = Util.getBatchNum();

        for (Map<String, String> map : lst) {
            String dataSetPath = map.get(ConstantValue.DB_COL_DATASET_PATH_NAME);
            String pathName = map.get(ConstantValue.DB_COL_INS_PATH_NAME);
            String id = map.get(ConstantValue.DB_COL_INS_ID);
            String instanceCode = map.get(ConstantValue.DB_COL_INS_CODE);
            String algTableName = DBOperation.getAlgorithmTableName(className);

            String inputFile = resourcePath + dataSetPath + pathName;
            try {
                basicFuncLoopKR(algo, kLower, kUpper, batchNum, id, instanceCode, algTableName, inputFile,
                        log);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * the basic structure to run algorithms and write to db
     * loop on k, r to get result on one instance
     * which is used for greedy dds algorithms
     *
     * @param algo,         algorithm object
     * @param kLower,       the lower boundary of k
     * @param kUpper,       the upper boundary of k
     * @param batchNum,     batch processing number
     * @param id,           id
     * @param instanceCode, instance code
     * @param algTableName, algorithm result table name
     * @param inputFile,    input file path and name
     * @param log,          log
     * @throws IOException,           IOException
     * @throws NumberFormatException, NumberFormatException
     */
    public static void basicFuncLoopKR(IAlgorithm algo, int kLower, int kUpper, String batchNum,
                                       String id, String instanceCode, String algTableName, String inputFile, Logger log) throws IOException, NumberFormatException {
        for (int tmpK = kLower; tmpK <= kUpper; tmpK++) {
            for (int tmpR = 1; tmpR <= tmpK - 1; tmpR++) {
                for(int tmpM=2; tmpM<= tmpK-1; tmpM++) {
                    basicFunc(algo, batchNum, id, instanceCode, algTableName, inputFile, tmpK, tmpR, tmpM, log);
                }
            }
        }
    }

    /**
     * the basic structure to run algorithms and write to db
     * loop on instance with specified k,r
     *
     * @param className,   class name
     * @param dataSetName, dataset name
     * @param algo,        algorithm object
     * @param log,         log
     * @param k,           algorithm parameter k
     * @param r,           algorithm parameter r
     */
    public static void basicFuncLoopIns(String className, String dataSetName, IAlgorithm algo, int k, int r,int momentRegretThreshold,
                                        Logger log) {
        /*
         * get the info of the instances of a certain dataset such as id, code,
         * path
         */
        List<Map<String, String>> lst = DBOperation.getInstanceInfo(dataSetName);

        // loop to run the algorithm with the instance info and write to db
        String resourcePath = TestUtil.getBasePath() + "/src/test/resources";

        String batchNum = Util.getBatchNum();

        for (Map<String, String> map : lst) {
            String dataSetPath = map.get(ConstantValue.DB_COL_DATASET_PATH_NAME);
            String pathName = map.get(ConstantValue.DB_COL_INS_PATH_NAME);
            String id = map.get(ConstantValue.DB_COL_INS_ID);
            String instanceCode = map.get(ConstantValue.DB_COL_INS_CODE);
            String algTableName = DBOperation.getAlgorithmTableName(className);

            String inputFile = resourcePath + dataSetPath + pathName;
            try {
                basicFunc(algo, batchNum, id, instanceCode, algTableName, inputFile, k, r,momentRegretThreshold, log);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * the basic structure to run algorithms and write to db
     * run on only one instance
     * which is used for greedy dds algorithms
     *
     * @param algo,         algorithm object
     * @param log,          log object
     * @param batchNum,     batch processing number
     * @param id,           id
     * @param instanceCode, instance code
     * @param algTableName, algorithm result table name
     * @param inputFile,    database file path and name
     * @param k,            algorithm parameter k
     * @param r,            algorithm parameter r
     * @throws IOException,           IOException
     * @throws NumberFormatException, NumberFormatException
     */
    public static void basicFunc(IAlgorithm algo, String batchNum, String id, String instanceCode,
                                 String algTableName, String inputFile, int k, int r, int momentRegretThreshold, Logger log) throws IOException, NumberFormatException {
        DBParameter dbpOut;
        // read file
        GlobalVariable g = FileOperation.readGraphByEdgePair(inputFile);
        algo.setGlobalVariable(g);
        algo.setKRM(k,r,momentRegretThreshold);
        long start = System.nanoTime();
        // run algorithm
        algo.compute();
        long end = System.nanoTime();

        // ensure the solution is valid
        Assert.assertTrue(AlgoUtil.isValidSolution(g));

        // write to db
        DBOperation.createTable(algTableName);
        dbpOut = getDBParamOutput(g, algTableName, batchNum, id, start, end, k, r,momentRegretThreshold);
        DBOperation.executeInsert(dbpOut);

        // write to console
        String sb = instanceCode + ":" + k + "," + r + ":" + g.getIdxSolSize() +
                ":" + String.format("%.3f", ((end - start) / 1000000000.0)) + " s.";
        log.debug(sb);

        // write to file
        StringBuilder outputStrBuff = new StringBuilder();
        int[] sol = AlgoUtil.getLabSolution(g);
        int solSize = sol.length;
        outputStrBuff.append(solSize);
        outputStrBuff.append("\r\n");
        for (int aSol : sol) {
            outputStrBuff.append(aSol).append(",");
        }

        File f = new File(inputFile);
        String fileName = f.getName();

        String outputFilePath = TestUtil.getBasePath() + "/result/output/";


        String outputFileName = outputFilePath + fileName + ".out";

        FileOperation.saveFile(outputFileName, outputStrBuff.substring(0, outputStrBuff.length() - 1));

    }

    /**
     * generate the database parameters for being written to db
     * used for greedy
     *
     * @param algTableName, algorithm result table name
     * @param batchNum,     batch processing number
     * @param id,           id
     * @param gv,           variables representing a graph
     * @param start,        start time
     * @param end,          end time
     * @return a database parameter object
     */
    private static DBParameter getDBParamOutput(String algTableName, String batchNum, String id, GlobalVariable gv,
                                                long start, long end) {
        DBParameter dbpOut;
        dbpOut = new DBParameter();
        dbpOut.setTableName(algTableName);
        String[] colPairNamesOut = {ConstantValue.DB_COL_INS_ID, ConstantValue.DB_COL_BATCH_NUM,
                ConstantValue.DB_COL_RESULT_SIZE, ConstantValue.DB_COL_RUNNING_TIME};
        String[] colPairValuesOut = {id, batchNum, Integer.toString(gv.getIdxSolSize()),
                Long.toString((end - start))};
        dbpOut.setColPairNames(colPairNamesOut);
        dbpOut.setColPairValues(colPairValuesOut);
        return dbpOut;
    }

    /**
     * generate the database parameters for being written to db
     * used for greedy dds
     *
     * @param g,            variables representing a graph
     * @param algTableName, algorithm result table name
     * @param batchNum,     batch process number
     * @param id,           id for record in algorithm result table
     * @param start,        start time
     * @param end,          end time
     * @param k,            algorithm parameter k
     * @param r,            algorithm parameter r
     * @return a database parameter object
     */
    private static DBParameter getDBParamOutput(GlobalVariable g, String algTableName, String batchNum, String id,
                                                long start, long end, int k, int r,int momentRegretThreshold) {
        DBParameter dbpOut;
        dbpOut = new DBParameter();
        dbpOut.setTableName(algTableName);
        String[] colPairNamesOut = {ConstantValue.DB_COL_INS_ID, ConstantValue.DB_COL_BATCH_NUM,
                ConstantValue.DB_COL_RESULT_SIZE, ConstantValue.DB_COL_RUNNING_TIME, ConstantValue.DB_COL_K,
                ConstantValue.DB_COL_R,ConstantValue.DB_COL_M, ConstantValue.DB_COL_RESULTS};
        String[] colPairValuesOut = {id, batchNum, Integer.toString(g.getIdxSolSize()), String.valueOf(end - start),
                String.valueOf(k), String.valueOf(r), String.valueOf(momentRegretThreshold), AlgoUtil.getLabSolutionStr(g)};

        dbpOut.setColPairNames(colPairNamesOut);
        dbpOut.setColPairValues(colPairValuesOut);
        return dbpOut;
    }

    /**
     * print status of global variables in a format
     *
     * @param gv, global variables
     */
    public static void printGlobalVariableStatus(GlobalVariable gv) {
        String styleStr = "%-6s %-6s %-6s %-6s %-15s %-6s %-15s %-40s %-40s";

        int[] idxLst = gv.getIdxLst();
        int actVerCnt = gv.getActVerCnt();
        int verCnt = gv.getVerCnt();
        int[] idxDegree = gv.getIdxDegree();
        int[][] idxAL = gv.getIdxAL();
        int[][] idxIM = gv.getIdxIM();
        boolean[] idxDomed = gv.getIdxDomed();
        boolean[] idxAdded = gv.getIdxAdded();
        float[] idxVote = gv.getIdxVote();
        float[] idxWeight = gv.getIdxWeight();

        int[] vL = gv.getLabLst();

        printStatus(styleStr, verCnt, actVerCnt, vL, idxLst, idxDegree, idxDomed,
                idxVote, idxAdded, idxWeight, idxAL, idxIM);

        int[] idxSol = gv.getIdxSol();
        int idxSolSize = gv.getIdxSolSize();
        StringBuilder sb = new StringBuilder();
        sb.append(idxSolSize).append(":");
        for (int i = 0; i < idxSolSize; i++) {
            sb.append(idxSol[i]).append(",");
        }
        System.out.println(sb.toString());

        System.out.println("--------------------------------------------------------");
    }

    /**
     * print status of global variables in a format
     *
     * @param styleStr,  a string for show style
     * @param verCnt,    vertex count
     * @param actVerCnt, active vertex count
     * @param l,         list values
     * @param il,        index list
     * @param deg,       degrees
     * @param dom,       dominated status
     * @param vote,      vote
     * @param added,     added status
     * @param weight,    weight
     * @param al,        adjacent list
     * @param im,        incident matrix
     */
    private static void printStatus(String styleStr, int verCnt, int actVerCnt, int[] l,
                                    int[] il, int[] deg, boolean[] dom, float[] vote,
                                    boolean[] added, float[] weight, int[][] al,
                                    int[][] im) {

        System.out.println("Vertex Count:" + verCnt + ",Active Vertex Count:" + actVerCnt);
        System.out.printf(styleStr, "vL", "vL", "degree", "domed", "vote", "added", "weight", "vAL", "vIM");
        System.out.println();
        for (int i = 0; i < verCnt; i++) {

            System.out.printf(styleStr, i + " " + l[i], i + " " + il[i], i + " " + deg[i],
                    i + " " + (dom[i] ? "T" : "F"), i + " " + String.format("%.4f", vote[i]),
                    i + " " + (added[i] ? "T" : "F"), i + " " + String.format("%.4f", weight[i]),
                    i + " " + arrayToString(al[i]), arrayToString(im[i]));

            System.out.println();
        }
    }

    /**
     * convert an array to string, used for print status
     *
     * @param array, array
     * @return string
     */
    private static String arrayToString(int[] array) {
        if (array == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        for (int anArray : array) {
            if (anArray == ConstantValue.IMPOSSIBLE_VALUE) {
                sb.append("N").append(",");
            } else {
                sb.append(anArray).append(",");
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

//    /**
//     * verify if the output is the same as the expect
//     *
//     * @param expect
//     * @param output
//     */
//    public static void verify(int[][] expect, List<List<Integer>> output) {
//        int expectLen = expect.length;
//        int outputSize = output.size();
//        Assert.assertTrue(expectLen == outputSize);
//        for (int i = 0; i < expectLen; i++) {
//            String s1 = IntStream.of(expect[i]).map(str -> str).boxed().collect(Collectors.toList()).stream()
//                    .map(num -> Integer.toString(num)).collect(Collectors.joining(","));
//
//            String s2 = output.get(i).stream().map(num -> Integer.toString(num)).collect(Collectors.joining(","));
//            Assert.assertTrue(s1.equals(s2));
//        }
//
//    }

//    public static void verifySort(int[][] expect, int[][] output) {
//        int expectLen = expect.length;
//        int outputSize = output.length;
//        assertEquals(expectLen, outputSize);
//        for (int i = 0; i < expectLen; i++) {
//            String s1 = Arrays.stream(expect[i]).map(str -> str).sorted().boxed().collect(Collectors.toList()).stream()
//                    .map(num -> Integer.toString(num)).collect(Collectors.joining(","));
//
//            String s2 = Arrays.stream(output[i]).map(str -> str).sorted().boxed().collect(Collectors.toList()).stream()
//                    .map(num -> Integer.toString(num)).collect(Collectors.joining(","));
//            assertEquals(s1, s2);
//        }
//
//    }

    public static void verifyUnsort(int[][] expect, int[][] output) {
        int expectLen = expect.length;
        int outputSize = output.length;
        assertEquals(expectLen, outputSize);
        for (int i = 0; i < expectLen; i++) {
            String s1 = Arrays.stream(expect[i]).boxed().collect(Collectors.toList()).stream()
                    .map(num -> Integer.toString(num)).collect(Collectors.joining(","));

            String s2 = Arrays.stream(output[i]).boxed().collect(Collectors.toList()).stream()
                    .map(num -> Integer.toString(num)).collect(Collectors.joining(","));
            assertEquals(s1, s2);
        }

    }

//    public static void verify(String[] expect, List<String> output) {
//
//        int expectLen = expect.length;
//        int outputSize = output.size();
//        assertEquals(expectLen, outputSize);
//        String expectStr = Arrays.asList(expect).stream().collect(Collectors.joining(","));
//        String outputStr = output.stream().collect(Collectors.joining(","));
//        assertEquals(expectStr, outputStr);
//    }

//    public static void verifyUnsort(String[] expect, String[] output) {
//
//        int expectLen = expect.length;
//        int outputSize = output.length;
//        assertEquals(expectLen, outputSize);
//        String expectStr = Arrays.asList(expect).stream().collect(Collectors.joining(","));
//        String outputStr = Arrays.asList(output).stream().collect(Collectors.joining(","));
//        assertEquals(expectStr, outputStr);
//    }

//    public static void verifySort(String[] expect, String[] output) {
//
//        int expectLen = expect.length;
//        int outputSize = output.length;
//        assertEquals(expectLen, outputSize);
//
//        String expectStr = Arrays.asList(expect).stream().sorted().collect(Collectors.joining(","));
//        String outputStr = Arrays.asList(output).stream().sorted().collect(Collectors.joining(","));
//        assertEquals(expectStr, outputStr);
//    }

//    public static void verify(int[] expect, List<Integer> output) {
//
//        int expectLen = expect.length;
//        int outputSize = output.size();
//        assertEquals(expectLen, outputSize);
//        String expectStr = Arrays.stream(expect).sorted().boxed().collect(Collectors.toList()).stream()
//                .map(num -> Integer.toString(num)).collect(Collectors.joining(","));
//        String outputStr = output.stream().sorted().map(num -> Integer.toString(num)).collect(Collectors.joining(","));
//        assertEquals(expectStr, outputStr);
//    }

//    public static void verify(int[] expect, Integer[] output) {
//
//        int expectLen = expect.length;
//        int outputSize = output.length;
//        assertEquals(expectLen, outputSize);
//        String expectStr = Arrays.stream(expect).sorted().boxed().collect(Collectors.toList()).stream()
//                .map(num -> Integer.toString(num)).collect(Collectors.joining(","));
//        String outputStr = Arrays.stream(output).sorted().collect(Collectors.toList()).stream()
//                .map(num -> Integer.toString(num)).collect(Collectors.joining(","));
//        assertEquals(expectStr, outputStr);
//    }

    public static void verifySort(int[] expect, int[] output) {
        if (expect == null && output == null) return;

        assert expect != null;
        int expectLen = expect.length;
        int outputSize = output.length;
        assertEquals(expectLen, outputSize);
        String expectStr = Arrays.stream(expect).sorted().boxed().collect(Collectors.toList()).stream()
                .map(num -> Integer.toString(num)).collect(Collectors.joining(","));
        String outputStr = Arrays.stream(output).sorted().boxed().collect(Collectors.toList()).stream()
                .map(num -> Integer.toString(num)).collect(Collectors.joining(","));
        assertEquals(expectStr, outputStr);
    }

    public static void verifyUnsort(int[] expect, int[] output) {

        int expectLen = expect.length;
        int outputSize = output.length;
        assertEquals(expectLen, outputSize);
        assertEquals(expectLen, outputSize);
        String expectStr = Arrays.toString(expect);
        String outputStr = Arrays.toString(output);
        assertEquals(expectStr, outputStr);
    }

    public static void verifyUnsort(boolean[] expect, boolean[] output) {

        int expectLen = expect.length;
        int outputSize = output.length;
        assertEquals(expectLen, outputSize);

        boolean cmp = Util.verifyUnsort(expect, output);

        Assert.assertTrue(cmp);
    }

//	public static void verifySort(float[] expect, float[] output) {
//
//		int expectLen = expect.length;
//		int outputLen = output.length;
//		assertEquals(expectLen, outputLen);
//		Arrays.sort(output);
//		boolean eqFlag = true;
//		for (int i = 0; i < expectLen; i++) {
//			if (!(Math.abs(expect[i] - output[i]) <= ConstantValue.FLOAT_NO_DIFF)) {
//				eqFlag = false;
//			}
//		}
//		Assert.assertTrue(eqFlag);
//
//	}

    public static void verifyUnsort(float[] expect, float[] output) {
        int expectLen = expect.length;
        int outputLen = output.length;
        assertEquals(expectLen, outputLen);
        boolean eqFlag = true;
        for (int i = 0; i < expectLen; i++) {
            if (!(Math.abs(expect[i] - output[i]) <= ConstantValue.FLOAT_NO_DIFF)) {
                eqFlag = false;
            }
        }

        Assert.assertTrue(eqFlag);
    }

//	public static void verify(int expect, int output) {
//		assertEquals(expect, output);
//	}

    public static void verify(boolean expect, boolean output) {
        assertEquals(expect, output);
    }

    public static void verify(String expect, String output) {
        assertEquals(expect, output);
    }

    /**
     * convert an integer array to an integer list
     *
     * @param a, an integer array
     * @return an integer list
     */
    public static Set<Integer> arrayToSet(int[] a) {
        int aLen = a.length;
        if (aLen == 0) {
            return null;
        }

        Set<Integer> set = new HashSet<>();
        for (int anA : a) {
            set.add(anA);
        }

        return set;
    }

    public static List<String[]> simpleAM0() {
        List<String[]> am = new ArrayList<String[]>();
        Util.addElementToList(am,
                new String[] { "0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0" });
        Util.addElementToList(am,
                new String[] { "1", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0" });
        Util.addElementToList(am,
                new String[] { "0", "1", "0", "1", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0" });
        Util.addElementToList(am,
                new String[] { "0", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0" });
        Util.addElementToList(am,
                new String[] { "0", "0", "1", "0", "0", "1", "1", "1", "0", "0", "0", "0", "0", "0" });
        Util.addElementToList(am,
                new String[] { "0", "0", "0", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0" });
        Util.addElementToList(am,
                new String[] { "0", "0", "0", "0", "1", "0", "0", "0", "1", "0", "0", "0", "0", "0" });
        Util.addElementToList(am,
                new String[] { "0", "0", "0", "0", "1", "0", "0", "0", "1", "0", "0", "0", "0", "0" });
        Util.addElementToList(am,
                new String[] { "0", "0", "0", "0", "0", "0", "1", "1", "0", "1", "0", "0", "0", "0" });
        Util.addElementToList(am,
                new String[] { "0", "0", "0", "0", "0", "0", "0", "0", "1", "0", "1", "1", "0", "0" });
        Util.addElementToList(am,
                new String[] { "0", "0", "0", "0", "0", "0", "0", "0", "0", "1", "0", "1", "0", "0" });
        Util.addElementToList(am,
                new String[] { "0", "0", "0", "0", "0", "0", "0", "0", "0", "1", "1", "0", "1", "1" });
        Util.addElementToList(am,
                new String[] { "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "1", "0", "1" });
        Util.addElementToList(am,
                new String[] { "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "1", "1", "0" });
        return am;
    }

}
