// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.client.play;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.BlockFace;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.*;

// Déclaration de type (classe/interface/enum/record)
public record ClientPlayerActionPacket(
        // Instruction de code
        Status status, Point blockPosition,
        // Instruction de code
        BlockFace blockFace, int sequence
// Début d'une méthode/d'un bloc
) implements ClientPacket {
    // Affecte une valeur
    public static final NetworkBuffer.Type<ClientPlayerActionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.Enum(Status.class), ClientPlayerActionPacket::status,
            // Instruction de code
            BLOCK_POSITION, ClientPlayerActionPacket::blockPosition,
            // Instruction de code
            BYTE.transform(aByte -> BlockFace.values()[aByte], blockFace1 -> (byte) blockFace1.ordinal()), ClientPlayerActionPacket::blockFace,
            // Instruction de code
            VAR_INT, ClientPlayerActionPacket::sequence,
            // Instruction de code
            ClientPlayerActionPacket::new);

    // Déclaration de type (classe/interface/enum/record)
    public enum Status {
        // Instruction de code
        STARTED_DIGGING,
        // Instruction de code
        CANCELLED_DIGGING,
        // Instruction de code
        FINISHED_DIGGING,
        // Instruction de code
        DROP_ITEM_STACK,
        // Instruction de code
        DROP_ITEM,
        // Instruction de code
        UPDATE_ITEM_STATE,
        // Instruction de code
        SWAP_ITEM_HAND,
        // Instruction de code
        STAB,
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
