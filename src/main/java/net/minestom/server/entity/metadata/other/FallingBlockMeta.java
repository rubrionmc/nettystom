// Package declaration for this file
package net.minestom.server.entity.metadata.other;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.entity.metadata.EntityMeta;
// Import of a required class
import net.minestom.server.entity.metadata.ObjectDataProvider;
// Import of a required class
import net.minestom.server.instance.block.Block;

// Type declaration (class/interface/enum/record)
public class FallingBlockMeta extends EntityMeta implements ObjectDataProvider {
    // Assigns a value
    private Block block = Block.STONE;

    // Start of a method/block
    public FallingBlockMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public Point getSpawnPosition() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.FallingBlock.SPAWN_POSITION);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSpawnPosition(Point value) {
        // Calls a method
        metadata.set(MetadataDef.FallingBlock.SPAWN_POSITION, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Block getBlock() {
        // Returns a value to the caller
        return block;
    // End of a block/expression
    }

    /**
     * Sets which block to display.
     * This is possible only before spawn packet is sent.
     *
     * @param block which block to display.
     */
    // Start of a method/block
    public void setBlock(Block block) {
        // Access to the current/parent object
        this.block = block;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int getObjectData() {
        // Returns a value to the caller
        return block.stateId();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean requiresVelocityPacketAtSpawn() {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }
// End of a block/expression
}
