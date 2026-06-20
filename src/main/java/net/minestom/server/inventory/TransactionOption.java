// Package declaration for this file
package net.minestom.server.inventory;

// Import of a required class
import net.minestom.server.item.ItemStack;

// Import of a required class
import java.util.Map;

// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface TransactionOption<T> {

    /**
     * Place as much as the item as possible.
     * <p>
     * The remaining, can be air.
     */
    // Assigns a value
    TransactionOption<ItemStack> ALL = (inventory, result, itemChangesMap) -> {
        // Calls a method
        itemChangesMap.forEach(inventory::setItemStack);
        // Returns a value to the caller
        return result;
    // End of a block/expression
    };

    /**
     * Only place the item if can be fully added.
     * <p>
     * Returns true if the item has been added, false if nothing changed.
     */
    // Assigns a value
    TransactionOption<Boolean> ALL_OR_NOTHING = (inventory, result, itemChangesMap) -> {
        // Branch: checks a condition
        if (result.isAir()) {
            // Item can be fully placed inside the inventory, do so
            // Calls a method
            itemChangesMap.forEach(inventory::setItemStack);
            // Returns a value to the caller
            return true;
        // Alternative branch of the condition
        } else {
            // Inventory cannot accept the item fully
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
    // End of a block/expression
    };

    /**
     * Loop through the inventory items without changing anything.
     * <p>
     * Returns true if the item can be fully added, false otherwise.
     */
    // Calls a method
    TransactionOption<Boolean> DRY_RUN = (inventory, result, itemChangesMap) -> result.isAir();

    // Code statement
    T fill(AbstractInventory inventory,
                    // Code statement
                    ItemStack result,
                    // Code statement
                    Map<Integer, ItemStack> itemChangesMap);

    // Code statement
    default T fill(TransactionType type,
                            // Code statement
                            AbstractInventory inventory,
                            // Start of a method/block
                            ItemStack itemStack) {
        // Calls a method
        var pair = type.process(inventory, itemStack);
        // Returns a value to the caller
        return fill(inventory, pair.left(), pair.right());
    // End of a block/expression
    }
// End of a block/expression
}
