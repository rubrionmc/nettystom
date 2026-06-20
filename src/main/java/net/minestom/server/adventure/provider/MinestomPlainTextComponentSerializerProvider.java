// Package declaration for this file
package net.minestom.server.adventure.provider;

// Import of a required class
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

// Import of a required class
import java.util.function.Consumer;

// Annotation for the following element
@SuppressWarnings("UnstableApiUsage") // we are permitted to provide this
// Type declaration (class/interface/enum/record)
public final class MinestomPlainTextComponentSerializerProvider implements PlainTextComponentSerializer.Provider {
    // Annotation for the following element
    @Override
    // Start of a method/block
    public PlainTextComponentSerializer plainTextSimple() {
        // Returns a value to the caller
        return PlainTextComponentSerializer.builder()
                // Code statement
                .flattener(MinestomFlattenerProvider.INSTANCE)
                // Calls a method
                .build();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Consumer<PlainTextComponentSerializer.Builder> plainText() {
        // we will provide our flattener to allow for custom translations/etc
        // Returns a value to the caller
        return builder -> builder.flattener(MinestomFlattenerProvider.INSTANCE);
    // End of a block/expression
    }
// End of a block/expression
}
