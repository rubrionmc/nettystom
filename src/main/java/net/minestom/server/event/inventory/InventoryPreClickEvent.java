// Déclaration du paquet de ce fichier
package net.minestom.server.event.inventory;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.InventoryEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.inventory.AbstractInventory;
// Import d'une classe nécessaire
import net.minestom.server.inventory.click.Click;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

/**
 * Called before {@link InventoryClickEvent}, used to potentially cancel the click.
 */
// Déclaration de type (classe/interface/enum/record)
public class InventoryPreClickEvent implements InventoryEvent, PlayerInstanceEvent, CancellableEvent {

    // Instruction de code
    private final AbstractInventory inventory;
    // Instruction de code
    private final Player player;
    // Instruction de code
    private Click click;

    // Instruction de code
    private boolean cancelled;

    // Instruction de code
    public InventoryPreClickEvent(AbstractInventory inventory,
                                  // Instruction de code
                                  Player player,
                                  // Début d'une méthode/d'un bloc
                                  Click click) {
        // Accès à l'objet courant/parent
        this.inventory = inventory;
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.click = click;
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
     * Gets the player's click.
     */
    // Début d'une méthode/d'un bloc
    public Click getClick() {
        // Renvoie une valeur à l'appelant
        return click;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the player's click.
     */
    // Début d'une méthode/d'un bloc
    public void setClick(Click click) {
        // Accès à l'objet courant/parent
        this.click = click;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the clicked slot. This is only for convenience and may return -999 (a meaningless number), as some clicks
     * don't have a relevant slot (drag clicks and some drops). See {@link Click#slot()} for details.
     */
    // Début d'une méthode/d'un bloc
    public int getSlot() {
        // Renvoie une valeur à l'appelant
        return this.click.slot();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the clicked item. Some clicks involve more than a single item, like drops or clicks outside the inventory
     * menu; in these cases, the cursor is returned.
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getClickedItem() {
        // Appelle une méthode
        int slot = getSlot();

        // Renvoie une valeur à l'appelant
        return slot == -999 ? player.getInventory().getCursorItem()
                // Appelle une méthode
                : this.inventory.getItemStack(slot);
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
