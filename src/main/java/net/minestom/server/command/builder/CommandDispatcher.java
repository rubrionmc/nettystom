// Package declaration for this file
package net.minestom.server.command.builder;

// Import of a required class
import net.minestom.server.command.CommandManager;
// Import of a required class
import net.minestom.server.command.CommandParser;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Set;

/**
 * Class responsible for parsing {@link Command}.
 */
// Type declaration (class/interface/enum/record)
public class CommandDispatcher {
    // Code statement
    private final CommandManager manager;

    // Start of a method/block
    public CommandDispatcher(CommandManager manager) {
        // Access to the current/parent object
        this.manager = manager;
    // End of a block/expression
    }

    // Start of a method/block
    public CommandDispatcher() {
        // Calls a method
        this(new CommandManager());
    // End of a block/expression
    }

    /**
     * Registers a command,
     * be aware that registering a command name or alias will override the previous entry.
     *
     * @param command the command to register
     */
    // Start of a method/block
    public void register(Command command) {
        // Calls a method
        manager.register(command);
    // End of a block/expression
    }

    // Start of a method/block
    public void unregister(Command command) {
        // Calls a method
        manager.unregister(command);
    // End of a block/expression
    }

    // Start of a method/block
    public Set<Command> getCommands() {
        // Returns a value to the caller
        return manager.getCommands();
    // End of a block/expression
    }

    /**
     * Gets the command class associated with the name.
     *
     * @param commandName the command name
     * @return the {@link Command} associated with the name, null if not any
     */
    // Start of a method/block
    public @Nullable Command findCommand(String commandName) {
        // Returns a value to the caller
        return manager.getCommand(commandName);
    // End of a block/expression
    }

    /**
     * Checks if the command exists, and execute it.
     *
     * @param source        the command source
     * @param commandString the command with the argument(s)
     * @return the command result
     */
    // Start of a method/block
    public CommandResult execute(CommandSender source, String commandString) {
        // Returns a value to the caller
        return manager.execute(source, commandString);
    // End of a block/expression
    }

    /**
     * Parses the given command.
     *
     * @param commandString the command (containing the command name and the args if any)
     * @return the parsing result
     */
    // Start of a method/block
    public CommandResult parse(CommandSender sender, String commandString) {
        // Calls a method
        final net.minestom.server.command.CommandParser.Result test = manager.parseCommand(sender, commandString);
        // Returns a value to the caller
        return resultConverter(test, commandString);
    // End of a block/expression
    }

    // Start of a method/block
    private static CommandResult resultConverter(net.minestom.server.command.CommandParser.Result parseResult, String input) {
        // Assigns a value
        CommandResult.Type type = switch (parseResult) {
            // Multiple branching (switch/case)
            case CommandParser.Result.UnknownCommand unknownCommand -> CommandResult.Type.UNKNOWN;
            // Multiple branching (switch/case)
            case CommandParser.Result.KnownCommand.Valid valid -> CommandResult.Type.SUCCESS;
            // Multiple branching (switch/case)
            case CommandParser.Result.KnownCommand.Invalid invalid -> CommandResult.Type.INVALID_SYNTAX;
            // Multiple branching (switch/case)
            case null -> throw new IllegalStateException("Unknown CommandParser.Result type");
        // End of a block/expression
        };
        // Returns a value to the caller
        return CommandResult.of(type, input, ParsedCommand.fromExecutable(parseResult.executable()), null);
    // End of a block/expression
    }
// End of a block/expression
}
