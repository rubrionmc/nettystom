// Package declaration for this file
package net.minestom.server.entity.metadata.monster;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class BlazeMeta extends MonsterMeta {
    // Start of a method/block
    public BlazeMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isOnFire() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Blaze.IS_ON_FIRE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setOnFire(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Blaze.IS_ON_FIRE, value);
    // End of a block/expression
    }

// End of a block/expression
}
