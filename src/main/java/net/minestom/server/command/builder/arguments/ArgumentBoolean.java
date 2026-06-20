// Package declaration for this file
package net.minestom.server.command.builder.arguments;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

/**
 * Represents a boolean value.
 * <p>
 * Example: true
 */
// Type declaration (class/interface/enum/record)
public class ArgumentBoolean extends Argument<Boolean> {

    // Assigns a value
    public static final int NOT_BOOLEAN_ERROR = 1;

    // Start of a method/block
    public ArgumentBoolean(String id) {
        // Access to the current/parent object
        super(id);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Boolean parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Branch: checks a condition
        if (input.equalsIgnoreCase("true"))
            // Returns a value to the caller
            return true;
        // Branch: checks a condition
        if (input.equalsIgnoreCase("false"))
            // Returns a value to the caller
            return false;

        // Throws an exception
        throw new ArgumentSyntaxException("Not a boolean", input, NOT_BOOLEAN_ERROR);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.BOOL;
    // End of a block/expression
    }
    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("Boolean<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
