// Package declaration for this file
package net.minestom.server.network.debug;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.utils.collection.ObjectArray;

// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public record DebugSubscriptionImpl<T>(
        // Code statement
        int id,
        // Code statement
        Key key,
        // Code statement
        NetworkBuffer.Type<T> networkType
// Start of a method/block
) implements DebugSubscription<T>, NetworkBuffer.Type<T> {
    // Calls a method
    static final Map<String, DebugSubscription<?>> NAMESPACES = new HashMap<>(32);
    // Calls a method
    static final ObjectArray<DebugSubscription<?>> IDS = ObjectArray.singleThread(32);

    // Start of a method/block
    static {
        // Assigns a value
        var ignoredForInit = DebugSubscriptions.BEES;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void write(NetworkBuffer buffer, T value) {
        // Calls a method
        networkType.write(buffer, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public T read(NetworkBuffer buffer) {
        // Returns a value to the caller
        return networkType.read(buffer);
    // End of a block/expression
    }
// End of a block/expression
}
