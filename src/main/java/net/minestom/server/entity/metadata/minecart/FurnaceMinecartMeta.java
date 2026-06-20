// Package declaration for this file
package net.minestom.server.entity.metadata.minecart;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class FurnaceMinecartMeta extends AbstractMinecartMeta {
    // Start of a method/block
    public FurnaceMinecartMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isHasFuel() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.MinecartFurnace.HAS_FUEL);
    // End of a block/expression
    }

    // Start of a method/block
    public void setHasFuel(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.MinecartFurnace.HAS_FUEL, value);
    // End of a block/expression
    }

// End of a block/expression
}
