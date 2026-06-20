// Package declaration for this file
package net.minestom.server.utils.callback;

// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.utils.chunk.ChunkCallback;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Convenient class to execute callbacks which can be null.
 */
// Type declaration (class/interface/enum/record)
public class OptionalCallback {

    /**
     * Executes an optional {@link Runnable}.
     *
     * @param callback the optional runnable, can be null
     */
    // Start of a method/block
    public static void execute(@Nullable Runnable callback) {
        // Branch: checks a condition
        if (callback != null) {
            // Calls a method
            callback.run();
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Executes an optional {@link ChunkCallback}.
     *
     * @param callback the optional chunk callback, can be null
     * @param chunk    the chunk to forward to the callback
     */
    // Start of a method/block
    public static void execute(@Nullable ChunkCallback callback, @Nullable Chunk chunk) {
        // Branch: checks a condition
        if (callback != null) {
            // Calls a method
            callback.accept(chunk);
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
