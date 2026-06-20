// Package declaration for this file
package net.minestom.demo.block;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTagTypes;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.ListBinaryTag;
// Import of a required class
import net.minestom.server.instance.block.BlockHandler;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import net.minestom.server.tag.Tag;
// Import of a required class
import net.minestom.server.tag.TagReadable;
// Import of a required class
import net.minestom.server.tag.TagSerializer;
// Import of a required class
import net.minestom.server.tag.TagWritable;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public class CampfireHandler implements BlockHandler {

    // Assigns a value
    public static final Tag<List<ItemStack>> ITEMS = Tag.View(new TagSerializer<>() {
        // Calls a method
        private final Tag<BinaryTag> internal = Tag.NBT("Items");

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable List<ItemStack> read(TagReadable reader) {
            // Calls a method
            ListBinaryTag item = (ListBinaryTag) reader.getTag(internal);
            // Branch: checks a condition
            if (item == null)
                // Returns a value to the caller
                return null;
            // Calls a method
            List<ItemStack> result = new ArrayList<>();
            // Start of a method/block
            item.forEach(childTag -> {
                // Calls a method
                CompoundBinaryTag nbtCompound = (CompoundBinaryTag) childTag;
                // Calls a method
                int amount = nbtCompound.getByte("Count");
                // Calls a method
                String id = nbtCompound.getString("id");
                // Calls a method
                Material material = Material.fromKey(id);
                // Calls a method
                result.add(ItemStack.of(material, amount));
            // End of a block/expression
            });
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(TagWritable writer, @Nullable List<ItemStack> value) {
            // Branch: checks a condition
            if (value == null) {
                // Calls a method
                writer.removeTag(internal);
                // Returns a value to the caller
                return;
            // End of a block/expression
            }
            // Code statement
            writer.setTag(internal, ListBinaryTag.listBinaryTag(
                    // Code statement
                    BinaryTagTypes.COMPOUND,
                    // Code statement
                    value.stream()
                            // Code statement
                            .map(item -> (BinaryTag) CompoundBinaryTag.builder()
                                    // Code statement
                                    .putByte("Count", (byte) item.amount())
                                    // Code statement
                                    .putByte("Slot", (byte) 1)
                                    // Code statement
                                    .putString("id", item.material().name())
                                    // Code statement
                                    .build())
                            // Code statement
                            .toList()
            // Code statement
            ));
        // End of a block/expression
        }
    // End of a block/expression
    });

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Tag<?>> getBlockEntityTags() {
        // Returns a value to the caller
        return List.of(ITEMS);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Key getKey() {
        // Returns a value to the caller
        return Key.key("minestom:test");
    // End of a block/expression
    }
// End of a block/expression
}
