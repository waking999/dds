package au.edu.cdu.dds;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;
import org.junit.Assert;

import au.edu.cdu.dds.algo.ds.IAlgorithm;
import au.edu.cdu.dds.io.DBOperation;
import au.edu.cdu.dds.io.DBParameter;
import au.edu.cdu.dds.io.FileOperation;
import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;
import au.edu.cdu.dds.util.Util;

/**
 * an util class for test purpose
 */
public class TestUtil {

	public static final String FUNCTION_SEP = "***********************************************************";

	//
	public static String getCurrentPath() {
		return Paths.get(".").toAbsolutePath().normalize().toString();
	}

	

	/**
	 * the basic structure to run algorithms and write to db
	 * 
	 * @param className
	 * @param dataSetName
	 * @param algo
	 * @param log
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void basicFunc(String className, String dataSetName, IAlgorithm<String> algo, Logger log)
			throws FileNotFoundException, IOException, InterruptedException {
		// get the info of the instances of a certain dataset such as id, code, path
		List<Map<String, String>> lst = Util.getInstanceInfo(dataSetName);

		// loop to run the algorithm with the instance info and write to db
		String resourcePath = TestUtil.getCurrentPath() + "/src/test/resources";

		DBParameter dbpOut = null;
		String batchNum = Util.getBatchNum();

		for (Map<String, String> map : lst) {
			String dataSetPath = map.get(ConstantValue.DB_COL_DATASET_PATH_NAME);
			String pathName = map.get(ConstantValue.DB_COL_INS_PATH_NAME);
			String id = map.get(ConstantValue.DB_COL_ID);
			String instanceCode = map.get(ConstantValue.DB_COL_INS_CODE);
			String algTableName = ConstantValue.TBL_ALG_PREFIX + instanceCode;

			String inputFile = resourcePath + dataSetPath + pathName;
			// read file
			GlobalVariable<String> gv = new FileOperation().readGraphByEdgePair(inputFile);
			algo.setGV(gv);
			long start = System.nanoTime();
			// run algorithm
			algo.compute();
			long end = System.nanoTime();

			// ensure the solution is valid
			Assert.assertTrue(Util.isValidSolution(gv));

			// write to db
			DBOperation.createTable(instanceCode);
			dbpOut = getDBParamOutPut(algTableName, batchNum, id, gv, start, end, className);
			DBOperation.executeInsert(dbpOut);

			dbpOut = null;

			// write to console
			StringBuffer sb = new StringBuffer();
			sb.append(instanceCode).append(":").append(gv.getIdxSolSize()).append(":")
					.append(String.format("%.3f", ((end - start) / 1000000000.0))).append(" s.");
			log.debug(sb.toString());

		}
	}



	// /**
	// * the basic structure to run algorithms
	// *
	// * @param className
	// * @param path
	// * @param algo
	// * @param tps
	// * @param log
	// * @throws FileNotFoundException
	// * @throws IOException
	// * @throws InterruptedException
	// */
	// public static void basicFunc(String className, String path,
	// IAlgorithm<String> algo, TestParameter[] tps,
	// Logger log) throws FileNotFoundException, IOException, InterruptedException {
	//
	// for (TestParameter tp : tps) {
	// if (tp.isBeTest()) {
	// StringBuffer sb = new StringBuffer(className);
	//
	// sb.append("-").append(tp.getFile()).append(",");
	//
	// String inputFile = path + tp.getFile();
	//
	// GlobalVariable<String> gv = new
	// FileOperation().readGraphByEdgePair(inputFile);
	// algo.setGV(gv);
	// long start = System.nanoTime();
	// algo.compute();
	// long end = System.nanoTime();
	//
	// Assert.assertTrue(Util.isValidSolution(gv));
	//
	// sb.append((end - start) + " ns,");
	// sb.append(gv.getIdxSolSize());
	//
	// System.out.println(sb.toString());
	// }
	//
	// }
	//
	// }

	/**
	 * generate the database parameters for being written to db
	 * 
	 * @param algTableName
	 * @param batchNum
	 * @param id
	 * @param gv
	 * @param start
	 * @param end
	 * @return
	 */
	private static DBParameter getDBParamOutPut(String algTableName, String batchNum, String id,
			GlobalVariable<String> gv, long start, long end, String algoName) {
		DBParameter dbpOut;
		dbpOut = new DBParameter();
		dbpOut.setTableName(algTableName);
		String[] colPairNamesOut = { ConstantValue.DB_COL_INS_ID, ConstantValue.DB_COL_BATCH_NUM,
				ConstantValue.DB_COL_RESULT_SIZE, ConstantValue.DB_COL_RUNNING_TIME, ConstantValue.DB_COL_ALGORITHM };
		String[] colPairValuesOut = { id, batchNum, Integer.toString(gv.getIdxSolSize()), Long.toString((end - start)),
				algoName };
		dbpOut.setColPairNames(colPairNamesOut);
		dbpOut.setColPairValues(colPairValuesOut);
		return dbpOut;
	}

