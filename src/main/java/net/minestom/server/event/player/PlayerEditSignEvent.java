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

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public class PlayerEditSignEvent implements PlayerInstanceEvent, BlockEvent {
    // Code statement
    private final Player player;
    // Code statement
    private final Instance instance;
    // Code statement
    private final Block block;
    // Code statement
    private final BlockVec blockPosition;
    // Code statement
    private final List<String> lines;
    // Code statement
    private final boolean isFrontText;

    // Start of a method/block
    public PlayerEditSignEvent(Player player, Instance instance, Block block, BlockVec blockPosition, List<String> lines, boolean isFrontText) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.instance = instance;
        // Access to the current/parent object
        this.block = block;
        // Access to the current/parent object
        this.blockPosition = blockPosition;
        // Access to the current/parent object
        this.lines = lines;
        // Access to the current/parent object
        this.isFrontText = isFrontText;
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

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Block getBlock() {
        // Returns a value to the caller
        return block;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BlockVec getBlockPosition() {
        // Returns a value to the caller
        return blockPosition;
    // End of a block/expression
    }

    /**
     * Returns a list of strings representing the lines typed by the player onto the sign.
     * The length is always exactly 4.
     */
    // Start of a method/block
    public List<String> getLines() {
        // Returns a value to the caller
        return lines;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isFrontText() {
        // Returns a value to the caller
        return isFrontText;
    // End of a block/expression
    }
// End of a block/expression
}
