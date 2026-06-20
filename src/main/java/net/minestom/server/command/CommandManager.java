// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandDispatcher;
// Import of a required class
import net.minestom.server.command.builder.CommandResult;
// Import of a required class
import net.minestom.server.command.builder.ParsedCommand;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.EventDispatcher;
// Import of a required class
import net.minestom.server.event.player.PlayerCommandEvent;
// Import of a required class
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
// Import of a required class
import net.minestom.server.utils.callback.CommandCallback;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.*;

/**
 * Manager used to register {@link Command commands}.
 * <p>
 * It is also possible to simulate a command using {@link #execute(CommandSender, String)}.
 */
// Type declaration (class/interface/enum/record)
public final class CommandManager {

    // Assigns a value
    public static final String COMMAND_PREFIX = "/";

    // Calls a method
    private final ServerSender serverSender = new ServerSender();
    // Calls a method
    private final ConsoleSender consoleSender = new ConsoleSender();
    // Calls a method
    private final CommandParser parser = CommandParser.parser();
    // Calls a method
    private final CommandDispatcher dispatcher = new CommandDispatcher(this);
    // Calls a method
    private final Map<String, Command> commandMap = new HashMap<>();
    // Calls a method
    private final Set<Command> commands = new HashSet<>();

    // Code statement
    private CommandCallback unknownCommandCallback;
    // Code statement
    private volatile @Nullable Graph cachedGraph;

    // Start of a method/block
    public CommandManager() {
    // End of a block/expression
    }

