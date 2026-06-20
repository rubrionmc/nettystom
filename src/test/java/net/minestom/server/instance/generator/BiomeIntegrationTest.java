// Package declaration for this file
package net.minestom.server.instance.generator;

// Import of a required class
import net.minestom.server.MinecraftServer;
// Import of a required class
import net.minestom.server.instance.generator.GeneratorImpl.GenSection;
// Import of a required class
import net.minestom.server.world.biome.Biome;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.BeforeAll;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Arrays;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class BiomeIntegrationTest {

    // Code statement
    private static int PLAINS_ID, BADLANDS_ID;

    // Annotation for the following element
    @BeforeAll
    // Start of a method/block
    public static void prepareTest(Env env) {
        // Calls a method
        PLAINS_ID = env.process().biome().getId(Biome.PLAINS);
        // Calls a method
        BADLANDS_ID = env.process().biome().getId(Biome.BADLANDS);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkBiomeSet(Env env) {
        // Assigns a value
        final int minSection = -1;
        // Assigns a value
        final int maxSection = 5;
        // Assigns a value
        final int chunkX = 3;
        // Assigns a value
        final int chunkZ = -2;
        // Assigns a value
        final int sectionCount = maxSection - minSection;
        // Assigns a value
        GenSection[] sections = new GenSection[sectionCount];
        // Calls a method
        Arrays.setAll(sections, i -> new GenSection());
        // Calls a method
        var chunkUnits = GeneratorImpl.chunk(MinecraftServer.getBiomeRegistry(), sections, chunkX, minSection, chunkZ);
        // Assigns a value
        Generator generator = unit -> {
            // Calls a method
            var modifier = unit.modifier();
            // Calls a method
            modifier.setBiome(48, -16, -32, Biome.BADLANDS);
            // Calls a method
            modifier.setBiome(48 + 8, 0, -32, Biome.BADLANDS);
        // End of a block/expression
        };
        // Calls a method
        generator.generate(chunkUnits);

        // Reminder because I (matt) forgot: biome palettes are 4x4x4 sections, so x=2 is really x=8 in the chunk.
        // Calls a method
        assertEquals(BADLANDS_ID, sections[0].biomes().get(0, 0, 0));
        // Calls a method
        assertEquals(PLAINS_ID, sections[1].biomes().get(1, 0, 0));
        // Calls a method
        assertEquals(BADLANDS_ID, sections[1].biomes().get(2, 0, 0));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkBiomeFill(Env env) {
        // Assigns a value
        final int minSection = -1;
        // Assigns a value
        final int maxSection = 5;
        // Assigns a value
        final int chunkX = 3;
        // Assigns a value
        final int chunkZ = -2;
        // Assigns a value
        final int sectionCount = maxSection - minSection;
        // Assigns a value
        GenSection[] sections = new GenSection[sectionCount];
        // Calls a method
        Arrays.setAll(sections, i -> new GenSection());
        // Calls a method
        var chunkUnits = GeneratorImpl.chunk(MinecraftServer.getBiomeRegistry(), sections, chunkX, minSection, chunkZ);
        // Assigns a value
        Generator generator = chunk -> {
            // Calls a method
            var modifier = chunk.modifier();
            // Calls a method
            modifier.fillBiome(Biome.PLAINS);
        // End of a block/expression
        };
        // Calls a method
        generator.generate(chunkUnits);
        // Loop: repeats a block
        for (var section : sections) {
            // Code statement
            section.biomes().getAll((x, y, z, value) ->
                    // Calls a method
                    assertEquals(PLAINS_ID, value));
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
