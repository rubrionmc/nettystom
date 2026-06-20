// Package declaration for this file
package net.minestom.server.listener.manager;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

/**
 * Small convenient interface to use method references with {@link PacketListenerManager#setPlayListener(Class, PacketPlayListenerConsumer)}.
 *
 * @param <T> the packet type
 */
// Annotation for the following element
@FunctionalInterface
// Type declaration (class/interface/enum/record)
public interface PacketPlayListenerConsumer<T extends ClientPacket> {
    // Calls a method
    void accept(T packet, Player player);
// End of a block/expression
}
