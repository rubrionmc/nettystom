// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertThrows;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class WorldBorderIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void setWorldborderSize(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();

        // Calls a method
        instance.setWorldBorder(WorldBorder.DEFAULT_BORDER.withDiameter(50));
        // Calls a method
        assertEquals(50, instance.getWorldBorder().diameter());
        // Calls a method
        instance.setWorldBorder(WorldBorder.DEFAULT_BORDER.withDiameter(10));
        // Calls a method
        assertEquals(10, instance.getWorldBorder().diameter());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void resizeWorldBorder(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();

        // Calls a method
        WorldBorder border = instance.getWorldBorder();
        // Calls a method
        instance.setWorldBorder(border.withDiameter(10));
        // Calls a method
        assertEquals(10, instance.getWorldBorder().diameter());

        // Lerp
        // Calls a method
        instance.setWorldBorder(border.withDiameter(30), 1);
        // Loop: repeats a block
        for (int i = 0; i < 10; i++) {
            // Calls a method
            assertEquals(10 + i, instance.getWorldBorder().diameter());
            // Calls a method
            instance.tick(0);
        // End of a block/expression
        }

        // Lerp from another diameter mid lerp
        // Calls a method
        instance.setWorldBorder(border.withDiameter(25), 0.25);
        // Loop: repeats a block
        for (int i = 0; i < 5; i++) {
            // Calls a method
            assertEquals(20 + i, instance.getWorldBorder().diameter());
            // Calls a method
            instance.tick(0);
        // End of a block/expression
        }

        // Ensure lerp finished
        // Loop: repeats a block
        for (int i = 0; i < 4; i++) {
            // Calls a method
            assertEquals(25, instance.getWorldBorder().diameter());
            // Calls a method
            instance.tick(0);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void invalidArguments(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();

        // Calls a method
        WorldBorder border = instance.getWorldBorder();
        // Calls a method
        assertThrows(IllegalStateException.class, () -> instance.setWorldBorder(border, -1));
        // Calls a method
        assertThrows(IllegalArgumentException.class, () -> border.withDiameter(-1));
    // End of a block/expression
    }
// End of a block/expression
}
