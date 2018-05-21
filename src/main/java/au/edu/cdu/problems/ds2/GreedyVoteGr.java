package au.edu.cdu.problems.ds2;

import au.edu.cdu.common.util.AlgoUtil;
import au.edu.cdu.common.util.GlobalVariable;
import au.edu.cdu.problems.IAlgorithm;

public class GreedyVoteGr implements IAlgorithm {
    private GlobalVariable g; // representing the graph

    GreedyVoteGr(){

    }

    @Override
    public void setKRM(int k, int r, int momentRegretThreshold){

    }

    @Override
    public void setGlobalVariable(GlobalVariable g){
        this.g=g;
    }

    @Override
    public GlobalVariable getGlobalVariable( ){
        return this.g ;
    }

    /**
     * the major entrance of each algorithm
     */
    public void compute(){
        GreedyVote algo=new GreedyVote();
        algo.setGlobalVariable(g);
        algo.compute();

        g=AlgoUtil.grasp(algo.getGlobalVariable());


    }

}
