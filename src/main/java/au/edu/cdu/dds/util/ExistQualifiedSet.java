package au.edu.cdu.dds.util;

/**
 * a java bean to store if a satisfying set exist and what index of the set is
 */
public class ExistQualifiedSet<K> {
	private boolean exist;
	private K setKey;

	public boolean isExist() {
		return exist;
	}

	 
	public ExistQualifiedSet(boolean exist, K setKey) {
		this.exist = exist;
		this.setKey=setKey;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (exist) {
			sb.append("exist:").append(this.setKey);
		} else {
			sb.append("not exist.");
		}

		return sb.toString();
	}


	public K getSetKey() {
		return setKey;
	}


	public void setSetKey(K setKey) {
		this.setKey = setKey;
	}


	public void setExist(boolean exist) {
		this.exist = exist;
	}
}