// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.parser.ArgumentParser;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Test string version of arguments.
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentParserTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testArgumentParser() {
        // Test each argument
        // Appelle une méthode
        assertParserEquals("Literal<example>", ArgumentType.Literal("example"));
        // Appelle une méthode
        assertParserEquals("Boolean<example>", ArgumentType.Boolean("example"));
        // Appelle une méthode
        assertParserEquals("Integer<example>", ArgumentType.Integer("example"));
        // Appelle une méthode
        assertParserEquals("Double<example>", ArgumentType.Double("example"));
        // Appelle une méthode
        assertParserEquals("Float<example>", ArgumentType.Float("example"));
        // Appelle une méthode
        assertParserEquals("String<example>", ArgumentType.String("example"));
        // Appelle une méthode
        assertParserEquals("Word<example>", ArgumentType.Word("example"));
        // Appelle une méthode
        assertParserEquals("StringArray<example>", ArgumentType.StringArray("example"));
        // Appelle une méthode
        assertParserEquals("Command<example>", ArgumentType.Command("example"));
        // Appelle une méthode
        assertParserEquals("Color<example>", ArgumentType.Color("example"));
        // Appelle une méthode
        assertParserEquals("Time<example>", ArgumentType.Time("example"));
        // Appelle une méthode
        assertParserEquals("Particle<example>", ArgumentType.Particle("example"));
        // Appelle une méthode
        assertParserEquals("ResourceLocation<example>", ArgumentType.ResourceLocation("example"));
        // Appelle une méthode
        assertParserEquals("EntityType<example>", ArgumentType.EntityType("example"));
        // Appelle une méthode
        assertParserEquals("BlockState<example>", ArgumentType.BlockState("example"));
        // Appelle une méthode
        assertParserEquals("IntRange<example>", ArgumentType.IntRange("example"));
        // Appelle une méthode
        assertParserEquals("FloatRange<example>", ArgumentType.FloatRange("example"));
        // Appelle une méthode
        assertParserEquals("ItemStack<example>", ArgumentType.ItemStack("example"));
        // Appelle une méthode
        assertParserEquals("Component<example>", ArgumentType.Component("example"));
        // Appelle une méthode
        assertParserEquals("UUID<example>", ArgumentType.UUID("example"));
        // Appelle une méthode
        assertParserEquals("NBT<example>", ArgumentType.NBT("example"));
        // Appelle une méthode
        assertParserEquals("NBTCompound<example>", ArgumentType.NbtCompound("example"));
        // Appelle une méthode
        assertParserEquals("RelativeBlockPosition<example>", ArgumentType.RelativeBlockPosition("example"));
        // Appelle une méthode
        assertParserEquals("RelativeVec2<example>", ArgumentType.RelativeVec2("example"));
        // Appelle une méthode
        assertParserEquals("RelativeVec3<example>", ArgumentType.RelativeVec3("example"));
        // Appelle une méthode
        assertParserEquals("Entities<example>", ArgumentType.Entity("example"));
        // Appelle une méthode
        assertParserEquals("Entity<example>", ArgumentType.Entity("example").singleEntity(true));
        // Appelle une méthode
        assertParserEquals("Players<example>", ArgumentType.Entity("example").onlyPlayers(true));
        // Appelle une méthode
        assertParserEquals("Player<example>", ArgumentType.Entity("example").onlyPlayers(true).singleEntity(true));

        // Test multiple argument functionality
        // Appelle une méthode
        assertParserEquals("NBT<arg1> RelativeVec2<arg2>", ArgumentType.NBT("arg1"), ArgumentType.RelativeVec2("arg2"));
        // Appelle une méthode
        assertParserEquals("Word<arg1> UUID<arg2> NBT<arg3>", ArgumentType.Word("arg1"), ArgumentType.UUID("arg2"), ArgumentType.NBT("arg3"));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static void assertParserEquals(String input, Argument<?> ... args) {
        // Appelle une méthode
        assertArrayEquals(ArgumentParser.generate(input), args);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
