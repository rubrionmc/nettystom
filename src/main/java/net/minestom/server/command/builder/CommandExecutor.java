// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;

/**
 * Callback executed once a syntax has been found for a {@link Command}.
 * <p>
 * Warning: it could be the default executor from {@link Command#getDefaultExecutor()} if not null.
 */
// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface CommandExecutor {

    /**
     * Executes the command callback once the syntax has been called (or the default executor).
     *
     * @param sender  the sender of the command
     * @param context the command context, used to retrieve the arguments and various other things
     */
    // Appelle une méthode
    void apply(CommandSender sender, CommandContext context);
// Fin d'un bloc/d'une expression
}