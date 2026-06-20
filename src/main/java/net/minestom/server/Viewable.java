// Déclaration du paquet de ce fichier
package net.minestom.server;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.Audience;
// Import d'une classe nécessaire
import net.minestom.server.adventure.audience.PacketGroupingAudience;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.SendablePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.utils.PacketSendingUtils;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;

/**
 * Represents something which can be displayed or hidden to players.
 */
// Déclaration de type (classe/interface/enum/record)
public interface Viewable {

    /**
     * Adds a viewer.
     *
     * @param player the viewer to add
     * @return true if the player has been added, false otherwise (could be because he is already a viewer)
     */
    // Appelle une méthode
    boolean addViewer(Player player);

    /**
     * Removes a viewer.
     *
     * @param player the viewer to remove
     * @return true if the player has been removed, false otherwise (could be because he was not a viewer)
     */
    // Appelle une méthode
    boolean removeViewer(Player player);

    /**
     * Gets all the viewers of this viewable element.
     *
     * @return A Set containing all the element's viewers
     */
    // Appelle une méthode
    Set<Player> getViewers();

    /**
     * Gets if a player is seeing this viewable object.
     *
     * @param player the player to check
     * @return true if {@code player} is a viewer, false otherwise
     */
    // Début d'une méthode/d'un bloc
    default boolean isViewer(Player player) {
        // Renvoie une valeur à l'appelant
        return getViewers().contains(player);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a packet to all viewers.
     * <p>
     * It is better than looping through the viewers
     * to send a packet since it is here only serialized once.
     *
     * @param packet the packet to send to all viewers
     */
    // Début d'une méthode/d'un bloc
    default void sendPacketToViewers(SendablePacket packet) {
        // Embranchement : vérifie une condition
        if (packet instanceof ServerPacket serverPacket) {
            // Appelle une méthode
            PacketSendingUtils.sendGroupedPacket(getViewers(), serverPacket);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            getViewers().forEach(player -> player.sendPacket(packet));
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void sendPacketsToViewers(Collection<SendablePacket> packets) {
        // Appelle une méthode
        packets.forEach(this::sendPacketToViewers);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void sendPacketsToViewers(SendablePacket... packets) {
        // Appelle une méthode
        sendPacketsToViewers(List.of(packets));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends a packet to all viewers and the viewable element if it is a player.
     * <p>
     * If 'this' isn't a player, then only {@link #sendPacketToViewers(SendablePacket)} is called.
     *
     * @param packet the packet to send
     */
    // Début d'une méthode/d'un bloc
    default void sendPacketToViewersAndSelf(SendablePacket packet) {
        // Appelle une méthode
        sendPacketToViewers(packet);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the result of {@link #getViewers()} as an Adventure Audience.
     *
     * @return the audience
     */
    // Début d'une méthode/d'un bloc
    default Audience getViewersAsAudience() {
        // Renvoie une valeur à l'appelant
        return PacketGroupingAudience.of(this.getViewers());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the result of {@link #getViewers()} as an {@link Iterable} of Adventure
     * {@link Audience}s.
     *
     * @return the audiences
     */
    // Début d'une méthode/d'un bloc
    default Iterable<? extends Audience> getViewersAsAudiences() {
        // Renvoie une valeur à l'appelant
        return this.getViewers();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
