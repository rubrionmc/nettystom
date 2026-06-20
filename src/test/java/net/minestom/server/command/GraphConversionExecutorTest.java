// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.CommandCondition;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class GraphConversionExecutorTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void defaultCondition() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Constant true
        // Début d'un bloc
        {
            // Appelle une méthode
            foo.setCondition((sender, commandString) -> true);
            // Appelle une méthode
            var graph = Graph.fromCommand(foo);
            // Appelle une méthode
            var execution = graph.root().execution();
            // Appelle une méthode
            assertNotNull(execution);
            // Appelle une méthode
            assertTrue(execution.test(null));
        // Fin d'un bloc/d'une expression
        }
        // Constant false
        // Début d'un bloc
        {
            // Appelle une méthode
            foo.setCondition((sender, commandString) -> false);
            // Appelle une méthode
            var graph = Graph.fromCommand(foo);
            // Appelle une méthode
            var execution = graph.root().execution();
            // Appelle une méthode
            assertNotNull(execution);
            // Appelle une méthode
            assertFalse(execution.test(null));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void emptySyntaxCondition() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.addSyntax(GraphConversionExecutorTest::dummyExecutor, Literal("first"));

        // Appelle une méthode
        var graph = Graph.fromCommand(foo);
        // Appelle une méthode
        assertEquals(1, graph.root().next().size());
        // Appelle une méthode
        var execution = graph.root().next().getFirst().execution();
        // Appelle une méthode
        assertNotNull(execution);
        // Appelle une méthode
        assertNull(execution.condition());
        // Appelle une méthode
        assertNotNull(execution.executor());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void syntaxConditionTrue() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Instruction de code
        foo.addConditionalSyntax((sender, context) -> true,
                // Appelle une méthode
                GraphConversionExecutorTest::dummyExecutor, Literal("first"));

        // Appelle une méthode
        var graph = Graph.fromCommand(foo);
        // Appelle une méthode
        assertEquals(1, graph.root().next().size());
        // Appelle une méthode
        var execution = graph.root().next().getFirst().execution();
        // Appelle une méthode
        assertNotNull(execution);
        // Appelle une méthode
        assertTrue(execution.test(null));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void syntaxConditionFalse() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Instruction de code
        foo.addConditionalSyntax((sender, context) -> false,
                // Appelle une méthode
                GraphConversionExecutorTest::dummyExecutor, Literal("first"));

        // Appelle une méthode
        var graph = Graph.fromCommand(foo);
        // Appelle une méthode
        assertEquals(1, graph.root().next().size());
        // Appelle une méthode
        var execution = graph.root().next().getFirst().execution();
        // Appelle une méthode
        assertNotNull(execution);
        // Appelle une méthode
        assertFalse(execution.test(null));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void commandConditionFalse() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        foo.setCondition((sender, commandString) -> false);
        // Appelle une méthode
        final Graph graph = Graph.fromCommand(foo);
        // Appelle une méthode
        final Graph.Execution execution = graph.root().execution();
        // Appelle une méthode
        assertNotNull(execution);
        // Appelle une méthode
        final CommandCondition condition = execution.condition();
        // Appelle une méthode
        assertNotNull(condition);
        // Appelle une méthode
        assertFalse(condition.canUse(null, null));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void dummyExecutor(CommandSender sender, CommandContext context) {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
