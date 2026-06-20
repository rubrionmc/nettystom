// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.data.ChunkData;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.data.LightData;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.INT;

// Déclaration de type (classe/interface/enum/record)
public record ChunkDataPacket(
        // Instruction de code
        int chunkX, int chunkZ,
        // Instruction de code
        ChunkData chunkData,
        // Instruction de code
        LightData lightData
// Début d'une méthode/d'un bloc
) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ChunkDataPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            INT, ChunkDataPacket::chunkX,
            // Instruction de code
            INT, ChunkDataPacket::chunkZ,
            // Instruction de code
            ChunkData.NETWORK_TYPE, ChunkDataPacket::chunkData,
            // Instruction de code
            LightData.NETWORK_TYPE, ChunkDataPacket::lightData,
            // Instruction de code
            ChunkDataPacket::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}