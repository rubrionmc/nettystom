// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.ExecutableCommand;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Represents a {@link Command} ready to be executed (already parsed).
 */
// Déclaration de type (classe/interface/enum/record)
public class ParsedCommand {
    // Instruction de code
    private final ExecutableCommand executableCommand;

    // Début d'une méthode/d'un bloc
    private ParsedCommand(ExecutableCommand executableCommand) {
        // Accès à l'objet courant/parent
        this.executableCommand = executableCommand;
    // Fin d'un bloc/d'une expression
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
    // Début d'une méthode/d'un bloc
    public @Nullable CommandData execute(CommandSender source) {
        // Appelle une méthode
        final ExecutableCommand.Result result = executableCommand.execute(source);
        // Renvoie une valeur à l'appelant
        return result.commandData();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static ParsedCommand fromExecutable(ExecutableCommand executableCommand) {
        // Renvoie une valeur à l'appelant
        return new ParsedCommand(executableCommand);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
