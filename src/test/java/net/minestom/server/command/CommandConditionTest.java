// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.kyori.adventure.identity.Identity;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandDispatcher;
// Import of a required class
import net.minestom.server.tag.TagHandler;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Integer;
// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class CommandConditionTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void mainCondition() {
        // Calls a method
        var dispatcher = new CommandDispatcher();
        // Calls a method
        assertNull(dispatcher.findCommand("name"));
        // Calls a method
        var sender = new Sender();
        // Calls a method
        var sender2 = new Sender();

        // Calls a method
        AtomicBoolean called = new AtomicBoolean(false);

        // Calls a method
        var command1 = new Command("name");
        // Calls a method
        command1.setDefaultExecutor((sender1, context) -> called.set(true));
        // Calls a method
        command1.setCondition((s, commandString) -> s == sender);

        // Calls a method
        dispatcher.register(command1);

        // Calls a method
        dispatcher.execute(sender, "name");
        // Calls a method
        assertTrue(called.get());

        // Calls a method
        called.set(false);
        // Calls a method
        dispatcher.execute(sender2, "name");
        // Calls a method
        assertFalse(called.get());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void subCondition() {
        // Calls a method
        var dispatcher = new CommandDispatcher();
        // Calls a method
        assertNull(dispatcher.findCommand("name"));
        // Calls a method
        var sender = new Sender();
        // Calls a method
        var sender2 = new Sender();

        // Calls a method
        AtomicBoolean called = new AtomicBoolean(false);

        // Calls a method
        var command1 = new Command("name");
        // Calls a method
        command1.setDefaultExecutor((sender1, context) -> called.set(true));

        // Start of a block
        {
            // Calls a method
            var sub = new Command("sub");
            // Calls a method
            sub.setDefaultExecutor((sender1, context) -> called.set(true));
            // Calls a method
            sub.setCondition((s, commandString) -> s == sender);

            // Calls a method
            command1.addSubcommand(sub);
        // End of a block/expression
        }

        // Calls a method
        dispatcher.register(command1);

        // Direct command
        // Start of a block
        {
            // Calls a method
            dispatcher.execute(sender, "name");
            // Calls a method
            assertTrue(called.get());

            // Calls a method
            called.set(false);
            // Calls a method
            dispatcher.execute(sender2, "name");
            // Calls a method
            assertTrue(called.get());
        // End of a block/expression
        }

        // Subcommand
        // Start of a block
        {
            // Calls a method
            called.set(false);
            // Calls a method
            dispatcher.execute(sender, "name sub");
            // Calls a method
            assertTrue(called.get());

            // Calls a method
            called.set(false);
            // Calls a method
            dispatcher.execute(sender2, "name sub");
            // Calls a method
            assertFalse(called.get());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void subConditionOverride() {
        // Calls a method
        var dispatcher = new CommandDispatcher();
        // Calls a method
        assertNull(dispatcher.findCommand("name"));
        // Calls a method
        var sender = new Sender();
        // Calls a method
        var sender2 = new Sender();

        // Calls a method
        AtomicBoolean called = new AtomicBoolean(false);

        // Calls a method
        var command1 = new Command("name");
        // Calls a method
        command1.setDefaultExecutor((sender1, context) -> called.set(true));
        // Calls a method
        command1.setCondition((s, commandString) -> s == sender);

        // Start of a block
        {
            // Calls a method
            var sub = new Command("sub");
            // Calls a method
            sub.setDefaultExecutor((sender1, context) -> called.set(true));
            // Calls a method
            command1.addSubcommand(sub);
        // End of a block/expression
        }

        // Calls a method
        dispatcher.register(command1);

        // Direct command
        // Start of a block
        {
            // Calls a method
            dispatcher.execute(sender, "name");
            // Calls a method
            assertTrue(called.get());

            // Calls a method
            called.set(false);
            // Calls a method
            dispatcher.execute(sender2, "name");
            // Calls a method
            assertFalse(called.get());
        // End of a block/expression
        }

        // Subcommand
        // Start of a block
        {
            // Calls a method
            called.set(false);
            // Calls a method
            dispatcher.execute(sender, "name sub");
            // Calls a method
            assertTrue(called.get());

            // Calls a method
            called.set(false);
            // Calls a method
            dispatcher.execute(sender2, "name sub");
            // Calls a method
            assertFalse(called.get(), "Subcommand execution should have been cancelled by parent command condition");
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void conditionBypassedByZeroArgSyntax() {
        // Calls a method
        var dispatcher = new CommandDispatcher();
        // Calls a method
        var adminSender = new Sender();
        // Calls a method
        var normalSender = new Sender();

        // Calls a method
        AtomicBoolean called = new AtomicBoolean(false);

        // Calls a method
        var command = new Command("admin");
        // Calls a method
        command.setCondition((sender, commandString) -> sender == adminSender);
        // Calls a method
        command.addSyntax((sender, context) -> called.set(true));

        // Calls a method
        dispatcher.register(command);

        // Calls a method
        dispatcher.execute(adminSender, "admin");
        // Calls a method
        assertTrue(called.get(), "Admin should be able to execute");

        // Calls a method
        called.set(false);
        // Calls a method
        dispatcher.execute(normalSender, "admin");
        // Calls a method
        assertFalse(called.get(), "Normal user should be blocked by command condition, but bug allows execution!");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void bothCommandAndSyntaxConditionsChecked() {
        // Calls a method
        var dispatcher = new CommandDispatcher();
        // Calls a method
        var sender1 = new Sender();
        // Calls a method
        var sender2 = new Sender();
        // Calls a method
        var sender3 = new Sender();

        // Calls a method
        AtomicBoolean called = new AtomicBoolean(false);

        // Calls a method
        var command = new Command("test");
        // Calls a method
        command.setCondition((sender, commandString) -> sender == sender1 || sender == sender2);
        // Code statement
        command.addConditionalSyntax((sender, commandString) -> sender == sender1 || sender == sender3,
                // Calls a method
                (sender, context) -> called.set(true));

        // Calls a method
        dispatcher.register(command);

        // Calls a method
        dispatcher.execute(sender1, "test");
        // Calls a method
        assertTrue(called.get(), "sender1 should satisfy both conditions");

        // Calls a method
        called.set(false);
        // Calls a method
        dispatcher.execute(sender2, "test");
        // Calls a method
        assertFalse(called.get(), "sender2 should be blocked by syntax condition");

        // Calls a method
        called.set(false);
        // Calls a method
        dispatcher.execute(sender3, "test");
        // Calls a method
        assertFalse(called.get(), "sender3 should be blocked by command condition");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void multipleZeroArgSyntaxesWithConditions() {
        // Calls a method
        var dispatcher = new CommandDispatcher();
        // Calls a method
        var sender1 = new Sender();
        // Calls a method
        var sender2 = new Sender();
        // Calls a method
        var sender3 = new Sender();

        // Calls a method
        AtomicInteger executionCount = new AtomicInteger(0);

        // Calls a method
        var command = new Command("multi");
        // Calls a method
        command.setCondition((sender, commandString) -> sender == sender1 || sender == sender2);

        // First zero-arg syntax with additional condition
        // Code statement
        command.addConditionalSyntax((sender, commandString) -> sender == sender1,
                // Calls a method
                (sender, context) -> executionCount.incrementAndGet());

        // Calls a method
        dispatcher.register(command);

        // sender1 should work (passes both conditions)
        // Calls a method
        dispatcher.execute(sender1, "multi");
        // Calls a method
        assertEquals(1, executionCount.get(), "sender1 should execute successfully");

        // sender2 should be blocked by syntax condition
        // Calls a method
        executionCount.set(0);
        // Calls a method
        dispatcher.execute(sender2, "multi");
        // Calls a method
        assertEquals(0, executionCount.get(), "sender2 should be blocked by syntax condition");

        // sender3 should be blocked by command condition
        // Calls a method
        dispatcher.execute(sender3, "multi");
        // Calls a method
        assertEquals(0, executionCount.get(), "sender3 should be blocked by command condition");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void defaultExecutorWithConditionAndSyntaxes() {
        // Calls a method
        var dispatcher = new CommandDispatcher();
        // Calls a method
        var adminSender = new Sender();
        // Calls a method
        var normalSender = new Sender();

        // Calls a method
        AtomicBoolean defaultCalled = new AtomicBoolean(false);
        // Calls a method
        AtomicBoolean syntaxCalled = new AtomicBoolean(false);

        // Calls a method
        var command = new Command("cmd");
        // Calls a method
        command.setCondition((sender, commandString) -> sender == adminSender);
        // Calls a method
        command.setDefaultExecutor((sender, context) -> defaultCalled.set(true));
        // Calls a method
        command.addSyntax((sender, context) -> syntaxCalled.set(true), Integer("value"));

        // Calls a method
        dispatcher.register(command);

        // Admin executing without args should trigger default executor
        // Calls a method
        dispatcher.execute(adminSender, "cmd");
        // Calls a method
        assertTrue(defaultCalled.get(), "Admin should execute default executor");
        // Calls a method
        assertFalse(syntaxCalled.get());

        // Normal user should be blocked even from default executor
        // Calls a method
        defaultCalled.set(false);
        // Calls a method
        dispatcher.execute(normalSender, "cmd");
        // Calls a method
        assertFalse(defaultCalled.get(), "Normal user should be blocked from default executor");
        // Calls a method
        assertFalse(syntaxCalled.get());

        // Admin with valid syntax
        // Calls a method
        dispatcher.execute(adminSender, "cmd 42");
        // Calls a method
        assertTrue(syntaxCalled.get(), "Admin should execute syntax");

        // Normal user should be blocked from syntax too
        // Calls a method
        syntaxCalled.set(false);
        // Calls a method
        dispatcher.execute(normalSender, "cmd 42");
        // Calls a method
        assertFalse(syntaxCalled.get(), "Normal user should be blocked from syntax");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void deeplyNestedSubcommandConditions() {
        // Calls a method
        var dispatcher = new CommandDispatcher();
        // Calls a method
        var adminSender = new Sender();
        // Calls a method
        var normalSender = new Sender();

        // Calls a method
        AtomicBoolean called = new AtomicBoolean(false);

        // Calls a method
        var rootCmd = new Command("root");
        // Calls a method
        rootCmd.setCondition((sender, commandString) -> sender == adminSender);

        // Calls a method
        var level1 = new Command("level1");
        // Calls a method
        var level2 = new Command("level2");
        // Calls a method
        var level3 = new Command("level3");
        // Calls a method
        level3.setDefaultExecutor((sender, context) -> called.set(true));

        // Calls a method
        level2.addSubcommand(level3);
        // Calls a method
        level1.addSubcommand(level2);
        // Calls a method
        rootCmd.addSubcommand(level1);

        // Calls a method
        dispatcher.register(rootCmd);

        // Admin should be able to execute deeply nested command
        // Calls a method
        dispatcher.execute(adminSender, "root level1 level2 level3");
        // Calls a method
        assertTrue(called.get(), "Admin should execute deeply nested command");

        // Normal user should be blocked at root level
        // Calls a method
        called.set(false);
        // Calls a method
        dispatcher.execute(normalSender, "root level1 level2 level3");
        // Calls a method
        assertFalse(called.get(), "Normal user should be blocked by root condition");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void syntaxConditionOnlyNoCommandCondition() {
        // Calls a method
        var dispatcher = new CommandDispatcher();
        // Calls a method
        var sender1 = new Sender();
        // Calls a method
        var sender2 = new Sender();

        // Calls a method
        AtomicBoolean called = new AtomicBoolean(false);

        // Calls a method
        var command = new Command("test");
        // No command condition set
        // Code statement
        command.addConditionalSyntax((sender, commandString) -> sender == sender1,
                // Calls a method
                (sender, context) -> called.set(true));

        // Calls a method
        dispatcher.register(command);

        // sender1 should execute (passes syntax condition)
        // Calls a method
        dispatcher.execute(sender1, "test");
        // Calls a method
        assertTrue(called.get(), "sender1 should execute with syntax condition");

        // sender2 should be blocked (fails syntax condition)
        // Calls a method
        called.set(false);
        // Calls a method
        dispatcher.execute(sender2, "test");
        // Calls a method
        assertFalse(called.get(), "sender2 should be blocked by syntax condition");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void mixedSyntaxConditions() {
        // Calls a method
        var dispatcher = new CommandDispatcher();
        // Calls a method
        var sender1 = new Sender();
        // Calls a method
        var sender2 = new Sender();
        // Calls a method
        var sender3 = new Sender();

        // Calls a method
        AtomicInteger whichSyntax = new AtomicInteger(0);

        // Calls a method
        var command = new Command("mixed");
        // Calls a method
        command.setCondition((sender, commandString) -> sender == sender1 || sender == sender2);

        // Syntax 1: zero-arg, no additional condition (should use command condition only)
        // Calls a method
        command.addSyntax((sender, context) -> whichSyntax.set(1));

        // Syntax 2: with arg and additional condition
        // Code statement
        command.addConditionalSyntax((sender, commandString) -> sender == sender1,
                // Calls a method
                (sender, context) -> whichSyntax.set(2), Literal("special"));

        // Calls a method
        dispatcher.register(command);

        // sender1 can execute both syntaxes
        // Calls a method
        dispatcher.execute(sender1, "mixed");
        // Calls a method
        assertEquals(1, whichSyntax.get(), "sender1 should execute syntax 1");

        // Calls a method
        whichSyntax.set(0);
        // Calls a method
        dispatcher.execute(sender1, "mixed special");
        // Calls a method
        assertEquals(2, whichSyntax.get(), "sender1 should execute syntax 2");

        // sender2 can execute syntax 1 but not syntax 2
        // Calls a method
        whichSyntax.set(0);
        // Calls a method
        dispatcher.execute(sender2, "mixed");
        // Calls a method
        assertEquals(1, whichSyntax.get(), "sender2 should execute syntax 1");

        // Calls a method
        whichSyntax.set(0);
        // Calls a method
        dispatcher.execute(sender2, "mixed special");
        // Calls a method
        assertEquals(0, whichSyntax.get(), "sender2 should be blocked from syntax 2");

        // sender3 should be blocked from everything
        // Calls a method
        dispatcher.execute(sender3, "mixed");
        // Calls a method
        assertEquals(0, whichSyntax.get(), "sender3 should be blocked by command condition");

        // Calls a method
        dispatcher.execute(sender3, "mixed special");
        // Calls a method
        assertEquals(0, whichSyntax.get(), "sender3 should be blocked by command condition");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void subcommandWithOwnConditionRequiresBoth() {
        // Calls a method
        var dispatcher = new CommandDispatcher();
        // Calls a method
        var sender1 = new Sender();
        // Calls a method
        var sender2 = new Sender();
        // Calls a method
        var sender3 = new Sender();

        // Calls a method
        AtomicBoolean called = new AtomicBoolean(false);

        // Calls a method
        var parent = new Command("parent");
        // Calls a method
        parent.setCondition((sender, commandString) -> sender == sender1 || sender == sender2);

        // Calls a method
        var child = new Command("child");
        // Calls a method
        child.setCondition((sender, commandString) -> sender == sender1 || sender == sender3);
        // Calls a method
        child.setDefaultExecutor((sender, context) -> called.set(true));

        // Calls a method
        parent.addSubcommand(child);
        // Calls a method
        dispatcher.register(parent);

        // sender1 passes both conditions
        // Calls a method
        dispatcher.execute(sender1, "parent child");
        // Calls a method
        assertTrue(called.get(), "sender1 should pass both conditions");

        // sender2 passes parent but not child
        // Calls a method
        called.set(false);
        // Calls a method
        dispatcher.execute(sender2, "parent child");
        // Calls a method
        assertFalse(called.get(), "sender2 should be blocked by child condition");

        // sender3 passes child but not parent
        // Calls a method
        dispatcher.execute(sender3, "parent child");
        // Calls a method
        assertFalse(called.get(), "sender3 should be blocked by parent condition");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void zeroArgSyntaxAndDefaultExecutorWithCondition() {
        // Calls a method
        var dispatcher = new CommandDispatcher();
        // Calls a method
        var adminSender = new Sender();
        // Calls a method
        var normalSender = new Sender();

        // Calls a method
        AtomicBoolean defaultCalled = new AtomicBoolean(false);
        // Calls a method
        AtomicBoolean syntaxCalled = new AtomicBoolean(false);

        // Calls a method
        var command = new Command("cmd");
        // Calls a method
        command.setCondition((sender, commandString) -> sender == adminSender);
        // Calls a method
        command.setDefaultExecutor((sender, context) -> defaultCalled.set(true));
        // Code statement
        command.addSyntax((sender, context) -> syntaxCalled.set(true)); // zero-arg syntax

        // Calls a method
        dispatcher.register(command);

        // Admin should execute the syntax (syntax takes precedence over default executor)
        // Calls a method
        dispatcher.execute(adminSender, "cmd");
        // Calls a method
        assertTrue(syntaxCalled.get(), "Admin should execute zero-arg syntax");
        // Calls a method
        assertFalse(defaultCalled.get(), "Default executor should not be called when syntax matches");

        // Normal user should be blocked
        // Calls a method
        syntaxCalled.set(false);
        // Calls a method
        dispatcher.execute(normalSender, "cmd");
        // Calls a method
        assertFalse(syntaxCalled.get(), "Normal user should be blocked from syntax");
        // Calls a method
        assertFalse(defaultCalled.get(), "Normal user should be blocked from default executor");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testGraphZeroArgCondition() {
        // Calls a method
        var adminSender = new Sender();
        // Calls a method
        var normalSender = new Sender();

        // Calls a method
        var command = new Command("admin");
        // Calls a method
        command.setCondition((sender, commandString) -> sender == adminSender);
        // Calls a method
        command.addSyntax((sender, context) -> {});

        // Calls a method
        Graph graph = Graph.fromCommand(command);
        // Calls a method
        Graph.Node root = graph.root();

        // Calls a method
        assertNotNull(root.execution(), "Root node should have execution");
        // Calls a method
        assertNotNull(root.execution().condition(), "Root node should preserve command condition");
        // Calls a method
        assertFalse(root.execution().test(normalSender), "Normal sender should fail condition check");
        // Calls a method
        assertTrue(root.execution().test(adminSender), "Admin sender should pass condition check");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void graphPreservesConditionsForSyntaxWithArguments() {
        // Calls a method
        var adminSender = new Sender();

        // Calls a method
        var command = new Command("admin");
        // Calls a method
        command.setCondition((sender, commandString) -> sender == adminSender);
        // Calls a method
        command.addSyntax((sender, context) -> {}, Integer("value"));

        // Calls a method
        Graph graph = Graph.fromCommand(command);
        // Calls a method
        Graph.Node root = graph.root();

        // Calls a method
        assertNotNull(root.execution(), "Root should have execution");
        // Calls a method
        assertNotNull(root.execution().condition(), "Root should have command condition");

        // Check that the argument node also has condition info propagated
        // Calls a method
        assertEquals(1, root.next().size(), "Should have one child for the Integer argument");
        // Calls a method
        Graph.Node argNode = root.next().getFirst();
        // Calls a method
        assertNotNull(argNode.execution(), "Argument node should have execution");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void graphComparisonDetectsConditionDifferences() {
        // Calls a method
        var adminSender = new Sender();

        // Calls a method
        var command1 = new Command("test");
        // Calls a method
        command1.setCondition((sender, commandString) -> sender == adminSender);
        // Calls a method
        command1.addSyntax((sender, context) -> {});

        // Calls a method
        var command2 = new Command("test");
        // No condition
        // Calls a method
        command2.addSyntax((sender, context) -> {});

        // Calls a method
        Graph graph1 = Graph.fromCommand(command1);
        // Calls a method
        Graph graph2 = Graph.fromCommand(command2);

        // Code statement
        assertFalse(graph1.compare(graph2, Graph.Comparator.TREE),
                // Code statement
                "Graphs with different conditions should not be equal");
    // End of a block/expression
    }

    // Start of a method/block
    private static final class Sender implements CommandSender {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public TagHandler tagHandler() {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Identity identity() {
            // Returns a value to the caller
            return Identity.nil();
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
