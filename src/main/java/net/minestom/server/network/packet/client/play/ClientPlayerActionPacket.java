// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.block.BlockFace;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ClientPlayerActionPacket(
        // Code statement
        Status status, Point blockPosition,
        // Code statement
        BlockFace blockFace, int sequence
// Start of a method/block
) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientPlayerActionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.Enum(Status.class), ClientPlayerActionPacket::status,
            // Code statement
            BLOCK_POSITION, ClientPlayerActionPacket::blockPosition,
            // Code statement
            BYTE.transform(aByte -> BlockFace.values()[aByte], blockFace1 -> (byte) blockFace1.ordinal()), ClientPlayerActionPacket::blockFace,
            // Code statement
            VAR_INT, ClientPlayerActionPacket::sequence,
            // Code statement
            ClientPlayerActionPacket::new);

    // Type declaration (class/interface/enum/record)
    public enum Status {
        // Code statement
        STARTED_DIGGING,
        // Code statement
        CANCELLED_DIGGING,
        // Code statement
        FINISHED_DIGGING,
        // Code statement
        DROP_ITEM_STACK,
        // Code statement
        DROP_ITEM,
        // Code statement
        UPDATE_ITEM_STATE,
        // Code statement
        SWAP_ITEM_HAND,
        // Code statement
        STAB,
    // End of a block/expression
    }
// End of a block/expression
}
