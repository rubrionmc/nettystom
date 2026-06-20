// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class HappyGhastMeta extends AnimalMeta {

    // Start of a method/block
    public HappyGhastMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isLeashHolder() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.HappyGhast.IS_LEASH_HOLDER);
    // End of a block/expression
    }

    // Start of a method/block
    public void setLeashHolder(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.HappyGhast.IS_LEASH_HOLDER, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isStaysStill() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.HappyGhast.STAYS_STILL);
    // End of a block/expression
    }

    // Start of a method/block
    public void setStaysStill(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.HappyGhast.STAYS_STILL, value);
    // End of a block/expression
    }
// End of a block/expression
}
