package au.edu.cdu.problems.ds2;


import au.edu.cdu.common.order2.OrderPackageUtil;
import au.edu.cdu.common.util.AlgoUtil;
import au.edu.cdu.common.util.GlobalVariable;
import au.edu.cdu.problems.IAlgorithm;

public class GreedyNaive implements IAlgorithm {
    private GlobalVariable g; // representing the graph

    public GreedyNaive(){

    }

    @Override
    public void setKRM(int k, int r, int momentRegretThreshold){

    }

    @Override
    public void setGlobalVariable(GlobalVariable g){
        this.g=g;
    }

    /**
     * the major entrance of each algorithm
     */
    public void compute(){
        boolean[] idxDomed=g.getIdxDomed();
        int[] idxSol=g.getIdxSol();
        int idxSolSize=0;
        int[][] idxAL=g.getIdxAL();

        do{
            int[] vList = OrderPackageUtil.getVertexListUtilityDesc(g);
            int vIdx=vList[0];

            idxSol[idxSolSize]=vIdx;
            idxSolSize++;

            idxDomed[vIdx]=true;

            int[] vNeigs=idxAL[vIdx];
            for(int uIdx:vNeigs){
                idxDomed[uIdx]=true;
            }
        }
        while (!AlgoUtil.isAllDominated(g));

        g.setIdxSolSize(idxSolSize);
    }
}
