// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.item.enchant.Enchantment;
// Import of a required class
import net.minestom.server.registry.RegistryTranscoder;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class EnchantmentListTest extends AbstractItemComponentTest<EnchantmentList> {
    // This is not a test, but it creates a compile error if the component type is changed away from Unit,
    // as a reminder that tests should be added for that new component type.
    // Assigns a value
    private static final List<DataComponent<EnchantmentList>> SHARED_COMPONENTS = List.of(
            // Code statement
            DataComponents.ENCHANTMENTS,
            // Code statement
            DataComponents.STORED_ENCHANTMENTS
    // End of a block/expression
    );

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<EnchantmentList> component() {
        // Returns a value to the caller
        return SHARED_COMPONENTS.getFirst();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, EnchantmentList>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                Map.entry("empty", EnchantmentList.EMPTY),
                // Code statement
                Map.entry("single entry", new EnchantmentList(Map.of(Enchantment.SHARPNESS, 1))),
                // Code statement
                Map.entry("multi entry", new EnchantmentList(Map.of(Enchantment.SHARPNESS, 1, Enchantment.PUNCH, 2)))
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testShorthandNbtSyntax(Env env) throws Exception {
        // Assigns a value
        var tag = MinestomAdventure.tagStringIO().asTag("""
                {
                    "sharpness": 1,
                    "punch": 2,
                }
                """);
        // Calls a method
        var coder = new RegistryTranscoder<>(Transcoder.NBT, env.process());
        // Calls a method
        var value = assertOk(component().decode(coder, tag));
        // Calls a method
        assertEquals(new EnchantmentList(Map.of(Enchantment.SHARPNESS, 1, Enchantment.PUNCH, 2)), value);
    // End of a block/expression
    }
// End of a block/expression
}
