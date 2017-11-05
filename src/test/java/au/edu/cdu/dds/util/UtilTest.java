package au.edu.cdu.dds.util;

import org.junit.Ignore;
import org.junit.Test;

import au.edu.cdu.dds.TestUtil;
import au.edu.cdu.dds.io.DBParameter;

public class UtilTest {
	@Ignore
	public void testIgnore() {

	}

	@Ignore
	@Test
	public void testGetBatchNum() {

		String batchNum = Util.getBatchNum();
		System.out.println(batchNum);
	}

	@Ignore
	@Test
	public void testCleanAlgoTablesDel() {
		DBParameter dbp = new DBParameter();
		String[] colPairNames = { ConstantValue.DB_COL_BATCH_NUM };
		String[] colPairOperators = { "=" };
		String[] colPairValues = { "20171103-0051" };
		dbp.setColPairNames(colPairNames);
		dbp.setColPairOperators(colPairOperators);
		dbp.setColPairValues(colPairValues);
		Util.cleanAlgoTables(ConstantValue.CLN_MODE_DEL, ConstantValue.DATASET_KONECT, dbp);

	}

	@Ignore
	@Test
	public void testCleanAlgoTablesDrop() {

		Util.cleanAlgoTables(ConstantValue.CLN_MODE_DROP, ConstantValue.DATASET_KONECT, null);
		Util.cleanAlgoTables(ConstantValue.CLN_MODE_DROP, ConstantValue.DATASET_BHOSLIB, null);
	}

	@Test
	public void testSet1MinusSet2() {
		int[] s1;
		int s1Len;
		int[] s2;
		int s2Len;
		int[] expect;
		int[] output;

		s1 = null;
		s2 = new int[] { 1 };
		s1Len=0;
		s2Len=1;
		expect = null;
		output = Util.set1Minus2(s1,s1Len, s2,s2Len);
		TestUtil.verifySort(expect, output);

		s1 = new int[] { 1 };
		s2 = null;
		s1Len=1;
		s2Len=0;
		expect = new int[] { 1 };
		output = Util.set1Minus2(s1,s1Len, s2,s2Len);
		TestUtil.verifySort(expect, output);

		s1 = new int[] { 2, 3 };
		s2 = new int[] { 1 };
		s1Len=2;
		s2Len=1;
		expect = new int[] { 2, 3 };
		output = Util.set1Minus2(s1,s1Len, s2,s2Len);
		TestUtil.verifySort(expect, output);
		
		s1 = new int[] { 2, 3 };
		s2 = new int[] { 1 ,-1};
		s1Len=2;
		s2Len=1;
		expect = new int[] { 2, 3 };
		output = Util.set1Minus2(s1,s1Len, s2,s2Len);
		TestUtil.verifySort(expect, output);

		s1 = new int[] { 1, 2, 3 };
		s2 = new int[] { 1, 2 };
		s1Len=2;
		s2Len=2;
		expect = new int[] {  };
		output = Util.set1Minus2(s1,s1Len, s2,s2Len);
		TestUtil.verifySort(expect, output);
		
		s1 = new int[] { 1, 2, 3 };
		s2 = new int[] { 1, 2 };
		s1Len=2;
		s2Len=1;
		expect = new int[] {2 };
		output = Util.set1Minus2(s1,s1Len, s2,s2Len);
		TestUtil.verifySort(expect, output);

		s1 = new int[] { 1, 2, 3 };
		s2 = new int[] { 1, 2 };
		s1Len=3;
		s2Len=1;
		expect = new int[] {2, 3 };
		output = Util.set1Minus2(s1,s1Len, s2,s2Len);
		TestUtil.verifySort(expect, output);

	}
	
	@Test
	public void testArrayToString() {
		byte[] binary;
		String expect;
		String output;
		
		
		binary=new byte[] {};
		expect="";
		output=Util.arrayToString(binary);
		TestUtil.verify(expect, output);
		
		
		binary=new byte[] {1,0,1};
		expect="101";
		output=Util.arrayToString(binary);
		TestUtil.verify(expect, output);
		
		binary=new byte[] {0,1,0};
		expect="010";
		output=Util.arrayToString(binary);
		TestUtil.verify(expect, output);
	}
	
	
	@Test
	public void testSetIntersect() {
		int[] s1;
		int s1Len;
		int[] s2;
		int s2Len;
		boolean expect;
		boolean output;
		
		s1=new int[] {1,2,3};
		s1Len=2;
		s2=new int[] {3,4,5};
		s2Len=2;
		expect=false;
		output=Util.setIntersect(s1, s1Len, s2, s2Len);
		TestUtil.verify(expect, output);
		
		s1=new int[] {1,2,3};
		s1Len=3;
		s2=new int[] {3,4,5};
		s2Len=2;
		expect=true;
		output=Util.setIntersect(s1, s1Len, s2, s2Len);
		TestUtil.verify(expect, output);
	}

}