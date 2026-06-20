// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.INT;

// Type declaration (class/interface/enum/record)
public record EntityStatusPacket(int entityId, byte status) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<EntityStatusPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            INT, EntityStatusPacket::entityId,
            // Code statement
            BYTE, EntityStatusPacket::status,
            // Code statement
            EntityStatusPacket::new);
// End of a block/expression
}
