// Package declaration for this file
package net.minestom.server.entity.metadata.display;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.instance.block.Block;

// Type declaration (class/interface/enum/record)
public class BlockDisplayMeta extends AbstractDisplayMeta {
    // Start of a method/block
    public BlockDisplayMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public Block getBlockStateId() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.BlockDisplay.DISPLAYED_BLOCK_STATE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setBlockState(Block value) {
        // Calls a method
        metadata.set(MetadataDef.BlockDisplay.DISPLAYED_BLOCK_STATE, value);
    // End of a block/expression
    }
// End of a block/expression
}
