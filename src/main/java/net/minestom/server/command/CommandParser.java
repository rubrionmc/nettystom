// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.suggestion.Suggestion;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public interface CommandParser {
    // Début d'une méthode/d'un bloc
    static CommandParser parser() {
        // Renvoie une valeur à l'appelant
        return CommandParserImpl.PARSER;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Parses the command by following the graph
     *
     * @param graph structure to use for parsing
     * @param input command string without prefix
     * @return the parsed command which can be executed and cached
     */
    // Annotation pour l'élément suivant
    @Contract("_, _ -> new")
    // Appelle une méthode
    Result parse(CommandSender sender, Graph graph, String input);

    // Déclaration de type (classe/interface/enum/record)
    sealed interface Result {
        // Appelle une méthode
        ExecutableCommand executable();

        // Annotation pour l'élément suivant
        @ApiStatus.Internal
        // Annotation pour l'élément suivant
        @Nullable Suggestion suggestion(CommandSender sender);

        // Annotation pour l'élément suivant
        @ApiStatus.Internal
        // Appelle une méthode
        List<Argument<?>> args();

        // Déclaration de type (classe/interface/enum/record)
        sealed interface UnknownCommand extends Result
                // Début d'une méthode/d'un bloc
                permits CommandParserImpl.UnknownCommandResult {
        // Fin d'un bloc/d'une expression
        }

        // Déclaration de type (classe/interface/enum/record)
        sealed interface KnownCommand extends Result
                // Début d'une méthode/d'un bloc
                permits CommandParserImpl.InternalKnownCommand, Result.KnownCommand.Invalid, Result.KnownCommand.Valid {

            // Déclaration de type (classe/interface/enum/record)
            sealed interface Valid extends KnownCommand
                    // Début d'une méthode/d'un bloc
                    permits CommandParserImpl.ValidCommand {
            // Fin d'un bloc/d'une expression
            }

            // Déclaration de type (classe/interface/enum/record)
            sealed interface Invalid extends KnownCommand
                    // Début d'une méthode/d'un bloc
                    permits CommandParserImpl.InvalidCommand {
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
