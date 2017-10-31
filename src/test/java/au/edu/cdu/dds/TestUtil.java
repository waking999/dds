package au.edu.cdu.dds;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.log4j.Logger;
import org.junit.Assert;

import au.edu.cdu.dds.algo.ds.IAlgorithm;
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
	 * the basic structure to run algorithms
	 * 
	 * @param className
	 * @param path
	 * @param algo
	 * @param tps
	 * @param log
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void basicFunc(String className, String path, IAlgorithm<String> algo, TestParameter[] tps,
			Logger log) throws FileNotFoundException, IOException, InterruptedException {

		for (TestParameter tp : tps) {
			if (tp.isBeTest()) {
				StringBuffer sb = new StringBuffer(className);

				sb.append("-").append(tp.getFile()).append(",");

				String inputFile = path + tp.getFile();
				GlobalVariable<String> gv = new FileOperation().readGraphByEdgePair(inputFile);
				algo.setGV(gv);
				long start = System.nanoTime();
				algo.compute();
				long end = System.nanoTime();

				Assert.assertTrue(Util.isValidSolution(gv));

				sb.append((end - start) + " ns,");
				sb.append(gv.getIdxSolSize());

				System.out.println(sb.toString());
			}

		}

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
			new TestParameter("063731-facebookfriendships.konet", 15, 15, false), };

	public static final String DIMACS_PATH = "src/test/resources/DIMACS/";

	public static final TestParameter[] DIMACS_TP = { new TestParameter("C1000.9.clq", 10, 10, true),
			new TestParameter("C125.9.clq", 10, 10, true), new TestParameter("C2000.5.clq", 10, 10, true),
			new TestParameter("C2000.9.clq", 10, 10, true), new TestParameter("C250.9.clq", 10, 10, true),
			new TestParameter("C4000.5.clq", 10, 10, true), new TestParameter("C500.9.clq", 10, 10, true),
			new TestParameter("DSJC1000.5.clq", 10, 10, true), new TestParameter("DSJC500.5.clq", 10, 10, true),
			new TestParameter("MANN_a27.clq", 10, 10, true), new TestParameter("MANN_a81.clq", 10, 10, true),
			new TestParameter("brock200_2.clq", 10, 10, true), new TestParameter("brock200_4.clq", 10, 10, true),
			new TestParameter("brock400_2.clq", 10, 10, true), new TestParameter("brock400_4.clq", 10, 10, true),
			new TestParameter("brock800_2.clq", 10, 10, true), new TestParameter("brock800_4.clq", 10, 10, true),
			new TestParameter("gen200_p0.9_44.clq", 10, 10, true),
			new TestParameter("gen200_p0.9_55.clq", 10, 10, true),
			new TestParameter("gen400_p0.9_55.clq", 10, 10, true),
			new TestParameter("gen400_p0.9_65.clq", 10, 10, true),
			new TestParameter("gen400_p0.9_75.clq", 10, 10, true), new TestParameter("hamming10-4.clq", 10, 10, true),
			new TestParameter("hamming8-4.clq", 10, 10, true), new TestParameter("keller4.clq", 10, 10, true),
			new TestParameter("keller5.clq", 10, 10, true), new TestParameter("keller6.clq", 10, 10, true),
			new TestParameter("p_hat1500-1.clq", 10, 10, true), new TestParameter("p_hat1500-2.clq", 10, 10, true),
			new TestParameter("p_hat1500-3.clq", 10, 10, true), new TestParameter("p_hat300-1.clq", 10, 10, true),
			new TestParameter("p_hat300-2.clq", 10, 10, true), new TestParameter("p_hat300-3.clq", 10, 10, true),
			new TestParameter("p_hat700-1.clq", 10, 10, true), new TestParameter("p_hat700-2.clq", 10, 10, true),
			new TestParameter("p_hat700-3.clq", 10, 10, true), };

	public static final String BHOSLIB_PATH = "src/test/resources/BHOSLIB/";

	public static final TestParameter[] BHOSLIB_TP = { new TestParameter("frb30-15-mis/frb30-15-1.mis", 10, 10, true),
			new TestParameter("frb30-15-mis/frb30-15-2.mis", 10, 10, true),
			new TestParameter("frb30-15-mis/frb30-15-3.mis", 10, 10, true),
			new TestParameter("frb30-15-mis/frb30-15-4.mis", 10, 10, true),
			new TestParameter("frb30-15-mis/frb30-15-5.mis", 10, 10, true),
			new TestParameter("frb35-17-mis/frb35-17-1.mis", 10, 10, true),
			new TestParameter("frb35-17-mis/frb35-17-2.mis", 10, 10, true),
			new TestParameter("frb35-17-mis/frb35-17-3.mis", 10, 10, true),
			new TestParameter("frb35-17-mis/frb35-17-4.mis", 10, 10, true),
			new TestParameter("frb35-17-mis/frb35-17-5.mis", 10, 10, true),
			new TestParameter("frb40-19-mis/frb40-19-1.mis", 10, 10, true),
			new TestParameter("frb40-19-mis/frb40-19-2.mis", 10, 10, true),
			new TestParameter("frb40-19-mis/frb40-19-3.mis", 10, 10, true),
			new TestParameter("frb40-19-mis/frb40-19-4.mis", 10, 10, true),
			new TestParameter("frb40-19-mis/frb40-19-5.mis", 10, 10, true),
			new TestParameter("frb45-21-mis/frb45-21-1.mis", 10, 10, true),
			new TestParameter("frb45-21-mis/frb45-21-2.mis", 10, 10, true),
			new TestParameter("frb45-21-mis/frb45-21-3.mis", 10, 10, true),
			new TestParameter("frb45-21-mis/frb45-21-4.mis", 10, 10, true),
			new TestParameter("frb45-21-mis/frb45-21-5.mis", 10, 10, true),
			new TestParameter("frb53-24-mis/frb53-24-1.mis", 10, 10, true),
			new TestParameter("frb53-24-mis/frb53-24-2.mis", 10, 10, true),
			new TestParameter("frb53-24-mis/frb53-24-3.mis", 10, 10, true),
			new TestParameter("frb53-24-mis/frb53-24-4.mis", 10, 10, true),
			new TestParameter("frb53-24-mis/frb53-24-5.mis", 10, 10, true),
			new TestParameter("frb56-25-mis/frb56-25-1.mis", 10, 10, true),
			new TestParameter("frb56-25-mis/frb56-25-2.mis", 10, 10, true),
			new TestParameter("frb56-25-mis/frb56-25-3.mis", 10, 10, true),
			new TestParameter("frb56-25-mis/frb56-25-4.mis", 10, 10, true),
			new TestParameter("frb56-25-mis/frb56-25-5.mis", 10, 10, true),
			new TestParameter("frb59-26-mis/frb59-26-1.mis", 10, 10, true),
			new TestParameter("frb59-26-mis/frb59-26-2.mis", 10, 10, true),
			new TestParameter("frb59-26-mis/frb59-26-3.mis", 10, 10, true),
			new TestParameter("frb59-26-mis/frb59-26-4.mis", 10, 10, true),
			new TestParameter("frb59-26-mis/frb59-26-5.mis", 10, 10, true), };

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
