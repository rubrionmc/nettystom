// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import net.minestom.server.Tickable;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Unmodifiable;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.function.IntFunction;

/**
 * ThreadDispatcher can be used to dispatch updates (ticks) across a number of "partitions" (such as chunks) that
 * house {@link Tickable} instances (such as entities). The parallelism of such updates is defined when the dispatcher
 * is constructed.
 * <p>
 * It is recommended that {@link Tickable}s being added to a dispatcher also implement {@link AcquirableSource}, as
 * doing so will allow the user to synchronize external access to them using the {@link Acquirable} API.
 * <p>
 * Instances of this class can be obtained by calling {@link ThreadDispatcher#dispatcher(ThreadProvider, int)}, or a similar
 * overload.
 *
 * @see Acquirable
 * @see AcquirableSource
 */
// Type declaration (class/interface/enum/record)
public sealed interface ThreadDispatcher<P, E extends Tickable> permits ThreadDispatcherImpl {
    /**
     * Creates a new ThreadDispatcher using default thread names (ex. Ms-Tick-n).
     * <p>Remember to start the dispatcher using {@link #start()}</p>
     *
     * @param provider    the {@link ThreadProvider} instance to be used for defining thread IDs
     * @param threadCount the number of threads to create for this dispatcher
     * @param <P>         the dispatcher partition type
     * @return a new ThreadDispatcher instance
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    static <P, E extends Tickable> ThreadDispatcher<P, E> dispatcher(ThreadProvider<P> provider, int threadCount) {
        // Returns a value to the caller
        return new ThreadDispatcherImpl<>(provider, threadCount, TickThread::new);
    // End of a block/expression
    }

    /**
     * Creates a new ThreadDispatcher using the caller-provided thread name generator {@code nameGenerator}. This is
     * useful to disambiguate custom ThreadDispatcher instances from ones used in core Minestom code.
     * <p>Remember to start the dispatcher using {@link #start()}</p>
     *
     * @param provider      the {@link ThreadProvider} instance to be used for defining thread IDs
     * @param nameGenerator a function that should return unique names, given a thread index
     * @param threadCount   the number of threads to create for this dispatcher
     * @param <P>           the dispatcher partition type
     * @return a new ThreadDispatcher instance
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Code statement
    static <P, E extends Tickable> ThreadDispatcher<P, E> dispatcher(ThreadProvider<P> provider,
                                                                              // Start of a method/block
                                                                              IntFunction<String> nameGenerator, int threadCount) {
        // Returns a value to the caller
        return new ThreadDispatcherImpl<>(provider, threadCount, index -> new TickThread(nameGenerator.apply(index)));
    // End of a block/expression
    }

    /**
     * Creates a single-threaded dispatcher that uses default thread names.
     * <p>Remember to start the dispatcher using {@link #start()}</p>
     *
     * @param <P> the dispatcher partition type
     * @return a new ThreadDispatcher instance
     */
    // Annotation for the following element
    @Contract(pure = true)
    // Start of a method/block
    static <P, E extends Tickable> ThreadDispatcher<P, E> singleThread() {
        // Returns a value to the caller
        return dispatcher(ThreadProvider.counter(), 1);
    // End of a block/expression
    }

    /**
     * Gets the unmodifiable list of TickThreads used to dispatch updates.
     * <p>
     * This method is marked internal to reflect {@link TickThread}s own internal status.
     *
     * @return the TickThreads used to dispatch updates
     */
    // Annotation for the following element
    @Unmodifiable
    // Annotation for the following element
    @ApiStatus.Internal
    // Calls a method
    List<TickThread> threads();

    /**
     * Prepares the update by creating the {@link TickThread} tasks.
     *
     * @param time the tick time in nanos
     */
    // Calls a method
    void updateAndAwait(long time);

    /**
     * Called at the end of each tick to clear removed tickables, refresh the partition linked to a tickable, and
     * partition threads based on {@link ThreadProvider#findThread(Object)}.
     *
     * @param nanoTimeout max time in nanoseconds to update partitions
     */
    // Calls a method
    void refreshThreads(long nanoTimeout);

    /**
     * Refreshes all thread as per {@link ThreadDispatcher#refreshThreads(long)}, with a timeout of
     * {@link Long#MAX_VALUE}.
     */
    // Start of a method/block
    default void refreshThreads() {
        // Calls a method
        refreshThreads(Long.MAX_VALUE);
    // End of a block/expression
    }

    /**
     * Signals an update to the dispatcher.
     * <p>
     * This method is used to notify the dispatcher of changes that need to be processed, such as partition loads,
     * unloads, or element updates.
     * <p>
     * Updates are processed at the start of each tick, before the actual ticking of elements.
     *
     * @param update the update to signal
     */
    // Calls a method
    void signalUpdate(ThreadDispatcher.Update<P, E> update);

    // Start of a method/block
    default void createPartition(P partition) {
        // Calls a method
        signalUpdate(new Update.PartitionLoad<>(partition));
    // End of a block/expression
    }

    // Start of a method/block
    default void deletePartition(P partition) {
        // Calls a method
        signalUpdate(new Update.PartitionUnload<>(partition));
    // End of a block/expression
    }

    // Start of a method/block
    default void updateElement(E element, P partition) {
        // Calls a method
        signalUpdate(new Update.ElementUpdate<>(element, partition));
    // End of a block/expression
    }

    // Start of a method/block
    default void removeElement(E element) {
        // Calls a method
        signalUpdate(new Update.ElementRemove<>(element));
    // End of a block/expression
    }

    /**
     * Starts all the {@link TickThread tick threads}.
     * <p>
     * This will throw an {@link IllegalThreadStateException} if the threads have already been started.
     */
    // Calls a method
    void start();

    /**
     * Checks if all the {@link TickThread tick threads} are alive.
     *
     * @return true if all threads are alive, false otherwise
     */
    // Calls a method
    boolean isAlive();

    /**
     * Shutdowns all the {@link TickThread tick threads}.
     * <p>
     * Action is irreversible.
     */
    // Calls a method
    void shutdown();

    // Annotation for the following element
    @ApiStatus.Internal
    // Annotation for the following element
    @SuppressWarnings("unused")
    // Type declaration (class/interface/enum/record)
    sealed interface Update<P, E> {

        /**
         * Registers a new partition.
         *
         * @param partition the partition to register
         */
        // Type declaration (class/interface/enum/record)
        record PartitionLoad<P, E>(P partition) implements Update<P, E> {
        // End of a block/expression
        }

        /**
         * Deletes an existing partition.
         *
         * @param partition the partition to delete
         */
        // Type declaration (class/interface/enum/record)
        record PartitionUnload<P, E>(P partition) implements Update<P, E> {
        // End of a block/expression
        }

        /**
         * Updates an element}, signalling that it is a part of {@code partition}.
         *
         * @param element   the element to update
         * @param partition the partition the Tickable is part of
         */
        // Type declaration (class/interface/enum/record)
        record ElementUpdate<P, E>(E element, P partition) implements Update<P, E> {
        // End of a block/expression
        }

        /**
         * Removes an element.
         *
         * @param element the element to remove
         */
        // Type declaration (class/interface/enum/record)
        record ElementRemove<P, E>(E element) implements Update<P, E> {
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
