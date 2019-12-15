package usi.sdm.a5;

import java.util.Arrays;

import org.junit.Test;
import usi.sdm.a5.strings.StringFunctions;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class Strings {

    @Test
    public void testReverseCharsInString() {
        // REVERSE CHARS IN A STRING
        String string = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String check = "zyxwvutsrqponmlkjihgfedcbaZYXWVUTSRQPONMLKJIHGFEDCBA";
        String result = StringFunctions.reverseWithStringConcat(string);
        assertTrue("Reverse With String Concat error. expect="+check+" got="+result, check.equals(result));

        result = StringFunctions.reverseWithStringBuilder(string);
        assertTrue("Reverse With String String Builder error. expect="+check+" got="+result, check.equals(result));

        result = StringFunctions.reverseWithStringBuilderBuiltinMethod(string);
        assertTrue("Reverse With Built-in error. expect="+check+" got="+result, check.equals(result));

        result = StringFunctions.reverseWithSwaps(string);
        assertTrue("Reverse With Swaps error. expect="+check+" got="+result, check.equals(result));

        result = StringFunctions.reverseWithXOR(string);
        assertTrue("Reverse With XOR error. expect="+check+" got="+result, check.equals(result));
    }

    @Test
    public void testReverseWordsInString() {
        // REVERSE WORDS IN A STRING
        String string = "Could you pretty please reverse this sentence";
        String check = "sentence this reverse please pretty you Could";
        String result = StringFunctions.reverseWordsByCharWithAdditionalStorage(string);
        assertTrue("Reverse Words By Char w/ Additional Storage error. expect="+check+" got="+result, check.equals(result));

        result = StringFunctions.reverseWordsUsingStringTokenizerWithAdditionalStorage(string);
        assertTrue("Reverse Words Using String Tokenizer w/ Additional Storage error. expect="+check+" got="+result, check.equals(result));

        result = StringFunctions.reverseWordsUsingSplitWithAdditionalStorage(string);
        assertTrue("Reverse Words Using Split w/ Additional Storage error. expect="+check+" got="+result, check.equals(result));

        result = StringFunctions.reverseWordsInPlace(string);
        assertTrue("Reverse Words In-Place error. expect="+check+" got="+result, check.equals(result));
    }
}