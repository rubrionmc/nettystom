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
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record ClientTabCompletePacket(int transactionId, String text) implements ClientPacket.Play {
    // Assigns a value
    public static final int MAX_TEXT_LENGTH = 32500;

    // Assigns a value
    public static final NetworkBuffer.Type<ClientTabCompletePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, ClientTabCompletePacket::transactionId,
            // Code statement
            STRING, ClientTabCompletePacket::text,
            // Code statement
            ClientTabCompletePacket::new);

    // Start of a method/block
    public ClientTabCompletePacket {
        // Calls a method
        Check.argCondition(text.length() > MAX_TEXT_LENGTH, "Text length cannot be greater than {0}", MAX_TEXT_LENGTH);
    // End of a block/expression
    }
// End of a block/expression
}
