package au.edu.cdu.dds.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * this is an util class for common functions
 */
public class Util {

	/**
	 * find the position of a value in a range of an array
	 * @param array
	 * @param arrayLen,
	 * the range
	 * @param val
	 * @return
	 */
	public static int findPos(int[] array, int arrayLen, int val) {
		for (int i = 0; i < arrayLen; i++) {
			if (array[i] == val) {
				return i;
			}
		}
		return ConstantValue.IMPOSSIBLE_VALUE;
	}

	/**
	 * generate a batch num by date and time
	 * @return
	 */
	public static String getBatchNum() {
		// given date
		Date date = new Date();
		// creates a new calendar instance
		Calendar calendar = GregorianCalendar.getInstance();
		// assigns calendar to given date
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h
														// format
		int min = calendar.get(Calendar.MINUTE);

		StringBuffer sb = new StringBuffer();
		String monthStr = String.format("%02d", month + 1);
		String dayStr = String.format("%02d", day);
		String hourStr = String.format("%02d", hour);
		String minStr = String.format("%02d", min);

		sb.append(year).append(monthStr).append(dayStr).append("-").append(hourStr).append(minStr);

		return sb.toString();

	}

	/**
	 * a set s1 minus a set s2, s1-s2
	 * @param s1,
	 * a set
	 * @param s2,
	 * a set
	 * @return the elements in set s1 not in s2
	 */
	public static int[] set1Minus2(int[] s1, int s1Len, int[] s2, int s2Len) {
		if (s1 == null) {
			return s1;
		}
		if (s2 == null) {
			return s1;
		}

		int[] tmpArr = new int[s1Len];
		int tmpArrLen = 0;
		for (int i = 0; i < s1Len; i++) {
			int pos = findPos(s2, s2Len, s1[i]);
			if (pos == ConstantValue.IMPOSSIBLE_VALUE) {
				tmpArr[tmpArrLen++] = s1[i];
			}
		}

		return Arrays.copyOf(tmpArr, tmpArrLen);

	}

	/**
	 * a set s1 minus a set s2, s1-s2
	 * @param s1,
	 * a set
	 * @param s2,
	 * a set
	 * @return the elements in set s1 not in s2
	 */
	public static int[] set1Minus2(int[] s1, int s1Len, Set<Integer> s2) {
		if (s1 == null) {
			return s1;
		}
		if (s2 == null) {
			return s1;
		}

		int[] s2Arr = convertSetToArray(s2);
		int s2Len = s2.size();
		return set1Minus2(s1, s1Len, s2Arr, s2Len);

	}

	/**
	 * a set s1 minus a set s2, s1-s2
	 * @param s1,
	 * a set
	 * @param s2,
	 * a set
	 * @return the elements in set s1 not in s2
	 */
	public static int[] set1Minus2(Set<Integer> s1, int[] s2, int s2Len) {
		if (s1 == null) {
			return null;
		}

		int[] s1Arr = convertSetToArray(s1);
		int s1Len = s1.size();
		return set1Minus2(s1Arr, s1Len, s2, s2Len);

	}

	/**
	 * a set s1 minus a set s2, s1-s2
	 * @param s1,
	 * a set
	 * @param s2,
	 * a set
	 * @return the elements in set s1 not in s2
	 */
	public static int[] set1Minus2(Set<Integer> s1, Set<Integer> s2) {
		if (s1 == null) {
			return null;
		}

		int[] s1Arr = convertSetToArray(s1);
		int s1Len = s1.size();
		if (s2 == null) {
			return s1Arr;
		}

		int[] s2Arr = convertSetToArray(s2);
		int s2Len = s2.size();

		return set1Minus2(s1Arr, s1Len, s2Arr, s2Len);

	}

	/**
	 * convert set to array
	 * @param s
	 * @param sLen
	 * @return
	 */
	public static int[] convertSetToArray(Set<Integer> s) {
		int sLen = s.size();
		int[] sArr = new int[sLen];
		Iterator<Integer> s1It = s.iterator();
		int pointer = 0;
		while (s1It.hasNext()) {
			sArr[pointer++] = s1It.next().intValue();
		}
		return sArr;
	}

	/**
	 * convert a boolean array to 01 string
	 * @param
	 * @return
	 */
	public static String arrayToString(boolean[] boolArr) {
		int boolArrSize = boolArr.length;
		char[] chArray = new char[boolArrSize];
		for (int i = 0; i < boolArrSize; i++) {
			chArray[i] = boolArr[i] ? '1' : '0';
		}
		String rtn = new String(chArray);
		return rtn;
	}

