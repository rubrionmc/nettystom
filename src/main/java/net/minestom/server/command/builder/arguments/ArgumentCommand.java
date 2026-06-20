// Package declaration for this file
package net.minestom.server.command.builder.arguments;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.CommandDispatcher;
// Import of a required class
import net.minestom.server.command.builder.CommandResult;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.utils.StringUtils;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Type declaration (class/interface/enum/record)
public class ArgumentCommand extends Argument<CommandResult> {

    // Assigns a value
    public static final int INVALID_COMMAND_ERROR = 1;

    // Code statement
    private boolean onlyCorrect;
    // Assigns a value
    private String shortcut = "";

    // Start of a method/block
    public ArgumentCommand(String id) {
        // Access to the current/parent object
        super(id, true, true);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CommandResult parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Assigns a value
        final String commandString = !shortcut.isEmpty() ?
                // Code statement
                shortcut + StringUtils.SPACE + input
                // Code statement
                : input;
        // Calls a method
        CommandDispatcher dispatcher = MinecraftServer.getCommandManager().getDispatcher();
        // Calls a method
        CommandResult result = dispatcher.parse(sender, commandString);

        // Branch: checks a condition
        if (onlyCorrect && result.getType() != CommandResult.Type.SUCCESS)
            // Throws an exception
            throw new ArgumentSyntaxException("Invalid command", input, INVALID_COMMAND_ERROR);

        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isOnlyCorrect() {
        // Returns a value to the caller
        return onlyCorrect;
    // End of a block/expression
    }

    // Start of a method/block
    public ArgumentCommand setOnlyCorrect(boolean onlyCorrect) {
        // Access to the current/parent object
        this.onlyCorrect = onlyCorrect;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public String getShortcut() {
        // Returns a value to the caller
        return shortcut;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public ArgumentCommand setShortcut(String shortcut) {
        // Access to the current/parent object
        this.shortcut = shortcut;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("Command<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
