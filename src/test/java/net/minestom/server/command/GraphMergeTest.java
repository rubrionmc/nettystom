// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Type declaration (class/interface/enum/record)
public class GraphMergeTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void commands() {
        // Calls a method
        var foo = new Command("foo");
        // Calls a method
        var bar = new Command("bar");
        // Assigns a value
        var result = Graph.builder(Literal(""))
                // Code statement
                .append(Literal("foo"))
                // Code statement
                .append(Literal("bar"))
                // Calls a method
                .build();
        // Calls a method
        assertEqualsGraph(result, Graph.merge(List.of(foo, bar)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void empty() {
        // Calls a method
        var graph1 = Graph.builder(Literal("foo")).build();
        // Calls a method
        var graph2 = Graph.builder(Literal("bar")).build();
        // Assigns a value
        var result = Graph.builder(Literal(""))
                // Code statement
                .append(Literal("foo"))
                // Code statement
                .append(Literal("bar"))
                // Calls a method
                .build();
        // Calls a method
        assertEqualsGraph(result, Graph.merge(graph1, graph2));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void literals() {
        // Calls a method
        var graph1 = Graph.builder(Literal("foo")).append(Literal("1")).build();
        // Calls a method
        var graph2 = Graph.builder(Literal("bar")).append(Literal("2")).build();
        // Assigns a value
        var result = Graph.builder(Literal(""))
                // Code statement
                .append(Literal("foo"), builder -> builder.append(Literal("1")))
                // Code statement
                .append(Literal("bar"), builder -> builder.append(Literal("2")))
                // Calls a method
                .build();
        // Calls a method
        assertEqualsGraph(result, Graph.merge(graph1, graph2));
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertEqualsGraph(Graph expected, Graph actual) {
        // Start of a method/block
        assertTrue(expected.compare(actual, Graph.Comparator.TREE), () -> {
            // Calls a method
            System.out.println("Expected: " + expected);
            // Calls a method
            System.out.println("Actual:   " + actual);
            // Returns a value to the caller
            return "";
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
