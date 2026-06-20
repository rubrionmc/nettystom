// Package declaration for this file
package net.minestom.server.adventure.provider;

// Import of a required class
import java.util.function.Consumer;

// Import of a required class
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;

// Annotation for the following element
@SuppressWarnings("UnstableApiUsage") // we are permitted to provide this
// Type declaration (class/interface/enum/record)
public final class MinestomAnsiComponentSerializerProvider implements ANSIComponentSerializer.Provider {
    // Annotation for the following element
    @Override
    // Start of a method/block
    public ANSIComponentSerializer ansi() {
        // Calls a method
        final ANSIComponentSerializer.Builder builder = ANSIComponentSerializer.builder();
        // Access to the current/parent object
        this.builder().accept(builder);
        // Returns a value to the caller
        return builder.build();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Consumer<ANSIComponentSerializer.Builder> builder() {
        // Returns a value to the caller
        return builder -> builder.flattener(MinestomFlattenerProvider.INSTANCE);
    // End of a block/expression
    }
// End of a block/expression
}
