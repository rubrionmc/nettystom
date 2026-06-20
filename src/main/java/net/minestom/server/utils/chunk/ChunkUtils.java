// Déclaration du paquet de ce fichier
package net.minestom.server.utils.chunk;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class ChunkUtils {

    // Début d'une méthode/d'un bloc
    private ChunkUtils() {
    // Fin d'un bloc/d'une expression
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
    // Instruction de code
    public static CompletableFuture<Void> optionalLoadAll(Instance instance, long [] chunks,
                                                                   // Annotation pour l'élément suivant
                                                                   @Nullable Consumer<Chunk> eachCallback) {
        // Affecte une valeur
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        // Appelle une méthode
        AtomicInteger counter = new AtomicInteger(0);
        // Boucle : répète un bloc
        for (long visibleChunk : chunks) {
            // WARNING: if autoload is disabled and no chunks are loaded beforehand, player will be stuck.
            // Instruction de code
            instance.loadOptionalChunk(CoordConversion.chunkIndexGetX(visibleChunk), CoordConversion.chunkIndexGetZ(visibleChunk))
                    // Début d'une méthode/d'un bloc
                    .thenAccept((chunk) -> {
                        // Embranchement : vérifie une condition
                        if (eachCallback != null) eachCallback.accept(chunk);
                        // Embranchement : vérifie une condition
                        if (counter.incrementAndGet() == chunks.length) {
                            // This is the last chunk to be loaded , spawn player
                            // Appelle une méthode
                            completableFuture.complete(null);
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    });
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return completableFuture;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean isLoaded(@Nullable Chunk chunk) {
        // Renvoie une valeur à l'appelant
        return chunk != null && chunk.isLoaded();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if a chunk is loaded.
     *
     * @param instance the instance to check
     * @param x        instance X coordinate
     * @param z        instance Z coordinate
     * @return true if the chunk is loaded, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public static boolean isLoaded(Instance instance, double x, double z) {
        // Appelle une méthode
        final Chunk chunk = instance.getChunk(CoordConversion.globalToChunk(x), CoordConversion.globalToChunk(z));
        // Renvoie une valeur à l'appelant
        return isLoaded(chunk);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean isLoaded(Instance instance, Point point) {
        // Appelle une méthode
        final Chunk chunk = instance.getChunk(point.chunkX(), point.chunkZ());
        // Renvoie une valeur à l'appelant
        return isLoaded(chunk);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Chunk retrieve(Instance instance, Chunk originChunk, double x, double z) {
        // Appelle une méthode
        final int chunkX = CoordConversion.globalToChunk(x);
        // Appelle une méthode
        final int chunkZ = CoordConversion.globalToChunk(z);
        // Instruction de code
        final boolean sameChunk = originChunk != null &&
                // Appelle une méthode
                originChunk.getChunkX() == chunkX && originChunk.getChunkZ() == chunkZ;
        // Renvoie une valeur à l'appelant
        return sameChunk ? originChunk : instance.getChunk(chunkX, chunkZ);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Chunk retrieve(Instance instance, Chunk originChunk, Point position) {
        // Renvoie une valeur à l'appelant
        return retrieve(instance, originChunk, position.x(), position.z());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
