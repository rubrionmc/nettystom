// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerInstanceEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.ClientPluginMessagePacket;

/**
 * Called when a player send {@link ClientPluginMessagePacket}.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerPluginMessageEvent implements PlayerInstanceEvent {

    // Instruction de code
    private final Player player;
    // Instruction de code
    private final String identifier;
    // Instruction de code
    private final byte[] message;

    // Début d'une méthode/d'un bloc
    public PlayerPluginMessageEvent(Player player, String identifier, byte[] message) {
        // Accès à l'objet courant/parent
        this.player = player;
        // Accès à l'objet courant/parent
        this.identifier = identifier;
        // Accès à l'objet courant/parent
        this.message = message;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the message identifier.
     *
     * @return the identifier
     */
    // Début d'une méthode/d'un bloc
    public String getIdentifier() {
        // Renvoie une valeur à l'appelant
        return identifier;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the message data as a byte array.
     *
     * @return the message
     */
    // Début d'une méthode/d'un bloc
    public byte[] getMessage() {
        // Renvoie une valeur à l'appelant
        return message;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the message data as a String.
     *
     * @return the message
     */
    // Début d'une méthode/d'un bloc
    public String getMessageString() {
        // Renvoie une valeur à l'appelant
        return new String(message);
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
