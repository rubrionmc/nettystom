// Package declaration for this file
package net.minestom.server.event.trait;

// Import of a required class
import net.minestom.server.event.Event;
// Import of a required class
import net.minestom.server.inventory.AbstractInventory;

/**
 * Represents any event inside an {@link AbstractInventory}.
 */
// Type declaration (class/interface/enum/record)
public interface InventoryEvent extends Event {

    /**
     * Gets the inventory that was clicked.
     */
    // Calls a method
    AbstractInventory getInventory();
// End of a block/expression
}
