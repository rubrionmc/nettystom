// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.debug.DebugSubscription;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import d'une classe nécessaire
import java.util.Set;

// Déclaration de type (classe/interface/enum/record)
public record ClientDebugSubscriptionRequestPacket(
        // Instruction de code
        Set<DebugSubscription<?>> subscriptions
// Début d'une méthode/d'un bloc
) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientDebugSubscriptionRequestPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            DebugSubscription.NETWORK_TYPE.set(DebugSubscription.values().size()), ClientDebugSubscriptionRequestPacket::subscriptions,
            // Instruction de code
            ClientDebugSubscriptionRequestPacket::new);

    // Début d'une méthode/d'un bloc
    public ClientDebugSubscriptionRequestPacket {
        // Appelle une méthode
        subscriptions = Set.copyOf(subscriptions);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
