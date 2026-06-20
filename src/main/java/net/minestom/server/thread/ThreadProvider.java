// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;

// Annotation for the following element
@FunctionalInterface
// Annotation for the following element
@ApiStatus.Experimental
// Type declaration (class/interface/enum/record)
public interface ThreadProvider<T> {
    // Start of a method/block
    static <T> ThreadProvider<T> counter() {
        // Returns a value to the caller
        return new ThreadProvider<>() {
            // Calls a method
            private final AtomicInteger counter = new AtomicInteger();

            // Annotation for the following element
            @Override
            // Start of a method/block
            public int findThread(T partition) {
                // Returns a value to the caller
                return counter.getAndIncrement();
            // End of a block/expression
            }
        // End of a block/expression
        };
    // End of a block/expression
    }

    /**
     * Performs a server tick for all chunks based on their linked thread.
     *
     * @param partition the partition
     */
    // Calls a method
    int findThread(T partition);

    /**
     * Defines how often chunks thread should be updated.
     *
     * @return the refresh type
     */
    // Start of a method/block
    default RefreshType refreshType() {
        // Returns a value to the caller
        return RefreshType.NEVER;
    // End of a block/expression
    }

    /**
     * Defines how often chunks thread should be refreshed.
     */
    // Type declaration (class/interface/enum/record)
    enum RefreshType {
        /**
         * Thread never change after being defined once.
         * <p>
         * Means that {@link #findThread(Object)} will only be called once for each partition.
         */
        // Code statement
        NEVER,
        /**
         * Thread is updated as often as possible.
         * <p>
         * Means that {@link #findThread(Object)} may be called multiple time for each partition.
         */
        // Code statement
        ALWAYS
    // End of a block/expression
    }
// End of a block/expression
}
