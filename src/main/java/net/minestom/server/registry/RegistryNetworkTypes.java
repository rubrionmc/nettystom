// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;

// Type declaration (class/interface/enum/record)
final class RegistryNetworkTypes {

    // Type declaration (class/interface/enum/record)
    record RegistryKeyImpl<T>(Registries.Selector<T> selector) implements NetworkBuffer.Type<RegistryKey<T>> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, RegistryKey<T> value) {
            // Calls a method
            final var registries = Objects.requireNonNull(buffer.registries(), "Buffer is missing registries");
            // Calls a method
            final var registry = selector.select(registries);
            // Calls a method
            final int id = registry.getId(value);
            // Calls a method
            Check.stateCondition(id == -1, "Key {0} is not registered in registry {1}", value, registry.key());
            // Calls a method
            buffer.write(NetworkBuffer.VAR_INT, id);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public RegistryKey<T> read(NetworkBuffer buffer) {
            // Calls a method
            final var registries = Objects.requireNonNull(buffer.registries(), "Buffer is missing registries");
            // Calls a method
            final var registry = selector.select(registries);
            // Calls a method
            final int id = buffer.read(NetworkBuffer.VAR_INT);
            // Calls a method
            final var key = registry.getKey(id);
            // Calls a method
            Check.stateCondition(key == null, "Unknown id {0} for registry {1}", id - 1, registry.key());
            // Returns a value to the caller
            return key;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record HolderNetworkTypeImpl<T extends Holder<T>>(
            // Code statement
            Registries.Selector<T> selector,
            // Code statement
            NetworkBuffer.Type<T> registryNetworkType
    // Start of a method/block
    ) implements NetworkBuffer.Type<Holder<T>> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, Holder<T> value) {
            // Calls a method
            final var registries = Objects.requireNonNull(buffer.registries(), "Buffer is missing registries");
            // Multiple branching (switch/case)
            switch (value.unwrap()) {
                // Multiple branching (switch/case)
                case Either.Left(RegistryKey<T> key) -> {
                    // Calls a method
                    final var registry = selector.select(registries);
                    // Calls a method
                    final int id = registry.getId(key);
                    // Calls a method
                    Check.stateCondition(id == -1, "Key {0} is not registered in registry {1}", key, registry.key());
                    // Calls a method
                    buffer.write(NetworkBuffer.VAR_INT, id + 1);
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case Either.Right(T direct) -> {
                    // Calls a method
                    buffer.write(NetworkBuffer.VAR_INT, 0);
                    // Calls a method
                    buffer.write(registryNetworkType, direct);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Holder<T> read(NetworkBuffer buffer) {
            // Calls a method
            final var registries = Objects.requireNonNull(buffer.registries(), "Buffer is missing registries");
            // Calls a method
            final int id = buffer.read(NetworkBuffer.VAR_INT);
            // Branch: checks a condition
            if (id == 0)
                // Returns a value to the caller
                return buffer.read(registryNetworkType);
            // Calls a method
            final var registry = selector.select(registries);
            // Calls a method
            final var key = registry.getKey(id - 1);
            // Calls a method
            Check.stateCondition(key == null, "Unknown id {0} for registry {1}", id - 1, registry.key());
            // Returns a value to the caller
            return key;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record RegistryTagImpl<T>(Registries.Selector<T> selector) implements NetworkBuffer.Type<RegistryTag<T>> {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void write(NetworkBuffer buffer, RegistryTag<T> value) {
            // Multiple branching (switch/case)
            switch (value) {
                // Multiple branching (switch/case)
                case net.minestom.server.registry.RegistryTagImpl.Backed<T> backed -> {
                    // Calls a method
                    buffer.write(NetworkBuffer.VAR_INT, 0);
                    // Calls a method
                    buffer.write(NetworkBuffer.KEY, backed.key().key());
                // End of a block/expression
                }
                // Multiple branching (switch/case)
                case net.minestom.server.registry.RegistryTagImpl.Empty() -> buffer.write(NetworkBuffer.VAR_INT, 1);
                // Multiple branching (switch/case)
                case net.minestom.server.registry.RegistryTagImpl.Direct(var entries) -> {
                    // Calls a method
                    final var registries = Objects.requireNonNull(buffer.registries(), "Buffer is missing registries");
                    // Calls a method
                    final var registry = selector.select(registries);
                    // Calls a method
                    buffer.write(NetworkBuffer.VAR_INT, entries.size() + 1);
                    // Loop: repeats a block
                    for (RegistryKey<T> key : entries) {
                        // Calls a method
                        final int id = registry.getId(key);
                        // Calls a method
                        Check.stateCondition(id == -1, "Key {0} is not registered in registry {1}", key, registry.key());
                        // Calls a method
                        buffer.write(NetworkBuffer.VAR_INT, id);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public RegistryTag<T> read(NetworkBuffer buffer) {
            // Calls a method
            final var registries = Objects.requireNonNull(buffer.registries(), "Buffer is missing registries");
            // Calls a method
            final var registry = selector.select(registries);
            // Calls a method
            int count = buffer.read(NetworkBuffer.VAR_INT) - 1;
            // Branch: checks a condition
            if (count < 0) {
                // Calls a method
                final var key = buffer.read(NetworkBuffer.KEY);
                // Calls a method
                final var tag = registry.getTag(key);
                // Calls a method
                Check.stateCondition(tag == null, "No such tag {0} for registry {1}", key, registry.key());
                // Returns a value to the caller
                return tag;
            // Branch: checks a condition
            } else if (count == 0) {
                // Returns a value to the caller
                return RegistryTag.empty();
            // Alternative branch of the condition
            } else {
                // Calls a method
                final List<RegistryKey<T>> keys = new ArrayList<>(count);
                // Loop: repeats a block
                for (int i = 0; i < count; i++) {
                    // Calls a method
                    final int id = buffer.read(NetworkBuffer.VAR_INT);
                    // Calls a method
                    final var key = registry.getKey(id);
                    // Calls a method
                    Check.stateCondition(key == null, "Unknown id {0} for registry {1}", id, registry.key());
                    // Calls a method
                    keys.add(key);
                // End of a block/expression
                }
                // Returns a value to the caller
                return new net.minestom.server.registry.RegistryTagImpl.Direct<>(keys);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
