// Déclaration du paquet de ce fichier
package net.minestom.server.instance.batch;

// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
// Import d'une classe nécessaire
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.function.Consumer;

/**
 * A {@link Batch} which can be used when changes are required across chunk borders, and
 * are going to be reused in different places. If translation is not required, {@link AbsoluteBlockBatch}
 * should be used instead for efficiency purposes.
 * <p>
 * Coordinates are relative to (0, 0, 0) with some limitations. All coordinates must
 * fit within a 16 bit integer of the first coordinate (32,767 blocks). If blocks must
 * be spread out over a larger area, an {@link AbsoluteBlockBatch} should be used.
 * <p>
 * All inverses are {@link AbsoluteBlockBatch}s and represent the inverse of the application
 * at the position which it was applied.
 * <p>
 * If a batch will be used multiple times at the same coordinate, it is suggested
 * to convert it to an {@link AbsoluteBlockBatch} and cache the result. Application
 * of absolute batches (currently) is significantly faster than their relative counterpart.
 *
 * @see Batch
 * @see AbsoluteBlockBatch
 */
// Déclaration de type (classe/interface/enum/record)
public class RelativeBlockBatch implements Batch<Consumer<AbsoluteBlockBatch>> {
    // relative pos format: nothing/relative x/relative y/relative z (16/16/16/16 bits)

    // Need to be synchronized manually
    // Format: relative pos - block
    // Affecte une valeur
    private final Long2ObjectMap<Block> blockIdMap = new Long2ObjectOpenHashMap<>();

    // Instruction de code
    private final BatchOption options;

    // Affecte une valeur
    private volatile boolean firstEntry = true;
    // Instruction de code
    private int offsetX, offsetY, offsetZ;

