// Package declaration for this file
package net.minestom.server.utils.collection;

// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Type declaration (class/interface/enum/record)
public class AutoIncrementMapTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void test() {
        // Calls a method
        AutoIncrementMap<String> map = new AutoIncrementMap<>();
        // Loop: repeats a block
        for (int i = 0; i < 1000; i++) {
            // Calls a method
            assertEquals(i, map.get("test" + i));
            // Loop: repeats a block
            for (int j = 0; j < i; j++) {
                // Calls a method
                assertEquals(j, map.get("test" + j));
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
