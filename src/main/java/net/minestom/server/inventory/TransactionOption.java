// Déclaration du paquet de ce fichier
package net.minestom.server.inventory;

// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

// Import d'une classe nécessaire
import java.util.Map;

// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface TransactionOption<T> {

    /**
     * Place as much as the item as possible.
     * <p>
     * The remaining, can be air.
     */
    // Affecte une valeur
    TransactionOption<ItemStack> ALL = (inventory, result, itemChangesMap) -> {
        // Appelle une méthode
        itemChangesMap.forEach(inventory::setItemStack);
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    };

    /**
     * Only place the item if can be fully added.
     * <p>
     * Returns true if the item has been added, false if nothing changed.
     */
    // Affecte une valeur
    TransactionOption<Boolean> ALL_OR_NOTHING = (inventory, result, itemChangesMap) -> {
        // Embranchement : vérifie une condition
        if (result.isAir()) {
            // Item can be fully placed inside the inventory, do so
            // Appelle une méthode
            itemChangesMap.forEach(inventory::setItemStack);
            // Renvoie une valeur à l'appelant
            return true;
        // Branche alternative de la condition
        } else {
            // Inventory cannot accept the item fully
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };

    /**
     * Loop through the inventory items without changing anything.
     * <p>
     * Returns true if the item can be fully added, false otherwise.
     */
    // Appelle une méthode
    TransactionOption<Boolean> DRY_RUN = (inventory, result, itemChangesMap) -> result.isAir();

    // Instruction de code
    T fill(AbstractInventory inventory,
                    // Instruction de code
                    ItemStack result,
                    // Instruction de code
                    Map<Integer, ItemStack> itemChangesMap);

    // Instruction de code
    default T fill(TransactionType type,
                            // Instruction de code
                            AbstractInventory inventory,
                            // Début d'une méthode/d'un bloc
                            ItemStack itemStack) {
        // Appelle une méthode
        var pair = type.process(inventory, itemStack);
        // Renvoie une valeur à l'appelant
        return fill(inventory, pair.left(), pair.right());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
