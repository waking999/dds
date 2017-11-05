package au.edu.cdu.dds.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import au.edu.cdu.dds.io.DBOperation;
import au.edu.cdu.dds.io.DBParameter;

/**
 * an util class for common functions
 */
public class Util {

	/**
	 * convert the idx solution into lable solution
	 * 
	 * @param gv
	 * @return
	 */
	public static int[] getVertexSolution(GlobalVariable gv) {
		int idxSolSize = gv.getIdxSolSize();
		int[] sol = new int[idxSolSize];
		for (int i = 0; i < idxSolSize; i++) {
			int v = gv.getVerLst()[gv.getIdxSol()[i]];
			sol[i] = v;
		}

		return sol;
	}

	/**
	 * initialize the global variables with the number of vertex
	 * 
	 * @param gv
	 * @param vCount
	 */
	public static void initGlobalVariable(GlobalVariable gv, int vCount) {
		int[] verLst = new int[vCount];
		Arrays.fill(verLst, ConstantValue.IMPOSSIBLE_VALUE);
		// the index of each vertex is the sequence no. initially
		int[] idxLst = new int[vCount];
		for (int i = 0; i < vCount; i++) {
			idxLst[i] = i;
		}

		// the degree of each vertex is 0 initially
		int[] idxDegree = new int[vCount];
		Arrays.fill(idxDegree, 0);

		// the vote of each vertex is 0 initially
		float[] idxVote = new float[vCount];
		Arrays.fill(idxVote, 0);

		// the weight of each vertex is 0 initially
		float[] idxWeight = new float[vCount];
		Arrays.fill(idxWeight, 0);

		// the dominated status of each vertex is false initially
		boolean[] idxDomed = new boolean[vCount];
		Arrays.fill(idxDomed, false);
		int undomCnt = vCount;

		// the added status of each vertex is false initially
		boolean[] idxAdded = new boolean[vCount];
		Arrays.fill(idxAdded, false);

		// the incident matrix of each vertex is set to be impossible value initially
		int[][] idxIM = new int[vCount][vCount];
		for (int i = 0; i < vCount; i++) {
			Arrays.fill(idxIM[i], ConstantValue.IMPOSSIBLE_VALUE);
		}

		// the adjacent list
		int[][] idxAL = new int[vCount][];

		// the solution
		int[] idxSol = new int[vCount];
		Arrays.fill(idxSol, ConstantValue.IMPOSSIBLE_VALUE);
		int idxSolSize = 0;

		gv.setIdxSol(idxSol);
		gv.setIdxSolSize(idxSolSize);
		gv.setVerCnt(vCount);
		gv.setActVerCnt(vCount);
		gv.setIdxAL(idxAL);
		gv.setIdxDomed(idxDomed);
		gv.setIdxAdded(idxAdded);
		gv.setIdxIM(idxIM);
		gv.setIdxDegree(idxDegree);
		gv.setIdxLst(idxLst);
		gv.setVerLst(verLst);
		gv.setUndomCnt(undomCnt);
		gv.setIdxVote(idxVote);
		gv.setIdxWeight(idxWeight);
	}

	/**
	 * initialize the vote and weight after a graph loaded
	 * 
	 * @param gv
	 */
	public static void initWeight(GlobalVariable gv) {
		int actVerCnt = gv.getActVerCnt();
		int[] idxDegree = gv.getIdxDegree();
		float[] idxVote = gv.getIdxVote();
		float[] idxWeight = gv.getIdxWeight();
		int[][] idxAL = gv.getIdxAL();

		for (int i = 0; i < actVerCnt; i++) {
			int degree = idxDegree[i];
			float vote = 1.0f / (1 + degree);
			idxVote[i] = vote;
			idxWeight[i] = vote;
		}

		for (int i = 0; i < actVerCnt; i++) {
			int[] iIdxNeigh = idxAL[i];

			float iWeight = idxWeight[i];
			int iNeighCnt = idxDegree[i];

			for (int j = 0; j < iNeighCnt; j++) {
				int jIdx = iIdxNeigh[j];
				float jVote = idxVote[jIdx];
				iWeight += jVote;
			}
			idxWeight[i] = iWeight;
		}
	}

