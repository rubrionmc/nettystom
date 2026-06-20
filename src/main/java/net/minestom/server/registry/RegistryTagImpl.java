// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Collections;
// Import of a required class
import java.util.Iterator;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Set;
// Import of a required class
import java.util.concurrent.CopyOnWriteArraySet;

// Type declaration (class/interface/enum/record)
final class RegistryTagImpl {

    // Type declaration (class/interface/enum/record)
    record Empty() implements RegistryTag<Object> {
        // Calls a method
        public static final Empty INSTANCE = new Empty();

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable TagKey<Object> key() {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean contains(RegistryKey<Object> value) {
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Iterator<RegistryKey<Object>> iterator() {
            // Returns a value to the caller
            return Collections.emptyIterator();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int size() {
            // Returns a value to the caller
            return 0;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * A tag that is backed by a registry.
     */
    // Type declaration (class/interface/enum/record)
    static final class Backed<T> implements RegistryTag<T> {
        // Code statement
        private final TagKey<T> key;
        // Calls a method
        private final Set<RegistryKey<T>> entries = new CopyOnWriteArraySet<>();

        // Start of a method/block
        Backed(TagKey<T> key) {
            // Access to the current/parent object
            this.key = key;
        // End of a block/expression
        }

        // Start of a method/block
        public TagKey<T> key() {
            // Returns a value to the caller
            return key;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean contains(RegistryKey<T> value) {
            // Returns a value to the caller
            return entries.contains(value instanceof RegistryKeyImpl<T> key ? key : new RegistryKeyImpl<>(value.key()));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int size() {
            // Returns a value to the caller
            return entries.size();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Iterator<RegistryKey<T>> iterator() {
            // Returns a value to the caller
            return entries.iterator();
        // End of a block/expression
        }

        // Annotation for the following element
        @ApiStatus.Internal
        // Start of a method/block
        void add(RegistryKey<T> key) {
            // Branch: checks a condition
            if (entries.add(key))
                // Calls a method
                invalidate();
        // End of a block/expression
        }

        // Annotation for the following element
        @ApiStatus.Internal
        // Start of a method/block
        void remove(RegistryKey<T> key) {
            // Branch: checks a condition
            if (entries.remove(key))
                // Calls a method
                invalidate();
        // End of a block/expression
        }

        // Start of a method/block
        private void invalidate() {
            // Calls a method
            var process = MinecraftServer.process();
            // Branch: checks a condition
            if (process == null) return;
            // Calls a method
            process.connection().invalidateTags();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Direct<T>(List<RegistryKey<T>> keys) implements RegistryTag<T> {
        // Start of a method/block
        public Direct {
            // Calls a method
            keys = List.copyOf(keys);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Nullable TagKey<T> key() {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean contains(RegistryKey<T> value) {
            // Returns a value to the caller
            return keys.contains(value instanceof RegistryKeyImpl<T> key ? key : new RegistryKeyImpl<>(value.key()));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Iterator<RegistryKey<T>> iterator() {
            // Returns a value to the caller
            return keys.iterator();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int size() {
            // Returns a value to the caller
            return keys.size();
        // End of a block/expression
        }

        // Equality is defined by the underlying keys rather than the concrete RegistryKey
        // implementation, so a tag built from registry values (e.g. Material) is equal to an
        // equivalent tag read back from the network (which holds RegistryKeyImpl instances).
        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean equals(Object o) {
            // Branch: checks a condition
            if (this == o) return true;
            // Branch: checks a condition
            if (!(o instanceof RegistryTagImpl.Direct<?>(var keys1))
                    // Calls a method
                    || keys.size() != keys1.size()) return false;
            // Loop: repeats a block
            for (int i = 0; i < keys.size(); i++)
                // Branch: checks a condition
                if (!keys.get(i).key().equals(keys1.get(i).key())) return false;
            // Returns a value to the caller
            return true;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int hashCode() {
            // Assigns a value
            int result = 1;
            // Loop: repeats a block
            for (RegistryKey<T> key : keys)
                // Calls a method
                result = 31 * result + key.key().hashCode();
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
