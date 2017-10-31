package au.edu.cdu.dds;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Assert;

import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;

/**
 * an util class for test purpose
 */
public class TestUtil {
	// private static Logger log = LogUtil.getLogger(TestUtil.class);
	// private static final int IV = ConstantValue.IMPOSSIBLE_VALUE;
	public static final String FUNCTION_SEP = "***********************************************************";

	//
	public static String getCurrentPath() {
		return Paths.get(".").toAbsolutePath().normalize().toString();
	}

	//
	// public static List<List<Integer>> getTestCase1ForBasicMSC() {
	// int[] l1 = { 1, 2, 3 };
	// int[] l2 = { 1, 2, 4 };
	// int[] l3 = { 1, 3, 4 };
	// int[] l4 = { 2, 3, 4, 5, 6, 7 };
	// int[] l5 = { 4, 5 };
	// int[] l6 = { 4, 6 };
	// int[] l7 = { 4, 7 };
	//
	// List<Integer> list1 = Util.arrayToList(l1);
	// List<Integer> list2 = Util.arrayToList(l2);
	// List<Integer> list3 = Util.arrayToList(l3);
	// List<Integer> list4 = Util.arrayToList(l4);
	// List<Integer> list5 = Util.arrayToList(l5);
	// List<Integer> list6 = Util.arrayToList(l6);
	// List<Integer> list7 = Util.arrayToList(l7);
	//
	// List<List<Integer>> list = new ArrayList<List<Integer>>();
	// list.add(list1);
	// list.add(list2);
	// list.add(list3);
	// list.add(list4);
	// list.add(list5);
	// list.add(list6);
	// list.add(list7);
	// return list;
	// }
	//
	// public static Map<Integer, List<Integer>> getTestCase1ForBasicMSCMap() {
	//
	// int[] l1 = { 1, 2, 3 };
	// int[] l2 = { 1, 2, 4 };
	// int[] l3 = { 1, 3, 4 };
	// int[] l4 = { 2, 3, 4, 5, 6, 7 };
	// int[] l5 = { 4, 5 };
	// int[] l6 = { 4, 6 };
	// int[] l7 = { 4, 7 };
	//
	// List<Integer> list1 = Util.arrayToList(l1);
	// List<Integer> list2 = Util.arrayToList(l2);
	// List<Integer> list3 = Util.arrayToList(l3);
	// List<Integer> list4 = Util.arrayToList(l4);
	// List<Integer> list5 = Util.arrayToList(l5);
	// List<Integer> list6 = Util.arrayToList(l6);
	// List<Integer> list7 = Util.arrayToList(l7);
	//
	// Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
	// map.put(1, list1);
	// map.put(2, list2);
	// map.put(3, list3);
	// map.put(4, list4);
	// map.put(5, list5);
	// map.put(6, list6);
	// map.put(7, list7);
	// return map;
	// }
	//
	// private static GlobalVariable<String, String> setGlobalVariable(int eCount,
	// int[] eL, int[] eIL, int[] freq,
	// int[][] eAL, int[][] eIM, int sCount, int[] sL, int[] sIL, int[] card,
	// int[][] sAL, int[][] sIM) {
	// GlobalVariable<String, String> gv = new GlobalVariable<String, String>();
	// gv.setCard(card);
	// gv.seteAL(eAL);
	// gv.seteIL(eIL);
	// gv.seteIM(eIM);
	// gv.setFreq(freq);
	// gv.setsAL(sAL);
	// gv.setsIL(sIL);
	// gv.setsIM(sIM);
	// gv.seteCount(eCount);
	// gv.setsCount(sCount);
	// gv.setsL(sL);
	// gv.seteL(eL);
	//
	// gv.setBestSolCount(sCount);
	// gv.setSolCount(0);
	//
	// int[] mate = new int[eCount + 1];
	//
	// // for (int i = 0; i <= eCount; i++) {
	// // mate[i] = ConstantValue.MATE_EXPOSE;
	// // }
	// gv.setMate(mate);
	//
	// int[] sol = new int[sCount];
	// int[] bestSol = new int[sCount];
	// for (int i = 0; i < sCount; i++) {
	// sol[i] = ConstantValue.IMPOSSIBLE_VALUE;
	// bestSol[i] = ConstantValue.IMPOSSIBLE_VALUE;
	// }
	// gv.setSol(sol);
	// gv.setBestSol(bestSol);
	// gv.setSolPtr(1);
	//
	// return gv;
	//
	// }

