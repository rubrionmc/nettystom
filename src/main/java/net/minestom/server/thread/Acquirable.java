// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Optional;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.stream.Stream;

// Annotation for the following element
@ApiStatus.Experimental
// Type declaration (class/interface/enum/record)
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
    // Start of a method/block
    static Stream<Entity> localEntities() {
        // Branch: checks a condition
        if (!(Thread.currentThread() instanceof TickThread tickThread)) return Stream.empty();
        // Returns a value to the caller
        return tickThread.entries.stream()
                // Code statement
                .flatMap(partitionEntry -> partitionEntry.elements().stream())
                // Code statement
                .filter(tickable -> tickable instanceof Entity)
                // Calls a method
                .map(tickable -> (Entity) tickable);
    // End of a block/expression
    }

    /**
     * Retrieve and reset acquiring time.
     */
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static long resetAcquiringTime() {
        // Returns a value to the caller
        return AcquirableImpl.WAIT_COUNTER_NANO.getAndSet(0);
    // End of a block/expression
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
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    static <T> Acquirable<T> unassigned(T value) {
        // Returns a value to the caller
        return new AcquirableImpl<>(value);
    // End of a block/expression
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
    // Calls a method
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
    // Start of a method/block
    default Optional<T> local() {
        // Returns a value to the caller
        return isLocal() ? Optional.of(unwrap()) : Optional.empty();
    // End of a block/expression
    }

    /**
     * Gets if the acquirable element is local to this thread
     *
     * @return true if the element is linked to the current thread
     */
    // Calls a method
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
    // Start of a method/block
    default Optional<T> owned() {
        // Returns a value to the caller
        return isOwned() ? Optional.of(unwrap()) : Optional.empty();
    // End of a block/expression
    }

    /**
     * Gets if the acquirable element is owned by this thread.
     * Either by being local, or by already being acquired in the current scope.
     *
     * @return true if the element is linked to the current thread
     */
    // Calls a method
    boolean isOwned();

    /**
     * Locks the acquirable element, execute {@code consumer} synchronously and unlock the thread.
     * <p>
     * Free if the element is already present in the current thread, blocking otherwise.
     *
     * @param consumer the callback to execute once the element has been safely acquired
     */
    // Calls a method
    void sync(Consumer<T> consumer);

    /**
     * Try to cheaply lock the acquirable element, execute {@code consumer} synchronously and unlock the thread.
     * <p>
     * Returns false if there is contention.
     *
     * @param consumer the callback to execute once the element has been safely acquired
     * @return true if the consumer was executed, false otherwise
     */
    // Calls a method
    boolean trySync(Consumer<T> consumer);

    /**
     * Locks the acquirable element, execute {@code function} synchronously and unlock the thread.
     * <p>
     * Free if the element is already present in the current thread, blocking otherwise.
     *
     * @param function the function to execute once the element has been safely acquired
     */
    // Start of a method/block
    default <R> R applySync(Function<T, R> function) {
        // Calls a method
        Acquired<T> acquired = lock();
        // Exception handling
        try {
            // Returns a value to the caller
            return function.apply(acquired.get());
        // Start of a method/block
        } finally {
            // Calls a method
            acquired.unlock();
        // End of a block/expression
        }
    // End of a block/expression
    }


    /**
     * Unwrap the contained object unsafely.
     * <p>
     * Should only be considered when thread-safety is not necessary (e.g. comparing positions)
     *
     * @return the unwrapped value
     */
    // Calls a method
    T unwrap();

    /**
     * Gets the thread to which this acquirable element is assigned.
     * <p>
     * May change to one tick to the next.
     *
     * @return the assigned thread, null if not initialized (likely on the next tick)
     */
    // Annotation for the following element
    @UnknownNullability
    // Calls a method
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
    // Calls a method
    void assertOwnership();
// End of a block/expression
}
