// Package declaration for this file
package net.minestom.server.event.player;

// Import of a required class
import net.minestom.server.coordinate.BlockVec;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.BlockEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;

/**
 * Called when a {@link Player} stops digging a block before it is broken
 */
// Type declaration (class/interface/enum/record)
public class PlayerCancelDiggingEvent implements PlayerInstanceEvent, BlockEvent {
    // Code statement
    private final Player player;
    // Code statement
    private final Instance instance;
    // Code statement
    private final Block block;
    // Code statement
    private final BlockVec blockPosition;

    // Start of a method/block
    public PlayerCancelDiggingEvent(Player player, Instance instance, Block block, BlockVec blockPosition) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.instance = instance;
        // Access to the current/parent object
        this.block = block;
        // Access to the current/parent object
        this.blockPosition = blockPosition;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Instance getInstance() {
        // Returns a value to the caller
        return instance;
    // End of a block/expression
    }

    /**
     * Gets the block which was being dug.
     *
     * @return the block
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public Block getBlock() {
        // Returns a value to the caller
        return block;
    // End of a block/expression
    }

    /**
     * Gets the block position.
     *
     * @return the block position
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public BlockVec getBlockPosition() {
        // Returns a value to the caller
        return blockPosition;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }
// End of a block/expression
}
