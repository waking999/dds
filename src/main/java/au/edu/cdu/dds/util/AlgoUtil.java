package au.edu.cdu.dds.util;

import java.util.Arrays;

import au.edu.cdu.dds.TestUtil;

public class AlgoUtil {
	/**
	 * add a vertex v to a new graph
	 * 
	 * @param gv,
	 *            the global variable of a source graph
	 * @param gvi,
	 *            the global variable of a graph to add vertex
	 * @param vIdx,
	 *            the index of v in gv
	 */
	public static void addVerToGVI(GlobalVariable sourceGV, GlobalVariable gvToAdd, int vIdx) {
		boolean[] idxAdded = sourceGV.getIdxAdded();
		if (idxAdded[vIdx]) {
			return;
		}
		int[][] idxAL = sourceGV.getIdxAL();
		int[] idxDegree = sourceGV.getIdxDegree();

		int[][] gviIdxAL = gvToAdd.getIdxAL();
		int[][] gviIdxIM = gvToAdd.getIdxIM();
		int[] gviIdxDegree = gvToAdd.getIdxDegree();
		int[] gviVerLst = gvToAdd.getVerLst();
		int[] gviIdxLst = gvToAdd.getIdxLst();
		int gviVerCnt = gvToAdd.getVerCnt();

		// mark v is added to gvi
		idxAdded[vIdx] = true;
		// since we start index from 0, the current vertex count is also the position
		// index for the new vertex;
		int gviCurrVerCnt = gvToAdd.getVerCnt();
		// since gvi is a new graph, we use the index of gv as the vertex of gvi such
		// that we can keep the connection between gvi and gv (by gvi.verlst contains
		// the same meaning thing as gv.idxlst)
		gviVerLst[gviCurrVerCnt] = vIdx;
		gviIdxLst[gviCurrVerCnt] = gviCurrVerCnt;

		// increase the the number of current vertex;
		gviCurrVerCnt++;
		gvToAdd.setActVerCnt(gviCurrVerCnt);
		gvToAdd.setVerCnt(gviCurrVerCnt);

		// get the new index of v in gvi
		int gviVIdx = AlgoUtil.getIndexByVertex(gvToAdd, vIdx);

		// the neighbor count of v in gv
		int vGVNeigCnt = idxDegree[vIdx];
		// prepare the same-size neighbor array of v in gvi;
		gviIdxAL[gviVIdx] = new int[vGVNeigCnt];
		Arrays.fill(gviIdxAL[gviVIdx], ConstantValue.IMPOSSIBLE_VALUE);

		// get the neighbours of v in gv
		int[] vGVNeighs = idxAL[vIdx];

		for (int uIdx : vGVNeighs) {
			// for any neighbour u of v in gv
			if (Util.findPos(gviVerLst, gviVerCnt, uIdx) != ConstantValue.IMPOSSIBLE_VALUE) {
				// if u in gvi
				// get the index of u in gvi
				int gviUIdx = AlgoUtil.getIndexByVertex(gvToAdd, uIdx);

				// set the al of gviUIdx, gviVIdx
				int gviUIdxDegree = gviIdxDegree[gviUIdx];
				gviIdxAL[gviUIdx][gviUIdxDegree] = gviVIdx;
				int gviVIdxDegree = gviIdxDegree[gviVIdx];
				gviIdxAL[gviVIdx][gviVIdxDegree] = gviUIdx;

				// set the im of gviUIdx, gviVIdx
				gviIdxIM[gviVIdx][gviUIdx] = gviUIdxDegree;
				gviIdxIM[gviUIdx][gviVIdx] = gviVIdxDegree;

				// set the degree of gviUIdx, gviVIdx;
				gviIdxDegree[gviUIdx]++;
				gviIdxDegree[gviVIdx]++;

			}
		}
		// copy degree info into util
		int[] gviIdxUtil = Arrays.copyOf(gviIdxDegree, sourceGV.getVerCnt());
		gvToAdd.setIdxUtil(gviIdxUtil);

		// TestUtil.printGlobalVariableStatus(gvi);
	}

	public static void deleteVertex(GlobalVariable gv, int vIdx) {
		int gvActVerCnt = gv.getActVerCnt();
		int[] gvVerLst = gv.getVerLst();
		int[] gvIdxLst = gv.getIdxLst();
		int[][] gvIdxAL = gv.getIdxAL();

		int[] gvIdxDegre = gv.getIdxDegree();
		// get the pos of vIdx in idxLst
		int vIdxPos = Util.findPos(gvIdxLst, gvActVerCnt, vIdx);
		if (vIdxPos == ConstantValue.IMPOSSIBLE_VALUE) {
			return;
		}

		int d = gvIdxDegre[vIdx];

		// swap v with the bottom of verLst
		int lastVer = gvVerLst[gvActVerCnt - 1];
		gvVerLst[gvActVerCnt - 1] = gvVerLst[vIdxPos];
		gvVerLst[vIdxPos] = lastVer;

		// swap vIdx with the bottom of idxLst
		int lastIdx = gvIdxLst[gvActVerCnt - 1];
		gvIdxLst[gvActVerCnt - 1] = gvIdxLst[vIdxPos];
		gvIdxLst[vIdxPos] = lastIdx;

		for (int j = d - 1; j >= 0; j--) {
			int uIdx = gvIdxAL[vIdx][j];
			deleteEdge(gv, uIdx, vIdx);
		}

		gv.setActVerCnt(gvActVerCnt - 1);

		// copy degree info into util
		int[] gvIdxUtil = Arrays.copyOf(gvIdxDegre, gv.getVerCnt());
		gv.setIdxUtil(gvIdxUtil);

		TestUtil.printGlobalVariableStatus(gv);

	}

