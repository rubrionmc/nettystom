// Package declaration for this file
package net.minestom.server;

// Import of a required class
import net.kyori.adventure.audience.Audience;
// Import of a required class
import net.minestom.server.adventure.audience.PacketGroupingAudience;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.server.SendablePacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.utils.PacketSendingUtils;
// Import of a required class
import org.jetbrains.annotations.Unmodifiable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;

/**
 * Represents something which can be displayed or hidden to players.
 */
// Type declaration (class/interface/enum/record)
public interface Viewable {

    /**
     * Adds a viewer.
     *
     * @param player the viewer to add
     * @return true if the player has been added, false otherwise (could be because he is already a viewer)
     */
    // Calls a method
    boolean addViewer(Player player);

    /**
     * Removes a viewer.
     *
     * @param player the viewer to remove
     * @return true if the player has been removed, false otherwise (could be because he was not a viewer)
     */
    // Calls a method
    boolean removeViewer(Player player);

    /**
     * Gets all the viewers of this viewable element.
     *
     * @return A Set containing all the element's viewers
     */
    // Annotation for the following element
    @Unmodifiable
    // Calls a method
    Set<? extends Player> getViewers();

    /**
     * Gets if a player is seeing this viewable object.
     *
     * @param player the player to check
     * @return true if {@code player} is a viewer, false otherwise
     */
    // Start of a method/block
    default boolean isViewer(Player player) {
        // Returns a value to the caller
        return getViewers().contains(player);
    // End of a block/expression
    }

    /**
     * Sends a packet to all viewers.
     * <p>
     * It is better than looping through the viewers
     * to send a packet since it is here only serialized once.
     *
     * @param packet the packet to send to all viewers
     */
    // Start of a method/block
    default void sendPacketToViewers(SendablePacket packet) {
        // Branch: checks a condition
        if (packet instanceof ServerPacket serverPacket) {
            // Calls a method
            PacketSendingUtils.sendGroupedPacket(getViewers(), serverPacket);
        // Alternative branch of the condition
        } else {
            // Calls a method
            getViewers().forEach(player -> player.sendPacket(packet));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    default void sendPacketsToViewers(Collection<? extends SendablePacket> packets) {
        // Calls a method
        packets.forEach(this::sendPacketToViewers);
    // End of a block/expression
    }

    // Start of a method/block
    default void sendPacketsToViewers(SendablePacket... packets) {
        // Calls a method
        sendPacketsToViewers(List.of(packets));
    // End of a block/expression
    }

    /**
     * Sends a packet to all viewers and the viewable element if it is a player.
     * <p>
     * If 'this' isn't a player, then only {@link #sendPacketToViewers(SendablePacket)} is called.
     *
     * @param packet the packet to send
     */
    // Start of a method/block
    default void sendPacketToViewersAndSelf(SendablePacket packet) {
        // Calls a method
        sendPacketToViewers(packet);
    // End of a block/expression
    }

    /**
     * Gets the result of {@link #getViewers()} as an Adventure Audience.
     *
     * @return the audience
     */
    // Start of a method/block
    default Audience getViewersAsAudience() {
        // Returns a value to the caller
        return PacketGroupingAudience.of(this.getViewers());
    // End of a block/expression
    }

    /**
     * Gets the result of {@link #getViewers()} as an {@link Iterable} of Adventure
     * {@link Audience}s.
     *
     * @return the audiences
     */
    // Start of a method/block
    default Iterable<? extends Audience> getViewersAsAudiences() {
        // Returns a value to the caller
        return this.getViewers();
    // End of a block/expression
    }
// End of a block/expression
}
