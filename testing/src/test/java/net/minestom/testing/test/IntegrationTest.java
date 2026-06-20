// Package declaration for this file
package net.minestom.testing.test;

// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class IntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testEnv(Env env) {
        // Calls a method
        Assertions.assertNotNull(env);
        // Calls a method
        Assertions.assertNotNull(env.process());
    // End of a block/expression
    }
// End of a block/expression
}
