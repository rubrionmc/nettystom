// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.INT;

// Déclaration de type (classe/interface/enum/record)
public record SetTitleTimePacket(int fadeIn, int stay, int fadeOut) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<SetTitleTimePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            INT, SetTitleTimePacket::fadeIn,
            // Instruction de code
            INT, SetTitleTimePacket::stay,
            // Instruction de code
            INT, SetTitleTimePacket::fadeOut,
            // Instruction de code
            SetTitleTimePacket::new);
// Fin d'un bloc/d'une expression
}
