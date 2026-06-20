// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.LONG;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_LONG_ARRAY;

// Déclaration de type (classe/interface/enum/record)
public record MultiBlockChangePacket(long chunkSectionPosition, long[] blocks) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<MultiBlockChangePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            LONG, MultiBlockChangePacket::chunkSectionPosition,
            // Instruction de code
            VAR_LONG_ARRAY, MultiBlockChangePacket::blocks,
            // Instruction de code
            MultiBlockChangePacket::new);

    // Début d'une méthode/d'un bloc
    public MultiBlockChangePacket(int chunkX, int section, int chunkZ, long[] blocks) {
        // Appelle une méthode
        this(((long) (chunkX & 0x3FFFFF) << 42) | (section & 0xFFFFF) | ((long) (chunkZ & 0x3FFFFF) << 20), blocks);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
