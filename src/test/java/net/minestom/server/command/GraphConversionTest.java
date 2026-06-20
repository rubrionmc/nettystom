// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.*;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Type declaration (class/interface/enum/record)
public class GraphConversionTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void empty() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        var graph = Graph.builder(Literal("foo")).build();
        // Calls a method
        assertEqualsGraph(graph, foo);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleLiteral() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        var first = Literal("first");
        // Calls a method
        foo.addSyntax(GraphConversionTest::dummyExecutor, first);
        // Assigns a value
        var graph = Graph.builder(Literal("foo"))
                // Calls a method
                .append(first, dummyExecution).build();
        // Calls a method
        assertEqualsGraph(graph, foo);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void literalsPath() {
        // Calls a method
        final Command foo = new Command("foo");
        // Calls a method
        var first = Literal("first");
        // Calls a method
        var second = Literal("second");

        // Calls a method
        foo.addSyntax(GraphConversionTest::dummyExecutor, first);
        // Calls a method
        foo.addSyntax(GraphConversionTest::dummyExecutor, second);

        // Assigns a value
        var graph = Graph.builder(Literal("foo"))
                // Code statement
                .append(first, dummyExecution)
                // Code statement
                .append(second, dummyExecution)
                // Calls a method
                .build();
        // Calls a method
        assertEqualsGraph(graph, foo);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void doubleSyntax() {
        // Type declaration (class/interface/enum/record)
        enum A {A, B, C, D, E}
        // Calls a method
        final Command foo = new Command("foo");

        // Calls a method
        var bar = Literal("bar");

        // Calls a method
        var baz = Literal("baz");
        // Calls a method
        var a = Enum("a", A.class);

        // Calls a method
        foo.addSyntax(GraphConversionTest::dummyExecutor, bar);
        // Calls a method
        foo.addSyntax(GraphConversionTest::dummyExecutor, baz, a);

        // Assigns a value
        var graph = Graph.builder(Literal("foo"))
                // Code statement
                .append(bar, dummyExecution)
                // Code statement
                .append(baz, builder ->
                        // Code statement
                        builder.append(a, dummyExecution))
                // Calls a method
                .build();
        // Calls a method
        assertEqualsGraph(graph, foo);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void doubleSyntaxMerge() {
        // Calls a method
        final Command foo = new Command("foo");

        // Calls a method
        var bar = Literal("bar");
        // Calls a method
        var number = Integer("number");

        // Calls a method
        foo.addSyntax(GraphConversionTest::dummyExecutor, bar);
        // Calls a method
        foo.addSyntax(GraphConversionTest::dummyExecutor, bar, number);

        // The two syntax shall start from the same node
        // Assigns a value
        var graph = Graph.builder(Literal("foo"))
                // Code statement
                .append(bar, dummyExecution, builder -> builder.append(number, dummyExecution))
                // Calls a method
                .build();
        // Calls a method
        assertEqualsGraph(graph, foo);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void subcommand() {
        // Calls a method
        final Command main = new Command("main");
        // Calls a method
        final Command sub = new Command("sub");

        // Calls a method
        var baz = Literal("baz");

        // Code statement
        main.addSyntax(GraphConversionTest::dummyExecutor, baz); // Check that subcommands are added to graph first

        // Calls a method
        var bar = Literal("bar");
        // Calls a method
        var number = Integer("number");

        // Calls a method
        sub.addSyntax(GraphConversionTest::dummyExecutor, bar);
        // Calls a method
        sub.addSyntax(GraphConversionTest::dummyExecutor, bar, number);

        // Calls a method
        main.addSubcommand(sub);

        // The two syntax shall start from the same node
        // Assigns a value
        var graph = Graph.builder(Literal("main"))
                // Code statement
                .append(Literal("sub"), builder ->
                        // Code statement
                        builder.append(bar, dummyExecution, builder1 -> builder1.append(number, dummyExecution)))
                // Code statement
                .append(Literal("baz"), dummyExecution)
                // Calls a method
                .build();
        // Calls a method
        assertEqualsGraph(graph, main);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void alias() {
        // Calls a method
        final Command main = new Command("main", "alias");
        // Calls a method
        var graph = Graph.builder(Word("main").from("main", "alias")).build();
        // Calls a method
        assertEqualsGraph(graph, main);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void aliases() {
        // Calls a method
        final Command main = new Command("main", "first", "second");
        // Calls a method
        var graph = Graph.builder(Word("main").from("main", "first", "second")).build();
        // Calls a method
        assertEqualsGraph(graph, main);
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertEqualsGraph(Graph expected, Command command) {
        // Calls a method
        final Graph actual = Graph.fromCommand(command);
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

    // Start of a method/block
    private static void dummyExecutor(CommandSender sender, CommandContext context) {
    // End of a block/expression
    }

    // Calls a method
    private static final Graph.Execution dummyExecution = new GraphImpl.ExecutionImpl(null, null, null, GraphConversionTest::dummyExecutor, null);
// End of a block/expression
}
