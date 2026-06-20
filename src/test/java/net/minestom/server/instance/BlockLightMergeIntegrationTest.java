// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.coordinate.Vec;
// Import of a required class
import net.minestom.server.instance.block.Block;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.HashMap;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Static import of a member
import static java.util.Map.entry;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.fail;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class BlockLightMergeIntegrationTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testPropagationAir(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
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
        instance.setBlock(8, 100,8 , Block.TORCH);

        // Calls a method
        Map<Vec, Integer> expectedLights = new HashMap<>();
        // Loop: repeats a block
        for (int y = -15; y <= 15; ++y) {
            // Calls a method
            expectedLights.put(new Vec(8, 100 + y, 8), Math.max(0, 14 - Math.abs(y)));
        // End of a block/expression
        }

        // Calls a method
        LightingChunk.relightSection(instance, 0, 6, 0);
        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testTorch(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
        // Calls a method
        instance.setChunkSupplier(LightingChunk::new);
        // Start of a method/block
        instance.setGenerator(unit -> {
            // Calls a method
            unit.modifier().fillHeight(39, 40, Block.STONE);
            // Calls a method
            unit.modifier().fillHeight(50, 51, Block.STONE);
        // End of a block/expression
        });

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
        instance.setBlock(1, 40,1 , Block.TORCH);

        // Assigns a value
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(2, 40, 2), 12)
        // End of a block/expression
        );

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testTorch2(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
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
        instance.setBlock(1, 40,1 , Block.TORCH);
        // Assigns a value
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(2, 40, 2), 12)
        // End of a block/expression
        );
        // Calls a method
        LightingChunk.relightSection(instance, 1, 2, 1);
        // Calls a method
        assertLightInstance(instance, expectedLights);

        // Calls a method
        instance.setBlock(-2, 40,-2, Block.TORCH);
        // Assigns a value
        expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(2, 40, 2), 12)
        // End of a block/expression
        );
        // Calls a method
        LightingChunk.relightSection(instance, -1, 2, -1);
        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testPropagationAir2(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
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
        instance.setBlock(4, 60,8 , Block.TORCH);

        // Calls a method
        Map<Vec, Integer> expectedLights = new HashMap<>();
        // Loop: repeats a block
        for (int y = -15; y <= 15; ++y) {
            // Calls a method
            expectedLights.put(new Vec(8, 60 + y, 8), Math.max(0, 10 - Math.abs(y)));
        // End of a block/expression
        }
        // Loop: repeats a block
        for (int y = -15; y <= 15; ++y) {
            // Calls a method
            expectedLights.put(new Vec(-2, 60 + y, 8), Math.max(0, 8 - Math.abs(y)));
        // End of a block/expression
        }

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testPropagationAirRemoval(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
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
        instance.setBlock(4, 100,8 , Block.TORCH);

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Calls a method
        instance.setBlock(4, 100,8 , Block.AIR);

        // Calls a method
        Map<Vec, Integer> expectedLights = new HashMap<>();
        // Loop: repeats a block
        for (int y = -15; y <= 15; ++y) {
            // Calls a method
            expectedLights.put(new Vec(8, 100 + y, 8), 0);
        // End of a block/expression
        }
        // Loop: repeats a block
        for (int y = -15; y <= 15; ++y) {
            // Calls a method
            expectedLights.put(new Vec(-2, 100 + y, 8), 0);
        // End of a block/expression
        }

        // Calls a method
        LightingChunk.relightSection(instance, 0, 6, 0);

        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBorderOcclusion(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
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
        instance.setBlock(-1, 40, 4, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-1, 40, 3, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-2, 40, 3, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-3, 40, 3, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-3, 40, 4, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-3, 40, 5, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-2, 40, 5, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-1, 40, 5, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-2, 41, 4, Block.STONE);
        // Calls a method
        instance.setBlock(-2, 40, 4, Block.TORCH);

        // Assigns a value
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(-2, 42, 4), 0),
                // Code statement
                entry(new Vec(-2, 42, 3), 1),
                // Code statement
                entry(new Vec(-2, 41, 3), 2),
                // Code statement
                entry(new Vec(0, 40, 4), 2)
        // End of a block/expression
        );

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBorderOcclusion2(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
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
        instance.setBlock(-1, 41, 4, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-1, 40, 3, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-2, 40, 3, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-3, 40, 3, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-3, 40, 4, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-3, 40, 5, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-2, 40, 5, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-1, 40, 5, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-2, 41, 4, Block.STONE);
        // Calls a method
        instance.setBlock(-2, 40, 4, Block.TORCH);

        // Assigns a value
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(-2, 42, 4), 8),
                // Code statement
                entry(new Vec(-2, 40, 2), 8),
                // Code statement
                entry(new Vec(-4, 40, 4), 4)

        // End of a block/expression
        );

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);
        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBorderOcclusion3(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
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
        instance.setBlock(0, 40, 8, Block.STONE);
        // Calls a method
        instance.setBlock(1, 40, 8, Block.STONE);
        // Calls a method
        instance.setBlock(0, 41, 7, Block.STONE);
        // Calls a method
        instance.setBlock(1, 41, 7, Block.STONE);
        // Calls a method
        instance.setBlock(2, 40, 7, Block.STONE);
        // Calls a method
        instance.setBlock(1, 40, 6, Block.STONE);
        // Calls a method
        instance.setBlock(0, 40, 6, Block.STONE);

        // Calls a method
        instance.setBlock(1, 40, 7, Block.TORCH);
        // Calls a method
        instance.setBlock(0, 40, 7, Block.SANDSTONE_SLAB.withProperty("type", "bottom"));
        // Calls a method
        instance.setBlock(-1, 40, 7, Block.SANDSTONE_SLAB.withProperty("type", "top"));

        // Assigns a value
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(-2, 40, 7), 0)

        // End of a block/expression
        );

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);
        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBorderCrossing(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
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

        // Loop: repeats a block
        for (int x = -2; x <= 1; ++x) {
            // Loop: repeats a block
            for (int z = 5; z <= 20; ++z) {
                // Calls a method
                instance.setBlock(x, 42, z, Block.STONE);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Loop: repeats a block
        for (int z = 5; z <= 20; ++z) {
            // Loop: repeats a block
            for (int y = 40; y <= 42; ++y) {
                // Calls a method
                instance.setBlock(1, y, z, Block.STONE);
                // Calls a method
                instance.setBlock(-2, y, z, Block.STONE);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Loop: repeats a block
        for (int y = 40; y <= 42; ++y) {
            // Calls a method
            instance.setBlock(-1, y, 6, Block.STONE);
            // Calls a method
            instance.setBlock(0, y, 8, Block.STONE);
            // Calls a method
            instance.setBlock(-1, y, 10, Block.STONE);
            // Calls a method
            instance.setBlock(0, y, 12, Block.STONE);
            // Calls a method
            instance.setBlock(-1, y, 14, Block.STONE);
            // Calls a method
            instance.setBlock(0, y, 16, Block.STONE);
            // Calls a method
            instance.setBlock(-1, y, 18, Block.STONE);
            // Calls a method
            instance.setBlock(0, y, 20, Block.STONE);
        // End of a block/expression
        }

        // Calls a method
        instance.setBlock(-1, 40, 11, Block.TORCH);

        // Assigns a value
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(-1, 40, 19), 2),
                // Code statement
                entry(new Vec(0, 40, 19), 3),
                // Code statement
                entry(new Vec(-1, 40, 16), 7),
                // Code statement
                entry(new Vec(-1, 40, 13), 12),
                // Code statement
                entry(new Vec(-1, 40, 7), 8),
                // Code statement
                entry(new Vec(-3, 40, 4), 1),
                // Code statement
                entry(new Vec(-3, 40, 5), 0),
                // Code statement
                entry(new Vec(-1, 40, 20), 1)

        // End of a block/expression
        );

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);
        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBorderOcclusionRemoval(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
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
        instance.setBlock(-1, 41, 4, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-1, 40, 3, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-2, 40, 3, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-3, 40, 3, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-3, 40, 4, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-3, 40, 5, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-2, 40, 5, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-1, 40, 5, Block.MAGMA_BLOCK);
        // Calls a method
        instance.setBlock(-2, 41, 4, Block.STONE);


        // Calls a method
        instance.setBlock(-2, 40, 4, Block.TORCH);

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Calls a method
        instance.setBlock(-2, 40, 4, Block.STONE);

        // Assigns a value
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(-2, 42, 4), 1),
                // Code statement
                entry(new Vec(-2, 40, 2), 2),
                // Code statement
                entry(new Vec(-4, 40, 4), 2)

        // End of a block/expression
        );

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void chunkIntersection(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
        // Calls a method
        instance.setChunkSupplier(LightingChunk::new);
        // Loop: repeats a block
        for (int x = 4; x <= 7; x++) {
            // Loop: repeats a block
            for (int z = 6; z <= 8; z++) {
                // Calls a method
                instance.loadChunk(x, z).join();
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        instance.setBlock(94, -35, 128, Block.GLOW_LICHEN.withProperties(Map.of("west", "true")));

        // Calls a method
        LightingChunk.relight(instance, instance.getChunks());

        // Calls a method
        var val = instance.getChunk(5, 8).getSection(-2).blockLight().getLevel(14, 0, 0);
        // Calls a method
        assertEquals(4, val);

        // Calls a method
        var val2 = instance.getChunk(5, 8).getSection(-3).blockLight().getLevel(14, 15, 0);
        // Calls a method
        assertEquals(5, val2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void lightLookupTest(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
        // Calls a method
        instance.setChunkSupplier(LightingChunk::new);
        // Loop: repeats a block
        for (int x = 4; x <= 7; x++) {
            // Loop: repeats a block
            for (int z = 6; z <= 8; z++) {
                // Calls a method
                instance.loadChunk(x, z).join();
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        instance.setBlock(94, -35, 128, Block.GLOW_LICHEN.withProperties(Map.of("west", "true")));

        // Calls a method
        var val = instance.getBlockLight(94, -35, 128);
        // Calls a method
        assertEquals(7, val);

        // Calls a method
        var val2 = instance.getBlockLight(94, -36, 128);
        // Calls a method
        assertEquals(6, val2);

        // Calls a method
        var val3 = instance.getSkyLight(94, -34, 128);
        // Calls a method
        assertEquals(0, val3);

        // Calls a method
        var val4 = instance.getSkyLight(94, 41, 128);
        // Calls a method
        assertEquals(15, val4);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void lightLookupTestCrossBorder(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
        // Calls a method
        instance.setChunkSupplier(LightingChunk::new);
        // Loop: repeats a block
        for (int x = 4; x <= 7; x++) {
            // Loop: repeats a block
            for (int z = 6; z <= 8; z++) {
                // Calls a method
                instance.loadChunk(x, z).join();
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        instance.setBlock(94, -35, 128, Block.GLOWSTONE);

        // Calls a method
        var val = instance.getBlockLight(94, -35, 128);
        // Calls a method
        assertEquals(15, val);

        // Calls a method
        var val2 = instance.getBlockLight(97, -36, 135);
        // Calls a method
        assertEquals(4, val2);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void skylight(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
        // Calls a method
        instance.setChunkSupplier(LightingChunk::new);
        // Loop: repeats a block
        for (int x = 4; x <= 7; x++) {
            // Loop: repeats a block
            for (int z = 6; z <= 8; z++) {
                // Calls a method
                instance.loadChunk(x, z).join();
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        instance.setBlock(94, 50, 128, Block.STONE);

        // Calls a method
        LightingChunk.relight(instance, instance.getChunks());

        // Calls a method
        var val = lightValSky(instance, new Vec(94, 41, 128));
        // Calls a method
        assertEquals(14, val);
    // End of a block/expression
    }


    // Annotation for the following element
    @Test
    // Start of a method/block
    public void skylightShortGrass(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
        // Calls a method
        instance.setChunkSupplier(LightingChunk::new);
        // Loop: repeats a block
        for (int x = 4; x <= 7; x++) {
            // Loop: repeats a block
            for (int z = 6; z <= 8; z++) {
                // Calls a method
                instance.loadChunk(x, z).join();
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        instance.setBlock(94, 50, 128, Block.SHORT_GRASS);

        // Calls a method
        LightingChunk.relight(instance, instance.getChunks());

        // Calls a method
        var val = lightValSky(instance, new Vec(94, 50, 128));
        // Calls a method
        assertEquals(15, val);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void skylightContained(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
        // Calls a method
        instance.setChunkSupplier(LightingChunk::new);
        // Loop: repeats a block
        for (int x = 4; x <= 7; x++) {
            // Loop: repeats a block
            for (int z = 6; z <= 8; z++) {
                // Calls a method
                instance.loadChunk(x, z).join();
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        instance.setBlock(94, 50, 128, Block.STONE);
        // Calls a method
        instance.setBlock(94, 52, 128, Block.STONE);

        // Calls a method
        instance.setBlock(94, 51, 127, Block.STONE);
        // Calls a method
        instance.setBlock(94, 51, 129, Block.STONE);
        // Calls a method
        instance.setBlock(93, 51, 128, Block.STONE);
        // Calls a method
        instance.setBlock(95, 51, 128, Block.STONE);

        // Calls a method
        LightingChunk.relight(instance, instance.getChunks());

        // Calls a method
        var val = lightValSky(instance, new Vec(94, 51, 128));
        // Calls a method
        var val2 = lightValSky(instance, new Vec(94, 52, 128));
        // Calls a method
        assertEquals(0, val2);
        // Calls a method
        assertEquals(0, val);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDiagonalRemoval(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
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
        instance.setBlock(-2, 40, 14, Block.TORCH);

        // Assigns a value
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(-2, 40, 14), 14),
                // Code statement
                entry(new Vec(-2, 40, 18), 10),
                // Code statement
                entry(new Vec(2, 40, 18), 6)

        // End of a block/expression
        );

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);
        // Calls a method
        assertLightInstance(instance, expectedLights);

        // Calls a method
        instance.setBlock(-2, 40, 14, Block.AIR);

        // Assigns a value
        expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(-2, 40, 14), 0),
                // Code statement
                entry(new Vec(-2, 40, 18), 0),
                // Code statement
                entry(new Vec(2, 40, 18), 0)

        // End of a block/expression
        );
        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);
        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDiagonalRemoval2(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
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
        instance.setBlock(1, 40, 1, Block.TORCH);
        // Calls a method
        instance.setBlock(1, 40, 17, Block.TORCH);

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Calls a method
        instance.setBlock(1, 40, 17, Block.AIR);

        // Assigns a value
        var expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(-3, 40, 2), 9)
        // End of a block/expression
        );

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testDouble(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
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
        instance.setBlock(-2, 40, 14, Block.TORCH);
        // Calls a method
        instance.setBlock(1, 40, 27, Block.TORCH);

        // Assigns a value
        var expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(-4, 40, 25), 7),
                // Code statement
                entry(new Vec(-4, 40, 18), 8)
        // End of a block/expression
        );

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Calls a method
        assertLightInstance(instance, expectedLights);

        // Calls a method
        instance.setBlock(-2, 40, 14, Block.AIR);

        // Assigns a value
        expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(-4, 40, 25), 7),
                // Code statement
                entry(new Vec(-4, 40, 18), 0)
        // End of a block/expression
        );

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void testBlockRemoval(Env env) {
        // Calls a method
        Instance instance = env.createFlatInstance();
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
        instance.setBlock(0, 40, 0, Block.STONE);
        // Calls a method
        instance.setBlock(1, 40, -1, Block.STONE);
        // Calls a method
        instance.setBlock(0, 40, -2, Block.STONE);
        // Calls a method
        instance.setBlock(-1, 40, -1, Block.STONE);
        // Calls a method
        instance.setBlock(0, 41, -1, Block.STONE);
        // Calls a method
        instance.setBlock(0, 40, -1, Block.GLOWSTONE);

        // Assigns a value
        var expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(-2, 40, -1), 0)
        // End of a block/expression
        );

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Calls a method
        assertLightInstance(instance, expectedLights);

        // Calls a method
        instance.setBlock(-1, 40, -1, Block.AIR);

        // Assigns a value
        expectedLights = Map.ofEntries(
                // Code statement
                entry(new Vec(-2, 40, -1), 13)
        // End of a block/expression
        );

        // Calls a method
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Calls a method
        assertLightInstance(instance, expectedLights);
    // End of a block/expression
    }

    // Start of a method/block
    static byte lightVal(Instance instance, Vec pos) {
        // Calls a method
        final Vec modPos = new Vec(((pos.blockX() % 16) + 16) % 16, ((pos.blockY() % 16) + 16) % 16, ((pos.blockZ() % 16) + 16) % 16);
        // Calls a method
        Chunk chunk = instance.getChunkAt(pos.blockX(), pos.blockZ());
        // Returns a value to the caller
        return (byte) chunk.getSectionAt(pos.blockY()).blockLight().getLevel(modPos.blockX(), modPos.blockY(), modPos.blockZ());
    // End of a block/expression
    }

    // Start of a method/block
    static byte lightValSky(Instance instance, Vec pos) {
        // Calls a method
        final Vec modPos = new Vec(((pos.blockX() % 16) + 16) % 16, ((pos.blockY() % 16) + 16) % 16, ((pos.blockZ() % 16) + 16) % 16);
        // Calls a method
        Chunk chunk = instance.getChunkAt(pos.blockX(), pos.blockZ());
        // Returns a value to the caller
        return (byte) chunk.getSectionAt(pos.blockY()).skyLight().getLevel(modPos.blockX(), modPos.blockY(), modPos.blockZ());
    // End of a block/expression
    }

    // Start of a method/block
    public static void assertLightInstance(Instance instance, Map<Vec, Integer> expectedLights) {
        // Calls a method
        List<String> errors = new ArrayList<>();
        // Loop: repeats a block
        for (var entry : expectedLights.entrySet()) {
            // Calls a method
            final Integer expected = entry.getValue();
            // Calls a method
            final Vec pos = entry.getKey();

            // Calls a method
            final byte light = lightVal(instance, pos);

            // Branch: checks a condition
            if (light != expected) {
                // Calls a method
                String errorLine = String.format("Expected %d at [%d,%d,%d] but got %d", expected, pos.blockX(), pos.blockY(), pos.blockZ(), light);
                // Calls a method
                System.err.println();
                // Calls a method
                errors.add(errorLine);
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Branch: checks a condition
        if (!errors.isEmpty()) {
            // Calls a method
            StringBuilder sb = new StringBuilder();
            // Loop: repeats a block
            for (String s : errors) {
                // Calls a method
                sb.append(s).append("\n");
            // End of a block/expression
            }
            // Calls a method
            System.err.println(sb);
            // Calls a method
            fail();
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}