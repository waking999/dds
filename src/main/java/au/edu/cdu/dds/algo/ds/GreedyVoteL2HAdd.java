package au.edu.cdu.dds.algo.ds;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import au.edu.cdu.dds.util.AlgoUtil;
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
	public void setKR(int k, int r) {

	}

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
							AlgoUtil.addVerToGVI(gv, gvi, uIdx);
						}

						// add vIdx to gvi;
						if (Util.findPos(gviIdxLst, currentVCount, vIdx) == ConstantValue.IMPOSSIBLE_VALUE) {
							AlgoUtil.addVerToGVI(gv, gvi, vIdx);
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

						AlgoUtil.addVerToGVI(gv, gvi, vIdx);
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
