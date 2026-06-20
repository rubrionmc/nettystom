// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft;

// Import of a required class
import net.kyori.adventure.key.InvalidKeyException;
// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.server.utils.block.BlockUtils;

// Type declaration (class/interface/enum/record)
public class ArgumentBlockState extends Argument<Block> {

    // Assigns a value
    public static final int NO_BLOCK = 1;
    // Assigns a value
    public static final int INVALID_BLOCK = 2;
    // Assigns a value
    public static final int INVALID_PROPERTY = 3;
    // Assigns a value
    public static final int INVALID_PROPERTY_VALUE = 4;

    // Start of a method/block
    public ArgumentBlockState(String id) {
        // Access to the current/parent object
        super(id, true, false);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Block parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Returns a value to the caller
        return staticParse(input);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.BLOCK_STATE;
    // End of a block/expression
    }

    /**
     * @deprecated use {@link Argument#parse(CommandSender, Argument)}
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public static Block staticParse(String input) throws ArgumentSyntaxException {
        // Calls a method
        final int nbtIndex = input.indexOf("[");
        // Branch: checks a condition
        if (nbtIndex == 0)
            // Throws an exception
            throw new ArgumentSyntaxException("No block type", input, NO_BLOCK);

        // Branch: checks a condition
        if (nbtIndex == -1) {
            // Only block name
            // Code statement
            Block block;
            // Exception handling
            try {
                // Calls a method
                block = Block.fromKey(input);
            // Start of a method/block
            } catch (InvalidKeyException ignored) {
                // Assigns a value
                block = null;
            // End of a block/expression
            }
            // Branch: checks a condition
            if (block == null)
                // Throws an exception
                throw new ArgumentSyntaxException("Invalid block type", input, INVALID_BLOCK);
            // Returns a value to the caller
            return block;
        // Alternative branch of the condition
        } else {
            // Branch: checks a condition
            if (!input.endsWith("]"))
                // Throws an exception
                throw new ArgumentSyntaxException("Property list need to end with ]", input, INVALID_PROPERTY);
            // Block state
            // Calls a method
            final String blockName = input.substring(0, nbtIndex);
            // Calls a method
            Block block = Block.fromKey(blockName);
            // Branch: checks a condition
            if (block == null)
                // Throws an exception
                throw new ArgumentSyntaxException("Invalid block type", input, INVALID_BLOCK);

            // Compute properties
            // Calls a method
            final String query = input.substring(nbtIndex);
            // Calls a method
            final var propertyMap = BlockUtils.parseProperties(query);
            // Exception handling
            try {
                // Returns a value to the caller
                return block.withProperties(propertyMap);
            // Start of a method/block
            } catch (IllegalArgumentException e) {
                // Throws an exception
                throw new ArgumentSyntaxException("Invalid property values", input, INVALID_PROPERTY_VALUE);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("BlockState<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
