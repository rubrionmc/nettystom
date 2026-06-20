// Package declaration for this file
package net.minestom.server.instance.light;

// Import of a required class
import net.minestom.server.ServerProcess;
// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.instance.LightingChunk;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.Map;

// Static import of a member
import static java.util.Map.entry;
// Static import of a member
import static net.minestom.server.instance.BlockLightMergeIntegrationTest.assertLightInstance;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class WorldRelightIntegrationTest {
    // Start of a method/block
    private Instance createLightingInstance(ServerProcess process) {
        // Calls a method
        var instance = process.instance().createInstanceContainer();
        // Start of a method/block
        instance.setGenerator(unit -> {
            // Calls a method
            unit.modifier().fillHeight(39, 40, Block.STONE);
            // Calls a method
            unit.subdivide().forEach(u -> u.modifier().setBlock(0, 10, 0, Block.GLOWSTONE));
            // Calls a method
            unit.modifier().fillHeight(50, 51, Block.STONE);
        // End of a block/expression
        });
        // Returns a value to the caller
        return instance;
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBorderLava(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
        // Calls a method
        instance.setChunkSupplier(LightingChunk::new);
        // Calls a method
        instance.loadChunk(6, 16).join();
        // Calls a method
        instance.loadChunk(6, 15).join();

        // Calls a method
        instance.setBlock(106, 70, 248, Block.LAVA);
        // Calls a method
        instance.setBlock(106, 71, 249, Block.LAVA);

        // Assigns a value
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(105, 72, 256), 6)
        // End of a block/expression
        );

        // Calls a method
        LightingChunk.relight(instance, instance.getChunks());
        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBlockRemoval(Env env) {
        // Calls a method
        Instance instance = createLightingInstance(env.process());
        // Calls a method
        instance.setChunkSupplier(LightingChunk::new);
        // Loop: repeats a block
        for (int x = -3; x <= 3; x++) {
            // Loop: repeats a block
            for (int z = -3; z <= 3; z++) {
                // Calls a method
                instance.loadChunk(x, z).join();
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        LightingChunk.relight(instance, instance.getChunks());

        // Assigns a value
        var expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(-1, 40, 0), 12),
                // Code statement
                entry(new Vec(-9, 40, 8), 0),
                // Code statement
                entry(new Vec(-1, 40, -16), 12),
                // Code statement
                entry(new Vec(-1, 37, 0), 3),
                // Code statement
                entry(new Vec(-8, 37, -8), 0)
        // End of a block/expression
        );
        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testJackOLantern(Env env) {
        // Calls a method
        Instance instance = createLightingInstance(env.process());
        // Calls a method
        instance.setChunkSupplier(LightingChunk::new);

        // Loop: repeats a block
        for (int x = -3; x <= 3; x++) {
            // Loop: repeats a block
            for (int z = -3; z <= 3; z++) {
                // Calls a method
                instance.loadChunk(x, z).join();
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        instance.setBlock(10, 60, 10, Block.JACK_O_LANTERN);
        // Calls a method
        LightingChunk.relight(instance, instance.getChunks());

        // Assigns a value
        var expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(11, 60, 10), 14),
                // Code statement
                entry(new Vec(10, 61, 10), 14),
                // Code statement
                entry(new Vec(15, 60, 10), 10)
        // End of a block/expression
        );

        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testRedstoneLamp(Env env) {
        // Calls a method
        Instance instance = createLightingInstance(env.process());
        // Calls a method
        instance.setChunkSupplier(LightingChunk::new);

        // Loop: repeats a block
        for (int x = -3; x <= 3; x++) {
            // Loop: repeats a block
            for (int z = -3; z <= 3; z++) {
                // Calls a method
                instance.loadChunk(x, z).join();
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        instance.setBlock(10, 60, 10, Block.REDSTONE_LAMP.withProperty("lit", "true"));
        // Calls a method
        LightingChunk.relight(instance, instance.getChunks());

        // Assigns a value
        var expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(11, 60, 10), 14),
                // Code statement
                entry(new Vec(10, 61, 10), 14),
                // Code statement
                entry(new Vec(15, 60, 10), 10)
        // End of a block/expression
        );

        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }
// End of a block/expression
}
