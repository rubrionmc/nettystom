// Package declaration for this file
package net.minestom.server.entity.metadata.item;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.EntityMeta;
// Import of a required class
import net.minestom.server.entity.metadata.ObjectDataProvider;
// Import of a required class
import net.minestom.server.item.ItemStack;

// Type declaration (class/interface/enum/record)
public class ItemEntityMeta extends EntityMeta implements ObjectDataProvider {
    // Start of a method/block
    public ItemEntityMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public ItemStack getItem() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.ItemEntity.ITEM);
    // End of a block/expression
    }

    // Start of a method/block
    public void setItem(ItemStack value) {
        // Calls a method
        metadata.set(MetadataDef.ItemEntity.ITEM, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int getObjectData() {
        // Returns a value to the caller
        return 1;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean requiresVelocityPacketAtSpawn() {
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

// End of a block/expression
}
