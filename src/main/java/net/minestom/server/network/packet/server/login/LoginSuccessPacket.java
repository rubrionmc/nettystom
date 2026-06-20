// Package declaration for this file
package net.minestom.server.network.packet.server.login;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.player.GameProfile;

// Type declaration (class/interface/enum/record)
public record LoginSuccessPacket(GameProfile gameProfile) implements ServerPacket.Login {
    // Assigns a value
    public static final NetworkBuffer.Type<LoginSuccessPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            GameProfile.SERIALIZER, LoginSuccessPacket::gameProfile,
            // Code statement
            LoginSuccessPacket::new);
// End of a block/expression
}
