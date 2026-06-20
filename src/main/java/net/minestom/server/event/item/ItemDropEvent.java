// Package declaration for this file
package net.minestom.server.event.item;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.ItemEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.item.ItemStack;

// Type declaration (class/interface/enum/record)
public class ItemDropEvent implements PlayerInstanceEvent, ItemEvent, CancellableEvent {

    // Code statement
    private final Player player;
    // Code statement
    private final ItemStack itemStack;

    // Code statement
    private boolean cancelled;

    // Start of a method/block
    public ItemDropEvent(Player player, ItemStack itemStack) {
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.itemStack = itemStack;
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

    // Start of a method/block
    public ItemStack getItemStack() {
        // Returns a value to the caller
        return itemStack;
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
