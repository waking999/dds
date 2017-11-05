package au.edu.cdu.dds.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
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
		s1Len = 0;
		s2Len = 1;
		expect = null;
		output = Util.set1Minus2(s1, s1Len, s2, s2Len);
		TestUtil.verifySort(expect, output);

		s1 = new int[] { 1 };
		s2 = null;
		s1Len = 1;
		s2Len = 0;
		expect = new int[] { 1 };
		output = Util.set1Minus2(s1, s1Len, s2, s2Len);
		TestUtil.verifySort(expect, output);

		s1 = new int[] { 2, 3 };
		s2 = new int[] { 1 };
		s1Len = 2;
		s2Len = 1;
		expect = new int[] { 2, 3 };
		output = Util.set1Minus2(s1, s1Len, s2, s2Len);
		TestUtil.verifySort(expect, output);

		s1 = new int[] { 2, 3 };
		s2 = new int[] { 1, -1 };
		s1Len = 2;
		s2Len = 1;
		expect = new int[] { 2, 3 };
		output = Util.set1Minus2(s1, s1Len, s2, s2Len);
		TestUtil.verifySort(expect, output);

		s1 = new int[] { 1, 2, 3 };
		s2 = new int[] { 1, 2 };
		s1Len = 2;
		s2Len = 2;
		expect = new int[] {};
		output = Util.set1Minus2(s1, s1Len, s2, s2Len);
		TestUtil.verifySort(expect, output);

		s1 = new int[] { 1, 2, 3 };
		s2 = new int[] { 1, 2 };
		s1Len = 2;
		s2Len = 1;
		expect = new int[] { 2 };
		output = Util.set1Minus2(s1, s1Len, s2, s2Len);
		TestUtil.verifySort(expect, output);

		s1 = new int[] { 1, 2, 3 };
		s2 = new int[] { 1, 2 };
		s1Len = 3;
		s2Len = 1;
		expect = new int[] { 2, 3 };
		output = Util.set1Minus2(s1, s1Len, s2, s2Len);
		TestUtil.verifySort(expect, output);

	}

	/*--------------------------*/
	@Test
	public void testArrayToString() {
		byte[] binary;
		String expect;
		String output;

		binary = new byte[] {};
		expect = "";
		output = Util.arrayToString(binary);
		TestUtil.verify(expect, output);

		binary = new byte[] { 1, 0, 1 };
		expect = "101";
		output = Util.arrayToString(binary);
		TestUtil.verify(expect, output);

		binary = new byte[] { 0, 1, 0 };
		expect = "010";
		output = Util.arrayToString(binary);
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

		s1 = new int[] { 1, 2, 3 };
		s1Len = 2;
		s2 = new int[] { 3, 4, 5 };
		s2Len = 2;
		expect = false;
		output = Util.setIntersect(s1, s1Len, s2, s2Len);
		TestUtil.verify(expect, output);

		s1 = new int[] { 1, 2, 3 };
		s1Len = 3;
		s2 = new int[] { 3, 4, 5 };
		s2Len = 2;
		expect = true;
		output = Util.setIntersect(s1, s1Len, s2, s2Len);
		TestUtil.verify(expect, output);
	}

	@Test
	public void testArrayToList() {
		int[] l1 = { 1, 2, 3 };
		Set<Integer> list1 = Util.arrayToSet(l1);
		Assert.assertTrue(list1.contains(1));
		Assert.assertTrue(list1.contains(2));
		Assert.assertTrue(list1.contains(3));
		Assert.assertFalse(list1.contains(4));
	}

	@Test
	public void testIs1Subset2() {
		int[] l1 = { 1, 2, 3 };
		int[] l2 = { 1, 2 };

		Set<Integer> list1 = Util.arrayToSet(l1);
		Set<Integer> list2 = Util.arrayToSet(l2);

		Assert.assertTrue(Util.is1Subset2(list2, list1));
		Assert.assertFalse(Util.is1Subset2(list1, list2));

		list1 = null;
		Assert.assertFalse(Util.is1Subset2(list2, list1));

		list2 = null;
		Assert.assertTrue(Util.is1Subset2(list2, list1));
	}

	@Test
	public void testCopySet() {
		int[] l1 = { 2, 3 };
		Set<Integer> list1 = null;
		Set<Integer> list1Copy = Util.copySet(list1);
		Assert.assertNull(list1Copy);

		list1 = new HashSet<Integer>();
		list1Copy = Util.copySet(list1);
		Assert.assertEquals(0, list1Copy.size());

		list1 = Util.arrayToSet(l1);
		list1Copy = Util.copySet(list1);
		Assert.assertEquals(2, list1Copy.size());
		Assert.assertTrue(list1Copy.contains(2));
		Assert.assertTrue(list1Copy.contains(3));

	}

	@Test
	public void testCopyMap() {
		int[] l1 = { 2, 3 };
		int[] l2 = { 1, 2 };
		int[] l3 = { 1, 2, 3 };

		Set<Integer> list1 = Util.arrayToSet(l1);
		Set<Integer> list2 = Util.arrayToSet(l2);
		Set<Integer> list3 = Util.arrayToSet(l3);

		Map<String, Set<Integer>> map = new HashMap<String, Set<Integer>>();
		map.put("1", list1);
		map.put("2", list2);
		map.put("3", list3);

		Map<String, Set<Integer>> map1 = Util.copyMap(map);
		Assert.assertEquals(3, map1.size());

	}

	@Test
	public void testUnionSets() {
		int[] l1 = { 2, 3 };
		int[] l2 = { 1, 2 };

		Set<Integer> list1 = Util.arrayToSet(l1);
		Set<Integer> list2 = Util.arrayToSet(l2);

		Map<String, Set<Integer>> map = null;
		Assert.assertNull(Util.unionSets(map));

		map = new HashMap<>();
		Assert.assertNull(Util.unionSets(map));

		map.put("1", list1);
		Set<Integer> uList = Util.unionSets(map);
		Assert.assertNotNull(uList);
		Assert.assertEquals(2, uList.size());
		Assert.assertTrue(uList.contains(2));
		Assert.assertTrue(uList.contains(3));

		map.put("2", list2);
		uList = Util.unionSets(map);
		Assert.assertEquals(3, uList.size());
		Assert.assertTrue(uList.contains(1));
	}

	@Test
	public void testExistUniqueSetForAElementList() {
		int[] l1 = { 1, 2, 3 };
		int[] l2 = { 1, 2 };

		Set<Integer> list1 = Util.arrayToSet(l1);
		Set<Integer> list2 = Util.arrayToSet(l2);
		Map<String, Set<Integer>> map = null;
		Assert.assertFalse(Util.existUniqueSetForAElement(2, map).isExist());
		map = new HashMap<String, Set<Integer>>();
		Assert.assertFalse(Util.existUniqueSetForAElement(2, map).isExist());
		Assert.assertFalse(Util.existUniqueSetForAElement(3, map).isExist());

		map.put("1", list1);
		map.put("2", list2);

		Assert.assertTrue(Util.existUniqueSetForAElement(3, map).isExist());
		Assert.assertEquals("1", Util.existUniqueSetForAElement(3, map).getSetKey());
		Assert.assertFalse(Util.existUniqueSetForAElement(2, map).isExist());
	}

	@Test
	public void testExistUniqueSetForAElement() {
		int[] l1 = { 2, 3 };
		//int[] l2 = { 1, 2 };
		int[] l3 = { 1, 2, 3 };

		Set<Integer> list1 = Util.arrayToSet(l1);
		//Set<Integer> list2 = Util.arrayToSet(l2);
		Set<Integer> list3 = Util.arrayToSet(l3);

		Map<String, Set<Integer>> map = null;
		Assert.assertFalse(Util.existUniqueSetForAElement(list3, map).isExist());

		map = new HashMap<String, Set<Integer>>();
		Assert.assertFalse(Util.existUniqueSetForAElement(list3, map).isExist());

		map.put("1", list1);
		Assert.assertTrue(Util.existUniqueSetForAElement(list3, map).isExist());
		Assert.assertEquals("1", Util.existUniqueSetForAElement(list3, map).getSetKey());

		map.put("3", list3);
		Assert.assertTrue(Util.existUniqueSetForAElement(list3, map).isExist());
		Assert.assertEquals("3", Util.existUniqueSetForAElement(list3, map).getSetKey());
	}

	@Test
	public void testGetMaxCardinalitySet() {
		int[] l1 = { 2, 3 };
		int[] l2 = { 1, 2 };
		int[] l3 = { 1, 2, 3 };

		Set<Integer> list1 = Util.arrayToSet(l1);
		Set<Integer> list2 = Util.arrayToSet(l2);
		Set<Integer> list3 = Util.arrayToSet(l3);

		Map<String, Set<Integer>> map = null;

		map = new HashMap<String, Set<Integer>>();

		map.put("1", list1);
		map.put("2", list2);
		map.put("3", list3);

		Assert.assertEquals("3", Util.getMaxCardinalitySetIndex(map));

	}

}