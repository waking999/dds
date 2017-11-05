package au.edu.cdu.dds.algo.sc;

import java.util.Set;

/**
 * a java bean to encapsulate results.
 * 
 */
public class ReturnResult<T> {
	int resultSize;

	Set<T> results;

	public int getResultSize() {
		return resultSize;
	}

	public void setResultSize(int resultSize) {
		this.resultSize = resultSize;
	}

	public Set<T> getResults() {
		return results;
	}

	public void setResults(Set<T> results) {
		this.results = results;
	}

	public ReturnResult(int resultSize, Set<T> results) {
		super();
		this.resultSize = resultSize;
		this.results = results;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(resultSize).append(":");
		for (T t : results) {
			sb.append(t.toString()).append(",");
		}
		return sb.substring(0, sb.length() - 1);
	}
}
