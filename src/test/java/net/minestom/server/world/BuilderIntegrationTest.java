// Package declaration for this file
package net.minestom.server.world;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.world.attribute.EnvironmentAttributeMap;
// Import of a required class
import net.minestom.server.world.biome.Biome;
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
public class BuilderIntegrationTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBiome(Env ignored) {
        // Calls a method
        Biome existing = MinecraftServer.getBiomeRegistry().get(Biome.CHERRY_GROVE);
        // Calls a method
        assertNotNull(existing);
        // Calls a method
        Biome.Builder builder = Biome.builder(existing);
        // Calls a method
        assertEquals(existing, builder.build());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDimensionType(Env ignored) {
        // Calls a method
        DimensionType existing = MinecraftServer.getDimensionTypeRegistry().get(DimensionType.THE_NETHER);
        // Calls a method
        assertNotNull(existing);
        // Calls a method
        DimensionType.Builder builder = DimensionType.builder(existing);
        // Calls a method
        assertEquals(existing, builder.build());
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testEnvironmentAttributeMap(Env ignored) {
        // Calls a method
        DimensionType existing = MinecraftServer.getDimensionTypeRegistry().get(DimensionType.OVERWORLD);
        // Calls a method
        assertNotNull(existing);
        // Calls a method
        EnvironmentAttributeMap.Builder builder = EnvironmentAttributeMap.builder(existing.attributes());
        // Calls a method
        assertEquals(existing.attributes(), builder.build());
    // End of a block/expression
    }
// End of a block/expression
}
