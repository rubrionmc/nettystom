// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import net.minestom.server.Tickable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Unmodifiable;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
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
// Déclaration de type (classe/interface/enum/record)
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    static <P, E extends Tickable> ThreadDispatcher<P, E> dispatcher(ThreadProvider<P> provider, int threadCount) {
        // Renvoie une valeur à l'appelant
        return new ThreadDispatcherImpl<>(provider, threadCount, TickThread::new);
    // Fin d'un bloc/d'une expression
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
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Instruction de code
    static <P, E extends Tickable> ThreadDispatcher<P, E> dispatcher(ThreadProvider<P> provider,
                                                                              // Début d'une méthode/d'un bloc
                                                                              IntFunction<String> nameGenerator, int threadCount) {
        // Renvoie une valeur à l'appelant
        return new ThreadDispatcherImpl<>(provider, threadCount, index -> new TickThread(nameGenerator.apply(index)));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a single-threaded dispatcher that uses default thread names.
     * <p>Remember to start the dispatcher using {@link #start()}</p>
     *
     * @param <P> the dispatcher partition type
     * @return a new ThreadDispatcher instance
     */
    // Annotation pour l'élément suivant
    @Contract(pure = true)
    // Début d'une méthode/d'un bloc
    static <P, E extends Tickable> ThreadDispatcher<P, E> singleThread() {
        // Renvoie une valeur à l'appelant
        return dispatcher(ThreadProvider.counter(), 1);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the unmodifiable list of TickThreads used to dispatch updates.
     * <p>
     * This method is marked internal to reflect {@link TickThread}s own internal status.
     *
     * @return the TickThreads used to dispatch updates
     */
    // Annotation pour l'élément suivant
    @Unmodifiable
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Appelle une méthode
    List<TickThread> threads();

    /**
     * Prepares the update by creating the {@link TickThread} tasks.
     *
     * @param time the tick time in nanos
     */
    // Appelle une méthode
    void updateAndAwait(long time);

    /**
     * Called at the end of each tick to clear removed tickables, refresh the partition linked to a tickable, and
     * partition threads based on {@link ThreadProvider#findThread(Object)}.
     *
     * @param nanoTimeout max time in nanoseconds to update partitions
     */
    // Appelle une méthode
    void refreshThreads(long nanoTimeout);

    /**
     * Refreshes all thread as per {@link ThreadDispatcher#refreshThreads(long)}, with a timeout of
     * {@link Long#MAX_VALUE}.
     */
    // Début d'une méthode/d'un bloc
    default void refreshThreads() {
        // Appelle une méthode
        refreshThreads(Long.MAX_VALUE);
    // Fin d'un bloc/d'une expression
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
    // Appelle une méthode
    void signalUpdate(ThreadDispatcher.Update<P, E> update);

    // Début d'une méthode/d'un bloc
    default void createPartition(P partition) {
        // Appelle une méthode
        signalUpdate(new Update.PartitionLoad<>(partition));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void deletePartition(P partition) {
        // Appelle une méthode
        signalUpdate(new Update.PartitionUnload<>(partition));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void updateElement(E element, P partition) {
        // Appelle une méthode
        signalUpdate(new Update.ElementUpdate<>(element, partition));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    default void removeElement(E element) {
        // Appelle une méthode
        signalUpdate(new Update.ElementRemove<>(element));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Starts all the {@link TickThread tick threads}.
     * <p>
     * This will throw an {@link IllegalThreadStateException} if the threads have already been started.
     */
    // Appelle une méthode
    void start();

    /**
     * Checks if all the {@link TickThread tick threads} are alive.
     *
     * @return true if all threads are alive, false otherwise
     */
    // Appelle une méthode
    boolean isAlive();

    /**
     * Shutdowns all the {@link TickThread tick threads}.
     * <p>
     * Action is irreversible.
     */
    // Appelle une méthode
    void shutdown();

    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Annotation pour l'élément suivant
    @SuppressWarnings("unused")
    // Déclaration de type (classe/interface/enum/record)
    sealed interface Update<P, E> {

        /**
         * Registers a new partition.
         *
         * @param partition the partition to register
         */
        // Déclaration de type (classe/interface/enum/record)
        record PartitionLoad<P, E>(P partition) implements Update<P, E> {
        // Fin d'un bloc/d'une expression
        }

        /**
         * Deletes an existing partition.
         *
         * @param partition the partition to delete
         */
        // Déclaration de type (classe/interface/enum/record)
        record PartitionUnload<P, E>(P partition) implements Update<P, E> {
        // Fin d'un bloc/d'une expression
        }

        /**
         * Updates an element}, signalling that it is a part of {@code partition}.
         *
         * @param element   the element to update
         * @param partition the partition the Tickable is part of
         */
        // Déclaration de type (classe/interface/enum/record)
        record ElementUpdate<P, E>(E element, P partition) implements Update<P, E> {
        // Fin d'un bloc/d'une expression
        }

        /**
         * Removes an element.
         *
         * @param element the element to remove
         */
        // Déclaration de type (classe/interface/enum/record)
        record ElementRemove<P, E>(E element) implements Update<P, E> {
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
