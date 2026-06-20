// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft;

// Import d'une classe nécessaire
import net.kyori.adventure.key.KeyPattern;
// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;

/**
 * Represents a resource location (namespaced identifier) value.
 * <p>
 *     Example: {@code minecraft:air}
 * </p>
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentResourceLocation extends Argument<Key> {

    // Affecte une valeur
    public static final int PARSE_ERROR = 1;

    // Début d'une méthode/d'un bloc
    public ArgumentResourceLocation(String id) {
        // Accès à l'objet courant/parent
        super(id);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Key parse(CommandSender sender, @KeyPattern String input) throws ArgumentSyntaxException {
        // Embranchement : vérifie une condition
        if (!Key.parseable(input))
            // Lève une exception
            throw new ArgumentSyntaxException("Invalid resource location", input, PARSE_ERROR);

        // Renvoie une valeur à l'appelant
        return Key.key(input);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.RESOURCE_LOCATION;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("ResourceLocation<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
