// Package declaration for this file
package net.minestom.server.entity.metadata.water.fish;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.water.WaterAnimalMeta;

// Type declaration (class/interface/enum/record)
public class AbstractFishMeta extends WaterAnimalMeta {
    // Start of a method/block
    protected AbstractFishMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isFromBucket() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractFish.FROM_BUCKET);
    // End of a block/expression
    }

    // Start of a method/block
    public void setFromBucket(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractFish.FROM_BUCKET, value);
    // End of a block/expression
    }
// End of a block/expression
}
