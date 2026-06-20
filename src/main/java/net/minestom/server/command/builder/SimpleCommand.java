// Package declaration for this file
package net.minestom.server.command.builder;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public abstract class SimpleCommand extends Command {
    // Start of a method/block
    public SimpleCommand(String name, @Nullable String... aliases) {
        // Access to the current/parent object
        super(name, aliases);

        // Calls a method
        setCondition(this::hasAccess);

        // Code statement
        setDefaultExecutor((sender, context) ->
                // Calls a method
                process(sender, context.getCommandName(), new String[0]));

        // Calls a method
        final var params = ArgumentType.StringArray("params");
        // Code statement
        addSyntax((sender, context) ->
                // Calls a method
                process(sender, context.getCommandName(), context.get(params)), params);
    // End of a block/expression
    }

    /**
     * Called when the command is executed by a {@link CommandSender}.
     *
     * @param sender  the sender which executed the command
     * @param command the command name used
     * @param args    an array containing all the args (split by space char)
     * @return true when the command is successful, false otherwise
     */
    // Calls a method
    public abstract boolean process(CommandSender sender, String command, String[] args);

    /**
     * Called to know if a player has access to the command.
     *
     * @param sender        the command sender to check the access
     * @param commandString the raw command string,
     *                      null if this is an access request
     * @return true if the player has access to the command, false otherwise
     */
    // Calls a method
    public abstract boolean hasAccess(CommandSender sender, @Nullable String commandString);

// End of a block/expression
}
