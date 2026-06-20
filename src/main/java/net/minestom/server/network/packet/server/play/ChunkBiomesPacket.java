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
// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE_ARRAY;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.INT;

// Déclaration de type (classe/interface/enum/record)
public record ChunkBiomesPacket(List<ChunkBiomeData> chunks) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ChunkBiomesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            ChunkBiomeData.SERIALIZER.list(), ChunkBiomesPacket::chunks,
            // Instruction de code
            ChunkBiomesPacket::new);

    // Début d'une méthode/d'un bloc
    public ChunkBiomesPacket {
        // Affecte une valeur
        chunks = List.copyOf(chunks); // TODO deep copy?
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record ChunkBiomeData(int chunkX, int chunkZ, byte[] data) {
        // x and z are inverted, not a bug
        // Affecte une valeur
        public static final NetworkBuffer.Type<ChunkBiomeData> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                INT, ChunkBiomeData::chunkZ,
                // Instruction de code
                INT, ChunkBiomeData::chunkX,
                // Instruction de code
                BYTE_ARRAY, ChunkBiomeData::data,
                // Instruction de code
                (z, x, data) -> new ChunkBiomeData(x, z, data)
        // Fin d'un bloc/d'une expression
        );

        // Début d'une méthode/d'un bloc
        public ChunkBiomeData {
            // Appelle une méthode
            data = data.clone();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean equals(Object o) {
            // Embranchement : vérifie une condition
            if (!(o instanceof ChunkBiomeData(int x, int z, byte[] data1))) return false;
            // Renvoie une valeur à l'appelant
            return chunkX() == x && chunkZ() == z && Arrays.equals(data(), data1);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int hashCode() {
            // Appelle une méthode
            int result = chunkX();
            // Appelle une méthode
            result = 31 * result + chunkZ();
            // Appelle une méthode
            result = 31 * result + Arrays.hashCode(data());
            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
