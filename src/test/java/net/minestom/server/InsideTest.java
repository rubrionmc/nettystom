// Package declaration for this file
package net.minestom.server;

// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertTrue;

// Type declaration (class/interface/enum/record)
public class InsideTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void inside() {
        // Calls a method
        assertTrue(ServerFlag.INSIDE_TEST);
    // End of a block/expression
    }
// End of a block/expression
}
