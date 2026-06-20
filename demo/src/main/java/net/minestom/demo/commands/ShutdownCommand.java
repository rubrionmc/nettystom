// Déclaration du paquet de ce fichier
package net.minestom.demo.commands;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;

/**
 * A simple shutdown command.
 */
// Déclaration de type (classe/interface/enum/record)
public class ShutdownCommand extends Command {

    // Début d'une méthode/d'un bloc
    public ShutdownCommand() {
        // Accès à l'objet courant/parent
        super("shutdown");
        // Appelle une méthode
        addSyntax(this::execute);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void execute(CommandSender commandSender, CommandContext commandContext) {
        // Appelle une méthode
        MinecraftServer.stopCleanly();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
