package usi.sdm.a5;


import static org.junit.Assert.assertTrue;

import org.junit.Test;
import usi.sdm.a5.search.BinarySearch;

import java.util.ArrayList;
import java.util.Arrays;

public class Search {

    private static final int SIZE = 9999;
    private static final int offset = 123;

    private static int[] sorted = new int[SIZE];

    static {
        for (int i = offset; i < offset + sorted.length; i++) {
            sorted[i - offset] = i;
        }
    }

    private static int valueIndex = SIZE - (SIZE / 4);
    private static int valueInArray = sorted[valueIndex];
    private static int valueNotInArray = sorted[SIZE - 1] + offset;

    @Test
    public void testBinarySearch() {
        int index = BinarySearch.find(valueInArray, sorted, false);
        assertTrue("Brute force error. expected=" + valueIndex + " got=" + index, (index == valueIndex));
        index = BinarySearch.find(valueNotInArray, sorted, false);
        assertTrue("Brute force error. expected=" + Integer.MAX_VALUE + " got=" + index, (index == Integer.MAX_VALUE));
    }

}
