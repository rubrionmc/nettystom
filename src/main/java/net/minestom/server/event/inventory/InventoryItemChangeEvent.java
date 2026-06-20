// Déclaration du paquet de ce fichier
package net.minestom.server.event.inventory;

// Import d'une classe nécessaire
import net.minestom.server.event.trait.InventoryEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.RecursiveEvent;
// Import d'une classe nécessaire
import net.minestom.server.inventory.AbstractInventory;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

/**
 * Called when {@link AbstractInventory#setItemStack(int, ItemStack)} is being invoked.
 * This event cannot be cancelled and items related to the change are already moved.
 */
// Déclaration de type (classe/interface/enum/record)
public class InventoryItemChangeEvent implements InventoryEvent, RecursiveEvent {

    // Instruction de code
    private final AbstractInventory inventory;
    // Instruction de code
    private final int slot;
    // Instruction de code
    private final ItemStack previousItem;
    // Instruction de code
    private final ItemStack newItem;

    // Instruction de code
    public InventoryItemChangeEvent(AbstractInventory inventory, int slot,
                                    // Début d'une méthode/d'un bloc
                                    ItemStack previousItem, ItemStack newItem) {
        // Accès à l'objet courant/parent
        this.inventory = inventory;
        // Accès à l'objet courant/parent
        this.slot = slot;
        // Accès à l'objet courant/parent
        this.previousItem = previousItem;
        // Accès à l'objet courant/parent
        this.newItem = newItem;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the changed slot number.
     *
     * @return the changed slot number.
     */
    // Début d'une méthode/d'un bloc
    public int getSlot() {
        // Renvoie une valeur à l'appelant
        return slot;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a previous item that was on changed slot.
     *
     * @return a previous item that was on changed slot.
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getPreviousItem() {
        // Renvoie une valeur à l'appelant
        return previousItem;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a new item on a changed slot.
     *
     * @return a new item on a changed slot.
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getNewItem() {
        // Renvoie une valeur à l'appelant
        return newItem;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public AbstractInventory getInventory() {
        // Renvoie une valeur à l'appelant
        return inventory;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
