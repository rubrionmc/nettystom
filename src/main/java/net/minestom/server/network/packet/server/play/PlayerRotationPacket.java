// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Type declaration (class/interface/enum/record)
public record PlayerRotationPacket(
        // Code statement
        float yaw,
        // Code statement
        boolean relativeYaw,
        // Code statement
        float pitch,
        // Code statement
        boolean relativePitch
// Start of a method/block
) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<PlayerRotationPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.FLOAT, PlayerRotationPacket::yaw,
            // Code statement
            NetworkBuffer.BOOLEAN, PlayerRotationPacket::relativeYaw,
            // Code statement
            NetworkBuffer.FLOAT, PlayerRotationPacket::pitch,
            // Code statement
            NetworkBuffer.BOOLEAN, PlayerRotationPacket::relativePitch,
            // Code statement
            PlayerRotationPacket::new);
// End of a block/expression
}
