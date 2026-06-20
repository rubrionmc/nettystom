// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.data.WorldPos;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.FLOAT;

// Déclaration de type (classe/interface/enum/record)
public record SpawnPositionPacket(WorldPos worldPos, float yaw, float pitch) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<SpawnPositionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            WorldPos.NETWORK_TYPE, SpawnPositionPacket::worldPos,
            // Instruction de code
            FLOAT, SpawnPositionPacket::yaw,
            // Instruction de code
            FLOAT, SpawnPositionPacket::pitch,
            // Instruction de code
            SpawnPositionPacket::new);
// Fin d'un bloc/d'une expression
}
