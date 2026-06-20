// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class GraphTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void empty() {
        // Assigns a value
        var result = Graph.builder(Literal(""))
                // Calls a method
                .build();
        // Calls a method
        var node = result.root();
        // Calls a method
        assertEquals(Literal(""), node.argument());
        // Calls a method
        assertTrue(node.next().isEmpty());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void next() {
        // Assigns a value
        var result = Graph.builder(Literal(""))
                // Code statement
                .append(Literal("foo"))
                // Calls a method
                .build();
        // Calls a method
        var node = result.root();
        // Calls a method
        assertEquals(Literal(""), node.argument());
        // Calls a method
        assertEquals(1, node.next().size());
        // Calls a method
        assertEquals(Literal("foo"), node.next().getFirst().argument());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void immutableNextBuilder() {
        // Assigns a value
        var result = Graph.builder(Literal(""))
                // Code statement
                .append(Literal("foo"))
                // Code statement
                .append(Literal("bar"))
                // Calls a method
                .build();
        // Calls a method
        var node = result.root();
        // Calls a method
        assertThrows(Exception.class, () -> result.root().next().add(node));
        // Calls a method
        assertThrows(Exception.class, () -> result.root().next().getFirst().next().add(node));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void immutableNextCommand() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        var first = Literal("first");
        // Calls a method
        foo.addSyntax(GraphTest::dummyExecutor, first);
        // Calls a method
        var result = Graph.fromCommand(foo);

        // Calls a method
        var node = result.root();
        // Calls a method
        assertThrows(Exception.class, () -> result.root().next().add(node));
        // Calls a method
        assertThrows(Exception.class, () -> result.root().next().getFirst().next().add(node));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void immutableNextCommands() {
        // Code statement
        final Command foo, bar;

        // Start of a block
        {
            // Calls a method
            var first = Literal("first");

            // Calls a method
            foo = new Command("foo");
            // Calls a method
            foo.addSyntax(GraphTest::dummyExecutor, first);

            // Calls a method
            bar = new Command("foo");
            // Calls a method
            bar.addSyntax(GraphTest::dummyExecutor, first);
        // End of a block/expression
        }

        // Calls a method
        var result = Graph.merge(List.of(foo, bar));

        // Calls a method
        var node = result.root();
        // Calls a method
        assertThrows(Exception.class, () -> result.root().next().add(node));
        // Calls a method
        assertThrows(Exception.class, () -> result.root().next().getFirst().next().add(node));
    // End of a block/expression
    }

    // Start of a method/block
    private static void dummyExecutor(CommandSender sender, CommandContext context) {
    // End of a block/expression
    }
// End of a block/expression
}