	/**
	 * to check if the two sets are intersected, s1 n s2
	 * @param s1
	 * @param s1Len
	 * @param s2
	 * @param s2Len
	 * @return
	 */
	public static boolean setsIntersect(int[] s1, int s1Len, int[] s2, int s2Len) {
		for (int i = 0; i < s1Len; i++) {
			int s1Val = s1[i];
			for (int j = 0; j < s2Len; j++) {
				int s2Val = s2[j];
				if (s1Val == s2Val) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * convert a neigh type domed map into an array of ruler object
	 * @param map
	 * @return
	 */
	public static Ruler[] converRulerMapToArray(Map<String, boolean[]> map) {
		Set<String> keySet = map.keySet();
		int mapSize = map.size();
		Ruler[] rulerArr = new Ruler[mapSize];
		int pos = 0;
		for (String key : keySet) {
			boolean[] ruler = map.get(key);
			rulerArr[pos++] = new Ruler(key, ruler);
		}
		return rulerArr;
	}

	/**
	 * to do or operation on cooresponding elements of the two boolean arrays
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	protected static boolean[] arrayOr(boolean[] arr1, boolean[] arr2) {
		int arr1Len = arr1.length;
		for (int i = 0; i < arr1Len; i++) {
			arr1[i] = arr1[i] || arr2[i];
		}
		return arr1;
	}

	/**
	 * @param combin
	 * @return
	 */
	public static boolean validCombin(Ruler[] combin) {

		int dataLen = combin.length;
		int rulerLen = combin[0].getRuler().length;

		// base means all positions should be true
		boolean[] base = new boolean[rulerLen];
		Arrays.fill(base, true);

		// or all rows as the compared
		boolean[] frow = combin[0].getRuler();
		boolean[] comp = Arrays.copyOf(frow, frow.length);

		for (int i = 1; i < dataLen; i++) {
			boolean[] row = combin[i].getRuler();
			comp = arrayOr(comp, row);
		}

		// compare base with the compared
		return verifyUnsort(base, comp);

	}

	/**
	 * get the list of all r out of n combinations for the ruler array
	 * @param rulerArr,
	 * the options to be chosen
	 * @param r,
	 * choose r out of the n options
	 * @return
	 */
	public static List<Ruler[]> getAllRoutOfNCombines(Ruler[] rulerArr, int r) {
		List<Ruler[]> result = new ArrayList<>();
		int n = rulerArr.length;
		if (n <= 0 || n < r)
			return result;

		List<Ruler> combinTry = new ArrayList<>(r);
		combineDfs(rulerArr, r, 0, combinTry, result); // because it need to
														// begin from 1

		return result;
	}

	/**
	 * a dfs helper for get the combinations.
	 * @param combinTry
	 * @return
	 */
	private static void combineDfs(Ruler[] rulerArr, int r, int start, List<Ruler> combinTry, List<Ruler[]> res) {
		if (combinTry.size() == r) {
			/*
			 * to avoid operation on the same object. the return should contains
			 * different
			 * objects
			 */
			Ruler[] combinValid = copyRulerArr(combinTry);
			res.add(combinValid);
			return;
		}
		int n = rulerArr.length;
		for (int i = start; i < n; i++) {
			combinTry.add(rulerArr[i]);
			combineDfs(rulerArr, r, i + 1, combinTry, res);
			combinTry.remove(combinTry.size() - 1);
		}
	}

	/**
	 * copy a ruler list in the middle step into an array as another object so
	 * that
	 * the modification on the ruler list in the middle step will not affect the
	 * array
	 * @param rulerList
	 * @return
	 */
	private static Ruler[] copyRulerArr(List<Ruler> rulerList) {
		int itemLen = rulerList.size();
		Ruler[] newItem = new Ruler[itemLen];
		for (int i = 0; i < itemLen; i++) {
			// not necessary deep copy since we just read rather than write to
			// ruler.
			newItem[i] = rulerList.get(i);
		}
		return newItem;

	}

	/**
	 * by combination, we can a solution with key string, we need convert the
	 * key
	 * string to valid vertex
	 * @param combin,
	 * the combination of rulers. each ruler has a key string
	 * @param typeDomingMap,
	 * a key string pointing a list of valid vertex, we can choose any
	 * one, so far we choose the first one
	 * @return
	 */
	public static int[] convertCombinToIdxSet(Ruler[] combin, Map<String, Set<Integer>> typeDomingMap, int[] d2) {
		int r = combin.length;
		int[] rtn = new int[r];
		int d2Len = d2.length;
		for (int i = 0; i < r; i++) {
			Ruler ruler = combin[i];
			String key = ruler.getKey();
			Set<Integer> vIdxs = typeDomingMap.get(key);
			boolean findInD2 = false;
			// we try to find the vertex which has already in the dominating set
			for (int j = 0; j < d2Len; j++) {
				if (vIdxs.contains(d2[j])) {
					findInD2 = true;
					rtn[i] = d2[j];
				}
			}

			if (!findInD2) {
				// if we can't, we just take the first one.
				Iterator<Integer> vIdxsIt = vIdxs.iterator();
				rtn[i] = vIdxsIt.next();
			}
		}
		return rtn;
	}

	/**
	 * verify if the array expect is same as output which are in original order
	 * (unsorted)
	 * @param expect
	 * @param output
	 * @return
	 */
	public static boolean verifyUnsort(boolean[] expect, boolean[] output) {
		String expectStr = arrayToString(expect);
		String outputStr = arrayToString(output);

		boolean cmp = expectStr.equals(outputStr);
		return cmp;
	}
}