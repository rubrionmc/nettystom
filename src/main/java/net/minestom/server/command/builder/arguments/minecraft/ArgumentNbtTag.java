// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

// Import d'une classe nécessaire
import java.io.IOException;

/**
 * Argument used to retrieve a {@link BinaryTag} based object, can be any kind of tag like
 * {@link net.kyori.adventure.nbt.CompoundBinaryTag}, {@link net.kyori.adventure.nbt.ListBinaryTag},
 * {@link net.kyori.adventure.nbt.IntBinaryTag}, etc...
 * <p>
 * Example: {display:{Name:"{\"text\":\"Sword of Power\"}"}} or [{display:{Name:"{\"text\":\"Sword of Power\"}"}}]
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentNbtTag extends Argument<BinaryTag> {

    // Affecte une valeur
    public static final int INVALID_NBT = 1;

    // Début d'une méthode/d'un bloc
    public ArgumentNbtTag(String id) {
        // Accès à l'objet courant/parent
        super(id, true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public BinaryTag parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return MinestomAdventure.tagStringIO().asTag(input);
        // Début d'une méthode/d'un bloc
        } catch (IOException e) {
            // Lève une exception
            throw new ArgumentSyntaxException("Invalid NBT", input, INVALID_NBT);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.NBT_TAG;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("NBT<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
