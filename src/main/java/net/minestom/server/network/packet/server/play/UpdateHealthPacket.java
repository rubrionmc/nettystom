// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.FLOAT;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record UpdateHealthPacket(float health, int food, float foodSaturation) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<UpdateHealthPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            FLOAT, UpdateHealthPacket::health,
            // Code statement
            VAR_INT, UpdateHealthPacket::food,
            // Code statement
            FLOAT, UpdateHealthPacket::foodSaturation,
            // Code statement
            UpdateHealthPacket::new);
// End of a block/expression
}
