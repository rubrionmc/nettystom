// Package declaration for this file
package net.minestom.server.command.builder.arguments.relative;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;

// Import of a required class
import java.util.function.Function;

/**
 * Represents a block position with 3 integers (x;y;z) which can take relative coordinates.
 * <p>
 * Example: 5 ~ -3
 */
// Type declaration (class/interface/enum/record)
public class ArgumentRelativeBlockPosition extends ArgumentRelativeVec {

    // Start of a method/block
    public ArgumentRelativeBlockPosition(String id) {
        // Access to the current/parent object
        super(id, 3);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.BLOCK_POS;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("RelativeBlockPosition<%s>", getId());
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
        return Integer::parseInt;
    // End of a block/expression
    }
// End of a block/expression
}
