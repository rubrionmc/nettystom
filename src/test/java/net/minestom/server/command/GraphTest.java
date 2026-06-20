// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class GraphTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void empty() {
        // Affecte une valeur
        var result = Graph.builder(Literal(""))
                // Appelle une méthode
                .build();
        // Appelle une méthode
        var node = result.root();
        // Appelle une méthode
        assertEquals(Literal(""), node.argument());
        // Appelle une méthode
        assertTrue(node.next().isEmpty());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void next() {
        // Affecte une valeur
        var result = Graph.builder(Literal(""))
                // Instruction de code
                .append(Literal("foo"))
                // Appelle une méthode
                .build();
        // Appelle une méthode
        var node = result.root();
        // Appelle une méthode
        assertEquals(Literal(""), node.argument());
        // Appelle une méthode
        assertEquals(1, node.next().size());
        // Appelle une méthode
        assertEquals(Literal("foo"), node.next().getFirst().argument());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void immutableNextBuilder() {
        // Affecte une valeur
        var result = Graph.builder(Literal(""))
                // Instruction de code
                .append(Literal("foo"))
                // Instruction de code
                .append(Literal("bar"))
                // Appelle une méthode
                .build();
        // Appelle une méthode
        var node = result.root();
        // Appelle une méthode
        assertThrows(Exception.class, () -> result.root().next().add(node));
        // Appelle une méthode
        assertThrows(Exception.class, () -> result.root().next().getFirst().next().add(node));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void immutableNextCommand() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        var first = Literal("first");
        // Appelle une méthode
        foo.addSyntax(GraphTest::dummyExecutor, first);
        // Appelle une méthode
        var result = Graph.fromCommand(foo);

        // Appelle une méthode
        var node = result.root();
        // Appelle une méthode
        assertThrows(Exception.class, () -> result.root().next().add(node));
        // Appelle une méthode
        assertThrows(Exception.class, () -> result.root().next().getFirst().next().add(node));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void immutableNextCommands() {
        // Instruction de code
        final Command foo, bar;

        // Début d'un bloc
        {
            // Appelle une méthode
            var first = Literal("first");

            // Appelle une méthode
            foo = new Command("foo");
            // Appelle une méthode
            foo.addSyntax(GraphTest::dummyExecutor, first);

            // Appelle une méthode
            bar = new Command("foo");
            // Appelle une méthode
            bar.addSyntax(GraphTest::dummyExecutor, first);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var result = Graph.merge(List.of(foo, bar));

        // Appelle une méthode
        var node = result.root();
        // Appelle une méthode
        assertThrows(Exception.class, () -> result.root().next().add(node));
        // Appelle une méthode
        assertThrows(Exception.class, () -> result.root().next().getFirst().next().add(node));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void dummyExecutor(CommandSender sender, CommandContext context) {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
