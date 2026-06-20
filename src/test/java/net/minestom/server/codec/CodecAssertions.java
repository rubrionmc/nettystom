// Package declaration for this file
package net.minestom.server.codec;


// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

// Type declaration (class/interface/enum/record)
public final class CodecAssertions {

    // Start of a method/block
    public static <T> T assertOk(Result<T> result) {
        // Returns a value to the caller
        return switch (result) {
            // Multiple branching (switch/case)
            case Result.Ok(T value) -> value;
            // Multiple branching (switch/case)
            case Result.Error(String message) -> throw new AssertionError("Expected Ok but got Error: " + message);
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    public static void assertError(String expected, Result<?> result) {
        // Calls a method
        final String message = assertInstanceOf(Result.Error.class, result).message();
        // Calls a method
        assertEquals(expected, message);
    // End of a block/expression
    }
// End of a block/expression
}
