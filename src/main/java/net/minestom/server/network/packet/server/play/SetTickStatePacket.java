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
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.FLOAT;

// Déclaration de type (classe/interface/enum/record)
public record SetTickStatePacket(float tickRate, boolean isFrozen) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<SetTickStatePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            FLOAT, SetTickStatePacket::tickRate,
            // Instruction de code
            BOOLEAN, SetTickStatePacket::isFrozen,
            // Instruction de code
            SetTickStatePacket::new);
// Fin d'un bloc/d'une expression
}
