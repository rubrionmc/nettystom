// Package declaration for this file
package net.minestom.server.event.entity;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import of a required class
import net.minestom.server.potion.Potion;

// Type declaration (class/interface/enum/record)
public class EntityPotionAddEvent implements EntityInstanceEvent, CancellableEvent {

    // Code statement
    private final Entity entity;
    // Code statement
    private final Potion potion;

    // Assigns a value
    private boolean cancelled = false;

    // Start of a method/block
    public EntityPotionAddEvent(Entity entity, Potion potion) {
        // Access to the current/parent object
        this.entity = entity;
        // Access to the current/parent object
        this.potion = potion;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Entity getEntity() {
        // Returns a value to the caller
        return entity;
    // End of a block/expression
    }

    /**
     * Returns the potion that was added.
     *
     * @return the added potion.
     */
    // Start of a method/block
    public Potion getPotion() {
        // Returns a value to the caller
        return potion;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isCancelled() {
        // Returns a value to the caller
        return cancelled;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void setCancelled(boolean cancel) {
        // Access to the current/parent object
        this.cancelled = cancel;
    // End of a block/expression
    }
// End of a block/expression
}
