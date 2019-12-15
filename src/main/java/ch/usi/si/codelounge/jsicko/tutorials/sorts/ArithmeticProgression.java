package ch.usi.si.codelounge.jsicko.tutorials.sorts;

import ch.usi.si.codelounge.jsicko.Contract;

/**
 * Compute the result of adding a sequence of numbers from N (startNumber) to N+X (startNumber+numberOfNumbersToCompute)
 * <p>
 * @see <a href="https://en.wikipedia.org/wiki/Arithmetic_progression">Arithmetic Progression (Wikipedia)</a>
 * <br>
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class ArithmeticProgression {

    /**
     * Compute the result of adding X (numberOfNumbersToCompute) together starting at N (startNumber).
     * <p>
     * e.g. result = N + (N+1) + (N+2) + (N+3) + ..... + (N+X)
     */
    @Contract.Pure
    @Contract.Requires("number_not_empty")
    @Contract.Ensures("ensure_both_methods_returns_same")
    public static final long sequenceTotalUsingLoop(int startNumber, int numberOfNumbersToCompute) {
        int start = startNumber;
        int length = numberOfNumbersToCompute;
        long result = 0L;
        while (length > 0) {
            result += start++;
            length--;
        }
        return result;
    }

    /**
     * Compute the result of adding X (numberOfNumbersToCompute) together starting at N (startNumber) using triangular numbers.
     * <p>
     * e.g. result = N + (N+1) + (N+2) + (N+3) + ..... + (N+X)<br>
     * <br>
     * @see <a href="https://en.wikipedia.org/wiki/Triangular_number">Triangular Number (Wikipedia)</a>
     */
    @Contract.Pure
    @Contract.Requires("number_not_empty")
    @Contract.Ensures("ensure_both_methods_returns_same")
    public static final long sequenceTotalUsingTriangularNumbers(int startNumber, int numberOfNumbersToCompute) {
        // n*(n+1)/2
        final int start = startNumber;
        final int length = numberOfNumbersToCompute;

        long result = length * (length + 1) / 2;
        result += (start - 1) * length;
        return result;
    }

    @Contract.Pure
    public static boolean ensure_both_methods_returns_same(int startNumber, int numberOfNumbersToCompute){
        return  sequenceTotalUsingLoop(startNumber, numberOfNumbersToCompute) ==
                sequenceTotalUsingTriangularNumbers(startNumber, numberOfNumbersToCompute);
    }

    @Contract.Pure
    public  static boolean number_not_empty(int startNumber, int numberOfNumbersToCompute){
        return (startNumber >=0 && numberOfNumbersToCompute > 0);
    }


}
