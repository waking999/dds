package au.edu.cdu.common.io;

/**
 * a java bean for db operations
 * 
 * @author kwang1
 *
 */
public class DBParameter {
	// table name
	private String tableName;
	// used for select clause
	private String[] colNames;
	/*
	 * used for where clause, pairName, pairOperator and pairValues should be
	 * matched one by one.
	 */
	private String[] colPairNames;
	private String[] colPairOperators;
	private String[] colPairValues;

	String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	String[] getColPairNames() {
		return colPairNames;
	}

	public void setColPairNames(String[] colPairNames) {
		this.colPairNames = colPairNames;
	}

	String[] getColPairOperators() {
		return colPairOperators;
	}

	void setColPairOperators(String[] colPairOperators) {
		this.colPairOperators = colPairOperators;
	}

	String[] getColPairValues() {
		return colPairValues;
	}

	public void setColPairValues(String[] colPairValues) {
		this.colPairValues = colPairValues;
	}

	String[] getColNames() {
		return colNames;
	}

	void setColNames(String[] colNames) {
		this.colNames = colNames;
	}

}
