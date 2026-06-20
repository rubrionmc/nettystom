// Package declaration for this file
package net.minestom.server.command;

// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.parser.ArgumentParser;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Test string version of arguments.
 */
// Type declaration (class/interface/enum/record)
public class ArgumentParserTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testArgumentParser() {
        // Test each argument
        // Calls a method
        assertParserEquals("Literal<example>", ArgumentType.Literal("example"));
        // Calls a method
        assertParserEquals("Boolean<example>", ArgumentType.Boolean("example"));
        // Calls a method
        assertParserEquals("Integer<example>", ArgumentType.Integer("example"));
        // Calls a method
        assertParserEquals("Double<example>", ArgumentType.Double("example"));
        // Calls a method
        assertParserEquals("Float<example>", ArgumentType.Float("example"));
        // Calls a method
        assertParserEquals("String<example>", ArgumentType.String("example"));
        // Calls a method
        assertParserEquals("Word<example>", ArgumentType.Word("example"));
        // Calls a method
        assertParserEquals("StringArray<example>", ArgumentType.StringArray("example"));
        // Calls a method
        assertParserEquals("Command<example>", ArgumentType.Command("example"));
        // Calls a method
        assertParserEquals("Color<example>", ArgumentType.Color("example"));
        // Calls a method
        assertParserEquals("Time<example>", ArgumentType.Time("example"));
        // Calls a method
        assertParserEquals("Particle<example>", ArgumentType.Particle("example"));
        // Calls a method
        assertParserEquals("ResourceLocation<example>", ArgumentType.ResourceLocation("example"));
        // Calls a method
        assertParserEquals("EntityType<example>", ArgumentType.EntityType("example"));
        // Calls a method
        assertParserEquals("BlockState<example>", ArgumentType.BlockState("example"));
        // Calls a method
        assertParserEquals("IntRange<example>", ArgumentType.IntRange("example"));
        // Calls a method
        assertParserEquals("FloatRange<example>", ArgumentType.FloatRange("example"));
        // Calls a method
        assertParserEquals("ItemStack<example>", ArgumentType.ItemStack("example"));
        // Calls a method
        assertParserEquals("Component<example>", ArgumentType.Component("example"));
        // Calls a method
        assertParserEquals("UUID<example>", ArgumentType.UUID("example"));
        // Calls a method
        assertParserEquals("NBT<example>", ArgumentType.NBT("example"));
        // Calls a method
        assertParserEquals("NBTCompound<example>", ArgumentType.NbtCompound("example"));
        // Calls a method
        assertParserEquals("RelativeBlockPosition<example>", ArgumentType.RelativeBlockPosition("example"));
        // Calls a method
        assertParserEquals("RelativeVec2<example>", ArgumentType.RelativeVec2("example"));
        // Calls a method
        assertParserEquals("RelativeVec3<example>", ArgumentType.RelativeVec3("example"));
        // Calls a method
        assertParserEquals("Entities<example>", ArgumentType.Entity("example"));
        // Calls a method
        assertParserEquals("Entity<example>", ArgumentType.Entity("example").singleEntity(true));
        // Calls a method
        assertParserEquals("Players<example>", ArgumentType.Entity("example").onlyPlayers(true));
        // Calls a method
        assertParserEquals("Player<example>", ArgumentType.Entity("example").onlyPlayers(true).singleEntity(true));

        // Test multiple argument functionality
        // Calls a method
        assertParserEquals("NBT<arg1> RelativeVec2<arg2>", ArgumentType.NBT("arg1"), ArgumentType.RelativeVec2("arg2"));
        // Calls a method
        assertParserEquals("Word<arg1> UUID<arg2> NBT<arg3>", ArgumentType.Word("arg1"), ArgumentType.UUID("arg2"), ArgumentType.NBT("arg3"));
    // End of a block/expression
    }

    // Start of a method/block
    private static void assertParserEquals(String input, Argument<?> ... args) {
        // Calls a method
        assertArrayEquals(ArgumentParser.generate(input), args);
    // End of a block/expression
    }
// End of a block/expression
}
