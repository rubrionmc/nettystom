// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.kyori.adventure.identity.Identity;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandDispatcher;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagHandler;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Integer;
// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class CommandConditionTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void mainCondition() {
        // Appelle une méthode
        var dispatcher = new CommandDispatcher();
        // Appelle une méthode
        assertNull(dispatcher.findCommand("name"));
        // Appelle une méthode
        var sender = new Sender();
        // Appelle une méthode
        var sender2 = new Sender();

        // Appelle une méthode
        AtomicBoolean called = new AtomicBoolean(false);

        // Appelle une méthode
        var command1 = new Command("name");
        // Appelle une méthode
        command1.setDefaultExecutor((sender1, context) -> called.set(true));
        // Appelle une méthode
        command1.setCondition((s, commandString) -> s == sender);

        // Appelle une méthode
        dispatcher.register(command1);

        // Appelle une méthode
        dispatcher.execute(sender, "name");
        // Appelle une méthode
        assertTrue(called.get());

        // Appelle une méthode
        called.set(false);
        // Appelle une méthode
        dispatcher.execute(sender2, "name");
        // Appelle une méthode
        assertFalse(called.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void subCondition() {
        // Appelle une méthode
        var dispatcher = new CommandDispatcher();
        // Appelle une méthode
        assertNull(dispatcher.findCommand("name"));
        // Appelle une méthode
        var sender = new Sender();
        // Appelle une méthode
        var sender2 = new Sender();

        // Appelle une méthode
        AtomicBoolean called = new AtomicBoolean(false);

        // Appelle une méthode
        var command1 = new Command("name");
        // Appelle une méthode
        command1.setDefaultExecutor((sender1, context) -> called.set(true));

        // Début d'un bloc
        {
            // Appelle une méthode
            var sub = new Command("sub");
            // Appelle une méthode
            sub.setDefaultExecutor((sender1, context) -> called.set(true));
            // Appelle une méthode
            sub.setCondition((s, commandString) -> s == sender);

            // Appelle une méthode
            command1.addSubcommand(sub);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        dispatcher.register(command1);

        // Direct command
        // Début d'un bloc
        {
            // Appelle une méthode
            dispatcher.execute(sender, "name");
            // Appelle une méthode
            assertTrue(called.get());

            // Appelle une méthode
            called.set(false);
            // Appelle une méthode
            dispatcher.execute(sender2, "name");
            // Appelle une méthode
            assertTrue(called.get());
        // Fin d'un bloc/d'une expression
        }

        // Subcommand
        // Début d'un bloc
        {
            // Appelle une méthode
            called.set(false);
            // Appelle une méthode
            dispatcher.execute(sender, "name sub");
            // Appelle une méthode
            assertTrue(called.get());

            // Appelle une méthode
            called.set(false);
            // Appelle une méthode
            dispatcher.execute(sender2, "name sub");
            // Appelle une méthode
            assertFalse(called.get());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void subConditionOverride() {
        // Appelle une méthode
        var dispatcher = new CommandDispatcher();
        // Appelle une méthode
        assertNull(dispatcher.findCommand("name"));
        // Appelle une méthode
        var sender = new Sender();
        // Appelle une méthode
        var sender2 = new Sender();

        // Appelle une méthode
        AtomicBoolean called = new AtomicBoolean(false);

        // Appelle une méthode
        var command1 = new Command("name");
        // Appelle une méthode
        command1.setDefaultExecutor((sender1, context) -> called.set(true));
        // Appelle une méthode
        command1.setCondition((s, commandString) -> s == sender);

        // Début d'un bloc
        {
            // Appelle une méthode
            var sub = new Command("sub");
            // Appelle une méthode
            sub.setDefaultExecutor((sender1, context) -> called.set(true));
            // Appelle une méthode
            command1.addSubcommand(sub);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        dispatcher.register(command1);

        // Direct command
        // Début d'un bloc
        {
            // Appelle une méthode
            dispatcher.execute(sender, "name");
            // Appelle une méthode
            assertTrue(called.get());

            // Appelle une méthode
            called.set(false);
            // Appelle une méthode
            dispatcher.execute(sender2, "name");
            // Appelle une méthode
            assertFalse(called.get());
        // Fin d'un bloc/d'une expression
        }

        // Subcommand
        // Début d'un bloc
        {
            // Appelle une méthode
            called.set(false);
            // Appelle une méthode
            dispatcher.execute(sender, "name sub");
            // Appelle une méthode
            assertTrue(called.get());

            // Appelle une méthode
            called.set(false);
            // Appelle une méthode
            dispatcher.execute(sender2, "name sub");
            // Appelle une méthode
            assertFalse(called.get(), "Subcommand execution should have been cancelled by parent command condition");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void conditionBypassedByZeroArgSyntax() {
        // Appelle une méthode
        var dispatcher = new CommandDispatcher();
        // Appelle une méthode
        var adminSender = new Sender();
        // Appelle une méthode
        var normalSender = new Sender();

        // Appelle une méthode
        AtomicBoolean called = new AtomicBoolean(false);

        // Appelle une méthode
        var command = new Command("admin");
        // Appelle une méthode
        command.setCondition((sender, commandString) -> sender == adminSender);
        // Appelle une méthode
        command.addSyntax((sender, context) -> called.set(true));

        // Appelle une méthode
        dispatcher.register(command);

        // Appelle une méthode
        dispatcher.execute(adminSender, "admin");
        // Appelle une méthode
        assertTrue(called.get(), "Admin should be able to execute");

        // Appelle une méthode
        called.set(false);
        // Appelle une méthode
        dispatcher.execute(normalSender, "admin");
        // Appelle une méthode
        assertFalse(called.get(), "Normal user should be blocked by command condition, but bug allows execution!");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void bothCommandAndSyntaxConditionsChecked() {
        // Appelle une méthode
        var dispatcher = new CommandDispatcher();
        // Appelle une méthode
        var sender1 = new Sender();
        // Appelle une méthode
        var sender2 = new Sender();
        // Appelle une méthode
        var sender3 = new Sender();

        // Appelle une méthode
        AtomicBoolean called = new AtomicBoolean(false);

        // Appelle une méthode
        var command = new Command("test");
        // Appelle une méthode
        command.setCondition((sender, commandString) -> sender == sender1 || sender == sender2);
        // Instruction de code
        command.addConditionalSyntax((sender, commandString) -> sender == sender1 || sender == sender3,
                // Appelle une méthode
                (sender, context) -> called.set(true));

        // Appelle une méthode
        dispatcher.register(command);

        // Appelle une méthode
        dispatcher.execute(sender1, "test");
        // Appelle une méthode
        assertTrue(called.get(), "sender1 should satisfy both conditions");

        // Appelle une méthode
        called.set(false);
        // Appelle une méthode
        dispatcher.execute(sender2, "test");
        // Appelle une méthode
        assertFalse(called.get(), "sender2 should be blocked by syntax condition");

        // Appelle une méthode
        called.set(false);
        // Appelle une méthode
        dispatcher.execute(sender3, "test");
        // Appelle une méthode
        assertFalse(called.get(), "sender3 should be blocked by command condition");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void multipleZeroArgSyntaxesWithConditions() {
        // Appelle une méthode
        var dispatcher = new CommandDispatcher();
        // Appelle une méthode
        var sender1 = new Sender();
        // Appelle une méthode
        var sender2 = new Sender();
        // Appelle une méthode
        var sender3 = new Sender();

        // Appelle une méthode
        AtomicInteger executionCount = new AtomicInteger(0);

        // Appelle une méthode
        var command = new Command("multi");
        // Appelle une méthode
        command.setCondition((sender, commandString) -> sender == sender1 || sender == sender2);

        // First zero-arg syntax with additional condition
        // Instruction de code
        command.addConditionalSyntax((sender, commandString) -> sender == sender1,
                // Appelle une méthode
                (sender, context) -> executionCount.incrementAndGet());

        // Appelle une méthode
        dispatcher.register(command);

        // sender1 should work (passes both conditions)
        // Appelle une méthode
        dispatcher.execute(sender1, "multi");
        // Appelle une méthode
        assertEquals(1, executionCount.get(), "sender1 should execute successfully");

        // sender2 should be blocked by syntax condition
        // Appelle une méthode
        executionCount.set(0);
        // Appelle une méthode
        dispatcher.execute(sender2, "multi");
        // Appelle une méthode
        assertEquals(0, executionCount.get(), "sender2 should be blocked by syntax condition");

        // sender3 should be blocked by command condition
        // Appelle une méthode
        dispatcher.execute(sender3, "multi");
        // Appelle une méthode
        assertEquals(0, executionCount.get(), "sender3 should be blocked by command condition");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void defaultExecutorWithConditionAndSyntaxes() {
        // Appelle une méthode
        var dispatcher = new CommandDispatcher();
        // Appelle une méthode
        var adminSender = new Sender();
        // Appelle une méthode
        var normalSender = new Sender();

        // Appelle une méthode
        AtomicBoolean defaultCalled = new AtomicBoolean(false);
        // Appelle une méthode
        AtomicBoolean syntaxCalled = new AtomicBoolean(false);

        // Appelle une méthode
        var command = new Command("cmd");
        // Appelle une méthode
        command.setCondition((sender, commandString) -> sender == adminSender);
        // Appelle une méthode
        command.setDefaultExecutor((sender, context) -> defaultCalled.set(true));
        // Appelle une méthode
        command.addSyntax((sender, context) -> syntaxCalled.set(true), Integer("value"));

        // Appelle une méthode
        dispatcher.register(command);

        // Admin executing without args should trigger default executor
        // Appelle une méthode
        dispatcher.execute(adminSender, "cmd");
        // Appelle une méthode
        assertTrue(defaultCalled.get(), "Admin should execute default executor");
        // Appelle une méthode
        assertFalse(syntaxCalled.get());

        // Normal user should be blocked even from default executor
        // Appelle une méthode
        defaultCalled.set(false);
        // Appelle une méthode
        dispatcher.execute(normalSender, "cmd");
        // Appelle une méthode
        assertFalse(defaultCalled.get(), "Normal user should be blocked from default executor");
        // Appelle une méthode
        assertFalse(syntaxCalled.get());

        // Admin with valid syntax
        // Appelle une méthode
        dispatcher.execute(adminSender, "cmd 42");
        // Appelle une méthode
        assertTrue(syntaxCalled.get(), "Admin should execute syntax");

        // Normal user should be blocked from syntax too
        // Appelle une méthode
        syntaxCalled.set(false);
        // Appelle une méthode
        dispatcher.execute(normalSender, "cmd 42");
        // Appelle une méthode
        assertFalse(syntaxCalled.get(), "Normal user should be blocked from syntax");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void deeplyNestedSubcommandConditions() {
        // Appelle une méthode
        var dispatcher = new CommandDispatcher();
        // Appelle une méthode
        var adminSender = new Sender();
        // Appelle une méthode
        var normalSender = new Sender();

        // Appelle une méthode
        AtomicBoolean called = new AtomicBoolean(false);

        // Appelle une méthode
        var rootCmd = new Command("root");
        // Appelle une méthode
        rootCmd.setCondition((sender, commandString) -> sender == adminSender);

        // Appelle une méthode
        var level1 = new Command("level1");
        // Appelle une méthode
        var level2 = new Command("level2");
        // Appelle une méthode
        var level3 = new Command("level3");
        // Appelle une méthode
        level3.setDefaultExecutor((sender, context) -> called.set(true));

        // Appelle une méthode
        level2.addSubcommand(level3);
        // Appelle une méthode
        level1.addSubcommand(level2);
        // Appelle une méthode
        rootCmd.addSubcommand(level1);

        // Appelle une méthode
        dispatcher.register(rootCmd);

        // Admin should be able to execute deeply nested command
        // Appelle une méthode
        dispatcher.execute(adminSender, "root level1 level2 level3");
        // Appelle une méthode
        assertTrue(called.get(), "Admin should execute deeply nested command");

        // Normal user should be blocked at root level
        // Appelle une méthode
        called.set(false);
        // Appelle une méthode
        dispatcher.execute(normalSender, "root level1 level2 level3");
        // Appelle une méthode
        assertFalse(called.get(), "Normal user should be blocked by root condition");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void syntaxConditionOnlyNoCommandCondition() {
        // Appelle une méthode
        var dispatcher = new CommandDispatcher();
        // Appelle une méthode
        var sender1 = new Sender();
        // Appelle une méthode
        var sender2 = new Sender();

        // Appelle une méthode
        AtomicBoolean called = new AtomicBoolean(false);

        // Appelle une méthode
        var command = new Command("test");
        // No command condition set
        // Instruction de code
        command.addConditionalSyntax((sender, commandString) -> sender == sender1,
                // Appelle une méthode
                (sender, context) -> called.set(true));

        // Appelle une méthode
        dispatcher.register(command);

        // sender1 should execute (passes syntax condition)
        // Appelle une méthode
        dispatcher.execute(sender1, "test");
        // Appelle une méthode
        assertTrue(called.get(), "sender1 should execute with syntax condition");

        // sender2 should be blocked (fails syntax condition)
        // Appelle une méthode
        called.set(false);
        // Appelle une méthode
        dispatcher.execute(sender2, "test");
        // Appelle une méthode
        assertFalse(called.get(), "sender2 should be blocked by syntax condition");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void mixedSyntaxConditions() {
        // Appelle une méthode
        var dispatcher = new CommandDispatcher();
        // Appelle une méthode
        var sender1 = new Sender();
        // Appelle une méthode
        var sender2 = new Sender();
        // Appelle une méthode
        var sender3 = new Sender();

        // Appelle une méthode
        AtomicInteger whichSyntax = new AtomicInteger(0);

        // Appelle une méthode
        var command = new Command("mixed");
        // Appelle une méthode
        command.setCondition((sender, commandString) -> sender == sender1 || sender == sender2);

        // Syntax 1: zero-arg, no additional condition (should use command condition only)
        // Appelle une méthode
        command.addSyntax((sender, context) -> whichSyntax.set(1));

        // Syntax 2: with arg and additional condition
        // Instruction de code
        command.addConditionalSyntax((sender, commandString) -> sender == sender1,
                // Appelle une méthode
                (sender, context) -> whichSyntax.set(2), Literal("special"));

        // Appelle une méthode
        dispatcher.register(command);

        // sender1 can execute both syntaxes
        // Appelle une méthode
        dispatcher.execute(sender1, "mixed");
        // Appelle une méthode
        assertEquals(1, whichSyntax.get(), "sender1 should execute syntax 1");

        // Appelle une méthode
        whichSyntax.set(0);
        // Appelle une méthode
        dispatcher.execute(sender1, "mixed special");
        // Appelle une méthode
        assertEquals(2, whichSyntax.get(), "sender1 should execute syntax 2");

        // sender2 can execute syntax 1 but not syntax 2
        // Appelle une méthode
        whichSyntax.set(0);
        // Appelle une méthode
        dispatcher.execute(sender2, "mixed");
        // Appelle une méthode
        assertEquals(1, whichSyntax.get(), "sender2 should execute syntax 1");

        // Appelle une méthode
        whichSyntax.set(0);
        // Appelle une méthode
        dispatcher.execute(sender2, "mixed special");
        // Appelle une méthode
        assertEquals(0, whichSyntax.get(), "sender2 should be blocked from syntax 2");

        // sender3 should be blocked from everything
        // Appelle une méthode
        dispatcher.execute(sender3, "mixed");
        // Appelle une méthode
        assertEquals(0, whichSyntax.get(), "sender3 should be blocked by command condition");

        // Appelle une méthode
        dispatcher.execute(sender3, "mixed special");
        // Appelle une méthode
        assertEquals(0, whichSyntax.get(), "sender3 should be blocked by command condition");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void subcommandWithOwnConditionRequiresBoth() {
        // Appelle une méthode
        var dispatcher = new CommandDispatcher();
        // Appelle une méthode
        var sender1 = new Sender();
        // Appelle une méthode
        var sender2 = new Sender();
        // Appelle une méthode
        var sender3 = new Sender();

        // Appelle une méthode
        AtomicBoolean called = new AtomicBoolean(false);

        // Appelle une méthode
        var parent = new Command("parent");
        // Appelle une méthode
        parent.setCondition((sender, commandString) -> sender == sender1 || sender == sender2);

        // Appelle une méthode
        var child = new Command("child");
        // Appelle une méthode
        child.setCondition((sender, commandString) -> sender == sender1 || sender == sender3);
        // Appelle une méthode
        child.setDefaultExecutor((sender, context) -> called.set(true));

        // Appelle une méthode
        parent.addSubcommand(child);
        // Appelle une méthode
        dispatcher.register(parent);

        // sender1 passes both conditions
        // Appelle une méthode
        dispatcher.execute(sender1, "parent child");
        // Appelle une méthode
        assertTrue(called.get(), "sender1 should pass both conditions");

        // sender2 passes parent but not child
        // Appelle une méthode
        called.set(false);
        // Appelle une méthode
        dispatcher.execute(sender2, "parent child");
        // Appelle une méthode
        assertFalse(called.get(), "sender2 should be blocked by child condition");

        // sender3 passes child but not parent
        // Appelle une méthode
        dispatcher.execute(sender3, "parent child");
        // Appelle une méthode
        assertFalse(called.get(), "sender3 should be blocked by parent condition");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void zeroArgSyntaxAndDefaultExecutorWithCondition() {
        // Appelle une méthode
        var dispatcher = new CommandDispatcher();
        // Appelle une méthode
        var adminSender = new Sender();
        // Appelle une méthode
        var normalSender = new Sender();

        // Appelle une méthode
        AtomicBoolean defaultCalled = new AtomicBoolean(false);
        // Appelle une méthode
        AtomicBoolean syntaxCalled = new AtomicBoolean(false);

        // Appelle une méthode
        var command = new Command("cmd");
        // Appelle une méthode
        command.setCondition((sender, commandString) -> sender == adminSender);
        // Appelle une méthode
        command.setDefaultExecutor((sender, context) -> defaultCalled.set(true));
        // Instruction de code
        command.addSyntax((sender, context) -> syntaxCalled.set(true)); // zero-arg syntax

        // Appelle une méthode
        dispatcher.register(command);

        // Admin should execute the syntax (syntax takes precedence over default executor)
        // Appelle une méthode
        dispatcher.execute(adminSender, "cmd");
        // Appelle une méthode
        assertTrue(syntaxCalled.get(), "Admin should execute zero-arg syntax");
        // Appelle une méthode
        assertFalse(defaultCalled.get(), "Default executor should not be called when syntax matches");

        // Normal user should be blocked
        // Appelle une méthode
        syntaxCalled.set(false);
        // Appelle une méthode
        dispatcher.execute(normalSender, "cmd");
        // Appelle une méthode
        assertFalse(syntaxCalled.get(), "Normal user should be blocked from syntax");
        // Appelle une méthode
        assertFalse(defaultCalled.get(), "Normal user should be blocked from default executor");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testGraphZeroArgCondition() {
        // Appelle une méthode
        var adminSender = new Sender();
        // Appelle une méthode
        var normalSender = new Sender();

        // Appelle une méthode
        var command = new Command("admin");
        // Appelle une méthode
        command.setCondition((sender, commandString) -> sender == adminSender);
        // Appelle une méthode
        command.addSyntax((sender, context) -> {});

        // Appelle une méthode
        Graph graph = Graph.fromCommand(command);
        // Appelle une méthode
        Graph.Node root = graph.root();

        // Appelle une méthode
        assertNotNull(root.execution(), "Root node should have execution");
        // Appelle une méthode
        assertNotNull(root.execution().condition(), "Root node should preserve command condition");
        // Appelle une méthode
        assertFalse(root.execution().test(normalSender), "Normal sender should fail condition check");
        // Appelle une méthode
        assertTrue(root.execution().test(adminSender), "Admin sender should pass condition check");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void graphPreservesConditionsForSyntaxWithArguments() {
        // Appelle une méthode
        var adminSender = new Sender();

        // Appelle une méthode
        var command = new Command("admin");
        // Appelle une méthode
        command.setCondition((sender, commandString) -> sender == adminSender);
        // Appelle une méthode
        command.addSyntax((sender, context) -> {}, Integer("value"));

        // Appelle une méthode
        Graph graph = Graph.fromCommand(command);
        // Appelle une méthode
        Graph.Node root = graph.root();

        // Appelle une méthode
        assertNotNull(root.execution(), "Root should have execution");
        // Appelle une méthode
        assertNotNull(root.execution().condition(), "Root should have command condition");

        // Check that the argument node also has condition info propagated
        // Appelle une méthode
        assertEquals(1, root.next().size(), "Should have one child for the Integer argument");
        // Appelle une méthode
        Graph.Node argNode = root.next().getFirst();
        // Appelle une méthode
        assertNotNull(argNode.execution(), "Argument node should have execution");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void graphComparisonDetectsConditionDifferences() {
        // Appelle une méthode
        var adminSender = new Sender();

        // Appelle une méthode
        var command1 = new Command("test");
        // Appelle une méthode
        command1.setCondition((sender, commandString) -> sender == adminSender);
        // Appelle une méthode
        command1.addSyntax((sender, context) -> {});

        // Appelle une méthode
        var command2 = new Command("test");
        // No condition
        // Appelle une méthode
        command2.addSyntax((sender, context) -> {});

        // Appelle une méthode
        Graph graph1 = Graph.fromCommand(command1);
        // Appelle une méthode
        Graph graph2 = Graph.fromCommand(command2);

        // Instruction de code
        assertFalse(graph1.compare(graph2, Graph.Comparator.TREE),
                // Instruction de code
                "Graphs with different conditions should not be equal");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static final class Sender implements CommandSender {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public TagHandler tagHandler() {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Identity identity() {
            // Renvoie une valeur à l'appelant
            return Identity.nil();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
