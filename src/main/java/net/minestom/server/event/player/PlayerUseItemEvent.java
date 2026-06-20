// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.ItemEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

/**
 * Event when an item is used without clicking on a block.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerUseItemEvent implements PlayerInstanceEvent, ItemEvent, CancellableEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final PlayerHand hand;
    // Instruction de code
    private final ItemStack itemStack;

    // Instruction de code
    private long itemUseTime;
    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public PlayerUseItemEvent(Player player, PlayerHand hand, ItemStack itemStack, long itemUseTime) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.hand = hand;
        // Accès à l'objet courant/parent
        this.itemStack = itemStack;
        // Accès à l'objet courant/parent
        this.itemUseTime = itemUseTime;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets which hand the player used.
     *
     * @return the hand used
     */
    // Début d'une méthode/d'un bloc
    public PlayerHand getHand() {
        // Renvoie une valeur à l'appelant
        return hand;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the item which has been used.
     *
     * @return the item
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemStack getItemStack() {
        // Renvoie une valeur à l'appelant
        return itemStack;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the item usage duration. After this amount of milliseconds,
     * the animation will stop automatically and {@link net.minestom.server.event.item.PlayerFinishItemUseEvent} is called.
     *
     * @return the item use time
     */
    // Début d'une méthode/d'un bloc
    public long getItemUseTime() {
        // Renvoie une valeur à l'appelant
        return itemUseTime;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the item usage duration.
     *
     * @param itemUseTime the new item use time
     */
    // Début d'une méthode/d'un bloc
    public void setItemUseTime(long itemUseTime) {
        // Accès à l'objet courant/parent
        this.itemUseTime = itemUseTime;
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
