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

// Déclaration de type (classe/interface/enum/record)
public record UnloadChunkPacket(int chunkX, int chunkZ) implements ServerPacket.Play {
    // Client reads this as a single long in big endian, so we have to write it backwards
    // Affecte une valeur
    public static final NetworkBuffer.Type<UnloadChunkPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            INT, UnloadChunkPacket::chunkZ,
            // Instruction de code
            INT, UnloadChunkPacket::chunkX,
            // Instruction de code
            (z, x) -> new UnloadChunkPacket(x, z)
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
