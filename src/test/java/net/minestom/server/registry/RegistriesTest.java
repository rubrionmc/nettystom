// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.minestom.server.component.DataComponentMap;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class RegistriesTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testMaterialPrototypes() {
        // Calls a method
        var registries = Registries.vanilla();
        // Loop: repeats a block
        for (var entry : registries.material().values()) {
            // Calls a method
            var prototype = entry.prototype();
            // Calls a method
            Assertions.assertNotNull(prototype);
            // Branch: checks a condition
            if (prototype.isEmpty()) {
                // Calls a method
                Assertions.assertSame(DataComponentMap.EMPTY, prototype);
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
