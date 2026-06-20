// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.codec.Transcoder;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static java.util.Map.entry;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class CustomDataTest extends AbstractItemComponentTest<CustomData> {
    // This is not a test, but it creates a compile error if the component type is changed away,
    // as a reminder that tests should be added for that new component type.
    // Assigns a value
    private static final List<DataComponent<CustomData>> SHARED_COMPONENTS = List.of(
            // Code statement
            DataComponents.CUSTOM_DATA,
            // Code statement
            DataComponents.BUCKET_ENTITY_DATA
    // End of a block/expression
    );

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected DataComponent<CustomData> component() {
        // Returns a value to the caller
        return SHARED_COMPONENTS.getFirst();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected List<Map.Entry<String, CustomData>> directReadWriteEntries() {
        // Returns a value to the caller
        return List.of(
                // Code statement
                entry("simple", new CustomData(CompoundBinaryTag.builder()
                        // Code statement
                        .putString("hello", "world")
                        // Code statement
                        .put("nested", CompoundBinaryTag.builder()
                                // Code statement
                                .putInt("number", 42)
                                // Code statement
                                .build())
                        // Code statement
                        .build()))
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void customDataTagPath() throws IOException {
        // Assigns a value
        final ItemStack item = ItemStack.builder(Material.STICK)
                // Code statement
                .set(Tag.Integer("num").path("test"), 5)
                // Calls a method
                .build();
        // Calls a method
        final String snbt = MinestomAdventure.tagStringIO().asString(item.get(DataComponents.CUSTOM_DATA).nbt());
        // Calls a method
        assertEquals("{test:{num:5}}", snbt);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void typedCustomDataWrite() throws IOException {
        // Calls a method
        var component = new TypedCustomData<>(EntityType.COD, CompoundBinaryTag.builder().putFloat("Health", 1.5f).build());
        // Calls a method
        var nbt = TypedCustomData.codec(EntityType.CODEC).encode(Transcoder.NBT, component).orElseThrow();
        // Calls a method
        final String snbt = MinestomAdventure.tagStringIO().asString(nbt);
        // Calls a method
        assertEquals("{Health:1.5f,id:\"minecraft:cod\"}", snbt);
    // End of a block/expression
    }
// End of a block/expression
}
