// Package declaration for this file
package net.minestom.server.command.builder;

// Import of a required class
import net.minestom.server.command.CommandSender;

/**
 * Callback executed once a syntax has been found for a {@link Command}.
 * <p>
 * Warning: it could be the default executor from {@link Command#getDefaultExecutor()} if not null.
 */
// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface CommandExecutor {

    /**
     * Executes the command callback once the syntax has been called (or the default executor).
     *
     * @param sender  the sender of the command
     * @param context the command context, used to retrieve the arguments and various other things
     */
    // Calls a method
    void apply(CommandSender sender, CommandContext context);
// End of a block/expression
}