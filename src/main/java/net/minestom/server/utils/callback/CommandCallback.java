// Package declaration for this file
package net.minestom.server.utils.callback;

// Import of a required class
import net.minestom.server.command.CommandSender;

/**
 * Functional interface used by the {@link net.minestom.server.command.CommandManager}
 * to execute a callback if an unknown command is run.
 * You can set it with {@link net.minestom.server.command.CommandManager#setUnknownCommandCallback(CommandCallback)}.
 */
// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface CommandCallback {

    /**
     * Executed if an unknown command is run.
     *
     * @param sender  the command sender
     * @param command the complete command string
     */
    // Calls a method
    void apply(CommandSender sender, String command);

// End of a block/expression
}
