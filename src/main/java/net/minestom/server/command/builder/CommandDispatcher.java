// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandManager;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandParser;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Set;

/**
 * Class responsible for parsing {@link Command}.
 */
// Déclaration de type (classe/interface/enum/record)
public class CommandDispatcher {
    // Instruction de code
    private final CommandManager manager;

    // Début d'une méthode/d'un bloc
    public CommandDispatcher(CommandManager manager) {
        // Accès à l'objet courant/parent
        this.manager = manager;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public CommandDispatcher() {
        // Appelle une méthode
        this(new CommandManager());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Registers a command,
     * be aware that registering a command name or alias will override the previous entry.
     *
     * @param command the command to register
     */
    // Début d'une méthode/d'un bloc
    public void register(Command command) {
        // Appelle une méthode
        manager.register(command);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void unregister(Command command) {
        // Appelle une méthode
        manager.unregister(command);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Set<Command> getCommands() {
        // Renvoie une valeur à l'appelant
        return manager.getCommands();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the command class associated with the name.
     *
     * @param commandName the command name
     * @return the {@link Command} associated with the name, null if not any
     */
    // Début d'une méthode/d'un bloc
    public @Nullable Command findCommand(String commandName) {
        // Renvoie une valeur à l'appelant
        return manager.getCommand(commandName);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Checks if the command exists, and execute it.
     *
     * @param source        the command source
     * @param commandString the command with the argument(s)
     * @return the command result
     */
    // Début d'une méthode/d'un bloc
    public CommandResult execute(CommandSender source, String commandString) {
        // Renvoie une valeur à l'appelant
        return manager.execute(source, commandString);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Parses the given command.
     *
     * @param commandString the command (containing the command name and the args if any)
     * @return the parsing result
     */
    // Début d'une méthode/d'un bloc
    public CommandResult parse(CommandSender sender, String commandString) {
        // Appelle une méthode
        final net.minestom.server.command.CommandParser.Result test = manager.parseCommand(sender, commandString);
        // Renvoie une valeur à l'appelant
        return resultConverter(test, commandString);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static CommandResult resultConverter(net.minestom.server.command.CommandParser.Result parseResult, String input) {
        // Instruction de code
        CommandResult.Type type;
        // Embranchement : vérifie une condition
        if (parseResult instanceof CommandParser.Result.UnknownCommand) {
            // Affecte une valeur
            type = CommandResult.Type.UNKNOWN;
        // Embranchement : vérifie une condition
        } else if (parseResult instanceof CommandParser.Result.KnownCommand.Valid) {
            // Affecte une valeur
            type = CommandResult.Type.SUCCESS;
        // Embranchement : vérifie une condition
        } else if (parseResult instanceof CommandParser.Result.KnownCommand.Invalid) {
            // Affecte une valeur
            type = CommandResult.Type.INVALID_SYNTAX;
        // Branche alternative de la condition
        } else {
            // Lève une exception
            throw new IllegalStateException("Unknown CommandParser.Result type");
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return CommandResult.of(type, input, ParsedCommand.fromExecutable(parseResult.executable()), null);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
