package au.edu.cdu.dds.util;

/**
 * a java bean to store graph representations
 * @param <VT>
 * vertex type
 */
public class ISGlobalVariable {

	private Graph graph;

	public ISGlobalVariable() {
		this.graph = new Graph();
	}

	private int actVerCnt; // the count active vertices
	private float[] idxVote; // the vote of each vertex (in index format)
	private float[] idxWeight; // the weight of each vertex (in index format)

	private boolean[] idxDomed; // if a vertex (in index format) is dominated
	private boolean[] idxAdded; // if a vertex (in index format) is add to
								// another graph

	private int[] idxSol; // solution in process
	private int idxSolSize; // size of solution in process

	public int getActVerCnt() {
		return actVerCnt;
	}

	public void setActVerCnt(int actVerCnt) {
		this.actVerCnt = actVerCnt;
	}

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

	public boolean[] getIdxAdded() {
		return idxAdded;
	}

	public void setIdxAdded(boolean[] idxAdded) {
		this.idxAdded = idxAdded;
	}

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

	public boolean[] getIdxDomed() {
		return idxDomed;
	}

	public void setIdxDomed(boolean[] idxDomed) {
		this.idxDomed = idxDomed;
	}

	public int[] getIdxDegree() {
		return graph.getIdxDegree();
	}

	public void setIdxDegree(int[] idxDegree) {
		this.graph.setIdxDegree(idxDegree);
	}

	public int getVerCnt() {
		return graph.getVerCnt();
	}

	public void setVerCnt(int verCnt) {
		this.graph.setVerCnt(verCnt);
	}

	public int[] getLabLst() {
		return graph.getLabLst();
	}

	public void setLabLst(int[] labLst) {
		this.graph.setLabLst(labLst);
	}

	public int[] getIdxLst() {
		return graph.getIdxLst();
	}

	public void setIdxLst(int[] idxLst) {
		this.graph.setIdxLst(idxLst);
	}

	public int[][] getIdxIM() {
		return graph.getIdxIM();
	}

	public void setIdxIM(int[][] idxIM) {
		this.graph.setIdxIM(idxIM);
	}

	public int[][] getIdxAL() {
		return graph.getIdxAL();
	}

	public void setIdxAL(int[][] idxAL) {
		this.graph.setIdxAL(idxAL);
	}

}

class Graph {

	private int verCnt; // the count of vertices
	// by labLst and idxLst, it allows vertex to be in any range
	private int[] labLst; // the list of vertex
	private int[] idxLst; // the list of vertex index
	private int[] idxDegree; // the degree of each vertex (in index format),
	private int[][] idxIM; // the incident matrix of vertex (in index format)
	private int[][] idxAL;// the adjacent list of vertex (in index format)

	public int[] getIdxDegree() {
		return idxDegree;
	}

	public void setIdxDegree(int[] idxDegree) {
		this.idxDegree = idxDegree;
	}

	public int getVerCnt() {
		return verCnt;
	}

	public void setVerCnt(int verCnt) {
		this.verCnt = verCnt;
	}

	public int[] getLabLst() {
		return labLst;
	}

	public void setLabLst(int[] labLst) {
		this.labLst = labLst;
	}

	public int[] getIdxLst() {
		return idxLst;
	}

	public void setIdxLst(int[] idxLst) {
		this.idxLst = idxLst;
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