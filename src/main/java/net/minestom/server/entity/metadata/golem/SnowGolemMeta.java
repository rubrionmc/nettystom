// Package declaration for this file
package net.minestom.server.entity.metadata.golem;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class SnowGolemMeta extends AbstractGolemMeta {
    // Start of a method/block
    public SnowGolemMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHasPumpkinHat() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.SnowGolem.PUMPKIN_HAT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHasPumpkinHat(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.SnowGolem.PUMPKIN_HAT, value);
    // End of a block/expression
    }

// End of a block/expression
}
