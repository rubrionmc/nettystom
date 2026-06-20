// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Literal;
// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.Word;
// Static import of a member
import static org.junit.jupiter.api.Assertions.*;

// Type declaration (class/interface/enum/record)
public class CommandParseTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void emptyCommand() {
        // Calls a method
        var graph = Graph.merge(Graph.builder(Literal("foo"), createExecutor(new AtomicBoolean())).build());
        // Calls a method
        assertUnknown(graph, "");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleParameterlessCommand() {
        // Calls a method
        final AtomicBoolean b = new AtomicBoolean();
        // Calls a method
        var foo = Graph.merge(Graph.builder(Literal("foo"), createExecutor(b)).build());

        // even though we add extra, it's still /foo so we'll call the default executor
        // Calls a method
        assertValid(foo, "foo bar baz", b);
        // Calls a method
        assertUnknown(foo, "bar");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void twoParameterlessCommand() {
        // Calls a method
        final AtomicBoolean b = new AtomicBoolean();
        // Calls a method
        final AtomicBoolean b1 = new AtomicBoolean();
        // Assigns a value
        var graph = Graph.merge(
                // Code statement
                Graph.builder(Literal("foo"), createExecutor(b)).build(),
                // Code statement
                Graph.builder(Literal("bar"), createExecutor(b1)).build()
        // End of a block/expression
        );
        // Calls a method
        assertValid(graph, "foo", b);
        // Calls a method
        assertValid(graph, "bar", b1);
        // Calls a method
        assertUnknown(graph, "baz");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandWithMultipleSyntax() {
        // Calls a method
        final AtomicBoolean add = new AtomicBoolean();
        // Calls a method
        final AtomicBoolean action = new AtomicBoolean();
        // Assigns a value
        var foo = Graph.merge(Graph.builder(Literal("foo"))
                // Code statement
                .append(Literal("add"),
                        // Code statement
                        x -> x.append(Word("name"), createExecutor(add)))
                // Code statement
                .append(Word("type").from("inc", "dec"),
                        // Code statement
                        x -> x.append(ArgumentType.Integer("num"), createExecutor(action)))
                // Calls a method
                .build());

        // Regular/Expected usage of the command
        // Calls a method
        assertValid(foo, "foo add test", add);
        // Calls a method
        assertValid(foo, "foo add inc", add);
        // Calls a method
        assertValid(foo, "foo add 157", add);
        // Calls a method
        assertValid(foo, "foo inc 157", action);
        // Calls a method
        assertValid(foo, "foo dec 157", action);

        // Since foo doesn't have a default executor, we want these to throw a syntax error
        // Calls a method
        assertSyntaxError(foo, "foo 15");
        // Calls a method
        assertSyntaxError(foo, "foo asd");

        // Foo and the inc argument both don't have a default executor (only a regular executor for inc), so these will fail
        // Calls a method
        assertSyntaxError(foo, "foo inc");
        // Calls a method
        assertSyntaxError(foo, "foo inc asd");

        // A valid command is provided, even if we have extra data we'll still accept it
        // Calls a method
        assertValid(foo, "foo inc 15 dec", action);
        // Calls a method
        assertValid(foo, "foo inc 15 20", action);

        // None of these are registered commands, make sure the correct command valuation is provided back
        // Calls a method
        assertUnknown(foo, "bar");
        // Calls a method
        assertUnknown(foo, "add");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandOptionalArgs() {
        // Calls a method
        final AtomicBoolean b = new AtomicBoolean();
        // Calls a method
        final AtomicReference<String> expectedFirstArg = new AtomicReference<>("T");
        // Assigns a value
        var foo = Graph.merge(Graph.builder(Literal("foo"))
                // Code statement
                .append(Word("a").setDefaultValue("A"),
                        // Code statement
                        x -> x.append(Word("b").setDefaultValue("B"),
                                // Code statement
                                x1 -> x1.append(Word("c").setDefaultValue("C"),
                                        // Code statement
                                        x2 -> x2.append(Word("d").setDefaultValue("D"),
                                                // Creates a new object
                                                new GraphImpl.ExecutionImpl(null, null, null,
                                                        // Start of a method/block
                                                        (sender, context) -> {
                                                            // Calls a method
                                                            b.set(true);
                                                            // Calls a method
                                                            assertEquals(expectedFirstArg.get(), context.get("a"));
                                                            // Calls a method
                                                            assertEquals("B", context.get("b"));
                                                            // Calls a method
                                                            assertEquals("C", context.get("c"));
                                                            // Calls a method
                                                            assertEquals("D", context.get("d"));
                                                        // Code statement
                                                        }, null)))))
                // Calls a method
                .build());
        // Calls a method
        assertValid(foo, "foo T", b);
        // Calls a method
        expectedFirstArg.set("A");
        // Calls a method
        assertValid(foo, "foo", b);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleCommandSingleEnumArg() {
        // Type declaration (class/interface/enum/record)
        enum A {a, b}
        // Calls a method
        final AtomicBoolean rootExecutor = new AtomicBoolean();
        // Calls a method
        final AtomicBoolean argExecutor = new AtomicBoolean();
        // Assigns a value
        var foo = Graph.merge(Graph.builder(Literal("foo"), createExecutor(rootExecutor))
                // Code statement
                .append(ArgumentType.Enum("test", A.class), createExecutor(argExecutor))
                // Calls a method
                .build());
        // Calls a method
        assertValid(foo, "foo a", argExecutor);
        // Calls a method
        assertValid(foo, "foo b", argExecutor);
        // Calls a method
        assertValid(foo, "foo c", rootExecutor);
        // Calls a method
        assertValid(foo, "foo", rootExecutor);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void aliasWithoutArgs() {
        // Calls a method
        final AtomicBoolean b = new AtomicBoolean();
        // Assigns a value
        var foo = Graph.merge(Graph.builder(Word("").from("foo", "bar"), createExecutor(b))
                // Calls a method
                .build());
        // Calls a method
        assertValid(foo, "foo", b);
        // Calls a method
        assertValid(foo, "bar", b);
        // Calls a method
        assertUnknown(foo, "test");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void aliasWithArgs() {
        // Calls a method
        final AtomicBoolean b = new AtomicBoolean();
        // Assigns a value
        var foo = Graph.merge(Graph.builder(Word("").from("foo", "bar"))
                // Code statement
                .append(ArgumentType.Integer("test"), createExecutor(b))
                // Calls a method
                .build());
        // Calls a method
        assertValid(foo, "foo 1", b);
        // Calls a method
        assertValid(foo, "bar 1", b);
        // Calls a method
        assertSyntaxError(foo, "foo");
        // Calls a method
        assertSyntaxError(foo, "bar");
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertSyntaxError(Graph graph, String input) {
        // Calls a method
        assertInstanceOf(CommandParser.Result.KnownCommand.Invalid.class, parseCommand(graph, input));
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertUnknown(Graph graph, String input) {
        // Calls a method
        assertInstanceOf(CommandParser.Result.UnknownCommand.class, parseCommand(graph, input));
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertValid(Graph graph, String input, AtomicBoolean executorTest) {
        // Calls a method
        final CommandParser.Result result = parseCommand(graph, input);
        // Calls a method
        assertInstanceOf(CommandParser.Result.KnownCommand.Valid.class, result);
        // Calls a method
        result.executable().execute(null);
        // Calls a method
        assertTrue(executorTest.get(), "Parser returned valid syntax, but with the wrong executor.");
        // Calls a method
        executorTest.set(false);
    // End of a block/expression
    }

    // Start of a method/block
    private static CommandParser.Result parseCommand(Graph graph, String input) {
        // Returns a value to the caller
        return CommandParser.parser().parse(new ServerSender(), graph, input);
    // End of a block/expression
    }

    // Start of a method/block
    private static Graph.Execution createExecutor(AtomicBoolean atomicBoolean) {
        // Returns a value to the caller
        return new GraphImpl.ExecutionImpl(null, null, null, (sender, context) -> atomicBoolean.set(true), null);
    // End of a block/expression
    }
// End of a block/expression
}
