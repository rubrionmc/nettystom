// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Import statique d'un membre
import static net.minestom.server.command.builder.arguments.ArgumentType.*;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.fail;

// Déclaration de type (classe/interface/enum/record)
public class CommandSyntaxSingleTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleInteger() {
        // Appelle une méthode
        List<Argument<?>> args = List.of(Integer("number"));
        // Appelle une méthode
        assertSyntax(args, "5", ExpectedExecution.SYNTAX, Map.of("number", 5));
        // Appelle une méthode
        assertSyntax(args, "5 5", ExpectedExecution.SYNTAX);
        // Appelle une méthode
        assertSyntax(args, "", ExpectedExecution.DEFAULT);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleIntegerInteger() {
        // Appelle une méthode
        List<Argument<?>> args = List.of(Integer("number"), Integer("number2"));
        // Appelle une méthode
        assertSyntax(args, "5", ExpectedExecution.DEFAULT);
        // Appelle une méthode
        assertSyntax(args, "5 6", ExpectedExecution.SYNTAX, Map.of("number", 5, "number2", 6));
        // Appelle une méthode
        assertSyntax(args, "", ExpectedExecution.DEFAULT);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleString() {
        // Appelle une méthode
        List<Argument<?>> args = List.of(String("string"));
        // Instruction de code
        assertSyntax(args, """
                "value"
                """, ExpectedExecution.SYNTAX, Map.of("string", "value"));
        // Appelle une méthode
        assertSyntax(args, "5 5", ExpectedExecution.SYNTAX);
        // Appelle une méthode
        assertSyntax(args, "", ExpectedExecution.DEFAULT);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleStringString() {
        // Appelle une méthode
        List<Argument<?>> args = List.of(String("string"), String("string2"));
        // Appelle une méthode
        assertSyntax(args, "test", ExpectedExecution.DEFAULT);
        // Instruction de code
        assertSyntax(args, """
                "first" "second"
                """, ExpectedExecution.SYNTAX, Map.of("string", "first", "string2", "second"));
        // Instruction de code
        assertSyntax(args, """
                "unescaped" "esc\\"aped"
                """, ExpectedExecution.SYNTAX, Map.of("string", "unescaped", "string2", "esc\"aped"));
        // Appelle une méthode
        assertSyntax(args, "5 5", ExpectedExecution.SYNTAX);
        // Appelle une méthode
        assertSyntax(args, "", ExpectedExecution.DEFAULT);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleGroup() {
        // Appelle une méthode
        List<Argument<?>> args = List.of(Group("loop", Integer("first"), Integer("second")));
        // 1 2
        // Début d'un bloc
        {
            // Appelle une méthode
            var context = new CommandContext("1 2");
            // Appelle une méthode
            context.setArg("first", 1, "1");
            // Appelle une méthode
            context.setArg("second", 2, "2");
            // Appelle une méthode
            assertSyntax(args, "1 2", ExpectedExecution.SYNTAX, Map.of("loop", context));
        // Fin d'un bloc/d'une expression
        }
        // Incomplete group
        // Appelle une méthode
        assertSyntax(args, "1", ExpectedExecution.DEFAULT);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleLoop() {
        // Appelle une méthode
        List<Argument<?>> stringLoop = List.of(Loop("loop", String("value")));
        // Appelle une méthode
        assertSyntax(stringLoop, "one two three", ExpectedExecution.SYNTAX, Map.of("loop", List.of("one", "two", "three")));

        // Appelle une méthode
        List<Argument<?>> intLoop = List.of(Loop("loop", Integer("value")));
        // Appelle une méthode
        assertSyntax(intLoop, "1 2 3", ExpectedExecution.SYNTAX, Map.of("loop", List.of(1, 2, 3)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleLoopGroup() {
        // Appelle une méthode
        List<Argument<?>> groupLoop = List.of(Loop("loop", Group("group", Integer("first"), Integer("second"))));
        // 1 2
        // Début d'un bloc
        {
            // Appelle une méthode
            var context = new CommandContext("1 2");
            // Appelle une méthode
            context.setArg("first", 1, "1");
            // Appelle une méthode
            context.setArg("second", 2, "2");
            // Appelle une méthode
            assertSyntax(groupLoop, "1 2", ExpectedExecution.SYNTAX, Map.of("loop", List.of(context)));
        // Fin d'un bloc/d'une expression
        }
        // 1 2 3 4
        // Début d'un bloc
        {
            // Appelle une méthode
            var context1 = new CommandContext("1 2");
            // Appelle une méthode
            var context2 = new CommandContext("3 4");

            // Appelle une méthode
            context1.setArg("first", 1, "1");
            // Appelle une méthode
            context1.setArg("second", 2, "2");

            // Appelle une méthode
            context2.setArg("first", 3, "3");
            // Appelle une méthode
            context2.setArg("second", 4, "4");

            // Appelle une méthode
            assertSyntax(groupLoop, "1 2 3 4", ExpectedExecution.SYNTAX, Map.of("loop", List.of(context1, context2)));
        // Fin d'un bloc/d'une expression
        }
        // Incomplete loop
        // Appelle une méthode
        assertSyntax(groupLoop, "1", ExpectedExecution.DEFAULT);
        // Appelle une méthode
        assertSyntax(groupLoop, "1 2 3", ExpectedExecution.DEFAULT);
        // Appelle une méthode
        assertSyntax(groupLoop, "1 2 3 4 5", ExpectedExecution.DEFAULT);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void singleLoopDoubleGroup() {
        // Affecte une valeur
        List<Argument<?>> groupLoop = List.of(
                // Instruction de code
                Loop("loop",
                        // Instruction de code
                        Group("group", BlockState("block"), EntityType("entity_type")),
                        // Instruction de code
                        Group("group2", EntityType("entity_type"), BlockState("block"))
                // Fin d'un bloc/d'une expression
                )
        // Fin d'un bloc/d'une expression
        );
        // block enchant
        // Début d'un bloc
        {
            // Affecte une valeur
            var input = "minecraft:stone minecraft:allay";
            // Appelle une méthode
            var context = new CommandContext(input);
            // Appelle une méthode
            context.setArg("block", Block.STONE, "minecraft:stone");
            // Appelle une méthode
            context.setArg("entity_type", EntityType.ALLAY, "minecraft:allay");
            // Appelle une méthode
            assertSyntax(groupLoop, input, ExpectedExecution.SYNTAX, Map.of("loop", List.of(context)));
        // Fin d'un bloc/d'une expression
        }
        // enchant block block enchant
        // Début d'un bloc
        {
            // Appelle une méthode
            var context1 = new CommandContext("minecraft:allay minecraft:stone");
            // Appelle une méthode
            var context2 = new CommandContext("minecraft:grass_block minecraft:zombie");

            // Appelle une méthode
            context1.setArg("entity_type", EntityType.ALLAY, "minecraft:allay");
            // Appelle une méthode
            context1.setArg("block", Block.STONE, "minecraft:stone");

            // Appelle une méthode
            context2.setArg("block", Block.GRASS_BLOCK, "minecraft:grass_block");
            // Appelle une méthode
            context2.setArg("entity_type", EntityType.ZOMBIE, "minecraft:zombie");

            // Appelle une méthode
            var input = context1.getInput() + " " + context2.getInput();
            // Appelle une méthode
            assertSyntax(groupLoop, input, ExpectedExecution.SYNTAX, Map.of("loop", List.of(context1, context2)));
        // Fin d'un bloc/d'une expression
        }
        // Incomplete loop
        // Appelle une méthode
        assertSyntax(groupLoop, "minecraft:allay", ExpectedExecution.DEFAULT);
        // Appelle une méthode
        assertSyntax(groupLoop, "minecraft:allay minecraft:allay", ExpectedExecution.DEFAULT);
        // Appelle une méthode
        assertSyntax(groupLoop, "minecraft:stone", ExpectedExecution.DEFAULT);
        // Appelle une méthode
        assertSyntax(groupLoop, "minecraft:stone minecraft:stone", ExpectedExecution.DEFAULT);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void assertSyntax(List<Argument<?>> args, String input, ExpectedExecution expectedExecution, Map<String, Object> expectedValues) {
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

        // Début d'une méthode/d'un bloc
        command.addSyntax((sender, context) -> {
            // Embranchement : vérifie une condition
            if (!result.compareAndSet(null, ExpectedExecution.SYNTAX)) {
                // Appelle une méthode
                fail("Multiple execution: " + result.get());
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            values.set(context.getMap());
        // Appelle une méthode
        }, args.toArray(Argument[]::new));

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
    private static void assertSyntax(List<Argument<?>> args, String input, ExpectedExecution expectedExecution) {
        // Appelle une méthode
        assertSyntax(args, input, expectedExecution, null);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    enum ExpectedExecution {
        // Instruction de code
        DEFAULT,
        // Instruction de code
        SYNTAX
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
