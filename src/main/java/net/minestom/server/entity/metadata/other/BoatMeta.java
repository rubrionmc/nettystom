// Package declaration for this file
package net.minestom.server.entity.metadata.other;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.AbstractVehicleMeta;

// Type declaration (class/interface/enum/record)
public class BoatMeta extends AbstractVehicleMeta {
    // Start of a method/block
    public BoatMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isLeftPaddleTurning() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Boat.IS_LEFT_PADDLE_TURNING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setLeftPaddleTurning(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Boat.IS_LEFT_PADDLE_TURNING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isRightPaddleTurning() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Boat.IS_RIGHT_PADDLE_TURNING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setRightPaddleTurning(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Boat.IS_RIGHT_PADDLE_TURNING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getSplashTimer() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Boat.SPLASH_TIMER);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSplashTimer(int value) {
        // Calls a method
        metadata.set(MetadataDef.Boat.SPLASH_TIMER, value);
    // End of a block/expression
    }
// End of a block/expression
}
