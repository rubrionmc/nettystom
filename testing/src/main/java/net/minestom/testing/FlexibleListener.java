// Package declaration for this file
package net.minestom.testing;

// Import of a required class
import net.minestom.server.event.Event;

// Import of a required class
import java.util.function.Consumer;

// Type declaration (class/interface/enum/record)
public interface FlexibleListener<E extends Event> {
    /**
     * Updates the handler. Fails if the previous followup has not been called.
     */
    // Calls a method
    void followup(Consumer<E> handler);

    // Start of a method/block
    default void followup() {
        // Start of a method/block
        followup(event -> {
            // Empty
        // End of a block/expression
        });
    // End of a block/expression
    }

    /**
     * Fails if an event is received. Valid until the next followup call.
     */
    // Calls a method
    void failFollowup();
// End of a block/expression
}
