// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.login;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record SetCompressionPacket(int threshold) implements ServerPacket.Login {
    // Affecte une valeur
    public static final NetworkBuffer.Type<SetCompressionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, SetCompressionPacket::threshold,
            // Instruction de code
            SetCompressionPacket::new);
// Fin d'un bloc/d'une expression
}
