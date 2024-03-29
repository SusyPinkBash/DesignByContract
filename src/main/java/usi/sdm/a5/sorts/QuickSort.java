package usi.sdm.a5.sorts;

import ch.usi.si.codelounge.jsicko.Contract;
import static ch.usi.si.codelounge.jsicko.Contract.old;
import java.util.Random;

/**
 * Quicksort is a sorting algorithm which, on average, makes O(n*log n) comparisons to sort
 * n items. In the worst case, it makes O(n^2) comparisons, though this behavior is
 * rare. Quicksort is often faster in practice than other algorithms. 
 * <p>
 * Family: Divide and conquer.<br> 
 * Space: In-place.<br>
 * Stable: False.<br>
 * <p>
 * Average case = O(n*log n)<br>
 * Worst case = O(n^2)<br>
 * Best case = O(n) [three-way partition and equal keys]<br>
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Quick_sort">Quicksort (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class QuickSort<T extends Comparable<T>> implements Contract {

    private static final Random RAND = new Random();

    public static enum PIVOT_TYPE {
        FIRST, MIDDLE, RANDOM
    }

    public static PIVOT_TYPE type = PIVOT_TYPE.RANDOM;

    private QuickSort() { }


    public static <T extends Comparable<T>> T[] sort(PIVOT_TYPE pivotType, T[] unsorted) {
        int pivot = 0;
        if (pivotType == PIVOT_TYPE.MIDDLE) {
            pivot = unsorted.length/2;
        } else if (pivotType == PIVOT_TYPE.RANDOM) {
            pivot = getRandom(unsorted.length);  
        }
        sort(pivot, 0, unsorted.length - 1, unsorted);
        return unsorted;
    }

    @Requires({"start_less_than_finish, indexInBound"})
    @Ensures("sorted")
    private static <T extends Comparable<T>> void sort(int index, int start, int finish, T[] unsorted) {
        int pivotIndex = start + index;
        T pivot = unsorted[pivotIndex];
        int s = start;
        int f = finish;
        while (s <= f) {
            while (unsorted[s].compareTo(pivot) < 0)
                s++;
            while (unsorted[f].compareTo(pivot) > 0)
                f--;
            if (s <= f) {
                swap(s, f, unsorted);
                s++;
                f--;
            }
        }
        if (start < f) {
            pivotIndex = getRandom((f - start) + 1);
            sort(pivotIndex, start, f, unsorted);
        }
        if (s < finish) {
            pivotIndex = getRandom((finish - s) + 1);
            sort(pivotIndex, s, finish, unsorted);
        }
    }

    @Pure
    private static final int getRandom(int length) {
        if (type == PIVOT_TYPE.RANDOM && length > 0)
            return RAND.nextInt(length);
        if (type == PIVOT_TYPE.FIRST && length > 0)
            return 0;
        return length / 2;
    }

    @Requires("indexesInBound")
    @Ensures({"swapped", "sizeNotChanged"})
    private static <T extends Comparable<T>> void swap(int index1, int index2, T[] unsorted) {
        T index2Element = unsorted[index1];
        unsorted[index1] = unsorted[index2];
        unsorted[index2] = index2Element;
    }


    // ########## INVARIANTS, PRE AND POST CONDITIONS FUNCTIONS ##########

    @Invariant
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
    public static <T extends Comparable<T>> boolean indexInBound(int index, int start, int finish, T[] unsorted) {
        return index < unsorted.length;
    }

    @Pure
    static boolean start_less_than_finish(int start, int finish) {
        return start < finish;
    }

    @Pure
    public static <T extends Comparable<T>> boolean swapped(int index1, int index2, T[] unsorted) {
        return (old(unsorted[index1]) == unsorted[index2] && (old(unsorted[index2]) == unsorted[index1]));
    }

    @Pure
    public static <T extends Comparable<T>> boolean indexesInBound(int index1, int index2, T[] unsorted) {
        return index1 >= 0 && index1 < index2 && index2 < unsorted.length;
    }

    @Pure
    protected static <T extends Comparable<T>> boolean sizeNotChanged(T[] unsorted) {
        return unsorted.length == old(unsorted).length;
    }
}
