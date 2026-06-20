// Package declaration for this file
package net.minestom.server.item.instrument;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.sound.SoundEvent;
// Import of a required class
import net.minestom.server.utils.validate.Check;

// Type declaration (class/interface/enum/record)
public record InstrumentImpl(
        // Code statement
        SoundEvent soundEvent,
        // Code statement
        float useDuration,
        // Code statement
        float range,
        // Code statement
        Component description
// Start of a method/block
) implements Instrument {

    // Annotation for the following element
    @SuppressWarnings("ConstantValue") // The builder can violate the nullability constraints
    // Start of a method/block
    public InstrumentImpl {
        // Calls a method
        Check.argCondition(soundEvent == null, "missing sound event");
        // Calls a method
        Check.argCondition(description == null, "missing description");
        // Calls a method
        Check.argCondition(useDuration <= 0, "use duration must be positive");
        // Calls a method
        Check.argCondition(range <= 0, "range must be positive");
    // End of a block/expression
    }

// End of a block/expression
}
