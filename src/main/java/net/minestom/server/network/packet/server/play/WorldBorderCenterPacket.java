// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.DOUBLE;

// Déclaration de type (classe/interface/enum/record)
public record WorldBorderCenterPacket(double x, double z) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<WorldBorderCenterPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            DOUBLE, WorldBorderCenterPacket::x,
            // Instruction de code
            DOUBLE, WorldBorderCenterPacket::z,
            // Instruction de code
            WorldBorderCenterPacket::new);
// Fin d'un bloc/d'une expression
}