	/**
	 * delete the edge between u and v in a graph
	 * 
	 * @param gv,
	 *            the global variable of the graph
	 * @param uIdx,
	 *            index of one end vertex
	 * @param vIdx,
	 *            index of another end vertex
	 */
	public static void deleteEdge(GlobalVariable gv, int uIdx, int vIdx) {
		deleteVFromU(gv, uIdx, vIdx);
		deleteVFromU(gv, vIdx, uIdx);
		// copy degree info into util
		int[] gvIdxUtil = Arrays.copyOf(gv.getIdxDegree(), gv.getVerCnt());
		gv.setIdxUtil(gvIdxUtil);
		TestUtil.printGlobalVariableStatus(gv);
	}

	/**
	 * delete the edge between u and v in a graph, where is from the u side
	 * 
	 * @param gv,
	 *            the global variable of the graph
	 * @param uIdx,
	 *            index of one end vertex
	 * @param vIdx,
	 *            index of another end vertex
	 */
	private static void deleteVFromU(GlobalVariable gv, int uIdx, int vIdx) {

		int[] gvIdxDegree = gv.getIdxDegree();
		int[][] gvIdxIM = gv.getIdxIM();
		int[][] gvIdxAL = gv.getIdxAL();

		// delete from u side
		int uIdxDegree = gvIdxDegree[uIdx];
		int vIdxIMPosOfUIdx = gvIdxIM[vIdx][uIdx];
		int uLastNeigIdx = gvIdxAL[uIdx][uIdxDegree - 1];
		// swap vIdx to the bottom of the neigh in idxAL
		gvIdxAL[uIdx][uIdxDegree - 1] = vIdx;
		gvIdxAL[uIdx][vIdxIMPosOfUIdx] = uLastNeigIdx;
		// swap vIdx's position for neigh in IdxIM
		gvIdxIM[vIdx][uIdx] = uIdxDegree - 1;
		gvIdxIM[uLastNeigIdx][uIdx] = vIdxIMPosOfUIdx;
		// decrement the degree of u
		gvIdxDegree[uIdx]--;
	}

	public static GlobalVariable copyGloablVariable(GlobalVariable gv) {
		GlobalVariable gvStar = new GlobalVariable();

		gvStar.setActVerCnt(gv.getActVerCnt());
		gvStar.setVerCnt(gv.getVerCnt());
		gvStar.setUndomCnt(gv.getUndomCnt());
		// verLstt
		int[] verLst = gv.getVerLst();
		gvStar.setVerLst(Arrays.copyOf(verLst, verLst.length));
		// idxLst
		int[] idxLst = gv.getIdxLst();
		gvStar.setIdxLst(Arrays.copyOf(idxLst, idxLst.length));
		// degree
		int[] idxDegree = gv.getIdxDegree();
		gvStar.setIdxDegree(Arrays.copyOf(idxDegree, idxDegree.length));
		// util
		int[] idxUtil = gv.getIdxUtil();
		gvStar.setIdxUtil(Arrays.copyOf(idxUtil, idxUtil.length));
		// domed
		boolean[] idxDomed = gv.getIdxDomed();
		gvStar.setIdxDomed(Arrays.copyOf(idxDomed, idxDomed.length));
		// added
		boolean[] idxAdded = gv.getIdxAdded();
		gvStar.setIdxAdded(Arrays.copyOf(idxAdded, idxAdded.length));
		// sol, solsize
		int[] idxSol = gv.getIdxSol();
		gvStar.setIdxSol(Arrays.copyOf(idxSol, idxSol.length));
		gvStar.setIdxSolSize(gv.getIdxSolSize());
		// vote
		float[] idxVote = gv.getIdxVote();
		gvStar.setIdxVote(Arrays.copyOf(idxVote, idxVote.length));
		// weight
		float[] idxWeight = gv.getIdxWeight();
		gvStar.setIdxWeight(Arrays.copyOf(idxWeight, idxWeight.length));
		// IM
		int[][] idxIM = gv.getIdxIM();
		int idxIMLen = idxIM.length;
		int[][] gvStarIdxIM = new int[idxIMLen][];
		for (int i = 0; i < idxIMLen; i++) {
			int[] idxIMRow = idxIM[i];
			gvStarIdxIM[i] = Arrays.copyOf(idxIMRow, idxIMRow.length);
		}
		gvStar.setIdxIM(gvStarIdxIM);

		// AL
		int[][] idxAL = gv.getIdxAL();
		int idxALLen = idxAL.length;
		int[][] gvStarIdxAL = new int[idxALLen][];
		for (int i = 0; i < idxALLen; i++) {
			int[] idxALRow = idxAL[i];
			if (idxALRow != null) {
				gvStarIdxAL[i] = Arrays.copyOf(idxALRow, idxALRow.length);
			} else {
				break;
			}
		}
		gvStar.setIdxAL(gvStarIdxAL);

		return gvStar;
	}

	/**
	 * get the index of a vertex
	 * 
	 * @param gv
	 * @param vertex
	 * @return
	 */
	public static int getIndexByVertex(GlobalVariable gv, int vertex) {
		int actVerCnt = gv.getActVerCnt();
		int[] verLst = gv.getVerLst();
		int[] idxLst = gv.getIdxLst();

		for (int i = 0; i < actVerCnt; i++) {
			if (vertex == verLst[i]) {
				return idxLst[i];
			}
		}
		return ConstantValue.IMPOSSIBLE_VALUE;
	}

}
