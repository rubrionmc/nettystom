// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandResult;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.ParsedCommand;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.EventDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerCommandEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.DeclareCommandsPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.callback.CommandCallback;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.*;

/**
 * Manager used to register {@link Command commands}.
 * <p>
 * It is also possible to simulate a command using {@link #execute(CommandSender, String)}.
 */
// Déclaration de type (classe/interface/enum/record)
public final class CommandManager {

    // Affecte une valeur
    public static final String COMMAND_PREFIX = "/";

    // Appelle une méthode
    private final ServerSender serverSender = new ServerSender();
    // Appelle une méthode
    private final ConsoleSender consoleSender = new ConsoleSender();
    // Appelle une méthode
    private final CommandParser parser = CommandParser.parser();
    // Appelle une méthode
    private final CommandDispatcher dispatcher = new CommandDispatcher(this);
    // Appelle une méthode
    private final Map<String, Command> commandMap = new HashMap<>();
    // Appelle une méthode
    private final Set<Command> commands = new HashSet<>();

    // Instruction de code
    private CommandCallback unknownCommandCallback;
    // Instruction de code
    private volatile @Nullable Graph cachedGraph;

    // Début d'une méthode/d'un bloc
    public CommandManager() {
    // Fin d'un bloc/d'une expression
    }

