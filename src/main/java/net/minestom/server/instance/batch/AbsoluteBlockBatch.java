// Déclaration du paquet de ce fichier
package net.minestom.server.instance.batch;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.CoordConversion;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.InstanceContainer;
// Import d'une classe nécessaire
import net.minestom.server.instance.LightingChunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.HashSet;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.concurrent.CountDownLatch;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicInteger;
// Import d'une classe nécessaire
import java.util.function.Consumer;

/**
 * A {@link Batch} which can be used when changes are required across chunk borders,
 * but the changes do not need any translation. If translation is required,
 * use a {@link RelativeBlockBatch} instead.
 * <p>
 * Coordinates are relative to the world origin.
 *
 * @see Batch
 * @see RelativeBlockBatch
 */
// Déclaration de type (classe/interface/enum/record)
public class AbsoluteBlockBatch implements Batch<Consumer<AbsoluteBlockBatch>> {

    // In the form of <Chunk Index, Batch>
    // Affecte une valeur
    private final Long2ObjectMap<ChunkBatch> chunkBatchesMap = new Long2ObjectOpenHashMap<>();

    // Available for other implementations to handle.
    // Instruction de code
    protected final CountDownLatch readyLatch;
    // Instruction de code
    private final BatchOption options;

    // Appelle une méthode
    private volatile BatchOption inverseOption = new BatchOption();

