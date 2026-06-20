// Package declaration for this file
package net.minestom.server.adventure;

// Import of a required class
import net.kyori.adventure.text.event.DataComponentValue;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public sealed interface MinestomDataComponentValue extends DataComponentValue permits MinestomDataComponentValueImpl, MinestomDataComponentValueImpl.Removed {

    // Start of a method/block
    static MinestomDataComponentValue removed() {
        // Returns a value to the caller
        return MinestomDataComponentValueImpl.Removed.INSTANCE;
    // End of a block/expression
    }

    // Start of a method/block
    static MinestomDataComponentValue dataComponentValue(final @Nullable Object data) {
        // Returns a value to the caller
        return new MinestomDataComponentValueImpl(data);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable Object value();
// End of a block/expression
}
