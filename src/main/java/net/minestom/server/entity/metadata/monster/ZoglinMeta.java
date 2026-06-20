// Package declaration for this file
package net.minestom.server.entity.metadata.monster;

// Import of a required class
import net.minestom.server.collision.BoundingBox;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class ZoglinMeta extends MonsterMeta {
    // Start of a method/block
    public ZoglinMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isBaby() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Zoglin.IS_BABY);
    // End of a block/expression
    }

    // Start of a method/block
    public void setBaby(boolean value) {
        // Branch: checks a condition
        if (isBaby() == value) {
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Access to the current/parent object
        this.consumeEntity((entity) -> {
            // Calls a method
            BoundingBox bb = entity.getBoundingBox();
            // Branch: checks a condition
            if (value) {
                // Calls a method
                double width = bb.width() / 2;
                // Calls a method
                entity.setBoundingBox(width, bb.height() / 2, width);
            // Alternative branch of the condition
            } else {
                // Calls a method
                double width = bb.width() * 2;
                // Calls a method
                entity.setBoundingBox(width, bb.height() * 2, width);
            // End of a block/expression
            }
        // End of a block/expression
        });
        // Calls a method
        metadata.set(MetadataDef.Zoglin.IS_BABY, value);
    // End of a block/expression
    }

// End of a block/expression
}
