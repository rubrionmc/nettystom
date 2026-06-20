// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class StringUtils {

    // Assigns a value
    public static final String SPACE = " ";
    // Assigns a value
    public static final char SPACE_CHAR = ' ';

    // Start of a method/block
    public static int countMatches(final CharSequence str, final char ch) {
        // Branch: checks a condition
        if (str.isEmpty()) {
            // Returns a value to the caller
            return 0;
        // End of a block/expression
        }
        // Assigns a value
        int count = 0;
        // We could also call str.toCharArray() for faster look ups but that would generate more garbage.
        // Loop: repeats a block
        for (int i = 0; i < str.length(); i++) {
            // Branch: checks a condition
            if (ch == str.charAt(i)) {
                // Code statement
                count++;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return count;
    // End of a block/expression
    }

    /**
     * Applies the Jaro-Winkler distance algorithm to the given strings, providing information about the
     * similarity of them.
     *
     * @param s1 The first string that gets compared. May be null or empty.
     * @param s2 The second string that gets compared. May be null or empty.
     * @return The Jaro-Winkler score (between 0.0 and 1.0), with a higher value indicating larger similarity.
     * @author Thomas Trojer thomas@trojer.net
     */
    // Start of a method/block
    public static double jaroWinklerScore(final @Nullable String s1, final @Nullable String s2) {
        // lowest score on empty strings
        // Branch: checks a condition
        if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) {
            // Returns a value to the caller
            return 0;
        // End of a block/expression
        }
        // highest score on equal strings
        // Branch: checks a condition
        if (s1.equals(s2)) {
            // Returns a value to the caller
            return 1;
        // End of a block/expression
        }
        // some score on different strings
        // Assigns a value
        int prefixMatch = 0; // exact prefix matches
        // Assigns a value
        int matches = 0; // matches (including prefix and ones requiring transpostion)
        // Assigns a value
        int transpositions = 0; // matching characters that are not aligned but close together
        // Calls a method
        int maxLength = Math.max(s1.length(), s2.length());
        // Assigns a value
        int maxMatchDistance = Math.max((int) Math.floor(maxLength / 2.0) - 1, 0); // look-ahead/-behind to limit transposed matches
        // comparison
        // Calls a method
        final String shorter = s1.length() < s2.length() ? s1 : s2;
        // Calls a method
        final String longer = s1.length() >= s2.length() ? s1 : s2;
        // Loop: repeats a block
        for (int i = 0; i < shorter.length(); i++) {
            // check for exact matches
            // Calls a method
            boolean match = shorter.charAt(i) == longer.charAt(i);
            // Branch: checks a condition
            if (match) {
                // Branch: checks a condition
                if (i < 4) {
                    // prefix match (of at most 4 characters, as described by the algorithm)
                    // Code statement
                    prefixMatch++;
                // End of a block/expression
                }
                // Code statement
                matches++;
                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }
            // check fro transposed matches
            // Loop: repeats a block
            for (int j = Math.max(i - maxMatchDistance, 0); j < Math.min(i + maxMatchDistance, longer.length()); j++) {
                // Branch: checks a condition
                if (i == j) {
                    // case already covered
                    // Continues to the next loop iteration
                    continue;
                // End of a block/expression
                }
                // transposition required to match?
                // Calls a method
                match = shorter.charAt(i) == longer.charAt(j);
                // Branch: checks a condition
                if (match) {
                    // Code statement
                    transpositions++;
                    // Breaks out of the loop/block
                    break;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // any matching characters?
        // Branch: checks a condition
        if (matches == 0) {
            // Returns a value to the caller
            return 0;
        // End of a block/expression
        }
        // modify transpositions (according to the algorithm)
        // Calls a method
        transpositions = (int) (transpositions / 2.0);
        // non prefix-boosted score
        // Assigns a value
        double score = 0.3334 * (matches / (double) longer.length() + matches / (double) shorter.length() + (matches - transpositions)
                // Calls a method
                / (double) matches);
        // Branch: checks a condition
        if (score < 0.7) {
            // Returns a value to the caller
            return score;
        // End of a block/expression
        }
        // we already have a good match, hence we boost the score proportional to the common prefix
        // Returns a value to the caller
        return score + prefixMatch * 0.1 * (1.0 - score);
    // End of a block/expression
    }

    // Start of a method/block
    public static String unescapeJavaString(String st) {
        // Calls a method
        StringBuilder sb = new StringBuilder(st.length());

        // Loop: repeats a block
        for (int i = 0; i < st.length(); i++) {
            // Calls a method
            char ch = st.charAt(i);
            // Branch: checks a condition
            if (ch == '\\') {
                // Assigns a value
                char nextChar = (i == st.length() - 1) ? '\\' : st
                        // Calls a method
                        .charAt(i + 1);
                // Octal escape?
                // Branch: checks a condition
                if (nextChar >= '0' && nextChar <= '7') {
                    // Assigns a value
                    String code = "" + nextChar;
                    // Code statement
                    i++;
                    // Branch: checks a condition
                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                            // Start of a method/block
                            && st.charAt(i + 1) <= '7') {
                        // Calls a method
                        code += st.charAt(i + 1);
                        // Code statement
                        i++;
                        // Branch: checks a condition
                        if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                                // Start of a method/block
                                && st.charAt(i + 1) <= '7') {
                            // Calls a method
                            code += st.charAt(i + 1);
                            // Code statement
                            i++;
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                    // Calls a method
                    sb.append((char) Integer.parseInt(code, 8));
                    // Continues to the next loop iteration
                    continue;
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                switch (nextChar) {
                    // Multiple branching (switch/case)
                    case '\\' -> ch = '\\';
                    // Multiple branching (switch/case)
                    case 'b' -> ch = '\b';
                    // Multiple branching (switch/case)
                    case 'f' -> ch = '\f';
                    // Multiple branching (switch/case)
                    case 'n' -> ch = '\n';
                    // Multiple branching (switch/case)
                    case 'r' -> ch = '\r';
                    // Multiple branching (switch/case)
                    case 't' -> ch = '\t';
                    // Multiple branching (switch/case)
                    case '\"' -> ch = '\"';
                    // Multiple branching (switch/case)
                    case '\'' -> ch = '\'';

                    // Hex Unicode: u????
                    // Multiple branching (switch/case)
                    case 'u' -> {
                        // Branch: checks a condition
                        if (i >= st.length() - 5) {
                            // Assigns a value
                            ch = 'u';
                            // Breaks out of the loop/block
                            break;
                        // End of a block/expression
                        }
                        // Assigns a value
                        int code = Integer.parseInt(
                                // Code statement
                                "" + st.charAt(i + 2) + st.charAt(i + 3)
                                        // Calls a method
                                        + st.charAt(i + 4) + st.charAt(i + 5), 16);
                        // Calls a method
                        sb.append(Character.toChars(code));
                        // Code statement
                        i += 5;
                        // Continues to the next loop iteration
                        continue;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Code statement
                i++;
            // End of a block/expression
            }
            // Calls a method
            sb.append(ch);
        // End of a block/expression
        }
        // Returns a value to the caller
        return sb.toString();
    // End of a block/expression
    }
// End of a block/expression
}
