package au.edu.cdu.problems.ds;

import au.edu.cdu.common.control.Result;
import au.edu.cdu.problems.IAlgorithm;

import java.util.List;
import java.util.Map;


public interface IGreedyDS<V> extends IAlgorithm {

	public Result run() throws InterruptedException;
	
	public List<V> getDominatingSet() ;
	
	public Map<String, Long> getRunningTimeMap();
}
