// Package declaration for this file
package net.minestom.server.event.inventory;

// Import of a required class
import net.minestom.server.event.trait.InventoryEvent;
// Import of a required class
import net.minestom.server.event.trait.RecursiveEvent;
// Import of a required class
import net.minestom.server.inventory.AbstractInventory;
// Import of a required class
import net.minestom.server.item.ItemStack;

/**
 * Called when {@link AbstractInventory#setItemStack(int, ItemStack)} is being invoked.
 * This event cannot be cancelled and items related to the change are already moved.
 */
// Type declaration (class/interface/enum/record)
public class InventoryItemChangeEvent implements InventoryEvent, RecursiveEvent {

    // Code statement
    private final AbstractInventory inventory;
    // Code statement
    private final int slot;
    // Code statement
    private final ItemStack previousItem;
    // Code statement
    private final ItemStack newItem;

    // Code statement
    public InventoryItemChangeEvent(AbstractInventory inventory, int slot,
                                    // Start of a method/block
                                    ItemStack previousItem, ItemStack newItem) {
        // Access to the current/parent object
        this.inventory = inventory;
        // Access to the current/parent object
        this.slot = slot;
        // Access to the current/parent object
        this.previousItem = previousItem;
        // Access to the current/parent object
        this.newItem = newItem;
    // End of a block/expression
    }

    /**
     * Gets the changed slot number.
     *
     * @return the changed slot number.
     */
    // Start of a method/block
    public int getSlot() {
        // Returns a value to the caller
        return slot;
    // End of a block/expression
    }

    /**
     * Gets a previous item that was on changed slot.
     *
     * @return a previous item that was on changed slot.
     */
    // Start of a method/block
    public ItemStack getPreviousItem() {
        // Returns a value to the caller
        return previousItem;
    // End of a block/expression
    }

    /**
     * Gets a new item on a changed slot.
     *
     * @return a new item on a changed slot.
     */
    // Start of a method/block
    public ItemStack getNewItem() {
        // Returns a value to the caller
        return newItem;
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
