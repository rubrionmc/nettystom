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
public record WorldBorderWarningReachPacket(int warningBlocks) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<WorldBorderWarningReachPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, WorldBorderWarningReachPacket::warningBlocks,
            // Instruction de code
            WorldBorderWarningReachPacket::new);
// Fin d'un bloc/d'une expression
}
