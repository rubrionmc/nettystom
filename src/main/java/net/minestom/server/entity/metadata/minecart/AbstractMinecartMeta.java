// Package declaration for this file
package net.minestom.server.entity.metadata.minecart;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.AbstractVehicleMeta;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public abstract class AbstractMinecartMeta extends AbstractVehicleMeta {
    // Start of a method/block
    protected AbstractMinecartMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable Block getCustomBlockState() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractMinecart.CUSTOM_BLOCK_STATE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setCustomBlockState(@Nullable Block value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractMinecart.CUSTOM_BLOCK_STATE, value);
    // End of a block/expression
    }

    // in 16th of a block
    // Start of a method/block
    public int getCustomBlockYPosition() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.AbstractMinecart.CUSTOM_BLOCK_Y_POSITION);
    // End of a block/expression
    }

    // Start of a method/block
    public void setCustomBlockYPosition(int value) {
        // Calls a method
        metadata.set(MetadataDef.AbstractMinecart.CUSTOM_BLOCK_Y_POSITION, value);
    // End of a block/expression
    }

// End of a block/expression
}