    /**
     * Registers a {@link Command}.
     *
     * @param command the command to register
     * @throws IllegalStateException if a command with the same name already exists
     */
    // Début d'une méthode/d'un bloc
    public synchronized void register(Command command) {
        // Instruction de code
        Check.stateCondition(commandExists(command.getName()),
                // Appelle une méthode
                "A command with the name " + command.getName() + " is already registered!");
        // Embranchement : vérifie une condition
        if (command.getAliases() != null) {
            // Boucle : répète un bloc
            for (String alias : command.getAliases()) {
                // Instruction de code
                Check.stateCondition(commandExists(alias),
                        // Instruction de code
                        "A command with the name " + alias + " is already registered!");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        commands.add(command);
        // Boucle : répète un bloc
        for (String name : command.getNames()) {
            // Appelle une méthode
            commandMap.put(name, command);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        invalidateGraphCache();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Register multiple {@link Command}s.
     *
     * @param commands the array of commands
     * @throws IllegalStateException if a command with the same name already exists
     */
    // Début d'une méthode/d'un bloc
    public synchronized void register(Command... commands) {
        // Boucle : répète un bloc
        for (Command command : commands) {
            // Appelle une méthode
            register(command);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Removes a command from the currently registered commands.
     * Does nothing if the command was not registered before
     *
     * @param command the command to remove
     */
    // Début d'une méthode/d'un bloc
    public void unregister(Command command) {
        // Appelle une méthode
        commands.remove(command);
        // Boucle : répète un bloc
        for (String name : command.getNames()) {
            // Appelle une méthode
            commandMap.remove(name);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        invalidateGraphCache();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link Command} registered by {@link #register(Command)}.
     *
     * @param commandName the command name
     * @return the command associated with the name, null if not any
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Command getCommand(String commandName) {
        // Renvoie une valeur à l'appelant
        return commandMap.get(commandName.toLowerCase(Locale.ROOT));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if a command with the name {@code commandName} already exists or not.
     *
     * @param commandName the command name to check
     * @return true if the command does exist
     */
    // Début d'une méthode/d'un bloc
    public boolean commandExists(String commandName) {
        // Renvoie une valeur à l'appelant
        return getCommand(commandName) != null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Executes a command for a {@link CommandSender}.
     *
     * @param sender  the sender of the command
     * @param command the raw command string (without the command prefix)
     * @return the execution result
     */
    // Début d'une méthode/d'un bloc
    public CommandResult execute(CommandSender sender, String command) {
        // Appelle une méthode
        command = command.trim();
        // Command event
        // Embranchement : vérifie une condition
        if (sender instanceof Player player) {
            // Appelle une méthode
            PlayerCommandEvent playerCommandEvent = new PlayerCommandEvent(player, command);
            // Appelle une méthode
            EventDispatcher.call(playerCommandEvent);
            // Embranchement : vérifie une condition
            if (playerCommandEvent.isCancelled())
                // Renvoie une valeur à l'appelant
                return CommandResult.of(CommandResult.Type.CANCELLED, command);
            // Appelle une méthode
            command = playerCommandEvent.getCommand();
        // Fin d'un bloc/d'une expression
        }
        // Process the command
        // Appelle une méthode
        final CommandParser.Result parsedCommand = parseCommand(sender, command);
        // Appelle une méthode
        final ExecutableCommand executable = parsedCommand.executable();
        // Appelle une méthode
        final ExecutableCommand.Result executeResult = executable.execute(sender);
        // Appelle une méthode
        final CommandResult result = resultConverter(executable, executeResult, command);
        // Embranchement : vérifie une condition
        if (result.getType() == CommandResult.Type.UNKNOWN) {
            // Embranchement : vérifie une condition
            if (unknownCommandCallback != null) {
                // Accès à l'objet courant/parent
                this.unknownCommandCallback.apply(sender, command);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Executes the command using a {@link ServerSender}. This can be used
     * to run a silent command (nothing is printed to console).
     *
     * @see #execute(CommandSender, String)
     */
    // Début d'une méthode/d'un bloc
    public CommandResult executeServerCommand(String command) {
        // Renvoie une valeur à l'appelant
        return execute(serverSender, command);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public CommandDispatcher getDispatcher() {
        // Renvoie une valeur à l'appelant
        return dispatcher;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the callback executed once an unknown command is run.
     *
     * @return the unknown command callback, null if not any
     */
    // Début d'une méthode/d'un bloc
    public @Nullable CommandCallback getUnknownCommandCallback() {
        // Renvoie une valeur à l'appelant
        return unknownCommandCallback;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the callback executed once an unknown command is run.
     *
     * @param unknownCommandCallback the new unknown command callback,
     *                               setting it to null mean that nothing will be executed
     */
    // Début d'une méthode/d'un bloc
    public void setUnknownCommandCallback(@Nullable CommandCallback unknownCommandCallback) {
        // Accès à l'objet courant/parent
        this.unknownCommandCallback = unknownCommandCallback;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link ConsoleSender} (which is used as a {@link CommandSender}).
     *
     * @return the {@link ConsoleSender}
     */
    // Début d'une méthode/d'un bloc
    public ConsoleSender getConsoleSender() {
        // Renvoie une valeur à l'appelant
        return consoleSender;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link DeclareCommandsPacket} for a specific player.
     * <p>
     * Can be used to update a player auto-completion list.
     *
     * @param player the player to get the commands packet
     * @return the {@link DeclareCommandsPacket} for {@code player}
     */
    // Début d'une méthode/d'un bloc
    public DeclareCommandsPacket createDeclareCommandsPacket(Player player) {
        // Renvoie une valeur à l'appelant
        return GraphConverter.createPacket(getGraph(), player);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Set<Command> getCommands() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableSet(commands);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Parses the command based on the registered commands
     *
     * @param input commands string without prefix
     * @return the parsing result
     */
    // Début d'une méthode/d'un bloc
    public CommandParser.Result parseCommand(CommandSender sender, String input) {
        // Renvoie une valeur à l'appelant
        return parser.parse(sender, getGraph(), input);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private Graph getGraph() {
        // Affecte une valeur
        Graph graph = cachedGraph;
        // Embranchement : vérifie une condition
        if (graph == null) {
            // Début d'une méthode/d'un bloc
            synchronized (this) {
                // Affecte une valeur
                graph = cachedGraph;
                // Embranchement : vérifie une condition
                if (graph == null) {
                    // Appelle une méthode
                    graph = cachedGraph = Graph.merge(getCommands());
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return graph;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void invalidateGraphCache() {
        // Affecte une valeur
        cachedGraph = null;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static CommandResult resultConverter(ExecutableCommand executable,
                                                 // Instruction de code
                                                 ExecutableCommand.Result newResult,
                                                 // Début d'une méthode/d'un bloc
                                                 String input) {
        // Renvoie une valeur à l'appelant
        return CommandResult.of(switch (newResult.type()) {
            // Embranchement multiple (switch/case)
            case SUCCESS -> CommandResult.Type.SUCCESS;
            // Embranchement multiple (switch/case)
            case CANCELLED, PRECONDITION_FAILED, EXECUTOR_EXCEPTION -> CommandResult.Type.CANCELLED;
            // Embranchement multiple (switch/case)
            case INVALID_SYNTAX -> CommandResult.Type.INVALID_SYNTAX;
            // Embranchement multiple (switch/case)
            case UNKNOWN -> CommandResult.Type.UNKNOWN;
        // Appelle une méthode
        }, input, ParsedCommand.fromExecutable(executable), newResult.commandData());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
