// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record EntityPositionPacket(int entityId, short deltaX, short deltaY, short deltaZ, boolean onGround)
        // Start of a method/block
        implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<EntityPositionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, EntityPositionPacket::entityId,
            // Code statement
            SHORT, EntityPositionPacket::deltaX,
            // Code statement
            SHORT, EntityPositionPacket::deltaY,
            // Code statement
            SHORT, EntityPositionPacket::deltaZ,
            // Code statement
            BOOLEAN, EntityPositionPacket::onGround,
            // Code statement
            EntityPositionPacket::new);

    // Code statement
    public static EntityPositionPacket getPacket(int entityId,
                                                 // Code statement
                                                 Pos newPosition, Pos oldPosition,
                                                 // Start of a method/block
                                                 boolean onGround) {
        // Calls a method
        final short deltaX = CoordConversion.deltaShort4096(newPosition.x(), oldPosition.x());
        // Calls a method
        final short deltaY = CoordConversion.deltaShort4096(newPosition.y(), oldPosition.y());
        // Calls a method
        final short deltaZ = CoordConversion.deltaShort4096(newPosition.z(), oldPosition.z());
        // Returns a value to the caller
        return new EntityPositionPacket(entityId, deltaX, deltaY, deltaZ, onGround);
    // End of a block/expression
    }
// End of a block/expression
}
