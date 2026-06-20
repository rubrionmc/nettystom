// Package declaration for this file
package net.minestom.server.entity.metadata.other;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.PathfinderMobMeta;

// Type declaration (class/interface/enum/record)
public class AllayMeta extends PathfinderMobMeta {
    // Start of a method/block
    public AllayMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isDancing() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Allay.IS_DANCING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setDancing(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Allay.IS_DANCING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean canDuplicate() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Allay.CAN_DUPLICATE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setCanDuplicate(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Allay.CAN_DUPLICATE, value);
    // End of a block/expression
    }

// End of a block/expression
}
