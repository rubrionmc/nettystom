// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Exception thrown when an acquirable element is accessed without proper ownership.
 */
// Type declaration (class/interface/enum/record)
public final class AcquirableOwnershipException extends RuntimeException {
    // Code statement
    private final Thread initThread;
    // Code statement
    private final @Nullable Thread assignedThread;
    // Code statement
    private final Object element;

    // Annotation for the following element
    @ApiStatus.Internal
    // Code statement
    public AcquirableOwnershipException(Thread initThread, @Nullable Thread assignedThread,
                                        // Start of a method/block
                                        Object element) {
        // Access to the current/parent object
        super(buildMessage(initThread, assignedThread, element));
        // Access to the current/parent object
        this.initThread = initThread;
        // Access to the current/parent object
        this.assignedThread = assignedThread;
        // Access to the current/parent object
        this.element = element;
    // End of a block/expression
    }

    // Code statement
    private static String buildMessage(Thread initThread, @Nullable Thread assignedThread,
                                       // Start of a method/block
                                       Object value) {
        // Calls a method
        final String valueString = value.toString();
        // Branch: checks a condition
        if (assignedThread != null) {
            // Returns a value to the caller
            return """
                    Thread ownership assertion failed for %s:
                      Current thread:  %s
                      Assigned thread: %s
                      Problem: The element is assigned to a different thread and is not currently owned.
                      Solution: Use Acquirable#sync() or Acquirable#lock() to acquire ownership before accessing the element.
                    """.formatted(valueString,
                    // Code statement
                    Thread.currentThread().getName(),
                    // Code statement
                    assignedThread.getName()
            // End of a block/expression
            );
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return """
                    Thread ownership assertion failed for %s:
                      Current thread:        %s
                      Initialization thread: %s
                      Problem: The element is not yet initialized and is being accessed from a different thread.
                      Solution: Handle the element in the same thread it has been initialized in until it is fully initialized.
                    """.formatted(valueString,
                    // Code statement
                    Thread.currentThread().getName(),
                    // Code statement
                    initThread.getName()
            // End of a block/expression
            );
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * The thread that initialized the acquirable element.
     */
    // Start of a method/block
    public Thread initThread() {
        // Returns a value to the caller
        return initThread;
    // End of a block/expression
    }

    /**
     * The thread to which the acquirable element is assigned.
     * May be null if the element is not yet initialized.
     */
    // Start of a method/block
    public @Nullable Thread assignedThread() {
        // Returns a value to the caller
        return assignedThread;
    // End of a block/expression
    }

    /**
     * The acquirable element that caused the ownership failure.
     */
    // Start of a method/block
    public Object element() {
        // Returns a value to the caller
        return element;
    // End of a block/expression
    }
// End of a block/expression
}
