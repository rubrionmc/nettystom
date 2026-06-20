// Package declaration for this file
package net.minestom.testing.test;

// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;

// Type declaration (class/interface/enum/record)
public class EnvironmentTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void insideTest() {
        // Calls a method
        Assertions.assertTrue(ServerFlag.INSIDE_TEST);
    // End of a block/expression
    }
// End of a block/expression
}
