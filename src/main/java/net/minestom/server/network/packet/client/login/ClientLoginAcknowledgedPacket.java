// Package declaration for this file
package net.minestom.server.network.packet.client.login;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Type declaration (class/interface/enum/record)
public record ClientLoginAcknowledgedPacket() implements ClientPacket.Login {
    // Calls a method
    public static final NetworkBuffer.Type<ClientLoginAcknowledgedPacket> SERIALIZER = NetworkBufferTemplate.template(new ClientLoginAcknowledgedPacket());
// End of a block/expression
}
