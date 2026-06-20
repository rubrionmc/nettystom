// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.utils.Range;

/**
 * Represents an argument which will give you an {@link Range.Int}.
 * <p>
 * Example: ..3, 3.., 5..10, 15
 */
// Type declaration (class/interface/enum/record)
public class ArgumentIntRange extends ArgumentRange<Range.Int, Integer> {

    // Start of a method/block
    public ArgumentIntRange(String id) {
        // Access to the current/parent object
        super(id, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer::parseInt, Range.Int::new);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.INT_RANGE;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("IntRange<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
