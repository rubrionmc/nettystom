// Package declaration for this file
package net.minestom.demo.commands;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;

// Import of a required class
import java.util.concurrent.CompletableFuture;
// Import of a required class
import java.util.concurrent.ExecutionException;

/**
 * A simple shutdown command.
 */
// Type declaration (class/interface/enum/record)
public class SaveCommand extends Command {

    // Start of a method/block
    public SaveCommand() {
        // Access to the current/parent object
        super("save");
        // Calls a method
        addSyntax(this::execute);
    // End of a block/expression
    }

    // Start of a method/block
    private void execute(CommandSender commandSender, CommandContext commandContext) {
        // Loop: repeats a block
        for(var instance : MinecraftServer.getInstanceManager().getInstances()) {
            // Calls a method
            CompletableFuture<Void> instanceSave = instance.saveInstance().thenCompose(v -> instance.saveChunksToStorage());
            // Exception handling
            try {
                // Calls a method
                instanceSave.get();
            // Start of a method/block
            } catch (InterruptedException | ExecutionException e) {
                // Calls a method
                MinecraftServer.getExceptionManager().handleException(e);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        commandSender.sendMessage("Saving done!");
    // End of a block/expression
    }
// End of a block/expression
}
