// Package declaration for this file
package net.minestom.server.event.entity.projectile;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Entity;

// Type declaration (class/interface/enum/record)
public final class ProjectileCollideWithEntityEvent extends ProjectileCollideEvent {

    // Code statement
    private final Entity target;

    // Code statement
    public ProjectileCollideWithEntityEvent(
            // Code statement
            Entity projectile,
            // Code statement
            Pos position,
            // Code statement
            Entity target
    // Start of a method/block
    ) {
        // Access to the current/parent object
        super(projectile, position);
        // Access to the current/parent object
        this.target = target;
    // End of a block/expression
    }

    // Start of a method/block
    public Entity getTarget() {
        // Returns a value to the caller
        return target;
    // End of a block/expression
    }
// End of a block/expression
}
