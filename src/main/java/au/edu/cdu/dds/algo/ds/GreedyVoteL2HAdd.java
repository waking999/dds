package au.edu.cdu.dds.algo.ds;

import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;
import au.edu.cdu.dds.util.Util;

public class GreedyVoteL2HAdd implements IAlgorithm {
	GlobalVariable<String> gv;

	public GreedyVoteL2HAdd(GlobalVariable<String> gv) {
		this.gv = gv;
	}

	public void compute() {
		int[] idxSol = gv.getIdxSol();
		int idxSolSize = gv.getIdxSolSize();

		do {

			int vIdx = Util.getLowestWeightVertexIdx(gv);
			int uIdx = Util.getHighestWeightNeighIdx(gv, vIdx); 
			if (uIdx != ConstantValue.IMPOSSIBLE_VALUE) {
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
