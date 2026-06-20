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

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record DestroyEntitiesPacket(List<Integer> entityIds) implements ServerPacket.Play {
    // Assigns a value
    public static final int MAX_ENTRIES = Short.MAX_VALUE;

    // Assigns a value
    public static final NetworkBuffer.Type<DestroyEntitiesPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            VAR_INT.list(Short.MAX_VALUE), DestroyEntitiesPacket::entityIds,
            // Code statement
            DestroyEntitiesPacket::new);

    // Start of a method/block
    public DestroyEntitiesPacket {
        // Calls a method
        entityIds = List.copyOf(entityIds);
    // End of a block/expression
    }

    // Start of a method/block
    public DestroyEntitiesPacket(int entityId) {
        // Calls a method
        this(List.of(entityId));
    // End of a block/expression
    }
// End of a block/expression
}
