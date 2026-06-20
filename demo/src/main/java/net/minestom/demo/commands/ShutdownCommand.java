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

/**
 * A simple shutdown command.
 */
// Type declaration (class/interface/enum/record)
public class ShutdownCommand extends Command {

    // Start of a method/block
    public ShutdownCommand() {
        // Access to the current/parent object
        super("shutdown");
        // Calls a method
        addSyntax(this::execute);
    // End of a block/expression
    }

    // Start of a method/block
    private void execute(CommandSender commandSender, CommandContext commandContext) {
        // Calls a method
        MinecraftServer.stopCleanly();
    // End of a block/expression
    }
// End of a block/expression
}
