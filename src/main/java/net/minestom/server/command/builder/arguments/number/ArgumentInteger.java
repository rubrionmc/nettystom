// Package declaration for this file
package net.minestom.server.command.builder.arguments.number;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;

// Type declaration (class/interface/enum/record)
public class ArgumentInteger extends ArgumentNumber<Integer> {

    // Start of a method/block
    public ArgumentInteger(String id) {
        // Access to the current/parent object
        super(id, ArgumentParserType.INTEGER, Integer::parseInt, Integer::parseInt,
                // Code statement
                NetworkBuffer.INT, Integer::compare);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("Integer<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
