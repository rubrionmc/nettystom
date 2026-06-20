// Package declaration for this file
package net.minestom.server.inventory;

// Import of a required class
import it.unimi.dsi.fastutil.Pair;
// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import of a required class
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import of a required class
import net.minestom.server.item.ItemStack;
// Import of a required class
import net.minestom.server.utils.MathUtils;

// Import of a required class
import java.util.Map;

/**
 * Represents a type of transaction that you can apply to an {@link AbstractInventory}.
 */
// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface TransactionType {

    /**
     * Adds an item to the inventory.
     * Can either take an air slot or be stacked.
     */
    // Assigns a value
    TransactionType ADD = (inventory, itemStack, slotPredicate, start, end, step) -> {
        // Calls a method
        Int2ObjectMap<ItemStack> itemChangesMap = new Int2ObjectOpenHashMap<>();
        // Check filled slot (not air)
        // Loop: repeats a block
        for (int i = start; step > 0 ? i < end : i > end; i += step) {
            // Calls a method
            ItemStack inventoryItem = inventory.getItemStack(i);
            // Branch: checks a condition
            if (inventoryItem.isAir()) {
                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }

            // Branch: checks a condition
            if (itemStack.isSimilar(inventoryItem)) {
                // Calls a method
                final int itemAmount = inventoryItem.amount();
                // Calls a method
                final int maxSize = inventoryItem.maxStackSize();
                // Branch: checks a condition
                if (itemAmount >= maxSize) continue;
                // Branch: checks a condition
                if (!slotPredicate.test(i, inventoryItem)) {
                    // Cancelled transaction
                    // Continues to the next loop iteration
                    continue;
                // End of a block/expression
                }

                // Calls a method
                final int itemStackAmount = itemStack.amount();
                // Assigns a value
                final int totalAmount = itemStackAmount + itemAmount;
                // Branch: checks a condition
                if (!MathUtils.isBetween(totalAmount, 0, itemStack.maxStackSize())) {
                    // Slot cannot accept the whole item, reduce amount to 'itemStack'
                    // Calls a method
                    itemChangesMap.put(i, inventoryItem.withAmount(maxSize));
                    // Calls a method
                    itemStack = itemStack.withAmount(totalAmount - maxSize);
                // Alternative branch of the condition
                } else {
                    // Slot can accept the whole item
                    // Calls a method
                    itemChangesMap.put(i, inventoryItem.withAmount(totalAmount));
                    // Assigns a value
                    itemStack = ItemStack.AIR;
                    // Breaks out of the loop/block
                    break;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Check air slot to fill
        // Loop: repeats a block
        for (int i = start; step > 0 ? i < end : i > end; i += step) {
            // Calls a method
            ItemStack inventoryItem = inventory.getItemStack(i);
            // Branch: checks a condition
            if (!inventoryItem.isAir()) continue;
            // Branch: checks a condition
            if (!slotPredicate.test(i, inventoryItem)) {
                // Cancelled transaction
                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }

            // Calls a method
            final int maxSize = itemStack.maxStackSize();
            // Calls a method
            final int currentSize = itemStack.amount();

            // Branch: checks a condition
            if (!MathUtils.isBetween(currentSize, 0, maxSize)) {
                // Slot cannot accept the whole item, reduce amount to 'itemStack'
                // Calls a method
                itemChangesMap.put(i, itemStack.withAmount(maxSize));
                // Calls a method
                itemStack = itemStack.withAmount(currentSize - maxSize);
            // Alternative branch of the condition
            } else {
                // Slot can accept the whole item
                // Calls a method
                itemChangesMap.put(i, itemStack.withAmount(currentSize));
                // Assigns a value
                itemStack = ItemStack.AIR;
                // Breaks out of the loop/block
                break;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Returns a value to the caller
        return Pair.of(itemStack, itemChangesMap);
    // End of a block/expression
    };

    /**
     * Takes an item from the inventory.
     * Can either transform items to air or reduce their amount.
     */
    // Assigns a value
    TransactionType TAKE = (inventory, itemStack, slotPredicate, start, end, step) -> {
        // Calls a method
        Int2ObjectMap<ItemStack> itemChangesMap = new Int2ObjectOpenHashMap<>();
        // Loop: repeats a block
        for (int i = start; step > 0 ? i < end : i > end; i += step) {
            // Calls a method
            final ItemStack inventoryItem = inventory.getItemStack(i);
            // Branch: checks a condition
            if (inventoryItem.isAir()) continue;
            // Branch: checks a condition
            if (itemStack.isSimilar(inventoryItem)) {
                // Branch: checks a condition
                if (!slotPredicate.test(i, inventoryItem)) {
                    // Cancelled transaction
                    // Continues to the next loop iteration
                    continue;
                // End of a block/expression
                }

                // Calls a method
                final int itemAmount = inventoryItem.amount();
                // Calls a method
                final int itemStackAmount = itemStack.amount();
                // Branch: checks a condition
                if (itemStackAmount < itemAmount) {
                    // Calls a method
                    itemChangesMap.put(i, inventoryItem.withAmount(itemAmount - itemStackAmount));
                    // Assigns a value
                    itemStack = ItemStack.AIR;
                    // Breaks out of the loop/block
                    break;
                // End of a block/expression
                }
                // Calls a method
                itemChangesMap.put(i, ItemStack.AIR);
                // Calls a method
                itemStack = itemStack.withAmount(itemStackAmount - itemAmount);
                // Branch: checks a condition
                if (itemStack.amount() == 0) {
                    // Assigns a value
                    itemStack = ItemStack.AIR;
                    // Breaks out of the loop/block
                    break;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return Pair.of(itemStack, itemChangesMap);
    // End of a block/expression
    };

    // Code statement
    Pair<ItemStack, Map<Integer, ItemStack>> process(AbstractInventory inventory,
                                                              // Code statement
                                                              ItemStack itemStack,
                                                              // Code statement
                                                              SlotPredicate slotPredicate,
                                                              // Code statement
                                                              int start, int end, int step);

    // Code statement
    default Pair<ItemStack, Map<Integer, ItemStack>> process(AbstractInventory inventory,
                                                                      // Code statement
                                                                      ItemStack itemStack,
                                                                      // Start of a method/block
                                                                      SlotPredicate slotPredicate) {
        // Returns a value to the caller
        return process(inventory, itemStack, slotPredicate, 0, inventory.getInnerSize(), 1);
    // End of a block/expression
    }

    // Code statement
    default Pair<ItemStack, Map<Integer, ItemStack>> process(AbstractInventory inventory,
                                                                      // Start of a method/block
                                                                      ItemStack itemStack) {
        // Returns a value to the caller
        return process(inventory, itemStack, (slot, itemStack1) -> true);
    // End of a block/expression
    }

    // Annotation for the following element
    @FunctionalInterface
    // Type declaration (class/interface/enum/record)
    interface SlotPredicate {
        // Calls a method
        boolean test(int slot, ItemStack itemStack);
    // End of a block/expression
    }
// End of a block/expression
}