// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.utils.Range;

// Import of a required class
import java.util.function.BiFunction;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.regex.Pattern;

/**
 * Abstract class used by {@link ArgumentIntRange} and {@link ArgumentFloatRange}.
 *
 * @param <T> the type of the range
 */
// Type declaration (class/interface/enum/record)
public abstract class ArgumentRange<T extends Range<N>, N extends Number> extends Argument<T> {

    // Assigns a value
    public static final int FORMAT_ERROR = -1;
    // Code statement
    private final N min;
    // Code statement
    private final N max;
    // Code statement
    private final Function<String, N> parser;
    // Code statement
    private final BiFunction<N, N, T> rangeConstructor;

    // Start of a method/block
    public ArgumentRange(String id, N min, N max, Function<String, N> parser, BiFunction<N, N, T> rangeConstructor) {
        // Access to the current/parent object
        super(id);
        // Access to the current/parent object
        this.min = min;
        // Access to the current/parent object
        this.max = max;
        // Access to the current/parent object
        this.parser = parser;
        // Access to the current/parent object
        this.rangeConstructor = rangeConstructor;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public T parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Exception handling
        try {
            // Calls a method
            final String[] split = input.split(Pattern.quote(".."), -1);

            // Branch: checks a condition
            if (split.length == 2) {
                // Code statement
                final N min;
                // Code statement
                final N max;
                // Branch: checks a condition
                if (split[0].isEmpty() && !split[1].isEmpty()) {
                    // Format ..NUMBER
                    // Assigns a value
                    min = this.min;
                    // Calls a method
                    max = parser.apply(split[1]);
                // Branch: checks a condition
                } else if (!split[0].isEmpty() && split[1].isEmpty()) {
                    // Format NUMBER..
                    // Calls a method
                    min = parser.apply(split[0]);
                    // Assigns a value
                    max = this.max;
                // Branch: checks a condition
                } else if (!split[0].isEmpty()) {
                    // Format NUMBER..NUMBER
                    // Calls a method
                    min = parser.apply(split[0]);
                    // Calls a method
                    max = parser.apply(split[1]);
                // Alternative branch of the condition
                } else {
                    // Format ..
                    // Throws an exception
                    throw new ArgumentSyntaxException("Invalid range format", input, FORMAT_ERROR);
                // End of a block/expression
                }
                // Returns a value to the caller
                return rangeConstructor.apply(min, max);
            // Branch: checks a condition
            } else if (split.length == 1) {
                // Calls a method
                final N number = parser.apply(input);
                // Returns a value to the caller
                return rangeConstructor.apply(number, number);
            // End of a block/expression
            }
        // Start of a method/block
        } catch (NumberFormatException e2) {
            // Throws an exception
            throw new ArgumentSyntaxException("Invalid number", input, FORMAT_ERROR);
        // End of a block/expression
        }
        // Throws an exception
        throw new ArgumentSyntaxException("Invalid range format", input, FORMAT_ERROR);
    // End of a block/expression
    }
// End of a block/expression
}
