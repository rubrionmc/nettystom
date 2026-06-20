// Déclaration du paquet de ce fichier
package net.minestom.server.utils.callback;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;

/**
 * Functional interface used by the {@link net.minestom.server.command.CommandManager}
 * to execute a callback if an unknown command is run.
 * You can set it with {@link net.minestom.server.command.CommandManager#setUnknownCommandCallback(CommandCallback)}.
 */
// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface CommandCallback {

    /**
     * Executed if an unknown command is run.
     *
     * @param sender  the command sender
     * @param command the complete command string
     */
    // Appelle une méthode
    void apply(CommandSender sender, String command);

// Fin d'un bloc/d'une expression
}
