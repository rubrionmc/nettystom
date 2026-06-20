// Package declaration for this file
package net.minestom.server.utils.collection;

// Import of a required class
import org.jetbrains.annotations.*;

// Import of a required class
import java.util.List;

/**
 * Represents an array which will be resized to the highest required index.
 *
 * @param <T> the type of the array
 */
// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public sealed interface ObjectArray<T>
        // Start of a method/block
        permits ObjectArrayImpl.SingleThread, ObjectArrayImpl.Concurrent {
    // Start of a method/block
    static <T> ObjectArray<T> singleThread(int initialSize) {
        // Returns a value to the caller
        return new ObjectArrayImpl.SingleThread<>(initialSize);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> ObjectArray<T> singleThread() {
        // Returns a value to the caller
        return singleThread(0);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> ObjectArray<T> concurrent(int initialSize) {
        // Returns a value to the caller
        return new ObjectArrayImpl.Concurrent<>(initialSize);
    // End of a block/expression
    }

    // Start of a method/block
    static <T> ObjectArray<T> concurrent() {
        // Returns a value to the caller
        return concurrent(0);
    // End of a block/expression
    }

    // Annotation for the following element
    @UnknownNullability T get(int index);

    // Calls a method
    void set(int index, @Nullable T object);

    // Start of a method/block
    default void remove(int index) {
        // Calls a method
        set(index, null);
    // End of a block/expression
    }

    // Calls a method
    void trim();

    // Annotation for the following element
    @Contract(pure = true)
    // Annotation for the following element
    @UnknownNullability T [] arrayCopy(Class<T> type);

    /**
     * Copies the array into a list.
     * Requires all elements to be present and indexed from 0.
     *
     * @return List of the array elements
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Annotation for the following element
    @Unmodifiable List<T> toList();
// End of a block/expression
}
