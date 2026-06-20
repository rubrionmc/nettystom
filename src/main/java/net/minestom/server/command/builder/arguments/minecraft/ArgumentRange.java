// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.utils.Range;

// Import d'une classe nécessaire
import java.util.function.BiFunction;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.regex.Pattern;

/**
 * Abstract class used by {@link ArgumentIntRange} and {@link ArgumentFloatRange}.
 *
 * @param <T> the type of the range
 */
// Déclaration de type (classe/interface/enum/record)
public abstract class ArgumentRange<T extends Range<N>, N extends Number> extends Argument<T> {

    // Affecte une valeur
    public static final int FORMAT_ERROR = -1;
    // Instruction de code
    private final N min;
    // Instruction de code
    private final N max;
    // Instruction de code
    private final Function<String, N> parser;
    // Instruction de code
    private final BiFunction<N, N, T> rangeConstructor;

    // Début d'une méthode/d'un bloc
    public ArgumentRange(String id, N min, N max, Function<String, N> parser, BiFunction<N, N, T> rangeConstructor) {
        // Accès à l'objet courant/parent
        super(id);
        // Accès à l'objet courant/parent
        this.min = min;
        // Accès à l'objet courant/parent
        this.max = max;
        // Accès à l'objet courant/parent
        this.parser = parser;
        // Accès à l'objet courant/parent
        this.rangeConstructor = rangeConstructor;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public T parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            final String[] split = input.split(Pattern.quote(".."), -1);

            // Embranchement : vérifie une condition
            if (split.length == 2) {
                // Instruction de code
                final N min;
                // Instruction de code
                final N max;
                // Embranchement : vérifie une condition
                if (split[0].length() == 0 && split[1].length() > 0) {
                    // Format ..NUMBER
                    // Affecte une valeur
                    min = this.min;
                    // Appelle une méthode
                    max = parser.apply(split[1]);
                // Embranchement : vérifie une condition
                } else if (split[0].length() > 0 && split[1].length() == 0) {
                    // Format NUMBER..
                    // Appelle une méthode
                    min = parser.apply(split[0]);
                    // Affecte une valeur
                    max = this.max;
                // Embranchement : vérifie une condition
                } else if (split[0].length() > 0) {
                    // Format NUMBER..NUMBER
                    // Appelle une méthode
                    min = parser.apply(split[0]);
                    // Appelle une méthode
                    max = parser.apply(split[1]);
                // Branche alternative de la condition
                } else {
                    // Format ..
                    // Lève une exception
                    throw new ArgumentSyntaxException("Invalid range format", input, FORMAT_ERROR);
                // Fin d'un bloc/d'une expression
                }
                // Renvoie une valeur à l'appelant
                return rangeConstructor.apply(min, max);
            // Embranchement : vérifie une condition
            } else if (split.length == 1) {
                // Appelle une méthode
                final N number = parser.apply(input);
                // Renvoie une valeur à l'appelant
                return rangeConstructor.apply(number, number);
            // Fin d'un bloc/d'une expression
            }
        // Début d'une méthode/d'un bloc
        } catch (NumberFormatException e2) {
            // Lève une exception
            throw new ArgumentSyntaxException("Invalid number", input, FORMAT_ERROR);
        // Fin d'un bloc/d'une expression
        }
        // Lève une exception
        throw new ArgumentSyntaxException("Invalid range format", input, FORMAT_ERROR);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
