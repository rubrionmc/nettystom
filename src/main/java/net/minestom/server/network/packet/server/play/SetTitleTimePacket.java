// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.INT;

// Type declaration (class/interface/enum/record)
public record SetTitleTimePacket(int fadeIn, int stay, int fadeOut) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<SetTitleTimePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            INT, SetTitleTimePacket::fadeIn,
            // Code statement
            INT, SetTitleTimePacket::stay,
            // Code statement
            INT, SetTitleTimePacket::fadeOut,
            // Code statement
            SetTitleTimePacket::new);
// End of a block/expression
}
