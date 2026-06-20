// Package declaration for this file
package net.minestom.server.event.instance;

// Import of a required class
import net.minestom.server.event.trait.InstanceEvent;
// Import of a required class
import net.minestom.server.instance.Instance;

/**
 * Called when an instance processes a tick.
 */
// Type declaration (class/interface/enum/record)
public class InstanceTickEvent implements InstanceEvent {

    // Code statement
    private final Instance instance;
    // Code statement
    private final int duration;

    // Start of a method/block
    public InstanceTickEvent(Instance instance, long time, long lastTickAge) {
        // Access to the current/parent object
        this.instance = instance;
        // Access to the current/parent object
        this.duration = (int) (time - lastTickAge);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Instance getInstance() {
        // Returns a value to the caller
        return instance;
    // End of a block/expression
    }

    /**
     * Gets the duration of the tick in ms.
     *
     * @return the duration
     */
    // Start of a method/block
    public int getDuration() {
        // Returns a value to the caller
        return duration;
    // End of a block/expression
    }
// End of a block/expression
}