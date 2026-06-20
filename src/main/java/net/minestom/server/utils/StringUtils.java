// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class StringUtils {

    // Affecte une valeur
    public static final String SPACE = " ";
    // Affecte une valeur
    public static final char SPACE_CHAR = ' ';

    // Début d'une méthode/d'un bloc
    public static int countMatches(final CharSequence str, final char ch) {
        // Embranchement : vérifie une condition
        if (str.isEmpty()) {
            // Renvoie une valeur à l'appelant
            return 0;
        // Fin d'un bloc/d'une expression
        }
        // Affecte une valeur
        int count = 0;
        // We could also call str.toCharArray() for faster look ups but that would generate more garbage.
        // Boucle : répète un bloc
        for (int i = 0; i < str.length(); i++) {
            // Embranchement : vérifie une condition
            if (ch == str.charAt(i)) {
                // Instruction de code
                count++;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return count;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public static double jaroWinklerScore(final @Nullable String s1, final @Nullable String s2) {
        // lowest score on empty strings
        // Embranchement : vérifie une condition
        if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) {
            // Renvoie une valeur à l'appelant
            return 0;
        // Fin d'un bloc/d'une expression
        }
        // highest score on equal strings
        // Embranchement : vérifie une condition
        if (s1.equals(s2)) {
            // Renvoie une valeur à l'appelant
            return 1;
        // Fin d'un bloc/d'une expression
        }
        // some score on different strings
        // Affecte une valeur
        int prefixMatch = 0; // exact prefix matches
        // Affecte une valeur
        int matches = 0; // matches (including prefix and ones requiring transpostion)
        // Affecte une valeur
        int transpositions = 0; // matching characters that are not aligned but close together
        // Appelle une méthode
        int maxLength = Math.max(s1.length(), s2.length());
        // Affecte une valeur
        int maxMatchDistance = Math.max((int) Math.floor(maxLength / 2.0) - 1, 0); // look-ahead/-behind to limit transposed matches
        // comparison
        // Appelle une méthode
        final String shorter = s1.length() < s2.length() ? s1 : s2;
        // Appelle une méthode
        final String longer = s1.length() >= s2.length() ? s1 : s2;
        // Boucle : répète un bloc
        for (int i = 0; i < shorter.length(); i++) {
            // check for exact matches
            // Appelle une méthode
            boolean match = shorter.charAt(i) == longer.charAt(i);
            // Embranchement : vérifie une condition
            if (match) {
                // Embranchement : vérifie une condition
                if (i < 4) {
                    // prefix match (of at most 4 characters, as described by the algorithm)
                    // Instruction de code
                    prefixMatch++;
                // Fin d'un bloc/d'une expression
                }
                // Instruction de code
                matches++;
                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }
            // check fro transposed matches
            // Boucle : répète un bloc
            for (int j = Math.max(i - maxMatchDistance, 0); j < Math.min(i + maxMatchDistance, longer.length()); j++) {
                // Embranchement : vérifie une condition
                if (i == j) {
                    // case already covered
                    // Passe à l'itération suivante de la boucle
                    continue;
                // Fin d'un bloc/d'une expression
                }
                // transposition required to match?
                // Appelle une méthode
                match = shorter.charAt(i) == longer.charAt(j);
                // Embranchement : vérifie une condition
                if (match) {
                    // Instruction de code
                    transpositions++;
                    // Interrompt la boucle/le bloc
                    break;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // any matching characters?
        // Embranchement : vérifie une condition
        if (matches == 0) {
            // Renvoie une valeur à l'appelant
            return 0;
        // Fin d'un bloc/d'une expression
        }
        // modify transpositions (according to the algorithm)
        // Appelle une méthode
        transpositions = (int) (transpositions / 2.0);
        // non prefix-boosted score
        // Affecte une valeur
        double score = 0.3334 * (matches / (double) longer.length() + matches / (double) shorter.length() + (matches - transpositions)
                // Appelle une méthode
                / (double) matches);
        // Embranchement : vérifie une condition
        if (score < 0.7) {
            // Renvoie une valeur à l'appelant
            return score;
        // Fin d'un bloc/d'une expression
        }
        // we already have a good match, hence we boost the score proportional to the common prefix
        // Renvoie une valeur à l'appelant
        return score + prefixMatch * 0.1 * (1.0 - score);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static String unescapeJavaString(String st) {
        // Appelle une méthode
        StringBuilder sb = new StringBuilder(st.length());

        // Boucle : répète un bloc
        for (int i = 0; i < st.length(); i++) {
            // Appelle une méthode
            char ch = st.charAt(i);
            // Embranchement : vérifie une condition
            if (ch == '\\') {
                // Affecte une valeur
                char nextChar = (i == st.length() - 1) ? '\\' : st
                        // Appelle une méthode
                        .charAt(i + 1);
                // Octal escape?
                // Embranchement : vérifie une condition
                if (nextChar >= '0' && nextChar <= '7') {
                    // Affecte une valeur
                    String code = "" + nextChar;
                    // Instruction de code
                    i++;
                    // Embranchement : vérifie une condition
                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                            // Début d'une méthode/d'un bloc
                            && st.charAt(i + 1) <= '7') {
                        // Appelle une méthode
                        code += st.charAt(i + 1);
                        // Instruction de code
                        i++;
                        // Embranchement : vérifie une condition
                        if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                                // Début d'une méthode/d'un bloc
                                && st.charAt(i + 1) <= '7') {
                            // Appelle une méthode
                            code += st.charAt(i + 1);
                            // Instruction de code
                            i++;
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                    // Appelle une méthode
                    sb.append((char) Integer.parseInt(code, 8));
                    // Passe à l'itération suivante de la boucle
                    continue;
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                switch (nextChar) {
                    // Embranchement multiple (switch/case)
                    case '\\' -> ch = '\\';
                    // Embranchement multiple (switch/case)
                    case 'b' -> ch = '\b';
                    // Embranchement multiple (switch/case)
                    case 'f' -> ch = '\f';
                    // Embranchement multiple (switch/case)
                    case 'n' -> ch = '\n';
                    // Embranchement multiple (switch/case)
                    case 'r' -> ch = '\r';
                    // Embranchement multiple (switch/case)
                    case 't' -> ch = '\t';
                    // Embranchement multiple (switch/case)
                    case '\"' -> ch = '\"';
                    // Embranchement multiple (switch/case)
                    case '\'' -> ch = '\'';

                    // Hex Unicode: u????
                    // Embranchement multiple (switch/case)
                    case 'u' -> {
                        // Embranchement : vérifie une condition
                        if (i >= st.length() - 5) {
                            // Affecte une valeur
                            ch = 'u';
                            // Interrompt la boucle/le bloc
                            break;
                        // Fin d'un bloc/d'une expression
                        }
                        // Affecte une valeur
                        int code = Integer.parseInt(
                                // Instruction de code
                                "" + st.charAt(i + 2) + st.charAt(i + 3)
                                        // Appelle une méthode
                                        + st.charAt(i + 4) + st.charAt(i + 5), 16);
                        // Appelle une méthode
                        sb.append(Character.toChars(code));
                        // Instruction de code
                        i += 5;
                        // Passe à l'itération suivante de la boucle
                        continue;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Instruction de code
                i++;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            sb.append(ch);
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return sb.toString();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
