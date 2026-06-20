// Package declaration for this file
package net.minestom.server.thread;

// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Function;

/**
 * An object that is a source of {@link Acquirable} objects, and can be synchronized within a {@link ThreadDispatcher}.
 *
 * @param <T> the type of the acquired object
 */
// Annotation for the following element
@ApiStatus.Experimental
// Type declaration (class/interface/enum/record)
public interface AcquirableSource<T> {
    /**
     * Obtains an {@link Acquirable}. To safely perform operations on this object, the user must call
     * {@link Acquirable#sync(Consumer)}, {@link Acquirable#applySync(Function)}, or {@link Acquirable#lock()} (followed by
     * a subsequent unlock) on the Acquirable instance.
     *
     * @return an Acquirable which can be used to synchronize access to this object
     */
    // Calls a method
    Acquirable<? extends T> acquirable();
// End of a block/expression
}
