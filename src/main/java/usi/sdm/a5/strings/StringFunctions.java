package usi.sdm.a5.strings;

import ch.usi.si.codelounge.jsicko.Contract;

import java.util.BitSet;
import java.util.StringTokenizer;

import static ch.usi.si.codelounge.jsicko.Contract.*;
import static ch.usi.si.codelounge.jsicko.Contract.old;

public class StringFunctions {

    private static final char SPACE = ' ';

    @Pure
    @Requires("stringExists")
    @Ensures("stringIsReversed")
    public static final String reverseWithStringConcat(String string) {
        String output = new String();
        for (int i = (string.length() - 1); i >= 0; i--) {
            output += (string.charAt(i));
        }
        return output;
    }

    @Pure
    @Requires("stringExists")
    @Ensures("stringIsReversed")
    public static final String reverseWithStringBuilder(String string) {
        final StringBuilder builder = new StringBuilder();
        for (int i = (string.length() - 1); i >= 0; i--) {
            builder.append(string.charAt(i));
        }
        return builder.toString();
    }

    @Pure
    @Requires("stringExists")
    @Ensures("stringIsReversed")
    public static final String reverseWithStringBuilderBuiltinMethod(String string) {
        final StringBuilder builder = new StringBuilder(string);
        return builder.reverse().toString();
    }

    @Pure
    @Requires("stringExists")
    @Ensures("stringIsReversed")
    public static final String reverseWithSwaps(String string) {
        final char[] array = string.toCharArray();
        final int length = array.length - 1;
        final int half = (int) Math.floor(array.length / 2);

        char c;
        for (int i = length; i >= half; i--) {
            c = array[length - i];
            array[length - i] = array[i];
            array[i] = c;
        }
        return String.valueOf(array);
    }

    @Pure
    @Requires("stringExists")
    @Ensures("stringIsReversed")
    public static final String reverseWithXOR(String string) {
        final char[] array = string.toCharArray();
        final int length = array.length;
        final int half = (int) Math.floor(array.length / 2);

        for (int i = 0; i < half; i++) {
            array[i] ^= array[length - i - 1];
            array[length - i - 1] ^= array[i];
            array[i] ^= array[length - i - 1];
        }
        return String.valueOf(array);
    }

    @Pure
    @Requires("stringExists")
    @Ensures("stringIsReversed")
    public static final String reverseWordsByCharWithAdditionalStorage(String string) {
        final StringBuilder builder = new StringBuilder();
        final int length = string.length() - 1;
        final StringBuilder temp = new StringBuilder();

        char c = 0;
        int index = 0;
        int last = string.length();
        for (int i = length; i >= 0; i--) {
            c = string.charAt(i);
            if (c == SPACE || i == 0) {
                index = (i == 0) ? 0 : i + 1;
                temp.append(string.substring(index, last));
                if (index != 0)
                    temp.append(c);
                builder.append(temp);
                temp.delete(0, temp.length());
                last = i;
            }
        }

        return builder.toString();
    }

    @Pure
    @Requires("stringExists")
    @Ensures("stringIsReversed")
    public static final String reverseWordsUsingStringTokenizerWithAdditionalStorage(String string) {
        final StringTokenizer st = new StringTokenizer(string);
        String output = new String();
        while (st.hasMoreTokens()) {
            output = (st.nextToken()) + ' ' + output;
        }

        return output.trim();
    }

    @Pure
    @Requires("stringExists")
    @Ensures("stringIsReversed")
    public static final String reverseWordsUsingSplitWithAdditionalStorage(String string) {
        final StringBuilder builder = new StringBuilder();
        final String[] temp = string.split(" ");

        for (int i = (temp.length - 1); i >= 0; i--) {
            builder.append(temp[i]).append(' ');
        }
        return builder.toString().trim();
    }

