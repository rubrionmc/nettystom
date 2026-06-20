// Package declaration for this file
package net.minestom.server.entity.metadata.water;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class DolphinMeta extends AgeableWaterAnimalMeta {
    // Start of a method/block
    public DolphinMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public Point getTreasurePosition() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Dolphin.TREASURE_POSITION);
    // End of a block/expression
    }

    // Start of a method/block
    public void setTreasurePosition(Point value) {
        // Calls a method
        metadata.set(MetadataDef.Dolphin.TREASURE_POSITION, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHasFish() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Dolphin.HAS_FISH);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHasFish(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Dolphin.HAS_FISH, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getMoistureLevel() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Dolphin.MOISTURE_LEVEL);
    // End of a block/expression
    }

    // Start of a method/block
    public void setMoistureLevel(int value) {
        // Calls a method
        metadata.set(MetadataDef.Dolphin.MOISTURE_LEVEL, value);
    // End of a block/expression
    }
// End of a block/expression
}
