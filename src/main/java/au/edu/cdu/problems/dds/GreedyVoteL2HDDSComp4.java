package au.edu.cdu.problems.dds;

import au.edu.cdu.common.util.AlgoUtil;
import au.edu.cdu.common.util.ConnectComponents;
import au.edu.cdu.common.util.ConstantValue;
import au.edu.cdu.common.util.GlobalVariable;
import au.edu.cdu.problems.IAlgorithm;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class GreedyVoteL2HDDSComp4 implements IAlgorithm {
    private final ThreadLocal<GlobalVariable> wholeG; // to represent the original graph
    // parameters for fpt subroutine
    private int k;
    int r;
    private int momentRegretThreshold;

    private GlobalVariable[] compGs;

    GreedyVoteL2HDDSComp4() {
        wholeG = new ThreadLocal<>();
    }

    @Override
    public void setKRM(int k, int r,int momentRegretThreshold) {
        this.k = k;
        this.r = r;
        this.momentRegretThreshold=momentRegretThreshold;
    }


    @Override
    public void setGlobalVariable(GlobalVariable g) {
        this.wholeG.set(g);
        ConnectComponents cc = new ConnectComponents();
        cc.setG(g);
        List<Set<Integer>> components = cc.getConnectComponents();
        int componentsSize = components.size();
        compGs = new GlobalVariable[componentsSize];
        processComponents(components);
    }
    @Override
    public GlobalVariable getGlobalVariable( ){
        return this.wholeG.get() ;
    }

    private void processComponents(List<Set<Integer>> components) {

        int[] idxDegree = this.wholeG.get().getIdxDegree();
        int[][] idxAL = this.wholeG.get().getIdxAL();
        int[][] idxIM = this.wholeG.get().getIdxIM();
        float[] idxVote = this.wholeG.get().getIdxVote();
        float[] idxWeight = this.wholeG.get().getIdxWeight();

        int componentsSize = components.size();
        for (int i = 0; i < componentsSize; i++) {
            GlobalVariable compG;
            compG = new GlobalVariable();
            Set<Integer> compVers = components.get(i);
            int compVerCnt = compVers.size();

            int[] compGLabLst = new int[compVerCnt];
            int[] compGIdxLst = new int[compVerCnt];
            int[] compGIdxDegree = new int[compVerCnt];
            float[] compGIdxVote = new float[compVerCnt];
            float[] compGIdxWeight = new float[compVerCnt];
            boolean[] compGIdxDomed = new boolean[compVerCnt];
            Arrays.fill(compGIdxDomed, false);
            boolean[] compGIdxAdded = new boolean[compVerCnt];
            Arrays.fill(compGIdxAdded, false);
            int[][] compGIdxIM = new int[compVerCnt][compVerCnt];
            for (int j = 0; j < compVerCnt; j++) {
                Arrays.fill(compGIdxIM[j], ConstantValue.IMPOSSIBLE_VALUE);
            }
            int[][] compGIdxAL = new int[compVerCnt][];
            int[] compGIdxSol = new int[compVerCnt];
            Arrays.fill(compGIdxSol, ConstantValue.IMPOSSIBLE_VALUE);
            int compGIdxSolSize = 0;

            Iterator<Integer> compVersIt = compVers.iterator();
            int cursor = 0;
            while (compVersIt.hasNext()) {
                int vIdx = compVersIt.next();
                compGLabLst[cursor] = vIdx;
                compGIdxLst[cursor] = cursor;
                compGIdxDegree[cursor] = idxDegree[vIdx];
                compGIdxVote[cursor] = idxVote[vIdx];
                compGIdxWeight[cursor] = idxWeight[vIdx];

                cursor++;
            }

            compG.setIdxSol(compGIdxSol);
            compG.setIdxSolSize(compGIdxSolSize);
            compG.setVerCnt(compVerCnt);
            compG.setActVerCnt(compVerCnt);
            compG.setIdxAL(compGIdxAL);
            compG.setIdxDomed(compGIdxDomed);
            compG.setIdxAdded(compGIdxAdded);
            compG.setIdxIM(compGIdxIM);
            compG.setIdxDegree(compGIdxDegree);
            compG.setIdxLst(compGIdxLst);
            compG.setLabLst(compGLabLst);
            compG.setIdxVote(compGIdxVote);
            compG.setIdxWeight(compGIdxWeight);

            compVersIt = compVers.iterator();
            while (compVersIt.hasNext()) {
                int vIdx = compVersIt.next();
                int compGVIdx = AlgoUtil.getIdxByLab(compG, vIdx);
                int[] vIdxAL = idxAL[vIdx];
                int vIdxALSize = vIdxAL.length;
                compGIdxAL[compGVIdx] = new int[vIdxALSize];
                for (int j = 0; j < vIdxALSize; j++) {
                    int uIdx = vIdxAL[j];
                    int compGUIdx = AlgoUtil.getIdxByLab(compG, uIdx);
                    compGIdxAL[compGVIdx][j] = compGUIdx;
                    compGIdxIM[compGUIdx][compGVIdx] = idxIM[uIdx][vIdx];
                }

            }

            this.compGs[i] = compG;
        }

    }

    @Override
    public void compute() {

        int compGsSize = this.compGs.length;
        int[][] sols = new int[compGsSize][];


        int wholeSolSize = 0;

        IAlgorithm algo = new GreedyVoteL2HDDS4();
        algo.setKRM(k, r,momentRegretThreshold);
        for (int i = 0; i < compGsSize; i++) {
            algo.setGlobalVariable(compGs[i]);
            algo.compute();
            //Assert.assertTrue(AlgoUtil.isValidSolution(compGs[i]));
            wholeSolSize += compGs[i].getIdxSolSize();
            int[] sol = AlgoUtil.getLabSolution(compGs[i]);
            sols[i] = sol;
        }

        int[] wholeSol = new int[wholeSolSize];
        int cursor = 0;
        for (int i = 0; i < compGsSize; i++) {
            int[] sol = sols[i];

            for (int aSol : sol) {
                wholeSol[cursor++] = aSol;
            }
        }

        this.wholeG.get().setIdxSol(wholeSol);
        this.wholeG.get().setIdxSolSize(wholeSolSize);


    }
}
