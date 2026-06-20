// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record SelectAdvancementTabPacket(@Nullable String identifier) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<SelectAdvancementTabPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            STRING.optional(), SelectAdvancementTabPacket::identifier,
            // Code statement
            SelectAdvancementTabPacket::new);
// End of a block/expression
}
