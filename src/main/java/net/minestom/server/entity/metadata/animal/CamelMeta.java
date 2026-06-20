// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class CamelMeta extends AbstractHorseMeta {
    // Start of a method/block
    public CamelMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isDashing() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Camel.DASHING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setDashing(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Camel.DASHING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public long getLastPoseChangeTick() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Camel.LAST_POSE_CHANGE_TICK);
    // End of a block/expression
    }

    // Start of a method/block
    public void setLastPoseChangeTick(long value) {
        // Calls a method
        metadata.set(MetadataDef.Camel.LAST_POSE_CHANGE_TICK, value);
    // End of a block/expression
    }
// End of a block/expression
}
