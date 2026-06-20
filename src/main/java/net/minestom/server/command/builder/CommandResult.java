// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class CommandResult {

    // Affecte une valeur
    protected Type type = Type.UNKNOWN;
    // Instruction de code
    protected String input;
    // Instruction de code
    protected ParsedCommand parsedCommand;
    // Instruction de code
    protected CommandData commandData;

    // Début d'une méthode/d'un bloc
    public Type getType() {
        // Renvoie une valeur à l'appelant
        return type;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public String getInput() {
        // Renvoie une valeur à l'appelant
        return input;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable ParsedCommand getParsedCommand() {
        // Renvoie une valeur à l'appelant
        return parsedCommand;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable CommandData getCommandData() {
        // Renvoie une valeur à l'appelant
        return commandData;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Type {
        /**
         * Command and syntax successfully found.
         */
        // Instruction de code
        SUCCESS,
        /**
         * Command found, but the syntax is invalid.
         * Executor sets to {@link Command#getDefaultExecutor()}.
         */
        // Instruction de code
        INVALID_SYNTAX,
        /**
         * Command cancelled by an event listener.
         */
        // Instruction de code
        CANCELLED,
        /**
         * Command is not registered, it is also the default result type.
         */
        // Instruction de code
        UNKNOWN
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static CommandResult of(Type type, String input) {
        // Appelle une méthode
        CommandResult result = new CommandResult();
        // Affecte une valeur
        result.type = type;
        // Affecte une valeur
        result.input = input;
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static CommandResult of(Type type, String input, ParsedCommand parsedCommand, CommandData data) {
        // Appelle une méthode
        CommandResult result = new CommandResult();
        // Affecte une valeur
        result.type = type;
        // Affecte une valeur
        result.input = input;
        // Affecte une valeur
        result.parsedCommand = parsedCommand;
        // Affecte une valeur
        result.commandData = data;
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
