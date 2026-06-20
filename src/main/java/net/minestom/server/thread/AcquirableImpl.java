// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;

// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.concurrent.atomic.AtomicLong;
// Import of a required class
import java.util.concurrent.locks.ReentrantLock;
// Import of a required class
import java.util.function.Consumer;

// Type declaration (class/interface/enum/record)
final class AcquirableImpl<T> implements Acquirable<T> {
    // Calls a method
    private static final boolean ASSERTIONS_ENABLED = AcquirableImpl.class.desiredAssertionStatus();
    // Calls a method
    static final AtomicLong WAIT_COUNTER_NANO = new AtomicLong();

    /**
     * Global lock used for synchronization.
     */
    // Calls a method
    static final ReentrantLock GLOBAL_LOCK = new ReentrantLock();

    // Code statement
    private final T value;
    // Calls a method
    private final Thread initThread = Thread.currentThread();
    // Code statement
    private volatile @Nullable TickThread assignedThread;

    // Start of a method/block
    public AcquirableImpl(T value) {
        // Access to the current/parent object
        this.value = value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Acquired<T> lock() {
        // Assigns a value
        final TickThread assignedThread = this.assignedThread;
        // Branch: checks a condition
        if (assignedThread == null) {
            // Calls a method
            assertInitThread();
            // Returns a value to the caller
            return new AcquiredImpl<>(unwrap(), null);
        // End of a block/expression
        }
        // Calls a method
        ReentrantLock lock = enter(assignedThread);
        // Calls a method
        assert assignedThread.lock().isHeldByCurrentThread();
        // Returns a value to the caller
        return new AcquiredImpl<>(unwrap(), lock);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isLocal() {
        // Assigns a value
        final TickThread assignedThread = this.assignedThread;
        // Returns a value to the caller
        return Thread.currentThread() == Objects.requireNonNullElse(assignedThread, initThread);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isOwned() {
        // Assigns a value
        final TickThread assignedThread = this.assignedThread;
        // Branch: checks a condition
        if (assignedThread == null) return Thread.currentThread() == initThread;
        // Returns a value to the caller
        return AcquirableImpl.isOwnedImpl(assignedThread);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void sync(Consumer<T> consumer) {
        // Assigns a value
        final TickThread assignedThread = this.assignedThread;
        // Branch: checks a condition
        if (assignedThread == null) {
            // Calls a method
            assertInitThread();
            // Calls a method
            consumer.accept(unwrap());
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Calls a method
        ReentrantLock lock = enter(assignedThread);
        // Exception handling
        try {
            // Calls a method
            assert assignedThread.lock().isHeldByCurrentThread();
            // Calls a method
            consumer.accept(unwrap());
        // Start of a method/block
        } finally {
            // Calls a method
            leave(lock);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean trySync(Consumer<T> consumer) {
        // Branch: checks a condition
        if (isOwned()) {
            // Calls a method
            consumer.accept(unwrap());
            // Returns a value to the caller
            return true;
        // End of a block/expression
        }
        // Assigns a value
        TickThread assignedThread = this.assignedThread;
        // Branch: checks a condition
        if (assignedThread != null) {
            // Calls a method
            ReentrantLock lock = assignedThread.lock();
            // Branch: checks a condition
            if (lock.tryLock()) {
                // Exception handling
                try {
                    // Calls a method
                    consumer.accept(unwrap());
                    // Returns a value to the caller
                    return true;
                // Start of a method/block
                } finally {
                    // Calls a method
                    lock.unlock();
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public T unwrap() {
        // Returns a value to the caller
        return value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public @UnknownNullability TickThread assignedThread() {
        // Returns a value to the caller
        return assignedThread;
    // End of a block/expression
    }

    // Start of a method/block
    void assign(TickThread thread) {
        // Access to the current/parent object
        this.assignedThread = thread;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void assertOwnership() {
        // Branch: checks a condition
        if (!ASSERTIONS_ENABLED && !ServerFlag.ACQUIRABLE_STRICT) return;
        // Branch: checks a condition
        if (isOwned()) return;
        // Assigns a value
        TickThread assignedThread = this.assignedThread;
        // Assigns a value
        Thread initThread = this.initThread;
        // Branch: checks a condition
        if (assignedThread == null && Thread.currentThread() == initThread) return;
        // Throws an exception
        throw new AcquirableOwnershipException(initThread, assignedThread, unwrap().toString());
    // End of a block/expression
    }

    // Start of a method/block
    void assertInitThread() {
        // Branch: checks a condition
        if (Thread.currentThread() != initThread)
            // Throws an exception
            throw new IllegalStateException("Cannot lock an uninitialized Acquirable from a different thread");
    // End of a block/expression
    }

    // Start of a method/block
    static boolean isOwnedImpl(TickThread elementThread) {
        // Branch: checks a condition
        if (Thread.currentThread() == elementThread) return true;
        // Returns a value to the caller
        return elementThread.lock().isHeldByCurrentThread();
    // End of a block/expression
    }

    // Start of a method/block
    static @Nullable ReentrantLock enter(TickThread elementThread) {
        // Branch: checks a condition
        if (isOwnedImpl(elementThread)) return null; // Nothing to lock, already owned by the current thread.
        // Calls a method
        final long time = System.nanoTime();
        // Enter the target thread
        // Branch: checks a condition
        if (Thread.currentThread() instanceof TickThread tickThread && tickThread.lock().isHeldByCurrentThread()) {
            // Loop: repeats a block
            while (!GLOBAL_LOCK.tryLock()) {
                // Calls a method
                tickThread.lock().unlock();
                // Calls a method
                tickThread.lock().lock();
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Calls a method
            GLOBAL_LOCK.lock();
        // End of a block/expression
        }
        // Calls a method
        final ReentrantLock targetLock = elementThread.lock();
        // Calls a method
        targetLock.lock();
        // Calls a method
        WAIT_COUNTER_NANO.addAndGet(System.nanoTime() - time);
        // Returns a value to the caller
        return targetLock;
    // End of a block/expression
    }

    // Start of a method/block
    static void leave(@Nullable ReentrantLock lock) {
        // Branch: checks a condition
        if (lock != null) {
            // Calls a method
            lock.unlock();
            // Calls a method
            GLOBAL_LOCK.unlock();
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
