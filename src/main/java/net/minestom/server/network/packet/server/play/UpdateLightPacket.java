// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.data.LightData;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record UpdateLightPacket(int chunkX, int chunkZ,
                                // Début d'une méthode/d'un bloc
                                LightData lightData) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<UpdateLightPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, UpdateLightPacket::chunkX,
            // Instruction de code
            VAR_INT, UpdateLightPacket::chunkZ,
            // Instruction de code
            LightData.NETWORK_TYPE, UpdateLightPacket::lightData,
            // Instruction de code
            UpdateLightPacket::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
