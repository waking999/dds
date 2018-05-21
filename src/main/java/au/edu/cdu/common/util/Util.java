package au.edu.cdu.common.util;

import au.edu.cdu.common.order.OrderPackageUtil;

import java.util.*;

/**
 * this is an util class for common functions
 */
public class Util {
    /**
     * avoid adding duplicated elements into a list
     *
     * @param list
     *            , the list receiving elements
     * @param e
     *            , an element
     * @return the list
     */
    public static <E> Collection<E> addElementToList(Collection<E> list, E e) {
        if (!list.contains(e)) {
            list.add(e);
        }
        return list;
    }
    /**
     * find the position of a value in a range of an array
     *
     * @param array,    the search base
     * @param arrayLen, the range
     * @param val,      the value
     * @return position or ConstantValue.IMPOSSIBLE_VALUE if not found
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
     *
     * @return a batch num by date and time
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

        StringBuilder sb = new StringBuilder();
        String monthStr = String.format("%02d", month + 1);
        String dayStr = String.format("%02d", day);
        String hourStr = String.format("%02d", hour);
        String minStr = String.format("%02d", min);

        sb.append(year).append(monthStr).append(dayStr).append("-").append(hourStr).append(minStr);

        return sb.toString();

    }

    /**
     * a set s1 minus a set s2, s1-s2
     *
     * @param s1, a set
     * @param s2, a set
     * @return the elements in set s1 not in s2
     */
    public static int[] set1Minus2(int[] s1, int s1Len, int[] s2, int s2Len) {
        if (s1 == null) {
            return null;
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
     *
     * @param s1, a set
     * @param s2, a set
     * @return the elements in set s1 not in s2
     */
    public static int[] set1Minus2(int[] s1, int s1Len, Set<Integer> s2) {
        if (s1 == null) {
            return null;
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
     *
     * @param s1, a set
     * @param s2, a set
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
     *
     * @param s1, a set
     * @param s2, a set
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
     *
     * @param s, a set
     * @return an array
     */
    static int[] convertSetToArray(Set<Integer> s) {
        int sLen = s.size();
        int[] sArr = new int[sLen];
        Iterator<Integer> s1It = s.iterator();
        int pointer = 0;
        while (s1It.hasNext()) {
            sArr[pointer++] = s1It.next();
        }
        return sArr;
    }

    /**
     * convert a boolean array to 01 string
     *
     * @param boolArr, a boolean array
     * @return a 01 string representing the boolean array
     */
    public static String arrayToString(boolean[] boolArr) {
        int boolArrSize = boolArr.length;
        char[] chArray = new char[boolArrSize];
        for (int i = 0; i < boolArrSize; i++) {
            chArray[i] = boolArr[i] ? '1' : '0';
        }
        return new String(chArray);
    }

    /**
     * to check if the two sets are intersected, s1 n s2
     *
     * @param s1,    set 1
     * @param s1Len, length of set 1
     * @param s2,    set 2
     * @param s2Len, length of set 2
     * @return true if set1 intersection s2 not null, false otherwise
     */
    public static boolean setsIntersect(int[] s1, int s1Len, int[] s2, int s2Len) {
        if((s1==null)||(s2==null)){
            return false;
        }
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
     * convert a neighbor type domed map into an array of ruler object
     *
     * @param map, a neighbor type dominated map
     * @return a ruler array
     */
    public static String[] converRulerMapToArray(Map<String, boolean[]> map) {
        Set<String> keySet = map.keySet();
        int keySetSize = keySet.size();
        String[] rtn = new String[keySetSize];
        int i = 0;
        for (String key : keySet) {
            rtn[i++] = key;
        }
        return rtn;
    }

    /**
     * to do or operation on cooresponding elements of the two boolean arrays
     *
     * @param arr1, array 1
     * @param arr2, array 2
     * @return array after elements in array 1 or that in array 2
     */
    static boolean[] arrayOr(boolean[] arr1, boolean[] arr2) {
        int arr1Len = arr1.length;
        for (int i = 0; i < arr1Len; i++) {
            arr1[i] = arr1[i] || arr2[i];
        }
        return arr1;
    }

    /**
     * @param combin, the combination
     * @return true if the combination is valid solution, false otherwise
     */
    public static boolean validCombin(String[] combin, Map<String, boolean[]> neigTypeDomedMap) {

        int dataLen = combin.length;
        boolean[] frow = neigTypeDomedMap.get(combin[0]);
        int rulerLen = frow.length;

        // base means all positions should be true
        boolean[] base = new boolean[rulerLen];
        Arrays.fill(base, true);

        // or all rows as the compared
        boolean[] comp = Arrays.copyOf(frow, frow.length);

        for (int i = 1; i < dataLen; i++) {
            boolean[] row = neigTypeDomedMap.get(combin[i]);
            comp = arrayOr(comp, row);
        }

        // compare base with the compared
        return verifyUnsort(base, comp);

    }

    static List<int[]> getAllRoutOfNCombines(int[] array, int r) {
        List<int[]> result = new ArrayList<>();
        int n = array.length;
        if (n <= 0 || n < r)
            return result;

        List<Integer> combinTry = new ArrayList<>(r);
        combineDfs(array, r, 0, combinTry, result); // because it need to
        // begin from 1

        return result;
    }

    /**
     * a dfs helper for get the combinations.
     *
     * @param array, any array as the choice base
     * @param r, how many elements in the combinations
     * @param start, start point from the array
     * @param combinTry, the combination of elements in half way
     * @param res, the container of formed combinations of r elements
     */
    private static void combineDfs(int[] array, int r, int start, List<Integer> combinTry, List<int[]> res) {
        if (combinTry.size() == r) {
            /*
             * to avoid operation on the same object. the return should contains
             * different
             * objects
             */
            int[] combinValid = copyIntArr(combinTry);
            res.add(combinValid);
            return;
        }
        int n = array.length;
        for (int i = start; i < n; i++) {
            combinTry.add(array[i]);
            combineDfs(array, r, i + 1, combinTry, res);
            combinTry.remove(combinTry.size() - 1);
        }
    }

    /**
     * copy a ruler list in the middle step into an array as another object so that the modification on the ruler list in the middle step will not affect the  array
     *
     * @param arrayList, copy list to be an array
     * @return an array
     */
    private static int[] copyIntArr(List<Integer> arrayList) {
        int itemLen = arrayList.size();
        int[] newItem = new int[itemLen];
        for (int i = 0; i < itemLen; i++) {
            // not necessary deep copy since we just read rather than write to
            // ruler.
            newItem[i] = arrayList.get(i);
        }
        return newItem;

    }

    /**
     * get the list of all r out of n combinations for the ruler array
     *
     * @param rulerKeyArr, the options to be chosen out of
     * @param r,           choose r out of the n options
     * @return the list of all r out of n combinations for the ruler array
     */
    public static List<String[]> getAllRoutOfNCombines(String[] rulerKeyArr, int r) {
        List<String[]> result = new ArrayList<>();
        int n = rulerKeyArr.length;
        if (n <= 0 || n < r)
            return result;

        List<String> combinTry = new ArrayList<>(r);
        combineDfs(rulerKeyArr, r, 0, combinTry, result); // because it need to
        // begin from 1

        return result;
    }

    /**
     * a dfs helper for get the combinations.
     *
     * @param rulerKeyArr, any array as the choice base
     * @param r, how many elements in the combinations
     * @param start, start point from the array
     * @param combinTry, the combination of elements in half way
     * @param res, the container of formed combinations of r elements
     *
     */
    private static void combineDfs(String[] rulerKeyArr, int r, int start, List<String> combinTry, List<String[]> res) {
        if (combinTry.size() == r) {
            /*
             * to avoid operation on the same object. the return should contains
             * different
             * objects
             */
            String[] combinValid = copyStringArr(combinTry);
            res.add(combinValid);
            return;
        }
        int n = rulerKeyArr.length;
        for (int i = start; i < n; i++) {
            combinTry.add(rulerKeyArr[i]);
            combineDfs(rulerKeyArr, r, i + 1, combinTry, res);
            combinTry.remove(combinTry.size() - 1);
        }
    }

    /**
     * copy a ruler list in the middle step into an array as another object so
     * that the modification on the ruler list in the middle step will not affect the
     * array
     *
     * @param rulerList, copy list to be an array
     * @return an array
     */
    private static String[] copyStringArr(List<String> rulerList) {
        int itemLen = rulerList.size();
        String[] newItem = new String[itemLen];
        for (int i = 0; i < itemLen; i++) {
            // not necessary deep copy since we just read rather than write to
            // ruler.
            newItem[i] = rulerList.get(i);
        }
        return newItem;

    }

    /**
     * by combination, we can a solution with key string, we need convert the
     * key string to valid vertex
     *
     * @param combin,        the combination of rulers. each ruler has a key string
     * @param typeDomingMap, a key string pointing a list of valid vertex, we can choose any
     *                       one, so far we choose the first one
     * @return a potential dominating vertex set
     */
    public static int[] convertCombinToIdxSet(String[] combin, Map<String, Set<Integer>> typeDomingMap, int[] d2) {
        int r = combin.length;
        int[] rtn = new int[r];
        for (int i = 0; i < r; i++) {
            String key = combin[i];
            Set<Integer> vIdxs = typeDomingMap.get(key);
            boolean findInD2 = false;
            // we try to find the vertex which has already in the dominating set
            for (int aD2 : d2) {
                if (vIdxs.contains(aD2)) {
                    findInD2 = true;
                    rtn[i] = aD2;
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
     *
     * @param expect, expect value
     * @param real, real value
     * @return true if real value equals expect value respectively, false otherwise
     */
    public static boolean verifyUnsort(boolean[] expect, boolean[] real) {
        String expectStr = arrayToString(expect);
        String outputStr = arrayToString(real);

        return expectStr.equals(outputStr);
    }

    /**
     *
     * @param n
     * @param s
     * @return
     */
    public static <T> List<T> getFirstNItemsInCollection(int n, Collection<T> s) {
        List<T> rtn = new ArrayList<T>();
        int count = 0;
        for (T t : s) {
            rtn.add(t);
            count++;
            if (count == n) {
                break;
            }
        }

        return rtn;
    }

    public static byte[] convertStringArrayToByteArray(String[] stringArray){

        int stringArraySize=stringArray.length;

        byte[] rtn=new byte[stringArraySize];

        for(int i=0;i<stringArraySize;i++){
            rtn[i]= Byte.parseByte(stringArray[i]);
        }
        return rtn;
    }
}