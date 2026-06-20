// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.BYTE;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.FLOAT;

// Type declaration (class/interface/enum/record)
public record PlayerAbilitiesPacket(byte flags, float flyingSpeed, float walkingSpeed) implements ServerPacket.Play {
    // Assigns a value
    public static final byte FLAG_INVULNERABLE = 0x01;
    // Assigns a value
    public static final byte FLAG_FLYING = 0x02;
    // Assigns a value
    public static final byte FLAG_ALLOW_FLYING = 0x04;
    // Assigns a value
    public static final byte FLAG_INSTANT_BREAK = 0x08;

    // Assigns a value
    public static final NetworkBuffer.Type<PlayerAbilitiesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            BYTE, PlayerAbilitiesPacket::flags,
            // Code statement
            FLOAT, PlayerAbilitiesPacket::flyingSpeed,
            // Code statement
            FLOAT, PlayerAbilitiesPacket::walkingSpeed,
            // Code statement
            PlayerAbilitiesPacket::new);
// End of a block/expression
}
