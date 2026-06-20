// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static net.kyori.adventure.nbt.StringBinaryTag.stringBinaryTag;
// Static import of a member
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class ItemRarityTest extends AbstractItemComponentTest<ItemRarity> {
    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<ItemRarity> component() {
        // Returns a value to the caller
        return DataComponents.RARITY;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, ItemRarity>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                Map.entry("common", ItemRarity.COMMON)
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testReadFromNbtInt() {
        // Calls a method
        var value = assertOk(ItemRarity.CODEC.decode(Transcoder.NBT, stringBinaryTag("rare")));
        // Calls a method
        assertEquals(ItemRarity.RARE, value);
    // End of a block/expression
    }
// End of a block/expression
}
