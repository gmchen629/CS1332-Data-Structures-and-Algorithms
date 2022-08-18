import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Guanming Chen
 * @version 1.0
 * @userid gchen353
 * @GTID 903661790
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the buildFailureTable() method before implementing
     * this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new java.lang.IllegalArgumentException("Error, pattern is null or has length 0");
        } else if (text == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("Error, text or comparator is null");
        }
        List<Integer> idxList = new ArrayList<>();
        int i = 0;
        int j = 0;
        int m = pattern.length();
        int n =  text.length();
        if (m > n) {
            return idxList;
        }
        int[] ft = buildFailureTable(pattern, comparator);
        while (i < n) {
            if (i - j > n - m) {
                return idxList;
            }
            if (comparator.compare(pattern.charAt(j), text.charAt(i)) == 0) {
                if (j < m - 1) {
                    i++;
                    j++;
                } else {
                    idxList.add(i - j);
                    i++;
                    j = ft[j];
                }
            } else if (j == 0) {
                j++;
            } else {
                j = ft[j - 1];
            }
        }
        return idxList;
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will contain the length of the largest prefix
     * of the pattern indices [0..i] that is also a suffix of the pattern
     * indices [1..i]. This means that index 0 of the returned table will always
     * be equal to 0
     *
     * Ex. pattern = ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("Error, the pattern or comparator is null");
        }
        int[] failureTable = new int[pattern.length()];
        failureTable[0] = 0;
        int i = 0;
        int j = 1;
        while (j < pattern.length()) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                failureTable[j] = i + 1;
                i++;
                j++;
            } else if (i == 0) {
                failureTable[j] = 0;
                j++;
            } else {
                i = failureTable[i - 1];
            }
        }
        return failureTable;
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the buildLastTable() method before implementing
     * this method.
     *
     * Note: You may find the getOrDefault() method from Java's Map class
     * useful.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new java.lang.IllegalArgumentException("Error, pattern is null or has length 0");
        } else if (text == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("Error, text or comparator is null");
        }
        Map<Character, Integer> lt = buildLastTable(pattern);
        List<Integer> idxList = new ArrayList<>();
        int m = pattern.length();
        int n =  text.length();
        if (m > n) {
            return idxList;
        }
        int i = 0;
        while (i <= n - m) {
            int j = m - 1;
            while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                j--;
            }
            if (j == -1) {
                idxList.add(i);
                i++;
            } else {
                int shift = lt.getOrDefault(text.charAt(i + j), -1);
                if (shift < j) {
                    i = i + j - shift;
                } else {
                    i++;
                }
            }
        }
        return idxList;
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. pattern = octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new java.lang.IllegalArgumentException("Error, the pattern is null");
        }
        Map<Character, Integer> lastTable = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            lastTable.put(pattern.charAt(i), i);
        }
        return lastTable;
    }

    public class PatternMatching1 {

        /**
         * Boyer Moore algorithm that relies on last occurrence table. Works better
         * with large alphabets.
         *
         * If the pattern is not found within the text or there are less than
         * two occurrences the algorithm returns -1.
         *
         * Use the buildLastTable() method provided within the PatternMatching class.
         *
         * Note: You may find the getOrDefault() method from Java's Map class
         * useful.
         *
         * @param pattern    the pattern you are searching for in a body of text
         * @param text       the body of text where you search for the pattern
         * @return int       the starting index of the second occurrence of the pattern
         *                   found in the text or -1 otherwise.
         * @throws java.lang.IllegalArgumentException if the pattern is null or has
         *                                            length 0
         * @throws java.lang.IllegalArgumentException if text is null
         */
        public int boyerMoore(CharSequence pattern, CharSequence text) {
            // FINISH THIS METHOD HERE
            if (pattern == null || pattern.length() == 0) {
                throw new java.lang.IllegalArgumentException("Error, pattern is null or has length 0");
            } else if (text == null) {
                throw new java.lang.IllegalArgumentException("Error, text or comparator is null");
            }
            int m = pattern.length();
            int n =  text.length();
            if (m > n) {
                return -1;
            }
            Map<Character, Integer> lt = buildLastTable(pattern);
            List<Integer> idxList = new ArrayList<>();
            int i = 0;
            while (i <= n - m) {
                int j = m - 1;
                while (j >= 0 && text.charAt(i + j) == pattern.charAt(j)) {
                    j--;
                }
                if (j == -1) {
                    idxList.add(i);
                    i++;
                } else {
                    int shift = lt.getOrDefault(text.charAt(i + j), -1);
                    if (shift < j) {
                        i = i + j - shift;
                    } else {
                        i++;
                    }
                }
            }
            return idxList.get(1);
        }

        public Map<Character, Integer> buildLastTable(CharSequence pattern) {
            if (pattern == null) {
                throw new java.lang.IllegalArgumentException("Error, the pattern is null");
            }
            Map<Character, Integer> lastTable = new HashMap<>();
            for (int i = 0; i < pattern.length(); i++) {
                lastTable.put(pattern.charAt(i), i);
            }
            return lastTable;
        }
    }
    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 2;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i)
     *   c is the integer value of the current character, and
     *   i is the index of the character
     *
     * We recommend building the hash for the pattern and the first m characters
     * of the text by starting at index (m - 1) to efficiently exponentiate the
     * BASE. This allows you to avoid using Math.pow().
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to calculate BASE^(m - 1).
     *
     * Ex. Hashing "bunn" as a substring of "bunny" with base 113
     * = (b * 113 ^ 3) + (u * 113 ^ 2) + (n * 113 ^ 1) + (n * 113 ^ 0)
     * = (98 * 113 ^ 3) + (117 * 113 ^ 2) + (110 * 113 ^ 1) + (110 * 113 ^ 0)
     * = 142910419
     *
     * Another key point of this algorithm is that updating the hash from
     * one substring to the next substring must be O(1). To update the hash,
     * subtract the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar as shown by this formula:
     * (oldHash - oldChar * BASE ^ (pattern.length - 1)) * BASE + newChar
     *
     * Ex. Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y
     *              = (142910419 - 98 * 113 ^ 3) * 113 + 121
     *              = 170236090
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^(m - 1) is for updating the hash.
     *
     * Do NOT use Math.pow() in this method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new java.lang.IllegalArgumentException("Error, pattern is null or has length 0");
        } else if (text == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("Error, text or comparator is null");
        }
        List<Integer> idxList = new ArrayList<>();
        int m = pattern.length();
        int n =  text.length();
        if (m > n) {
            return idxList;
        }
        int patternHash = rollingHash(pattern);
        int textSubStringHash = -1;
        for (int i = 0; i <= n - m; i++) {
            if (i == 0) {
                textSubStringHash = rollingHash(text.subSequence(0, m));
            } else {
                textSubStringHash = (textSubStringHash - (text.charAt(i - 1) * pow(BASE, m - 1))) * BASE
                        + text.charAt(i + m - 1);
            }
            if (patternHash == textSubStringHash) {
                if (compareSubString(pattern, text.subSequence(i, i + m), comparator)) {
                    idxList.add(i);
                    System.out.println(i);
                }
            }
        }
        return idxList;
    }

    /**
     * Rolling Hash method for rabinKarp
     * @param pattern the charSequence to calculate the new hash code
     * @return the new hashcode after rolling hash
     */
    private static int rollingHash(CharSequence pattern) {
        int rHash = 0;
        for (int i = 0; i < pattern.length(); i++) {
            rHash += pattern.charAt(i) * pow(BASE, pattern.length() - 1 - i);
        }
        return rHash;
    }

    /**
     * Pow method for rabinKarp
     * @param num the number to calculate the result
     * @param exp the exponent to calculate the result
     * @return the value after calculation
     */
    private static int pow(int num, int exp) {
        int res = 1;
        if (exp == 0) {
            return 1;
        } else {
            for (int i = 0; i < exp; i++) {
                res *= num;
            }
        }
        return res;
    }

    /**
     * Check equality of two sequences for rabinKarp
     * @param pattern the pattern string to search in the text
     * @param text the text string to search the pattern
     * @param comparator the helper to check if characters are equal
     * @return true if pattern matches the text
     */
    private static boolean compareSubString(CharSequence pattern, CharSequence text,
                                 CharacterComparator comparator) {
        for (int i = 0; i < pattern.length(); i++) {
            if (comparator.compare(pattern.charAt(i), text.charAt(i)) != 0) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        CharacterComparator comparator = new CharacterComparator();
        PatternMatching.rabinKarp("400", "161522401361", comparator);
    }
}