    /**
     * Registers a {@link Command}.
     *
     * @param command the command to register
     * @throws IllegalStateException if a command with the same name already exists
     */
    // Start of a method/block
    public synchronized void register(Command command) {
        // Code statement
        Check.stateCondition(commandExists(command.getName()),
                // Calls a method
                "A command with the name " + command.getName() + " is already registered!");
        // Branch: checks a condition
        if (command.getAliases() != null) {
            // Loop: repeats a block
            for (String alias : command.getAliases()) {
                // Code statement
                Check.stateCondition(commandExists(alias),
                        // Code statement
                        "A command with the name " + alias + " is already registered!");
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Calls a method
        commands.add(command);
        // Loop: repeats a block
        for (String name : command.getNames()) {
            // Calls a method
            commandMap.put(name, command);
        // End of a block/expression
        }

        // Calls a method
        invalidateGraphCache();
    // End of a block/expression
    }

    /**
     * Register multiple {@link Command}s.
     *
     * @param commands the array of commands
     * @throws IllegalStateException if a command with the same name already exists
     */
    // Start of a method/block
    public synchronized void register(Command... commands) {
        // Loop: repeats a block
        for (Command command : commands) {
            // Calls a method
            register(command);
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Removes a command from the currently registered commands.
     * Does nothing if the command was not registered before
     *
     * @param command the command to remove
     */
    // Start of a method/block
    public void unregister(Command command) {
        // Calls a method
        commands.remove(command);
        // Loop: repeats a block
        for (String name : command.getNames()) {
            // Calls a method
            commandMap.remove(name);
        // End of a block/expression
        }

        // Calls a method
        invalidateGraphCache();
    // End of a block/expression
    }

    /**
     * Gets the {@link Command} registered by {@link #register(Command)}.
     *
     * @param commandName the command name
     * @return the command associated with the name, null if not any
     */
    // Start of a method/block
    public @Nullable Command getCommand(String commandName) {
        // Returns a value to the caller
        return commandMap.get(commandName.toLowerCase(Locale.ROOT));
    // End of a block/expression
    }

    /**
     * Gets if a command with the name {@code commandName} already exists or not.
     *
     * @param commandName the command name to check
     * @return true if the command does exist
     */
    // Start of a method/block
    public boolean commandExists(String commandName) {
        // Returns a value to the caller
        return getCommand(commandName) != null;
    // End of a block/expression
    }

    /**
     * Executes a command for a {@link CommandSender}.
     *
     * @param sender  the sender of the command
     * @param command the raw command string (without the command prefix)
     * @return the execution result
     */
    // Start of a method/block
    public CommandResult execute(CommandSender sender, String command) {
        // Calls a method
        command = command.trim();
        // Command event
        // Branch: checks a condition
        if (sender instanceof Player player) {
            // Calls a method
            PlayerCommandEvent playerCommandEvent = new PlayerCommandEvent(player, command);
            // Calls a method
            EventDispatcher.call(playerCommandEvent);
            // Branch: checks a condition
            if (playerCommandEvent.isCancelled())
                // Returns a value to the caller
                return CommandResult.of(CommandResult.Type.CANCELLED, command);
            // Calls a method
            command = playerCommandEvent.getCommand();
        // End of a block/expression
        }
        // Process the command
        // Calls a method
        final CommandParser.Result parsedCommand = parseCommand(sender, command);
        // Calls a method
        final ExecutableCommand executable = parsedCommand.executable();
        // Calls a method
        final ExecutableCommand.Result executeResult = executable.execute(sender);
        // Calls a method
        final CommandResult result = resultConverter(executable, executeResult, command);
        // Branch: checks a condition
        if (result.getType() == CommandResult.Type.UNKNOWN) {
            // Branch: checks a condition
            if (unknownCommandCallback != null) {
                // Access to the current/parent object
                this.unknownCommandCallback.apply(sender, command);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    /**
     * Executes the command using a {@link ServerSender}. This can be used
     * to run a silent command (nothing is printed to console).
     *
     * @see #execute(CommandSender, String)
     */
    // Start of a method/block
    public CommandResult executeServerCommand(String command) {
        // Returns a value to the caller
        return execute(serverSender, command);
    // End of a block/expression
    }

    // Start of a method/block
    public CommandDispatcher getDispatcher() {
        // Returns a value to the caller
        return dispatcher;
    // End of a block/expression
    }

    /**
     * Gets the callback executed once an unknown command is run.
     *
     * @return the unknown command callback, null if not any
     */
    // Start of a method/block
    public @Nullable CommandCallback getUnknownCommandCallback() {
        // Returns a value to the caller
        return unknownCommandCallback;
    // End of a block/expression
    }

    /**
     * Sets the callback executed once an unknown command is run.
     *
     * @param unknownCommandCallback the new unknown command callback,
     *                               setting it to null mean that nothing will be executed
     */
    // Start of a method/block
    public void setUnknownCommandCallback(@Nullable CommandCallback unknownCommandCallback) {
        // Access to the current/parent object
        this.unknownCommandCallback = unknownCommandCallback;
    // End of a block/expression
    }

    /**
     * Gets the {@link ConsoleSender} (which is used as a {@link CommandSender}).
     *
     * @return the {@link ConsoleSender}
     */
    // Start of a method/block
    public ConsoleSender getConsoleSender() {
        // Returns a value to the caller
        return consoleSender;
    // End of a block/expression
    }

    /**
     * Gets the {@link DeclareCommandsPacket} for a specific player.
     * <p>
     * Can be used to update a player auto-completion list.
     *
     * @param player the player to get the commands packet
     * @return the {@link DeclareCommandsPacket} for {@code player}
     */
    // Start of a method/block
    public DeclareCommandsPacket createDeclareCommandsPacket(Player player) {
        // Returns a value to the caller
        return GraphConverter.createPacket(getGraph(), player);
    // End of a block/expression
    }

    // Start of a method/block
    public Set<Command> getCommands() {
        // Returns a value to the caller
        return Collections.unmodifiableSet(commands);
    // End of a block/expression
    }

    /**
     * Parses the command based on the registered commands
     *
     * @param input commands string without prefix
     * @return the parsing result
     */
    // Start of a method/block
    public CommandParser.Result parseCommand(CommandSender sender, String input) {
        // Returns a value to the caller
        return parser.parse(sender, getGraph(), input);
    // End of a block/expression
    }

    // Start of a method/block
    private Graph getGraph() {
        // Assigns a value
        Graph graph = cachedGraph;
        // Branch: checks a condition
        if (graph == null) {
            // Start of a method/block
            synchronized (this) {
                // Assigns a value
                graph = cachedGraph;
                // Branch: checks a condition
                if (graph == null) {
                    // Calls a method
                    graph = cachedGraph = Graph.merge(getCommands());
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Returns a value to the caller
        return graph;
    // End of a block/expression
    }

    // Start of a method/block
    private void invalidateGraphCache() {
        // Assigns a value
        cachedGraph = null;
    // End of a block/expression
    }

    // Code statement
    private static CommandResult resultConverter(ExecutableCommand executable,
                                                 // Code statement
                                                 ExecutableCommand.Result newResult,
                                                 // Start of a method/block
                                                 String input) {
        // Returns a value to the caller
        return CommandResult.of(switch (newResult.type()) {
            // Multiple branching (switch/case)
            case SUCCESS -> CommandResult.Type.SUCCESS;
            // Multiple branching (switch/case)
            case CANCELLED, PRECONDITION_FAILED, EXECUTOR_EXCEPTION -> CommandResult.Type.CANCELLED;
            // Multiple branching (switch/case)
            case INVALID_SYNTAX -> CommandResult.Type.INVALID_SYNTAX;
            // Multiple branching (switch/case)
            case UNKNOWN -> CommandResult.Type.UNKNOWN;
        // Calls a method
        }, input, ParsedCommand.fromExecutable(executable), newResult.commandData());
    // End of a block/expression
    }
// End of a block/expression
}
