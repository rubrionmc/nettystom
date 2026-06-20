// Déclaration du paquet de ce fichier
package net.minestom.server.listener.manager;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

/**
 * Small convenient interface to use method references with {@link PacketListenerManager#setPlayListener(Class, PacketPlayListenerConsumer)}.
 *
 * @param <T> the packet type
 */
// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
public interface PacketPlayListenerConsumer<T extends ClientPacket> {
    // Appelle une méthode
    void accept(T packet, Player player);
// Fin d'un bloc/d'une expression
}
