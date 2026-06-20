// Package declaration for this file
package net.minestom.server.adventure.provider;

// Import of a required class
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
// Import of a required class
import net.kyori.adventure.text.serializer.json.JSONOptions;

// Import of a required class
import java.util.function.Consumer;

// Annotation for the following element
@SuppressWarnings("UnstableApiUsage") // we are permitted to provide this
// Type declaration (class/interface/enum/record)
public final class MinestomGsonComponentSerializerProvider implements GsonComponentSerializer.Provider {
    // Annotation for the following element
    @Override
    // Start of a method/block
    public GsonComponentSerializer gson() {
        // Returns a value to the caller
        return GsonComponentSerializer.builder()
                // Code statement
                .legacyHoverEventSerializer(NBTLegacyHoverEventSerializer.INSTANCE)
                // Calls a method
                .build();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public GsonComponentSerializer gsonLegacy() {
        // Returns a value to the caller
        return GsonComponentSerializer.builder()
                // Code statement
                .legacyHoverEventSerializer(NBTLegacyHoverEventSerializer.INSTANCE)
                // Code statement
                .editOptions(features -> features.value(JSONOptions.EMIT_RGB, false))
                // Calls a method
                .build();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Consumer<GsonComponentSerializer.Builder> builder() {
        // Returns a value to the caller
        return _ -> {}; // we don't need to touch the builder here
    // End of a block/expression
    }
// End of a block/expression
}
