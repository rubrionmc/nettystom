// Package declaration for this file
package net.minestom.server.utils;

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

// Import of a required class
import java.util.Iterator;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.Random;

// Type declaration (class/interface/enum/record)
public final class WeightedList<T> implements Iterable<T> {
    // Start of a method/block
    public static <T> NetworkBuffer.Type<WeightedList<T>> networkType(NetworkBuffer.Type<T> valueType) {
        // Returns a value to the caller
        return Entry.networkType(valueType).list().transform(WeightedList::new, WeightedList::entries);
    // End of a block/expression
    }
    // Start of a method/block
    public static <T> Codec<WeightedList<T>> codec(StructCodec<T> valueCodec) {
        // Returns a value to the caller
        return Entry.codec(valueCodec).list().transform(WeightedList::new, WeightedList::entries);
    // End of a block/expression
    }
    // Start of a method/block
    public static <T> Codec<WeightedList<T>> codec(Codec<T> valueCodec) {
        // Calls a method
        StructCodec<T> wrapper = StructCodec.struct("data", valueCodec, t -> t, t -> t);
        // Returns a value to the caller
        return Entry.codec(wrapper).list().transform(WeightedList::new, WeightedList::entries);
    // End of a block/expression
    }

    // Annotation for the following element
    @SafeVarargs
    // Start of a method/block
    public static <T> WeightedList<T> of(Entry<T>... entries) {
        // Returns a value to the caller
        return new WeightedList<>(List.of(entries));
    // End of a block/expression
    }

    // Code statement
    private final List<Entry<T>> entries;
    // Code statement
    private final int totalWeight;

    // Start of a method/block
    public WeightedList(List<Entry<T>> entries) {
        // Access to the current/parent object
        this.entries = List.copyOf(entries);

        // Assigns a value
        int total = 0;
        // Loop: repeats a block
        for (Entry<T> entry : this.entries)
            // Calls a method
            total += entry.weight();
        // Access to the current/parent object
        this.totalWeight = total;
    // End of a block/expression
    }

    // Start of a method/block
    public List<Entry<T>> entries() {
        // Returns a value to the caller
        return entries;
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable T pick(Random random) {
        // Calls a method
        int pick = random.nextInt(totalWeight);
        // Loop: repeats a block
        for (Entry<T> entry : entries) {
            // Calls a method
            pick -= entry.weight();
            // Branch: checks a condition
            if (pick < 0) return entry.value();
        // End of a block/expression
        }
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Start of a method/block
    public T pickOrThrow(Random random) {
        // Returns a value to the caller
        return Objects.requireNonNull(pick(random), "Weighted list was empty");
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Iterator<T> iterator() {
        // Calls a method
        final var delegate = entries.iterator();
        // Returns a value to the caller
        return new Iterator<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            public boolean hasNext() {
                // Returns a value to the caller
                return delegate.hasNext();
            // End of a block/expression
            }

            // Annotation for the following element
            @Override
            // Start of a method/block
            public T next() {
                // Returns a value to the caller
                return delegate.next().value();
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Entry<T>(T value, int weight) {
        // Start of a method/block
        public static <T> NetworkBuffer.Type<Entry<T>> networkType(NetworkBuffer.Type<T> valueType) {
            // Returns a value to the caller
            return NetworkBufferTemplate.template(
                    // Code statement
                    valueType, Entry::value,
                    // Code statement
                    NetworkBuffer.VAR_INT, Entry::weight,
                    // Code statement
                    Entry::new);
        // End of a block/expression
        }
        // Start of a method/block
        public static <T> StructCodec<Entry<T>> codec(StructCodec<T> valueCodec) {
            // Returns a value to the caller
            return StructCodec.struct(
                    // Code statement
                    StructCodec.INLINE, valueCodec, Entry::value,
                    // Code statement
                    "weight", Codec.INT, Entry::weight,
                    // Code statement
                    Entry::new);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (!(o instanceof WeightedList<?> that)) return false;
        // Returns a value to the caller
        return totalWeight == that.totalWeight && entries.equals(that.entries);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Calls a method
        int result = entries.hashCode();
        // Assigns a value
        result = 31 * result + totalWeight;
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
