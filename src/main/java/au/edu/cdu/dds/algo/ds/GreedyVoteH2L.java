package au.edu.cdu.dds.algo.ds;

import au.edu.cdu.dds.util.AlgoUtil;
import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.ISGlobalVariable;

/**
 * this class implements the greedy vote algorithm for dominating set, where the
 * order of weight is listed from highest to lowest
 * 
 * @author kwang
 */
public class GreedyVoteH2L implements IAlgorithm {
	ISGlobalVariable g; // representing the graph

	public GreedyVoteH2L() {
	}

	@Override
	public void setKR(int k, int r) {
		// this greedy does not need such parameters
	}

	@Override
	public void setGlobalVariable(ISGlobalVariable g) {
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
