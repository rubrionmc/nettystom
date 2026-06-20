// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.potion.Potion;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record EntityEffectPacket(int entityId, Potion potion) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<EntityEffectPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, EntityEffectPacket::entityId,
            // Code statement
            Potion.NETWORK_TYPE, EntityEffectPacket::potion,
            // Code statement
            EntityEffectPacket::new
    // End of a block/expression
    );
// End of a block/expression
}
