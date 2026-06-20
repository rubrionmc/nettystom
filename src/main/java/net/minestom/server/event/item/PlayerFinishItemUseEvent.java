// Déclaration du paquet de ce fichier
package net.minestom.server.event.item;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.ItemEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

/**
 * Called when a player completely finishes using an item.
 *
 * <p>{@link #getUseDuration()} represents the total time spent using the item.</p>
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerFinishItemUseEvent implements PlayerInstanceEvent, ItemEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final PlayerHand hand;
    // Instruction de code
    private final ItemStack itemStack;
    // Instruction de code
    private final long useDuration;
    // Affecte une valeur
    private boolean isRiptideSpinAttack = false;

    // Début d'une méthode/d'un bloc
    public PlayerFinishItemUseEvent(Player player, PlayerHand hand, ItemStack itemStack, long useDuration) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.hand = hand;
        // Accès à l'objet courant/parent
        this.itemStack = itemStack;
        // Accès à l'objet courant/parent
        this.useDuration = useDuration;
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
    public long getUseDuration() {
        // Renvoie une valeur à l'appelant
        return useDuration;
    // Fin d'un bloc/d'une expression
    }

    /**
     * True if this event will transition the player into a riptide spin attack.
     */
    // Début d'une méthode/d'un bloc
    public boolean isRiptideSpinAttack() {
        // Renvoie une valeur à l'appelant
        return isRiptideSpinAttack;
    // Fin d'un bloc/d'une expression
    }

    /**
     * True if this event will transition the player into a riptide spin attack.
     */
    // Début d'une méthode/d'un bloc
    public void setRiptideSpinAttack(boolean riptideSpinAttack) {
        // Affecte une valeur
        isRiptideSpinAttack = riptideSpinAttack;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
