// Package declaration for this file
package net.minestom.server.network.packet.server.common;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record CookieRequestPacket(String key) implements
        // Start of a method/block
        ServerPacket.Login, ServerPacket.Configuration, ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<CookieRequestPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING, CookieRequestPacket::key,
            // Code statement
            CookieRequestPacket::new);
// End of a block/expression
}