    // Début d'une méthode/d'un bloc
    public AbsoluteBlockBatch() {
        // Appelle une méthode
        this(new BatchOption());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public AbsoluteBlockBatch(BatchOption options) {
        // Appelle une méthode
        this(options, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private AbsoluteBlockBatch(BatchOption options, boolean ready) {
        // Accès à l'objet courant/parent
        this.readyLatch = new CountDownLatch(ready ? 0 : 1);
        // Accès à l'objet courant/parent
        this.options = options;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setBlock(int x, int y, int z, Block block) {
        // Appelle une méthode
        final int chunkX = CoordConversion.globalToChunk(x);
        // Appelle une méthode
        final int chunkZ = CoordConversion.globalToChunk(z);
        // Appelle une méthode
        final long chunkIndex = CoordConversion.chunkIndex(chunkX, chunkZ);

        // Instruction de code
        final ChunkBatch chunkBatch;
        // Début d'une méthode/d'un bloc
        synchronized (chunkBatchesMap) {
            // Appelle une méthode
            chunkBatch = chunkBatchesMap.computeIfAbsent(chunkIndex, i -> new ChunkBatch(this.options));
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        final int relativeX = x - (chunkX * Chunk.CHUNK_SIZE_X);
        // Affecte une valeur
        final int relativeZ = z - (chunkZ * Chunk.CHUNK_SIZE_Z);
        // Appelle une méthode
        chunkBatch.setBlock(relativeX, y, relativeZ, block);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void clear() {
        // Début d'une méthode/d'un bloc
        synchronized (chunkBatchesMap) {
            // Accès à l'objet courant/parent
            this.chunkBatchesMap.clear();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isReady() {
        // Renvoie une valeur à l'appelant
        return this.readyLatch.getCount() == 0;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void awaitReady() {
        // Gestion des exceptions
        try {
            // Accès à l'objet courant/parent
            this.readyLatch.await();
        // Début d'une méthode/d'un bloc
        } catch (InterruptedException e) {
            // Lève une exception
            throw new RuntimeException("#awaitReady interrupted!", e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies this batch to the given instance.
     *
     * @param instance The instance in which the batch should be applied
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public @UnknownNullability AbsoluteBlockBatch apply(Instance instance, @Nullable Consumer<@UnknownNullability AbsoluteBlockBatch> callback) {
        // Renvoie une valeur à l'appelant
        return apply(instance, callback, true);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies this batch to the given instance, and execute the callback immediately when the
     * blocks have been applied, in an unknown thread.
     *
     * @param instance The instance in which the batch should be applied
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Début d'une méthode/d'un bloc
    public @UnknownNullability AbsoluteBlockBatch unsafeApply(Instance instance, @Nullable Consumer<@UnknownNullability AbsoluteBlockBatch> callback) {
        // Renvoie une valeur à l'appelant
        return apply(instance, callback, false);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies this batch to the given instance, and execute the callback depending on safeCallback.
     *
     * @param instance     The instance in which the batch should be applied
     * @param callback     The callback to be executed when the batch is applied
     * @param safeCallback If true, the callback will be executed in the next instance update.
     *                     Otherwise it will be executed immediately upon completion
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Début d'une méthode/d'un bloc
    protected @UnknownNullability AbsoluteBlockBatch apply(Instance instance, @Nullable Consumer<@UnknownNullability AbsoluteBlockBatch> callback, boolean safeCallback) {
        // Embranchement : vérifie une condition
        if (!this.options.isUnsafeApply()) this.awaitReady();

        // Appelle une méthode
        final AbsoluteBlockBatch inverse = this.options.shouldCalculateInverse() ? new AbsoluteBlockBatch(inverseOption) : null;
        // Début d'une méthode/d'un bloc
        synchronized (chunkBatchesMap) {
            // Appelle une méthode
            AtomicInteger counter = new AtomicInteger();
            // Appelle une méthode
            Set<Chunk> updated = ConcurrentHashMap.newKeySet();

            // Boucle : répète un bloc
            for (var entry : Long2ObjectMaps.fastIterable(chunkBatchesMap)) {
                // Appelle une méthode
                final long chunkIndex = entry.getLongKey();
                // Appelle une méthode
                final int chunkX = CoordConversion.chunkIndexGetX(chunkIndex);
                // Appelle une méthode
                final int chunkZ = CoordConversion.chunkIndexGetZ(chunkIndex);
                // Appelle une méthode
                final ChunkBatch batch = entry.getValue();
                // Affecte une valeur
                ChunkBatch chunkInverse = batch.apply(instance, chunkX, chunkZ, c -> {
                    // Appelle une méthode
                    final boolean isLast = counter.incrementAndGet() == chunkBatchesMap.size();
                    // Execute the callback if this was the last chunk to process
                    // Embranchement : vérifie une condition
                    if (isLast) {
                        // Embranchement : vérifie une condition
                        if (inverse != null) inverse.readyLatch.countDown();
                        // Embranchement : vérifie une condition
                        if (instance instanceof InstanceContainer) {
                            // FIXME: put method in Instance instead
                            // Appelle une méthode
                            ((InstanceContainer) instance).refreshLastBlockChangeTime();
                        // Fin d'un bloc/d'une expression
                        }
                        // Embranchement : vérifie une condition
                        if (callback != null) {
                            // Embranchement : vérifie une condition
                            if (safeCallback) {
                                // Appelle une méthode
                                instance.scheduleNextTick(inst -> callback.accept(inverse));
                            // Branche alternative de la condition
                            } else {
                                // Appelle une méthode
                                callback.accept(inverse);
                            // Fin d'un bloc/d'une expression
                            }
                        // Fin d'un bloc/d'une expression
                        }

                        // Affecte une valeur
                        Set<Chunk> expanded = new HashSet<>();
                        // Boucle : répète un bloc
                        for (Chunk chunk : updated) {
                            // Boucle : répète un bloc
                            for (int i = -1; i <= 1; ++i) {
                                // Boucle : répète un bloc
                                for (int j = -1; j <= 1; ++j) {
                                    // Appelle une méthode
                                    Chunk toAdd = instance.getChunk(chunk.getChunkX() + i, chunk.getChunkZ() + j);
                                    // Embranchement : vérifie une condition
                                    if (toAdd != null) {
                                        // Appelle une méthode
                                        expanded.add(toAdd);
                                    // Fin d'un bloc/d'une expression
                                    }
                                // Fin d'un bloc/d'une expression
                                }
                            // Fin d'un bloc/d'une expression
                            }
                        // Fin d'un bloc/d'une expression
                        }

                        // Update the chunk's light
                        // Boucle : répète un bloc
                        for (Chunk chunk : expanded) {
                            // Embranchement : vérifie une condition
                            if (chunk instanceof LightingChunk dc) {
                                // Appelle une méthode
                                dc.sendLighting();
                            // Fin d'un bloc/d'une expression
                            }
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                });
                // Embranchement : vérifie une condition
                if (inverse != null) inverse.chunkBatchesMap.put(chunkIndex, chunkInverse);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return inverse;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public BatchOption getInverseOption() {
        // Renvoie une valeur à l'appelant
        return inverseOption;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setInverseOption(BatchOption inverseOption) {
        // Accès à l'objet courant/parent
        this.inverseOption = inverseOption;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
