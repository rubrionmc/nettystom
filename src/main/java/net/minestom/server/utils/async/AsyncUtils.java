// Package declaration for this file
package net.minestom.server.utils.async;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.concurrent.CompletableFuture;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class AsyncUtils {
    // Calls a method
    public static final CompletableFuture<Void> VOID_FUTURE = CompletableFuture.completedFuture(null);

    // Start of a method/block
    public static <T> CompletableFuture<T> empty() {
        //noinspection unchecked
        // Returns a value to the caller
        return (CompletableFuture<T>) VOID_FUTURE;
    // End of a block/expression
    }

    // Start of a method/block
    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        // Returns a value to the caller
        return CompletableFuture.runAsync(() -> {
            // Exception handling
            try {
                // Calls a method
                runnable.run();
            // Start of a method/block
            } catch (Exception e) {
                // Calls a method
                MinecraftServer.getExceptionManager().handleException(e);
            // End of a block/expression
            }
        // End of a block/expression
        });
    // End of a block/expression
    }
// End of a block/expression
}
