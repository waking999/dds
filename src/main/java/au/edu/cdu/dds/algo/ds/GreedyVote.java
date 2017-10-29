package au.edu.cdu.dds.algo.ds;

import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;
import au.edu.cdu.dds.util.Util;

public class GreedyVote implements IAlgorithm {
	GlobalVariable<String> gv;

	public GreedyVote(GlobalVariable<String> gv) {
		this.gv = gv;
	}

	public void compute() {
		int[] idxSol = gv.getIdxSol();
		int idxSolSize = gv.getIdxSolSize();

		do {

			int vIdx = Util.getLowestVoteVertexIdx(gv);
			int uIdx = Util.getHighestWeightNeighIdx(gv, vIdx);
			if (uIdx != ConstantValue.IMPOSSIBLE_VALUE) {
				idxSol[idxSolSize++] = uIdx;
				Util.adjustWeight(gv, uIdx);
			}

		} while (!Util.isAllDominated(gv));
		
		gv.setIdxSol(idxSol);
		gv.setIdxSolSize(idxSolSize);
	}

	



}
