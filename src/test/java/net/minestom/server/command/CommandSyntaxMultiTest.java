// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.lang.String;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Float;
// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.Integer;
// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.*;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.fail;

// Déclaration de type (classe/interface/enum/record)
public class CommandSyntaxMultiTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void integerFloat() {
        // Affecte une valeur
        List<List<Argument<?>>> args = List.of(
                // Instruction de code
                List.of(Literal("integer"), Integer("number")),
                // Instruction de code
                List.of(Literal("float"), Float("number"))
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        assertSyntax(args, "integer 5", ExpectedExecution.FIRST_SYNTAX, Map.of("integer", "integer", "number", 5));
        // Appelle une méthode
        assertSyntax(args, "float 5.5", ExpectedExecution.SECOND_SYNTAX, Map.of("float", "float", "number", 5.5f));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void argPriority() {
        // Affecte une valeur
        List<List<Argument<?>>> args = List.of(
                // Instruction de code
                List.of(Word("word")),
                // Instruction de code
                List.of(Literal("literal"))
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        assertSyntax(args, "literal", ExpectedExecution.SECOND_SYNTAX);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void similarArgs() {
        // Affecte une valeur
        List<List<Argument<?>>> args = List.of(
                // Instruction de code
                List.of(Word("a")),
                // Instruction de code
                List.of(Word("b"), Word("a"))
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        assertSyntax(args, "baz", ExpectedExecution.FIRST_SYNTAX);
        // Appelle une méthode
        assertSyntax(args, "bar baz", ExpectedExecution.SECOND_SYNTAX);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void assertSyntax(List<List<Argument<?>>> args, String input, ExpectedExecution expectedExecution, Map<String, Object> expectedValues) {
        // Affecte une valeur
        final String commandName = "name";

        // Appelle une méthode
        var manager = new CommandManager();
        // Appelle une méthode
        var command = new Command(commandName);
        // Appelle une méthode
        manager.register(command);

        // Affecte une valeur
        AtomicReference<ExpectedExecution> result = new AtomicReference<>();
        // Affecte une valeur
        AtomicReference<Map<String, Object>> values = new AtomicReference<>();

        // Début d'une méthode/d'un bloc
        command.setDefaultExecutor((sender, context) -> {
            // Embranchement : vérifie une condition
            if (!result.compareAndSet(null, ExpectedExecution.DEFAULT)) {
                // Appelle une méthode
                fail("Multiple execution: " + result.get());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });

        // Appelle une méthode
        int i = ExpectedExecution.FIRST_SYNTAX.ordinal();
        // Boucle : répète un bloc
        for (List<Argument<?>> t : args) {
            // Appelle une méthode
            ExpectedExecution id = ExpectedExecution.values()[i++];
            // Début d'une méthode/d'un bloc
            command.addSyntax((sender, context) -> {
                // Embranchement : vérifie une condition
                if (!result.compareAndSet(null, id)) {
                    // Appelle une méthode
                    fail("Multiple execution: " + result.get());
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                values.set(context.getMap());
            // Appelle une méthode
            }, t.toArray(Argument[]::new));
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        final String executeString = commandName + " " + input;
        // Appelle une méthode
        manager.executeServerCommand(executeString);
        // Appelle une méthode
        assertEquals(expectedExecution, result.get());
        // Embranchement : vérifie une condition
        if (expectedValues != null) {
            // Appelle une méthode
            assertEquals(expectedValues, values.get());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void assertSyntax(List<List<Argument<?>>> args, String input, ExpectedExecution expectedExecution) {
        // Appelle une méthode
        assertSyntax(args, input, expectedExecution, null);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    enum ExpectedExecution {
        // Instruction de code
        DEFAULT,

        // Instruction de code
        FIRST_SYNTAX,
        // Instruction de code
        SECOND_SYNTAX,
        // Instruction de code
        THIRD_SYNTAX
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
