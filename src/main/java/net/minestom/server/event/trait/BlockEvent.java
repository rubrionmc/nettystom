// Package declaration for this file
package net.minestom.server.event.trait;

// Import of a required class
import net.minestom.server.coordinate.BlockVec;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents an event related to a {@link Block} happening in an {@link Instance}.
 */
// Type declaration (class/interface/enum/record)
public interface BlockEvent extends InstanceEvent {
    // Calls a method
    Block getBlock();

    // Calls a method
    BlockVec getBlockPosition();
// End of a block/expression
}
