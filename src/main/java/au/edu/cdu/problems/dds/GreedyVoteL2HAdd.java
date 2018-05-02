package au.edu.cdu.problems.dds;

import java.util.HashSet;
import java.util.Set;

import au.edu.cdu.common.util.AlgoUtil;
import au.edu.cdu.common.util.ConstantValue;
import au.edu.cdu.common.util.GlobalVariable;
import au.edu.cdu.common.util.Util;

/**
 ** this class implements the greedy vote algorithm for dominating set, where the
 * order of weight is listed from lowest to highest. during the period, a new
 * graph starting from empty to the final graph step by step of adding vertices.
 * This is used as the basic framework for fpt subroutine greedy algorithm,
 * although the gi does not get involved in the final real result
 * 
 * @author kwang
 */
public class GreedyVoteL2HAdd implements IAlgorithm {
	private GlobalVariable g; // representing the original graph
	private GlobalVariable gi;
	int k;
	int r;

	GreedyVoteL2HAdd() {
	}

	@Override
	public void setKR(int k, int r) {
	}

	@Override
	public void setGlobalVariable(GlobalVariable gv) {
		this.g = gv;

		// initialize the graph representing at each round
		this.gi = new GlobalVariable();
		int verCnt = gv.getVerCnt();
		AlgoUtil.initGlobalVariable(gi, verCnt);

		// although most internal variables of gi are of the same value as g,
		// some of gi
		// is of own values
		AlgoUtil.adjustGIInitStatus(gi);

		k = 5;
	}

	@Override
	public void compute() {
		int[] idxSol = g.getIdxSol();
		int idxSolSize = g.getIdxSolSize();
		boolean[] idxDomed = g.getIdxDomed();

		@SuppressWarnings("unchecked")
		Set<Integer>[] step = new HashSet[k];

		int currentVCount = 0;
		int[] gviIdxLst = gi.getIdxLst();

		int p = 0;
		do {

			int vIdx = AlgoUtil.getUnaddedLowestWeightVertexIdx(g);

			if (vIdx != ConstantValue.IMPOSSIBLE_VALUE) {
				if (!idxDomed[vIdx]) {

					int uIdx = AlgoUtil.getHighestWeightNeighIdx(g, vIdx);
					if (uIdx != ConstantValue.IMPOSSIBLE_VALUE) {
						// if this vertex is not in the list, add it to vertex
						// list
						// add uIdx to gvi;
						if (Util.findPos(gviIdxLst, currentVCount, uIdx) == ConstantValue.IMPOSSIBLE_VALUE) {
							AlgoUtil.addVerToGI(g, gi, uIdx);
						}

						// add vIdx to gvi;
						if (Util.findPos(gviIdxLst, currentVCount, vIdx) == ConstantValue.IMPOSSIBLE_VALUE) {
							AlgoUtil.addVerToGI(g, gi, vIdx);
						}

						// add uIdx into solution
						idxSol[idxSolSize++] = uIdx;

						// adjust weight;
						AlgoUtil.adjustWeight(g, uIdx);
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

						AlgoUtil.addVerToGI(g, gi, vIdx);
					}

					// add vIdx to step[p]
					Set<Integer> tmpStepList = step[p - 1];
					tmpStepList.add(vIdx);

				}

			}

		} while (!AlgoUtil.isAllDominated(g));

		g.setIdxSol(idxSol);
		g.setIdxSolSize(idxSolSize);
	}

}
