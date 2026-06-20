// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.*;

// Type declaration (class/interface/enum/record)
public record ClientUpdateCommandBlockPacket(Point blockPosition, String command,
                                             // Start of a method/block
                                             Mode mode, byte flags) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientUpdateCommandBlockPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BLOCK_POSITION, ClientUpdateCommandBlockPacket::blockPosition,
            // Code statement
            STRING, ClientUpdateCommandBlockPacket::command,
            // Code statement
            Enum(Mode.class), ClientUpdateCommandBlockPacket::mode,
            // Code statement
            BYTE, ClientUpdateCommandBlockPacket::flags,
            // Code statement
            ClientUpdateCommandBlockPacket::new);

    // Start of a method/block
    public ClientUpdateCommandBlockPacket {
        // Calls a method
        Check.argCondition(command.length() > Short.MAX_VALUE, "Command length cannot be greater than Short.MAX_VALUE");
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Mode {
        // Code statement
        SEQUENCE, AUTO, REDSTONE
    // End of a block/expression
    }
// End of a block/expression
}
