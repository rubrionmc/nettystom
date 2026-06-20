// Package declaration for this file
package net.minestom.server.network.debug;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.registry.StaticProtocolObject;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collection;

// Type declaration (class/interface/enum/record)
public sealed interface DebugSubscription<T> extends StaticProtocolObject<DebugSubscription<T>>, DebugSubscriptions permits DebugSubscriptionImpl {

    // Assigns a value
    NetworkBuffer.Type<DebugSubscription<?>> NETWORK_TYPE = NetworkBuffer.VAR_INT
            // Calls a method
            .transform(DebugSubscription::fromId, DebugSubscription::id);

    // Calls a method
    int id();

    // Calls a method
    Key key();

    // Start of a method/block
    static @Nullable DebugSubscription<?> fromKey(String key) {
        // Returns a value to the caller
        return DebugSubscriptionImpl.NAMESPACES.get(key);
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable DebugSubscription<?> fromKey(Key key) {
        // Returns a value to the caller
        return fromKey(key.asString());
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable DebugSubscription<?> fromId(int id) {
        // Returns a value to the caller
        return DebugSubscriptionImpl.IDS.get(id);
    // End of a block/expression
    }

    // Start of a method/block
    static Collection<DebugSubscription<?>> values() {
        // Returns a value to the caller
        return DebugSubscriptionImpl.NAMESPACES.values();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Event<T>(DebugSubscription<T> subscription, T value) {
        // Annotation for the following element
        @SuppressWarnings("unchecked")
        // Assigns a value
        public static final NetworkBuffer.Type<DebugSubscription.Event<?>> NETWORK_TYPE = new NetworkBuffer.Type<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, Event<?> value) {
                // Calls a method
                buffer.write(DebugSubscription.NETWORK_TYPE, value.subscription);
                // Calls a method
                ((DebugSubscriptionImpl<Object>) value.subscription).write(buffer, value.value);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Event<?> read(NetworkBuffer buffer) {
                // Calls a method
                var subscription = (DebugSubscriptionImpl<Object>) buffer.read(DebugSubscription.NETWORK_TYPE);
                // Calls a method
                Object value = subscription.read(buffer);
                // Returns a value to the caller
                return new Event<>(subscription, value);
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Update<T>(DebugSubscription<T> subscription, @Nullable T value) {
        // Annotation for the following element
        @SuppressWarnings("unchecked")
        // Assigns a value
        public static final NetworkBuffer.Type<DebugSubscription.Update<?>> NETWORK_TYPE = new NetworkBuffer.Type<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public void write(NetworkBuffer buffer, Update<?> value) {
                // Calls a method
                buffer.write(DebugSubscription.NETWORK_TYPE, value.subscription);
                // Calls a method
                buffer.write(NetworkBuffer.BOOLEAN, value.value != null);
                // Branch: checks a condition
                if (value.value != null) ((DebugSubscriptionImpl<Object>) value.subscription).write(buffer, value.value);
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public Update<?> read(NetworkBuffer buffer) {
                // Calls a method
                var subscription = (DebugSubscriptionImpl<Object>) buffer.read(DebugSubscription.NETWORK_TYPE);
                // Calls a method
                boolean hasValue = buffer.read(NetworkBuffer.BOOLEAN);
                // Calls a method
                Object value = hasValue ? subscription.read(buffer) : null;
                // Returns a value to the caller
                return new Update<>(subscription, value);
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

// End of a block/expression
}
