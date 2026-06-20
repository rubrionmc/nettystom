// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.*;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class CommandSuggestionSubcommandTest {

    /**
     * Make sure that when we have a {@code /foo bar} and {@code /foo baz}, we use the correct default executor within
     * the command chain
     */
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void useProperDefaultExecutor() {
        // Appelle une méthode
        var manager = new CommandManager();
        // Appelle une méthode
        var command = new Command("foo");
        // Appelle une méthode
        var barCommand = new Command("bar");
        // Appelle une méthode
        var bazCommand = new Command("baz");

        // Appelle une méthode
        var wordArg1 = Word("wordArg1");
        // Appelle une méthode
        var wordArg2 = Word("wordArg2");

        // Début d'une méthode/d'un bloc
        command.setDefaultExecutor((sender, context) -> {
            // Since baz has a default executor, we shouldn't be calling this
            // Appelle une méthode
            fail("Command executor should not have been called");
        // Fin d'un bloc/d'une expression
        });

        // Début d'une méthode/d'un bloc
        barCommand.setDefaultExecutor((sender, context) -> {
            // This should never be called, original behaviour had this happen due to malformed command chain
            // Appelle une méthode
            fail("Bar subcommand executor should not have been called");
        // Fin d'un bloc/d'une expression
        });

        // This is the default executor we're expecting to call
        // Appelle une méthode
        bazCommand.setDefaultExecutor((sender, context) -> {});
        // Appelle une méthode
        bazCommand.addSyntax((sender, context) -> {}, wordArg1, wordArg2);

        // Appelle une méthode
        command.addSubcommand(barCommand);
        // Appelle une méthode
        command.addSubcommand(bazCommand);
        // Appelle une méthode
        manager.register(command);

        // Failing execution for the baz subcommand should not be calling any other default executor if itself has one
        // if we just did 'foo baz' this would work, however would call the wrong default executor (the one belonging to
        // bar) when we only provided a subset of the arguments rather than all.
        // Appelle une méthode
        manager.executeServerCommand("foo baz test");
    // Fin d'un bloc/d'une expression
    }

    /**
     * Make sure than when we have a {@code /foo} command, and we enter incorrect
     * arguments it defaults to the correct default executor
     */
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void useCorrectDefaultExecutor() {
        // Appelle une méthode
        var manager = new CommandManager();
        // Appelle une méthode
        var command = new Command("foo");
        // Appelle une méthode
        var barCommand = new Command("bar");
        // Appelle une méthode
        var bazCommand = new Command("baz", "qux");

        // Appelle une méthode
        var wordArg1 = Word("wordArg1");
        // Appelle une méthode
        var wordArg2 = Word("wordArg2");

        // Début d'une méthode/d'un bloc
        bazCommand.setDefaultExecutor((sender, context) -> {
            // Since the base command has a default executor, we shouldn't be calling this
            // Appelle une méthode
            fail("Baz subcommand command executor should not have been called");
        // Fin d'un bloc/d'une expression
        });

        // Début d'une méthode/d'un bloc
        barCommand.setDefaultExecutor((sender, context) -> {
            // This should never be called, original behaviour had this happen due to malformed command chain
            // Appelle une méthode
            fail("Bar subcommand executor should not have been called");
        // Fin d'un bloc/d'une expression
        });

        // This is the default executor we're expecting to call
        // Appelle une méthode
        command.setDefaultExecutor((sender, context) -> {});
        // Appelle une méthode
        bazCommand.addSyntax((sender, context) -> {}, wordArg1, wordArg2);

        // Appelle une méthode
        command.addSubcommand(barCommand);
        // Appelle une méthode
        command.addSubcommand(bazCommand);
        // Appelle une méthode
        manager.register(command);

        // Failing this means that the base command is defaulting to the incorrect executor
        // in the chain, it should be using the own default executor but its using one of the
        // subcommands's default executors
        // Appelle une méthode
        manager.executeServerCommand("foo abc");
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
