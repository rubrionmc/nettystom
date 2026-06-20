// Package declaration for this file
package net.minestom.server.command.builder;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class CommandResult {

    // Assigns a value
    protected Type type = Type.UNKNOWN;
    // Code statement
    protected String input;
    // Code statement
    protected @Nullable ParsedCommand parsedCommand;
    // Code statement
    protected @Nullable CommandData commandData;

    // Start of a method/block
    public Type getType() {
        // Returns a value to the caller
        return type;
    // End of a block/expression
    }

    // Start of a method/block
    public String getInput() {
        // Returns a value to the caller
        return input;
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable ParsedCommand getParsedCommand() {
        // Returns a value to the caller
        return parsedCommand;
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable CommandData getCommandData() {
        // Returns a value to the caller
        return commandData;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Type {
        /**
         * Command and syntax successfully found.
         */
        // Code statement
        SUCCESS,
        /**
         * Command found, but the syntax is invalid.
         * Executor sets to {@link Command#getDefaultExecutor()}.
         */
        // Code statement
        INVALID_SYNTAX,
        /**
         * Command cancelled by an event listener.
         */
        // Code statement
        CANCELLED,
        /**
         * Command is not registered, it is also the default result type.
         */
        // Code statement
        UNKNOWN
    // End of a block/expression
    }

    // Start of a method/block
    public static CommandResult of(Type type, String input) {
        // Calls a method
        CommandResult result = new CommandResult();
        // Assigns a value
        result.type = type;
        // Assigns a value
        result.input = input;
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static CommandResult of(Type type, String input, ParsedCommand parsedCommand, @Nullable CommandData data) {
        // Calls a method
        CommandResult result = of(type, input);
        // Assigns a value
        result.parsedCommand = parsedCommand;
        // Assigns a value
        result.commandData = data;
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
