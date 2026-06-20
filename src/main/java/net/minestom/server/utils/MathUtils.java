// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class MathUtils {

    // Start of a method/block
    private MathUtils() {
    // End of a block/expression
    }

    // Start of a method/block
    public static int square(int num) {
        // Returns a value to the caller
        return num * num;
    // End of a block/expression
    }

    // Start of a method/block
    public static float square(float num) {
        // Returns a value to the caller
        return num * num;
    // End of a block/expression
    }

    // Start of a method/block
    public static double square(double num) {
        // Returns a value to the caller
        return num * num;
    // End of a block/expression
    }

    // Start of a method/block
    public static double round(double value, int places) {
        // Branch: checks a condition
        if (places < 0) throw new IllegalArgumentException();

        // Calls a method
        final long factor = (long) Math.pow(10, places);
        // Assigns a value
        value = value * factor;
        // Calls a method
        long tmp = Math.round(value);
        // Returns a value to the caller
        return (double) tmp / factor;
    // End of a block/expression
    }

    // Start of a method/block
    public static float round(float value, int places) {
        // Branch: checks a condition
        if (places < 0) throw new IllegalArgumentException();

        // Calls a method
        final long factor = (long) Math.pow(10, places);
        // Assigns a value
        value = value * factor;
        // Calls a method
        long tmp = Math.round(value);
        // Returns a value to the caller
        return (float) tmp / factor;
    // End of a block/expression
    }

    // Start of a method/block
    public static Direction getHorizontalDirection(float yawInDegrees) {
        // +45f gives a 90° angle for the direction (-1° and 1° are towards the same direction)
        // Calls a method
        int directionIndex = (int) Math.floor(((yawInDegrees + 45f) / 90f));
        // Branch: checks a condition
        if (directionIndex < 0) {
            // Calls a method
            directionIndex = (-directionIndex) % Direction.HORIZONTAL.length;
            // Assigns a value
            directionIndex = Direction.HORIZONTAL.length - directionIndex;
        // End of a block/expression
        }
        // Code statement
        directionIndex %= Direction.HORIZONTAL.length;
        // Returns a value to the caller
        return Direction.HORIZONTAL[directionIndex];
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean isBetween(byte number, byte min, byte max) {
        // Returns a value to the caller
        return number >= min && number <= max;
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean isBetween(int number, int min, int max) {
        // Returns a value to the caller
        return number >= min && number <= max;
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean isBetween(double number, double min, double max) {
        // Returns a value to the caller
        return number >= min && number <= max;
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean isBetween(float number, float min, float max) {
        // Returns a value to the caller
        return number >= min && number <= max;
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean isBetweenUnordered(double number, double compare1, double compare2) {
        // Branch: checks a condition
        if (compare1 > compare2) {
            // Returns a value to the caller
            return isBetween(number, compare2, compare1);
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return isBetween(number, compare1, compare2);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean isBetweenUnordered(float number, float compare1, float compare2) {
        // Branch: checks a condition
        if (compare1 > compare2) {
            // Returns a value to the caller
            return isBetween(number, compare2, compare1);
        // Alternative branch of the condition
        } else {
            // Returns a value to the caller
            return isBetween(number, compare1, compare2);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public static int clamp(int value, int min, int max) {
        // Returns a value to the caller
        return Math.clamp(value, min, max);
    // End of a block/expression
    }

    // Start of a method/block
    public static float clamp(float value, float min, float max) {
        // Returns a value to the caller
        return Math.clamp(value, min, max);
    // End of a block/expression
    }

    // Start of a method/block
    public static double clamp(double value, double min, double max) {
        // Returns a value to the caller
        return Math.clamp(value, min, max);
    // End of a block/expression
    }

    // Start of a method/block
    public static double mod(final double a, final double b) {
        // Returns a value to the caller
        return (a % b + b) % b;
    // End of a block/expression
    }

    // Start of a method/block
    public static int bitsToRepresent(int n) {
        // Calls a method
        Check.argCondition(n < 1, "n must be greater than 0");
        // Returns a value to the caller
        return Integer.SIZE - Integer.numberOfLeadingZeros(n);
    // End of a block/expression
    }

    // Start of a method/block
    public static long ceilLong(double value) {
        // Calls a method
        long i = (long) value;
        // Returns a value to the caller
        return value > i ? i + 1L : i;
    // End of a block/expression
    }

    // Start of a method/block
    public static double absMax(double d0, double d1) {
        // Returns a value to the caller
        return Math.max(Math.abs(d0), Math.abs(d1));
    // End of a block/expression
    }

// End of a block/expression
}
