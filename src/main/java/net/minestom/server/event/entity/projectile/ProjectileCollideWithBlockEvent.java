// Package declaration for this file
package net.minestom.server.event.entity.projectile;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.instance.block.Block;

// Type declaration (class/interface/enum/record)
public final class ProjectileCollideWithBlockEvent extends ProjectileCollideEvent {

    // Code statement
    private final Block block;

    // Code statement
    public ProjectileCollideWithBlockEvent(
            // Code statement
            Entity projectile,
            // Code statement
            Pos position,
            // Code statement
            Block block
    // Start of a method/block
    ) {
        // Access to the current/parent object
        super(projectile, position);
        // Access to the current/parent object
        this.block = block;
    // End of a block/expression
    }

    // Start of a method/block
    public Block getBlock() {
        // Returns a value to the caller
        return block;
    // End of a block/expression
    }
// End of a block/expression
}
