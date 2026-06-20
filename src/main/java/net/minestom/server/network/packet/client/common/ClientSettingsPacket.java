// Package declaration for this file
package net.minestom.server.network.packet.client.common;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.player.ClientSettings;

// Type declaration (class/interface/enum/record)
public record ClientSettingsPacket(ClientSettings settings) implements ClientPacket.Configuration, ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientSettingsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            ClientSettings.NETWORK_TYPE, ClientSettingsPacket::settings,
            // Code statement
            ClientSettingsPacket::new);
// End of a block/expression
}
