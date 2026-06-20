// Déclaration du paquet de ce fichier
package net.minestom.server.event.inventory;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

/**
 * Called when a player interacts with an item in the creative menu
 */
// Déclaration de type (classe/interface/enum/record)
public class CreativeInventoryActionEvent implements PlayerInstanceEvent, CancellableEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final int slot;
    // Instruction de code
    private ItemStack clickedItem;
    // Instruction de code
    private boolean cancelled;

    // Instruction de code
    public CreativeInventoryActionEvent(Player player,
                                        // Instruction de code
                                        int slot,
                                        // Début d'une méthode/d'un bloc
                                        ItemStack clicked) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.slot = slot;
        // Accès à l'objet courant/parent
        this.clickedItem = clicked;
        // Accès à l'objet courant/parent
        this.cancelled = false;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player who is trying to click on the inventory.
     *
     * @return the player who clicked
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
     * Gets the item which has been clicked.
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
     * Changes the clicked item.
     *
     * @param clickedItem the clicked item
     */
    // Début d'une méthode/d'un bloc
    public void setClickedItem(ItemStack clickedItem) {
        // Accès à l'objet courant/parent
        this.clickedItem = clickedItem;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isCancelled() {
        // Renvoie une valeur à l'appelant
        return cancelled;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setCancelled(boolean cancel) {
        // Accès à l'objet courant/parent
        this.cancelled = cancel;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
