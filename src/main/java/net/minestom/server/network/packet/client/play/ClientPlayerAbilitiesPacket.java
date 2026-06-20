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

// Type declaration (class/interface/enum/record)
public record ClientPlayerAbilitiesPacket(byte flags) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientPlayerAbilitiesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BYTE, ClientPlayerAbilitiesPacket::flags,
            // Code statement
            ClientPlayerAbilitiesPacket::new);
// End of a block/expression
}
