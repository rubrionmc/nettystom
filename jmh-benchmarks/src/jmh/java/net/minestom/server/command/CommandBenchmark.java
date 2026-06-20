// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import org.openjdk.jmh.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jmh.infra.Blackhole;

// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.TimeUnit;
// Import d'une classe nécessaire
import java.util.function.Function;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.*;

// Annotation pour l'élément suivant
@BenchmarkMode(Mode.AverageTime)
// Annotation pour l'élément suivant
@State(Scope.Benchmark)
// Annotation pour l'élément suivant
@OutputTimeUnit(TimeUnit.NANOSECONDS)
// Annotation pour l'élément suivant
@Fork(1)
// Annotation pour l'élément suivant
@Warmup(time = 2, iterations = 3)
// Annotation pour l'élément suivant
@Measurement(time = 6)
// Déclaration de type (classe/interface/enum/record)
public class CommandBenchmark {
    // Instruction de code
    Function<String, Object> parser;

    // Annotation pour l'élément suivant
    @Setup
    // Début d'une méthode/d'un bloc
    public void setup() {
        // Affecte une valeur
        var graph = Graph.merge(Set.of(
                // Crée un nouvel objet
                new Command("tp", "teleport") {{
                    // Appelle une méthode
                    addSyntax((sender, context) -> {}, RelativeVec3("pos"));
                    // Appelle une méthode
                    addSyntax((sender, context) -> {}, Entity("entity"), RelativeVec3("pos"));
                // Instruction de code
                }},
                // Crée un nouvel objet
                new Command("setblock", "set") {{
                    // Appelle une méthode
                    addSyntax((sender, context) -> {}, RelativeBlockPosition("pos"), BlockState("block"));
                // Instruction de code
                }},
                // Crée un nouvel objet
                new Command("foo") {{
                    // Appelle une méthode
                    setCondition((sender, commandString) -> true);
                    // Début d'une méthode/d'un bloc
                    addSubcommand(new Command("bar") {{
                        // Appelle une méthode
                        addConditionalSyntax((sender, commandString) -> true, (sender, context) -> {});
                    // Instruction de code
                    }});
                    // Début d'une méthode/d'un bloc
                    addSubcommand(new Command("baz"){{
                        // Appelle une méthode
                        addSyntax((sender, context) -> {}, Word("A").from("a", "b", "c"), Word("B").from("a", "b", "c"));
                    // Instruction de code
                    }});
                // Instruction de code
                }},
                // Crée un nouvel objet
                new Command("def") {{
                    // Instruction de code
                    addSyntax((sender, context) -> {}, Literal("a"), Literal("b"), Literal("c"), Literal("d"),
                            // Appelle une méthode
                            Literal("e"), Literal("f"));
                    // Appelle une méthode
                    setDefaultExecutor((sender, context) -> {});
                // Instruction de code
                }},
                // Crée un nouvel objet
                new Command("parse") {{
                    // Appelle une méthode
                    addSyntax((sender, context) -> {}, Literal("int"), Integer("val"));
                    // Appelle une méthode
                    addSyntax((sender, context) -> {}, Literal("double"), Double("val"));
                    // Appelle une méthode
                    addSyntax((sender, context) -> {}, Literal("float"), Float("val"));
                    // Appelle une méthode
                    addSyntax((sender, context) -> {}, Literal("long"), Long("val"));
                // Instruction de code
                }}
        // Instruction de code
        ));
        // Appelle une méthode
        final CommandParser commandParser = CommandParser.parser();
        // Accès à l'objet courant/parent
        this.parser = input -> commandParser.parse(null, graph, input);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void unknownCommand5Char(Blackhole bh) {
        // Appelle une méthode
        bh.consume(parser.apply("01234"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void unknownCommand50Char(Blackhole bh) {
        // Appelle une méthode
        bh.consume(parser.apply("01234567890123456789012345678901234567890123456789"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void validCommandWithValidLiteral(Blackhole bh) {
        // Appelle une méthode
        bh.consume(parser.apply("foo bar"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void validCommandWithInvalid50CharLiteral(Blackhole bh) {
        // Appelle une méthode
        bh.consume(parser.apply("foo 01234567890123456789012345678901234567890123456789"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void numberParsing3Digit(Blackhole bh) {
        // Appelle une méthode
        bh.consume(parser.apply("parse int 123"));
        // Appelle une méthode
        bh.consume(parser.apply("parse float 123"));
        // Appelle une méthode
        bh.consume(parser.apply("parse double 123"));
        // Appelle une méthode
        bh.consume(parser.apply("parse long 123"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void numberParsing10Digit(Blackhole bh) {
        // Appelle une méthode
        bh.consume(parser.apply("parse int 1234567890"));
        // Appelle une méthode
        bh.consume(parser.apply("parse float 1234567890"));
        // Appelle une méthode
        bh.consume(parser.apply("parse double 1234567890"));
        // Appelle une méthode
        bh.consume(parser.apply("parse long 1234567890"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Benchmark
    // Début d'une méthode/d'un bloc
    public void numberParsing10DigitInvalid(Blackhole bh) {
        // Appelle une méthode
        bh.consume(parser.apply("parse int a1234567890"));
        // Appelle une méthode
        bh.consume(parser.apply("parse float a1234567890"));
        // Appelle une méthode
        bh.consume(parser.apply("parse double a1234567890"));
        // Appelle une méthode
        bh.consume(parser.apply("parse long a1234567890"));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
