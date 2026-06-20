// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class ArgumentResourceOrTag extends Argument<String> {

    // Affecte une valeur
    public static final int SPACE_ERROR = 1;

    // Instruction de code
    private final String identifier;

    // Début d'une méthode/d'un bloc
    public ArgumentResourceOrTag(String id, String identifier) {
        // Accès à l'objet courant/parent
        super(id);
        // Accès à l'objet courant/parent
        this.identifier = identifier;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Embranchement : vérifie une condition
        if (input.contains(StringUtils.SPACE))
            // Lève une exception
            throw new ArgumentSyntaxException("Resource location cannot contain space character", input, SPACE_ERROR);

        // Renvoie une valeur à l'appelant
        return input;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.RESOURCE_OR_TAG;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("ResourceOrTag<%s>", getId());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte @Nullable [] nodeProperties() {
        // Renvoie une valeur à l'appelant
        return NetworkBuffer.makeArray(NetworkBuffer.STRING, identifier);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
