// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft;

// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
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
 * Argument used to retrieve a {@link CompoundBinaryTag} if you need key-value data.
 * <p>
 * Example: {display:{Name:"{\"text\":\"Sword of Power\"}"}}
 */
// Type declaration (class/interface/enum/record)
public class ArgumentNbtCompoundTag extends Argument<CompoundBinaryTag> {

    // Assigns a value
    public static final int INVALID_NBT = 1;

    // Start of a method/block
    public ArgumentNbtCompoundTag(String id) {
        // Access to the current/parent object
        super(id, true);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public CompoundBinaryTag parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Exception handling
        try {
            // Returns a value to the caller
            return MinestomAdventure.tagStringIO().asCompound(input);
        // Start of a method/block
        } catch (IOException e) {
            // Throws an exception
            throw new ArgumentSyntaxException("NBTCompound is invalid", input, INVALID_NBT);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.NBT_COMPOUND_TAG;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("NbtCompound<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
