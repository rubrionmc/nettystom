// Package declaration for this file
package net.minestom.server.command.builder.arguments.number;

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
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.math.BigDecimal;
// Import of a required class
import java.util.Comparator;
// Import of a required class
import java.util.function.BiFunction;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.regex.Pattern;

// Type declaration (class/interface/enum/record)
public class ArgumentNumber<T extends Number> extends Argument<T> {

    // Assigns a value
    public static final int NOT_NUMBER_ERROR = 1;
    // Assigns a value
    public static final int TOO_LOW_ERROR = 2;
    // Assigns a value
    public static final int TOO_HIGH_ERROR = 3;

    // Code statement
    protected boolean hasMin, hasMax;
    // Code statement
    protected T min, max;

    // Code statement
    protected final ArgumentParserType parserName;
    // Code statement
    protected final BiFunction<String, Integer, T> radixParser;
    // Code statement
    protected final Function<String, T> parser;
    // Code statement
    protected final NetworkBuffer.Type<T> networkType;
    // Code statement
    protected final Comparator<T> comparator;

    // Code statement
    ArgumentNumber(String id, ArgumentParserType parserName, Function<String, T> parser,
                   // Code statement
                   BiFunction<String, Integer, T> radixParser, NetworkBuffer.Type<T> networkType,
                   // Start of a method/block
                   Comparator<T> comparator) {
        // Access to the current/parent object
        super(id);
        // Access to the current/parent object
        this.parserName = parserName;
        // Access to the current/parent object
        this.radixParser = radixParser;
        // Access to the current/parent object
        this.parser = parser;
        // Access to the current/parent object
        this.networkType = networkType;
        // Access to the current/parent object
        this.comparator = comparator;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public T parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Exception handling
        try {
            // Code statement
            final T value;
            // Calls a method
            final int radix = getRadix(input);
            // Branch: checks a condition
            if (radix == 10) {
                // Calls a method
                value = parser.apply(parseValue(input));
            // Alternative branch of the condition
            } else {
                // Calls a method
                value = radixParser.apply(parseValue(input), radix);
            // End of a block/expression
            }

            // Check range
            // Branch: checks a condition
            if (hasMin && comparator.compare(value, min) < 0) {
                // Throws an exception
                throw new ArgumentSyntaxException("Input is lower than the minimum allowed value", input, TOO_LOW_ERROR);
            // End of a block/expression
            }
            // Branch: checks a condition
            if (hasMax && comparator.compare(value, max) > 0) {
                // Throws an exception
                throw new ArgumentSyntaxException("Input is higher than the maximum allowed value", input, TOO_HIGH_ERROR);
            // End of a block/expression
            }

            // Returns a value to the caller
            return value;
        // Start of a method/block
        } catch (NumberFormatException | NullPointerException e) {
            // Throws an exception
            throw new ArgumentSyntaxException("Input is not a number, or it's invalid for the given type", input, NOT_NUMBER_ERROR);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return parserName;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte @Nullable [] nodeProperties() {
        // Returns a value to the caller
        return NetworkBuffer.makeArray(buffer -> {
            // Calls a method
            buffer.write(NetworkBuffer.BYTE, getNumberProperties());
            // Branch: checks a condition
            if (this.hasMin())
                // Calls a method
                networkType.write(buffer, getMin());
            // Branch: checks a condition
            if (this.hasMax())
                // Calls a method
                networkType.write(buffer, getMax());
        // End of a block/expression
        });
    // End of a block/expression
    }

    // Start of a method/block
    public ArgumentNumber<T> min(T value) {
        // Access to the current/parent object
        this.min = value;
        // Access to the current/parent object
        this.hasMin = true;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public ArgumentNumber<T> max(T value) {
        // Access to the current/parent object
        this.max = value;
        // Access to the current/parent object
        this.hasMax = true;

        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public ArgumentNumber<T> between(T min, T max) {
        // Access to the current/parent object
        this.min = min;
        // Access to the current/parent object
        this.max = max;
        // Access to the current/parent object
        this.hasMin = true;
        // Access to the current/parent object
        this.hasMax = true;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Creates the byteflag based on the number's min/max existence.
     *
     * @return A byteflag for argument specification.
     */
    // Start of a method/block
    public byte getNumberProperties() {
        // Assigns a value
        byte result = 0;
        // Branch: checks a condition
        if (this.hasMin())
            // Code statement
            result |= 0x1;
        // Branch: checks a condition
        if (this.hasMax())
            // Code statement
            result |= 0x2;
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    /**
     * Gets if the argument has a minimum.
     *
     * @return true if the argument has a minimum
     */
    // Start of a method/block
    public boolean hasMin() {
        // Returns a value to the caller
        return hasMin;
    // End of a block/expression
    }

    /**
     * Gets the minimum value for this argument.
     *
     * @return the minimum of this argument
     */
    // Start of a method/block
    public T getMin() {
        // Returns a value to the caller
        return min;
    // End of a block/expression
    }

    /**
     * Gets if the argument has a maximum.
     *
     * @return true if the argument has a maximum
     */
    // Start of a method/block
    public boolean hasMax() {
        // Returns a value to the caller
        return hasMax;
    // End of a block/expression
    }

    /**
     * Gets the maximum value for this argument.
     *
     * @return the maximum of this argument
     */
    // Start of a method/block
    public T getMax() {
        // Returns a value to the caller
        return max;
    // End of a block/expression
    }

    // Start of a method/block
    protected String parseValue(String value) {
        // Branch: checks a condition
        if (value.startsWith("0b")) {
            // Calls a method
            value = value.replaceFirst(Pattern.quote("0b"), "");
        // Branch: checks a condition
        } else if (value.startsWith("0x")) {
            // Calls a method
            value = value.replaceFirst(Pattern.quote("0x"), "");
        // Branch: checks a condition
        } else if (value.toLowerCase().contains("e")) {
            // Calls a method
            value = removeScientificNotation(value);
        // End of a block/expression
        }
        // TODO number suffix support (k,m,b,t)
        // Returns a value to the caller
        return value;
    // End of a block/expression
    }

    // Start of a method/block
    protected int getRadix(String value) {
        // Branch: checks a condition
        if (value.startsWith("0b")) {
            // Returns a value to the caller
            return 2;
        // Branch: checks a condition
        } else if (value.startsWith("0x")) {
            // Returns a value to the caller
            return 16;
        // End of a block/expression
        }
        // Returns a value to the caller
        return 10;
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    protected String removeScientificNotation(String value) {
        // Exception handling
        try {
            // Returns a value to the caller
            return new BigDecimal(value).toPlainString();
        // Start of a method/block
        } catch (NumberFormatException e) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
