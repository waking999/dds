package au.edu.cdu.dds.algo.ds;

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
public class GreedyVoteL2HAdd implements IAlgorithm<String> {
	GlobalVariable<String> gv;
	GlobalVariable<String> gvi;
	int k;

	@Override
	public void setGV(GlobalVariable<String> gv) {
		this.gv = gv;
		this.gvi = new GlobalVariable<String>();
		int verCnt = gv.getVerCnt();

		Util.initGlobalVariable(gvi, verCnt);
		k = 5;
	}

	public GreedyVoteL2HAdd() {

	}

	public void compute() {
		int[] idxSol = gv.getIdxSol();
		int idxSolSize = gv.getIdxSolSize();
		boolean[] idxDomed = gv.getIdxDomed();

		@SuppressWarnings("unchecked")
		Set<Integer>[] step = new HashSet[k];
		String[] gvVerLst = gv.getVerLst();

		int currentVCount = 0;
		String[] gviVerLst = gvi.getVerLst();
		int[] gviIdxLst = gvi.getIdxLst();
		int p = 0;
		do {

			int vIdx = Util.getUndomedLowestWeightVertexIdx(gv);

			if (vIdx != ConstantValue.IMPOSSIBLE_VALUE) {
				if (!idxDomed[vIdx]) {

					int uIdx = Util.getHighestWeightNeighIdx(gv, vIdx);
					if (uIdx != ConstantValue.IMPOSSIBLE_VALUE) {
						// add uIdx to gvi;
						// add vIdx to gvi;
						// if this vertex is not in the list, add it to vertex list
						if (Util.findPos(gviIdxLst, currentVCount, uIdx) == ConstantValue.IMPOSSIBLE_VALUE) {

							gviVerLst[currentVCount] = gvVerLst[uIdx];
							gviIdxLst[currentVCount] = uIdx;
							currentVCount++;
						}
						if (Util.findPos(gviIdxLst, currentVCount, vIdx) == ConstantValue.IMPOSSIBLE_VALUE) {
							gviVerLst[currentVCount] = gvVerLst[vIdx];
							gviIdxLst[currentVCount] = vIdx;
							currentVCount++;
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
						gviVerLst[currentVCount] = gvVerLst[vIdx];
						gviIdxLst[currentVCount] = vIdx;
						currentVCount++;
					}

					idxDomed[vIdx] = true;

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
