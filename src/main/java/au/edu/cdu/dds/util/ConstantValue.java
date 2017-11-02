package au.edu.cdu.dds.util;

/**
 * 
 * some common constant values are stored here
 */
public class ConstantValue {
	public static final int IMPOSSIBLE_VALUE = -1;
	public static final int NOT_NULL=Integer.MAX_VALUE;
	public static final String BLANK = " ";
	
	public static final float FLOAT_NO_DIFF=1E-12f;

	//public static final int LABEL_TYPE_START = 0;
	//public static final int LABEL_TYPE_VERTEX = 1;
	//public static final int LABEL_TYPE_EDGE = 2;

	//public static final int MATE_EXPOSE = -1;

	public static final String COMMA=",";
	
	// time limit of algorithm running
	//public static final long RUNNING_TIME_LIMIT = 15 * 60 * 1000000000L;

	// data set
	public static final String DATASET_DIMACS = "DIMACS";
	public static final String DATASET_KONECT = "KONECT";
	public static final String DATASET_BHOSLIB = "BHOSLIB";
	
	// database table/view names
	public static final String DB_TBNAME_DATASET = "dataset";
	public static final String DB_TBNAME_INS = "instance";
//	public static final String DB_TBNAME_ALG1 = "alg1running";
//	public static final String DB_TBNAME_ALG2 = "alg2running";
//	public static final String DB_TBNAME_ALG3 = "alg3running";
	
	public static final String DB_VNAME_INS = "v_instance";
	//public static final String DB_VNAME_INS_OPT = "v_instance_opt";
	// database column names
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
	
//	public static final String DB_COL_BEST_RESULT_SIZE = "best_result_size";
//	public static final String DB_COL_BEST_RUNNING_TIME = "best_running_nano_sec";
//	public static final String DB_COL_ALLOWED_RUNNING_TIME = "allowed_running_time";
			
//	public static final String DB_COL_ACCEPT_RESULT_SIZE = "accepted_result_size";
//	public static final String DB_COL_UNACCEPT_RESULT_SIZE = "unaccepted_result_size";
	
	//public static final String DB_COL_THRESHOLD = "threshold";
	//public static final String DB_COL_MODEL= "model";
	public static final String DB_COL_ALGORITHM= "algorithm";
	
	public static final String TBL_ALG_PREFIX="algRunning_";
	
//	public static final String DB_COL_THRESHOLD1 = "threshold1";
//	public static final String DB_COL_RESULT_SIZE1 = "result_size1";
//	public static final String DB_COL_RUNNING_TIME1 = "running_nano_sec1";
// 
//	public static final String DB_COL_THRESHOLD2 = "threshold2";
//	public static final String DB_COL_RESULT_SIZE2 = "result_size2";
//	public static final String DB_COL_RUNNING_TIME2 = "running_nano_sec2";
	
	public static final String CLN_MODE_DROP="drop";
	public static final String CLN_MODE_DEL="delete";
	 
	//
	// public static final TestParameter[] KONECT_TP = { new
	// TestParameter("000027_zebra.konet", 15, 15, true),
	// new TestParameter("000034_zachary.konet", 15, 15, true),
	// new TestParameter("000062_dolphins.konet", 15, 15, true),
	// new TestParameter("000112_David_Copperfield.konet", 15, 15, true),
	// new TestParameter("000198_Jazz_musicians.konet", 15, 15, true),
	// new TestParameter("000212_pdzbase.konet", 15, 15, true),
	// new TestParameter("001133_rovira.konet", 50, 50, true),
	// new TestParameter("001174_euroroad.konet", 50, 50, true),
	// new TestParameter("001858_hamster.konet", 50, 50, true),
	// new TestParameter("002426_hamster_ful.konet", 15, 15, true),
	// new TestParameter("002888_facebook.konet", 15, 15, true),
	// new TestParameter("010680_Pretty_Good_Privacy.konet", 15, 15, true),
	// new TestParameter("018771_arXiv.konet", 15, 15, true),
	// new TestParameter("026475-caida.konet", 15, 15, false),
	// new TestParameter("028093_arXiv_hep.konet", 15, 15, false),
	// new TestParameter("058228-brightkite.konet", 15, 15, false),
	// new TestParameter("063731-facebookfriendships.konet", 15, 15, false), };
	//
	 
