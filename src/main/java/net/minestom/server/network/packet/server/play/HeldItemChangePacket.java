// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BYTE;

// Déclaration de type (classe/interface/enum/record)
public record HeldItemChangePacket(byte slot) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<HeldItemChangePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BYTE, HeldItemChangePacket::slot,
            // Instruction de code
            HeldItemChangePacket::new);
// Fin d'un bloc/d'une expression
}
