// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.BOOLEAN;
// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.POS;

// Déclaration de type (classe/interface/enum/record)
public record ClientVehicleMovePacket(Pos position, boolean onGround) implements ClientPacket.Play {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientVehicleMovePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            POS, ClientVehicleMovePacket::position,
            // Instruction de code
            BOOLEAN, ClientVehicleMovePacket::onGround,
            // Instruction de code
            ClientVehicleMovePacket::new);
// Fin d'un bloc/d'une expression
}
