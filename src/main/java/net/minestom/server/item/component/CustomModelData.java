// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.kyori.adventure.util.RGBLike;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.color.Color;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public record CustomModelData(
        // Code statement
        List<Float> floats, List<Boolean> flags,
        // Code statement
        List<String> strings, List<RGBLike> colors
// Start of a method/block
) {
    // Assigns a value
    private static final int MAX_ENTRIES = 256;

    // Assigns a value
    public static final NetworkBuffer.Type<CustomModelData> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.FLOAT.list(MAX_ENTRIES), CustomModelData::floats,
            // Code statement
            NetworkBuffer.BOOLEAN.list(MAX_ENTRIES), CustomModelData::flags,
            // Code statement
            NetworkBuffer.STRING.list(MAX_ENTRIES), CustomModelData::strings,
            // Code statement
            Color.NETWORK_TYPE.list(MAX_ENTRIES), CustomModelData::colors,
            // Code statement
            CustomModelData::new);
    // Assigns a value
    public static final Codec<CustomModelData> CODEC = StructCodec.struct(
            // Code statement
            "floats", Codec.FLOAT.list().optional(List.of()), CustomModelData::floats,
            // Code statement
            "flags", Codec.BOOLEAN.list().optional(List.of()), CustomModelData::flags,
            // Code statement
            "strings", Codec.STRING.list().optional(List.of()), CustomModelData::strings,
            // Code statement
            "colors", Color.CODEC.list().optional(List.of()), CustomModelData::colors,
            // Code statement
            CustomModelData::new);

    // Start of a method/block
    public CustomModelData {
        // Calls a method
        floats = List.copyOf(floats);
        // Calls a method
        flags = List.copyOf(flags);
        // Calls a method
        strings = List.copyOf(strings);
        // Calls a method
        colors = List.copyOf(colors);
    // End of a block/expression
    }
// End of a block/expression
}
