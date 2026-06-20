// Package declaration for this file
package net.minestom.server.event.entity.projectile;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;

// Type declaration (class/interface/enum/record)
public final class ProjectileUncollideEvent implements EntityInstanceEvent {

    // Code statement
    private final Entity projectile;

    // Start of a method/block
    public ProjectileUncollideEvent(Entity projectile) {
        // Access to the current/parent object
        this.projectile = projectile;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Entity getEntity() {
        // Returns a value to the caller
        return projectile;
    // End of a block/expression
    }

// End of a block/expression
}
