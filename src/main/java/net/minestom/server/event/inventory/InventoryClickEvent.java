// Déclaration du paquet de ce fichier
package net.minestom.server.event.inventory;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.InventoryEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.inventory.AbstractInventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.click.ClickType;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

/**
 * Called after {@link InventoryPreClickEvent}, this event cannot be cancelled and items related to the click
 * are already moved.
 */
// Déclaration de type (classe/interface/enum/record)
public class InventoryClickEvent implements InventoryEvent, PlayerInstanceEvent {

    // Instruction de code
    private final AbstractInventory inventory;
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final int slot;
    // Instruction de code
    private final ClickType clickType;
    // Instruction de code
    private final ItemStack clickedItem;
    // Instruction de code
    private final ItemStack cursorItem;

    // Instruction de code
    public InventoryClickEvent(AbstractInventory inventory, Player player,
                               // Instruction de code
                               int slot, ClickType clickType,
                               // Début d'une méthode/d'un bloc
                               ItemStack clicked, ItemStack cursor) {
        // Accès à l'objet courant/parent
        this.inventory = inventory;
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.slot = slot;
        // Accès à l'objet courant/parent
        this.clickType = clickType;
        // Accès à l'objet courant/parent
        this.clickedItem = clicked;
        // Accès à l'objet courant/parent
        this.cursorItem = cursor;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player who clicked in the inventory.
     *
     * @return the player who clicked in the inventory
     */
    // Début d'une méthode/d'un bloc
    public Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return player;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the clicked slot number.
     *
     * @return the clicked slot number
     */
    // Début d'une méthode/d'un bloc
    public int getSlot() {
        // Renvoie une valeur à l'appelant
        return slot;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the click type.
     *
     * @return the click type
     */
    // Début d'une méthode/d'un bloc
    public ClickType getClickType() {
        // Renvoie une valeur à l'appelant
        return clickType;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the clicked item.
     *
     * @return the clicked item
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getClickedItem() {
        // Renvoie une valeur à l'appelant
        return clickedItem;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the item in the player cursor.
     *
     * @return the cursor item
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getCursorItem() {
        // Renvoie une valeur à l'appelant
        return cursorItem;
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
