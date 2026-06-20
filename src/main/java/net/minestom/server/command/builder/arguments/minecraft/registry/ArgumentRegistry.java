// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft.registry;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

// Déclaration de type (classe/interface/enum/record)
public abstract class ArgumentRegistry<T> extends Argument<T> {

    // Affecte une valeur
    public static final int INVALID_NAME = -2;

    // Début d'une méthode/d'un bloc
    public ArgumentRegistry(String id) {
        // Accès à l'objet courant/parent
        super(id);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    public abstract T getRegistry(String value);

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public T parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Appelle une méthode
        final T registryValue = getRegistry(input);
        // Embranchement : vérifie une condition
        if (registryValue == null)
            // Lève une exception
            throw new ArgumentSyntaxException("Registry value is invalid", input, INVALID_NAME);

        // Renvoie une valeur à l'appelant
        return registryValue;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