	/**
	 * print status of global variables in a format
	 *
	 * @param gv,
	 *            global variables
	 */
	public static void printGlobalVariableStatus(GlobalVariable<String> gv) {
		String styleStr = "%-6s %-6s %-6s %-20s %-20s";

		int[] idxLst = gv.getIdxLst();
		int actVerCnt = gv.getActVerCnt();
		int[] idxUtil = gv.getIdxUtil();
		int[][] idxAL = gv.getIdxAL();
		int[][] idxIM = gv.getIdxIM();

		String[] vL = gv.getVerLst();

		printStatus(styleStr, actVerCnt, "vCount", actVerCnt, "vL", vL, "vIL", idxLst, "util", idxUtil, "vAL", idxAL,
				"vIM", idxIM);

		System.out.println("--------------------------------------------------------");
	}

	/**
	 * print status of global variables in a format
	 * 
	 * @param styleStr
	 * @param len
	 * @param actCountName
	 * @param actCount
	 * @param lName
	 * @param l
	 * @param ilName
	 * @param il
	 * @param degName
	 * @param deg
	 * @param alName
	 * @param al
	 * @param imName
	 * @param im
	 */
	private static void printStatus(String styleStr, int len, String actCountName, int actCount, String lName,
			String[] l, String ilName, int[] il, String degName, int[] deg, String alName, int[][] al, String imName,
			int[][] im) {
		StringBuffer sb = new StringBuffer();

		System.out.println(actCountName + ":" + actCount);
		System.out.printf(styleStr, lName, ilName, degName, alName, imName);
		System.out.println();
		for (int i = 0; i < len; i++) {
			sb.setLength(0);

			System.out.printf(styleStr, i + " " + l[i], i + " " + il[i], i + " " + deg[i],
					i + " " + arrayToString(al[i]), arrayToString(im[i]));
			System.out.println();
		}
	}

