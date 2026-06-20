// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.UUID;

// Type declaration (class/interface/enum/record)
public record PlayerInfoRemovePacket(List<UUID> uuids) implements ServerPacket.Play {
    // Assigns a value
    public static final int MAX_ENTRIES = 1024;

    // Assigns a value
    public static final NetworkBuffer.Type<PlayerInfoRemovePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.UUID.list(MAX_ENTRIES), PlayerInfoRemovePacket::uuids,
            // Code statement
            PlayerInfoRemovePacket::new);

    // Start of a method/block
    public PlayerInfoRemovePacket(UUID uuid) {
        // Calls a method
        this(List.of(uuid));
    // End of a block/expression
    }

    // Start of a method/block
    public PlayerInfoRemovePacket {
        // Calls a method
        uuids = List.copyOf(uuids);
    // End of a block/expression
    }
// End of a block/expression
}