	/**
	 * adjust the weight of the graph after the domination status of some vertices
	 * change
	 * 
	 * @param gv
	 * @param uIdx
	 */
	public static void adjustWeight(GlobalVariable gv, int uIdx) {
		int[][] idxAL = gv.getIdxAL();
		int[] idxUtil = gv.getIdxUtil();
		int uUtil = idxUtil[uIdx];
		int[] uIdxNeigs = idxAL[uIdx];
		boolean[] idxDomed = gv.getIdxDomed();

		float[] idxWeight = gv.getIdxWeight();
		float[] idxVote = gv.getIdxVote();

		idxWeight[uIdx] = 0f;

		for (int i = 0; i < uUtil; i++) {
			int vIdx = uIdxNeigs[i];
			if (idxWeight[vIdx] - 0 > ConstantValue.FLOAT_NO_DIFF) {
				if (!idxDomed[uIdx]) {
					idxWeight[vIdx] -= idxVote[uIdx];
				}
				if (!idxDomed[vIdx]) {
					idxDomed[vIdx] = true;
					idxWeight[vIdx] -= idxVote[vIdx];

					int[] vIdxNeigs = idxAL[vIdx];
					int vUtil = idxUtil[vIdx];
					for (int j = 0; j < vUtil; j++) {
						int wIdx = vIdxNeigs[j];
						if (idxWeight[wIdx] - 0 > ConstantValue.FLOAT_NO_DIFF) {
							idxWeight[wIdx] -= idxVote[vIdx];
						}
					}
				}
			}
		}
		idxDomed[uIdx] = true;

	}

	/**
	 * find the position of a value in a range of an array
	 * 
	 * @param array
	 * @param arrayLen
	 * @param val
	 * @return
	 */
	public static int findPos(int[] array, int arrayLen, int val) {
		for (int i = 0; i < arrayLen; i++) {
			if (array[i] == val) {
				return i;
			}
		}
		return ConstantValue.IMPOSSIBLE_VALUE;
	}

