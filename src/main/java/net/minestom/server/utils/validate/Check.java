// Package declaration for this file
package net.minestom.server.utils.validate;

// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.text.MessageFormat;
// Import of a required class
import java.util.Objects;

/**
 * Convenient class to check for common exceptions.
 */
// Type declaration (class/interface/enum/record)
public final class Check {

    // Start of a method/block
    private Check() {

    // End of a block/expression
    }

    // Annotation for the following element
    @Deprecated(forRemoval = true) // Use Objects.requireNonNull instead. (Has much better IDE support)
    // Annotation for the following element
    @Contract("null, _ -> fail")
    // Start of a method/block
    public static void notNull(@Nullable Object object, String reason) {
        // Branch: checks a condition
        if (Objects.isNull(object)) {
            // Throws an exception
            throw new NullPointerException(reason);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("null, _, _ -> fail")
    // Start of a method/block
    public static void notNull(@Nullable Object object, String reason, Object... arguments) {
        // Branch: checks a condition
        if (Objects.isNull(object)) {
            // Throws an exception
            throw new NullPointerException(MessageFormat.format(reason, arguments));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("true, _ -> fail")
    // Start of a method/block
    public static void argCondition(boolean condition, String reason) {
        // Branch: checks a condition
        if (condition) {
            // Throws an exception
            throw new IllegalArgumentException(reason);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("true, _, _ -> fail")
    // Start of a method/block
    public static void argCondition(boolean condition, String reason, Object... arguments) {
        // Branch: checks a condition
        if (condition) {
            // Throws an exception
            throw new IllegalArgumentException(MessageFormat.format(reason, arguments));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Deprecated(forRemoval = true) // Just throw instead, as javac control flow is opaque to calling this function.
    // Annotation for the following element
    @Contract("_ -> fail")
    // Start of a method/block
    public static void fail(String reason) {
        // Throws an exception
        throw new IllegalArgumentException(reason);
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("_, _ -> fail")
    // Start of a method/block
    public static void fail(String reason, Object... arguments) {
        // Throws an exception
        throw new IllegalArgumentException(MessageFormat.format(reason, arguments));
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("true, _ -> fail")
    // Start of a method/block
    public static void stateCondition(boolean condition, String reason) {
        // Branch: checks a condition
        if (condition) {
            // Throws an exception
            throw new IllegalStateException(reason);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("true, _, _ -> fail")
    // Start of a method/block
    public static void stateCondition(boolean condition, String reason, Object... arguments) {
        // Branch: checks a condition
        if (condition) {
            // Throws an exception
            throw new IllegalStateException(MessageFormat.format(reason, arguments));
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("false, _ -> fail")
    // Start of a method/block
    public static void isTrue(boolean condition, String reason) {
        // Branch: checks a condition
        if (!condition) {
            // Throws an exception
            throw new IllegalStateException(reason);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("false, _, _ -> fail")
    // Start of a method/block
    public static void isTrue(boolean condition, String reason, Object... arguments) {
        // Branch: checks a condition
        if (!condition) {
            // Throws an exception
            throw new IllegalStateException(MessageFormat.format(reason, arguments));
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
