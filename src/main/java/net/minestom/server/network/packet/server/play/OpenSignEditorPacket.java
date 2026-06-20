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
import static net.minestom.server.network.NetworkBuffer.BLOCK_POSITION;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Déclaration de type (classe/interface/enum/record)
public record OpenSignEditorPacket(Point position, boolean isFrontText) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<OpenSignEditorPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BLOCK_POSITION, OpenSignEditorPacket::position,
            // Instruction de code
            BOOLEAN, OpenSignEditorPacket::isFrontText,
            // Instruction de code
            OpenSignEditorPacket::new);
// Fin d'un bloc/d'une expression
}
