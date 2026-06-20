// Package declaration for this file
package net.minestom.server.event.trait;

// Import of a required class
import net.minestom.server.event.Event;
// Import of a required class
import net.minestom.server.item.ItemStack;

/**
 * Represents any event called about an {@link ItemStack}.
 */
// Type declaration (class/interface/enum/record)
public interface ItemEvent extends Event {
    // Calls a method
    ItemStack getItemStack();
// End of a block/expression
}
