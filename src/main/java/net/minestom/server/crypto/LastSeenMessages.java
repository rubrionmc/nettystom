// Package declaration for this file
package net.minestom.server.crypto;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.BitSet;
// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.FixedBitSet;
// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record LastSeenMessages(List<MessageSignature> entries) {
    // Assigns a value
    public static final int MAX_ENTRIES = 20;

    // Start of a method/block
    public LastSeenMessages {
        // Calls a method
        entries = List.copyOf(entries);
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<LastSeenMessages> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            MessageSignature.SERIALIZER.list(MAX_ENTRIES), LastSeenMessages::entries,
            // Code statement
            LastSeenMessages::new
    // End of a block/expression
    );

    // Type declaration (class/interface/enum/record)
    public record Packed(List<MessageSignature.Packed> entries) {
        // Calls a method
        public static final Packed EMPTY = new Packed(List.of());

        // Assigns a value
        public static final NetworkBuffer.Type<Packed> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                MessageSignature.Packed.SERIALIZER.list(MAX_ENTRIES), Packed::entries,
                // Code statement
                Packed::new
        // End of a block/expression
        );

        // Start of a method/block
        public Packed {
            // Calls a method
            entries = List.copyOf(entries);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Update(int offset, BitSet acknowledged) {
        // Assigns a value
        public static final NetworkBuffer.Type<Update> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                VAR_INT, Update::offset,
                // Code statement
                FixedBitSet(20), Update::acknowledged,
                // Code statement
                Update::new
        // End of a block/expression
        );

        // Start of a method/block
        public Update {
            // Calls a method
            acknowledged = (BitSet) acknowledged.clone();
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
