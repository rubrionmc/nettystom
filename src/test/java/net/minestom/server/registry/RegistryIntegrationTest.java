// Package declaration for this file
package net.minestom.server.registry;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.gamedata.DataPack;
// Import of a required class
import net.minestom.server.world.DimensionType;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;


// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class RegistryIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testUnnamedPack(Env env) {
        // Calls a method
        DynamicRegistry<DimensionType> dimensionRegistry = env.process().dimensionType();
        // Assigns a value
        DimensionType dimensionType = DimensionType.builder()
                // Code statement
                .ambientLight(2f)
                // Calls a method
                .build();
        // Calls a method
        var registryKey = dimensionRegistry.register(Key.key("toocool:fortests"), dimensionType, DataPack.MINESTOM_UNNAMED);
        // Calls a method
        assertEquals(dimensionType, dimensionRegistry.get(registryKey));
        // Calls a method
        assertEquals(DataPack.MINESTOM_UNNAMED, dimensionRegistry.getPack(registryKey));
        // Start of a method/block
        assertDoesNotThrow(() -> {
            // Calls a method
            dimensionRegistry.registryDataPacket(env.process(), false);
        // Code statement
        }, "Registry data packet should not throw for null pack");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    void testDifferentPacksInterlaced(Env env) {
        // Calls a method
        DynamicRegistry<DimensionType> dimensionRegistry = env.process().dimensionType();
        // Assigns a value
        DimensionType dimensionType = DimensionType.builder()
                // Code statement
                .ambientLight(2f)
                // Calls a method
                .build();
        // Calls a method
        assertDoesNotThrow(()-> dimensionRegistry.register(Key.key("toocool:fortests"), dimensionType, DataPack.MINESTOM_UNNAMED));
        // Calls a method
        assertDoesNotThrow(() -> dimensionRegistry.register(Key.key("toocool:fortests2"), dimensionType, DataPack.MINECRAFT_CORE));
    // End of a block/expression
    }
// End of a block/expression
}
