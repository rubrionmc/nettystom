// Package declaration for this file
package net.minestom.server.adventure;

// Import of a required class
import net.kyori.adventure.text.event.DataComponentValue;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
record MinestomDataComponentValueImpl(@Nullable Object value) implements MinestomDataComponentValue {
    // Type declaration (class/interface/enum/record)
    record Removed() implements MinestomDataComponentValue, DataComponentValue.Removed {
        // Calls a method
        static final MinestomDataComponentValue INSTANCE = new MinestomDataComponentValueImpl.Removed();

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable Object value() {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
