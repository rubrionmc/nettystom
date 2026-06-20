// Package declaration for this file
package net.minestom.server.instance.block.predicate;

// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import org.junit.jupiter.api.Nested;
// Import of a required class
import org.junit.jupiter.api.Test;
// Import of a required class
import org.junit.jupiter.params.ParameterizedTest;
// Import of a required class
import org.junit.jupiter.params.provider.Arguments;
// Import of a required class
import org.junit.jupiter.params.provider.MethodSource;

// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.stream.Stream;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;
// Static import of a member
import static org.junit.jupiter.params.provider.Arguments.arguments;

// Type declaration (class/interface/enum/record)
public class PropertiesPredicateTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testMultiMatch() {
        // Assigns a value
        var predicate = new PropertiesPredicate(Map.of("facing", new PropertiesPredicate.ValuePredicate.Exact("east"),
                // Calls a method
                "shape", new PropertiesPredicate.ValuePredicate.Exact("inner_left")));
        // Calls a method
        assertTrue(predicate.test(Block.STONE_STAIRS.withProperties(Map.of("facing", "east", "shape", "inner_left"))));
        // Calls a method
        assertFalse(predicate.test(Block.STONE_STAIRS.withProperties(Map.of("facing", "east"))));
        // Calls a method
        assertFalse(predicate.test(Block.STONE));
    // End of a block/expression
    }

    // Annotation for the following element
    @Nested
    // Type declaration (class/interface/enum/record)
    class ValuePredicate {

        // Start of a method/block
        private static Stream<Arguments> exactTests() {
            // Returns a value to the caller
            return Stream.of(
                    // name, expected, actual, valid
                    // Code statement
                    arguments("success", "value", "value", true),
                    // Code statement
                    arguments("fail", "value", "other", false),
                    // Code statement
                    arguments("missing exp", null, "value", false),
                    // Code statement
                    arguments("missing act", "value", null, false)
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @ParameterizedTest(name = "{0}")
        // Annotation for the following element
        @MethodSource("exactTests")
        // Start of a method/block
        public void matchExact(String name, String expected, String actual, boolean valid) {
            // Calls a method
            var predicate = new PropertiesPredicate.ValuePredicate.Exact(expected);
            // Calls a method
            assertEquals(valid, predicate.test(actual));
        // End of a block/expression
        }

        // Start of a method/block
        private static Stream<Arguments> rangeTests() {
            // Returns a value to the caller
            return Stream.of(
                    // name, min, max, value, valid
                    // Code statement
                    arguments("int / min exact", "0", null, "0", true),
                    // Code statement
                    arguments("int / min too low (inclusive)", "1", null, "0", false),
                    // Code statement
                    arguments("int / max exact", null, "1", "0", true),
                    // Code statement
                    arguments("int / max too high (exclusive)", null, "1", "1", false),
                    // Code statement
                    arguments("int / range good a", "0", "2", "1", true),
                    // Code statement
                    arguments("int / range good b", "0", "20", "11", true),
                    // Code statement
                    arguments("int / range too low", "0", "2", "-1", false),
                    // Code statement
                    arguments("int / range too high", "0", "2", "3", false),

                    // Code statement
                    arguments("string / min exact", "a", null, "a", true),
                    // Code statement
                    arguments("string / max exact", null, "b", "a", true),
                    // Code statement
                    arguments("string / range good", "c", "g", "e", true),
                    // Code statement
                    arguments("string / range bad low", "c", "g", "a", false),
                    // Code statement
                    arguments("string / range bad high", "c", "g", "z", false)
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @ParameterizedTest(name = "{0}")
        // Annotation for the following element
        @MethodSource("rangeTests")
        // Start of a method/block
        public void matchRange(String name, String min, String max, String value, boolean valid) {
            // Calls a method
            var predicate = new PropertiesPredicate.ValuePredicate.Range(min, max);
            // Calls a method
            assertEquals(valid, predicate.test(value));
        // End of a block/expression
        }

    // End of a block/expression
    }
// End of a block/expression
}