	/**
	 * used for print status
	 * 
	 * @param array
	 * @return
	 */
	private static String arrayToString(int[] array) {
		StringBuffer sb = new StringBuffer();
		int arrayLen = array.length;
		for (int i = 0; i < arrayLen; i++) {
			if (array[i] == ConstantValue.IMPOSSIBLE_VALUE) {
				sb.append("N").append(",");
			} else {
				sb.append(array[i]).append(",");
			}
		}
		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * verify if the output is the same as the expect
	 * 
	 * @param expect
	 * @param output
	 */
	public static void verify(int[][] expect, List<List<Integer>> output) {
		int expectLen = expect.length;
		int outputSize = output.size();
		Assert.assertTrue(expectLen == outputSize);
		for (int i = 0; i < expectLen; i++) {
			String s1 = IntStream.of(expect[i]).map(str -> str).boxed().collect(Collectors.toList()).stream()
					.map(num -> Integer.toString(num)).collect(Collectors.joining(","));

			String s2 = output.get(i).stream().map(num -> Integer.toString(num)).collect(Collectors.joining(","));
			Assert.assertTrue(s1.equals(s2));
		}

	}

	public static void verifySort(int[][] expect, int[][] output) {
		int expectLen = expect.length;
		int outputSize = output.length;
		Assert.assertTrue(expectLen == outputSize);
		for (int i = 0; i < expectLen; i++) {
			String s1 = Arrays.stream(expect[i]).map(str -> str).sorted().boxed().collect(Collectors.toList()).stream()
					.map(num -> Integer.toString(num)).collect(Collectors.joining(","));

			String s2 = Arrays.stream(output[i]).map(str -> str).sorted().boxed().collect(Collectors.toList()).stream()
					.map(num -> Integer.toString(num)).collect(Collectors.joining(","));
			Assert.assertTrue(s1.equals(s2));
		}

	}

	public static void verifyUnsort(int[][] expect, int[][] output) {
		int expectLen = expect.length;
		int outputSize = output.length;
		Assert.assertTrue(expectLen == outputSize);
		for (int i = 0; i < expectLen; i++) {
			String s1 = Arrays.stream(expect[i]).map(str -> str).boxed().collect(Collectors.toList()).stream()
					.map(num -> Integer.toString(num)).collect(Collectors.joining(","));

			String s2 = Arrays.stream(output[i]).map(str -> str).boxed().collect(Collectors.toList()).stream()
					.map(num -> Integer.toString(num)).collect(Collectors.joining(","));
			Assert.assertTrue(s1.equals(s2));
		}

	}

	public static void verify(String[] expect, List<String> output) {

		int expectLen = expect.length;
		int outputSize = output.size();
		Assert.assertTrue(expectLen == outputSize);
		String expectStr = Arrays.asList(expect).stream().collect(Collectors.joining(","));
		String outputStr = output.stream().collect(Collectors.joining(","));
		Assert.assertTrue(expectStr.equals(outputStr));
	}

	public static void verifyUnsort(String[] expect, String[] output) {

		int expectLen = expect.length;
		int outputSize = output.length;
		Assert.assertTrue(expectLen == outputSize);
		String expectStr = Arrays.asList(expect).stream().collect(Collectors.joining(","));
		String outputStr = Arrays.asList(output).stream().collect(Collectors.joining(","));
		Assert.assertTrue(expectStr.equals(outputStr));
	}

	public static void verifySort(String[] expect, String[] output) {

		int expectLen = expect.length;
		int outputSize = output.length;
		Assert.assertTrue(expectLen == outputSize);
		String expectStr = Arrays.asList(expect).stream().sorted().collect(Collectors.joining(","));
		String outputStr = Arrays.asList(output).stream().sorted().collect(Collectors.joining(","));
		Assert.assertTrue(expectStr.equals(outputStr));
	}

	public static void verify(int[] expect, List<Integer> output) {

		int expectLen = expect.length;
		int outputSize = output.size();
		Assert.assertTrue(expectLen == outputSize);
		String expectStr = Arrays.stream(expect).sorted().boxed().collect(Collectors.toList()).stream()
				.map(num -> Integer.toString(num)).collect(Collectors.joining(","));
		String outputStr = output.stream().sorted().map(num -> Integer.toString(num)).collect(Collectors.joining(","));
		Assert.assertTrue(expectStr.equals(outputStr));
	}

	public static void verify(int[] expect, Integer[] output) {

		int expectLen = expect.length;
		int outputSize = output.length;
		Assert.assertTrue(expectLen == outputSize);
		String expectStr = Arrays.stream(expect).sorted().boxed().collect(Collectors.toList()).stream()
				.map(num -> Integer.toString(num)).collect(Collectors.joining(","));
		String outputStr = Arrays.stream(output).sorted().collect(Collectors.toList()).stream()
				.map(num -> Integer.toString(num)).collect(Collectors.joining(","));
		Assert.assertTrue(expectStr.equals(outputStr));
	}

	public static void verifySort(int[] expect, int[] output) {

		int expectLen = expect.length;
		int outputSize = output.length;
		Assert.assertTrue(expectLen == outputSize);
		String expectStr = Arrays.stream(expect).sorted().boxed().collect(Collectors.toList()).stream()
				.map(num -> Integer.toString(num)).collect(Collectors.joining(","));
		String outputStr = Arrays.stream(output).sorted().boxed().collect(Collectors.toList()).stream()
				.map(num -> Integer.toString(num)).collect(Collectors.joining(","));
		Assert.assertTrue(expectStr.equals(outputStr));
	}

	public static void verifyUnsort(int[] expect, int[] output) {

		int expectLen = expect.length;
		int outputSize = output.length;
		Assert.assertTrue(expectLen == outputSize);
		String expectStr = Arrays.stream(expect).boxed().collect(Collectors.toList()).stream()
				.map(num -> Integer.toString(num)).collect(Collectors.joining(","));
		String outputStr = Arrays.stream(output).boxed().collect(Collectors.toList()).stream()
				.map(num -> Integer.toString(num)).collect(Collectors.joining(","));
		Assert.assertTrue(expectStr.equals(outputStr));
	}

	public static void verifySort(float[] expect, float[] output) {

		int expectLen = expect.length;
		int outputLen = output.length;
		Assert.assertTrue(expectLen == outputLen);
		Arrays.sort(output);
		boolean eqFlag = true;
		for (int i = 0; i < expectLen; i++) {
			if (Math.abs(expect[i] - output[i]) <= ConstantValue.FLOAT_NO_DIFF) {
				continue;
			} else {
				eqFlag = false;
			}
		}
		Assert.assertTrue(eqFlag);

	}

	public static void verifyUnsort(float[] expect, float[] output) {
		int expectLen = expect.length;
		int outputLen = output.length;
		Assert.assertTrue(expectLen == outputLen);
		boolean eqFlag = true;
		for (int i = 0; i < expectLen; i++) {
			if (Math.abs(expect[i] - output[i]) <= ConstantValue.FLOAT_NO_DIFF) {
				continue;
			} else {
				eqFlag = false;
			}
		}
		Assert.assertTrue(eqFlag);
	}

	public static void verify(int expect, int output) {
		Assert.assertTrue(expect == output);
	}

	public static void verify(boolean expect, boolean output) {
		Assert.assertTrue(expect == output);
	}

	public static void verify(String expect, String output) {
		Assert.assertTrue(expect.equals(output));
	}

}
