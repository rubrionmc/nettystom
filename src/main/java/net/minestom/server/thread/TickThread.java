// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.Tickable;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.concurrent.CountDownLatch;
// Import of a required class
import java.util.concurrent.TimeUnit;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;
// Import of a required class
import java.util.concurrent.locks.LockSupport;
// Import of a required class
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread responsible for ticking {@link Chunk chunks} and {@link Entity entities}.
 * <p>
 * Created in {@link ThreadDispatcher}, and awaken every tick with a task to execute.
 */
// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public class TickThread extends MinestomThread {
    // Calls a method
    private final ReentrantLock lock = new ReentrantLock();
    // Code statement
    private volatile boolean stop;

    // Calls a method
    private final AtomicReference<CountDownLatch> latchRef = new AtomicReference<>();
    // Code statement
    private volatile long tickTimeNanos;

    // Assigns a value
    private long tickNum = 0;
    // Calls a method
    final List<ThreadDispatcherImpl.Partition> entries = new ArrayList<>();

    // Start of a method/block
    public TickThread(int number) {
        // Access to the current/parent object
        super(MinecraftServer.THREAD_NAME_TICK + "-" + number);
    // End of a block/expression
    }

    // Start of a method/block
    public TickThread(String name) {
        // Access to the current/parent object
        super(name);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void run() {
        // Code statement
        LockSupport.park(this); // Wait for first tick
        // Loop: repeats a block
        while (!stop) {
            // Calls a method
            final CountDownLatch latch = this.latchRef.get();
            // Branch: checks a condition
            if (latch == null) {
                // Should not happen, but just in case
                // Calls a method
                LockSupport.park(this);
                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }
            // Assigns a value
            final ReentrantLock lock = this.lock;
            // Calls a method
            lock.lock();
            // Exception handling
            try {
                // Calls a method
                tick();
            // Start of a method/block
            } catch (Exception e) {
                // Calls a method
                MinecraftServer.getExceptionManager().handleException(e);
            // Start of a method/block
            } finally {
                // Calls a method
                lock.unlock();
                // #acquire() callbacks
            // End of a block/expression
            }
            // Access to the current/parent object
            this.latchRef.set(null);
            // Calls a method
            latch.countDown();
            // Calls a method
            LockSupport.park(this);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    protected void tick() {
        // Assigns a value
        final ReentrantLock lock = this.lock;
        // Calls a method
        final long tickTime = TimeUnit.NANOSECONDS.toMillis(this.tickTimeNanos);
        // Loop: repeats a block
        for (ThreadDispatcherImpl.Partition entry : entries) {
            // Calls a method
            assert entry.thread() == this;
            // Calls a method
            final List<Tickable> elements = entry.elements();
            // Branch: checks a condition
            if (elements.isEmpty()) continue;
            // Loop: repeats a block
            for (Tickable element : elements) {
                // Branch: checks a condition
                if (lock.hasQueuedThreads()) {
                    // Calls a method
                    lock.unlock();
                    // #acquire() callbacks
                    // Calls a method
                    lock.lock();
                // End of a block/expression
                }
                // Exception handling
                try {
                    // Calls a method
                    assert assertElement(element);
                    // Calls a method
                    element.tick(tickTime);
                // Start of a method/block
                } catch (Throwable e) {
                    // Calls a method
                    MinecraftServer.getExceptionManager().handleException(e);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private boolean assertElement(Tickable element) {
        // Returns a value to the caller
        return !(element instanceof AcquirableSource<?> source)
                // Code statement
                || source.acquirable().assignedThread() == this &&
                // Calls a method
                source.acquirable().assignedThread().lock().isHeldByCurrentThread();
    // End of a block/expression
    }

    // Start of a method/block
    void startTick(CountDownLatch latch, long tickTimeNanos) {
        // Assigns a value
        CountDownLatch update = latchRef
                // Calls a method
                .updateAndGet(prevLatch -> prevLatch == null || prevLatch.getCount() == 0 ? latch : prevLatch);
        // Branch: checks a condition
        if (update != latch) {
            // Tick already in progress, wait for it to complete then start our own tick
            // Exception handling
            try {
                // Calls a method
                update.await();
            // Start of a method/block
            } catch (InterruptedException e) {
                // Throws an exception
                throw new RuntimeException(e);
            // End of a block/expression
            }
            // Calls a method
            startTick(latch, tickTimeNanos);
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Branch: checks a condition
        if (stop || entries.isEmpty()) {
            // Nothing to tick
            // Calls a method
            latch.countDown();
            // Returns a value to the caller
            return;
        // End of a block/expression
        }
        // Access to the current/parent object
        this.tickTimeNanos = tickTimeNanos;
        // Access to the current/parent object
        this.tickNum++;
        // Calls a method
        LockSupport.unpark(this);
    // End of a block/expression
    }

    /**
     * Gets the lock used to ensure the safety of entity acquisition.
     *
     * @return the thread lock
     */
    // Start of a method/block
    public ReentrantLock lock() {
        // Returns a value to the caller
        return lock;
    // End of a block/expression
    }

    // Start of a method/block
    public long getTick() {
        // Returns a value to the caller
        return tickNum;
    // End of a block/expression
    }

    // Start of a method/block
    void shutdown() {
        // Access to the current/parent object
        this.stop = true;
        // Calls a method
        LockSupport.unpark(this);
    // End of a block/expression
    }
// End of a block/expression
}
