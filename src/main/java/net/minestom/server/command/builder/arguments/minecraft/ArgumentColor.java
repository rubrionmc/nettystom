// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft;

// Import of a required class
import net.kyori.adventure.text.format.NamedTextColor;
// Import of a required class
import net.kyori.adventure.text.format.Style;
// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;

/**
 * Represents an argument which will give you a {@link Style} containing the color or no
 * color if the argument was {@code reset}.
 * <p>
 * Example: red, white, reset
 */
// Type declaration (class/interface/enum/record)
public class ArgumentColor extends Argument<Style> {

    // Assigns a value
    public static final int UNDEFINED_COLOR = -2;

    // Start of a method/block
    public ArgumentColor(String id) {
        // Access to the current/parent object
        super(id);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Style parse(CommandSender sender, String input) throws ArgumentSyntaxException {

        // check for color
        // Calls a method
        NamedTextColor color = NamedTextColor.NAMES.value(input);
        // Branch: checks a condition
        if (color != null) {
            // Returns a value to the caller
            return Style.style(color);
        // End of a block/expression
        }

        // check for reset
        // Branch: checks a condition
        if (input.equals("reset")) {
            // Returns a value to the caller
            return Style.empty();
        // End of a block/expression
        }

        // Throws an exception
        throw new ArgumentSyntaxException("Undefined color", input, UNDEFINED_COLOR);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.COLOR;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("Color<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
