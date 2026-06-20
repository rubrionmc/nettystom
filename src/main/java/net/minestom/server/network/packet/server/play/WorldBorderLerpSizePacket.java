// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.DOUBLE;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_LONG;

// Déclaration de type (classe/interface/enum/record)
public record WorldBorderLerpSizePacket(double oldDiameter, double newDiameter,
                                        // Début d'une méthode/d'un bloc
                                        long speed) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<WorldBorderLerpSizePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            DOUBLE, WorldBorderLerpSizePacket::oldDiameter,
            // Instruction de code
            DOUBLE, WorldBorderLerpSizePacket::newDiameter,
            // Instruction de code
            VAR_LONG, WorldBorderLerpSizePacket::speed,
            // Instruction de code
            WorldBorderLerpSizePacket::new);
// Fin d'un bloc/d'une expression
}
