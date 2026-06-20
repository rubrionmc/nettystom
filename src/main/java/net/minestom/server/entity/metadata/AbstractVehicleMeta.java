// Package declaration for this file
package net.minestom.server.entity.metadata;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class AbstractVehicleMeta extends EntityMeta {
    // Start of a method/block
    public AbstractVehicleMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public int getShakingTicks() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractVehicle.SHAKING_POWER);
    // End of a block/expression
    }

    // Start of a method/block
    public void setShakingTicks(int value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractVehicle.SHAKING_POWER, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getShakingDirection() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractVehicle.SHAKING_DIRECTION);
    // End of a block/expression
    }

    // Start of a method/block
    public void setShakingDirection(int value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractVehicle.SHAKING_DIRECTION, value);
    // End of a block/expression
    }

    // Start of a method/block
    public float getShakingMultiplier() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractVehicle.SHAKING_MULTIPLIER);
    // End of a block/expression
    }

    // Start of a method/block
    public void setShakingMultiplier(float value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractVehicle.SHAKING_MULTIPLIER, value);
    // End of a block/expression
    }
// End of a block/expression
}
