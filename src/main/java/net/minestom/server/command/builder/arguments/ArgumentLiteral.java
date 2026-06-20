// Package declaration for this file
package net.minestom.server.command.builder.arguments;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

// Type declaration (class/interface/enum/record)
public class ArgumentLiteral extends Argument<String> {

    // Assigns a value
    public static final int INVALID_VALUE_ERROR = 1;

    // Start of a method/block
    public ArgumentLiteral(String id) {
        // Access to the current/parent object
        super(id);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Branch: checks a condition
        if (!input.equals(getId()))
            // Throws an exception
            throw new ArgumentSyntaxException("Invalid literal value", input, INVALID_VALUE_ERROR);

        // Returns a value to the caller
        return input;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("Literal<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
