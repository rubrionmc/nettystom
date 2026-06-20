// Package declaration for this file
package net.minestom.server.event.entity;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.ItemEntity;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;

/**
 * Called when two {@link ItemEntity} are merging their {@link ItemStack} together to form a sole entity.
 */
// Type declaration (class/interface/enum/record)
public class EntityItemMergeEvent implements EntityInstanceEvent, CancellableEvent {

    // Code statement
    private final Entity entity;
    // Code statement
    private final ItemEntity merged;
    // Code statement
    private ItemStack result;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public EntityItemMergeEvent(ItemEntity source, ItemEntity merged, ItemStack result) {
        // Access to the current/parent object
        this.entity = source;
        // Access to the current/parent object
        this.merged = merged;
        // Access to the current/parent object
        this.result = result;
    // End of a block/expression
    }

    /**
     * Gets the {@link ItemEntity} who is receiving {@link #getMerged()}.
     * <p>
     * This can be used to get the final ItemEntity position.
     *
     * @return the source ItemEntity
     */
    // Annotation for the following element
    @Override
    // Start of a method/block
    public ItemEntity getEntity() {
        // Returns a value to the caller
        return (ItemEntity) entity;
    // End of a block/expression
    }

    /**
     * Gets the entity who will be merged.
     * <p>
     * This entity will be removed after the event.
     *
     * @return the merged ItemEntity
     */
    // Start of a method/block
    public ItemEntity getMerged() {
        // Returns a value to the caller
        return merged;
    // End of a block/expression
    }

    /**
     * Gets the final item stack on the ground.
     *
     * @return the item stack
     */
    // Start of a method/block
    public ItemStack getResult() {
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    /**
     * Changes the item stack which will appear on the ground.
     *
     * @param result the new item stack
     */
    // Start of a method/block
    public void setResult(ItemStack result) {
        // Access to the current/parent object
        this.result = result;
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
