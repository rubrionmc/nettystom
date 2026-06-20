// Package declaration for this file
package net.minestom.server.entity.metadata.water.fish;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class TadpoleMeta extends AbstractFishMeta {
    // Start of a method/block
    public TadpoleMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }


    // Start of a method/block
    public boolean isAgeLocked() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Tadpole.AGE_LOCKED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setAgeLocked(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Tadpole.AGE_LOCKED, value);
    // End of a block/expression
    }
// End of a block/expression
}
