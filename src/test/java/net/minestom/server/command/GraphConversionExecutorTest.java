// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.condition.CommandCondition;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class GraphConversionExecutorTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void defaultCondition() {
        // Calls a method
        final Command foo = new Command("foo");
        // Constant true
        // Start of a block
        {
            // Calls a method
            foo.setCondition((sender, commandString) -> true);
            // Calls a method
            var graph = Graph.fromCommand(foo);
            // Calls a method
            var execution = graph.root().execution();
            // Calls a method
            assertNotNull(execution);
            // Calls a method
            assertTrue(execution.test(null));
        // End of a block/expression
        }
        // Constant false
        // Start of a block
        {
            // Calls a method
            foo.setCondition((sender, commandString) -> false);
            // Calls a method
            var graph = Graph.fromCommand(foo);
            // Calls a method
            var execution = graph.root().execution();
            // Calls a method
            assertNotNull(execution);
            // Calls a method
            assertFalse(execution.test(null));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void emptySyntaxCondition() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.addSyntax(GraphConversionExecutorTest::dummyExecutor, Literal("first"));

        // Calls a method
        var graph = Graph.fromCommand(foo);
        // Calls a method
        assertEquals(1, graph.root().next().size());
        // Calls a method
        var execution = graph.root().next().getFirst().execution();
        // Calls a method
        assertNotNull(execution);
        // Calls a method
        assertNull(execution.condition());
        // Calls a method
        assertNotNull(execution.executor());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void syntaxConditionTrue() {
        // Calls a method
        final Command foo = new Command("foo");
        // Code statement
        foo.addConditionalSyntax((sender, context) -> true,
                // Calls a method
                GraphConversionExecutorTest::dummyExecutor, Literal("first"));

        // Calls a method
        var graph = Graph.fromCommand(foo);
        // Calls a method
        assertEquals(1, graph.root().next().size());
        // Calls a method
        var execution = graph.root().next().getFirst().execution();
        // Calls a method
        assertNotNull(execution);
        // Calls a method
        assertTrue(execution.test(null));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void syntaxConditionFalse() {
        // Calls a method
        final Command foo = new Command("foo");
        // Code statement
        foo.addConditionalSyntax((sender, context) -> false,
                // Calls a method
                GraphConversionExecutorTest::dummyExecutor, Literal("first"));

        // Calls a method
        var graph = Graph.fromCommand(foo);
        // Calls a method
        assertEquals(1, graph.root().next().size());
        // Calls a method
        var execution = graph.root().next().getFirst().execution();
        // Calls a method
        assertNotNull(execution);
        // Calls a method
        assertFalse(execution.test(null));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void commandConditionFalse() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        foo.setCondition((sender, commandString) -> false);
        // Calls a method
        final Graph graph = Graph.fromCommand(foo);
        // Calls a method
        final Graph.Execution execution = graph.root().execution();
        // Calls a method
        assertNotNull(execution);
        // Calls a method
        final CommandCondition condition = execution.condition();
        // Calls a method
        assertNotNull(condition);
        // Calls a method
        assertFalse(condition.canUse(null, null));
    // End of a block/expression
    }

    // Start of a method/block
    private static void dummyExecutor(CommandSender sender, CommandContext context) {
    // End of a block/expression
    }
// End of a block/expression
}
