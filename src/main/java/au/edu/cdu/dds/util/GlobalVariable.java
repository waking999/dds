package au.edu.cdu.dds.util;

/**
 * a java bean to store graph representations  
 * 
 * @param <VT>
 *            vertex type
 */
public class GlobalVariable<VT> {
	int verCnt; // the count of vertices, never change
	int actVerCnt; // the count active vertices, change

	public int getActVerCnt() {
		return actVerCnt;
	}

	public void setActVerCnt(int actVerCnt) {
		this.actVerCnt = actVerCnt;
	}

	// this allow vertex to be in any range
	private VT[] verLst; // the list of vertex
	private int[] idxLst; // the list of vertex index

	private int[] idxDegree; //the degree of each vertex (in index format), never change
	public int[] getIdxDegree() {
		return idxDegree;
	}

	public void setIdxDegree(int[] idxDegree) {
		this.idxDegree = idxDegree;
	}

	private int[] idxUtil; // the utility of each vertex (in index format), change
	private float[] idxVote; //the vote of each vertex (in index format), change
	private float[] idxWeight; //the weight of each vertex (in index format), change

	public float[] getIdxVote() {
		return idxVote;
	}

	public void setIdxVote(float[] idxVote) {
		this.idxVote = idxVote;
	}

	public float[] getIdxWeight() {
		return idxWeight;
	}

	public void setIdxWeight(float[] idxWeight) {
		this.idxWeight = idxWeight;
	}

	private boolean[] idxDomed; // if a vertex (in index format) is dominated
	private int undomCnt; // the count of vertices which are not dominated

	private int[][] idxIM; // the incident matrix of vertex (in index format)
	private int[][] idxAL;// the adjacent list of vertex (in index format)

	private int[] idxSol; //  solution in process
	private int idxSolSize; // size of solution in process

 


	public int[] getIdxSol() {
		return idxSol;
	}

	public void setIdxSol(int[] idxSol) {
		this.idxSol = idxSol;
	}

	public int getIdxSolSize() {
		return idxSolSize;
	}

	public void setIdxSolSize(int idxSolSize) {
		this.idxSolSize = idxSolSize;
	}

	public int getVerCnt() {
		return verCnt;
	}

	public void setVerCnt(int verCnt) {
		this.verCnt = verCnt;
	}

	public VT[] getVerLst() {
		return verLst;
	}

	public void setVerLst(VT[] verLst) {
		this.verLst = verLst;
	}

	public int[] getIdxLst() {
		return idxLst;
	}

	public void setIdxLst(int[] idxLst) {
		this.idxLst = idxLst;
	}

	public int[] getIdxUtil() {
		return idxUtil;
	}

	public void setIdxUtil(int[] idxUtils) {
		this.idxUtil = idxUtils;
	}

	public boolean[] getIdxDomed() {
		return idxDomed;
	}

	public void setIdxDomed(boolean[] idxDomed) {
		this.idxDomed = idxDomed;
	}

	public int getUndomCnt() {
		return undomCnt;
	}

	public void setUndomCnt(int undomCnt) {
		this.undomCnt = undomCnt;
	}

	public int[][] getIdxIM() {
		return idxIM;
	}

	public void setIdxIM(int[][] idxIM) {
		this.idxIM = idxIM;
	}

	public int[][] getIdxAL() {
		return idxAL;
	}

	public void setIdxAL(int[][] idxAL) {
		this.idxAL = idxAL;
	}

}