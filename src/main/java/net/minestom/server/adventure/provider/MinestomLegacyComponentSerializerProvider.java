// Package declaration for this file
package net.minestom.server.adventure.provider;

// Import of a required class
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

// Import of a required class
import java.util.function.Consumer;

// Annotation for the following element
@SuppressWarnings("UnstableApiUsage") // we are permitted to provide this
// Type declaration (class/interface/enum/record)
public final class MinestomLegacyComponentSerializerProvider implements LegacyComponentSerializer.Provider {
    // Annotation for the following element
    @Override
    // Start of a method/block
    public LegacyComponentSerializer legacyAmpersand() {
        // Returns a value to the caller
        return LegacyComponentSerializer.builder()
                // Code statement
                .character(LegacyComponentSerializer.AMPERSAND_CHAR)
                // Code statement
                .flattener(MinestomFlattenerProvider.INSTANCE)
                // Calls a method
                .build();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public LegacyComponentSerializer legacySection() {
        // Returns a value to the caller
        return LegacyComponentSerializer.builder()
                // Code statement
                .character(LegacyComponentSerializer.SECTION_CHAR)
                // Code statement
                .flattener(MinestomFlattenerProvider.INSTANCE)
                // Calls a method
                .build();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Consumer<LegacyComponentSerializer.Builder> legacy() {
        // we will provide our flattener to allow for custom translations/etc
        // Returns a value to the caller
        return builder -> builder.flattener(MinestomFlattenerProvider.INSTANCE);
    // End of a block/expression
    }
// End of a block/expression
}
