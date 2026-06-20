// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import net.minestom.server.Tickable;
// Import of a required class
import net.minestom.server.utils.collection.ConcurrentMessageQueues;
// Import of a required class
import org.jctools.queues.MessagePassingQueue;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Unmodifiable;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.CountDownLatch;
// Import of a required class
import java.util.function.IntFunction;

// Type declaration (class/interface/enum/record)
final class ThreadDispatcherImpl<P, E extends Tickable> implements ThreadDispatcher<P, E> {
    // Code statement
    private final ThreadProvider<P> provider;
    // Code statement
    private final List<TickThread> threads;

    // Partition -> dispatching context
    // Defines how computation is dispatched to the threads
    // Calls a method
    private final Map<P, Partition> partitions = new WeakHashMap<>();
    // Cache to retrieve the threading context from a tickable element
    // Calls a method
    private final Map<Tickable, Partition> elements = new WeakHashMap<>();
    // Queue to update partition linked thread
    // Calls a method
    private final ArrayDeque<P> partitionUpdateQueue = new ArrayDeque<>();

    // Requests consumed at the end of each tick
    // Calls a method
    private final MessagePassingQueue<Update<P, E>> updates = ConcurrentMessageQueues.mpscUnboundedArrayQueue(1024);

    // Code statement
    ThreadDispatcherImpl(ThreadProvider<P> provider, int threadCount,
                         // Start of a method/block
                         IntFunction<? extends TickThread> threadGenerator) {
        // Access to the current/parent object
        this.provider = provider;
        // Assigns a value
        TickThread[] threads = new TickThread[threadCount];
        // Calls a method
        Arrays.setAll(threads, threadGenerator);
        // Access to the current/parent object
        this.threads = List.of(threads);
    // End of a block/expression
    }

