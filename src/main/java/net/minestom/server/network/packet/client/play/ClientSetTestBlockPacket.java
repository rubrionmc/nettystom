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

// Type declaration (class/interface/enum/record)
public record ClientSetTestBlockPacket(
        // Code statement
        Point blockPosition,
        // Code statement
        TestBlockMode mode,
        // Code statement
        String message
// Start of a method/block
) implements ClientPacket.Play {

    // Assigns a value
    public static final NetworkBuffer.Type<ClientSetTestBlockPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.BLOCK_POSITION, ClientSetTestBlockPacket::blockPosition,
            // Code statement
            TestBlockMode.NETWORK_TYPE, ClientSetTestBlockPacket::mode,
            // Code statement
            NetworkBuffer.STRING, ClientSetTestBlockPacket::message,
            // Code statement
            ClientSetTestBlockPacket::new);

    // Start of a method/block
    public ClientSetTestBlockPacket {
        // Calls a method
        Check.argCondition(message.length() > Short.MAX_VALUE, "Message length cannot be greater than Short.MAX_VALUE");
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum TestBlockMode {
        // Code statement
        START,
        // Code statement
        LOG,
        // Code statement
        FAIL,
        // Code statement
        ACCEPT;

        // Calls a method
        public static final NetworkBuffer.Type<TestBlockMode> NETWORK_TYPE = NetworkBuffer.Enum(TestBlockMode.class);
    // End of a block/expression
    }
// End of a block/expression
}
