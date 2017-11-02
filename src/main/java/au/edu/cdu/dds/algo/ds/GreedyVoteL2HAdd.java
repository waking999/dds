package au.edu.cdu.dds.algo.ds;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;
import au.edu.cdu.dds.util.Util;

/**
 ** this class implements the greedy vote algorithm for dominating set, where the
 * order of weight is listed from lowest to highest. during the period, a new
 * graph starting from empty to the final graph step by step of adding vertices.
 * This is used as the basic framework for fptI
 * 
 * @author kwang
 */
public class GreedyVoteL2HAdd implements IAlgorithm {
	GlobalVariable gv;
	GlobalVariable gvi;
	int k;

	@Override
	public void setGV(GlobalVariable gv) {
		this.gv = gv;
		this.gvi = new GlobalVariable();
		int verCnt = gv.getVerCnt();

		Util.initGlobalVariable(gvi, verCnt);
		adjustGVIInitStatus();

		k = 5;
	}

	private void adjustGVIInitStatus() {
		gvi.setActVerCnt(0);
		gvi.setVerCnt(0);
		int[] gviIdxLst = gvi.getIdxLst();
		Arrays.fill(gviIdxLst, ConstantValue.IMPOSSIBLE_VALUE);

		int[] gviIdxDegree = gvi.getIdxDegree();
		int[] gviIdxUtil = Arrays.copyOf(gviIdxDegree, gv.getVerCnt());
		gvi.setIdxUtil(gviIdxUtil);

	}

	public GreedyVoteL2HAdd() {

	}

	/**
	 * add a vertex v to gvi
	 * 
	 * @param gv
	 * @param gvi
	 * @param vIdx,
	 *            the index of v in gv
	 */
	private void addVerToGVI(GlobalVariable gv, GlobalVariable gvi, int vIdx) {
		boolean[] idxAdded = gv.getIdxAdded();
		if (idxAdded[vIdx]) {
			return;
		}
		int[][] idxAL = gv.getIdxAL();
		int[] idxDegree = gv.getIdxDegree();

		int[][] gviIdxAL = gvi.getIdxAL();
		int[][] gviIdxIM = gvi.getIdxIM();
		int[] gviIdxDegree = gvi.getIdxDegree();
		int[] gviVerLst = gvi.getVerLst();
		int[] gviIdxLst = gvi.getIdxLst();
		int gviVerCnt = gvi.getVerCnt();

		// mark v is added to gvi
		idxAdded[vIdx] = true;
		// since we start index from 0, the current vertex count is also the position
		// index for the new vertex;
		int gviCurrVerCnt = gvi.getVerCnt();
		// since gvi is a new graph, we use the index of gv as the vertex of gvi such
		// that we can keep the connection between gvi and gv (by gvi.verlst contains
		// the same meaning thing as gv.idxlst)
		gviVerLst[gviCurrVerCnt] = vIdx;
		gviIdxLst[gviCurrVerCnt] = gviCurrVerCnt;

		// increase the the number of current vertex;
		gviCurrVerCnt++;
		gvi.setActVerCnt(gviCurrVerCnt);
		gvi.setVerCnt(gviCurrVerCnt);

		// get the new index of v in gvi
		int gviVIdx = Util.getIndexByVertex(gvi, vIdx);

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
				int gviUIdx = Util.getIndexByVertex(gvi, uIdx);

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
		int[] gviIdxUtil = Arrays.copyOf(gviIdxDegree, gv.getVerCnt());
		gvi.setIdxUtil(gviIdxUtil);

		// TestUtil.printGlobalVariableStatus(gvi);
	}

	@SuppressWarnings("unchecked")
	public void compute() {
		int[] idxSol = gv.getIdxSol();
		int idxSolSize = gv.getIdxSolSize();
		boolean[] idxDomed = gv.getIdxDomed();

		Set<Integer>[] step = new HashSet[k];

		int currentVCount = 0;
		int[] gviIdxLst = gvi.getIdxLst();

		int p = 0;
		do {

			int vIdx = Util.getUnaddedLowestWeightVertexIdx(gv);

			if (vIdx != ConstantValue.IMPOSSIBLE_VALUE) {
				if (!idxDomed[vIdx]) {

					int uIdx = Util.getHighestWeightNeighIdx(gv, vIdx);
					if (uIdx != ConstantValue.IMPOSSIBLE_VALUE) {
						// if this vertex is not in the list, add it to vertex list
						// add uIdx to gvi;
						if (Util.findPos(gviIdxLst, currentVCount, uIdx) == ConstantValue.IMPOSSIBLE_VALUE) {
							addVerToGVI(gv, gvi, uIdx);
						}

						// add vIdx to gvi;
						if (Util.findPos(gviIdxLst, currentVCount, vIdx) == ConstantValue.IMPOSSIBLE_VALUE) {
							addVerToGVI(gv, gvi, vIdx);
						}

						// add uIdx into solution
						idxSol[idxSolSize++] = uIdx;

						// adjust weight;
						Util.adjustWeight(gv, uIdx);
						// store uIdx, vIdx to step[p+1];
						Set<Integer> tmpStepList = new HashSet<>();
						tmpStepList.add(uIdx);
						tmpStepList.add(vIdx);
						if (p >= k) {
							p = p % k;
						}
						step[p++] = tmpStepList;
					}

				} else {
					// add vIdx to gvi;
					if (Util.findPos(gviIdxLst, currentVCount, vIdx) == ConstantValue.IMPOSSIBLE_VALUE) {

						addVerToGVI(gv, gvi, vIdx);
					}

					// add vIdx to step[p]
					Set<Integer> tmpStepList = step[p - 1];
					tmpStepList.add(vIdx);

				}

			}

		} while (!Util.isAllDominated(gv));

		gv.setIdxSol(idxSol);
		gv.setIdxSolSize(idxSolSize);
	}

}
