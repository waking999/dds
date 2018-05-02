package au.edu.cdu.common.util;

import java.util.Arrays;

public class Queue {
    private int[] list;
    private int size;
    private int startCursor;
    private int endCursor;

    Queue(int size) {
        this.size = size;
        list = new int[this.size];
        Arrays.fill(list, ConstantValue.IMPOSSIBLE_VALUE);
        startCursor = 0;
        endCursor = 0;
    }

    public void add(int val) {
        list[endCursor++] = val;
        if (endCursor == this.size) {
            if (startCursor == 0) {
                this.size = 2 * this.size;
                list = Arrays.copyOf(list, this.size);
            } else {
                list = Arrays.copyOf(Arrays.copyOfRange(list, startCursor, endCursor), this.size);
                endCursor = endCursor - startCursor;
                startCursor = 0;

            }
        }
    }

    int poll() {
        int rtn = list[startCursor];
        startCursor++;
        return rtn;
    }


    boolean isEmpty() {
        return startCursor == endCursor;
    }


}
