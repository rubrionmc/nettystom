// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a packet which is already framed. (packet id+payload) + optional compression
 * Can be used if you want to send the exact same buffer to multiple clients without processing it more than once.
 */
// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public record FramedPacket(ServerPacket packet,
                           // Début d'une méthode/d'un bloc
                           NetworkBuffer body) implements SendablePacket {
    // Début d'une méthode/d'un bloc
    public FramedPacket {
        // Appelle une méthode
        body.readIndex(0);
        // Appelle une méthode
        body.readOnly();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
