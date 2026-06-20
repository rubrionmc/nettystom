// Package declaration for this file
package net.minestom.server.command.builder.arguments.relative;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.utils.StringUtils;
// Import of a required class
import net.minestom.server.utils.location.RelativeVec;

// Import of a required class
import java.util.function.Function;

// Static import of a member
import static net.minestom.server.utils.location.RelativeVec.CoordinateType.*;

/**
 * Common interface for all the relative location arguments.
 */
// Type declaration (class/interface/enum/record)
abstract class ArgumentRelativeVec extends Argument<RelativeVec> {

    // Assigns a value
    private static final char RELATIVE_CHAR = '~';
    // Assigns a value
    private static final char LOCAL_CHAR = '^';

    // Assigns a value
    public static final int INVALID_NUMBER_COUNT_ERROR = 1;
    // Assigns a value
    public static final int INVALID_NUMBER_ERROR = 2;
    // Assigns a value
    public static final int MIXED_TYPE_ERROR = 3;

    // Code statement
    private final int numberCount;

    // Start of a method/block
    public ArgumentRelativeVec(String id, int numberCount) {
        // Access to the current/parent object
        super(id, true);
        // Access to the current/parent object
        this.numberCount = numberCount;
    // End of a block/expression
    }

    // Calls a method
    abstract Function<String, ? extends Number> getRelativeNumberParser();

    // Calls a method
    abstract Function<String, ? extends Number> getAbsoluteNumberParser();

    // Annotation for the following element
    @Override
    // Start of a method/block
    public RelativeVec parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Calls a method
        final String[] split = input.split(StringUtils.SPACE);
        // Branch: checks a condition
        if (split.length != getNumberCount()) {
            // Throws an exception
            throw new ArgumentSyntaxException("Invalid number of values", input, INVALID_NUMBER_COUNT_ERROR);
        // End of a block/expression
        }

        // Assigns a value
        double[] coordinates = new double[split.length];
        // Assigns a value
        boolean[] isRelative = new boolean[split.length];
        // Assigns a value
        boolean isLocalType = false;

        // Loop: repeats a block
        for (int i = 0; i < split.length; i++) {
            // Assigns a value
            final String element = split[i];
            // Exception handling
            try {
                // Calls a method
                final char modifierChar = element.charAt(0);

                // Branch: checks a condition
                if (isLocalType && modifierChar != LOCAL_CHAR) {
                    // Throws an exception
                    throw new ArgumentSyntaxException("Cannot mix world & local coordinates (everything must either use ^ or not)", input, MIXED_TYPE_ERROR);
                // End of a block/expression
                }

                // Multiple branching (switch/case)
                switch (modifierChar) {
                    // Multiple branching (switch/case)
                    case LOCAL_CHAR: {
                        // Assigns a value
                        isLocalType = true;
                        // Everything in local has to be relative. Fall through.
                    // End of a block/expression
                    }
                    // Multiple branching (switch/case)
                    case RELATIVE_CHAR: {
                        // Assigns a value
                        isRelative[i] = true;
                        // Branch: checks a condition
                        if (element.length() == 1) break;
                        // Calls a method
                        final String potentialNumber = element.substring(1);
                        // Calls a method
                        coordinates[i] = getRelativeNumberParser().apply(potentialNumber).doubleValue();
                        // Breaks out of the loop/block
                        break;
                    // End of a block/expression
                    }
                    // Multiple branching (switch/case)
                    default: {
                        // Calls a method
                        coordinates[i] = getAbsoluteNumberParser().apply(element).doubleValue();
                        // Breaks out of the loop/block
                        break;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
            // Start of a method/block
            } catch (NumberFormatException e) {
                // Throws an exception
                throw new ArgumentSyntaxException("Invalid number", input, INVALID_NUMBER_ERROR);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Assigns a value
        final boolean xRelative = isRelative[0];
        // Assigns a value
        final boolean yRelative = split.length == 3 && isRelative[1];
        // Assigns a value
        final boolean zRelative = isRelative[split.length == 3 ? 2 : 1];

        // Code statement
        final RelativeVec.CoordinateType type;
        // Branch: checks a condition
        if (isLocalType) {
            // Assigns a value
            type = LOCAL;
        // Branch: checks a condition
        } else if (xRelative || yRelative || zRelative) {
            // Assigns a value
            type = RELATIVE;
        // Alternative branch of the condition
        } else {
            // Assigns a value
            type = ABSOLUTE;
        // End of a block/expression
        }

        // Returns a value to the caller
        return new RelativeVec(split.length == 3 ?
                // Creates a new object
                new Vec(coordinates[0], coordinates[1], coordinates[2]) : new Vec(coordinates[0], coordinates[1]),
                // Code statement
                type,
                // Code statement
                xRelative, yRelative, zRelative);
    // End of a block/expression
    }

    /**
     * Gets the amount of numbers that this relative location needs.
     *
     * @return the amount of coordinate required
     */
    // Start of a method/block
    public int getNumberCount() {
        // Returns a value to the caller
        return numberCount;
    // End of a block/expression
    }
// End of a block/expression
}
