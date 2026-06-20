// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

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
        // Appelle une méthode
        chunks = List.copyOf(chunks);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record ChunkBiomeData(int chunkX, int chunkZ, byte[] data) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<ChunkBiomeData> SERIALIZER = new NetworkBuffer.Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, ChunkBiomeData value) {
                // Instruction de code
                buffer.write(INT, value.chunkZ); // x and z are inverted, not a bug
                // Appelle une méthode
                buffer.write(INT, value.chunkX);
                // Appelle une méthode
                buffer.write(BYTE_ARRAY, value.data);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public ChunkBiomeData read(NetworkBuffer buffer) {
                // Appelle une méthode
                int chunkZ = buffer.read(INT);
                // Appelle une méthode
                int chunkX = buffer.read(INT);
                // Appelle une méthode
                byte[] data = buffer.read(BYTE_ARRAY);
                // Renvoie une valeur à l'appelant
                return new ChunkBiomeData(chunkX, chunkZ, data);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
