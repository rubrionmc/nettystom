// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public record ItemBlockState(Map<String, String> properties) {
    // Calls a method
    public static final ItemBlockState EMPTY = new ItemBlockState(Map.of());

    // Assigns a value
    public static final NetworkBuffer.Type<ItemBlockState> NETWORK_TYPE = NetworkBuffer.STRING.mapValue(NetworkBuffer.STRING)
            // Calls a method
            .transform(ItemBlockState::new, ItemBlockState::properties);
    // Assigns a value
    public static final Codec<ItemBlockState> CODEC = Codec.STRING.mapValue(Codec.STRING)
            // Calls a method
            .transform(ItemBlockState::new, ItemBlockState::properties);

    // Start of a method/block
    public ItemBlockState {
        // Calls a method
        properties = Map.copyOf(properties);
    // End of a block/expression
    }

    // Start of a method/block
    public ItemBlockState(String key, String value) {
        // Calls a method
        this(Map.of(key, value));
    // End of a block/expression
    }

    // Start of a method/block
    public ItemBlockState with(String key, String value) {
        // Calls a method
        Map<String, String> newProperties = new HashMap<>(properties);
        // Calls a method
        newProperties.put(key, value);
        // Returns a value to the caller
        return new ItemBlockState(newProperties);
    // End of a block/expression
    }

    // Start of a method/block
    public Block apply(Block block) {
        // Loop: repeats a block
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            // Branch: checks a condition
            if (block.getProperty(entry.getKey()) == null)
                // Continues to the next loop iteration
                continue; // Ignore properties not present on this block
            // Calls a method
            block = block.withProperty(entry.getKey(), entry.getValue());
        // End of a block/expression
        }
        // Returns a value to the caller
        return block;
    // End of a block/expression
    }
// End of a block/expression
}
