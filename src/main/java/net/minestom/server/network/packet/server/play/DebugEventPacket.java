// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.debug.DebugSubscription;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public record DebugEventPacket(DebugSubscription.Event<?> event) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugEventPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            DebugSubscription.Event.NETWORK_TYPE, DebugEventPacket::event,
            // Instruction de code
            DebugEventPacket::new);
// Fin d'un bloc/d'une expression
}
