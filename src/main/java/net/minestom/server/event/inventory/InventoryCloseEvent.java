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
import net.minestom.server.inventory.Inventory;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Called when an {@link AbstractInventory} is closed by a player.
 */
// Déclaration de type (classe/interface/enum/record)
public class InventoryCloseEvent implements InventoryEvent, PlayerInstanceEvent {

    // Instruction de code
    private final AbstractInventory inventory;
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final boolean fromClient;
    // Instruction de code
    private Inventory newInventory;

    // Début d'une méthode/d'un bloc
    public InventoryCloseEvent(AbstractInventory inventory, Player player, boolean fromClient) {
        // Accès à l'objet courant/parent
        this.inventory = inventory;
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.fromClient = fromClient;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the player who closed the inventory.
     *
     * @return the player who closed the inventory
     */
    // Début d'une méthode/d'un bloc
    public Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return player;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets whether the client closed the inventory or the server did.
     *
     * @return true if the client closed the inventory, false if the server closed the inventory
     */
    // Début d'une méthode/d'un bloc
    public boolean isFromClient() {
        // Renvoie une valeur à l'appelant
        return fromClient;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the new inventory to open.
     *
     * @return the new inventory to open, null if there isn't any
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Inventory getNewInventory() {
        // Renvoie une valeur à l'appelant
        return newInventory;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Can be used to open a new inventory after closing the previous one.
     *
     * @param newInventory the inventory to open, null to do not open any
     */
    // Début d'une méthode/d'un bloc
    public void setNewInventory(@Nullable Inventory newInventory) {
        // Accès à l'objet courant/parent
        this.newInventory = newInventory;
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
