// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.Unmodifiable;

// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.function.ToIntFunction;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class ArrayUtils {

    // Start of a method/block
    private ArrayUtils() {
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean isEmpty(@Nullable Object [] array) {
        // Loop: repeats a block
        for (Object object : array) {
            // Branch: checks a condition
            if (object != null) return false;
        // End of a block/expression
        }
        // Returns a value to the caller
        return true;
    // End of a block/expression
    }

    // Start of a method/block
    public static <T> int[] mapToIntArray(Collection<T> collection, ToIntFunction<T> function) {
        // Calls a method
        final int size = collection.size();
        // Branch: checks a condition
        if (size == 0)
            // Returns a value to the caller
            return new int[0];
        // Assigns a value
        int[] result = new int[size];
        // Assigns a value
        int i = 0;
        // Loop: repeats a block
        for (T object : collection) {
            // Calls a method
            result[i++] = function.applyAsInt(object);
        // End of a block/expression
        }
        // Code statement
        assert i == size;
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Start of a method/block
    public static <K, V> @Unmodifiable Map<K, V> toMap(K[] keys, V[] values, int length) {
        // Code statement
        assert keys.length >= length && keys.length == values.length;
        // Returns a value to the caller
        return switch (length) {
            // Multiple branching (switch/case)
            case 0 -> Map.of();
            // Multiple branching (switch/case)
            case 1 -> Map.of(keys[0], values[0]);
            // Multiple branching (switch/case)
            case 2 -> Map.of(keys[0], values[0], keys[1], values[1]);
            // Multiple branching (switch/case)
            case 3 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2]);
            // Multiple branching (switch/case)
            case 4 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2],
                    // Code statement
                    keys[3], values[3]);
            // Multiple branching (switch/case)
            case 5 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2],
                    // Code statement
                    keys[3], values[3], keys[4], values[4]);
            // Multiple branching (switch/case)
            case 6 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2],
                    // Code statement
                    keys[3], values[3], keys[4], values[4], keys[5], values[5]);
            // Multiple branching (switch/case)
            case 7 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2],
                    // Code statement
                    keys[3], values[3], keys[4], values[4], keys[5], values[5], keys[6], values[6]);
            // Multiple branching (switch/case)
            case 8 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2],
                    // Code statement
                    keys[3], values[3], keys[4], values[4], keys[5], values[5], keys[6], values[6],
                    // Code statement
                    keys[7], values[7]);
            // Multiple branching (switch/case)
            case 9 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2],
                    // Code statement
                    keys[3], values[3], keys[4], values[4], keys[5], values[5], keys[6], values[6],
                    // Code statement
                    keys[7], values[7], keys[8], values[8]);
            // Multiple branching (switch/case)
            case 10 -> Map.of(keys[0], values[0], keys[1], values[1], keys[2], values[2],
                    // Code statement
                    keys[3], values[3], keys[4], values[4], keys[5], values[5], keys[6], values[6],
                    // Code statement
                    keys[7], values[7], keys[8], values[8], keys[9], values[9]);
            // Multiple branching (switch/case)
            default -> Map.copyOf(new Object2ObjectArrayMap<>(keys, values, length));
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
