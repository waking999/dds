package au.edu.cdu.dds.util;

/**
 * a java bean to store if a satisfying set exist and what index of the set is
 */
public class ExistQualifiedSet {
	private boolean exist;
	private int setIndex;

	public boolean isExist() {
		return exist;
	}

	public int getSetIndex() {
		return setIndex;
	}

	ExistQualifiedSet(boolean exist, int subsetIndex) {
		this.exist = exist;
		this.setIndex = subsetIndex;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (exist) {
			sb.append("exist:").append(this.setIndex);
		} else {
			sb.append("not exist.");
		}

		return sb.toString();
	}
}