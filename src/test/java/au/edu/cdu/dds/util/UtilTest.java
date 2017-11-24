package au.edu.cdu.dds.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import au.edu.cdu.dds.TestUtil;

public class UtilTest {
	@Ignore
	public void testIgnore() {
	}

	@Test
	public void testGetBatchNum() {
		String batchNum = Util.getBatchNum();
		System.out.println(batchNum);
	}

	@Test
	public void testArrayToString() {
		boolean[] binary;
		String expect;
		String output;

		binary = new boolean[] {};
		expect = "";
		output = Util.arrayToString(binary);
		TestUtil.verify(expect, output);

		binary = new boolean[] { true, false, true };
		expect = "101";
		output = Util.arrayToString(binary);
		TestUtil.verify(expect, output);

		binary = new boolean[] { false, true, false };
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
		output = Util.setsIntersect(s1, s1Len, s2, s2Len);
		TestUtil.verify(expect, output);

		s1 = new int[] { 1, 2, 3 };
		s1Len = 3;
		s2 = new int[] { 3, 4, 5 };
		s2Len = 2;
		expect = true;
		output = Util.setsIntersect(s1, s1Len, s2, s2Len);
		TestUtil.verify(expect, output);
	}

	@Test
	public void testArrayToList() {
		int[] l1 = { 1, 2, 3 };
		Set<Integer> list1 = TestUtil.arrayToSet(l1);
		Assert.assertTrue(list1.contains(1));
		Assert.assertTrue(list1.contains(2));
		Assert.assertTrue(list1.contains(3));
		Assert.assertFalse(list1.contains(4));
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

	@Test
	public void testArrayOr() {
		boolean[] arr1;
		boolean[] arr2;
		boolean[] output;
		boolean[] expect;

		arr1 = new boolean[] { true, false };
		arr2 = new boolean[] { false, false };
		expect = new boolean[] { true, false };
		output = Util.arrayOr(arr1, arr2);
		TestUtil.verifyUnsort(expect, output);

	}

	@Test
	public void testValidCombin() {
		boolean[] arr0;
		boolean[] arr1;
		boolean[] arr2;
		boolean[] arr3;
		Map<String, boolean[]> map;
		String[] rulerKeyArr;

		boolean output;
		boolean expect;

		arr0 = new boolean[] { false, false, false, true, false, true };
		arr1 = new boolean[] { true, false, true, false, true, false };
		arr2 = new boolean[] { false, false, true, false, false, true };
		arr3 = new boolean[] { false, true, false, true, true, false };
		map = new HashMap<>();
		map.put("0", arr0);
		map.put("1", arr1);
		map.put("2", arr2);
		map.put("3", arr3);
		rulerKeyArr = new String[] { "0", "1", "2", "3" };
		expect = true;
		output = Util.validCombin(rulerKeyArr, map);
		Assert.assertTrue(expect == output);

		arr0 = new boolean[] { false, false, false, false, false, true };
		arr1 = new boolean[] { true, false, true, false, true, false };
		arr2 = new boolean[] { false, false, true, false, false, true };
		arr3 = new boolean[] { false, true, false, false, true, false };
		map.clear();
		map.put("0", arr0);
		map.put("1", arr0);
		map.put("2", arr0);
		map.put("3", arr0);
		rulerKeyArr = new String[] { "0", "1", "2", "3" };

		expect = false;
		output = Util.validCombin(rulerKeyArr, map);
		Assert.assertTrue(expect == output);

	}

	@Test
	public void testGetAllRoutOfNCombines() { 
		String[] rulerKeyArr;

		int r;

		List<String[]> expect;
		List<String[]> output;

		 
		rulerKeyArr = new String[]{ "0", "1", "2", "3" };
		r = 2;
		expect = new ArrayList<String[]>();
		expect.add(new String[] { rulerKeyArr[0], rulerKeyArr[1] });
		expect.add(new String[] { rulerKeyArr[0], rulerKeyArr[2] });
		expect.add(new String[] { rulerKeyArr[0], rulerKeyArr[3] });
		expect.add(new String[] { rulerKeyArr[1], rulerKeyArr[2] });
		expect.add(new String[] { rulerKeyArr[1], rulerKeyArr[3] });
		expect.add(new String[] { rulerKeyArr[2], rulerKeyArr[3] });
		output = Util.getAllRoutOfNCombines(rulerKeyArr, r);
		Assert.assertEquals(expect.size(), output.size());
		for (int i = 0; i < expect.size(); i++) {
			String[] expectRow = expect.get(0);
			String[] outputRow = output.get(0);
			for (int j = 0; j < r; j++) {
				String eR = expectRow[j];
				String oR = outputRow[j]; 
				Assert.assertTrue(eR.equals(oR));
			}

		}

	}
}