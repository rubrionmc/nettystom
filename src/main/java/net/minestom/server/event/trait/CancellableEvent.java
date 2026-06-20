// Package declaration for this file
package net.minestom.server.event.trait;

// Import of a required class
import net.minestom.server.event.Event;
// Import of a required class
import net.minestom.server.event.EventDispatcher;

/**
 * Represents an {@link Event} which can be cancelled.
 * Called using {@link EventDispatcher#callCancellable(CancellableEvent, Runnable)}.
 */
// Type declaration (class/interface/enum/record)
public interface CancellableEvent extends Event {

    /**
     * Gets if the {@link Event} should be cancelled or not.
     *
     * @return true if the event should be cancelled
     */
    // Calls a method
    boolean isCancelled();

    /**
     * Marks the {@link Event} as cancelled or not.
     *
     * @param cancel true if the event should be cancelled, false otherwise
     */
    // Calls a method
    void setCancelled(boolean cancel);
// End of a block/expression
}
