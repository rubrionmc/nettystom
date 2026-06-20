// Package declaration for this file
package net.minestom.server.event.instance;

// Import of a required class
import net.minestom.server.event.trait.InstanceEvent;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

/**
 * This event is triggered when a section of an instance is manually marked as invalid.
 * <p>
 * Changes in this case are not known but indicate that its content must be reinterpreted.
 * <p>
 * Can be triggered using {@link Instance#invalidateSection(int, int, int)}
 */
// Type declaration (class/interface/enum/record)
public class InstanceSectionInvalidateEvent implements InstanceEvent {
    // Code statement
    private final Instance instance;
    // Code statement
    private final int sectionX, sectionY, sectionZ;

    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public InstanceSectionInvalidateEvent(Instance instance, int sectionX, int sectionY, int sectionZ) {
        // Access to the current/parent object
        this.instance = instance;
        // Access to the current/parent object
        this.sectionX = sectionX;
        // Access to the current/parent object
        this.sectionY = sectionY;
        // Access to the current/parent object
        this.sectionZ = sectionZ;
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

    // Start of a method/block
    public int sectionX() {
        // Returns a value to the caller
        return sectionX;
    // End of a block/expression
    }

    // Start of a method/block
    public int sectionY() {
        // Returns a value to the caller
        return sectionY;
    // End of a block/expression
    }

    // Start of a method/block
    public int sectionZ() {
        // Returns a value to the caller
        return sectionZ;
    // End of a block/expression
    }
// End of a block/expression
}
