// Package declaration for this file
package net.minestom.server.command.builder.arguments.relative;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.coordinate.Vec;

// Import of a required class
import java.util.function.Function;

/**
 * Represents a {@link Vec} with 2 floating numbers (x;z) which can take relative coordinates.
 * <p>
 * Example: -1.2 ~
 */
// Type declaration (class/interface/enum/record)
public class ArgumentRelativeVec2 extends ArgumentRelativeVec {

    // Start of a method/block
    public ArgumentRelativeVec2(String id) {
        // Access to the current/parent object
        super(id, 2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.VEC2;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("RelativeVec2<%s>", getId());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    Function<String, ? extends Number> getRelativeNumberParser() {
        // Returns a value to the caller
        return Double::parseDouble;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    Function<String, ? extends Number> getAbsoluteNumberParser() {
        // Returns a value to the caller
        return Double::parseDouble;
    // End of a block/expression
    }
// End of a block/expression
}
