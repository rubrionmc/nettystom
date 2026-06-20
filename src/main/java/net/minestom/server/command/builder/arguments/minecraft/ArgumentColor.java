// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft;

// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.Style;
// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

/**
 * Represents an argument which will give you a {@link Style} containing the colour or no
 * colour if the argument was {@code reset}.
 * <p>
 * Example: red, white, reset
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentColor extends Argument<Style> {

    // Affecte une valeur
    public static final int UNDEFINED_COLOR = -2;

    // Début d'une méthode/d'un bloc
    public ArgumentColor(String id) {
        // Accès à l'objet courant/parent
        super(id);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Style parse(CommandSender sender, String input) throws ArgumentSyntaxException {

        // check for colour
        // Appelle une méthode
        NamedTextColor color = NamedTextColor.NAMES.value(input);
        // Embranchement : vérifie une condition
        if (color != null) {
            // Renvoie une valeur à l'appelant
            return Style.style(color);
        // Fin d'un bloc/d'une expression
        }

        // check for reset
        // Embranchement : vérifie une condition
        if (input.equals("reset")) {
            // Renvoie une valeur à l'appelant
            return Style.empty();
        // Fin d'un bloc/d'une expression
        }

        // Lève une exception
        throw new ArgumentSyntaxException("Undefined color", input, UNDEFINED_COLOR);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.COLOR;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("Color<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
