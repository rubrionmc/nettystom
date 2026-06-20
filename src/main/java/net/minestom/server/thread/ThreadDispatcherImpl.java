// Déclaration du paquet de ce fichier
package net.minestom.server.thread;

// Import d'une classe nécessaire
import net.minestom.server.Tickable;
// Import d'une classe nécessaire
import net.minestom.server.utils.collection.ConcurrentMessageQueues;
// Import d'une classe nécessaire
import org.jctools.queues.MessagePassingQueue;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Unmodifiable;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.concurrent.CountDownLatch;
// Import d'une classe nécessaire
import java.util.function.IntFunction;

// Déclaration de type (classe/interface/enum/record)
final class ThreadDispatcherImpl<P, E extends Tickable> implements ThreadDispatcher<P, E> {
    // Instruction de code
    private final ThreadProvider<P> provider;
    // Instruction de code
    private final List<TickThread> threads;

    // Partition -> dispatching context
    // Defines how computation is dispatched to the threads
    // Appelle une méthode
    private final Map<P, Partition> partitions = new WeakHashMap<>();
    // Cache to retrieve the threading context from a tickable element
    // Appelle une méthode
    private final Map<Tickable, Partition> elements = new WeakHashMap<>();
    // Queue to update partition linked thread
    // Appelle une méthode
    private final ArrayDeque<P> partitionUpdateQueue = new ArrayDeque<>();

    // Requests consumed at the end of each tick
    // Appelle une méthode
    private final MessagePassingQueue<Update<P, E>> updates = ConcurrentMessageQueues.mpscUnboundedArrayQueue(1024);