	/**
	 * to check if all vertices are dominated
	 * 
	 * @param gv
	 * @return
	 */
	public static boolean isAllDominated(GlobalVariable gv) {

		int verCnt = gv.getVerCnt();
		for (int i = 0; i < verCnt; i++) {
			if (!gv.getIdxDomed()[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * get a vertex with the highest weight among the vertices
	 * 
	 * @param gv
	 * @return
	 */
	public static int getHighestWeightVertexIdx(GlobalVariable gv) {
		int actVerCount = gv.getActVerCnt();
		int[] idxLst = gv.getIdxLst();

		float[] idxWeight = gv.getIdxWeight();

		float maxWeight = Float.MIN_VALUE;

		int retIdx = 0;

		for (int i = 0; i < actVerCount; i++) {
			int vIdx = idxLst[i];
			if ((idxWeight[vIdx] - 0 > ConstantValue.FLOAT_NO_DIFF)
					&& (idxWeight[vIdx] - maxWeight > ConstantValue.FLOAT_NO_DIFF)) {
				maxWeight = idxWeight[vIdx];
				retIdx = vIdx;
			}
		}

		if (Math.abs(maxWeight - 0f) <= ConstantValue.FLOAT_NO_DIFF) {
			return ConstantValue.IMPOSSIBLE_VALUE;
		}

		return retIdx;
	}

	/**
	 * get a vertex with the lowest weight among unadded vertices
	 * 
	 * @param gv
	 * @return
	 */
	public static int getUnaddedLowestWeightVertexIdx(GlobalVariable gv) {
		int actVerCount = gv.getActVerCnt();
		int[] idxLst = gv.getIdxLst();
		boolean[] idxAdded = gv.getIdxAdded();

		float[] idxWeight = gv.getIdxWeight();

		float minWeight = Float.MAX_VALUE;

		int retIdx = ConstantValue.IMPOSSIBLE_VALUE;

		for (int i = 0; i < actVerCount; i++) {
			int vIdx = idxLst[i];

			if ((!idxAdded[vIdx]) && (idxWeight[vIdx] >= 0) && (idxWeight[vIdx] < minWeight)) {
				minWeight = idxWeight[vIdx];
				retIdx = vIdx;
			}
		}

		return retIdx;
	}

	/**
	 * get a vertex with the lowest weight among undominated vertices
	 * 
	 * @param gv
	 * @return
	 */
	public static int getUndomedLowestWeightVertexIdx(GlobalVariable gv) {
		int actVerCount = gv.getActVerCnt();
		int[] idxLst = gv.getIdxLst();
		boolean[] idxDomed = gv.getIdxDomed();

		float[] idxWeight = gv.getIdxWeight();

		float minWeight = Float.MAX_VALUE;

		int retIdx = ConstantValue.IMPOSSIBLE_VALUE;

		for (int i = 0; i < actVerCount; i++) {
			int vIdx = idxLst[i];

			if ((!idxDomed[vIdx]) && (idxWeight[vIdx] - 0 > ConstantValue.FLOAT_NO_DIFF)
					&& (idxWeight[vIdx] - minWeight < ConstantValue.FLOAT_NO_DIFF)) {
				minWeight = idxWeight[vIdx];
				retIdx = vIdx;
			}
		}

		if (Math.abs(minWeight - 0f) <= ConstantValue.FLOAT_NO_DIFF) {
			return ConstantValue.IMPOSSIBLE_VALUE;
		}

		return retIdx;
	}

	/**
	 * get an neighbor with the highest weight of a vertex
	 * 
	 * @param gv
	 * @param vIdx
	 * @return
	 */
	public static int getHighestWeightNeighIdx(GlobalVariable gv, int vIdx) {
		int[][] idxAL = gv.getIdxAL();
		int[] idxUtil = gv.getIdxUtil();
		int vUtil = idxUtil[vIdx];
		int[] vIdxNeigs = idxAL[vIdx];
		float[] idxWeight = gv.getIdxWeight();
		float minWeight = idxWeight[vIdx];

		int retIdx = vIdx;
		for (int i = 0; i < vUtil; i++) {
			int uIdx = vIdxNeigs[i];
			if ((idxWeight[uIdx] - minWeight > ConstantValue.FLOAT_NO_DIFF)) {
				minWeight = idxWeight[uIdx];
				retIdx = uIdx;

			}
		}

		return retIdx;
	}

	/**
	 * to check if a solution is valid
	 * 
	 * @param gv,
	 *            global variables
	 * @return true if it is valid, otherwise false
	 */
	public static boolean isValidSolution(GlobalVariable gv) {

		int[] idxLst = gv.getIdxLst();
		List<Integer> idxLstList =   Arrays.stream(idxLst).boxed().collect(Collectors.toList());

		int[] idxSol = gv.getIdxSol();
		int idxSolSize = gv.getIdxSolSize();
		int[][] idxAL = gv.getIdxAL();

		for (int i = 0; i < idxSolSize; i++) {
			int vIdx = idxSol[i];
			int[] vNeigs = idxAL[vIdx];
			for (int uIdx : vNeigs) {
				idxLstList.remove(Integer.valueOf(uIdx));
			}
			idxLstList.remove(Integer.valueOf(vIdx));
		}

		return (idxLstList.size() == 0);
	}

	/**
	 * generate a batch num by date and time
	 * 
	 * @return
	 */
	public static String getBatchNum() {
		Date date = new Date(); // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new
															 // calendar
															 // instance
		calendar.setTime(date); // assigns calendar to given date
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		int min = calendar.get(Calendar.MINUTE);

		StringBuffer sb = new StringBuffer();
		String monthStr = String.format("%02d", month + 1);
		String dayStr = String.format("%02d", day);

		sb.append(year).append(monthStr).append(dayStr).append("-");
		if (hour < 10) {
			sb.append("0");
		}
		sb.append(hour);
		if (min < 10) {
			sb.append("0");
		}
		sb.append(min);

		return sb.toString();

	}

	/**
	 * clean algorithm tables
	 * 
	 * @param mode
	 * @param dataSetName
	 * @param dbp
	 */
	public static void cleanAlgoTables(String mode, String dataSetName, DBParameter dbp) {
		switch (mode) {
		case ConstantValue.CLN_MODE_DROP:
			cleanAlgoTableDrop(dataSetName);
			break;
		case ConstantValue.CLN_MODE_DEL:
			cleanAlgoTableDel(dataSetName, dbp);
			break;
		default:
			break;
		}
	}

	/**
	 * get the infomation of instances by data set such as id, code, path and so on
	 * 
	 * @param dataSetName
	 * @return
	 */
	public static List<Map<String, String>> getInstanceInfo(String dataSetName) {
		DBParameter dbpIn = new DBParameter();
		dbpIn.setTableName(ConstantValue.DB_VNAME_INS);
		String[] colNames = { ConstantValue.DB_COL_INS_ID, ConstantValue.DB_COL_INS_CODE, ConstantValue.DB_COL_INS_NAME,
				ConstantValue.DB_COL_DATASET_NAME, ConstantValue.DB_COL_DATASET_PATH_NAME,
				ConstantValue.DB_COL_INS_PATH_NAME };
		String[] colPairNames = { ConstantValue.DB_COL_DATASET_NAME, ConstantValue.DB_COL_TO_BE_TESTED };
		String[] colPairOperators = { "=", "=" };
		String[] colPairValues = { dataSetName, "1" };
		dbpIn.setColNames(colNames);
		dbpIn.setColPairNames(colPairNames);
		dbpIn.setColPairOperators(colPairOperators);
		dbpIn.setColPairValues(colPairValues);

		List<Map<String, String>> lst = DBOperation.executeQuery(dbpIn);
		dbpIn = null;
		return lst;
	}

	/**
	 * @param instanceCode
	 * @param id
	 * @return
	 */
	public static String getAlgorithmTableName(String instanceCode, String id) {
		String algTableName = ConstantValue.TBL_ALG_PREFIX + id + "_" + instanceCode;
		return algTableName;
	}

	/**
	 * drop algorithm tables
	 * 
	 * @param dataSetName
	 */
	private static void cleanAlgoTableDrop(String dataSetName) {
		List<Map<String, String>> lst = getInstanceInfo(dataSetName);
		for (Map<String, String> map : lst) {
			String instanceCode = map.get(ConstantValue.DB_COL_INS_CODE);
			String id = map.get(ConstantValue.DB_COL_INS_ID);
			String algTableName = getAlgorithmTableName(instanceCode, id);
			DBOperation.executeDrop(algTableName);
		}
	}

	/**
	 * delete data from algorithm tables
	 * 
	 * @param dataSetName
	 * @param dbp
	 */
	private static void cleanAlgoTableDel(String dataSetName, DBParameter dbp) {
		List<Map<String, String>> lst = getInstanceInfo(dataSetName);
		for (Map<String, String> map : lst) {
			String instanceCode = map.get(ConstantValue.DB_COL_INS_CODE);
			String id = map.get(ConstantValue.DB_COL_INS_ID);
			String algTableName = getAlgorithmTableName(instanceCode, id);
			DBOperation.executeDel(algTableName, dbp);
		}
	}

	/**
	 * a set s1 minus a set s2
	 * 
	 * @param s1,
	 *            a set
	 * @param s2,
	 *            a set
	 * @return the elements in set s1 not in s2
	 */
	public static int[] set1Minus2(int[] s1, int s1Len, int[] s2, int s2Len) {
		if (s1 == null) {
			return s1;
		}
		if (s2 == null) {
			return s1;
		}

		for (int i = 0; i < s2Len; i++) {

			int pos = findPos(s1, s1Len, s2[i]);
			if (pos != ConstantValue.IMPOSSIBLE_VALUE) {
				int tmp = s1[pos];
				s1[pos] = s1[s1Len - 1];
				s1[s1Len - 1] = tmp;
				s1Len--;
			}
		}

		return Arrays.copyOf(s1, s1Len);

	}

	/**
	 * a set s1 minus a set s2
	 * 
	 * @param s1,
	 *            a set
	 * @param s2,
	 *            a set
	 * @return the elements in set s1 not in s2
	 */
	public static int[] set1Minus2(int[] s1, int s1Len, Set<Integer> s2) {
		if (s1 == null) {
			return s1;
		}
		if (s2 == null) {
			return s1;
		}

		Iterator<Integer> s2It = s2.iterator();
		while (s2It.hasNext()) {
			int val = s2It.next().intValue();
			int pos = findPos(s1, s1Len, val);
			if (pos != ConstantValue.IMPOSSIBLE_VALUE) {
				int tmp = s1[pos];
				s1[pos] = s1[s1Len - 1];
				s1[s1Len - 1] = tmp;
				s1Len--;
			}
		}

		return Arrays.copyOf(s1, s1Len);

	}

	/**
	 * a set s1 minus a set s2
	 * 
	 * @param s1,
	 *            a set
	 * @param s2,
	 *            a set
	 * @return the elements in set s1 not in s2
	 */
	public static int[] set1Minus2(Set<Integer> s1, int[] s2, int s2Len) {
		if (s1 == null) {
			return null;
		}
		int s1Len = s1.size();
		int[] s1Arr = new int[s1Len];
		Iterator<Integer> s1It = s1.iterator();
		int pointer = 0;
		while (s1It.hasNext()) {
			s1Arr[pointer++] = s1It.next().intValue();
		}

		return set1Minus2(s1Arr, s1Len, s2, s2Len);

	}

	/**
	 * @param binary
	 * @return
	 */
	public static String arrayToString(byte[] binary) {
		int binarySize = binary.length;
		char[] chArray = new char[binarySize];
		for (int i = 0; i < binarySize; i++) {
			chArray[i] = (char) (binary[i] + ConstantValue.ASCII_0_SEQ_NO);
		}
		String rtn = new String(chArray);
		return rtn;
	}

	/**
	 * to check if the two sets are intersected
	 * 
	 * @param s1
	 * @param s1Len
	 * @param s2
	 * @param s2Len
	 * @return
	 */
	public static boolean setIntersect(int[] s1, int s1Len, int[] s2, int s2Len) {
		for (int i = 0; i < s1Len; i++) {
			int s1Val = s1[i];
			for (int j = 0; j < s2Len; j++) {
				int s2Val = s2[j];
				if (s1Val == s2Val) {
					return true;
				}
			}
		}
		return false;
	}
}