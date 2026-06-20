// Package declaration for this file
package net.minestom.server.entity.metadata.other;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.EntityMeta;
// Import of a required class
import net.minestom.server.item.ItemStack;

// Type declaration (class/interface/enum/record)
public class OminousItemSpawnerMeta extends EntityMeta {
    // Start of a method/block
    public OminousItemSpawnerMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public ItemStack getItem() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.OminousItemSpawner.ITEM);
    // End of a block/expression
    }

    // Start of a method/block
    public void setItem(ItemStack value) {
        // Calls a method
        metadata.set(MetadataDef.OminousItemSpawner.ITEM, value);
    // End of a block/expression
    }

// End of a block/expression
}
