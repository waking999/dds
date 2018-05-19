package au.edu.cdu.problems.ds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.edu.cdu.common.control.ITask;
import au.edu.cdu.common.control.Result;
import au.edu.cdu.common.control.TaskLock;
import au.edu.cdu.common.exception.ArraysNotSameLengthException;
import au.edu.cdu.common.exception.ExceedLongMaxException;
import au.edu.cdu.common.order.OrderPackageUtil;
import au.edu.cdu.common.util.AlgoUtil;
import au.edu.cdu.common.util.LogUtil;

import au.edu.cdu.common.exception.MOutofNException;

import au.edu.cdu.common.util.Util;
import edu.uci.ics.jung.graph.Graph;
import org.apache.log4j.Logger;


public class GreedyNative implements ITask, IGreedyDS<Integer> {


	private static Logger log = LogUtil.getLogger(GreedyNative.class.getSimpleName());
	private long runningTime;
	public Map<String, Long> getRunningTimeMap(){
		return null;
	}

	private TaskLock lock;

	public TaskLock getLock() {
		return lock;
	}

	public void setLock(TaskLock lock) {
		this.lock = lock;
	}

	@Override
	public Result run() {

		try {
			computing();
			Thread.sleep(1000);

            return getResult();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return null;
	}

	public Result getResult() {
		Result r = new Result();
		r.setHasSolution(true);

        String sb = "," + this.dominatingSet.size() + "," +
                this.runningTime;
        r.setString(sb);
		return r;
	}

	/**
	 * the graph
	 */
	private Graph<Integer, String> g;
	/**
	 * 
	 * a sorted vertices with their degree (from highest degree to the lowest)
	 */

	private String indicator;

	Map<Integer, Boolean> dominatedMap;
	/**
	 * the desired dominating set
	 */
	List<Integer> dominatingSet;

	public List<Integer> getDominatingSet() {
		return dominatingSet;
	}

	/**
	 * number of vertices
	 */
	@SuppressWarnings("unused")
	private int numOfVertices;

	/**
	 * the adjacency matrix of the graph
	 */
	private List<String[]> adjacencyMatrix;

	public GreedyNative(List<String[]> adjacencyMatrix) {
		this.adjacencyMatrix = adjacencyMatrix;
		this.numOfVertices = adjacencyMatrix.size();
		this.g = AlgoUtil.prepareGenericGraph(this.adjacencyMatrix);

	}

	public GreedyNative(String indicator, List<String[]> adjacencyMatrix) {
		this.indicator = indicator;
		this.adjacencyMatrix = adjacencyMatrix;
		this.numOfVertices = adjacencyMatrix.size();
		this.g = AlgoUtil.prepareGenericGraph(this.adjacencyMatrix);

	}

	public GreedyNative(Graph<Integer, String> g) {
		this.g = g;
		this.numOfVertices = g.getVertexCount();

	}

	/**
	 * the major function do the computing to get the desired solution. In this
	 * case, the desired result is a dominating set
	 */
	public void computing() throws MOutofNException, ExceedLongMaxException,
			ArraysNotSameLengthException {
		long start = System.nanoTime();
		initialization();
		greedy();
		long end = System.nanoTime();
		runningTime = end - start;

	}

	private void initialization() {
		dominatingSet = new ArrayList<>();

		dominatedMap = new HashMap<>();
		Collection<Integer> vertices = g.getVertices();
		for (Integer v : vertices) {
			dominatedMap.put(v, false);
		}
	}

	private void greedy() {
		Collection<Integer> vertices = g.getVertices();
		for (Integer v : vertices) {
			int degree=g.degree(v);
			
			if(degree==0){
				dominatedMap.put(v, true);
				Util.addElementToList(dominatingSet, v);
			}
		}
		

		while (!AlgoUtil.isAllDominated(dominatedMap)) {
			// get the vertex with highest utility (the number of undominated
			// neighbors)
//			List<VertexDegree> vdList = AlgorithmUtil
//					.sortVertexAccordingToUtility(g, dominatedMap);
//			VertexDegree vd = vdList.get(0);
//
//			Integer v = vd.getVertex();
	
			List<Integer> vList = OrderPackageUtil.getVertexListUtilityDesc(this.g,this.dominatedMap);
			Integer v=vList.get(0);
			
			
			// add it into dominating set
		 	Util.addElementToList(dominatingSet, v);
			// set it is dominated
			dominatedMap.put(v, true);

			// set its neigbors is dominated
			Collection<Integer> wNeigs = g.getNeighbors(v);

			for (Integer u : wNeigs) {
				dominatedMap.put(u, true);
			}
		}

	}

	

}
