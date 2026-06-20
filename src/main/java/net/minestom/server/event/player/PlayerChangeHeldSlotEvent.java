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
// Import d'une classe nécessaire
import net.minestom.server.utils.MathUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

/**
 * Called when a player change his held slot (by pressing 1-9 keys).
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerChangeHeldSlotEvent implements PlayerInstanceEvent, CancellableEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final byte oldSlot;
    // Instruction de code
    private byte newSlot;

    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public PlayerChangeHeldSlotEvent(Player player, byte oldSlot, byte newSlot) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.oldSlot = oldSlot;
        // Accès à l'objet courant/parent
        this.newSlot = newSlot;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the slot number that the player is currently holding
     *
     * @return The slot index that the player currently is holding
     */
    // Début d'une méthode/d'un bloc
    public byte getOldSlot() {
        // Renvoie une valeur à l'appelant
        return oldSlot;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the slot which the player will hold.
     * @return the future slot
     */
    // Début d'une méthode/d'un bloc
    public byte getNewSlot() {
        // Renvoie une valeur à l'appelant
        return newSlot;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the final held slot of the player.
     *
     * @param slot the new held slot
     * @throws IllegalArgumentException if <code>slot</code> is not between 0 and 8
     */
    // Début d'une méthode/d'un bloc
    public void setNewSlot(byte slot) {
        // Appelle une méthode
        Check.argCondition(!MathUtils.isBetween(slot, 0, 8), "The held slot needs to be between 0 and 8");
        // Accès à l'objet courant/parent
        this.newSlot = slot;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the ItemStack in the player's currently held slot
     * @return The ItemStack in the player's currently held slot
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getItemInOldSlot() {
        // Renvoie une valeur à l'appelant
        return player.getInventory().getItemStack(oldSlot);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the ItemStack in the slot the player will hold
     * @return The ItemStack in the final held slot of the player
     */
    // Début d'une méthode/d'un bloc
    public ItemStack getItemInNewSlot() {
        // Renvoie une valeur à l'appelant
        return player.getInventory().getItemStack(newSlot);
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
