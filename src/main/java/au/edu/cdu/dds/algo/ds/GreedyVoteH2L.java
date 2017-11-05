package au.edu.cdu.dds.algo.ds;

import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;
import au.edu.cdu.dds.util.Util;

/**
 * this class implements the greedy vote algorithm for dominating set, where the
 * order of weight is listed from highest to lowest
 * 
 * @author kwang
 */
public class GreedyVoteH2L implements IAlgorithm {
	GlobalVariable gv;

	@Override
	public void setKR(int k, int r) {

	}

	@Override
	public void setGV(GlobalVariable gv) {
		this.gv = gv;
	}

	public GreedyVoteH2L() {
	}

	public void compute() {
		int[] idxSol = gv.getIdxSol();
		int idxSolSize = gv.getIdxSolSize();

		do {
			// get a vertex with highest weight
			int uIdx = Util.getHighestWeightVertexIdx(gv);

			if (uIdx != ConstantValue.IMPOSSIBLE_VALUE) {
				// if such a vertex's index is valid

				// add uIdx into solution
				idxSol[idxSolSize++] = uIdx;

				// adjust weight;
				Util.adjustWeight(gv, uIdx);

			}

		} while (!Util.isAllDominated(gv));

		gv.setIdxSol(idxSol);
		gv.setIdxSolSize(idxSolSize);
	}

}
