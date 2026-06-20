// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record EntityAnimationPacket(int entityId, Animation animation) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<EntityAnimationPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT, EntityAnimationPacket::entityId,
            // Code statement
            NetworkBuffer.Enum(Animation.class), EntityAnimationPacket::animation,
            // Code statement
            EntityAnimationPacket::new
    // End of a block/expression
    );

    // Type declaration (class/interface/enum/record)
    public enum Animation {
        // Code statement
        SWING_MAIN_ARM,
        // Code statement
        TAKE_DAMAGE,
        // Code statement
        LEAVE_BED,
        // Code statement
        SWING_OFF_HAND,
        // Code statement
        CRITICAL_EFFECT,
        // Code statement
        MAGICAL_CRITICAL_EFFECT
    // End of a block/expression
    }
// End of a block/expression
}
