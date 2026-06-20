// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.INT;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record OpenHorseWindowPacket(int windowId, int slotCount, int entityId) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<OpenHorseWindowPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, OpenHorseWindowPacket::windowId,
            // Instruction de code
            VAR_INT, OpenHorseWindowPacket::slotCount,
            // Instruction de code
            INT, OpenHorseWindowPacket::entityId,
            // Instruction de code
            OpenHorseWindowPacket::new);
// Fin d'un bloc/d'une expression
}
