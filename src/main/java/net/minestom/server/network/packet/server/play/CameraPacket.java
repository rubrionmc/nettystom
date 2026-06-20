// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record CameraPacket(int cameraId) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<CameraPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, CameraPacket::cameraId,
            // Instruction de code
            CameraPacket::new);
// Fin d'un bloc/d'une expression
}
