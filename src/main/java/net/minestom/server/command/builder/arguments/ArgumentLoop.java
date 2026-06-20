// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
public class ArgumentLoop<T> extends Argument<List<T>> {

    // Affecte une valeur
    public static final int INVALID_INPUT_ERROR = 1;

    // Appelle une méthode
    private final List<Argument<T>> arguments = new ArrayList<>();

    // Annotation pour l'élément suivant
    @SafeVarargs
    // Début d'une méthode/d'un bloc
    public ArgumentLoop(String id, Argument<T>... arguments) {
        // Accès à l'objet courant/parent
        super(id, true, true);
        // Accès à l'objet courant/parent
        this.arguments.addAll(Arrays.asList(arguments));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public List<T> parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Appelle une méthode
        List<T> result = new ArrayList<>();
        // Appelle une méthode
        final String[] split = input.split(StringUtils.SPACE);

        // Appelle une méthode
        final StringBuilder builder = new StringBuilder();
        // Affecte une valeur
        boolean success = false;
        // Boucle : répète un bloc
        for (String s : split) {
            // Appelle une méthode
            builder.append(s);

            // Boucle : répète un bloc
            for (Argument<T> argument : arguments) {
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    final String inputString = builder.toString();
                    // Appelle une méthode
                    final T value = argument.parse(sender, inputString);
                    // Affecte une valeur
                    success = true;
                    // Appelle une méthode
                    result.add(value);
                    // Interrompt la boucle/le bloc
                    break;
                // Début d'une méthode/d'un bloc
                } catch (ArgumentSyntaxException ignored) {
                    // Affecte une valeur
                    success = false;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (success) {
                // Instruction de code
                builder.setLength(0); // Clear
            // Branche alternative de la condition
            } else {
                // Appelle une méthode
                builder.append(StringUtils.SPACE);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (result.isEmpty() || !success) {
            // Lève une exception
            throw new ArgumentSyntaxException("Invalid loop, there is no valid argument found", input, INVALID_INPUT_ERROR);
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public List<Argument<T>> arguments() {
        // Renvoie une valeur à l'appelant
        return arguments;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
