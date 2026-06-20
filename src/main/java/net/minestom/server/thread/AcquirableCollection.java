// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.concurrent.locks.ReentrantLock;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.stream.Stream;

// Annotation for the following element
@ApiStatus.Experimental
// Type declaration (class/interface/enum/record)
public class AcquirableCollection<E> implements Collection<Acquirable<E>> {
    // Code statement
    private final Collection<Acquirable<E>> acquirableCollection;

    // Start of a method/block
    public AcquirableCollection(Collection<Acquirable<E>> acquirableCollection) {
        // Access to the current/parent object
        this.acquirableCollection = acquirableCollection;
    // End of a block/expression
    }

    // Start of a method/block
    public void acquireSync(Consumer<E> consumer) {
        // Calls a method
        final Map<TickThread, List<E>> threadEntitiesMap = retrieveOptionalThreadMap(acquirableCollection, consumer);
        // Acquire all the threads one by one
        // Loop: repeats a block
        for (Map.Entry<TickThread, List<E>> entry : threadEntitiesMap.entrySet()) {
            // Calls a method
            final TickThread tickThread = entry.getKey();
            // Calls a method
            ReentrantLock lock = AcquirableImpl.enter(tickThread);
            // Exception handling
            try {
                // Calls a method
                final List<E> values = entry.getValue();
                // Loop: repeats a block
                for (E value : values) {
                    // Calls a method
                    consumer.accept(value);
                // End of a block/expression
                }
            // Start of a method/block
            } finally {
                // Calls a method
                AcquirableImpl.leave(lock);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public Stream<E> unwrap() {
        // Returns a value to the caller
        return acquirableCollection.stream().map(Acquirable::unwrap);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int size() {
        // Returns a value to the caller
        return acquirableCollection.size();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isEmpty() {
        // Returns a value to the caller
        return acquirableCollection.isEmpty();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean contains(Object o) {
        // Returns a value to the caller
        return acquirableCollection.contains(o);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Iterator<Acquirable<E>> iterator() {
        // Returns a value to the caller
        return acquirableCollection.iterator();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Object[] toArray() {
        // Returns a value to the caller
        return acquirableCollection.toArray();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T> T[] toArray(T[] a) {
        // Returns a value to the caller
        return acquirableCollection.toArray(a);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean add(Acquirable<E> eAcquirable) {
        // Returns a value to the caller
        return acquirableCollection.add(eAcquirable);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean remove(Object o) {
        // Returns a value to the caller
        return acquirableCollection.remove(o);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean containsAll(Collection<?> c) {
        // Returns a value to the caller
        return acquirableCollection.containsAll(c);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean addAll(Collection<? extends Acquirable<E>> c) {
        // Returns a value to the caller
        return acquirableCollection.addAll(c);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean removeAll(Collection<?> c) {
        // Returns a value to the caller
        return acquirableCollection.removeAll(c);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean retainAll(Collection<?> c) {
        // Returns a value to the caller
        return acquirableCollection.retainAll(c);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void clear() {
        // Access to the current/parent object
        this.acquirableCollection.clear();
    // End of a block/expression
    }

    /**
     * @param collection the acquirable collection
     * @param consumer   the consumer to execute when an element is already in the current thread
     * @return a new Thread to acquirable elements map
     */
    // Code statement
    protected static <T> Map<TickThread, List<T>> retrieveOptionalThreadMap(Collection<Acquirable<T>> collection,
                                                                            // Start of a method/block
                                                                            Consumer<T> consumer) {
        // Separate a collection of acquirable elements into a map of thread->elements
        // Useful to reduce the number of acquisition
        // Calls a method
        Map<TickThread, List<T>> threadCacheMap = new HashMap<>();
        // Loop: repeats a block
        for (var element : collection) {
            // Calls a method
            final T value = element.unwrap();
            // Calls a method
            final TickThread elementThread = element.assignedThread();
            // Branch: checks a condition
            if (Thread.currentThread() == elementThread) {
                // The element is managed in the current thread, consumer can be immediately called
                // Calls a method
                consumer.accept(value);
            // Alternative branch of the condition
            } else {
                // The element is manager in a different thread, cache it
                // Calls a method
                List<T> threadCacheList = threadCacheMap.computeIfAbsent(elementThread, tickThread -> new ArrayList<>());
                // Calls a method
                threadCacheList.add(value);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return threadCacheMap;
    // End of a block/expression
    }
// End of a block/expression
}
