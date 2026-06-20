// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play.data;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;

// Import d'une classe nécessaire
import java.util.BitSet;
// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BITSET;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE_ARRAY;

// Déclaration de type (classe/interface/enum/record)
public record LightData(
        // Instruction de code
        BitSet skyMask, BitSet blockMask,
        // Instruction de code
        BitSet emptySkyMask, BitSet emptyBlockMask,
        // Instruction de code
        List<byte[]> skyLight,
        // Instruction de code
        List<byte[]> blockLight
// Début d'une méthode/d'un bloc
) {
    // Début d'une méthode/d'un bloc
    public LightData {
        // Appelle une méthode
        skyMask = (BitSet) skyMask.clone();
        // Appelle une méthode
        blockMask = (BitSet) blockMask.clone();
        // Appelle une méthode
        emptySkyMask = (BitSet) emptySkyMask.clone();
        // Appelle une méthode
        emptyBlockMask = (BitSet) emptyBlockMask.clone();
        // Appelle une méthode
        skyLight = List.copyOf(skyLight);
        // Appelle une méthode
        blockLight = List.copyOf(blockLight);
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final int MAX_SECTIONS = 4096 / 16;

    // Affecte une valeur
    public static final NetworkBuffer.Type<LightData> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            BITSET, LightData::skyMask,
            // Instruction de code
            BITSET, LightData::blockMask,
            // Instruction de code
            BITSET, LightData::emptySkyMask,
            // Instruction de code
            BITSET, LightData::emptyBlockMask,
            // Instruction de code
            BYTE_ARRAY.list(MAX_SECTIONS), LightData::skyLight,
            // Instruction de code
            BYTE_ARRAY.list(MAX_SECTIONS), LightData::blockLight,
            // Instruction de code
            LightData::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
