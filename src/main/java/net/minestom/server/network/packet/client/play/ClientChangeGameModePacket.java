// Package declaration for this file
package net.minestom.server.network.packet.client.play;

// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;

// Type declaration (class/interface/enum/record)
public record ClientChangeGameModePacket(GameMode gameMode) implements ClientPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ClientChangeGameModePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            GameMode.NETWORK_TYPE, ClientChangeGameModePacket::gameMode,
            // Code statement
            ClientChangeGameModePacket::new);
// End of a block/expression
}
