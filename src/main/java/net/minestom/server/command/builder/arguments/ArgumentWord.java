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

// Import of a required class
import java.util.Objects;

/**
 * Represents a single word in the command.
 * <p>
 * You can specify the valid words with {@link #from(String...)} (do not abuse it or the client will not be able to join).
 * <p>
 * Example: hey
 */
// Type declaration (class/interface/enum/record)
public class ArgumentWord extends Argument<String> {

    // Assigns a value
    public static final int SPACE_ERROR = 1;
    // Assigns a value
    public static final int RESTRICTION_ERROR = 2;

    // Code statement
    protected String[] restrictions;

    // Start of a method/block
    public ArgumentWord(String id) {
        // Access to the current/parent object
        super(id);
    // End of a block/expression
    }

    /**
     * Used to force the use of a few precise words instead of complete freedom.
     * <p>
     * WARNING: having an array too long would result in a packet too big or the client being stuck during login.
     *
     * @param restrictions the accepted words,
     *                     can be null but if an array is passed
     *                     you need to ensure that it is filled with non-null values
     * @return 'this' for chaining
     * @throws NullPointerException if {@code restrictions} is not null but contains null value(s)
     */
    // Start of a method/block
    public ArgumentWord from(@Nullable String... restrictions) {
        // Branch: checks a condition
        if (restrictions != null) {
            // Loop: repeats a block
            for (String restriction : restrictions) {
                // Calls a method
                Objects.requireNonNull(restriction, "ArgumentWord restriction cannot be null, you can pass 'null' instead of an empty array");
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Access to the current/parent object
        this.restrictions = restrictions;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Branch: checks a condition
        if (input.contains(StringUtils.SPACE))
            // Throws an exception
            throw new ArgumentSyntaxException("Word cannot contain space character", input, SPACE_ERROR);

        // Check restrictions (acting as literal)
        // Branch: checks a condition
        if (hasRestrictions()) {
            // Loop: repeats a block
            for (String r : restrictions) {
                // Branch: checks a condition
                if (input.equals(r))
                    // Returns a value to the caller
                    return input;
            // End of a block/expression
            }
            // Throws an exception
            throw new ArgumentSyntaxException("Word needs to be in the restriction list", input, RESTRICTION_ERROR);
        // End of a block/expression
        }

        // Returns a value to the caller
        return input;
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
        return NetworkBuffer.makeArray(NetworkBuffer.VAR_INT, 0); // Single word
    // End of a block/expression
    }

    /**
     * Gets if this argument allow complete freedom in the word choice or if a list has been defined.
     *
     * @return true if the word selection is restricted
     */
    // Start of a method/block
    public boolean hasRestrictions() {
        // Returns a value to the caller
        return restrictions != null && restrictions.length > 0;
    // End of a block/expression
    }

    /**
     * Gets all the word restrictions.
     *
     * @return the word restrictions, can be null
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public String[] getRestrictions() {
        // Returns a value to the caller
        return restrictions;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("Word<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
