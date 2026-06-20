// Package declaration for this file
package net.minestom.server.adventure.serializer.nbt;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.text.event.DataComponentValue;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
record NbtDataComponentValueImpl(@Nullable BinaryTag value) implements NbtDataComponentValue {
    // Type declaration (class/interface/enum/record)
    record Removed() implements DataComponentValue.Removed, NbtDataComponentValue {
        // Calls a method
        static final NbtDataComponentValueImpl.Removed INSTANCE = new NbtDataComponentValueImpl.Removed();

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable BinaryTag value() {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
