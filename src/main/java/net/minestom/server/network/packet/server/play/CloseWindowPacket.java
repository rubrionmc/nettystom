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
public record CloseWindowPacket(int windowId) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<CloseWindowPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, CloseWindowPacket::windowId,
            // Instruction de code
            CloseWindowPacket::new);
// Fin d'un bloc/d'une expression
}
