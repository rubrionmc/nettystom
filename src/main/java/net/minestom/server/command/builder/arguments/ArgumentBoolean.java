// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

/**
 * Represents a boolean value.
 * <p>
 * Example: true
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentBoolean extends Argument<Boolean> {

    // Affecte une valeur
    public static final int NOT_BOOLEAN_ERROR = 1;

    // Début d'une méthode/d'un bloc
    public ArgumentBoolean(String id) {
        // Accès à l'objet courant/parent
        super(id);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Boolean parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Embranchement : vérifie une condition
        if (input.equalsIgnoreCase("true"))
            // Renvoie une valeur à l'appelant
            return true;
        // Embranchement : vérifie une condition
        if (input.equalsIgnoreCase("false"))
            // Renvoie une valeur à l'appelant
            return false;

        // Lève une exception
        throw new ArgumentSyntaxException("Not a boolean", input, NOT_BOOLEAN_ERROR);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.BOOL;
    // Fin d'un bloc/d'une expression
    }
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("Boolean<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