    // Instruction de code
    ThreadDispatcherImpl(ThreadProvider<P> provider, int threadCount,
                         // Début d'une méthode/d'un bloc
                         IntFunction<? extends TickThread> threadGenerator) {
        // Accès à l'objet courant/parent
        this.provider = provider;
        // Affecte une valeur
        TickThread[] threads = new TickThread[threadCount];
        // Appelle une méthode
        Arrays.setAll(threads, threadGenerator);
        // Accès à l'objet courant/parent
        this.threads = List.of(threads);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Unmodifiable
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public List<TickThread> threads() {
        // Renvoie une valeur à l'appelant
        return threads;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public synchronized void updateAndAwait(long time) {
        // Update dispatcher
        // Accès à l'objet courant/parent
        this.updates.drain(update -> {
            // Embranchement multiple (switch/case)
            switch (update) {
                // Embranchement multiple (switch/case)
                case Update.PartitionLoad<P, E> chunkUpdate -> processLoadedPartition(chunkUpdate.partition());
                // Embranchement multiple (switch/case)
                case Update.PartitionUnload<P, E> partitionUnload ->
                        // Appelle une méthode
                        processUnloadedPartition(partitionUnload.partition());
                // Embranchement multiple (switch/case)
                case Update.ElementUpdate<P, E> elementUpdate ->
                        // Appelle une méthode
                        processUpdatedElement(elementUpdate.element(), elementUpdate.partition());
                // Embranchement multiple (switch/case)
                case Update.ElementRemove<P, E> elementRemove -> processRemovedElement(elementRemove.element());
                // Embranchement multiple (switch/case)
                case null, default -> throw new IllegalStateException("Unknown update type: " +
                        // Appelle une méthode
                        (update == null ? "null" : update.getClass().getSimpleName()));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        });
        // Tick all partitions
        // Appelle une méthode
        CountDownLatch latch = new CountDownLatch(threads.size());
        // Boucle : répète un bloc
        for (TickThread thread : threads) thread.startTick(latch, time);
        // Gestion des exceptions
        try {
            // Appelle une méthode
            latch.await();
        // Début d'une méthode/d'un bloc
        } catch (InterruptedException e) {
            // Lève une exception
            throw new RuntimeException(e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public synchronized void refreshThreads(long nanoTimeout) {
        // Embranchement multiple (switch/case)
        switch (provider.refreshType()) {
            // Embranchement multiple (switch/case)
            case NEVER -> {
                // Do nothing
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case ALWAYS -> {
                // Appelle une méthode
                final long currentTime = System.nanoTime();
                // Appelle une méthode
                int counter = partitionUpdateQueue.size();
                // Boucle : répète un bloc
                while (true) {
                    // Appelle une méthode
                    final P partition = partitionUpdateQueue.pollFirst();
                    // Embranchement : vérifie une condition
                    if (partition == null) break;
                    // Update chunk's thread
                    // Appelle une méthode
                    Partition partitionEntry = partitions.get(partition);
                    // Instruction de code
                    assert partitionEntry != null;
                    // Affecte une valeur
                    final TickThread previous = partitionEntry.thread;
                    // Appelle une méthode
                    final TickThread next = retrieveThread(partition);
                    // Embranchement : vérifie une condition
                    if (next != previous) {
                        // Affecte une valeur
                        partitionEntry.thread = next;
                        // Appelle une méthode
                        previous.entries.remove(partitionEntry);
                        // Appelle une méthode
                        next.entries.add(partitionEntry);
                    // Fin d'un bloc/d'une expression
                    }
                    // Accès à l'objet courant/parent
                    this.partitionUpdateQueue.addLast(partition);
                    // Embranchement : vérifie une condition
                    if (--counter <= 0 || System.nanoTime() - currentTime >= nanoTimeout) {
                        // Interrompt la boucle/le bloc
                        break;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void refreshThreads() {
        // Appelle une méthode
        refreshThreads(Long.MAX_VALUE);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public synchronized void start() {
        // Accès à l'objet courant/parent
        this.threads.forEach(Thread::start);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean isAlive() {
        // Boucle : répète un bloc
        for (TickThread thread : threads) {
            // Embranchement : vérifie une condition
            if (!thread.isAlive()) return false;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return !threads.isEmpty();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public synchronized void shutdown() {
        // Accès à l'objet courant/parent
        this.threads.forEach(TickThread::shutdown);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private TickThread retrieveThread(P partition) {
        // Appelle une méthode
        final int threadId = provider.findThread(partition);
        // Appelle une méthode
        final int index = Math.abs(threadId) % threads.size();
        // Renvoie une valeur à l'appelant
        return threads.get(index);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void signalUpdate(ThreadDispatcher.Update<P, E> update) {
        // Accès à l'objet courant/parent
        this.updates.relaxedOffer(update);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void processLoadedPartition(P partition) {
        // Embranchement : vérifie une condition
        if (partitions.containsKey(partition)) return;
        // Appelle une méthode
        final TickThread thread = retrieveThread(partition);
        // Appelle une méthode
        final Partition partitionEntry = new Partition(thread);
        // Appelle une méthode
        thread.entries.add(partitionEntry);
        // Accès à l'objet courant/parent
        this.partitions.put(partition, partitionEntry);
        // Accès à l'objet courant/parent
        this.partitionUpdateQueue.add(partition);
        // Embranchement : vérifie une condition
        if (partition instanceof Tickable tickable) {
            // Appelle une méthode
            processUpdatedElement(tickable, partition);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void processUnloadedPartition(P partition) {
        // Appelle une méthode
        final Partition partitionEntry = partitions.remove(partition);
        // Embranchement : vérifie une condition
        if (partitionEntry != null) {
            // Affecte une valeur
            TickThread thread = partitionEntry.thread;
            // Appelle une méthode
            thread.entries.remove(partitionEntry);
        // Fin d'un bloc/d'une expression
        }
        // Accès à l'objet courant/parent
        this.partitionUpdateQueue.remove(partition);
        // Embranchement : vérifie une condition
        if (partition instanceof Tickable tickable) {
            // Appelle une méthode
            processRemovedElement(tickable);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void processRemovedElement(Tickable tickable) {
        // Appelle une méthode
        Partition partition = elements.get(tickable);
        // Embranchement : vérifie une condition
        if (partition != null) {
            // Appelle une méthode
            partition.elements.remove(tickable);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void processUpdatedElement(Tickable tickable, P partition) {
        // Instruction de code
        Partition partitionEntry;

        // Appelle une méthode
        partitionEntry = elements.get(tickable);
        // Remove from previous list
        // Embranchement : vérifie une condition
        if (partitionEntry != null) {
            // Appelle une méthode
            partitionEntry.elements.remove(tickable);
        // Fin d'un bloc/d'une expression
        }
        // Add to new list
        // Appelle une méthode
        partitionEntry = partitions.get(partition);
        // Embranchement : vérifie une condition
        if (partitionEntry != null) {
            // Accès à l'objet courant/parent
            this.elements.put(tickable, partitionEntry);
            // Appelle une méthode
            partitionEntry.elements.add(tickable);
            // Embranchement : vérifie une condition
            if (tickable instanceof AcquirableSource<?> acquirableSource) {
                // Appelle une méthode
                ((AcquirableImpl<?>) acquirableSource.acquirable()).assign(partitionEntry.thread());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * A data structure which may contain {@link Tickable}s, and is assigned a single {@link TickThread}.
     */
    // Début d'une méthode/d'un bloc
    public static final class Partition {
        // Instruction de code
        private TickThread thread;
        // Appelle une méthode
        private final List<Tickable> elements = new ArrayList<>();

        // Début d'une méthode/d'un bloc
        private Partition(TickThread thread) {
            // Accès à l'objet courant/parent
            this.thread = thread;
        // Fin d'un bloc/d'une expression
        }

        /**
         * The {@link TickThread} used by this partition.
         * <p>
         * This method is marked internal to reflect {@link TickThread}s own internal status.
         *
         * @return the TickThread used by this partition
         */
        // Annotation pour l'élément suivant
        @ApiStatus.Internal
        // Début d'une méthode/d'un bloc
        public TickThread thread() {
            // Renvoie une valeur à l'appelant
            return thread;
        // Fin d'un bloc/d'une expression
        }

        /**
         * The {@link Tickable}s assigned to this partition.
         *
         * @return the tickables assigned to this partition
         */
        // Début d'une méthode/d'un bloc
        public List<Tickable> elements() {
            // Renvoie une valeur à l'appelant
            return elements;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
