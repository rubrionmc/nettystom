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
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Represents a single word in the command.
 * <p>
 * You can specify the valid words with {@link #from(String...)} (do not abuse it or the client will not be able to join).
 * <p>
 * Example: hey
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentWord extends Argument<String> {

    // Affecte une valeur
    public static final int SPACE_ERROR = 1;
    // Affecte une valeur
    public static final int RESTRICTION_ERROR = 2;

    // Instruction de code
    protected String[] restrictions;

    // Début d'une méthode/d'un bloc
    public ArgumentWord(String id) {
        // Accès à l'objet courant/parent
        super(id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Used to force the use of a few precise words instead of complete freedom.
     * <p>
     * WARNING: having an array too long would result in a packet too big or the client being stuck during login.
     *
     * @param restrictions the accepted words,
     *                     can be null but if an array is passed
     *                     you need to ensure that it is filled with non-null values
     * @return 'this' for chaining
     * @throws NullPointerException if {@code restrictions} is not null but contains null value(s)
     */
    // Début d'une méthode/d'un bloc
    public ArgumentWord from(@Nullable String... restrictions) {
        // Embranchement : vérifie une condition
        if (restrictions != null) {
            // Boucle : répète un bloc
            for (String restriction : restrictions) {
                // Instruction de code
                Check.notNull(restriction,
                        // Instruction de code
                        "ArgumentWord restriction cannot be null, you can pass 'null' instead of an empty array");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Accès à l'objet courant/parent
        this.restrictions = restrictions;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Embranchement : vérifie une condition
        if (input.contains(StringUtils.SPACE))
            // Lève une exception
            throw new ArgumentSyntaxException("Word cannot contain space character", input, SPACE_ERROR);

        // Check restrictions (acting as literal)
        // Embranchement : vérifie une condition
        if (hasRestrictions()) {
            // Boucle : répète un bloc
            for (String r : restrictions) {
                // Embranchement : vérifie une condition
                if (input.equals(r))
                    // Renvoie une valeur à l'appelant
                    return input;
            // Fin d'un bloc/d'une expression
            }
            // Lève une exception
            throw new ArgumentSyntaxException("Word needs to be in the restriction list", input, RESTRICTION_ERROR);
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return input;
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
        return NetworkBuffer.makeArray(NetworkBuffer.VAR_INT, 0); // Single word
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if this argument allow complete freedom in the word choice or if a list has been defined.
     *
     * @return true if the word selection is restricted
     */
    // Début d'une méthode/d'un bloc
    public boolean hasRestrictions() {
        // Renvoie une valeur à l'appelant
        return restrictions != null && restrictions.length > 0;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all the word restrictions.
     *
     * @return the word restrictions, can be null
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public String[] getRestrictions() {
        // Renvoie une valeur à l'appelant
        return restrictions;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("Word<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
