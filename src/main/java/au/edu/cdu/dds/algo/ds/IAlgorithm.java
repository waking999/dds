package au.edu.cdu.dds.algo.ds;

import au.edu.cdu.dds.util.GlobalVariable;

public interface IAlgorithm<VT> {
	
	public void setGV(GlobalVariable<VT> gv);
	
	public void compute();
}
