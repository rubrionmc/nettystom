// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft;

// Import of a required class
import it.unimi.dsi.fastutil.chars.CharArrayList;
// Import of a required class
import it.unimi.dsi.fastutil.chars.CharList;
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
import net.minestom.server.utils.time.TimeUnit;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.time.Duration;
// Import of a required class
import java.time.temporal.TemporalUnit;

/**
 * Represents an argument giving a time (day/second/tick).
 * <p>
 * Example: 50d, 25s, 75t
 */
// Type declaration (class/interface/enum/record)
public class ArgumentTime extends Argument<Duration> {

    // Assigns a value
    public static final int INVALID_TIME_FORMAT = -2;
    // Assigns a value
    public static final int NO_NUMBER = -3;

    // Calls a method
    private static final CharList SUFFIXES = new CharArrayList(new char[]{'d', 's', 't'});

    // Assigns a value
    private int min = 0;

    // Start of a method/block
    public ArgumentTime(String id) {
        // Access to the current/parent object
        super(id);
    // End of a block/expression
    }

    // Start of a method/block
    public ArgumentTime min(int min) {
        // Access to the current/parent object
        this.min = min;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Duration parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Calls a method
        final char lastChar = input.charAt(input.length() - 1);

        // Code statement
        TemporalUnit timeUnit;
        // Branch: checks a condition
        if (Character.isDigit(lastChar))
            // Assigns a value
            timeUnit = TimeUnit.SERVER_TICK;
        // Branch: checks a condition
        else if (SUFFIXES.contains(lastChar)) {
            // Calls a method
            input = input.substring(0, input.length() - 1);

            // Branch: checks a condition
            if (lastChar == 'd') {
                // Assigns a value
                timeUnit = TimeUnit.DAY;
            // Branch: checks a condition
            } else if (lastChar == 's') {
                // Assigns a value
                timeUnit = TimeUnit.SECOND;
            // Branch: checks a condition
            } else if (lastChar == 't') {
                // Assigns a value
                timeUnit = TimeUnit.SERVER_TICK;
            // Alternative branch of the condition
            } else {
                // Throws an exception
                throw new ArgumentSyntaxException("Time needs to have the unit d, s, t, or none", input, NO_NUMBER);
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else
            // Throws an exception
            throw new ArgumentSyntaxException("Time needs to have a unit", input, NO_NUMBER);

        // Exception handling
        try {
            // Check if value is a number
            // Calls a method
            final int time = Integer.parseInt(input);
            // Returns a value to the caller
            return Duration.of(time, timeUnit);
        // Start of a method/block
        } catch (NumberFormatException e) {
            // Throws an exception
            throw new ArgumentSyntaxException("Time needs to be a number", input, NO_NUMBER);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte @Nullable [] nodeProperties() {
        // Returns a value to the caller
        return NetworkBuffer.makeArray(NetworkBuffer.INT, min);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.TIME;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Returns a value to the caller
        return String.format("Time<%s>", getId());
    // End of a block/expression
    }
// End of a block/expression
}
