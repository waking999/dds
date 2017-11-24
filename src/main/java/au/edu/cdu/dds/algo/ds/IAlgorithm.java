package au.edu.cdu.dds.algo.ds;

import au.edu.cdu.dds.util.ISGlobalVariable;

public interface IAlgorithm {

	/**
	 * set the parameters for a FPT algorithm
	 * 
	 * @param k,
	 *            is there a solution whose size is less then or equals to k;
	 * @param r,
	 *            is there a subroutine solution whose size is less then or eques to
	 *            r;
	 */
	public void setKR(int k, int r);

	/**
	 * set the global variable for a algorithm for it to start run, necessary for
	 * all algorithms
	 * 
	 * @param g,
	 *            representing a graph
	 */
	public void setGlobalVariable(ISGlobalVariable g);

	/**
	 * the major entrance of each algorithm
	 */
	public void compute();
}
