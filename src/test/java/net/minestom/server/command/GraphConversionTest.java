// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.*;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Déclaration de type (classe/interface/enum/record)
public class GraphConversionTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void empty() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        var graph = Graph.builder(Literal("foo")).build();
        // Appelle une méthode
        assertEqualsGraph(graph, foo);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleLiteral() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        var first = Literal("first");
        // Appelle une méthode
        foo.addSyntax(GraphConversionTest::dummyExecutor, first);
        // Affecte une valeur
        var graph = Graph.builder(Literal("foo"))
                // Appelle une méthode
                .append(first, dummyExecution).build();
        // Appelle une méthode
        assertEqualsGraph(graph, foo);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void literalsPath() {
        // Appelle une méthode
        final Command foo = new Command("foo");
        // Appelle une méthode
        var first = Literal("first");
        // Appelle une méthode
        var second = Literal("second");

        // Appelle une méthode
        foo.addSyntax(GraphConversionTest::dummyExecutor, first);
        // Appelle une méthode
        foo.addSyntax(GraphConversionTest::dummyExecutor, second);

        // Affecte une valeur
        var graph = Graph.builder(Literal("foo"))
                // Instruction de code
                .append(first, dummyExecution)
                // Instruction de code
                .append(second, dummyExecution)
                // Appelle une méthode
                .build();
        // Appelle une méthode
        assertEqualsGraph(graph, foo);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void doubleSyntax() {
        // Déclaration de type (classe/interface/enum/record)
        enum A {A, B, C, D, E}
        // Appelle une méthode
        final Command foo = new Command("foo");

        // Appelle une méthode
        var bar = Literal("bar");

        // Appelle une méthode
        var baz = Literal("baz");
        // Appelle une méthode
        var a = Enum("a", A.class);

        // Appelle une méthode
        foo.addSyntax(GraphConversionTest::dummyExecutor, bar);
        // Appelle une méthode
        foo.addSyntax(GraphConversionTest::dummyExecutor, baz, a);

        // Affecte une valeur
        var graph = Graph.builder(Literal("foo"))
                // Instruction de code
                .append(bar, dummyExecution)
                // Instruction de code
                .append(baz, builder ->
                        // Instruction de code
                        builder.append(a, dummyExecution))
                // Appelle une méthode
                .build();
        // Appelle une méthode
        assertEqualsGraph(graph, foo);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void doubleSyntaxMerge() {
        // Appelle une méthode
        final Command foo = new Command("foo");

        // Appelle une méthode
        var bar = Literal("bar");
        // Appelle une méthode
        var number = Integer("number");

        // Appelle une méthode
        foo.addSyntax(GraphConversionTest::dummyExecutor, bar);
        // Appelle une méthode
        foo.addSyntax(GraphConversionTest::dummyExecutor, bar, number);

        // The two syntax shall start from the same node
        // Affecte une valeur
        var graph = Graph.builder(Literal("foo"))
                // Instruction de code
                .append(bar, dummyExecution, builder -> builder.append(number, dummyExecution))
                // Appelle une méthode
                .build();
        // Appelle une méthode
        assertEqualsGraph(graph, foo);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void subcommand() {
        // Appelle une méthode
        final Command main = new Command("main");
        // Appelle une méthode
        final Command sub = new Command("sub");

        // Appelle une méthode
        var baz = Literal("baz");

        // Instruction de code
        main.addSyntax(GraphConversionTest::dummyExecutor, baz); // Check that subcommands are added to graph first

        // Appelle une méthode
        var bar = Literal("bar");
        // Appelle une méthode
        var number = Integer("number");

        // Appelle une méthode
        sub.addSyntax(GraphConversionTest::dummyExecutor, bar);
        // Appelle une méthode
        sub.addSyntax(GraphConversionTest::dummyExecutor, bar, number);

        // Appelle une méthode
        main.addSubcommand(sub);

        // The two syntax shall start from the same node
        // Affecte une valeur
        var graph = Graph.builder(Literal("main"))
                // Instruction de code
                .append(Literal("sub"), builder ->
                        // Instruction de code
                        builder.append(bar, dummyExecution, builder1 -> builder1.append(number, dummyExecution)))
                // Instruction de code
                .append(Literal("baz"), dummyExecution)
                // Appelle une méthode
                .build();
        // Appelle une méthode
        assertEqualsGraph(graph, main);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void alias() {
        // Appelle une méthode
        final Command main = new Command("main", "alias");
        // Appelle une méthode
        var graph = Graph.builder(Word("main").from("main", "alias")).build();
        // Appelle une méthode
        assertEqualsGraph(graph, main);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void aliases() {
        // Appelle une méthode
        final Command main = new Command("main", "first", "second");
        // Appelle une méthode
        var graph = Graph.builder(Word("main").from("main", "first", "second")).build();
        // Appelle une méthode
        assertEqualsGraph(graph, main);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void assertEqualsGraph(Graph expected, Command command) {
        // Appelle une méthode
        final Graph actual = Graph.fromCommand(command);
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

    // Début d'une méthode/d'un bloc
    private static void dummyExecutor(CommandSender sender, CommandContext context) {
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    private static final Graph.Execution dummyExecution = new GraphImpl.ExecutionImpl(null, null, null, GraphConversionTest::dummyExecutor, null);
// Fin d'un bloc/d'une expression
}
