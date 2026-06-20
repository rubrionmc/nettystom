// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandData;
// Import of a required class
import net.minestom.server.command.builder.CommandSyntax;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public interface ExecutableCommand {
    // Calls a method
    Result execute(CommandSender sender);

    // Type declaration (class/interface/enum/record)
    interface Result {
        // Calls a method
        Type type();

        // Calls a method
        CommandData commandData();

        // Type declaration (class/interface/enum/record)
        enum Type {
            /**
             * Command executed successfully.
             */
            // Code statement
            SUCCESS,
            /**
             * Command cancelled by an event listener.
             */
            // Code statement
            CANCELLED,
            /**
             * Either {@link Command#getCondition()} or {@link CommandSyntax#getCommandCondition()} failed
             */
            // Code statement
            PRECONDITION_FAILED,
            /**
             * Command couldn't be executed because of a syntax error
             */
            // Code statement
            INVALID_SYNTAX,
            /**
             * The command executor threw an exception
             */
            // Code statement
            EXECUTOR_EXCEPTION,
            /**
             * Unknown command
             */
            // Code statement
            UNKNOWN
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
