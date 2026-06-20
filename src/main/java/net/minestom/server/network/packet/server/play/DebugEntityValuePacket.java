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
public record DebugEntityValuePacket(int entityId, DebugSubscription.Update<?> update) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<DebugEntityValuePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.VAR_INT, DebugEntityValuePacket::entityId,
            // Instruction de code
            DebugSubscription.Update.NETWORK_TYPE, DebugEntityValuePacket::update,
            // Instruction de code
            DebugEntityValuePacket::new);
// Fin d'un bloc/d'une expression
}
