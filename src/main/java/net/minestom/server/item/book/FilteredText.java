// Package declaration for this file
package net.minestom.server.item.book;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public record FilteredText<T>(T text, @Nullable T filtered) {

    // Calls a method
    public static NetworkBuffer.Type<FilteredText<String>> STRING_NETWORK_TYPE = createNetworkType(NetworkBuffer.STRING);
    // Calls a method
    public static Codec<FilteredText<String>> STRING_CODEC = createCodec(Codec.STRING);

    // Calls a method
    public static NetworkBuffer.Type<FilteredText<Component>> COMPONENT_NETWORK_TYPE = createNetworkType(NetworkBuffer.COMPONENT);
    // Calls a method
    public static Codec<FilteredText<Component>> COMPONENT_CODEC = createCodec(Codec.COMPONENT);

    // Start of a method/block
    private static <T> NetworkBuffer.Type<FilteredText<T>> createNetworkType(NetworkBuffer.Type<T> inner) {
        // Returns a value to the caller
        return NetworkBufferTemplate.template(
                // Code statement
                inner, FilteredText::text,
                // Code statement
                inner.optional(), FilteredText::filtered,
                // Code statement
                FilteredText::new);
    // End of a block/expression
    }

    // Start of a method/block
    private static <T> Codec<FilteredText<T>> createCodec(Codec<T> inner) {
        // Returns a value to the caller
        return StructCodec.struct(
                // Code statement
                "raw", inner, FilteredText::text,
                // Code statement
                "filtered", inner.optional(), FilteredText::filtered,
                // Code statement
                FilteredText::new);
    // End of a block/expression
    }
// End of a block/expression
}
