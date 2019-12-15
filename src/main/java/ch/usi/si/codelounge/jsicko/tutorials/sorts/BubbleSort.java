package ch.usi.si.codelounge.jsicko.tutorials.sorts;

import ch.usi.si.codelounge.jsicko.Contract;
import static ch.usi.si.codelounge.jsicko.Contract.old;


public class BubbleSort<T extends Comparable<T>> implements Contract{

    private BubbleSort() { }


    @Ensures({"nonempty", "size_not_changed"})
    public static <T extends Comparable<T>> T[] sort(T[] unsorted) {
        boolean swapped = true;
        int length = unsorted.length;
        while (swapped) {
            swapped = false;
            for (int i = 1; i < length; i++) {
                if (unsorted[i].compareTo(unsorted[i - 1]) < 0) {
                    swap(i, i - 1, unsorted);
                    swapped = true;
                }
            }
            length--;
        }
        return unsorted;
    }


    @Requires("nonempty")
    private static <T extends Comparable<T>> void swap(int index1, int index2, T[] unsorted) {
        T value = unsorted[index1];
        unsorted[index1] = unsorted[index2];
        unsorted[index2] = value;
    }

    @Pure
    public static  <T extends Comparable<T>>  boolean nonempty(T[] unsorted) {
        return unsorted.length > 0;
    }

    @Pure
    protected boolean size_not_changed(T[] unsorted) {
        return unsorted.length == old(unsorted).length;
    }



}
