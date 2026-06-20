// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.*;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class CommandSuggestionSubcommandTest {

    /**
     * Make sure that when we have a {@code /foo bar} and {@code /foo baz}, we use the correct default executor within
     * the command chain
     */
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void useProperDefaultExecutor() {
        // Calls a method
        var manager = new CommandManager();
        // Calls a method
        var command = new Command("foo");
        // Calls a method
        var barCommand = new Command("bar");
        // Calls a method
        var bazCommand = new Command("baz");

        // Calls a method
        var wordArg1 = Word("wordArg1");
        // Calls a method
        var wordArg2 = Word("wordArg2");

        // Start of a method/block
        command.setDefaultExecutor((sender, context) -> {
            // Since baz has a default executor, we shouldn't be calling this
            // Calls a method
            fail("Command executor should not have been called");
        // End of a block/expression
        });

        // Start of a method/block
        barCommand.setDefaultExecutor((sender, context) -> {
            // This should never be called, original behaviour had this happen due to malformed command chain
            // Calls a method
            fail("Bar subcommand executor should not have been called");
        // End of a block/expression
        });

        // This is the default executor we're expecting to call
        // Calls a method
        bazCommand.setDefaultExecutor((sender, context) -> {});
        // Calls a method
        bazCommand.addSyntax((sender, context) -> {}, wordArg1, wordArg2);

        // Calls a method
        command.addSubcommand(barCommand);
        // Calls a method
        command.addSubcommand(bazCommand);
        // Calls a method
        manager.register(command);

        // Failing execution for the baz subcommand should not be calling any other default executor if itself has one
        // if we just did 'foo baz' this would work, however would call the wrong default executor (the one belonging to
        // bar) when we only provided a subset of the arguments rather than all.
        // Calls a method
        manager.executeServerCommand("foo baz test");
    // End of a block/expression
    }

    /**
     * Make sure than when we have a {@code /foo} command, and we enter incorrect
     * arguments it defaults to the correct default executor
     */
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void useCorrectDefaultExecutor() {
        // Calls a method
        var manager = new CommandManager();
        // Calls a method
        var command = new Command("foo");
        // Calls a method
        var barCommand = new Command("bar");
        // Calls a method
        var bazCommand = new Command("baz", "qux");

        // Calls a method
        var wordArg1 = Word("wordArg1");
        // Calls a method
        var wordArg2 = Word("wordArg2");

        // Start of a method/block
        bazCommand.setDefaultExecutor((sender, context) -> {
            // Since the base command has a default executor, we shouldn't be calling this
            // Calls a method
            fail("Baz subcommand command executor should not have been called");
        // End of a block/expression
        });

        // Start of a method/block
        barCommand.setDefaultExecutor((sender, context) -> {
            // This should never be called, original behaviour had this happen due to malformed command chain
            // Calls a method
            fail("Bar subcommand executor should not have been called");
        // End of a block/expression
        });

        // This is the default executor we're expecting to call
        // Calls a method
        command.setDefaultExecutor((sender, context) -> {});
        // Calls a method
        bazCommand.addSyntax((sender, context) -> {}, wordArg1, wordArg2);

        // Calls a method
        command.addSubcommand(barCommand);
        // Calls a method
        command.addSubcommand(bazCommand);
        // Calls a method
        manager.register(command);

        // Failing this means that the base command is defaulting to the incorrect executor
        // in the chain, it should be using the own default executor but its using one of the
        // subcommands's default executors
        // Calls a method
        manager.executeServerCommand("foo abc");
    // End of a block/expression
    }
// End of a block/expression
}
