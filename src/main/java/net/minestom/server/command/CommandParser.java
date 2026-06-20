// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.suggestion.Suggestion;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public interface CommandParser {
    // Start of a method/block
    static CommandParser parser() {
        // Returns a value to the caller
        return CommandParserImpl.PARSER;
    // End of a block/expression
    }

    /**
     * Parses the command by following the graph
     *
     * @param graph structure to use for parsing
     * @param input command string without prefix
     * @return the parsed command which can be executed and cached
     */
    // Annotation for the following element
    @Contract("_, _ -> new")
    // Calls a method
    Result parse(CommandSender sender, Graph graph, String input);

    // Type declaration (class/interface/enum/record)
    sealed interface Result {
        // Calls a method
        ExecutableCommand executable();

        // Annotation for the following element
        @ApiStatus.Internal
        // Annotation for the following element
        @Nullable Suggestion suggestion(CommandSender sender);

        // Annotation for the following element
        @ApiStatus.Internal
        // Calls a method
        List<Argument<?>> args();

        // Type declaration (class/interface/enum/record)
        sealed interface UnknownCommand extends Result
                // Start of a method/block
                permits CommandParserImpl.UnknownCommandResult {
        // End of a block/expression
        }

        // Type declaration (class/interface/enum/record)
        sealed interface KnownCommand extends Result
                // Start of a method/block
                permits CommandParserImpl.InternalKnownCommand, Result.KnownCommand.Invalid, Result.KnownCommand.Valid {

            // Type declaration (class/interface/enum/record)
            sealed interface Valid extends KnownCommand
                    // Start of a method/block
                    permits CommandParserImpl.ValidCommand {
            // End of a block/expression
            }

            // Type declaration (class/interface/enum/record)
            sealed interface Invalid extends KnownCommand
                    // Start of a method/block
                    permits CommandParserImpl.InvalidCommand {
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
