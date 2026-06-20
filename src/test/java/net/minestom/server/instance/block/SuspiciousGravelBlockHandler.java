// Package declaration for this file
package net.minestom.server.instance.block;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.tag.Tag;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public class SuspiciousGravelBlockHandler implements BlockHandler {
    // Calls a method
    public static final SuspiciousGravelBlockHandler INSTANCE = new SuspiciousGravelBlockHandler(true);
    // Calls a method
    public static final SuspiciousGravelBlockHandler INSTANCE_NO_TAGS = new SuspiciousGravelBlockHandler(false);

    // Calls a method
    public static final Tag<String> LOOT_TABLE = Tag.String("LootTable");
    // Calls a method
    public static final Tag<ItemStack> ITEM = Tag.ItemStack("item");

    // Code statement
    private final boolean hasTags;

    // Start of a method/block
    public SuspiciousGravelBlockHandler(boolean hasTags) {
        // Access to the current/parent object
        this.hasTags = hasTags;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Key getKey() {
        // Returns a value to the caller
        return Key.key("suspicious_gravel");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Collection<Tag<?>> getBlockEntityTags() {
        // Returns a value to the caller
        return hasTags ? List.of(LOOT_TABLE, ITEM) : List.of();
    // End of a block/expression
    }
// End of a block/expression
}
