// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.kyori.adventure.nbt.IntBinaryTag;
// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.kyori.adventure.util.RGBLike;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.color.Color;
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
import static java.util.Map.entry;
// Static import of a member
import static net.minestom.server.codec.CodecAssertions.assertOk;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class ColorTest extends AbstractItemComponentTest<RGBLike> {
    // This is not a test, but it creates a compile error if the component type is changed away from Integer,
    // as a reminder that tests should be added for that new component type.
    // Assigns a value
    private static final List<DataComponent<RGBLike>> SHARED_COMPONENTS = List.of(
            // Code statement
            DataComponents.MAP_COLOR
    // End of a block/expression
    );

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<RGBLike> component() {
        // Returns a value to the caller
        return SHARED_COMPONENTS.getFirst();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, RGBLike>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                entry("simple", new Color(0x123456))
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void namedTextColor() {
        // Calls a method
        var tag = assertOk(DataComponents.MAP_COLOR.encode(Transcoder.NBT, NamedTextColor.YELLOW));
        // Calls a method
        assertEquals(IntBinaryTag.intBinaryTag(16777045), tag);
    // End of a block/expression
    }
// End of a block/expression
}
