// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

// Import of a required class
import java.io.IOException;

/**
 * Argument used to retrieve a {@link BinaryTag} based object, can be any kind of tag like
 * {@link net.kyori.adventure.nbt.CompoundBinaryTag}, {@link net.kyori.adventure.nbt.ListBinaryTag},
 * {@link net.kyori.adventure.nbt.IntBinaryTag}, etc...
 * <p>
 * Example: {display:{Name:"{\"text\":\"Sword of Power\"}"}} or [{display:{Name:"{\"text\":\"Sword of Power\"}"}}]
 */
// Type declaration (class/interface/enum/record)
public class ArgumentNbtTag extends Argument<BinaryTag> {

    // Assigns a value
    public static final int INVALID_NBT = 1;

    // Start of a method/block
    public ArgumentNbtTag(String id) {
        // Access to the current/parent object
        super(id, true);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public BinaryTag parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Exception handling
        try {
            // Returns a value to the caller
            return MinestomAdventure.tagStringIO().asTag(input);
        // Start of a method/block
        } catch (IOException e) {
            // Throws an exception
            throw new ArgumentSyntaxException("Invalid NBT", input, INVALID_NBT);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.NBT_TAG;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("NBT<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
