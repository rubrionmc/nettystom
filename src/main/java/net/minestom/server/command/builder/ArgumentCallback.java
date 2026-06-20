// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

/**
 * Callback executed when an error is found within the {@link Argument}.
 */
// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface ArgumentCallback {

    /**
     * Executed when an error is found.
     *
     * @param sender    the sender which executed the command
     * @param exception the exception containing the message, input and error code related to the issue
     */
    // Appelle une méthode
    void apply(CommandSender sender, ArgumentSyntaxException exception);
// Fin d'un bloc/d'une expression
}
