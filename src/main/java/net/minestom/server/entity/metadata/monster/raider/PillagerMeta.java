// Package declaration for this file
package net.minestom.server.entity.metadata.monster.raider;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class PillagerMeta extends AbstractIllagerMeta {
    // Start of a method/block
    public PillagerMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isChargingCrossbow() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Pillager.IS_CHARGING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setChargingCrossbow(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Pillager.IS_CHARGING, value);
    // End of a block/expression
    }

// End of a block/expression
}
