// Déclaration du paquet de ce fichier
package net.minestom.server.instance.batch;

// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.concurrent.ExecutorService;
// Import d'une classe nécessaire
import java.util.concurrent.ForkJoinPool;

/**
 * A Batch is a tool used to cache a list of block changes, and apply the changes whenever you want.
 * <p>
 * Batches offer a performance benefit because clients are not notified of any change until all of
 * the blocks have been placed, and because changes can happen with less synchronization.
 * <p>
 * All batches may be rotated using {link}, however rotate operations do not mutate the batch, so the
 * result should be cached if used multiple times.
 * <p>
 * If reversal is a desired behavior, batches may be applied in "reversal mode" using {link}. This
 * operation will return a new batch with the blocks set to whatever they were before the batch was
 * applied.
 *
 * @param <C> The callback function type.
 * @see ChunkBatch
 * @see AbsoluteBlockBatch
 * @see RelativeBlockBatch
 */
// Déclaration de type (classe/interface/enum/record)
public interface Batch<C> extends Block.Setter {

    // Appelle une méthode
    ExecutorService BLOCK_BATCH_POOL = ForkJoinPool.commonPool();

    /**
     * Gets if the batch is ready to be applied to an instance.
     *
     * @return true if the batch is ready to apply
     */
    // Début d'une méthode/d'un bloc
    default boolean isReady() {
        // Renvoie une valeur à l'appelant
        return true;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Blocks the current thread until the batch is ready to be applied.
     *
     * @see #isReady() for a non-blocking way to determine if the batch is ready
     */
    // Début d'une méthode/d'un bloc
    default void awaitReady() {
    // Fin d'un bloc/d'une expression
    }

    /**
     * Removes all block data from this batch.
     */
    // Appelle une méthode
    void clear();

    /**
     * Called to apply the batch to the given instance.
     * <p>
     * The implementation for all current batches executes the block updates in a dedicated pool,
     * and runs the callback on the next instance update after block placement is complete. This
     * means that the callback can be called up to 50ms after the blocks have been placed, however,
     * it will be called in a determinable thread. If immediate execution of the callback is needed,
     * see the unsafeApply method in each implementation.
     * <p>
     * See the specific batch classes for alternative application methods.
     *
     * @param instance The instance in which the batch should be applied
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Annotation pour l'élément suivant
    @UnknownNullability
    // Appelle une méthode
    Batch<C> apply(Instance instance, @Nullable C callback);
// Fin d'un bloc/d'une expression
}
