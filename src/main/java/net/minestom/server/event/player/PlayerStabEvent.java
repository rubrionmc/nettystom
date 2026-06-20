// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.item.ItemStack;

/**
 * Called when a player attempts to use a stab attack on an item with the {@link net.minestom.server.item.component.PiercingWeapon} enchantment.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerStabEvent implements PlayerInstanceEvent {
    // Instruction de code
    private final Player player;

    // Début d'une méthode/d'un bloc
    public PlayerStabEvent(Player player) {
        // Accès à l'objet courant/parent
        this.player = player;
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

    /**
     * Gets the item which the player attacked with.
     *
     * @return the item in main hand
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getItemStack() {
        // Renvoie une valeur à l'appelant
        return player.getItemInMainHand();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
