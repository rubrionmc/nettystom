// Package declaration for this file
package net.minestom.server.listener.manager;

// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;

/**
 * Small convenient interface to use method references with {@link PacketListenerManager#setListener(ConnectionState, Class, PacketPrePlayListenerConsumer)}.
 *
 * @param <T> the packet type
 */
// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface PacketPrePlayListenerConsumer<T extends ClientPacket> {
    // Calls a method
    void accept(T packet, PlayerConnection connection);
// End of a block/expression
}
