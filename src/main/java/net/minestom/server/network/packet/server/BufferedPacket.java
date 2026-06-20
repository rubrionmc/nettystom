// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

/**
 * Represents a buffer to directly write to the network.
 * <p>
 * May contain multiple packets.
 */
// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public record BufferedPacket(NetworkBuffer buffer,
                             // Début d'une méthode/d'un bloc
                             long index, long length) implements SendablePacket {
    // Début d'une méthode/d'un bloc
    public BufferedPacket {
        // Appelle une méthode
        buffer.readOnly();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
