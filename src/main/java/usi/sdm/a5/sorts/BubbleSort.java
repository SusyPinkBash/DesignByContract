package usi.sdm.a5.sorts;

import ch.usi.si.codelounge.jsicko.Contract;
import static ch.usi.si.codelounge.jsicko.Contract.old;


public class BubbleSort<T extends Comparable<T>> implements Contract{

    private BubbleSort() { }

    @Requires("nonEmpty")
    @Ensures({"sorted", "sizeNotChanged"})
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


    @Requires({"nonEmpty", "indexesInBound"})
    @Ensures({"swapped", "sizeNotChanged"})
    private static <T extends Comparable<T>> void swap(int index1, int index2, T[] unsorted) {
        T value = unsorted[index1];
        unsorted[index1] = unsorted[index2];
        unsorted[index2] = value;
    }


    // ########## PRE AND POST CONDITIONS FUNCTIONS ##########

    @Pure
    public static <T extends Comparable<T>> boolean nonEmpty(T[] unsorted) {
        return unsorted.length > 0;
    }

    @Pure
    public static <T extends Comparable<T>> boolean sorted(T[] unsorted) {
        for (int i =0; i < unsorted.length-1; ++i){
            if (unsorted[i+1].compareTo(unsorted[i]) < 0)
                return false;
        }
        return true;
    }

    @Pure
    public static <T extends Comparable<T>> boolean indexesInBound(int index1, int index2, T[] unsorted) {
        return index1 >= 0 && index1 < index2 && index2 < unsorted.length;
    }


    @Pure
    public static <T extends Comparable<T>> boolean swapped(int index1, int index2, T[] unsorted) {
        return (old(unsorted[index1]) == unsorted[index2] && (old(unsorted[index2]) == unsorted[index1]));
    }

    @Pure
    protected static <T extends Comparable<T>> boolean sizeNotChanged(T[] unsorted) {
        return unsorted.length == old(unsorted).length;

    }

}
