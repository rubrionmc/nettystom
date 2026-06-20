// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.data.WorldPos;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.FLOAT;

// Type declaration (class/interface/enum/record)
public record SpawnPositionPacket(WorldPos worldPos, float yaw, float pitch) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<SpawnPositionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            WorldPos.NETWORK_TYPE, SpawnPositionPacket::worldPos,
            // Code statement
            FLOAT, SpawnPositionPacket::yaw,
            // Code statement
            FLOAT, SpawnPositionPacket::pitch,
            // Code statement
            SpawnPositionPacket::new);
// End of a block/expression
}
