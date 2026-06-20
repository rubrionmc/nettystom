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

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientUseItemPacket(PlayerHand hand, int sequence, float yaw,
                                  // Début d'une méthode/d'un bloc
                                  float pitch) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientUseItemPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Enum(PlayerHand.class), ClientUseItemPacket::hand,
            // Instruction de code
            VAR_INT, ClientUseItemPacket::sequence,
            // Instruction de code
            FLOAT, ClientUseItemPacket::yaw,
            // Instruction de code
            FLOAT, ClientUseItemPacket::pitch,
            // Instruction de code
            ClientUseItemPacket::new);
// Fin d'un bloc/d'une expression
}
