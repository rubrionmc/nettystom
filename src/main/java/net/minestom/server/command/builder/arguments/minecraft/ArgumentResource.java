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
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.utils.StringUtils;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class ArgumentResource extends Argument<String> {

    // Assigns a value
    public static final int SPACE_ERROR = 1;

    // Code statement
    private final String identifier;

    // Start of a method/block
    public ArgumentResource(String id, String identifier) {
        // Access to the current/parent object
        super(id);
        // Access to the current/parent object
        this.identifier = identifier;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Branch: checks a condition
        if (input.contains(StringUtils.SPACE))
            // Throws an exception
            throw new ArgumentSyntaxException("Resource location cannot contain space character", input, SPACE_ERROR);

        // Returns a value to the caller
        return input;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.RESOURCE;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("Resource<%s>", getId());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte @Nullable [] nodeProperties() {
        // Returns a value to the caller
        return NetworkBuffer.makeArray(NetworkBuffer.STRING, identifier);
    // End of a block/expression
    }
// End of a block/expression
}
