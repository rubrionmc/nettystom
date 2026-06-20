// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import net.minestom.server.utils.collection.ConcurrentMessageQueues;
// Import of a required class
import org.jctools.queues.MessagePassingQueue;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.lang.ref.Cleaner;
// Import of a required class
import java.lang.ref.SoftReference;
// Import of a required class
import java.util.Collection;
// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.Supplier;
// Import of a required class
import java.util.function.UnaryOperator;

// Annotation for the following element
@ApiStatus.Internal
// Annotation for the following element
@ApiStatus.Experimental
// Type declaration (class/interface/enum/record)
public final class ObjectPool<T> {
    // Assigns a value
    private static final int QUEUE_SIZE = 32_768;
    // Calls a method
    private static final Cleaner CLEANER = Cleaner.create();

    // Code statement
    private final MessagePassingQueue<SoftReference<T>> pool;
    // Code statement
    private final Supplier<T> supplier;
    // Code statement
    private final UnaryOperator<T> sanitizer;

    // Start of a method/block
    public static <T> ObjectPool<T> pool(Supplier<T> supplier, UnaryOperator<T> sanitizer) {
        // Returns a value to the caller
        return new ObjectPool<>(supplier, sanitizer);
    // End of a block/expression
    }

    // Start of a method/block
    public static <T> ObjectPool<T> pool(Supplier<T> supplier) {
        // Returns a value to the caller
        return new ObjectPool<>(supplier, UnaryOperator.identity());
    // End of a block/expression
    }

    // Start of a method/block
    private ObjectPool(Supplier<T> supplier, UnaryOperator<T> sanitizer) {
        // Access to the current/parent object
        this.pool = ConcurrentMessageQueues.mpmcSpecialUnboundedArrayQueue(QUEUE_SIZE);
        // Access to the current/parent object
        this.supplier = supplier;
        // Access to the current/parent object
        this.sanitizer = sanitizer;
    // End of a block/expression
    }

    // Start of a method/block
    public T get() {
        // Code statement
        T result;
        // Code statement
        SoftReference<T> ref;
        // Loop: repeats a block
        while ((ref = pool.poll()) != null) {
            // Branch: checks a condition
            if ((result = ref.get()) != null) return result;
        // End of a block/expression
        }
        // Returns a value to the caller
        return supplier.get();
    // End of a block/expression
    }

    // Start of a method/block
    public T getAndRegister(Object ref) {
        // Calls a method
        T result = get();
        // Calls a method
        register(ref, result);
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Start of a method/block
    public void add(T object) {
        // Calls a method
        object = sanitizer.apply(object);
        // Access to the current/parent object
        this.pool.offer(new SoftReference<>(object));
    // End of a block/expression
    }

    // Start of a method/block
    public void clear() {
        // Access to the current/parent object
        this.pool.clear();
    // End of a block/expression
    }

    // Start of a method/block
    public int count() {
        // Returns a value to the caller
        return pool.size();
    // End of a block/expression
    }

    // Start of a method/block
    public void register(Object ref, AtomicReference<T> objectRef) {
        // Calls a method
        CLEANER.register(ref, new BufferRefCleaner<>(this, objectRef));
    // End of a block/expression
    }

    // Start of a method/block
    public void register(Object ref, T object) {
        // Calls a method
        CLEANER.register(ref, new BufferCleaner<>(this, object));
    // End of a block/expression
    }

    // Start of a method/block
    public void register(Object ref, Collection<T> objects) {
        // Calls a method
        CLEANER.register(ref, new BuffersCleaner<>(this, objects));
    // End of a block/expression
    }

    // Start of a method/block
    public Holder hold() {
        // Returns a value to the caller
        return new Holder(get());
    // End of a block/expression
    }

    // Start of a method/block
    public <R> R use(Function<T, R> function) {
        // Calls a method
        T object = get();
        // Exception handling
        try {
            // Returns a value to the caller
            return function.apply(object);
        // Start of a method/block
        } finally {
            // Calls a method
            add(object);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record BufferRefCleaner<T>(ObjectPool<T> pool, AtomicReference<T> objectRef) implements Runnable {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void run() {
            // Access to the current/parent object
            this.pool.add(objectRef.get());
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record BufferCleaner<T>(ObjectPool<T> pool, T object) implements Runnable {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void run() {
            // Access to the current/parent object
            this.pool.add(object);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private record BuffersCleaner<T>(ObjectPool<T> pool, Collection<T> objects) implements Runnable {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void run() {
            // Loop: repeats a block
            for (T buffer : objects) {
                // Access to the current/parent object
                this.pool.add(buffer);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public final class Holder implements AutoCloseable {
        // Code statement
        private final T object;
        // Calls a method
        private final AtomicBoolean closed = new AtomicBoolean(false);

        // Start of a method/block
        Holder(T object) {
            // Access to the current/parent object
            this.object = object;
        // End of a block/expression
        }

        // Start of a method/block
        public T get() {
            // Branch: checks a condition
            if (closed.get()) throw new IllegalStateException("Holder is closed");
            // Returns a value to the caller
            return object;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void close() {
            // Branch: checks a condition
            if (closed.compareAndSet(false, true)) {
                // Calls a method
                add(object);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
