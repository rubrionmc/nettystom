// Package declaration for this file
package net.minestom.server.network.packet.client.handshake;

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
public record ClientHandshakePacket(int protocolVersion, String serverAddress,
                                    // Start of a method/block
                                    int serverPort, Intent intent) implements ClientPacket.Handshake {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientHandshakePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientHandshakePacket::protocolVersion,
            // Code statement
            STRING, ClientHandshakePacket::serverAddress,
            // Code statement
            UNSIGNED_SHORT, ClientHandshakePacket::serverPort,
            // Code statement
            VAR_INT.transform(Intent::fromId, Intent::id), ClientHandshakePacket::intent,
            // Code statement
            ClientHandshakePacket::new);

    // Start of a method/block
    public ClientHandshakePacket {
        //TODO, while this is dependent on Auth the max default is 255, bungee guard could be up to MAX_VALUE (we do check in the listener)
        // Calls a method
        Check.argCondition(serverAddress.length() > Short.MAX_VALUE, "Server address length cannot be greater than {0}", Short.MAX_VALUE);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Intent {
        // Code statement
        STATUS,
        // Code statement
        LOGIN,
        // Code statement
        TRANSFER;

        // Start of a method/block
        public static Intent fromId(int id) {
            // Returns a value to the caller
            return switch (id) {
                // Multiple branching (switch/case)
                case 1 -> STATUS;
                // Multiple branching (switch/case)
                case 2 -> LOGIN;
                // Multiple branching (switch/case)
                case 3 -> TRANSFER;
                // Multiple branching (switch/case)
                default -> throw new IllegalArgumentException("Unknown connection intent: " + id);
            // End of a block/expression
            };
        // End of a block/expression
        }

        // Start of a method/block
        public int id() {
            // Returns a value to the caller
            return ordinal() + 1;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
