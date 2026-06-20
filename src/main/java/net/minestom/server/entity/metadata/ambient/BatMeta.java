// Package declaration for this file
package net.minestom.server.entity.metadata.ambient;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class BatMeta extends AmbientCreatureMeta {
    // Start of a method/block
    public BatMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHanging() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Bat.IS_HANGING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHanging(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Bat.IS_HANGING, value);
    // End of a block/expression
    }

// End of a block/expression
}
