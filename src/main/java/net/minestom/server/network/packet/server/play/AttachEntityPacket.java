// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.INT;

// Type declaration (class/interface/enum/record)
public record AttachEntityPacket(int attachedEntityId, int holdingEntityId) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<AttachEntityPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            INT, AttachEntityPacket::attachedEntityId,
            // Code statement
            INT, AttachEntityPacket::holdingEntityId,
            // Code statement
            AttachEntityPacket::new);
// End of a block/expression
}
