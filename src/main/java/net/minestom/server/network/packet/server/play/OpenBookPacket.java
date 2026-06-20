// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;

// Déclaration de type (classe/interface/enum/record)
public record OpenBookPacket(PlayerHand hand) implements ServerPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<OpenBookPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.Enum(PlayerHand.class), OpenBookPacket::hand,
            // Instruction de code
            OpenBookPacket::new);
// Fin d'un bloc/d'une expression
}
