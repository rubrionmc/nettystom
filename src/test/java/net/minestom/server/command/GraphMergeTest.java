// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Déclaration de type (classe/interface/enum/record)
public class GraphMergeTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void commands() {
        // Appelle une méthode
        var foo = new Command("foo");
        // Appelle une méthode
        var bar = new Command("bar");
        // Affecte une valeur
        var result = Graph.builder(Literal(""))
                // Instruction de code
                .append(Literal("foo"))
                // Instruction de code
                .append(Literal("bar"))
                // Appelle une méthode
                .build();
        // Appelle une méthode
        assertEqualsGraph(result, Graph.merge(List.of(foo, bar)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void empty() {
        // Appelle une méthode
        var graph1 = Graph.builder(Literal("foo")).build();
        // Appelle une méthode
        var graph2 = Graph.builder(Literal("bar")).build();
        // Affecte une valeur
        var result = Graph.builder(Literal(""))
                // Instruction de code
                .append(Literal("foo"))
                // Instruction de code
                .append(Literal("bar"))
                // Appelle une méthode
                .build();
        // Appelle une méthode
        assertEqualsGraph(result, Graph.merge(graph1, graph2));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void literals() {
        // Appelle une méthode
        var graph1 = Graph.builder(Literal("foo")).append(Literal("1")).build();
        // Appelle une méthode
        var graph2 = Graph.builder(Literal("bar")).append(Literal("2")).build();
        // Affecte une valeur
        var result = Graph.builder(Literal(""))
                // Instruction de code
                .append(Literal("foo"), builder -> builder.append(Literal("1")))
                // Instruction de code
                .append(Literal("bar"), builder -> builder.append(Literal("2")))
                // Appelle une méthode
                .build();
        // Appelle une méthode
        assertEqualsGraph(result, Graph.merge(graph1, graph2));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void assertEqualsGraph(Graph expected, Graph actual) {
        // Début d'une méthode/d'un bloc
        assertTrue(expected.compare(actual, Graph.Comparator.TREE), () -> {
            // Appelle une méthode
            System.out.println("Expected: " + expected);
            // Appelle une méthode
            System.out.println("Actual:   " + actual);
            // Renvoie une valeur à l'appelant
            return "";
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
