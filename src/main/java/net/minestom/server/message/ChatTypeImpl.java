// Package declaration for this file
package net.minestom.server.message;

// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
record ChatTypeImpl(
        // Code statement
        ChatTypeDecoration chat,
        // Code statement
        ChatTypeDecoration narration
// Start of a method/block
) implements ChatType {

    // Start of a method/block
    ChatTypeImpl {
        // Calls a method
        Objects.requireNonNull(chat, "missing chat");
        // Calls a method
        Objects.requireNonNull(narration, "missing narration");
    // End of a block/expression
    }

// End of a block/expression
}
