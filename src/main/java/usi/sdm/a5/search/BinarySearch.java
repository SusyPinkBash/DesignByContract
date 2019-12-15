package usi.sdm.a5.search;


import ch.usi.si.codelounge.jsicko.Contract;
import static ch.usi.si.codelounge.jsicko.Contract.old;

import java.util.Arrays;

public class BinarySearch {
    private static final int SWITCH_TO_BRUTE_FORCE = 200;

    private static int[] sorted = null;

    // Assuming the array is sorted
    @Contract.Pure
    @Contract.Requires({"sorted", "nonEmpty"})
    @Contract.Ensures({"valueAtIndex", "arrayHasNoChanges"})
    public static final int find(int value, int[] array, boolean optimize) {
        BinarySearch.sorted = array;
        try {
            return recursiveFind(value, 0, BinarySearch.sorted.length - 1, optimize);
        } finally {
            BinarySearch.sorted = null;
        }
    }

    @Contract.Pure
    @Contract.Requires({"sorted", "nonEmpty"})
    @Contract.Ensures({"valueAtIndex", "arrayHasNoChanges"})
    private static int recursiveFind(int value, int start, int end, boolean optimize) {
        if (start == end) {
            int lastValue = sorted[start]; // start==end
            if (value == lastValue)
                return start; // start==end
            return Integer.MAX_VALUE;
        }

        final int low = start;
        final int high = end + 1; // zero indexed, so add one.
        final int middle = low + ((high - low) / 2);

        final int middleValue = sorted[middle];
        if (value == middleValue)
            return middle;
        if (value > middleValue) {
            if (optimize && (end - middle) <= SWITCH_TO_BRUTE_FORCE)
                return linearSearch(value, middle + 1, end);
            return recursiveFind(value, middle + 1, end, optimize);
        }
        if (optimize && (end - middle) <= SWITCH_TO_BRUTE_FORCE)
            return linearSearch(value, start, middle - 1);
        return recursiveFind(value, start, middle - 1, optimize);
    }

    @Contract.Pure
    @Contract.Requires({"nonEmpty", "sorted"})
    @Contract.Ensures({"valueAtIndex", "arrayHasNoChanges"})
    private static final int linearSearch(int value, int start, int end) {
        for (int i = start; i <= end; i++) {
            int iValue = sorted[i];
            if (value == iValue)
                return i;
        }
        return Integer.MAX_VALUE;
    }

    // ########## PRE AND POST CONDITIONS FUNCTIONS ##########

    @Contract.Pure
    public static boolean nonEmpty(int[] array) {
        return array.length > 0;
    }

    @Contract.Pure
    public static <T extends Comparable<T>> boolean sorted(int[] array) {
        for (int i=0; i+1 < array.length; ++i) {
            if (array[i] > (array[i+1]))
                return false;
        }
        return true;
    }

    @Contract.Pure
    public static <T extends Comparable<T>> boolean valueAtIndex(int returns, int value, int[] array, boolean optimize) {
        return (returns != Integer.MAX_VALUE && array[returns] == value) || Arrays.stream(array).noneMatch(elem -> elem == value);
    }

    @Contract.Pure
    public static boolean arrayHasNoChanges(int[] array) {
        if (array.length != old(array.length)) return false;
        for (int i=0; i < array.length; ++i) {
            if (array[i] != old(array[i]))
                return false;
        }
        return true;
    }
}
