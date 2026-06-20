// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.function.Supplier;

/**
 * Represents a packet that is lazily allocated. Potentially in a different thread.
 * <p>
 * Supplier must be thread-safe.
 */
// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class LazyPacket implements SendablePacket {
    // Instruction de code
    private final Supplier<ServerPacket> packetSupplier;
    // Instruction de code
    private volatile ServerPacket packet;

    // Début d'une méthode/d'un bloc
    public LazyPacket(Supplier<ServerPacket> packetSupplier) {
        // Accès à l'objet courant/parent
        this.packetSupplier = packetSupplier;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ServerPacket packet() {
        // Affecte une valeur
        ServerPacket packet = this.packet;
        // Embranchement : vérifie une condition
        if (packet == null) {
            // Début d'une méthode/d'un bloc
            synchronized (this) {
                // Affecte une valeur
                packet = this.packet;
                // Embranchement : vérifie une condition
                if (packet == null) this.packet = packet = packetSupplier.get();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return packet;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
