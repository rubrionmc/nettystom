// Package declaration for this file
package net.minestom.server.command.builder;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.ExecutableCommand;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Represents a {@link Command} ready to be executed (already parsed).
 */
// Type declaration (class/interface/enum/record)
public class ParsedCommand {
    // Code statement
    private final ExecutableCommand executableCommand;

    // Start of a method/block
    private ParsedCommand(ExecutableCommand executableCommand) {
        // Access to the current/parent object
        this.executableCommand = executableCommand;
    // End of a block/expression
    }

    /**
     * Executes the command for the given source.
     * <p>
     * The command will not be executed if {@link Command#getCondition()}
     * is not validated.
     *
     * @param source the command source
     * @return the command data, null if none
     */
    // Start of a method/block
    public @Nullable CommandData execute(CommandSender source) {
        // Calls a method
        final ExecutableCommand.Result result = executableCommand.execute(source);
        // Returns a value to the caller
        return result.commandData();
    // End of a block/expression
    }

    // Start of a method/block
    public static ParsedCommand fromExecutable(ExecutableCommand executableCommand) {
        // Returns a value to the caller
        return new ParsedCommand(executableCommand);
    // End of a block/expression
    }
// End of a block/expression
}
