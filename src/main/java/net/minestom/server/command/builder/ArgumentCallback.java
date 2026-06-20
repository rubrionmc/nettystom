// Package declaration for this file
package net.minestom.server.command.builder;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

/**
 * Callback executed when an error is found within the {@link Argument}.
 */
// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface ArgumentCallback {

    /**
     * Executed when an error is found.
     *
     * @param sender    the sender which executed the command
     * @param exception the exception containing the message, input and error code related to the issue
     */
    // Calls a method
    void apply(CommandSender sender, ArgumentSyntaxException exception);
// End of a block/expression
}
