// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientUpdateCommandBlockPacket(Point blockPosition, String command,
                                             // Début d'une méthode/d'un bloc
                                             Mode mode, byte flags) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientUpdateCommandBlockPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            BLOCK_POSITION, ClientUpdateCommandBlockPacket::blockPosition,
            // Instruction de code
            STRING, ClientUpdateCommandBlockPacket::command,
            // Instruction de code
            Enum(Mode.class), ClientUpdateCommandBlockPacket::mode,
            // Instruction de code
            BYTE, ClientUpdateCommandBlockPacket::flags,
            // Instruction de code
            ClientUpdateCommandBlockPacket::new);

    // Déclaration de type (classe/interface/enum/record)
    public enum Mode {
        // Instruction de code
        SEQUENCE, AUTO, REDSTONE
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
