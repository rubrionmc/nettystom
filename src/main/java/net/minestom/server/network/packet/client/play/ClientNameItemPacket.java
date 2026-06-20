// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record ClientNameItemPacket(String itemName) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientNameItemPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, ClientNameItemPacket::itemName,
            // Code statement
            ClientNameItemPacket::new);

    // Start of a method/block
    public ClientNameItemPacket {
        // Calls a method
        Check.argCondition(itemName.length() > Short.MAX_VALUE, "Item name cannot be longer than Short.MAX_SIZE");
    // End of a block/expression
    }
// End of a block/expression
}
