package usi.sdm.a5.sorts;


import ch.usi.si.codelounge.jsicko.Contract;

@SuppressWarnings("unchecked")
public class MergeSort<T extends Comparable<T>> {

    public static enum SPACE_TYPE { IN_PLACE, NOT_IN_PLACE }

    private MergeSort() { }

    public static <T extends Comparable<T>> T[] sort(SPACE_TYPE type, T[] unsorted) {
        sort(type, 0, unsorted.length, unsorted);
        return unsorted;
    }
    @Contract.Requires("indexesInBound")
    @Contract.Ensures("sortedInGivenBound")
    private static <T extends Comparable<T>> void sort(SPACE_TYPE type, int start, int length, T[] unsorted) {
        if (length > 2) {
            int aLength = (int) Math.floor(length / 2);
            int bLength = length - aLength;
            sort(type, start, aLength, unsorted);
            sort(type, start + aLength, bLength, unsorted);
            if (type == SPACE_TYPE.IN_PLACE)
                mergeInPlace(start, aLength, start + aLength, bLength, unsorted);
            else
                mergeWithExtraStorage(start, aLength, start + aLength, bLength, unsorted);
        } else if (length == 2) {
            T e = unsorted[start + 1];
            if (e.compareTo(unsorted[start]) < 0) {
                unsorted[start + 1] = unsorted[start];
                unsorted[start] = e;
            }
        }
    }

    @Contract.Requires("subarraysInBound")
    @Contract.Ensures("sortedInGivenBounds")
    private static <T extends Comparable<T>> void mergeInPlace(int aStart, int aLength, int bStart, int bLength, T[] unsorted) {
        int i = aStart;
        int j = bStart;
        int aSize = aStart + aLength;
        int bSize = bStart + bLength;
        while (i < aSize && j < bSize) {
            T a = unsorted[i];
            T b = unsorted[j];
            if (b.compareTo(a) < 0) {
                // Shift everything to the right one spot
                System.arraycopy(unsorted, i, unsorted, i+1, j-i);
                unsorted[i] = b;
                i++;
                j++;
                aSize++;
            } else {
                i++;
            }
        }
    }

    @Contract.Requires("subarraysInBound")
    @Contract.Ensures("sortedInGivenBounds")
    private static <T extends Comparable<T>> void mergeWithExtraStorage(int aStart, int aLength, int bStart, int bLength, T[] unsorted) {
        int count = 0;
        T[] output = (T[]) new Comparable[aLength + bLength];
        int i = aStart;
        int j = bStart;
        int aSize = aStart + aLength;
        int bSize = bStart + bLength;
        while (i < aSize || j < bSize) {
            T a = null;
            if (i < aSize) {
                a = unsorted[i];
            }
            T b = null;
            if (j < bSize) {
                b = unsorted[j];
            }
            if (a != null && b == null) {
                output[count++] = a;
                i++;
            } else if (b != null && a == null) {
                output[count++] = b;
                j++;
            } else if (b != null && b.compareTo(a) <= 0) {
                output[count++] = b;
                j++;
            } else {
                output[count++] = a;
                i++;
            }
        }
        int x = 0;
        int size = aStart + aLength + bLength;
        for (int y = aStart; y < size; y++) {
            unsorted[y] = output[x++];
        }
    }


    // ########## PRE AND POST CONDITIONS FUNCTIONS ##########

    @Contract.Pure
    public static <T extends Comparable<T>> boolean sortedInGivenBounds(int aStart, int aLength, int bStart, int bLength, T[] unsorted) {
        int aSize = aStart + aLength - 1;
        int bSize = bStart + bLength - 1;
        for (int a = aStart; a < aSize; ++a) {
            if (unsorted[a+1].compareTo(unsorted[a]) < 0)
                return false;
        }
        for (int b = bStart; b < bSize; ++b) {
            if (unsorted[b+1].compareTo(unsorted[b]) < 0)
                return false;
        }
        return true;
    }

    @Contract.Pure
    public static <T extends Comparable<T>> boolean sortedInGivenBound(int start, int length, T[] unsorted) {
        int size = start + length - 1;
        for (int a = start; a < size; ++a) {
            if (unsorted[a+1].compareTo(unsorted[a]) < 0)
                return false;
        }
        return true;
    }

    @Contract.Pure
    public static <T extends Comparable<T>> boolean indexesInBound(int start, int length, T[] unsorted) {
        return length - start < unsorted.length/2;
    }

    @Contract.Pure
    public static <T extends Comparable<T>> boolean subarraysInBound(int aStart, int aLength, int bStart, int bLength, T[] unsorted) {
        return (aStart + aLength) < unsorted.length/2 && (bStart + bLength) > unsorted.length/2 && (bStart + bLength) <= unsorted.length;
    }
}
