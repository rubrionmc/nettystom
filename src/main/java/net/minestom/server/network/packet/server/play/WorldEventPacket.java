// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record WorldEventPacket(int effectId, Point position, int data,
                               // Début d'une méthode/d'un bloc
                               boolean disableRelativeVolume) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<WorldEventPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            INT, WorldEventPacket::effectId,
            // Instruction de code
            BLOCK_POSITION, WorldEventPacket::position,
            // Instruction de code
            INT, WorldEventPacket::data,
            // Instruction de code
            BOOLEAN, WorldEventPacket::disableRelativeVolume,
            // Instruction de code
            WorldEventPacket::new);
// Fin d'un bloc/d'une expression
}
