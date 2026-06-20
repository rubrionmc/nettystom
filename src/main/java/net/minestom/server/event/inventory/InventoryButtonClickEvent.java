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

/**
 * Represents an event triggered when a player interacts with a button in an {@link AbstractInventory}, such
 * as the entries in a stonecutter, the buttons in an enchanting table, etc.
 * <br>
 * See the <a href="https://minecraft.wiki/w/Java_Edition_protocol/Inventory">minecraft protocol wiki</a> for a
 * list of all button ids.
 */
// Déclaration de type (classe/interface/enum/record)
public class InventoryButtonClickEvent implements InventoryEvent, PlayerInstanceEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private final AbstractInventory inventory;
    // Instruction de code
    private final int buttonId;

    // Début d'une méthode/d'un bloc
    public InventoryButtonClickEvent(Player player, AbstractInventory inventory, int buttonId) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.inventory = inventory;
        // Accès à l'objet courant/parent
        this.buttonId = buttonId;
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

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Player getPlayer() {
        // Renvoie une valeur à l'appelant
        return player;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getButtonId() {
        // Renvoie une valeur à l'appelant
        return buttonId;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
