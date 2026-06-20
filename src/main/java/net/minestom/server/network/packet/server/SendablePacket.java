// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server;

// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Represents a packet that can be sent to a {@link PlayerConnection}.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface SendablePacket
        // Début d'une méthode/d'un bloc
        permits BufferedPacket, CachedPacket, FramedPacket, LazyPacket, ServerPacket {

    // Début d'une méthode/d'un bloc
    static @Nullable ServerPacket extractServerPacket(ConnectionState state, SendablePacket packet) {
        // Renvoie une valeur à l'appelant
        return switch (packet) {
            // Embranchement multiple (switch/case)
            case ServerPacket serverPacket -> serverPacket;
            // Embranchement multiple (switch/case)
            case CachedPacket cachedPacket -> cachedPacket.packet(state);
            // Embranchement multiple (switch/case)
            case FramedPacket framedPacket -> framedPacket.packet();
            // Embranchement multiple (switch/case)
            case LazyPacket lazyPacket -> lazyPacket.packet();
            // Embranchement multiple (switch/case)
            case BufferedPacket bufferedPacket -> null;
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
