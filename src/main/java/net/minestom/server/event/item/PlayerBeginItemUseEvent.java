// Déclaration du paquet de ce fichier
package net.minestom.server.event.item;

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
import net.minestom.server.item.ItemAnimation;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

/**
 * Called when a player begins using an item with the item, animation, and duration.
 *
 * <p>Setting the use duration to zero or cancelling the event will prevent consumption.</p>
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerBeginItemUseEvent implements PlayerInstanceEvent, ItemEvent, CancellableEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final PlayerHand hand;
    // Instruction de code
    private final ItemStack itemStack;
    // Instruction de code
    private final ItemAnimation animation;
    // Instruction de code
    private long itemUseDuration;

    // Affecte une valeur
    private boolean cancelled = false;

    // Instruction de code
    public PlayerBeginItemUseEvent(Player player, PlayerHand hand,
                                   // Instruction de code
                                   ItemStack itemStack, ItemAnimation animation,
                                   // Début d'une méthode/d'un bloc
                                   long itemUseDuration) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.hand = hand;
        // Accès à l'objet courant/parent
        this.itemStack = itemStack;
        // Accès à l'objet courant/parent
        this.animation = animation;
        // Accès à l'objet courant/parent
        this.itemUseDuration = itemUseDuration;
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

    // Début d'une méthode/d'un bloc
    public PlayerHand getHand() {
        // Renvoie une valeur à l'appelant
        return hand;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ItemStack getItemStack() {
        // Renvoie une valeur à l'appelant
        return itemStack;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ItemAnimation getAnimation() {
        // Renvoie une valeur à l'appelant
        return animation;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the item use duration, in ticks. A duration of zero will prevent consumption (same effect as cancellation).
     *
     * @return the current item use duration
     */
    // Début d'une méthode/d'un bloc
    public long getItemUseDuration() {
        // Renvoie une valeur à l'appelant
        return itemUseDuration;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the item use duration, in ticks.
     */
    // Début d'une méthode/d'un bloc
    public void setItemUseDuration(long itemUseDuration) {
        // Appelle une méthode
        Check.argCondition(itemUseDuration < 0, "Item use duration cannot be negative");
        // Accès à l'objet courant/parent
        this.itemUseDuration = itemUseDuration;
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
    public void setCancelled(boolean cancelled) {
        // Accès à l'objet courant/parent
        this.cancelled = cancelled;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
