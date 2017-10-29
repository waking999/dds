package au.edu.cdu.dds.algo.ds;

import au.edu.cdu.dds.util.ConstantValue;
import au.edu.cdu.dds.util.GlobalVariable;
import au.edu.cdu.dds.util.Util;

public class GreedyVoteH2L implements IAlgorithm {
	GlobalVariable<String> gv;

	public GreedyVoteH2L(GlobalVariable<String> gv) {
		this.gv = gv;
	}

	public void compute() {
		int[] idxSol = gv.getIdxSol();
		int idxSolSize = gv.getIdxSolSize();

		do {

			//int vIdx = Util.getLowestWeightVertexIdx(gv);
			//int uIdx = Util.getHighestWeightNeighIdx(gv, vIdx);
			int uIdx=Util.getHighestWeightVertexIdx(gv);
    			if (uIdx != ConstantValue.IMPOSSIBLE_VALUE) {
				//add uIdx into solution
				idxSol[idxSolSize++] = uIdx;
				
				//adjust weight;
				Util.adjustWeight(gv, uIdx);
				
//				//adjust vertex list
//				Util.adjustVertexList(gv,uIdx);
//				//adjust util
//				Util.adjustUtil(gv,uIdx);
			}

		} while (!Util.isAllDominated(gv));
		
		gv.setIdxSol(idxSol);
		gv.setIdxSolSize(idxSolSize);
	}

	



}