    // Début d'une méthode/d'un bloc
    public RelativeBlockBatch() {
        // Appelle une méthode
        this(new BatchOption());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public RelativeBlockBatch(BatchOption options) {
        // Accès à l'objet courant/parent
        this.options = options;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void setBlock(int x, int y, int z, Block block) {
        // Save the offsets if it is the first entry
        // Embranchement : vérifie une condition
        if (firstEntry) {
            // Accès à l'objet courant/parent
            this.firstEntry = false;

            // Accès à l'objet courant/parent
            this.offsetX = x;
            // Accès à l'objet courant/parent
            this.offsetY = y;
            // Accès à l'objet courant/parent
            this.offsetZ = z;
        // Fin d'un bloc/d'une expression
        }

        // Subtract offset
        // Affecte une valeur
        x -= offsetX;
        // Affecte une valeur
        y -= offsetY;
        // Affecte une valeur
        z -= offsetZ;

        // Verify that blocks are not too far from each other
        // Appelle une méthode
        Check.argCondition(Math.abs(x) > Short.MAX_VALUE, "Relative x position may not be more than 16 bits long.");
        // Appelle une méthode
        Check.argCondition(Math.abs(y) > Short.MAX_VALUE, "Relative y position may not be more than 16 bits long.");
        // Appelle une méthode
        Check.argCondition(Math.abs(z) > Short.MAX_VALUE, "Relative z position may not be more than 16 bits long.");

        // Appelle une méthode
        long pos = Short.toUnsignedLong((short)x);
        // Appelle une méthode
        pos = (pos << 16) | Short.toUnsignedLong((short)y);
        // Appelle une méthode
        pos = (pos << 16) | Short.toUnsignedLong((short)z);

        //final int block = (blockStateId << 16) | customBlockId;
        // Début d'une méthode/d'un bloc
        synchronized (blockIdMap) {
            // Accès à l'objet courant/parent
            this.blockIdMap.put(pos, block);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void clear() {
        // Début d'une méthode/d'un bloc
        synchronized (blockIdMap) {
            // Accès à l'objet courant/parent
            this.blockIdMap.clear();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies this batch to the given instance at the origin (0, 0, 0) of the instance.
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
        return apply(instance, 0, 0, 0, callback);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies this batch to the given instance at the given block position.
     *
     * @param instance The instance in which the batch should be applied
     * @param position The position to apply the batch
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Début d'une méthode/d'un bloc
    public @UnknownNullability AbsoluteBlockBatch apply(Instance instance, Point position, @Nullable Consumer<@UnknownNullability AbsoluteBlockBatch> callback) {
        // Renvoie une valeur à l'appelant
        return apply(instance, position.blockX(), position.blockY(), position.blockZ(), callback);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies this batch to the given instance at the given position.
     *
     * @param instance The instance in which the batch should be applied
     * @param x        The x position to apply the batch
     * @param y        The y position to apply the batch
     * @param z        The z position to apply the batch
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Début d'une méthode/d'un bloc
    public @UnknownNullability AbsoluteBlockBatch apply(Instance instance, int x, int y, int z, @Nullable Consumer<@UnknownNullability AbsoluteBlockBatch> callback) {
        // Renvoie une valeur à l'appelant
        return apply(instance, x, y, z, callback, true);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies this batch to the given instance at the given position, and execute the callback
     * immediately when the blocks have been applied, int an unknown thread.
     *
     * @param instance The instance in which the batch should be applied
     * @param x        The x position to apply the batch
     * @param y        The y position to apply the batch
     * @param z        The z position to apply the batch
     * @param callback The callback to be executed when the batch is applied
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Début d'une méthode/d'un bloc
    public @UnknownNullability AbsoluteBlockBatch applyUnsafe(Instance instance, int x, int y, int z, @Nullable Consumer<@UnknownNullability AbsoluteBlockBatch> callback) {
        // Renvoie une valeur à l'appelant
        return apply(instance, x, y, z, callback, false);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Applies this batch to the given instance at the given position, execute the callback depending on safeCallback.
     *
     * @param instance     The instance in which the batch should be applied
     * @param x            The x position to apply the batch
     * @param y            The y position to apply the batch
     * @param z            The z position to apply the batch
     * @param callback     The callback to be executed when the batch is applied
     * @param safeCallback If true, the callback will be executed in the next instance update. Otherwise it will be executed immediately upon completion
     * @return The inverse of this batch, if inverse is enabled in the {@link BatchOption}
     */
    // Début d'une méthode/d'un bloc
    protected @UnknownNullability AbsoluteBlockBatch apply(Instance instance, int x, int y, int z, @Nullable Consumer<@UnknownNullability AbsoluteBlockBatch> callback, boolean safeCallback) {
        // Renvoie une valeur à l'appelant
        return this.toAbsoluteBatch(x, y, z).apply(instance, callback, safeCallback);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Converts this batch to an absolute batch at the origin (0, 0, 0).
     *
     * @return An absolute batch of this batch at the origin
     */
    // Début d'une méthode/d'un bloc
    public AbsoluteBlockBatch toAbsoluteBatch() {
        // Renvoie une valeur à l'appelant
        return toAbsoluteBatch(0, 0, 0);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Converts this batch to an absolute batch at the given coordinates.
     *
     * @param x The x position of the batch in the world
     * @param y The y position of the batch in the world
     * @param z The z position of the batch in the world
     * @return An absolute batch of this batch at (x, y, z)
     */
    // Début d'une méthode/d'un bloc
    public AbsoluteBlockBatch toAbsoluteBatch(int x, int y, int z) {
        // Appelle une méthode
        final AbsoluteBlockBatch batch = new AbsoluteBlockBatch(this.options);
        // Début d'une méthode/d'un bloc
        synchronized (blockIdMap) {
            // Boucle : répète un bloc
            for (var entry : blockIdMap.long2ObjectEntrySet()) {
                // Appelle une méthode
                final long pos = entry.getLongKey();
                // Affecte une valeur
                final short relZ = (short) (pos & 0xFFFF);
                // Affecte une valeur
                final short relY = (short) ((pos >> 16) & 0xFFFF);
                // Affecte une valeur
                final short relX = (short) ((pos >> 32) & 0xFFFF);

                // Appelle une méthode
                final Block block = entry.getValue();

                // Affecte une valeur
                final int finalX = x + offsetX + relX;
                // Affecte une valeur
                final int finalY = y + offsetY + relY;
                // Affecte une valeur
                final int finalZ = z + offsetZ + relZ;

                // Appelle une méthode
                batch.setBlock(finalX, finalY, finalZ, block);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return batch;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
