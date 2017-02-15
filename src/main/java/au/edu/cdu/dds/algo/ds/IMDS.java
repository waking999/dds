package au.edu.cdu.dds.algo.ds;

import au.edu.cdu.dds.util.GlobalVariable;

public interface IMDS<ET, ST> {
	public int run(GlobalVariable<ET, ST> gv, AlgorithmParameter ap);
}
