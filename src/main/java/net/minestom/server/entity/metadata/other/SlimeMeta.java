// Package declaration for this file
package net.minestom.server.entity.metadata.other;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.MobMeta;

// Type declaration (class/interface/enum/record)
public class SlimeMeta extends MobMeta {
    // Start of a method/block
    public SlimeMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public int getSize() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Slime.SIZE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSize(int value) {
        // Access to the current/parent object
        this.consumeEntity((entity) -> {
            // Assigns a value
            float boxSize = 0.51000005f * value;
            // Calls a method
            entity.setBoundingBox(boxSize, boxSize, boxSize);
        // End of a block/expression
        });
        // Calls a method
        metadata.set(MetadataDef.Slime.SIZE, value);
    // End of a block/expression
    }

// End of a block/expression
}
