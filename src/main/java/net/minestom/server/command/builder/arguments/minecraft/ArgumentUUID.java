// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

// Import of a required class
import java.util.UUID;

// Type declaration (class/interface/enum/record)
public class ArgumentUUID extends Argument<UUID> {

    // Assigns a value
    public static final int INVALID_UUID = -1;

    // Start of a method/block
    public ArgumentUUID(String id) {
        // Access to the current/parent object
        super(id);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public UUID parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Exception handling
        try {
            // Returns a value to the caller
            return UUID.fromString(input);
        // Start of a method/block
        } catch (IllegalArgumentException exception) {
            // Throws an exception
            throw new ArgumentSyntaxException("Invalid UUID", input, INVALID_UUID);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.UUID;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("UUID<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
