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

// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;
// Import d'une classe nécessaire
import java.util.concurrent.ExecutionException;

/**
 * A simple shutdown command.
 */
// Déclaration de type (classe/interface/enum/record)
public class SaveCommand extends Command {

    // Début d'une méthode/d'un bloc
    public SaveCommand() {
        // Accès à l'objet courant/parent
        super("save");
        // Appelle une méthode
        addSyntax(this::execute);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void execute(CommandSender commandSender, CommandContext commandContext) {
        // Boucle : répète un bloc
        for(var instance : MinecraftServer.getInstanceManager().getInstances()) {
            // Appelle une méthode
            CompletableFuture<Void> instanceSave = instance.saveInstance().thenCompose(v -> instance.saveChunksToStorage());
            // Gestion des exceptions
            try {
                // Appelle une méthode
                instanceSave.get();
            // Début d'une méthode/d'un bloc
            } catch (InterruptedException | ExecutionException e) {
                // Appelle une méthode
                MinecraftServer.getExceptionManager().handleException(e);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        commandSender.sendMessage("Saving done!");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
