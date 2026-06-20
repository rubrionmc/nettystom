// Package declaration for this file
package net.minestom.server.utils.chunk;

// Import of a required class
import net.minestom.server.coordinate.CoordConversion;
// Import of a required class
import net.minestom.server.coordinate.Point;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.concurrent.CompletableFuture;
// Import of a required class
import java.util.concurrent.atomic.AtomicInteger;
// Import of a required class
import java.util.function.Consumer;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class ChunkUtils {

    // Start of a method/block
    private ChunkUtils() {
    // End of a block/expression
    }

    /**
     * Executes {@link Instance#loadOptionalChunk(int, int)} for the array of chunks {@code chunks}
     * with multiple callbacks, {@code eachCallback} which is executed each time a new chunk is loaded and
     * {@code endCallback} when all the chunks in the array have been loaded.
     * <p>
     * Be aware that {@link Instance#loadOptionalChunk(int, int)} can give a null chunk in the callback
     * if {@link Instance#hasEnabledAutoChunkLoad()} returns false and the chunk is not already loaded.
     *
     * @param instance     the instance to load the chunks from
     * @param chunks       the chunks to loaded, long value from {@link CoordConversion#chunkIndex(int, int)}
     * @param eachCallback the optional callback when a chunk get loaded
     * @return a {@link CompletableFuture} completed once all chunks have been processed
     */
    // Code statement
    public static CompletableFuture<Void> optionalLoadAll(Instance instance, long [] chunks,
                                                                   // Annotation for the following element
                                                                   @Nullable Consumer<Chunk> eachCallback) {
        // Calls a method
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        // Calls a method
        AtomicInteger counter = new AtomicInteger(0);
        // Loop: repeats a block
        for (long visibleChunk : chunks) {
            // WARNING: if autoload is disabled and no chunks are loaded beforehand, player will be stuck.
            // Code statement
            instance.loadOptionalChunk(CoordConversion.chunkIndexGetX(visibleChunk), CoordConversion.chunkIndexGetZ(visibleChunk))
                    // Start of a method/block
                    .thenAccept((chunk) -> {
                        // Branch: checks a condition
                        if (eachCallback != null) eachCallback.accept(chunk);
                        // Branch: checks a condition
                        if (counter.incrementAndGet() == chunks.length) {
                            // This is the last chunk to be loaded , spawn player
                            // Calls a method
                            completableFuture.complete(null);
                        // End of a block/expression
                        }
                    // End of a block/expression
                    });
        // End of a block/expression
        }
        // Returns a value to the caller
        return completableFuture;
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean isLoaded(@Nullable Chunk chunk) {
        // Returns a value to the caller
        return chunk != null && chunk.isLoaded();
    // End of a block/expression
    }

    /**
     * Gets if a chunk is loaded.
     *
     * @param instance the instance to check
     * @param x        instance X coordinate
     * @param z        instance Z coordinate
     * @return true if the chunk is loaded, false otherwise
     */
    // Start of a method/block
    public static boolean isLoaded(Instance instance, double x, double z) {
        // Calls a method
        final Chunk chunk = instance.getChunk(CoordConversion.globalToChunk(x), CoordConversion.globalToChunk(z));
        // Returns a value to the caller
        return isLoaded(chunk);
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean isLoaded(Instance instance, Point point) {
        // Calls a method
        final Chunk chunk = instance.getChunk(point.chunkX(), point.chunkZ());
        // Returns a value to the caller
        return isLoaded(chunk);
    // End of a block/expression
    }

    // Start of a method/block
    public static Chunk retrieve(Instance instance, Chunk originChunk, double x, double z) {
        // Calls a method
        final int chunkX = CoordConversion.globalToChunk(x);
        // Calls a method
        final int chunkZ = CoordConversion.globalToChunk(z);
        // Assigns a value
        final boolean sameChunk = originChunk != null &&
                // Calls a method
                originChunk.getChunkX() == chunkX && originChunk.getChunkZ() == chunkZ;
        // Returns a value to the caller
        return sameChunk ? originChunk : instance.getChunk(chunkX, chunkZ);
    // End of a block/expression
    }

    // Start of a method/block
    public static Chunk retrieve(Instance instance, Chunk originChunk, Point position) {
        // Returns a value to the caller
        return retrieve(instance, originChunk, position.x(), position.z());
    // End of a block/expression
    }
// End of a block/expression
}
