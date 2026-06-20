// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.regex.Pattern;

/**
 * Represents an argument which will take all the remaining of the command.
 * <p>
 * Example: Hey I am a string
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentStringArray extends Argument<String[]> {

    // Début d'une méthode/d'un bloc
    public ArgumentStringArray(String id) {
        // Accès à l'objet courant/parent
        super(id, true, true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String[] parse(CommandSender sender, String input) {
        // Renvoie une valeur à l'appelant
        return input.split(Pattern.quote(StringUtils.SPACE));
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
        return NetworkBuffer.makeArray(NetworkBuffer.VAR_INT, 2); // Greedy phrase
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("StringArray<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
