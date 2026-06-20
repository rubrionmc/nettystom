// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class TurtleMeta extends AnimalMeta {
    // Start of a method/block
    public TurtleMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHasEgg() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Turtle.HAS_EGG);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHasEgg(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Turtle.HAS_EGG, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isLayingEgg() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Turtle.IS_LAYING_EGG);
    // End of a block/expression
    }

    // Start of a method/block
    public void setLayingEgg(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Turtle.IS_LAYING_EGG, value);
    // End of a block/expression
    }

// End of a block/expression
}
