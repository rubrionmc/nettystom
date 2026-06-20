// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft;

// Import of a required class
import net.kyori.adventure.key.KeyPattern;
// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.kyori.adventure.key.Key;

/**
 * Represents a resource location (namespaced identifier) value.
 * <p>
 *     Example: {@code minecraft:air}
 * </p>
 */
// Type declaration (class/interface/enum/record)
public class ArgumentResourceLocation extends Argument<Key> {

    // Assigns a value
    public static final int PARSE_ERROR = 1;

    // Start of a method/block
    public ArgumentResourceLocation(String id) {
        // Access to the current/parent object
        super(id);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Key parse(CommandSender sender, @KeyPattern String input) throws ArgumentSyntaxException {
        // Branch: checks a condition
        if (!Key.parseable(input))
            // Throws an exception
            throw new ArgumentSyntaxException("Invalid resource location", input, PARSE_ERROR);

        // Returns a value to the caller
        return Key.key(input);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.RESOURCE_LOCATION;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("ResourceLocation<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
