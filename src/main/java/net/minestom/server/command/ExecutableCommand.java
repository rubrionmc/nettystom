// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandData;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandSyntax;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public interface ExecutableCommand {
    // Appelle une méthode
    Result execute(CommandSender sender);

    // Déclaration de type (classe/interface/enum/record)
    interface Result {
        // Appelle une méthode
        Type type();

        // Appelle une méthode
        CommandData commandData();

        // Déclaration de type (classe/interface/enum/record)
        enum Type {
            /**
             * Command executed successfully.
             */
            // Instruction de code
            SUCCESS,
            /**
             * Command cancelled by an event listener.
             */
            // Instruction de code
            CANCELLED,
            /**
             * Either {@link Command#getCondition()} or {@link CommandSyntax#getCommandCondition()} failed
             */
            // Instruction de code
            PRECONDITION_FAILED,
            /**
             * Command couldn't be executed because of a syntax error
             */
            // Instruction de code
            INVALID_SYNTAX,
            /**
             * The command executor threw an exception
             */
            // Instruction de code
            EXECUTOR_EXCEPTION,
            /**
             * Unknown command
             */
            // Instruction de code
            UNKNOWN
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
