// Déclaration du paquet de ce fichier
package net.minestom.server.event.player;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.CancellableEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.trait.PlayerEvent;

// Import d'une classe nécessaire
import java.util.Objects;

/**
 * Called when a {@link Player} is about to be redirected to another server.
 * <br>
 * It can be canceled to prevent the transfer from occurring.
 */
// Déclaration de type (classe/interface/enum/record)
public class OutgoingTransferEvent implements PlayerEvent, CancellableEvent {
    // Instruction de code
    private final Player player;
    // Instruction de code
    private String host;
    // Instruction de code
    private int port;
    // Instruction de code
    private boolean cancelled;

    // Début d'une méthode/d'un bloc
    public OutgoingTransferEvent(Player player, String host, int port) {
        // Accès à l'objet courant/parent
        this.player = Objects.requireNonNull(player);
        // Accès à l'objet courant/parent
        this.host = Objects.requireNonNull(host);
        // Accès à l'objet courant/parent
        this.port = port;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isCancelled() {
        // Renvoie une valeur à l'appelant
        return this.cancelled;
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
        return this.player;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the address of the target server that the player will be transferred to.
     *
     * @return the target host, usually an IP or domain name
     */
    // Début d'une méthode/d'un bloc
    public String getHost() {
        // Renvoie une valeur à l'appelant
        return this.host;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns the port of the target server that the player will be transferred to.
     *
     * @return the target port
     */
    // Début d'une méthode/d'un bloc
    public int getPort() {
        // Renvoie une valeur à l'appelant
        return this.port;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the address of the target server that the player will be transferred to.
     *
     * @param host the address of the target server, usually an IP or domain name
     */
    // Début d'une méthode/d'un bloc
    public void setHost(String host) {
        // Accès à l'objet courant/parent
        this.host = Objects.requireNonNull(host);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Changes the port of the target server that the player will be transferred to.
     *
     * @param port the target port
     */
    // Début d'une méthode/d'un bloc
    public void setPort(int port) {
        // Accès à l'objet courant/parent
        this.port = port;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
