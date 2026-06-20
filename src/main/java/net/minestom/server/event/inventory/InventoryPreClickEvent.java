// Package declaration for this file
package net.minestom.server.event.inventory;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.trait.CancellableEvent;
// Import of a required class
import net.minestom.server.event.trait.InventoryEvent;
// Import of a required class
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import of a required class
import net.minestom.server.inventory.AbstractInventory;
// Import of a required class
import net.minestom.server.inventory.click.Click;
// Import of a required class
import net.minestom.server.item.ItemStack;

/**
 * Called before {@link InventoryClickEvent}, used to potentially cancel the click.
 */
// Type declaration (class/interface/enum/record)
public class InventoryPreClickEvent implements InventoryEvent, PlayerInstanceEvent, CancellableEvent {

    // Code statement
    private final AbstractInventory inventory;
    // Code statement
    private final Player player;
    // Code statement
    private Click click;

    // Code statement
    private boolean cancelled;

    // Code statement
    public InventoryPreClickEvent(AbstractInventory inventory,
                                  // Code statement
                                  Player player,
                                  // Start of a method/block
                                  Click click) {
        // Access to the current/parent object
        this.inventory = inventory;
        // Access to the current/parent object
        this.player = player;
        // Access to the current/parent object
        this.click = click;
    // End of a block/expression
    }

    /**
     * Gets the player who is trying to click on the inventory.
     *
     * @return the player who clicked
     */
    // Start of a method/block
    public Player getPlayer() {
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }

    /**
     * Gets the player's click.
     */
    // Start of a method/block
    public Click getClick() {
        // Returns a value to the caller
        return click;
    // End of a block/expression
    }

    /**
     * Sets the player's click.
     */
    // Start of a method/block
    public void setClick(Click click) {
        // Access to the current/parent object
        this.click = click;
    // End of a block/expression
    }

    /**
     * Returns the clicked slot. This is only for convenience and may return -999 (a meaningless number), as some clicks
     * don't have a relevant slot (drag clicks and some drops). See {@link Click#slot()} for details.
     */
    // Start of a method/block
    public int getSlot() {
        // Returns a value to the caller
        return this.click.slot();
    // End of a block/expression
    }

    /**
     * Returns the clicked item. Some clicks involve more than a single item, like drops or clicks outside the inventory
     * menu; in these cases, the cursor is returned.
     */
    // Start of a method/block
    public ItemStack getClickedItem() {
        // Calls a method
        int slot = getSlot();

        // Returns a value to the caller
        return slot == -999 ? player.getInventory().getCursorItem()
                // Calls a method
                : this.inventory.getItemStack(slot);
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
    public AbstractInventory getInventory() {
        // Returns a value to the caller
        return inventory;
    // End of a block/expression
    }
// End of a block/expression
}
