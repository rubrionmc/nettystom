// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.utils.Range;

/**
 * Represents an argument which will give you an {@link Range.Float}.
 * <p>
 * Example: ..3, 3.., 5..10, 15
 */
// Type declaration (class/interface/enum/record)
public class ArgumentFloatRange extends ArgumentRange<Range.Float, Float> {

    // Start of a method/block
    public ArgumentFloatRange(String id) {
        // Access to the current/parent object
        super(id, -Float.MAX_VALUE, Float.MAX_VALUE, Float::parseFloat, Range.Float::new);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.FLOAT_RANGE;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("FloatRange<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
