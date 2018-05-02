package au.edu.cdu.problems.dds;

import au.edu.cdu.common.util.AlgoUtil;
import au.edu.cdu.common.util.ConstantValue;
import au.edu.cdu.common.util.GlobalVariable;

/**
 * this class implements the greedy vote algorithm for dominating set, where the
 * order of weight is listed from highest to lowest
 * 
 * @author kwang
 */
class GreedyVoteH2L implements IAlgorithm {
	private GlobalVariable g; // representing the graph

	GreedyVoteH2L() {
	}

	@Override
	public void setKR(int k, int r) {
		// this greedy does not need such parameters
	}

	@Override
	public void setGlobalVariable(GlobalVariable g) {
		this.g = g;
	}

	@Override
	public void compute() {
		int[] idxSol = g.getIdxSol();
		int idxSolSize = g.getIdxSolSize();

		do {
			// get a vertex with highest weight
			int uIdx = AlgoUtil.getHighestWeightVertexIdx(g);

			if (uIdx != ConstantValue.IMPOSSIBLE_VALUE) {
				// if such a vertex's index is valid

				// add uIdx into solution
				idxSol[idxSolSize++] = uIdx;

				// adjust weight;
				AlgoUtil.adjustWeight(g, uIdx);

			}

		} while (!AlgoUtil.isAllDominated(g));

		g.setIdxSol(idxSol);
		g.setIdxSolSize(idxSolSize);
	}

}