    @Pure
    @Requires("stringExists")
    @Ensures("stringIsReversed")
    public static final String reverseWordsInPlace(String string) {
        char[] chars = string.toCharArray();

        int lengthI = 0;
        int lastI = 0;
        int lengthJ = 0;
        int lastJ = chars.length - 1;

        int i = 0;
        char iChar = 0;
        char jChar = 0;
        while (i < chars.length && i <= lastJ) {
            iChar = chars[i];
            if (iChar == SPACE) {
                lengthI = i - lastI;
                for (int j = lastJ; j >= i; j--) {
                    jChar = chars[j];
                    if (jChar == SPACE) {
                        lengthJ = lastJ - j;
                        swapWords(lastI, i - 1, j + 1, lastJ, chars);
                        lastJ = lastJ - lengthI - 1;
                        break;
                    }
                }
                lastI = lastI + lengthJ + 1;
                i = lastI;
            } else {
                i++;
            }
        }

        return String.valueOf(chars);
    }


    @Requires({"arrayNotEmpty", "indexesInBound"})
    @Ensures("wordsAreSwapped")
    private static final void swapWords(int startA, int endA, int startB, int endB, char[] array) {
        int lengthA = endA - startA + 1;
        int lengthB = endB - startB + 1;

        int length = lengthA;
        if (lengthA > lengthB)
            length = lengthB;

        int indexA = 0;
        int indexB = 0;
        char c = 0;
        for (int i = 0; i < length; i++) {
            indexA = startA + i;
            indexB = startB + i;

            c = array[indexB];
            array[indexB] = array[indexA];
            array[indexA] = c;
        }

        if (lengthB > lengthA) {
            length = lengthB - lengthA;
            int end = 0;
            for (int i = 0; i < length; i++) {
                end = endB - ((length - 1) - i);
                c = array[end];
                shiftRight(endA + i, end, array);
                array[endA + 1 + i] = c;
            }
        } else if (lengthA > lengthB) {
            length = lengthA - lengthB;
            for (int i = 0; i < length; i++) {
                c = array[endA];
                shiftLeft(endA, endB, array);
                array[endB + i] = c;
            }
        }
    }

    @Requires("startAndEnd")
    @Ensures("shiftRightCorrect")
    private static final void shiftRight(int start, int end, char[] array) {
        for (int i = end; i > start; i--) {
            array[i] = array[i - 1];
        }
    }

    @Requires("startAndEnd")
    @Ensures("shiftLeftCorrect")
    private static final void shiftLeft(int start, int end, char[] array) {
        for (int i = start; i < end; i++) {
            array[i] = array[i + 1];
        }
    }

    // ########## INVARIANTS, PRE AND POST CONDITIONS FUNCTIONS ##########

    @Invariant
    @Pure
    public static <T extends Comparable<T>> boolean nonEmpty(char[] array) {
        return array.length > 0;
    }

    @Invariant
    @Pure
    public static <T extends Comparable<T>> boolean stringNonNull(String string) {
        return string != null;
    }

    @Pure
    static boolean stringExists (String string) {
        return string != null;
    }

    @Pure
    static boolean stringIsReversed (String string) {
        int len = string.length();
        if (len != old(string).length())
            return false;
        for (int i=0; i<len; ++i) {
            if (string.charAt(i) != old(string).charAt(len-i))
                return false;
        }
        return true;
    }


    @Pure
   static boolean shiftRightCorrect(int start, int end, char[] array) {
       for(int i = start; i< end; ++i) {
           if (array[i] != old(array)[i + 1])
               return false;
       }
       return true;
    }

    @Pure
   static boolean shiftLeftCorrect(int start, int end, char[] array) {
       for(int i = start; i< end; --i) {
           if (array[i] != old(array)[i - 1])
               return false;
       }
       return true;
    }

    @Pure
    static boolean startAndEnd(int start, int end){
        return end > start;
    }


    @Pure
    static boolean arrayNotEmpty(int startA, int endA, int startB, int endB, char[] array) {
        return array.length > 0;
    }

    @Pure
    static boolean indexesInBound(int startA, int endA, int startB, int endB, char[] array) {
        return startA > 0 && startA < endA && endA < startB && startB < endB && endB < array.length;
    }

    @Pure
    static boolean wordsAreSwapped(int startA, int endA, int startB, int endB, char[] array) {
        char[] old = old(array);
        if (array.length != old.length)
            return false;
        for (int i = startA; i<endA; ++i) {
            if (array[i] != old[startB+i])
                return false;
        }
        for (int i = startB; i<endB; ++i) {
            if (array[i] != old[startA+i])
                return false;
        }
        return true;
    }


}
