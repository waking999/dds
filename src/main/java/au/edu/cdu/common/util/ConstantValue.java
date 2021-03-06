package au.edu.cdu.common.util;

/**
 * some common constant values are stored here
 */
public class ConstantValue {
	public static final int IMPOSSIBLE_VALUE = -1;
	public static final int NOT_NULL = Integer.MAX_VALUE;
	public static final String BLANK = " ";

	public static final float FLOAT_NO_DIFF = 1E-12f;

//	public static final String CONNECTED = "1";
//	//public static final String UNCONNECTED = "0";
//
//	public static final byte CONNECTED_BYTE = 1;
//	public static final byte UNCONNECTED_BYTE = 0;

//	public static final String UNDERLINE = "_";

	public final static boolean MARKED = true;
	public final static boolean UNMARKED = false;

//    public final static boolean CHOSEN = true;
//    public final static boolean UNCHOSEN = false;
//
//    public final static String RUNNING_TIME_TOTAL = "Total";
//    public final static String RUNNING_TIME_DDS = "DDS";
//    public final static String RUNNING_TIME_MINI = "Minimal";
//    public final static String RUNNING_TIME_LS = "LS";
//    public final static String RUNNING_TIME_POLYRR = "Poly-RR";
//    public final static String RUNNING_TIME_DEGREERR = "Degree-RR";
//    public final static String RUNNING_TIME_GUARANTEE = "Guarantee";

	// the ascii code of 0
	//public static final byte ASCII_0_SEQ_NO = 48;

	public static final String COMMA = ",";

	// data set
	public static final String DATASET_DIMACS = "DIMACS";
	public static final String DATASET_KONECT = "KONECT";
	public static final String DATASET_BHOSLIB = "BHOSLIB";
	public static final String DATASET_DIMACS_MIS = "DIMACS-MIS";
	public static final String DATASET_NTEREPO = "netrepo";
	
	// database table/view names
	//public static final String DB_TBNAME_DATASET = "dataset";
	public static final String DB_TBNAME_INS = "instance";

	public static final String DB_VNAME_INS = "v_instance";

	public static final String DB_COL_ID = "id";

	public static final String DB_COL_INS_ID = "i_id";
	public static final String DB_COL_INS_CODE = "i_code";
	public static final String DB_COL_INS_NAME = "i_name";
	public static final String DB_COL_INS_PATH_NAME = "path_name";
	public static final String DB_COL_DATASET_PATH_NAME = "d_path";
	public static final String DB_COL_DATASET_NAME = "d_name";

	public static final String DB_COL_V_COUNT = "v_count";
	public static final String DB_COL_E_COUNT = "e_count";
	public static final String DB_COL_TO_BE_TESTED = "to_be_tested";

	public static final String DB_COL_BATCH_NUM = "batch_num";
	public static final String DB_COL_RESULT_SIZE = "result_size";
	public static final String DB_COL_RUNNING_TIME = "running_nano_sec";
	public static final String DB_COL_RESULTS = "results";
	
	public static final String DB_COL_ALGORITHM = "algorithm";

	public static final String DB_COL_K = "k";
	public static final String DB_COL_R = "r";
	public static final String DB_COL_M= "m";

	public static final String TBL_ALG_PREFIX = "result_";

	public static final String CLN_MODE_DROP = "drop";
	public static final String CLN_MODE_DEL = "delete";

}
