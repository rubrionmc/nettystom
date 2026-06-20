// Package declaration for this file
package net.minestom.server.entity.metadata.water;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class GlowSquidMeta extends AgeableWaterAnimalMeta {
    // Start of a method/block
    public GlowSquidMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    private int getDarkTicksRemaining() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.GlowSquid.DARK_TICKS_REMAINING);
    // End of a block/expression
    }

    // Start of a method/block
    private void setDarkTicksRemaining(int ticks) {
        // Calls a method
        metadata.set(MetadataDef.GlowSquid.DARK_TICKS_REMAINING, ticks);
    // End of a block/expression
    }

// End of a block/expression
}
