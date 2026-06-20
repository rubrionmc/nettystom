// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;

// Déclaration de type (classe/interface/enum/record)
public record ClientSteerBoatPacket(boolean leftPaddleTurning, boolean rightPaddleTurning) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientSteerBoatPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BOOLEAN, ClientSteerBoatPacket::leftPaddleTurning,
            // Instruction de code
            BOOLEAN, ClientSteerBoatPacket::rightPaddleTurning,
            // Instruction de code
            ClientSteerBoatPacket::new);
// Fin d'un bloc/d'une expression
}