	// public static GlobalVariable<String, String> getTC1RepFile() throws
	// IOException {
	// String filePath = TestUtil.getCurrentPath() +
	// "/src/test/resources/sample1.txt";
	//
	// GlobalVariable<String, String> gv = new
	// FileOperation().readGraphByEdgePair(filePath);
	// return gv;
	//
	// }

	// public static GlobalVariable<String, String> getTC1Rep() {
	// int eCount = 6;
	//
	// int[] eL = { IV, 1, 2, 3, 4, 5, 6 };
	// int[] eIL = { IV, 1, 2, 3, 4, 5, 6 };
	// int[] freq = { eCount, 3, 3, 3, 4, 3, 2 };
	// int[][] eAL = { {}, { IV, 1, 2, 3 }, { IV, 1, 2, 4 }, { IV, 1, 3, 4 }, { IV,
	// 2, 3, 4, 5 }, { IV, 4, 5, 6 },
	// { IV, 5, 6 } };
	// int[][] eIM = { { IV, IV, IV, IV, IV, IV, IV, }, { IV, 1, 1, 1, IV, IV, IV },
	// { IV, 2, 2, IV, 1, IV, IV },
	// { IV, 3, IV, 2, 2, IV, IV }, { IV, IV, 3, 3, 3, 1, IV }, { IV, IV, IV, IV, 4,
	// 2, 1 },
	// { IV, IV, IV, IV, IV, 3, 2 } };
	//
	// int sCount = 6;
	//
	// int[] sL = { IV, 1, 2, 3, 4, 5, 6 };
	// int[] sIL = { IV, 1, 2, 3, 4, 5, 6 };
	// int[] card = { sCount, 3, 3, 3, 4, 3, 2 };
	// int[][] sAL = { {}, { IV, 1, 2, 3 }, { IV, 1, 2, 4 }, { IV, 1, 3, 4 }, { IV,
	// 2, 3, 4, 5 }, { IV, 4, 5, 6 },
	// { IV, 5, 6 } };
	// int[][] sIM = { { IV, IV, IV, IV, IV, IV, IV, }, { IV, 1, 1, 1, IV, IV, IV },
	// { IV, 2, 2, IV, 1, IV, IV },
	// { IV, 3, IV, 2, 2, IV, IV }, { IV, IV, 3, 3, 3, 1, IV }, { IV, IV, IV, IV, 4,
	// 2, 1 },
	// { IV, IV, IV, IV, IV, 3, 2 } };
	// return setGlobalVariable(eCount, eL, eIL, freq, eAL, eIM, sCount, sL, sIL,
	// card, sAL, sIM);
	//
	// }
	//
	// public static GlobalVariable<String, String> getTC2Rep() {
	// int eCount = 7;
	// int[] eL = { IV, 1, 2, 3, 4, 5, 6, 7 };
	// int[] eIL = { IV, 1, 2, 3, 4, 5, 6, 7 };
	// int[] freq = { eCount, 3, 3, 2, 2, 1, 2, 1 };
	// int[][] eAL = { {}, { IV, 1, 2, 3 }, { IV, 1, 4, 5 }, { IV, 2, 4 }, { IV, 3,
	// 5 }, { IV, 6 }, { IV, 6, 7 },
	// { IV, 7 } };
	//
	// int[][] eIM = { { IV, IV, IV, IV, IV, IV, IV, IV, }, { IV, 1, 1, IV, IV, IV,
	// IV, IV },
	// { IV, 2, IV, 1, IV, IV, IV, IV }, { IV, 3, IV, IV, 2, IV, IV, IV }, { IV, IV,
	// 2, 2, IV, IV, IV, IV },
	// { IV, IV, 3, IV, 2, IV, IV, IV }, { IV, IV, IV, IV, IV, 1, 1, IV }, { IV, IV,
	// IV, IV, IV, IV, 2, 1 } };
	//
	// int sCount = 7;
	// int[] sL = { IV, 1, 2, 3, 4, 5, 6, 7 };
	// int[] sIL = { IV, 1, 2, 3, 4, 5, 6, 7 };
	// int[] card = { sCount, 2, 2, 2, 2, 2, 2, 2 };
	// int[][] sAL = { {}, { IV, 1, 2 }, { IV, 1, 3 }, { IV, 1, 4 }, { IV, 2, 3 }, {
	// IV, 2, 4 }, { IV, 5, 6 },
	// { IV, 6, 7 } };
	//
	// int[][] sIM = { { IV, IV, IV, IV, IV, IV, IV, IV }, { IV, 1, 1, 1, IV, IV,
	// IV, IV },
	// { IV, 2, IV, IV, 1, 1, IV, IV }, { IV, IV, 2, IV, 2, IV, IV, IV }, { IV, IV,
	// IV, 2, IV, 2, IV, IV },
	// { IV, IV, IV, IV, IV, IV, 1, IV }, { IV, IV, IV, IV, IV, IV, 2, 1 },
	// { IV, IV, IV, IV, IV, IV, IV, 2 } };
	//
	// return setGlobalVariable(eCount, eL, eIL, freq, eAL, eIM, sCount, sL, sIL,
	// card, sAL, sIM);
	// }
	//
	// public static Map<Integer, List<Integer>> getTestCase1ForMaxMatching() {
	// Map<Integer, List<Integer>> g = new HashMap<Integer, List<Integer>>();
	// List<Integer> l0 = new ArrayList<Integer>();
	// l0.add(1);
	// l0.add(2);
	// l0.add(3);
	// g.put(0, l0);
	//
	// List<Integer> l1 = new ArrayList<Integer>();
	// l1.add(0);
	// l1.add(2);
	// l1.add(3);
	// g.put(1, l1);
	//
	// List<Integer> l2 = new ArrayList<Integer>();
	// l2.add(0);
	// l2.add(1);
	// g.put(2, l2);
	//
	// List<Integer> l3 = new ArrayList<Integer>();
	// l3.add(0);
	// l3.add(1);
	// g.put(3, l3);
	//
	// List<Integer> l4 = new ArrayList<Integer>();
	// l4.add(5);
	// g.put(4, l4);
	//
	// List<Integer> l5 = new ArrayList<Integer>();
	// l5.add(4);
	// l5.add(6);
	// g.put(5, l5);
	//
	// List<Integer> l6 = new ArrayList<Integer>();
	// l6.add(5);
	// g.put(6, l6);
	// return g;
	// }
	//
	// public static Map<Integer, List<Integer>> getTestCase2ForMaxMatching() {
	// Map<Integer, List<Integer>> g = new HashMap<Integer, List<Integer>>();
	// List<Integer> l0 = new ArrayList<Integer>();
	// l0.add(1);
	//
	// g.put(0, l0);
	//
	// List<Integer> l1 = new ArrayList<Integer>();
	// l1.add(0);
	// l1.add(2);
	// l1.add(3);
	// g.put(1, l1);
	//
	// List<Integer> l2 = new ArrayList<Integer>();
	// l2.add(1);
	// l2.add(5);
	// g.put(2, l2);
	//
	// List<Integer> l3 = new ArrayList<Integer>();
	// l3.add(1);
	// l3.add(4);
	// l3.add(5);
	// g.put(3, l3);
	//
	// List<Integer> l4 = new ArrayList<Integer>();
	// l4.add(3);
	// l4.add(5);
	// g.put(4, l4);
	//
	// List<Integer> l5 = new ArrayList<Integer>();
	// l5.add(2);
	// l5.add(3);
	// l5.add(4);
	// g.put(5, l5);
	//
	// return g;
	// }

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

