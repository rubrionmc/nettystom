// Package declaration for this file
package net.minestom.server.utils.collection;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.function.BiConsumer;
// Import of a required class
import java.util.function.Predicate;
// Import of a required class
import java.util.stream.Stream;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class MergedMap<K, V> extends AbstractMap<K, V> {
    // Code statement
    private final Map<K, V> first, second;

    // Start of a method/block
    public MergedMap(Map<K, V> first, Map<K, V> second) {
        // Access to the current/parent object
        this.first = Objects.requireNonNull(first);
        // Access to the current/parent object
        this.second = Objects.requireNonNull(second);
    // End of a block/expression
    }

    // mandatory methods

    // Assigns a value
    final Set<Entry<K, V>> entrySet = new AbstractSet<>() {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public Iterator<Map.Entry<K, V>> iterator() {
            // Returns a value to the caller
            return stream().iterator();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public int size() {
            // Returns a value to the caller
            return (int) stream().count();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Stream<Entry<K, V>> stream() {
            // Returns a value to the caller
            return Stream.concat(first.entrySet().stream(), secondStream())
                    // Calls a method
                    .map(e -> new AbstractMap.SimpleImmutableEntry<>(e.getKey(), e.getValue()));
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Stream<Entry<K, V>> parallelStream() {
            // Returns a value to the caller
            return stream().parallel();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public Spliterator<Entry<K, V>> spliterator() {
            // Returns a value to the caller
            return stream().spliterator();
        // End of a block/expression
        }
    // End of a block/expression
    };

    // Start of a method/block
    Stream<Entry<K, V>> secondStream() {
        // Returns a value to the caller
        return second.entrySet().stream().filter(e -> !first.containsKey(e.getKey()));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Set<Map.Entry<K, V>> entrySet() {
        // Returns a value to the caller
        return entrySet;
    // End of a block/expression
    }

    // optimizations

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean containsKey(Object key) {
        // Returns a value to the caller
        return first.containsKey(key) || second.containsKey(key);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean containsValue(Object value) {
        // Returns a value to the caller
        return first.containsValue(value) ||
                // Calls a method
                secondStream().anyMatch(Predicate.isEqual(value));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public V get(Object key) {
        // Calls a method
        V v = first.get(key);
        // Returns a value to the caller
        return v != null ? v : second.get(key);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public V getOrDefault(Object key, V defaultValue) {
        // Calls a method
        V v = first.get(key);
        // Returns a value to the caller
        return v != null ? v : second.getOrDefault(key, defaultValue);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void forEach(BiConsumer<? super K, ? super V> action) {
        // Calls a method
        first.forEach(action);
        // Start of a method/block
        second.forEach((k, v) -> {
            // Branch: checks a condition
            if (!first.containsKey(k)) action.accept(k, v);
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
