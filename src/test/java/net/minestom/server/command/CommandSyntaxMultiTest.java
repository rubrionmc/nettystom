// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.lang.String;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Float;
// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Integer;
// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.*;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.fail;

// Type declaration (class/interface/enum/record)
public class CommandSyntaxMultiTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void integerFloat() {
        // Assigns a value
        List<List<Argument<?>>> args = List.of(
                // Code statement
                List.of(Literal("integer"), Integer("number")),
                // Code statement
                List.of(Literal("float"), Float("number"))
        // End of a block/expression
        );
        // Calls a method
        assertSyntax(args, "integer 5", ExpectedExecution.FIRST_SYNTAX, Map.of("integer", "integer", "number", 5));
        // Calls a method
        assertSyntax(args, "float 5.5", ExpectedExecution.SECOND_SYNTAX, Map.of("float", "float", "number", 5.5f));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void argPriority() {
        // Assigns a value
        List<List<Argument<?>>> args = List.of(
                // Code statement
                List.of(Word("word")),
                // Code statement
                List.of(Literal("literal"))
        // End of a block/expression
        );
        // Calls a method
        assertSyntax(args, "literal", ExpectedExecution.SECOND_SYNTAX);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void similarArgs() {
        // Assigns a value
        List<List<Argument<?>>> args = List.of(
                // Code statement
                List.of(Word("a")),
                // Code statement
                List.of(Word("b"), Word("a"))
        // End of a block/expression
        );
        // Calls a method
        assertSyntax(args, "baz", ExpectedExecution.FIRST_SYNTAX);
        // Calls a method
        assertSyntax(args, "bar baz", ExpectedExecution.SECOND_SYNTAX);
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertSyntax(List<List<Argument<?>>> args, String input, ExpectedExecution expectedExecution, Map<String, Object> expectedValues) {
        // Assigns a value
        final String commandName = "name";

        // Calls a method
        var manager = new CommandManager();
        // Calls a method
        var command = new Command(commandName);
        // Calls a method
        manager.register(command);

        // Calls a method
        AtomicReference<ExpectedExecution> result = new AtomicReference<>();
        // Calls a method
        AtomicReference<Map<String, Object>> values = new AtomicReference<>();

        // Start of a method/block
        command.setDefaultExecutor((sender, context) -> {
            // Branch: checks a condition
            if (!result.compareAndSet(null, ExpectedExecution.DEFAULT)) {
                // Calls a method
                fail("Multiple execution: " + result.get());
            // End of a block/expression
            }
        // End of a block/expression
        });

        // Calls a method
        int i = ExpectedExecution.FIRST_SYNTAX.ordinal();
        // Loop: repeats a block
        for (List<Argument<?>> t : args) {
            // Calls a method
            ExpectedExecution id = ExpectedExecution.values()[i++];
            // Start of a method/block
            command.addSyntax((sender, context) -> {
                // Branch: checks a condition
                if (!result.compareAndSet(null, id)) {
                    // Calls a method
                    fail("Multiple execution: " + result.get());
                // End of a block/expression
                }
                // Calls a method
                values.set(context.getMap());
            // Calls a method
            }, t.toArray(Argument[]::new));
        // End of a block/expression
        }

        // Assigns a value
        final String executeString = commandName + " " + input;
        // Calls a method
        manager.executeServerCommand(executeString);
        // Calls a method
        assertEquals(expectedExecution, result.get());
        // Branch: checks a condition
        if (expectedValues != null) {
            // Calls a method
            assertEquals(expectedValues, values.get());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertSyntax(List<List<Argument<?>>> args, String input, ExpectedExecution expectedExecution) {
        // Calls a method
        assertSyntax(args, input, expectedExecution, null);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    enum ExpectedExecution {
        // Code statement
        DEFAULT,

        // Code statement
        FIRST_SYNTAX,
        // Code statement
        SECOND_SYNTAX,
        // Code statement
        THIRD_SYNTAX
    // End of a block/expression
    }
// End of a block/expression
}
