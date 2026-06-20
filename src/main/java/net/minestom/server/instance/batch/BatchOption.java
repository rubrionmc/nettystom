// Déclaration du paquet de ce fichier
package net.minestom.server.instance.batch;

// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;

/**
 * Represents options for {@link Batch}s.
 */
// Déclaration de type (classe/interface/enum/record)
public class BatchOption {

    // Affecte une valeur
    private boolean fullChunk = false;
    // Affecte une valeur
    private boolean calculateInverse = false;
    // Affecte une valeur
    private boolean unsafeApply = false;
    // Affecte une valeur
    private boolean sendUpdate = true;

    // Début d'une méthode/d'un bloc
    public BatchOption() {
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the batch is responsible for composing the whole chunk.
     * <p>
     * Having it to true means that the batch will clear the chunk data before placing the blocks.
     * <p>
     * Defaults to false.
     *
     * @return true if the batch is responsible for all the chunk
     */
    // Début d'une méthode/d'un bloc
    public boolean isFullChunk() {
        // Renvoie une valeur à l'appelant
        return fullChunk;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the batch will calculate the inverse of the batch when it is applied for an 'undo' behavior.
     * <p>
     * This flag will determine the return value of {@link Batch#apply(Instance, Object)} (and other variants).
     * If true, a {@link Batch} will be returned. Otherwise null will be returned.
     * <p>
     * Defaults to false.
     *
     * @return true if the batch will calculate its inverse on application
     * @see #isUnsafeApply()
     */
    // Début d'une méthode/d'un bloc
    public boolean shouldCalculateInverse() {
        // Renvoie une valeur à l'appelant
        return calculateInverse;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the batch will wait ignore whether it is ready or not when applying it.
     * <p>
     * If set, the batch may not be ready, or it may be partially ready which will cause an undefined result.
     * {@link Batch#isReady()} and {@link Batch#awaitReady()} may be used to check if it is ready and block
     * until it is ready.
     * <p>
     * The default implementations of {@link ChunkBatch}, {@link AbsoluteBlockBatch}, and {@link RelativeBlockBatch}
     * are always ready unless they are an inverse batch. This is not a safe assumption, and may change in the future.
     * <p>
     * Defaults to false.
     *
     * @return true if the batch will immediately
     */
    // Début d'une méthode/d'un bloc
    public boolean isUnsafeApply() {
        // Renvoie une valeur à l'appelant
        return this.unsafeApply;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean shouldSendUpdate() {
        // Renvoie une valeur à l'appelant
        return sendUpdate;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @param fullChunk true to make this batch composes the whole chunk
     * @return 'this' for chaining
     * @see #isFullChunk()
     */
    // Annotation pour l'élément suivant
    @Contract("_ -> this")
    // Début d'une méthode/d'un bloc
    public BatchOption setFullChunk(boolean fullChunk) {
        // Accès à l'objet courant/parent
        this.fullChunk = fullChunk;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @param calculateInverse true to make this batch calculate the inverse on application
     * @return 'this' for chaining
     * @see #shouldCalculateInverse()
     */
    // Annotation pour l'élément suivant
    @Contract("_ -> this")
    // Début d'une méthode/d'un bloc
    public BatchOption setCalculateInverse(boolean calculateInverse) {
        // Accès à l'objet courant/parent
        this.calculateInverse = calculateInverse;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @param unsafeApply true to make this batch apply without checking if it is ready to apply.
     * @return 'this' for chaining
     * @see #isUnsafeApply()
     * @see Batch#isReady()
     */
    // Annotation pour l'élément suivant
    @Contract("_ -> this")
    // Début d'une méthode/d'un bloc
    public BatchOption setUnsafeApply(boolean unsafeApply) {
        // Accès à l'objet courant/parent
        this.unsafeApply = unsafeApply;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Contract("_ -> this")
    // Début d'une méthode/d'un bloc
    public BatchOption setSendUpdate(boolean sendUpdate) {
        // Accès à l'objet courant/parent
        this.sendUpdate = sendUpdate;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
