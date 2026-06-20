// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.Pair;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;

// Import d'une classe nécessaire
import java.util.Map;

/**
 * Represents a type of transaction that you can apply to an {@link AbstractInventory}.
 */
// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface TransactionType {

    /**
     * Adds an item to the inventory.
     * Can either take an air slot or be stacked.
     */
    // Affecte une valeur
    TransactionType ADD = (inventory, itemStack, slotPredicate, start, end, step) -> {
        // Appelle une méthode
        Int2ObjectMap<ItemStack> itemChangesMap = new Int2ObjectOpenHashMap<>();
        // Check filled slot (not air)
        // Boucle : répète un bloc
        for (int i = start; step > 0 ? i < end : i > end; i += step) {
            // Appelle une méthode
            ItemStack inventoryItem = inventory.getItemStack(i);
            // Embranchement : vérifie une condition
            if (inventoryItem.isAir()) {
                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }

            // Embranchement : vérifie une condition
            if (itemStack.isSimilar(inventoryItem)) {
                // Appelle une méthode
                final int itemAmount = inventoryItem.amount();
                // Appelle une méthode
                final int maxSize = inventoryItem.maxStackSize();
                // Embranchement : vérifie une condition
                if (itemAmount >= maxSize) continue;
                // Embranchement : vérifie une condition
                if (!slotPredicate.test(i, inventoryItem)) {
                    // Cancelled transaction
                    // Passe à l'itération suivante de la boucle
                    continue;
                // Fin d'un bloc/d'une expression
                }

                // Appelle une méthode
                final int itemStackAmount = itemStack.amount();
                // Affecte une valeur
                final int totalAmount = itemStackAmount + itemAmount;
                // Embranchement : vérifie une condition
                if (!MathUtils.isBetween(totalAmount, 0, itemStack.maxStackSize())) {
                    // Slot cannot accept the whole item, reduce amount to 'itemStack'
                    // Appelle une méthode
                    itemChangesMap.put(i, inventoryItem.withAmount(maxSize));
                    // Appelle une méthode
                    itemStack = itemStack.withAmount(totalAmount - maxSize);
                // Branche alternative de la condition
                } else {
                    // Slot can accept the whole item
                    // Appelle une méthode
                    itemChangesMap.put(i, inventoryItem.withAmount(totalAmount));
                    // Affecte une valeur
                    itemStack = ItemStack.AIR;
                    // Interrompt la boucle/le bloc
                    break;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Check air slot to fill
        // Boucle : répète un bloc
        for (int i = start; step > 0 ? i < end : i > end; i += step) {
            // Appelle une méthode
            ItemStack inventoryItem = inventory.getItemStack(i);
            // Embranchement : vérifie une condition
            if (!inventoryItem.isAir()) continue;
            // Embranchement : vérifie une condition
            if (!slotPredicate.test(i, inventoryItem)) {
                // Cancelled transaction
                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }

            // Appelle une méthode
            final int maxSize = itemStack.maxStackSize();
            // Appelle une méthode
            final int currentSize = itemStack.amount();

            // Embranchement : vérifie une condition
            if (!MathUtils.isBetween(currentSize, 0, maxSize)) {
                // Slot cannot accept the whole item, reduce amount to 'itemStack'
                // Appelle une méthode
                itemChangesMap.put(i, itemStack.withAmount(maxSize));
                // Appelle une méthode
                itemStack = itemStack.withAmount(currentSize - maxSize);
            // Branche alternative de la condition
            } else {
                // Slot can accept the whole item
                // Appelle une méthode
                itemChangesMap.put(i, itemStack.withAmount(currentSize));
                // Affecte une valeur
                itemStack = ItemStack.AIR;
                // Interrompt la boucle/le bloc
                break;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return Pair.of(itemStack, itemChangesMap);
    // Fin d'un bloc/d'une expression
    };

    /**
     * Takes an item from the inventory.
     * Can either transform items to air or reduce their amount.
     */
    // Affecte une valeur
    TransactionType TAKE = (inventory, itemStack, slotPredicate, start, end, step) -> {
        // Appelle une méthode
        Int2ObjectMap<ItemStack> itemChangesMap = new Int2ObjectOpenHashMap<>();
        // Boucle : répète un bloc
        for (int i = start; step > 0 ? i < end : i > end; i += step) {
            // Appelle une méthode
            final ItemStack inventoryItem = inventory.getItemStack(i);
            // Embranchement : vérifie une condition
            if (inventoryItem.isAir()) continue;
            // Embranchement : vérifie une condition
            if (itemStack.isSimilar(inventoryItem)) {
                // Embranchement : vérifie une condition
                if (!slotPredicate.test(i, inventoryItem)) {
                    // Cancelled transaction
                    // Passe à l'itération suivante de la boucle
                    continue;
                // Fin d'un bloc/d'une expression
                }

                // Appelle une méthode
                final int itemAmount = inventoryItem.amount();
                // Appelle une méthode
                final int itemStackAmount = itemStack.amount();
                // Embranchement : vérifie une condition
                if (itemStackAmount < itemAmount) {
                    // Appelle une méthode
                    itemChangesMap.put(i, inventoryItem.withAmount(itemAmount - itemStackAmount));
                    // Affecte une valeur
                    itemStack = ItemStack.AIR;
                    // Interrompt la boucle/le bloc
                    break;
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                itemChangesMap.put(i, ItemStack.AIR);
                // Appelle une méthode
                itemStack = itemStack.withAmount(itemStackAmount - itemAmount);
                // Embranchement : vérifie une condition
                if (itemStack.amount() == 0) {
                    // Affecte une valeur
                    itemStack = ItemStack.AIR;
                    // Interrompt la boucle/le bloc
                    break;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return Pair.of(itemStack, itemChangesMap);
    // Fin d'un bloc/d'une expression
    };

    // Instruction de code
    Pair<ItemStack, Map<Integer, ItemStack>> process(AbstractInventory inventory,
                                                              // Instruction de code
                                                              ItemStack itemStack,
                                                              // Instruction de code
                                                              SlotPredicate slotPredicate,
                                                              // Instruction de code
                                                              int start, int end, int step);

    // Instruction de code
    default Pair<ItemStack, Map<Integer, ItemStack>> process(AbstractInventory inventory,
                                                                      // Instruction de code
                                                                      ItemStack itemStack,
                                                                      // Début d'une méthode/d'un bloc
                                                                      SlotPredicate slotPredicate) {
        // Renvoie une valeur à l'appelant
        return process(inventory, itemStack, slotPredicate, 0, inventory.getInnerSize(), 1);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    default Pair<ItemStack, Map<Integer, ItemStack>> process(AbstractInventory inventory,
                                                                      // Début d'une méthode/d'un bloc
                                                                      ItemStack itemStack) {
        // Renvoie une valeur à l'appelant
        return process(inventory, itemStack, (slot, itemStack1) -> true);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @FunctionalInterface
    // Déclaration de type (classe/interface/enum/record)
    interface SlotPredicate {
        // Appelle une méthode
        boolean test(int slot, ItemStack itemStack);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}