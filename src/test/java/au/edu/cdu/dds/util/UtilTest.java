package au.edu.cdu.dds.util;

import java.util.ArrayList;
import java.util.List;
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
		Ruler[] rulerArr;

		boolean output;
		boolean expect;

		arr0 = new boolean[] { false, false, false, true, false, true };
		arr1 = new boolean[] { true, false, true, false, true, false };
		arr2 = new boolean[] { false, false, true, false, false, true };
		arr3 = new boolean[] { false, true, false, true, true, false };

		rulerArr = new Ruler[4];
		rulerArr[0] = new Ruler("0", arr0);
		rulerArr[1] = new Ruler("1", arr1);
		rulerArr[2] = new Ruler("2", arr2);
		rulerArr[3] = new Ruler("3", arr3);
		expect = true;
		output = Util.validCombin(rulerArr);
		Assert.assertTrue(expect == output);

		arr0 = new boolean[] { false, false, false, false, false, true };
		arr1 = new boolean[] { true, false, true, false, true, false };
		arr2 = new boolean[] { false, false, true, false, false, true };
		arr3 = new boolean[] { false, true, false, false, true, false };

		rulerArr = new Ruler[4];
		rulerArr[0] = new Ruler("0", arr0);
		rulerArr[1] = new Ruler("1", arr1);
		rulerArr[2] = new Ruler("2", arr2);
		rulerArr[3] = new Ruler("3", arr3);
		expect = false;
		output = Util.validCombin(rulerArr);
		Assert.assertTrue(expect == output);

	}

	@Test
	public void testGetAllRoutOfNCombines() {
		boolean[] arr0;
		boolean[] arr1;
		boolean[] arr2;
		boolean[] arr3;
		Ruler[] rulerArr;

		int r;

		List<Ruler[]> expect;
		List<Ruler[]> output;

		arr0 = new boolean[] { false, false, false, true, false, true };
		arr1 = new boolean[] { true, false, true, false, true, false };
		arr2 = new boolean[] { false, false, true, false, false, true };
		arr3 = new boolean[] { false, true, false, true, true, false };
		rulerArr = new Ruler[4];
		rulerArr[0] = new Ruler("0", arr0);
		rulerArr[1] = new Ruler("1", arr1);
		rulerArr[2] = new Ruler("2", arr2);
		rulerArr[3] = new Ruler("3", arr3);
		r = 2;
		expect = new ArrayList<Ruler[]>();
		expect.add(new Ruler[] { rulerArr[0], rulerArr[1] });
		expect.add(new Ruler[] { rulerArr[0], rulerArr[2] });
		expect.add(new Ruler[] { rulerArr[0], rulerArr[3] });
		expect.add(new Ruler[] { rulerArr[1], rulerArr[2] });
		expect.add(new Ruler[] { rulerArr[1], rulerArr[3] });
		expect.add(new Ruler[] { rulerArr[2], rulerArr[3] });
		output = Util.getAllRoutOfNCombines(rulerArr, r);
		Assert.assertEquals(expect.size(), output.size());
		for (int i = 0; i < expect.size(); i++) {
			Ruler[] expectRow = expect.get(0);
			Ruler[] outputRow = output.get(0);
			for (int j = 0; j < r; j++) {
				Ruler eR = expectRow[j];
				Ruler oR = outputRow[j];
				Assert.assertEquals(eR.getKey(), oR.getKey());
				String eRRulerStr = Util.arrayToString(eR.getRuler());
				String oRRulerStr = Util.arrayToString(oR.getRuler());
				Assert.assertEquals(eRRulerStr, oRRulerStr);
			}

		}

	}
}