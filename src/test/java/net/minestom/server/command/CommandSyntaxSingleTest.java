// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandContext;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Static import of a member
import static net.minestom.server.command.builder.arguments.ArgumentType.*;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.fail;

// Type declaration (class/interface/enum/record)
public class CommandSyntaxSingleTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleInteger() {
        // Calls a method
        List<Argument<?>> args = List.of(Integer("number"));
        // Calls a method
        assertSyntax(args, "5", ExpectedExecution.SYNTAX, Map.of("number", 5));
        // Calls a method
        assertSyntax(args, "5 5", ExpectedExecution.SYNTAX);
        // Calls a method
        assertSyntax(args, "", ExpectedExecution.DEFAULT);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleIntegerInteger() {
        // Calls a method
        List<Argument<?>> args = List.of(Integer("number"), Integer("number2"));
        // Calls a method
        assertSyntax(args, "5", ExpectedExecution.DEFAULT);
        // Calls a method
        assertSyntax(args, "5 6", ExpectedExecution.SYNTAX, Map.of("number", 5, "number2", 6));
        // Calls a method
        assertSyntax(args, "", ExpectedExecution.DEFAULT);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleString() {
        // Calls a method
        List<Argument<?>> args = List.of(String("string"));
        // Code statement
        assertSyntax(args, """
                "value"
                """, ExpectedExecution.SYNTAX, Map.of("string", "value"));
        // Calls a method
        assertSyntax(args, "5 5", ExpectedExecution.SYNTAX);
        // Calls a method
        assertSyntax(args, "", ExpectedExecution.DEFAULT);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleStringString() {
        // Calls a method
        List<Argument<?>> args = List.of(String("string"), String("string2"));
        // Calls a method
        assertSyntax(args, "test", ExpectedExecution.DEFAULT);
        // Code statement
        assertSyntax(args, """
                "first" "second"
                """, ExpectedExecution.SYNTAX, Map.of("string", "first", "string2", "second"));
        // Code statement
        assertSyntax(args, """
                "unescaped" "esc\\"aped"
                """, ExpectedExecution.SYNTAX, Map.of("string", "unescaped", "string2", "esc\"aped"));
        // Calls a method
        assertSyntax(args, "5 5", ExpectedExecution.SYNTAX);
        // Calls a method
        assertSyntax(args, "", ExpectedExecution.DEFAULT);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleGroup() {
        // Calls a method
        List<Argument<?>> args = List.of(Group("loop", Integer("first"), Integer("second")));
        // 1 2
        // Start of a block
        {
            // Calls a method
            var context = new CommandContext("1 2");
            // Calls a method
            context.setArg("first", 1, "1");
            // Calls a method
            context.setArg("second", 2, "2");
            // Calls a method
            assertSyntax(args, "1 2", ExpectedExecution.SYNTAX, Map.of("loop", context));
        // End of a block/expression
        }
        // Incomplete group
        // Calls a method
        assertSyntax(args, "1", ExpectedExecution.DEFAULT);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleLoop() {
        // Calls a method
        List<Argument<?>> stringLoop = List.of(Loop("loop", String("value")));
        // Calls a method
        assertSyntax(stringLoop, "one two three", ExpectedExecution.SYNTAX, Map.of("loop", List.of("one", "two", "three")));

        // Calls a method
        List<Argument<?>> intLoop = List.of(Loop("loop", Integer("value")));
        // Calls a method
        assertSyntax(intLoop, "1 2 3", ExpectedExecution.SYNTAX, Map.of("loop", List.of(1, 2, 3)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleLoopGroup() {
        // Calls a method
        List<Argument<?>> groupLoop = List.of(Loop("loop", Group("group", Integer("first"), Integer("second"))));
        // 1 2
        // Start of a block
        {
            // Calls a method
            var context = new CommandContext("1 2");
            // Calls a method
            context.setArg("first", 1, "1");
            // Calls a method
            context.setArg("second", 2, "2");
            // Calls a method
            assertSyntax(groupLoop, "1 2", ExpectedExecution.SYNTAX, Map.of("loop", List.of(context)));
        // End of a block/expression
        }
        // 1 2 3 4
        // Start of a block
        {
            // Calls a method
            var context1 = new CommandContext("1 2");
            // Calls a method
            var context2 = new CommandContext("3 4");

            // Calls a method
            context1.setArg("first", 1, "1");
            // Calls a method
            context1.setArg("second", 2, "2");

            // Calls a method
            context2.setArg("first", 3, "3");
            // Calls a method
            context2.setArg("second", 4, "4");

            // Calls a method
            assertSyntax(groupLoop, "1 2 3 4", ExpectedExecution.SYNTAX, Map.of("loop", List.of(context1, context2)));
        // End of a block/expression
        }
        // Incomplete loop
        // Calls a method
        assertSyntax(groupLoop, "1", ExpectedExecution.DEFAULT);
        // Calls a method
        assertSyntax(groupLoop, "1 2 3", ExpectedExecution.DEFAULT);
        // Calls a method
        assertSyntax(groupLoop, "1 2 3 4 5", ExpectedExecution.DEFAULT);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void singleLoopDoubleGroup() {
        // Assigns a value
        List<Argument<?>> groupLoop = List.of(
                // Code statement
                Loop("loop",
                        // Code statement
                        Group("group", BlockState("block"), EntityType("entity_type")),
                        // Code statement
                        Group("group2", EntityType("entity_type"), BlockState("block"))
                // End of a block/expression
                )
        // End of a block/expression
        );
        // block enchant
        // Start of a block
        {
            // Assigns a value
            var input = "minecraft:stone minecraft:allay";
            // Calls a method
            var context = new CommandContext(input);
            // Calls a method
            context.setArg("block", Block.STONE, "minecraft:stone");
            // Calls a method
            context.setArg("entity_type", EntityType.ALLAY, "minecraft:allay");
            // Calls a method
            assertSyntax(groupLoop, input, ExpectedExecution.SYNTAX, Map.of("loop", List.of(context)));
        // End of a block/expression
        }
        // enchant block block enchant
        // Start of a block
        {
            // Calls a method
            var context1 = new CommandContext("minecraft:allay minecraft:stone");
            // Calls a method
            var context2 = new CommandContext("minecraft:grass_block minecraft:zombie");

            // Calls a method
            context1.setArg("entity_type", EntityType.ALLAY, "minecraft:allay");
            // Calls a method
            context1.setArg("block", Block.STONE, "minecraft:stone");

            // Calls a method
            context2.setArg("block", Block.GRASS_BLOCK, "minecraft:grass_block");
            // Calls a method
            context2.setArg("entity_type", EntityType.ZOMBIE, "minecraft:zombie");

            // Calls a method
            var input = context1.getInput() + " " + context2.getInput();
            // Calls a method
            assertSyntax(groupLoop, input, ExpectedExecution.SYNTAX, Map.of("loop", List.of(context1, context2)));
        // End of a block/expression
        }
        // Incomplete loop
        // Calls a method
        assertSyntax(groupLoop, "minecraft:allay", ExpectedExecution.DEFAULT);
        // Calls a method
        assertSyntax(groupLoop, "minecraft:allay minecraft:allay", ExpectedExecution.DEFAULT);
        // Calls a method
        assertSyntax(groupLoop, "minecraft:stone", ExpectedExecution.DEFAULT);
        // Calls a method
        assertSyntax(groupLoop, "minecraft:stone minecraft:stone", ExpectedExecution.DEFAULT);
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertSyntax(List<Argument<?>> args, String input, ExpectedExecution expectedExecution, Map<String, Object> expectedValues) {
        // Assigns a value
        final String commandName = "name";

        // Calls a method
        var manager = new CommandManager();
        // Calls a method
        var command = new Command(commandName);
        // Calls a method
        manager.register(command);

        // Calls a method
        AtomicReference<ExpectedExecution> result = new AtomicReference<>();
        // Calls a method
        AtomicReference<Map<String, Object>> values = new AtomicReference<>();

        // Start of a method/block
        command.setDefaultExecutor((sender, context) -> {
            // Branch: checks a condition
            if (!result.compareAndSet(null, ExpectedExecution.DEFAULT)) {
                // Calls a method
                fail("Multiple execution: " + result.get());
            // End of a block/expression
            }
        // End of a block/expression
        });

        // Start of a method/block
        command.addSyntax((sender, context) -> {
            // Branch: checks a condition
            if (!result.compareAndSet(null, ExpectedExecution.SYNTAX)) {
                // Calls a method
                fail("Multiple execution: " + result.get());
            // End of a block/expression
            }
            // Calls a method
            values.set(context.getMap());
        // Calls a method
        }, args.toArray(Argument[]::new));

        // Assigns a value
        final String executeString = commandName + " " + input;
        // Calls a method
        manager.executeServerCommand(executeString);
        // Calls a method
        assertEquals(expectedExecution, result.get());
        // Branch: checks a condition
        if (expectedValues != null) {
            // Calls a method
            assertEquals(expectedValues, values.get());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertSyntax(List<Argument<?>> args, String input, ExpectedExecution expectedExecution) {
        // Calls a method
        assertSyntax(args, input, expectedExecution, null);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    enum ExpectedExecution {
        // Code statement
        DEFAULT,
        // Code statement
        SYNTAX
    // End of a block/expression
    }
// End of a block/expression
}
