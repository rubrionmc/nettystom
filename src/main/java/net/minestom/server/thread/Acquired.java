// Package declaration for this file
package net.minestom.server.thread;

/**
 * Represents an object that has been safely acquired and can be freed again.
 * <p>
 * This class should not be shared, and it is recommended to call {@link #unlock()}
 * once the acquisition goal has been fulfilled to limit blocking time.
 *
 * @param <T> the type of the acquired object
 */
// Type declaration (class/interface/enum/record)
public sealed interface Acquired<T> permits AcquiredImpl {
    // Calls a method
    T get();

    // Calls a method
    void unlock();
// End of a block/expression
}
