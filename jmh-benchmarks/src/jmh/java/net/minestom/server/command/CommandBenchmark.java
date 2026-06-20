// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import org.openjdk.jmh.annotations.*;
// Import of a required class
import org.openjdk.jmh.infra.Blackhole;

// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.TimeUnit;
// Import of a required class
import java.util.function.Function;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.*;

// Annotation for the following element
@BenchmarkMode(Mode.AverageTime)
// Annotation for the following element
@State(Scope.Benchmark)
// Annotation for the following element
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation for the following element
@Fork(1)
// Annotation for the following element
@Warmup(time = 2, iterations = 3)
// Annotation for the following element
@Measurement(time = 6)
// Type declaration (class/interface/enum/record)
public class CommandBenchmark {
    // Code statement
    Function<String, Object> parser;

    // Annotation for the following element
    @Setup
    // Start of a method/block
    public void setup() {
        // Assigns a value
        var graph = Graph.merge(Set.of(
                // Creates a new object
                new Command("tp", "teleport") {{
                    // Calls a method
                    addSyntax((sender, context) -> {}, RelativeVec3("pos"));
                    // Calls a method
                    addSyntax((sender, context) -> {}, Entity("entity"), RelativeVec3("pos"));
                // Code statement
                }},
                // Creates a new object
                new Command("setblock", "set") {{
                    // Calls a method
                    addSyntax((sender, context) -> {}, RelativeBlockPosition("pos"), BlockState("block"));
                // Code statement
                }},
                // Creates a new object
                new Command("foo") {{
                    // Calls a method
                    setCondition((sender, commandString) -> true);
                    // Start of a method/block
                    addSubcommand(new Command("bar") {{
                        // Calls a method
                        addConditionalSyntax((sender, commandString) -> true, (sender, context) -> {});
                    // Code statement
                    }});
                    // Start of a method/block
                    addSubcommand(new Command("baz"){{
                        // Calls a method
                        addSyntax((sender, context) -> {}, Word("A").from("a", "b", "c"), Word("B").from("a", "b", "c"));
                    // Code statement
                    }});
                // Code statement
                }},
                // Creates a new object
                new Command("def") {{
                    // Code statement
                    addSyntax((sender, context) -> {}, Literal("a"), Literal("b"), Literal("c"), Literal("d"),
                            // Calls a method
                            Literal("e"), Literal("f"));
                    // Calls a method
                    setDefaultExecutor((sender, context) -> {});
                // Code statement
                }},
                // Creates a new object
                new Command("parse") {{
                    // Calls a method
                    addSyntax((sender, context) -> {}, Literal("int"), Integer("val"));
                    // Calls a method
                    addSyntax((sender, context) -> {}, Literal("double"), Double("val"));
                    // Calls a method
                    addSyntax((sender, context) -> {}, Literal("float"), Float("val"));
                    // Calls a method
                    addSyntax((sender, context) -> {}, Literal("long"), Long("val"));
                // Code statement
                }}
        // Code statement
        ));
        // Calls a method
        final CommandParser commandParser = CommandParser.parser();
        // Access to the current/parent object
        this.parser = input -> commandParser.parse(null, graph, input);
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void unknownCommand5Char(Blackhole bh) {
        // Calls a method
        bh.consume(parser.apply("01234"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void unknownCommand50Char(Blackhole bh) {
        // Calls a method
        bh.consume(parser.apply("01234567890123456789012345678901234567890123456789"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void validCommandWithValidLiteral(Blackhole bh) {
        // Calls a method
        bh.consume(parser.apply("foo bar"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void validCommandWithInvalid50CharLiteral(Blackhole bh) {
        // Calls a method
        bh.consume(parser.apply("foo 01234567890123456789012345678901234567890123456789"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void numberParsing3Digit(Blackhole bh) {
        // Calls a method
        bh.consume(parser.apply("parse int 123"));
        // Calls a method
        bh.consume(parser.apply("parse float 123"));
        // Calls a method
        bh.consume(parser.apply("parse double 123"));
        // Calls a method
        bh.consume(parser.apply("parse long 123"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void numberParsing10Digit(Blackhole bh) {
        // Calls a method
        bh.consume(parser.apply("parse int 1234567890"));
        // Calls a method
        bh.consume(parser.apply("parse float 1234567890"));
        // Calls a method
        bh.consume(parser.apply("parse double 1234567890"));
        // Calls a method
        bh.consume(parser.apply("parse long 1234567890"));
    // End of a block/expression
    }

    // Annotation for the following element
    @Benchmark
    // Start of a method/block
    public void numberParsing10DigitInvalid(Blackhole bh) {
        // Calls a method
        bh.consume(parser.apply("parse int a1234567890"));
        // Calls a method
        bh.consume(parser.apply("parse float a1234567890"));
        // Calls a method
        bh.consume(parser.apply("parse double a1234567890"));
        // Calls a method
        bh.consume(parser.apply("parse long a1234567890"));
    // End of a block/expression
    }
// End of a block/expression
}
