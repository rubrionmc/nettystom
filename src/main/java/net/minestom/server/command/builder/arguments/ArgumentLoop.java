// Package declaration for this file
package net.minestom.server.command.builder.arguments;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.utils.StringUtils;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public class ArgumentLoop<T> extends Argument<List<T>> {

    // Assigns a value
    public static final int INVALID_INPUT_ERROR = 1;

    // Calls a method
    private final List<Argument<T>> arguments = new ArrayList<>();

    // Annotation for the following element
    @SafeVarargs
    // Start of a method/block
    public ArgumentLoop(String id, Argument<T>... arguments) {
        // Access to the current/parent object
        super(id, true, true);
        // Access to the current/parent object
        this.arguments.addAll(Arrays.asList(arguments));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public List<T> parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Calls a method
        List<T> result = new ArrayList<>();
        // Calls a method
        final String[] split = input.split(StringUtils.SPACE);

        // Calls a method
        final StringBuilder builder = new StringBuilder();
        // Assigns a value
        boolean success = false;
        // Loop: repeats a block
        for (String s : split) {
            // Calls a method
            builder.append(s);

            // Loop: repeats a block
            for (Argument<T> argument : arguments) {
                // Exception handling
                try {
                    // Calls a method
                    final String inputString = builder.toString();
                    // Calls a method
                    final T value = argument.parse(sender, inputString);
                    // Assigns a value
                    success = true;
                    // Calls a method
                    result.add(value);
                    // Breaks out of the loop/block
                    break;
                // Start of a method/block
                } catch (ArgumentSyntaxException ignored) {
                    // Assigns a value
                    success = false;
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Branch: checks a condition
            if (success) {
                // Code statement
                builder.setLength(0); // Clear
            // Alternative branch of the condition
            } else {
                // Calls a method
                builder.append(StringUtils.SPACE);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Branch: checks a condition
        if (result.isEmpty() || !success) {
            // Throws an exception
            throw new ArgumentSyntaxException("Invalid loop, there is no valid argument found", input, INVALID_INPUT_ERROR);
        // End of a block/expression
        }

        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Start of a method/block
    public List<Argument<T>> arguments() {
        // Returns a value to the caller
        return arguments;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }
// End of a block/expression
}
