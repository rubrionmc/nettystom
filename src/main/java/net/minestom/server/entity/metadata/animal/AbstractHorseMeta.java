// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class AbstractHorseMeta extends AnimalMeta {
    // Start of a method/block
    protected AbstractHorseMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isTamed() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractHorse.IS_TAME);
    // End of a block/expression
    }

    // Start of a method/block
    public void setTamed(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractHorse.IS_TAME, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHasBred() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractHorse.HAS_BRED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHasBred(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractHorse.HAS_BRED, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isEating() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractHorse.IS_EATING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setEating(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractHorse.IS_EATING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isRearing() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractHorse.IS_REARING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setRearing(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractHorse.IS_REARING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isMouthOpen() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractHorse.IS_MOUTH_OPEN);
    // End of a block/expression
    }

    // Start of a method/block
    public void setMouthOpen(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractHorse.IS_MOUTH_OPEN, value);
    // End of a block/expression
    }

// End of a block/expression
}
