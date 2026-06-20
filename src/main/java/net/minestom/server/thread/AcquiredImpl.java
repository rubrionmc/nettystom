// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.concurrent.locks.ReentrantLock;

// Type declaration (class/interface/enum/record)
final class AcquiredImpl<T> implements Acquired<T> {
    // Code statement
    private final T value;
    // Code statement
    private final Thread owner;
    // Code statement
    private final @Nullable ReentrantLock lock;
    // Code statement
    private boolean unlocked;

    // Start of a method/block
    AcquiredImpl(T value, @Nullable ReentrantLock lock) {
        // Access to the current/parent object
        this.value = value;
        // Access to the current/parent object
        this.owner = Thread.currentThread();
        // Access to the current/parent object
        this.lock = lock;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public T get() {
        // Calls a method
        safeCheck();
        // Returns a value to the caller
        return value;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public void unlock() {
        // Calls a method
        safeCheck();
        // Access to the current/parent object
        this.unlocked = true;
        // Calls a method
        AcquirableImpl.leave(lock);
    // End of a block/expression
    }

    // Start of a method/block
    private void safeCheck() {
        // Calls a method
        Check.stateCondition(Thread.currentThread() != owner, "Acquired object is owned by the thread {0}", owner);
        // Calls a method
        Check.stateCondition(unlocked, "The acquired element has already been unlocked!");
    // End of a block/expression
    }
// End of a block/expression
}
