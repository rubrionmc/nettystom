// Package declaration for this file
package net.minestom.server.network.packet.server.common;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.UUID;

// Type declaration (class/interface/enum/record)
public record ResourcePackPopPacket(@Nullable UUID id) implements ServerPacket.Configuration, ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<ResourcePackPopPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.UUID.optional(), ResourcePackPopPacket::id,
            // Code statement
            ResourcePackPopPacket::new);
// End of a block/expression
}
