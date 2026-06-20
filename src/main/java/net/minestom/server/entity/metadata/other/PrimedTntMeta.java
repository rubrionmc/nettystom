// Package declaration for this file
package net.minestom.server.entity.metadata.other;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.EntityMeta;
// Import of a required class
import net.minestom.server.instance.block.Block;

// Type declaration (class/interface/enum/record)
public class PrimedTntMeta extends EntityMeta {
    // Start of a method/block
    public PrimedTntMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public int getFuseTime() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.PrimedTnt.FUSE_TIME);
    // End of a block/expression
    }

    // Start of a method/block
    public void setFuseTime(int value) {
        // Calls a method
        metadata.set(MetadataDef.PrimedTnt.FUSE_TIME, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Block getBlockState() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.PrimedTnt.BLOCK_STATE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setBlockState(Block block) {
        // Calls a method
        metadata.set(MetadataDef.PrimedTnt.BLOCK_STATE, block);
    // End of a block/expression
    }

// End of a block/expression
}
