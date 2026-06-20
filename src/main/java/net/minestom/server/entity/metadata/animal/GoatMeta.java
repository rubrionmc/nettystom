// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class GoatMeta extends AnimalMeta {
    // Start of a method/block
    public GoatMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isScreaming() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Goat.IS_SCREAMING_GOAT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setScreaming(boolean screaming) {
        // Calls a method
        metadata.set(MetadataDef.Goat.IS_SCREAMING_GOAT, screaming);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean hasLeftHorn() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Goat.HAS_LEFT_HORN);
    // End of a block/expression
    }

    // Start of a method/block
    public void setLeftHorn(boolean leftHorn) {
        // Calls a method
        metadata.set(MetadataDef.Goat.HAS_LEFT_HORN, leftHorn);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean hasRightHorn() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Goat.HAS_RIGHT_HORN);
    // End of a block/expression
    }

    // Start of a method/block
    public void setRightHorn(boolean rightHorn) {
        // Calls a method
        metadata.set(MetadataDef.Goat.HAS_RIGHT_HORN, rightHorn);
    // End of a block/expression
    }
// End of a block/expression
}
