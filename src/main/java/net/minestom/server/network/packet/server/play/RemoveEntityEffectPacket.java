// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.potion.PotionEffect;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record RemoveEntityEffectPacket(int entityId, PotionEffect potionEffect) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<RemoveEntityEffectPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, RemoveEntityEffectPacket::entityId,
            // Code statement
            PotionEffect.NETWORK_TYPE, RemoveEntityEffectPacket::potionEffect,
            // Code statement
            RemoveEntityEffectPacket::new
    // End of a block/expression
    );
// End of a block/expression
}
