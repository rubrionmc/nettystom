// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import d'une classe nécessaire
import java.util.Arrays;

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
    public MultiBlockChangePacket {
        // Appelle une méthode
        blocks = blocks.clone();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public MultiBlockChangePacket(int chunkX, int section, int chunkZ, long[] blocks) {
        // Appelle une méthode
        this(((long) (chunkX & 0x3FFFFF) << 42) | (section & 0xFFFFF) | ((long) (chunkZ & 0x3FFFFF) << 20), blocks);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (!(o instanceof MultiBlockChangePacket(long sectionPosition, long[] blocks1))) return false;
        // Renvoie une valeur à l'appelant
        return chunkSectionPosition() == sectionPosition && Arrays.equals(blocks(), blocks1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Appelle une méthode
        int result = Long.hashCode(chunkSectionPosition());
        // Appelle une méthode
        result = 31 * result + Arrays.hashCode(blocks());
        // Renvoie une valeur à l'appelant
        return result;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
