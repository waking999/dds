package au.edu.cdu.dds.io;

public class DBParameter {
	private String tableName;
	private String[] colNames;
	private String[] colPairNames;
	private String[] colPairOperators;

	private String[] colPairValues;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String[] getColPairNames() {
		return colPairNames;
	}

	public void setColPairNames(String[] colPairNames) {
		this.colPairNames = colPairNames;
	}

	public String[] getColPairOperators() {
		return colPairOperators;
	}

	public void setColPairOperators(String[] colPairOperators) {
		this.colPairOperators = colPairOperators;
	}

	public String[] getColPairValues() {
		return colPairValues;
	}

	public void setColPairValues(String[] colPairValues) {
		this.colPairValues = colPairValues;
	}

	public String[] getColNames() {
		return colNames;
	}

	public void setColNames(String[] colNames) {
		this.colNames = colNames;
	}

}
