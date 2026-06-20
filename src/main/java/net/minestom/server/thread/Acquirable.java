// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Optional;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.stream.Stream;

// Annotation pour l'élément suivant
@ApiStatus.Experimental
// Déclaration de type (classe/interface/enum/record)
public sealed interface Acquirable<T> permits AcquirableImpl {

    /**
     * Gets all the {@link Entity entities} being ticked in the current thread.
     * <p>
     * Useful when you want to ensure that no acquisition is ever done.
     * <p>
     * Be aware that the entity stream is only updated at the beginning of the thread tick.
     *
     * @return the entities ticked in the current thread
     */
    // Début d'une méthode/d'un bloc
    static Stream<Entity> localEntities() {
        // Embranchement : vérifie une condition
        if (!(Thread.currentThread() instanceof TickThread tickThread)) return Stream.empty();
        // Renvoie une valeur à l'appelant
        return tickThread.entries.stream()
                // Instruction de code
                .flatMap(partitionEntry -> partitionEntry.elements().stream())
                // Instruction de code
                .filter(tickable -> tickable instanceof Entity)
                // Appelle une méthode
                .map(tickable -> (Entity) tickable);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Retrieve and reset acquiring time.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static long resetAcquiringTime() {
        // Renvoie une valeur à l'appelant
        return AcquirableImpl.WAIT_COUNTER_NANO.getAndSet(0);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new {@link Acquirable} object.
     * <p>
     * Mostly for internal use, as a {@link TickThread} has to be used
     * and properly synchronized.
     *
     * @param value the acquirable element
     * @param <T>   the acquirable element type
     * @return a new acquirable object
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static <T> Acquirable<T> unassigned(T value) {
        // Renvoie une valeur à l'appelant
        return new AcquirableImpl<>(value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Returns a new {@link Acquired} object which will be locked to the current thread.
     * <p>
     * Useful when your code cannot be done inside a callback and need to be sync.
     * Do not forget to call {@link Acquired#unlock()} once you are done with it.
     *
     * @return an acquired object
     * @throws IllegalStateException if the acquirable element is not initialized
     * @see #sync(Consumer) for auto-closeable capability
     * @see #applySync(Function) for auto-closeable capability
     */
    // Appelle une méthode
    Acquired<T> lock();

    /**
     * Retrieves the acquirable value if and only if the element
     * is already present/ticked in the current thread.
     * <p>
     * Useful when you want only want to acquire an element when you are guaranteed
     * to do not access any external thread.
     *
     * @return an optional containing the acquired element if safe
     * {@link Optional#empty()} otherwise
     */
    // Début d'une méthode/d'un bloc
    default Optional<T> local() {
        // Renvoie une valeur à l'appelant
        return isLocal() ? Optional.of(unwrap()) : Optional.empty();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the acquirable element is local to this thread
     *
     * @return true if the element is linked to the current thread
     */
    // Appelle une méthode
    boolean isLocal();

    /**
     * Retrieves the acquirable value if and only if the element
     * is already acquired/owned.
     * <p>
     * Useful when you want only want to acquire an element without depending
     * on any more lock.
     * <p>
     * Less strict than {@link #local()} as using an owned element may create contention.
     *
     * @return an optional containing the acquired element if safe
     * {@link Optional#empty()} otherwise
     */
    // Début d'une méthode/d'un bloc
    default Optional<T> owned() {
        // Renvoie une valeur à l'appelant
        return isOwned() ? Optional.of(unwrap()) : Optional.empty();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the acquirable element is owned by this thread.
     * Either by being local, or by already being acquired in the current scope.
     *
     * @return true if the element is linked to the current thread
     */
    // Appelle une méthode
    boolean isOwned();

    /**
     * Locks the acquirable element, execute {@code consumer} synchronously and unlock the thread.
     * <p>
     * Free if the element is already present in the current thread, blocking otherwise.
     *
     * @param consumer the callback to execute once the element has been safely acquired
     */
    // Appelle une méthode
    void sync(Consumer<T> consumer);

    /**
     * Try to cheaply lock the acquirable element, execute {@code consumer} synchronously and unlock the thread.
     * <p>
     * Returns false if there is contention.
     *
     * @param consumer the callback to execute once the element has been safely acquired
     * @return true if the consumer was executed, false otherwise
     */
    // Appelle une méthode
    boolean trySync(Consumer<T> consumer);

    /**
     * Locks the acquirable element, execute {@code function} synchronously and unlock the thread.
     * <p>
     * Free if the element is already present in the current thread, blocking otherwise.
     *
     * @param function the function to execute once the element has been safely acquired
     */
    // Début d'une méthode/d'un bloc
    default <R> R applySync(Function<T, R> function) {
        // Appelle une méthode
        Acquired<T> acquired = lock();
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return function.apply(acquired.get());
        // Début d'une méthode/d'un bloc
        } finally {
            // Appelle une méthode
            acquired.unlock();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }


    /**
     * Unwrap the contained object unsafely.
     * <p>
     * Should only be considered when thread-safety is not necessary (e.g. comparing positions)
     *
     * @return the unwrapped value
     */
    // Appelle une méthode
    T unwrap();

    /**
     * Gets the thread to which this acquirable element is assigned.
     * <p>
     * May change to one tick to the next.
     *
     * @return the assigned thread, null if not initialized (likely on the next tick)
     */
    // Annotation pour l'élément suivant
    @UnknownNullability
    // Appelle une méthode
    TickThread assignedThread();

    /**
     * Checks if the current thread owns the acquirable element.
     * <p>
     * Throws an {@link AcquirableOwnershipException} if not owned.
     * <p>
     * This method is only enabled when assertions are enabled or
     * {@link net.minestom.server.ServerFlag#ACQUIRABLE_STRICT} is set to true.
     *
     * @throws AcquirableOwnershipException if the current thread does not own the acquirable element
     */
    // Appelle une méthode
    void assertOwnership();
// Fin d'un bloc/d'une expression
}
