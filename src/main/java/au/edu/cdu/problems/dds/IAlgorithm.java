package au.edu.cdu.problems.dds;

import au.edu.cdu.common.util.GlobalVariable;

public interface IAlgorithm {

    /**
     * set the parameters for a FPT algorithm
     *
     * @param k, is there a solution whose size is less then or equals to k;
     * @param r, is there a subroutine solution whose size is less then or eques to
     *           r;
     */
    //void setKR(int k, int r);

    void setKRM(int k, int r, int momentRegretThreshold);

    /**
     * set the global variable for a algorithm for it to start run, necessary for
     * all algorithms
     *
     * @param g, representing a graph
     */
    void setGlobalVariable(GlobalVariable g);

    /**
     * the major entrance of each algorithm
     */
    void compute();
}
