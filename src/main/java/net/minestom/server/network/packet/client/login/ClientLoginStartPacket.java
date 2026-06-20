// Package declaration for this file
package net.minestom.server.network.packet.client.login;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Import of a required class
import java.util.UUID;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.UUID;

// Type declaration (class/interface/enum/record)
public record ClientLoginStartPacket(String username,
                                     // Start of a method/block
                                     UUID profileId) implements ClientPacket.Login {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientLoginStartPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, ClientLoginStartPacket::username,
            // Code statement
            UUID, ClientLoginStartPacket::profileId,
            // Code statement
            ClientLoginStartPacket::new);

    // Start of a method/block
    public ClientLoginStartPacket {
        // Branch: checks a condition
        if (username.length() > 16)
            // Throws an exception
            throw new IllegalArgumentException("Username is not allowed to be longer than 16 characters");
    // End of a block/expression
    }
// End of a block/expression
}
