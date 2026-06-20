// Package declaration for this file
package net.minestom.server.component;

// Import of a required class
import net.kyori.adventure.key.Key;
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
public class DataComponentTest {
    // Annotation for the following element
    @Test
    // Code statement
    public void registry(Env env) { // Tricky registry; so we ensure they are loaded (requires class loading before accessible keys)
        // Calls a method
        Assertions.assertNotNull(DataComponent.fromKey(Key.key("lore")), "Registry class was not initialized");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void stringFromKey(Env env) {
        // Calls a method
        Assertions.assertSame(DataComponent.fromKey("lore"), DataComponent.fromKey(Key.key("lore")));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testStatic(Env env) {
        // Calls a method
        Assertions.assertSame(DataComponents.LORE, DataComponent.fromKey("lore"));
    // End of a block/expression
    }
// End of a block/expression
}