	//
	// public static final TestParameter[] DIMACS_TP = { new
	// TestParameter("C1000.9.clq", 10, 10, true),
	// new TestParameter("C125.9.clq", 10, 10, true), new
	// TestParameter("C2000.5.clq", 10, 10, true),
	// new TestParameter("C2000.9.clq", 10, 10, true), new
	// TestParameter("C250.9.clq", 10, 10, true),
	// new TestParameter("C4000.5.clq", 10, 10, true), new
	// TestParameter("C500.9.clq", 10, 10, true),
	// new TestParameter("DSJC1000.5.clq", 10, 10, true), new
	// TestParameter("DSJC500.5.clq", 10, 10, true),
	// new TestParameter("MANN_a27.clq", 10, 10, true), new
	// TestParameter("MANN_a81.clq", 10, 10, true),
	// new TestParameter("brock200_2.clq", 10, 10, true), new
	// TestParameter("brock200_4.clq", 10, 10, true),
	// new TestParameter("brock400_2.clq", 10, 10, true), new
	// TestParameter("brock400_4.clq", 10, 10, true),
	// new TestParameter("brock800_2.clq", 10, 10, true), new
	// TestParameter("brock800_4.clq", 10, 10, true),
	// new TestParameter("gen200_p0.9_44.clq", 10, 10, true),
	// new TestParameter("gen200_p0.9_55.clq", 10, 10, true),
	// new TestParameter("gen400_p0.9_55.clq", 10, 10, true),
	// new TestParameter("gen400_p0.9_65.clq", 10, 10, true),
	// new TestParameter("gen400_p0.9_75.clq", 10, 10, true), new
	// TestParameter("hamming10-4.clq", 10, 10, true),
	// new TestParameter("hamming8-4.clq", 10, 10, true), new
	// TestParameter("keller4.clq", 10, 10, true),
	// new TestParameter("keller5.clq", 10, 10, true), new
	// TestParameter("keller6.clq", 10, 10, true),
	// new TestParameter("p_hat1500-1.clq", 10, 10, true), new
	// TestParameter("p_hat1500-2.clq", 10, 10, true),
	// new TestParameter("p_hat1500-3.clq", 10, 10, true), new
	// TestParameter("p_hat300-1.clq", 10, 10, true),
	// new TestParameter("p_hat300-2.clq", 10, 10, true), new
	// TestParameter("p_hat300-3.clq", 10, 10, true),
	// new TestParameter("p_hat700-1.clq", 10, 10, true), new
	// TestParameter("p_hat700-2.clq", 10, 10, true),
	// new TestParameter("p_hat700-3.clq", 10, 10, true), };
	//
	
	//
	// public static final TestParameter[] BHOSLIB_TP = { new
	// TestParameter("frb30-15-mis/frb30-15-1.mis", 10, 10, true),
	// new TestParameter("frb30-15-mis/frb30-15-2.mis", 10, 10, true),
	// new TestParameter("frb30-15-mis/frb30-15-3.mis", 10, 10, true),
	// new TestParameter("frb30-15-mis/frb30-15-4.mis", 10, 10, true),
	// new TestParameter("frb30-15-mis/frb30-15-5.mis", 10, 10, true),
	// new TestParameter("frb35-17-mis/frb35-17-1.mis", 10, 10, true),
	// new TestParameter("frb35-17-mis/frb35-17-2.mis", 10, 10, true),
	// new TestParameter("frb35-17-mis/frb35-17-3.mis", 10, 10, true),
	// new TestParameter("frb35-17-mis/frb35-17-4.mis", 10, 10, true),
	// new TestParameter("frb35-17-mis/frb35-17-5.mis", 10, 10, true),
	// new TestParameter("frb40-19-mis/frb40-19-1.mis", 10, 10, true),
	// new TestParameter("frb40-19-mis/frb40-19-2.mis", 10, 10, true),
	// new TestParameter("frb40-19-mis/frb40-19-3.mis", 10, 10, true),
	// new TestParameter("frb40-19-mis/frb40-19-4.mis", 10, 10, true),
	// new TestParameter("frb40-19-mis/frb40-19-5.mis", 10, 10, true),
	// new TestParameter("frb45-21-mis/frb45-21-1.mis", 10, 10, true),
	// new TestParameter("frb45-21-mis/frb45-21-2.mis", 10, 10, true),
	// new TestParameter("frb45-21-mis/frb45-21-3.mis", 10, 10, true),
	// new TestParameter("frb45-21-mis/frb45-21-4.mis", 10, 10, true),
	// new TestParameter("frb45-21-mis/frb45-21-5.mis", 10, 10, true),
	// new TestParameter("frb53-24-mis/frb53-24-1.mis", 10, 10, true),
	// new TestParameter("frb53-24-mis/frb53-24-2.mis", 10, 10, true),
	// new TestParameter("frb53-24-mis/frb53-24-3.mis", 10, 10, true),
	// new TestParameter("frb53-24-mis/frb53-24-4.mis", 10, 10, true),
	// new TestParameter("frb53-24-mis/frb53-24-5.mis", 10, 10, true),
	// new TestParameter("frb56-25-mis/frb56-25-1.mis", 10, 10, true),
	// new TestParameter("frb56-25-mis/frb56-25-2.mis", 10, 10, true),
	// new TestParameter("frb56-25-mis/frb56-25-3.mis", 10, 10, true),
	// new TestParameter("frb56-25-mis/frb56-25-4.mis", 10, 10, true),
	// new TestParameter("frb56-25-mis/frb56-25-5.mis", 10, 10, true),
	// new TestParameter("frb59-26-mis/frb59-26-1.mis", 10, 10, true),
	// new TestParameter("frb59-26-mis/frb59-26-2.mis", 10, 10, true),
	// new TestParameter("frb59-26-mis/frb59-26-3.mis", 10, 10, true),
	// new TestParameter("frb59-26-mis/frb59-26-4.mis", 10, 10, true),
	// new TestParameter("frb59-26-mis/frb59-26-5.mis", 10, 10, true), };

}
