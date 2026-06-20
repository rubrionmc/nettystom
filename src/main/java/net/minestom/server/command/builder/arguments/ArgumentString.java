// Package declaration for this file
package net.minestom.server.command.builder.arguments;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.utils.StringUtils;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Argument which will take a quoted string.
 * <p>
 * Example: "Hey I am a string"
 */
// Type declaration (class/interface/enum/record)
public class ArgumentString extends Argument<String> {

    // Assigns a value
    private static final char BACKSLASH = '\\';
    // Assigns a value
    private static final char DOUBLE_QUOTE = '"';
    // Assigns a value
    private static final char QUOTE = '\'';

    // Assigns a value
    public static final int QUOTE_ERROR = 1;

    // Start of a method/block
    public ArgumentString(String id) {
        // Access to the current/parent object
        super(id, true);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Returns a value to the caller
        return staticParse(input);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.STRING;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte @Nullable [] nodeProperties() {
        // Returns a value to the caller
        return NetworkBuffer.makeArray(NetworkBuffer.VAR_INT, 1); // Quotable phrase
    // End of a block/expression
    }

    /**
     * @deprecated use {@link Argument#parse(CommandSender, Argument)}
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public static String staticParse(String input) throws ArgumentSyntaxException {
        // Return if not quoted
        // Branch: checks a condition
        if (!input.contains(String.valueOf(DOUBLE_QUOTE)) &&
                // Code statement
                !input.contains(String.valueOf(QUOTE)) &&
                // Start of a method/block
                !input.contains(StringUtils.SPACE)) {
            // Returns a value to the caller
            return input;
        // End of a block/expression
        }

        // Check if value start and end with quote
        // Calls a method
        final char first = input.charAt(0);
        // Calls a method
        final char last = input.charAt(input.length() - 1);
        // Assigns a value
        final boolean quote = input.length() >= 2 &&
                // Calls a method
                first == last && (first == DOUBLE_QUOTE || first == QUOTE);
        // Branch: checks a condition
        if (!quote)
            // Throws an exception
            throw new ArgumentSyntaxException("String argument needs to start and end with quotes", input, QUOTE_ERROR);

        // Remove first and last characters (quotes)
        // Calls a method
        input = input.substring(1, input.length() - 1);

        // Verify backslashes
        // Loop: repeats a block
        for (int i = 1; i < input.length(); i++) {
            // Calls a method
            final char c = input.charAt(i);
            // Branch: checks a condition
            if (c == first) {
                // Calls a method
                final char lastChar = input.charAt(i - 1);
                // Branch: checks a condition
                if (lastChar != BACKSLASH) {
                    // Throws an exception
                    throw new ArgumentSyntaxException("Non-escaped quote", input, QUOTE_ERROR);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Returns a value to the caller
        return StringUtils.unescapeJavaString(input);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("String<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
