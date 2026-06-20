// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.common;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.LONG;

// Déclaration de type (classe/interface/enum/record)
public record PingResponsePacket(long number) implements ServerPacket.Status, ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<PingResponsePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            LONG, PingResponsePacket::number,
            // Instruction de code
            PingResponsePacket::new);
// Fin d'un bloc/d'une expression
}
