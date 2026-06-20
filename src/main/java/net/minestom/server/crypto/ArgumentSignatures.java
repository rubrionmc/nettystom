// Package declaration for this file
package net.minestom.server.crypto;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record ArgumentSignatures(List<Entry> entries) {
    // Assigns a value
    public static final int MAX_ENTRIES = 8;

    // Start of a method/block
    public ArgumentSignatures {
        // Calls a method
        entries = List.copyOf(entries);
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<ArgumentSignatures> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Entry.SERIALIZER.list(MAX_ENTRIES), ArgumentSignatures::entries,
            // Code statement
            ArgumentSignatures::new
    // End of a block/expression
    );

    // Type declaration (class/interface/enum/record)
    public record Entry(String name, MessageSignature signature) {
        // Assigns a value
        public static final NetworkBuffer.Type<Entry> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                STRING, Entry::name,
                // Code statement
                MessageSignature.SERIALIZER, Entry::signature,
                // Code statement
                Entry::new
        // End of a block/expression
        );
    // End of a block/expression
    }
// End of a block/expression
}
