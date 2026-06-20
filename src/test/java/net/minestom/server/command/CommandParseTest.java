// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;
// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Word;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class CommandParseTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void emptyCommand() {
        // Appelle une méthode
        var graph = Graph.merge(Graph.builder(Literal("foo"), createExecutor(new AtomicBoolean())).build());
        // Appelle une méthode
        assertUnknown(graph, "");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleParameterlessCommand() {
        // Appelle une méthode
        final AtomicBoolean b = new AtomicBoolean();
        // Appelle une méthode
        var foo = Graph.merge(Graph.builder(Literal("foo"), createExecutor(b)).build());

        // even though we add extra, it's still /foo so we'll call the default executor
        // Appelle une méthode
        assertValid(foo, "foo bar baz", b);
        // Appelle une méthode
        assertUnknown(foo, "bar");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void twoParameterlessCommand() {
        // Appelle une méthode
        final AtomicBoolean b = new AtomicBoolean();
        // Appelle une méthode
        final AtomicBoolean b1 = new AtomicBoolean();
        // Affecte une valeur
        var graph = Graph.merge(
                // Instruction de code
                Graph.builder(Literal("foo"), createExecutor(b)).build(),
                // Instruction de code
                Graph.builder(Literal("bar"), createExecutor(b1)).build()
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        assertValid(graph, "foo", b);
        // Appelle une méthode
        assertValid(graph, "bar", b1);
        // Appelle une méthode
        assertUnknown(graph, "baz");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandWithMultipleSyntax() {
        // Appelle une méthode
        final AtomicBoolean add = new AtomicBoolean();
        // Appelle une méthode
        final AtomicBoolean action = new AtomicBoolean();
        // Affecte une valeur
        var foo = Graph.merge(Graph.builder(Literal("foo"))
                // Instruction de code
                .append(Literal("add"),
                        // Instruction de code
                        x -> x.append(Word("name"), createExecutor(add)))
                // Instruction de code
                .append(Word("type").from("inc", "dec"),
                        // Instruction de code
                        x -> x.append(ArgumentType.Integer("num"), createExecutor(action)))
                // Appelle une méthode
                .build());

        // Regular/Expected usage of the command
        // Appelle une méthode
        assertValid(foo, "foo add test", add);
        // Appelle une méthode
        assertValid(foo, "foo add inc", add);
        // Appelle une méthode
        assertValid(foo, "foo add 157", add);
        // Appelle une méthode
        assertValid(foo, "foo inc 157", action);
        // Appelle une méthode
        assertValid(foo, "foo dec 157", action);

        // Since foo doesn't have a default executor, we want these to throw a syntax error
        // Appelle une méthode
        assertSyntaxError(foo, "foo 15");
        // Appelle une méthode
        assertSyntaxError(foo, "foo asd");

        // Foo and the inc argument both don't have a default executor (only a regular executor for inc), so these will fail
        // Appelle une méthode
        assertSyntaxError(foo, "foo inc");
        // Appelle une méthode
        assertSyntaxError(foo, "foo inc asd");

        // A valid command is provided, even if we have extra data we'll still accept it
        // Appelle une méthode
        assertValid(foo, "foo inc 15 dec", action);
        // Appelle une méthode
        assertValid(foo, "foo inc 15 20", action);

        // None of these are registered commands, make sure the correct command valuation is provided back
        // Appelle une méthode
        assertUnknown(foo, "bar");
        // Appelle une méthode
        assertUnknown(foo, "add");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandOptionalArgs() {
        // Appelle une méthode
        final AtomicBoolean b = new AtomicBoolean();
        // Appelle une méthode
        final AtomicReference<String> expectedFirstArg = new AtomicReference<>("T");
        // Affecte une valeur
        var foo = Graph.merge(Graph.builder(Literal("foo"))
                // Instruction de code
                .append(Word("a").setDefaultValue("A"),
                        // Instruction de code
                        x -> x.append(Word("b").setDefaultValue("B"),
                                // Instruction de code
                                x1 -> x1.append(Word("c").setDefaultValue("C"),
                                        // Instruction de code
                                        x2 -> x2.append(Word("d").setDefaultValue("D"),
                                                // Crée un nouvel objet
                                                new GraphImpl.ExecutionImpl(null, null, null,
                                                        // Début d'une méthode/d'un bloc
                                                        (sender, context) -> {
                                                            // Appelle une méthode
                                                            b.set(true);
                                                            // Appelle une méthode
                                                            assertEquals(expectedFirstArg.get(), context.get("a"));
                                                            // Appelle une méthode
                                                            assertEquals("B", context.get("b"));
                                                            // Appelle une méthode
                                                            assertEquals("C", context.get("c"));
                                                            // Appelle une méthode
                                                            assertEquals("D", context.get("d"));
                                                        // Instruction de code
                                                        }, null)))))
                // Appelle une méthode
                .build());
        // Appelle une méthode
        assertValid(foo, "foo T", b);
        // Appelle une méthode
        expectedFirstArg.set("A");
        // Appelle une méthode
        assertValid(foo, "foo", b);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleCommandSingleEnumArg() {
        // Déclaration de type (classe/interface/enum/record)
        enum A {a, b}
        // Appelle une méthode
        final AtomicBoolean rootExecutor = new AtomicBoolean();
        // Appelle une méthode
        final AtomicBoolean argExecutor = new AtomicBoolean();
        // Affecte une valeur
        var foo = Graph.merge(Graph.builder(Literal("foo"), createExecutor(rootExecutor))
                // Instruction de code
                .append(ArgumentType.Enum("test", A.class), createExecutor(argExecutor))
                // Appelle une méthode
                .build());
        // Appelle une méthode
        assertValid(foo, "foo a", argExecutor);
        // Appelle une méthode
        assertValid(foo, "foo b", argExecutor);
        // Appelle une méthode
        assertValid(foo, "foo c", rootExecutor);
        // Appelle une méthode
        assertValid(foo, "foo", rootExecutor);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void aliasWithoutArgs() {
        // Appelle une méthode
        final AtomicBoolean b = new AtomicBoolean();
        // Affecte une valeur
        var foo = Graph.merge(Graph.builder(Word("").from("foo", "bar"), createExecutor(b))
                // Appelle une méthode
                .build());
        // Appelle une méthode
        assertValid(foo, "foo", b);
        // Appelle une méthode
        assertValid(foo, "bar", b);
        // Appelle une méthode
        assertUnknown(foo, "test");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void aliasWithArgs() {
        // Appelle une méthode
        final AtomicBoolean b = new AtomicBoolean();
        // Affecte une valeur
        var foo = Graph.merge(Graph.builder(Word("").from("foo", "bar"))
                // Instruction de code
                .append(ArgumentType.Integer("test"), createExecutor(b))
                // Appelle une méthode
                .build());
        // Appelle une méthode
        assertValid(foo, "foo 1", b);
        // Appelle une méthode
        assertValid(foo, "bar 1", b);
        // Appelle une méthode
        assertSyntaxError(foo, "foo");
        // Appelle une méthode
        assertSyntaxError(foo, "bar");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void assertSyntaxError(Graph graph, String input) {
        // Appelle une méthode
        assertInstanceOf(CommandParser.Result.KnownCommand.Invalid.class, parseCommand(graph, input));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void assertUnknown(Graph graph, String input) {
        // Appelle une méthode
        assertInstanceOf(CommandParser.Result.UnknownCommand.class, parseCommand(graph, input));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void assertValid(Graph graph, String input, AtomicBoolean executorTest) {
        // Appelle une méthode
        final CommandParser.Result result = parseCommand(graph, input);
        // Appelle une méthode
        assertInstanceOf(CommandParser.Result.KnownCommand.Valid.class, result);
        // Appelle une méthode
        result.executable().execute(null);
        // Appelle une méthode
        assertTrue(executorTest.get(), "Parser returned valid syntax, but with the wrong executor.");
        // Appelle une méthode
        executorTest.set(false);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static CommandParser.Result parseCommand(Graph graph, String input) {
        // Renvoie une valeur à l'appelant
        return CommandParser.parser().parse(new ServerSender(), graph, input);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static Graph.Execution createExecutor(AtomicBoolean atomicBoolean) {
        // Renvoie une valeur à l'appelant
        return new GraphImpl.ExecutionImpl(null, null, null, (sender, context) -> atomicBoolean.set(true), null);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
