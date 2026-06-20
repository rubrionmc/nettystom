// Package declaration for this file
package net.minestom.server.adventure.serializer.nbt;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.text.event.DataComponentValue;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public sealed interface NbtDataComponentValue extends DataComponentValue permits NbtDataComponentValueImpl, NbtDataComponentValueImpl.Removed {

    // Start of a method/block
    static NbtDataComponentValue removed() {
        // Returns a value to the caller
        return NbtDataComponentValueImpl.Removed.INSTANCE;
    // End of a block/expression
    }

    // Start of a method/block
    static NbtDataComponentValue nbtDataComponentValue(BinaryTag tag) {
        // Returns a value to the caller
        return new NbtDataComponentValueImpl(tag);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable BinaryTag value();
// End of a block/expression
}
