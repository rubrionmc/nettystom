// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.stream.Collectors;

// Type declaration (class/interface/enum/record)
public enum ItemRarity {
    // Code statement
    COMMON,
    // Code statement
    UNCOMMON,
    // Code statement
    RARE,
    // Code statement
    EPIC;

    // Assigns a value
    private static final Map<String, ItemRarity> BY_ID = Arrays.stream(values())
            // Calls a method
            .collect(Collectors.toMap(v -> v.name().toLowerCase(), Function.identity()));

    // Calls a method
    public static final NetworkBuffer.Type<ItemRarity> NETWORK_TYPE = NetworkBuffer.Enum(ItemRarity.class);
    // Calls a method
    public static final Codec<ItemRarity> CODEC = Codec.STRING.transform(BY_ID::get, v -> v.name().toLowerCase());
// End of a block/expression
}
