// Package declaration for this file
package net.minestom.server.instance.block.rule;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.item.Material;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public abstract class BlockPlacementRule {
    // Assigns a value
    public static final int DEFAULT_UPDATE_RANGE = 10;

    // Code statement
    protected final Block block;

    // Start of a method/block
    protected BlockPlacementRule(Block block) {
        // Access to the current/parent object
        this.block = block;
    // End of a block/expression
    }

    /**
     * Called when the block state id can be updated (for instance if a neighbour block changed).
     * This is first called on a newly placed block, and then this is called for all neighbors of the block
     *
     * @param updateState The current parameters to the block update
     * @return the updated block
     */
    // Start of a method/block
    public Block blockUpdate(UpdateState updateState) {
        // Returns a value to the caller
        return updateState.currentBlock();
    // End of a block/expression
    }

    /**
     * Called when the block is placed.
     * It is recommended that you only set up basic properties on the block for this placement, such as determining facing, etc
     *
     * @param placementState The current parameters to the block placement
     * @return the block to place, {@code null} to cancel
     */
    // Calls a method
    public abstract @Nullable Block blockPlace(PlacementState placementState);

    // Start of a method/block
    public boolean isSelfReplaceable(Replacement replacement) {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Start of a method/block
    public Block getBlock() {
        // Returns a value to the caller
        return block;
    // End of a block/expression
    }

    /**
     * The max distance where a block update can be triggered. It is not based on block, so if the value is 3 and a completely
     * different block updates 3 blocks away it could still trigger an update.
     */
    // Start of a method/block
    public int maxUpdateDistance() {
        // Returns a value to the caller
        return DEFAULT_UPDATE_RANGE;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record PlacementState(
            // Code statement
            Block.Getter instance,
            // Code statement
            Block block,
            // Annotation for the following element
            @Nullable BlockFace blockFace,
            // Code statement
            Point placePosition,
            // Annotation for the following element
            @Nullable Point cursorPosition,
            // Annotation for the following element
            @Nullable Pos playerPosition,
            // Annotation for the following element
            @Nullable ItemStack usedItemStack,
            // Code statement
            boolean isPlayerShifting
    // Start of a method/block
    ) {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record UpdateState(Block.Getter instance,
                              // Code statement
                              Point blockPosition,
                              // Code statement
                              Block currentBlock,
                              // Start of a method/block
                              BlockFace fromFace) {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Replacement(
            // Code statement
            Block block,
            // Code statement
            BlockFace blockFace,
            // Code statement
            Point cursorPosition,
            /*
			  Whether the placement position is offset from the clicked block
			  position.
			 */
            // Code statement
            boolean isOffset,
            // Code statement
            Material material
    // Start of a method/block
    ) {
    // End of a block/expression
    }
// End of a block/expression
}
