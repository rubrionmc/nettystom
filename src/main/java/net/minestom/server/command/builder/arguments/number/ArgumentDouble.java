// Package declaration for this file
package net.minestom.server.command.builder.arguments.number;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;

// Type declaration (class/interface/enum/record)
public class ArgumentDouble extends ArgumentNumber<Double> {

    // Start of a method/block
    public ArgumentDouble(String id) {
        // Access to the current/parent object
        super(id, ArgumentParserType.DOUBLE, Double::parseDouble, ((s, radix) -> (double) Long.parseLong(s, radix)),
                // Code statement
                NetworkBuffer.DOUBLE, Double::compare);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("Double<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
