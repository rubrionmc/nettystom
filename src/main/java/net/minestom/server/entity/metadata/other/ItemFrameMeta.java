// Package declaration for this file
package net.minestom.server.entity.metadata.other;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.utils.Rotation;

// Type declaration (class/interface/enum/record)
public class ItemFrameMeta extends HangingMeta {
    // Start of a method/block
    public ItemFrameMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public ItemStack getItem() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ItemFrame.ITEM);
    // End of a block/expression
    }

    // Start of a method/block
    public void setItem(ItemStack value) {
        // Calls a method
        metadata.set(MetadataDef.ItemFrame.ITEM, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Rotation getRotation() {
        // Returns a value to the caller
        return Rotation.values()[metadata.get(MetadataDef.ItemFrame.ROTATION)];
    // End of a block/expression
    }

    // Start of a method/block
    public void setRotation(Rotation value) {
        // Calls a method
        metadata.set(MetadataDef.ItemFrame.ROTATION, value.ordinal());
    // End of a block/expression
    }

// End of a block/expression
}
