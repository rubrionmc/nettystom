// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

/**
 * Called when a player is trying to swap his main and off hand item.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerSwapItemEvent implements PlayerInstanceEvent, CancellableEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private ItemStack mainHandItem;
    // Instruction de code
    private ItemStack offHandItem;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public PlayerSwapItemEvent(Player player, ItemStack mainHandItem, ItemStack offHandItem) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.mainHandItem = mainHandItem;
        // Accès à l'objet courant/parent
        this.offHandItem = offHandItem;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the item which will be in player main hand after the event.
     *
     * @return the item in main hand
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getMainHandItem() {
        // Renvoie une valeur à l'appelant
        return mainHandItem;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the item which will be in the player main hand.
     *
     * @param mainHandItem the main hand item
     */
    // Début d'une méthode/d'un bloc
    public void setMainHandItem(ItemStack mainHandItem) {
        // Accès à l'objet courant/parent
        this.mainHandItem = mainHandItem;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the item which will be in player off hand after the event.
     *
     * @return the item in off hand
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getOffHandItem() {
        // Renvoie une valeur à l'appelant
        return offHandItem;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the item which will be in the player off hand.
     *
     * @param offHandItem the off hand item
     */
    // Début d'une méthode/d'un bloc
    public void setOffHandItem(ItemStack offHandItem) {
        // Accès à l'objet courant/parent
        this.offHandItem = offHandItem;
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
    public Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return player;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
