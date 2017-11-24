package au.edu.cdu.dds.algo.ds;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import au.edu.cdu.dds.util.AlgoUtil;
import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.ISGlobalVariable;
import au.edu.cdu.dds.util.Util;

public class DomAVC {
	
	/**
	 * the reduction rule 1 of dds to dom-a-vc:
	 * if a vertex v belongs to d, delete v
	 * @param gS, graph g*
	 * @param d1
	 */
	public static void ddsR1(ISGlobalVariable gS, int[] d1) {
		for (int d1VIdx : d1) {
			AlgoUtil.deleteVertex(gS, d1VIdx);
		}
	}

	/**
	 * the reduction rule 2 of dds to dom-a-vc:
	 * if a vertex v belongs to b and v's neigh not in c, delete v from b
	 * 
	 * @param gS, grpah g*
	 * @param c
	 * @param b
	 * @return
	 */
	public static int[] ddsR2(ISGlobalVariable gS, int[] c, int[] b) {
		int[][] gSIdxAL = gS.getIdxAL();
		int[] gSIdxDegree = gS.getIdxDegree();

		int cLen = c.length;
		int bLen = b.length;

		int[] bRem = new int[bLen];
		Arrays.fill(bRem, ConstantValue.IMPOSSIBLE_VALUE);
		int bRemLen = 0;
		for (int i = 0; i < bLen; i++) {
			int bVIdx = b[i];
			int[] bVNeigs = gSIdxAL[bVIdx];
			int bVIdxDegree = gSIdxDegree[bVIdx];
			boolean isIntersected = Util.setsIntersect(bVNeigs, bVIdxDegree, c, cLen);
			if (!isIntersected) {
				bRem[bRemLen++] = bVIdx;
				AlgoUtil.deleteVertex(gS, bVIdx);
			}

		}
		// remove the deleted vertices from b
		b = Util.set1Minus2(b, bLen, bRem, bRemLen);
		return b;
	}

	
	/**
	 * reduction rule 3 of dds to dom-a-vc:
	 * if edge (u,v) belongs to b, delete edge (u,v)
	 * @param gS, graph g*
	 * @param b
	 */
	public static void ddsR3(ISGlobalVariable gS, int[] b) {
		int[][] gSIdxAL = gS.getIdxAL();
		int[] gSIdxDegree = gS.getIdxDegree();
		int bLen = b.length;

		for (int i = 0; i < bLen; i++) {
			int bVIdx = b[i];
			int bVIdxDegree = gSIdxDegree[bVIdx];
			int[] bVNeigs = gSIdxAL[bVIdx];
			for (int j = bVIdxDegree - 1; j >= 0; j--) {
				int bVNeigIdx = bVNeigs[j];
				AlgoUtil.deleteEdge(gS, bVIdx, bVNeigIdx);
			}

		}
	}

	
	/**
	 * get neighbor types
	 * 
	 * @param gSIdxAL
	 * @param c
	 * @param b
	 * @param neigTypeDomedMap
	 * @param neigTypeDomingMap
	 */
	public static void getNeighType(int[][] gSIdxAL, int[] c, int[] b, Map<String, boolean[]> neigTypeDomedMap,
			Map<String, Set<Integer>> neigTypeDomingMap) {
		int cLen = c.length;
		/*
		 * because the vertices in the vertex cover can
		 * dominate other vertices in the
		 * vertex cover,they are considered
		 */
		for (int cVIdx : c) {

			int[] cVNeigs = gSIdxAL[cVIdx];

			boolean ruler[] = new boolean[cLen];
			Arrays.fill(ruler, ConstantValue.UNMARKED);

			// the vertex can dominated by itself
			int pos = Util.findPos(c, cLen, cVIdx);
			ruler[pos] = ConstantValue.MARKED;
			for (int cVNeigIdx : cVNeigs) {
				/*
				 * the position of the dominated vertex in
				 * the vertex cover will set 1
				 * a neighbour of the vertex is likely not
				 * to be in the vertex cover
				 */
				pos = Util.findPos(c, cLen, cVNeigIdx);
				if (pos != ConstantValue.IMPOSSIBLE_VALUE) {
					ruler[pos] = ConstantValue.MARKED;
				}
			}

			String rulerStr = Util.arrayToString(ruler);
			if (!neigTypeDomedMap.containsKey(rulerStr)) {

				neigTypeDomedMap.put(rulerStr, ruler);
				Set<Integer> domingVerSet = new HashSet<>();
				domingVerSet.add(cVIdx);
				neigTypeDomingMap.put(rulerStr, domingVerSet);
			} else {
				Set<Integer> domingVerSet = neigTypeDomingMap.get(rulerStr);
				domingVerSet.add(cVIdx);
				neigTypeDomingMap.put(rulerStr, domingVerSet);
			}
		}

		/*
		 * because the vertices in the independent set can
		 * dominate vertices in the
		 * vertex cover,they are considered
		 */
		for (int bVIdx : b) {
			int[] bVNeigs = gSIdxAL[bVIdx];

			boolean ruler[] = new boolean[cLen];
			Arrays.fill(ruler, ConstantValue.UNMARKED);

			for (int bVNeigIdx : bVNeigs) {
				/*
				 * the position of the dominate vertex in
				 * the vertex cover will set 1
				 */
				int pos = Util.findPos(c, cLen, bVNeigIdx);
				if (pos != ConstantValue.IMPOSSIBLE_VALUE) {
					ruler[pos] = ConstantValue.MARKED;
				}
			}

			String rulerStr = Util.arrayToString(ruler);
			if (!neigTypeDomedMap.containsKey(rulerStr)) {

				neigTypeDomedMap.put(rulerStr, ruler);
				Set<Integer> domingVerSet = new HashSet<>();
				domingVerSet.add(bVIdx);
				neigTypeDomingMap.put(rulerStr, domingVerSet);
			} else {
				Set<Integer> domingVerSet = neigTypeDomingMap.get(rulerStr);
				domingVerSet.add(bVIdx);
				neigTypeDomingMap.put(rulerStr, domingVerSet);
			}

		}
	}
	
	/**
	 * the brute force method to get all combinations of r out of the neighbor type
	 * size
	 * @param neigTypeDomedMap
	 * @param r
	 * @return
	 */
	public static String[] domAVCBruteForce(Map<String, boolean[]> neigTypeDomedMap, int r) {
		String[] rulerKeyArr = Util.converRulerMapToArray(neigTypeDomedMap);
		List<String[]> allRCombins = Util.getAllRoutOfNCombines(rulerKeyArr, r);
		boolean hasValidCombin = false;
		String[] validCombin = null;
		for (String[] combin : allRCombins) {
			hasValidCombin = Util.validCombin(combin, neigTypeDomedMap);
			if (hasValidCombin) {
				validCombin = combin;
				break;
			}
		}
		return validCombin;
	}
}
