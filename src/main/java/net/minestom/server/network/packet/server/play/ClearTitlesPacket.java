// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Déclaration de type (classe/interface/enum/record)
public record ClearTitlesPacket(boolean reset) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClearTitlesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BOOLEAN, ClearTitlesPacket::reset,
            // Instruction de code
            ClearTitlesPacket::new);
// Fin d'un bloc/d'une expression
}
