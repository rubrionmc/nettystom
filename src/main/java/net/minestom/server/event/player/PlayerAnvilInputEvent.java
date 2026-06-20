// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.InventoryEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.inventory.Inventory;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientNameItemPacket;

/**
 * Called every time a {@link Player} types a letter in an anvil GUI.
 *
 * @see ClientNameItemPacket
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerAnvilInputEvent implements PlayerInstanceEvent, InventoryEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final Inventory inventory;
    // Instruction de code
    private final String input;

    // Début d'une méthode/d'un bloc
    public PlayerAnvilInputEvent(Player player, Inventory inventory, String input) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.inventory = inventory;
        // Accès à l'objet courant/parent
        this.input = input;
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
    public String getInput() {
        // Renvoie une valeur à l'appelant
        return input;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Inventory getInventory() {
        // Renvoie une valeur à l'appelant
        return inventory;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
