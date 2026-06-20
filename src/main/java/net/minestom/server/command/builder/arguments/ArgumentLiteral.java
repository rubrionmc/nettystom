// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

// Déclaration de type (classe/interface/enum/record)
public class ArgumentLiteral extends Argument<String> {

    // Affecte une valeur
    public static final int INVALID_VALUE_ERROR = 1;

    // Début d'une méthode/d'un bloc
    public ArgumentLiteral(String id) {
        // Accès à l'objet courant/parent
        super(id);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Embranchement : vérifie une condition
        if (!input.equals(getId()))
            // Lève une exception
            throw new ArgumentSyntaxException("Invalid literal value", input, INVALID_VALUE_ERROR);

        // Renvoie une valeur à l'appelant
        return input;
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

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("Literal<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
