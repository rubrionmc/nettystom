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
import net.minestom.server.inventory.Inventory;

/**
 * Called when a player open an {@link AbstractInventory}.
 * <p>
 * Executed by {@link Player#openInventory(Inventory)}.
 */
// Déclaration de type (classe/interface/enum/record)
public class InventoryOpenEvent implements InventoryEvent, PlayerInstanceEvent, CancellableEvent {

    // Instruction de code
    private AbstractInventory inventory;
    // Instruction de code
    private final Player player;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public InventoryOpenEvent(AbstractInventory inventory, Player player) {
        // Accès à l'objet courant/parent
        this.inventory = inventory;
        // Accès à l'objet courant/parent
        this.player = player;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player who opens the inventory.
     *
     * @return the player who opens the inventory
     */
    // Début d'une méthode/d'un bloc
    public Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return player;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the inventory to open, this could have been change by the {@link #setInventory(AbstractInventory)}.
     *
     * @return the inventory to open, null to just close the current inventory if any
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public AbstractInventory getInventory() {
        // Renvoie une valeur à l'appelant
        return inventory;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the inventory to open.
     * <p>
     * To do not open any inventory see {@link #setCancelled(boolean)}.
     *
     * @param inventory the inventory to open
     */
    // Début d'une méthode/d'un bloc
    public void setInventory(AbstractInventory inventory) {
        // Accès à l'objet courant/parent
        this.inventory = inventory;
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
