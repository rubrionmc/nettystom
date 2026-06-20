// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.LightData;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record UpdateLightPacket(int chunkX, int chunkZ,
                                // Start of a method/block
                                LightData lightData) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<UpdateLightPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, UpdateLightPacket::chunkX,
            // Code statement
            VAR_INT, UpdateLightPacket::chunkZ,
            // Code statement
            LightData.NETWORK_TYPE, UpdateLightPacket::lightData,
            // Code statement
            UpdateLightPacket::new
    // End of a block/expression
    );
// End of a block/expression
}
