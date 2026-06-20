// Package declaration for this file
package net.minestom.server.event.instance;

// Import of a required class
import net.minestom.server.event.trait.InstanceEvent;
// Import of a required class
import net.minestom.server.instance.Instance;

/**
 * Called when an instance is registered
 */
// Type declaration (class/interface/enum/record)
public class InstanceRegisterEvent implements InstanceEvent {
    // Code statement
    private final Instance instance;

    // Start of a method/block
    public InstanceRegisterEvent(Instance instance) {
        // Access to the current/parent object
        this.instance = instance;
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
// End of a block/expression
}
