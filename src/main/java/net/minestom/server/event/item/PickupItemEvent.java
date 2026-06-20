// Package declaration for this file
package net.minestom.server.event.item;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.ItemEntity;
// Import of a required class
import net.minestom.server.entity.LivingEntity;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.EntityInstanceEvent;
// Import of a required class
import net.minestom.server.event.trait.ItemEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;

// Type declaration (class/interface/enum/record)
public class PickupItemEvent implements EntityInstanceEvent, ItemEvent, CancellableEvent {

    // Code statement
    private final LivingEntity livingEntity;
    // Code statement
    private final ItemEntity itemEntity;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public PickupItemEvent(LivingEntity livingEntity, ItemEntity itemEntity) {
        // Access to the current/parent object
        this.livingEntity = livingEntity;
        // Access to the current/parent object
        this.itemEntity = itemEntity;
    // End of a block/expression
    }

    // Start of a method/block
    public LivingEntity getLivingEntity() {
        // Returns a value to the caller
        return livingEntity;
    // End of a block/expression
    }

    // Start of a method/block
    public ItemEntity getItemEntity() {
        // Returns a value to the caller
        return itemEntity;
    // End of a block/expression
    }

    // Start of a method/block
    public ItemStack getItemStack() {
        // Returns a value to the caller
        return getItemEntity().getItemStack();
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

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Entity getEntity() {
        // Returns a value to the caller
        return livingEntity;
    // End of a block/expression
    }
// End of a block/expression
}
