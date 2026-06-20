// Package declaration for this file
package net.minestom.server.item.component;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public record DebugStickState(Map<String, String> state) {
    // Calls a method
    public static final DebugStickState EMPTY = new DebugStickState(Map.of());

    // Assigns a value
    public static final Codec<DebugStickState> CODEC = Codec.STRING.mapValue(Codec.STRING)
            // Calls a method
            .transform(DebugStickState::new, DebugStickState::state);
    // Calls a method
    public static final NetworkBuffer.Type<DebugStickState> NETWORK_TYPE = NetworkBuffer.TypedNBT(CODEC);

    // Start of a method/block
    public DebugStickState {
        // Calls a method
        state = Map.copyOf(state);
    // End of a block/expression
    }

    // Start of a method/block
    public DebugStickState set(String key, String value) {
        // Calls a method
        Map<String, String> newState = new HashMap<>(state);
        // Calls a method
        newState.put(key, value);
        // Returns a value to the caller
        return new DebugStickState(newState);
    // End of a block/expression
    }

    // Start of a method/block
    public DebugStickState remove(String key) {
        // Calls a method
        Map<String, String> newState = new HashMap<>(state);
        // Calls a method
        newState.remove(key);
        // Returns a value to the caller
        return new DebugStickState(newState);
    // End of a block/expression
    }

// End of a block/expression
}
