package au.edu.cdu.dds.algo.ds;

public class AlgorithmParameter {
	private long allowedRunningTime;
	private int unacceptedResultSize;
	private int acceptedResultSize;
	private int bestResultSize;
	private int threshold;
	
	public int getThreshold() {
		return threshold;
	}
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
 
	public long getAllowedRunningTime() {
		return allowedRunningTime;
	}
	public void setAllowedRunningTime(long allowedRunningTime) {
		this.allowedRunningTime = allowedRunningTime;
	}
	public int getUnacceptedResultSize() {
		return unacceptedResultSize;
	}
	public void setUnacceptedResultSize(int unacceptedResultSize) {
		this.unacceptedResultSize = unacceptedResultSize;
	}
	public int getAcceptedResultSize() {
		return acceptedResultSize;
	}
	public void setAcceptedResultSize(int acceptedResultSize) {
		this.acceptedResultSize = acceptedResultSize;
	}
	public int getBestResultSize() {
		return bestResultSize;
	}
	public void setBestResultSize(int bestResultSize) {
		this.bestResultSize = bestResultSize;
	}
 

}