    // Annotation for the following element
    @Unmodifiable
    // Annotation for the following element
    @ApiStatus.Internal
    // Annotation for the following element
    @Override
    // Start of a method/block
    public List<TickThread> threads() {
        // Returns a value to the caller
        return threads;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public synchronized void updateAndAwait(long time) {
        // Update dispatcher
        // Access to the current/parent object
        this.updates.drain(update -> {
            // Multiple branching (switch/case)
            switch (update) {
                // Multiple branching (switch/case)
                case Update.PartitionLoad<P, E> chunkUpdate -> processLoadedPartition(chunkUpdate.partition());
                // Multiple branching (switch/case)
                case Update.PartitionUnload<P, E> partitionUnload ->
                        // Calls a method
                        processUnloadedPartition(partitionUnload.partition());
                // Multiple branching (switch/case)
                case Update.ElementUpdate<P, E> elementUpdate ->
                        // Calls a method
                        processUpdatedElement(elementUpdate.element(), elementUpdate.partition());
                // Multiple branching (switch/case)
                case Update.ElementRemove<P, E> elementRemove -> processRemovedElement(elementRemove.element());
                // Multiple branching (switch/case)
                case null, default -> throw new IllegalStateException("Unknown update type: " +
                        // Calls a method
                        (update == null ? "null" : update.getClass().getSimpleName()));
            // End of a block/expression
            }
        // End of a block/expression
        });
        // Tick all partitions
        // Calls a method
        CountDownLatch latch = new CountDownLatch(threads.size());
        // Loop: repeats a block
        for (TickThread thread : threads) thread.startTick(latch, time);
        // Exception handling
        try {
            // Calls a method
            latch.await();
        // Start of a method/block
        } catch (InterruptedException e) {
            // Throws an exception
            throw new RuntimeException(e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public synchronized void refreshThreads(long nanoTimeout) {
        // Multiple branching (switch/case)
        switch (provider.refreshType()) {
            // Multiple branching (switch/case)
            case NEVER -> {
                // Do nothing
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case ALWAYS -> {
                // Calls a method
                final long currentTime = System.nanoTime();
                // Calls a method
                int counter = partitionUpdateQueue.size();
                // Loop: repeats a block
                while (true) {
                    // Calls a method
                    final P partition = partitionUpdateQueue.pollFirst();
                    // Branch: checks a condition
                    if (partition == null) break;
                    // Update chunk's thread
                    // Calls a method
                    Partition partitionEntry = partitions.get(partition);
                    // Code statement
                    assert partitionEntry != null;
                    // Assigns a value
                    final TickThread previous = partitionEntry.thread;
                    // Calls a method
                    final TickThread next = retrieveThread(partition);
                    // Branch: checks a condition
                    if (next != previous) {
                        // Assigns a value
                        partitionEntry.thread = next;
                        // Calls a method
                        previous.entries.remove(partitionEntry);
                        // Calls a method
                        next.entries.add(partitionEntry);
                    // End of a block/expression
                    }
                    // Access to the current/parent object
                    this.partitionUpdateQueue.addLast(partition);
                    // Branch: checks a condition
                    if (--counter <= 0 || System.nanoTime() - currentTime >= nanoTimeout) {
                        // Breaks out of the loop/block
                        break;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void refreshThreads() {
        // Calls a method
        refreshThreads(Long.MAX_VALUE);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public synchronized void start() {
        // Access to the current/parent object
        this.threads.forEach(Thread::start);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isAlive() {
        // Loop: repeats a block
        for (TickThread thread : threads) {
            // Branch: checks a condition
            if (!thread.isAlive()) return false;
        // End of a block/expression
        }
        // Returns a value to the caller
        return !threads.isEmpty();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public synchronized void shutdown() {
        // Access to the current/parent object
        this.threads.forEach(TickThread::shutdown);
    // End of a block/expression
    }

    // Start of a method/block
    private TickThread retrieveThread(P partition) {
        // Calls a method
        final int threadId = provider.findThread(partition);
        // Calls a method
        final int index = Math.abs(threadId) % threads.size();
        // Returns a value to the caller
        return threads.get(index);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void signalUpdate(ThreadDispatcher.Update<P, E> update) {
        // Access to the current/parent object
        this.updates.relaxedOffer(update);
    // End of a block/expression
    }

    // Start of a method/block
    private void processLoadedPartition(P partition) {
        // Branch: checks a condition
        if (partitions.containsKey(partition)) return;
        // Calls a method
        final TickThread thread = retrieveThread(partition);
        // Calls a method
        final Partition partitionEntry = new Partition(thread);
        // Calls a method
        thread.entries.add(partitionEntry);
        // Access to the current/parent object
        this.partitions.put(partition, partitionEntry);
        // Access to the current/parent object
        this.partitionUpdateQueue.add(partition);
        // Branch: checks a condition
        if (partition instanceof Tickable tickable) {
            // Calls a method
            processUpdatedElement(tickable, partition);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void processUnloadedPartition(P partition) {
        // Calls a method
        final Partition partitionEntry = partitions.remove(partition);
        // Branch: checks a condition
        if (partitionEntry != null) {
            // Assigns a value
            TickThread thread = partitionEntry.thread;
            // Calls a method
            thread.entries.remove(partitionEntry);
        // End of a block/expression
        }
        // Access to the current/parent object
        this.partitionUpdateQueue.remove(partition);
        // Branch: checks a condition
        if (partition instanceof Tickable tickable) {
            // Calls a method
            processRemovedElement(tickable);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void processRemovedElement(Tickable tickable) {
        // Calls a method
        Partition partition = elements.get(tickable);
        // Branch: checks a condition
        if (partition != null) {
            // Calls a method
            partition.elements.remove(tickable);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private void processUpdatedElement(Tickable tickable, P partition) {
        // Code statement
        Partition partitionEntry;

        // Calls a method
        partitionEntry = elements.get(tickable);
        // Remove from previous list
        // Branch: checks a condition
        if (partitionEntry != null) {
            // Calls a method
            partitionEntry.elements.remove(tickable);
        // End of a block/expression
        }
        // Add to new list
        // Calls a method
        partitionEntry = partitions.get(partition);
        // Branch: checks a condition
        if (partitionEntry != null) {
            // Access to the current/parent object
            this.elements.put(tickable, partitionEntry);
            // Calls a method
            partitionEntry.elements.add(tickable);
            // Branch: checks a condition
            if (tickable instanceof AcquirableSource<?> acquirableSource) {
                // Calls a method
                ((AcquirableImpl<?>) acquirableSource.acquirable()).assign(partitionEntry.thread());
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * A data structure which may contain {@link Tickable}s, and is assigned a single {@link TickThread}.
     */
    // Start of a method/block
    public static final class Partition {
        // Code statement
        private TickThread thread;
        // Calls a method
        private final List<Tickable> elements = new ArrayList<>();

        // Start of a method/block
        private Partition(TickThread thread) {
            // Access to the current/parent object
            this.thread = thread;
        // End of a block/expression
        }

        /**
         * The {@link TickThread} used by this partition.
         * <p>
         * This method is marked internal to reflect {@link TickThread}s own internal status.
         *
         * @return the TickThread used by this partition
         */
        // Annotation for the following element
        @ApiStatus.Internal
        // Start of a method/block
        public TickThread thread() {
            // Returns a value to the caller
            return thread;
        // End of a block/expression
        }

        /**
         * The {@link Tickable}s assigned to this partition.
         *
         * @return the tickables assigned to this partition
         */
        // Start of a method/block
        public List<Tickable> elements() {
            // Returns a value to the caller
            return elements;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