	public static final String KONECT_PATH = "src/test/resources/KONECT/";

	public static final TestParameter[] KONECT_TP = { new TestParameter("000027_zebra.konet", 15, 15, true),
			new TestParameter("000034_zachary.konet", 15, 15, true),
			new TestParameter("000062_dolphins.konet", 15, 15, true),
			new TestParameter("000112_David_Copperfield.konet", 15, 15, true),
			new TestParameter("000198_Jazz_musicians.konet", 15, 15, true),
			new TestParameter("000212_pdzbase.konet", 15, 15, true),
			new TestParameter("001133_rovira.konet", 50, 50, true),
			new TestParameter("001174_euroroad.konet", 50, 50, true),
			new TestParameter("001858_hamster.konet", 50, 50, true),
			new TestParameter("002426_hamster_ful.konet", 15, 15, true),
			new TestParameter("002888_facebook.konet", 15, 15, true),
			new TestParameter("010680_Pretty_Good_Privacy.konet", 15, 15, true),
			new TestParameter("018771_arXiv.konet", 15, 15, true),
			new TestParameter("026475-caida.konet", 15, 15, false),
			new TestParameter("028093_arXiv_hep.konet", 15, 15, false),
			new TestParameter("058228-brightkite.konet", 15, 15, false),
			new TestParameter("063731-facebookfriendships.konet", 15, 15, false),};
	// /**
	// *
	// * @param instanceCodes
	// * @param algTableName
	// * @param msc
	// * @throws IOException
	// */
	// public static void basicTest(String[] instanceCodes, String algTableName,
	// IMDS<String, String> msc)
	// throws IOException {
	// String baseFilePath = TestUtil.getCurrentPath() + "/src/test/resources";
	// String batchNum = Util.getBatchNum();
	//
	// DBParameter dbpIn = null;
	// DBParameter dbpOut = null;
	//
	// for (String instanceCode : instanceCodes) {
	//
	// dbpIn = getDBParamInput(instanceCode);
	//
	// List<Map<String, String>> lst = DBOperation.executeQuery(dbpIn);
	// int lstLen = lst.size();
	// for (int i = 0; i < lstLen; i++) {
	// Map<String, String> map = lst.get(i);
	// GlobalVariable<String, String> gv = getGV(baseFilePath, map);
	//
	// String id = map.get(ConstantValue.DB_COL_INS_ID);
	//
	// long start = System.nanoTime();
	// msc.run(gv);
	// long end = System.nanoTime();
	//
	// Assert.assertTrue(Util.isValidSolution(gv));
	//
	// dbpOut = getDBParamOutPut(algTableName, batchNum, id, gv, start, end);
	// DBOperation.executeInsert(dbpOut);
	//
	// dbpOut = null;
	//
	// StringBuffer sb = new StringBuffer();
	// sb.append(instanceCode).append(":").append(gv.getBestSolCount()).append(":")
	// .append(String.format("%.3f", ((end - start) / 1000000000.0))).append(" s.");
	// log.debug(sb.toString());
	//
	// }
	// dbpIn = null;
	//
	// }
	// }
	//
	// private static GlobalVariable<String, String> getGV(String baseFilePath,
	// Map<String, String> map)
	// throws IOException {
	// String iPathName = map.get(ConstantValue.DB_COL_INS_PATH_NAME);
	// String dPathName = map.get(ConstantValue.DB_COL_DATASET_PATH_NAME);
	// String filePath = baseFilePath + dPathName + iPathName;
	// GlobalVariable<String, String> gv = new
	// FileOperation().readGraphByEdgePair(filePath);
	// return gv;
	// }
	//
	//// private static DBParameter getDBParamOutPut(String algTableName, String id,
	// GlobalVariable<String, String> gv,
	//// long start, long end, String batchNum, int threshold) {
	//// DBParameter dbpOut;
	//// dbpOut = new DBParameter();
	//// dbpOut.setTableName(algTableName);
	//// // String[] colPairNamesOut = { ConstantValue.DB_COL_INS_ID,
	//// // ConstantValue.DB_COL_RESULT_SIZE,
	//// // ConstantValue.DB_COL_RUNNING_TIME, ConstantValue.DB_COL_BATCH_NUM,
	//// // ConstantValue.DB_COL_THRESHOLD,
	//// // ConstantValue.DB_COL_MODEL };
	//// String[] colPairNamesOut = { ConstantValue.DB_COL_INS_ID,
	// ConstantValue.DB_COL_RESULT_SIZE,
	//// ConstantValue.DB_COL_RUNNING_TIME, ConstantValue.DB_COL_BATCH_NUM, };
	////
	//// String[] colPairValuesOut = { id, Integer.toString(gv.getBestSolCount()),
	// Long.toString((end - start)),
	//// batchNum, Integer.toString(threshold), gv.getModel() };
	//// dbpOut.setColPairNames(colPairNamesOut);
	//// dbpOut.setColPairValues(colPairValuesOut);
	//// return dbpOut;
	//// }
	//
	// private static DBParameter getDBParamOutPut(String algTableName, String
	// batchNum, String id,
	// GlobalVariable<String, String> gv, long start, long end) {
	// DBParameter dbpOut;
	// dbpOut = new DBParameter();
	// dbpOut.setTableName(algTableName);
	// String[] colPairNamesOut = { ConstantValue.DB_COL_INS_ID,
	// ConstantValue.DB_COL_BATCH_NUM,
	// ConstantValue.DB_COL_RESULT_SIZE, ConstantValue.DB_COL_RUNNING_TIME };
	// String[] colPairValuesOut = { id, batchNum,
	// Integer.toString(gv.getBestSolCount()),
	// Long.toString((end - start)) };
	// dbpOut.setColPairNames(colPairNamesOut);
	// dbpOut.setColPairValues(colPairValuesOut);
	// return dbpOut;
	// }
	//
	// private static DBParameter getDBParamInput(String instanceCode) {
	// DBParameter dbpIn;
	// dbpIn = new DBParameter();
	// dbpIn.setTableName(ConstantValue.DB_VNAME_INS_OPT);
	// // String[] colNamesIn = { ConstantValue.DB_COL_INS_ID,
	// // ConstantValue.DB_COL_INS_NAME,
	// // ConstantValue.DB_COL_DATASET_PATH_NAME,
	// // ConstantValue.DB_COL_INS_PATH_NAME,
	// // ConstantValue.DB_COL_BEST_RESULT_SIZE,
	// // ConstantValue.DB_COL_ACCEPT_RESULT_SIZE,
	// // ConstantValue.DB_COL_UNACCEPT_RESULT_SIZE,
	// // ConstantValue.DB_COL_ALLOWED_RUNNING_TIME };
	// String[] colNamesIn = { ConstantValue.DB_COL_INS_ID,
	// ConstantValue.DB_COL_INS_NAME,
	// ConstantValue.DB_COL_DATASET_PATH_NAME, ConstantValue.DB_COL_INS_PATH_NAME,
	// ConstantValue.DB_COL_BEST_RESULT_SIZE };
	//
	// String[] colPairNamesIn = { ConstantValue.DB_COL_INS_CODE };
	// String[] colPairOperatorsIn = { "=" };
	// String[] colPairValuesIn = { instanceCode };
	// dbpIn.setColNames(colNamesIn);
	// dbpIn.setColPairNames(colPairNamesIn);
	// dbpIn.setColPairOperators(colPairOperatorsIn);
	// dbpIn.setColPairValues(colPairValuesIn);
	// return dbpIn;
	// }

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
