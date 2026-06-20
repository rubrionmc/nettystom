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
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record CustomChatCompletionPacket(Action action,
                                         // Start of a method/block
                                         List<String> entries) implements ServerPacket.Play {
    // Assigns a value
    public static final int MAX_ENTRIES = Short.MAX_VALUE;

    // Assigns a value
    public static final NetworkBuffer.Type<CustomChatCompletionPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.Enum(Action.class), CustomChatCompletionPacket::action,
            // Code statement
            STRING.list(MAX_ENTRIES), CustomChatCompletionPacket::entries,
            // Code statement
            CustomChatCompletionPacket::new);

    // Start of a method/block
    public CustomChatCompletionPacket {
        // Calls a method
        entries = List.copyOf(entries);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Action {
        // Code statement
        ADD, REMOVE, SET
    // End of a block/expression
    }
// End of a block/expression
}
