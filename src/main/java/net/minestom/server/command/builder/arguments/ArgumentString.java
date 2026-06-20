// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Argument which will take a quoted string.
 * <p>
 * Example: "Hey I am a string"
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentString extends Argument<String> {

    // Affecte une valeur
    private static final char BACKSLASH = '\\';
    // Affecte une valeur
    private static final char DOUBLE_QUOTE = '"';
    // Affecte une valeur
    private static final char QUOTE = '\'';

    // Affecte une valeur
    public static final int QUOTE_ERROR = 1;

    // Début d'une méthode/d'un bloc
    public ArgumentString(String id) {
        // Accès à l'objet courant/parent
        super(id, true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Renvoie une valeur à l'appelant
        return staticParse(input);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.STRING;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte @Nullable [] nodeProperties() {
        // Renvoie une valeur à l'appelant
        return NetworkBuffer.makeArray(NetworkBuffer.VAR_INT, 1); // Quotable phrase
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link Argument#parse(CommandSender, Argument)}
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public static String staticParse(String input) throws ArgumentSyntaxException {
        // Return if not quoted
        // Embranchement : vérifie une condition
        if (!input.contains(String.valueOf(DOUBLE_QUOTE)) &&
                // Instruction de code
                !input.contains(String.valueOf(QUOTE)) &&
                // Début d'une méthode/d'un bloc
                !input.contains(StringUtils.SPACE)) {
            // Renvoie une valeur à l'appelant
            return input;
        // Fin d'un bloc/d'une expression
        }

        // Check if value start and end with quote
        // Appelle une méthode
        final char first = input.charAt(0);
        // Appelle une méthode
        final char last = input.charAt(input.length() - 1);
        // Affecte une valeur
        final boolean quote = input.length() >= 2 &&
                // Appelle une méthode
                first == last && (first == DOUBLE_QUOTE || first == QUOTE);
        // Embranchement : vérifie une condition
        if (!quote)
            // Lève une exception
            throw new ArgumentSyntaxException("String argument needs to start and end with quotes", input, QUOTE_ERROR);

        // Remove first and last characters (quotes)
        // Appelle une méthode
        input = input.substring(1, input.length() - 1);

        // Verify backslashes
        // Boucle : répète un bloc
        for (int i = 1; i < input.length(); i++) {
            // Appelle une méthode
            final char c = input.charAt(i);
            // Embranchement : vérifie une condition
            if (c == first) {
                // Appelle une méthode
                final char lastChar = input.charAt(i - 1);
                // Embranchement : vérifie une condition
                if (lastChar != BACKSLASH) {
                    // Lève une exception
                    throw new ArgumentSyntaxException("Non-escaped quote", input, QUOTE_ERROR);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return StringUtils.unescapeJavaString(input);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("String<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
