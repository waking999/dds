package au.edu.cdu.problems.dds;

import au.edu.cdu.common.util.AlgoUtil;
import au.edu.cdu.common.util.ConstantValue;
import au.edu.cdu.common.util.GlobalVariable;
import au.edu.cdu.problems.IAlgorithm;

/**
 * this class implements the greedy vote algorithm for dominating set, where the
 * order of weight is listed from lowest to highest
 * 
 * @author kwang
 */
class GreedyVoteL2H implements IAlgorithm {
	private GlobalVariable g; // representing the graph

	GreedyVoteL2H() {
	}

	@Override
	public void setKRM(int k, int r,int momentRegretThreshold) {
		// this greedy does not need such parameters
	}

	@Override
	public void setGlobalVariable(GlobalVariable g) {
		this.g = g;
	}
	@Override
	public GlobalVariable getGlobalVariable( ){
		return this.g ;
	}
	@Override
	public void compute() {
		int[] idxSol = g.getIdxSol();
		int idxSolSize = g.getIdxSolSize();

		do {
			// get a vertex with the lowest weight;
			int vIdx = AlgoUtil.getUndomedLowestWeightVertexIdx(g);
			// get a neigh of the vertex with the highest weight
			int uIdx = AlgoUtil.getHighestWeightNeighIdx(g, vIdx);
			if (uIdx != ConstantValue.IMPOSSIBLE_VALUE) {
				// if such a vertex is valid
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
