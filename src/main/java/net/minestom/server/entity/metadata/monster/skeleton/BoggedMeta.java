// Package declaration for this file
package net.minestom.server.entity.metadata.monster.skeleton;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class BoggedMeta extends AbstractSkeletonMeta {
    // Start of a method/block
    public BoggedMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSheared() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Bogged.IS_SHEARED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSheared(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Bogged.IS_SHEARED, value);
    // End of a block/expression
    }
// End of a block/expression
}
