// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
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
public record ClientInteractEntityPacket(int targetId, PlayerHand hand, Vec location, boolean usingSecondaryAction) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientInteractEntityPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            VAR_INT, ClientInteractEntityPacket::targetId,
            // Instruction de code
            PlayerHand.NETWORK_TYPE, ClientInteractEntityPacket::hand,
            // Instruction de code
            LP_VECTOR3, ClientInteractEntityPacket::location,
            // Instruction de code
            BOOLEAN, ClientInteractEntityPacket::usingSecondaryAction,
            // Instruction de code
            ClientInteractEntityPacket::new
    // Fin d'un bloc/d'une expression
    );
// Fin d'un bloc/d'une expression
}
