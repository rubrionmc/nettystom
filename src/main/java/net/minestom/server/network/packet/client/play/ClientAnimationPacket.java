// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerHand;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Déclaration de type (classe/interface/enum/record)
public record ClientAnimationPacket(PlayerHand hand) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientAnimationPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.Enum(PlayerHand.class), ClientAnimationPacket::hand,
            // Instruction de code
            ClientAnimationPacket::new);
// Fin d'un bloc/d'une expression
}
