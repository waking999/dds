package au.edu.cdu.dds.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * an util class for common functions
 */
public class Util {

	/**
	 * get the index of a vertex
	 * 
	 * @param gv
	 * @param vertex
	 * @return
	 */
	public static int getIndexByVertex(GlobalVariable<String> gv, String vertex) {
		int actVerCnt = gv.getActVerCnt();
		String[] verLst = gv.getVerLst();
		int[] idxLst = gv.getIdxLst();

		for (int i = 0; i < actVerCnt; i++) {
			if (vertex.equals(verLst[i])) {
				return idxLst[i];
			}
		}
		return ConstantValue.IMPOSSIBLE_VALUE;
	}

	/**
	 * convert the idx solution into lable solution
	 * 
	 * @param gv
	 * @return
	 */
	public static String[] getVertexSolution(GlobalVariable<String> gv) {
		int idxSolSize = gv.getIdxSolSize();
		String[] sol = new String[idxSolSize];
		for (int i = 0; i < idxSolSize; i++) {
			String v = gv.getVerLst()[gv.getIdxSol()[i]];
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
	public static void initGlobalVariable(GlobalVariable<String> gv, int vCount) {
		String[] verLst = new String[vCount];
		Arrays.fill(verLst, null);
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
	public static void initWeight(GlobalVariable<String> gv) {
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
	public static void adjustWeight(GlobalVariable<String> gv, int uIdx) {
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
	public static boolean isAllDominated(GlobalVariable<String> gv) {

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
	public static int getHighestWeightVertexIdx(GlobalVariable<String> gv) {
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
	 * get a vertex with the lowest weight among undominated vertices
	 * 
	 * @param gv
	 * @return
	 */
	public static int getUndomedLowestWeightVertexIdx(GlobalVariable<String> gv) {
		int actVerCount = gv.getActVerCnt();
		int[] idxLst = gv.getIdxLst();
		boolean[] idxDomed = gv.getIdxDomed();

		float[] idxWeight = gv.getIdxWeight();

		float minWeight = Float.MAX_VALUE;

		int retIdx = 0;

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
	public static int getHighestWeightNeighIdx(GlobalVariable<String> gv, int vIdx) {
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
	public static <VT> boolean isValidSolution(GlobalVariable<VT> gv) {

		int[] idxLst = gv.getIdxLst();
		List<Integer> idxLstList = Arrays.stream(idxLst).boxed().collect(Collectors.toList());

		int[] idxSol = gv.getIdxSol();
		int idxSolSize = gv.getIdxSolSize();
		int[][] idxAL = gv.getIdxAL();

		for (int i = 0; i < idxSolSize; i++) {
			int vIdx = idxSol[i];
			int[] vNeigs = idxAL[vIdx];
			for (int u : vNeigs) {
				idxLstList.remove(new Integer(u));
			}
			idxLstList.remove(new Integer(vIdx));
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
		int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h
														 // format
		int min = calendar.get(Calendar.MINUTE);

		StringBuffer sb = new StringBuffer();
		String monthStr = String.format("%02d", month + 1);
		String dayStr = String.format("%02d", day);

		sb.append(year).append(monthStr).append(dayStr).append("-").append(hour).append(min);
		return sb.toString();

	}

}