// Package declaration for this file
package net.minestom.server.item;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class MaterialReadTest {

    // Start of a method/block
    static {
        // Calls a method
        MinecraftServer.init();
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void loadAllMaterials() {
        // Materials are lazy loaded now so this is a sanity check that they all load
        // Loop: repeats a block
        for (Material material : Material.values()) {
            // Just loading the material should be enough to test that it exists
            // Calls a method
            assertNotNull(material.prototype());
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
