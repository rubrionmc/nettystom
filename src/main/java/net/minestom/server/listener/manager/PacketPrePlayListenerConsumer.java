// Déclaration du paquet de ce fichier
package net.minestom.server.listener.manager;

// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;

/**
 * Small convenient interface to use method references with {@link PacketListenerManager#setListener(ConnectionState, Class, PacketPrePlayListenerConsumer)}.
 *
 * @param <T> the packet type
 */
// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface PacketPrePlayListenerConsumer<T extends ClientPacket> {
    // Appelle une méthode
    void accept(T packet, PlayerConnection connection);
// Fin d'un bloc/d'une expression
}
