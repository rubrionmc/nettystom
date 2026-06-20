// Package declaration for this file
package net.minestom.server.event.trait;

// Import of a required class
import net.minestom.server.event.Event;
// Import of a required class
import net.minestom.server.instance.Instance;

/**
 * Represents any event targeting an {@link Instance}.
 */
// Type declaration (class/interface/enum/record)
public interface InstanceEvent extends Event {

    /**
     * Gets the instance.
     *
     * @return instance
     */
    // Calls a method
    Instance getInstance();
// End of a block/expression
}
