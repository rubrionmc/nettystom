// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Static import of a member
import static net.minestom.server.network.packet.client.play.ClientPlayerPositionPacket.FLAG_HORIZONTAL_COLLISION;
// Static import of a member
import static net.minestom.server.network.packet.client.play.ClientPlayerPositionPacket.FLAG_ON_GROUND;

// Type declaration (class/interface/enum/record)
public record ClientPlayerPositionStatusPacket(byte flags) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientPlayerPositionStatusPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BYTE, ClientPlayerPositionStatusPacket::flags,
            // Code statement
            ClientPlayerPositionStatusPacket::new);

    // Start of a method/block
    public ClientPlayerPositionStatusPacket(boolean onGround, boolean horizontalCollision) {
        // Code statement
        this((byte) ((onGround ? FLAG_ON_GROUND : 0) |
                // Calls a method
                (byte) (horizontalCollision ? FLAG_HORIZONTAL_COLLISION : 0)));
    // End of a block/expression
    }

    // Start of a method/block
    public boolean onGround() {
        // Returns a value to the caller
        return (flags & FLAG_ON_GROUND) != 0;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean horizontalCollision() {
        // Returns a value to the caller
        return (flags & FLAG_HORIZONTAL_COLLISION) != 0;
    // End of a block/expression
    }

// End of a block/expression
}
