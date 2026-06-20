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

// Type declaration (class/interface/enum/record)
public record RecipeBookRemovePacket(List<Integer> displayIds) implements ServerPacket.Play {
    // Assigns a value
    public static final NetworkBuffer.Type<RecipeBookRemovePacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.VAR_INT.list(), RecipeBookRemovePacket::displayIds,
            // Code statement
            RecipeBookRemovePacket::new);

    // Start of a method/block
    public RecipeBookRemovePacket {
        // Calls a method
        displayIds = List.copyOf(displayIds);
    // End of a block/expression
    }

// End of a block/expression
}
