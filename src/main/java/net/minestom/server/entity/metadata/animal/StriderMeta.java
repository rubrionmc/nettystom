// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class StriderMeta extends AnimalMeta {
    // Start of a method/block
    public StriderMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public int getTimeToBoost() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Strider.FUNGUS_BOOST);
    // End of a block/expression
    }

    // Start of a method/block
    public void setTimeToBoost(int value) {
        // Calls a method
        metadata.set(MetadataDef.Strider.FUNGUS_BOOST, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isShaking() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Strider.IS_SHAKING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setShaking(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Strider.IS_SHAKING, value);
    // End of a block/expression
    }

// End of a block/expression
}
