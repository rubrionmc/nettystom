// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static java.util.Map.entry;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.fail;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class BlockLightMergeIntegrationTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testPropagationAir(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = -3; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z <= 3; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(8, 100,8 , Block.TORCH);

        // Affecte une valeur
        Map<Vec, Integer> expectedLights = new HashMap<>();
        // Boucle : répète un bloc
        for (int y = -15; y <= 15; ++y) {
            // Appelle une méthode
            expectedLights.put(new Vec(8, 100 + y, 8), Math.max(0, 14 - Math.abs(y)));
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 6, 0);
        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testTorch(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> {
            // Appelle une méthode
            unit.modifier().fillHeight(39, 40, Block.STONE);
            // Appelle une méthode
            unit.modifier().fillHeight(50, 51, Block.STONE);
        // Fin d'un bloc/d'une expression
        });

        // Boucle : répète un bloc
        for (int x = -3; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z <= 3; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(1, 40,1 , Block.TORCH);

        // Affecte une valeur
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(2, 40, 2), 12)
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testTorch2(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);

        // Boucle : répète un bloc
        for (int x = -3; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z <= 3; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(1, 40,1 , Block.TORCH);
        // Affecte une valeur
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(2, 40, 2), 12)
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        LightingChunk.relightSection(instance, 1, 2, 1);
        // Appelle une méthode
        assertLightInstance(instance, expectedLights);

        // Appelle une méthode
        instance.setBlock(-2, 40,-2, Block.TORCH);
        // Affecte une valeur
        expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(2, 40, 2), 12)
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        LightingChunk.relightSection(instance, -1, 2, -1);
        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testPropagationAir2(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = -3; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z <= 3; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(4, 60,8 , Block.TORCH);

        // Affecte une valeur
        Map<Vec, Integer> expectedLights = new HashMap<>();
        // Boucle : répète un bloc
        for (int y = -15; y <= 15; ++y) {
            // Appelle une méthode
            expectedLights.put(new Vec(8, 60 + y, 8), Math.max(0, 10 - Math.abs(y)));
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (int y = -15; y <= 15; ++y) {
            // Appelle une méthode
            expectedLights.put(new Vec(-2, 60 + y, 8), Math.max(0, 8 - Math.abs(y)));
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testPropagationAirRemoval(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = -3; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z <= 3; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(4, 100,8 , Block.TORCH);

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Appelle une méthode
        instance.setBlock(4, 100,8 , Block.AIR);

        // Affecte une valeur
        Map<Vec, Integer> expectedLights = new HashMap<>();
        // Boucle : répète un bloc
        for (int y = -15; y <= 15; ++y) {
            // Appelle une méthode
            expectedLights.put(new Vec(8, 100 + y, 8), 0);
        // Fin d'un bloc/d'une expression
        }
        // Boucle : répète un bloc
        for (int y = -15; y <= 15; ++y) {
            // Appelle une méthode
            expectedLights.put(new Vec(-2, 100 + y, 8), 0);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 6, 0);

        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testBorderOcclusion(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = -3; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z <= 3; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(-1, 40, 4, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-1, 40, 3, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-2, 40, 3, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-3, 40, 3, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-3, 40, 4, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-3, 40, 5, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-2, 40, 5, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-1, 40, 5, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-2, 41, 4, Block.STONE);
        // Appelle une méthode
        instance.setBlock(-2, 40, 4, Block.TORCH);

        // Affecte une valeur
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(-2, 42, 4), 0),
                // Instruction de code
                entry(new Vec(-2, 42, 3), 1),
                // Instruction de code
                entry(new Vec(-2, 41, 3), 2),
                // Instruction de code
                entry(new Vec(0, 40, 4), 2)
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testBorderOcclusion2(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = -3; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z <= 3; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(-1, 41, 4, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-1, 40, 3, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-2, 40, 3, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-3, 40, 3, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-3, 40, 4, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-3, 40, 5, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-2, 40, 5, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-1, 40, 5, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-2, 41, 4, Block.STONE);
        // Appelle une méthode
        instance.setBlock(-2, 40, 4, Block.TORCH);

        // Affecte une valeur
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(-2, 42, 4), 8),
                // Instruction de code
                entry(new Vec(-2, 40, 2), 8),
                // Instruction de code
                entry(new Vec(-4, 40, 4), 4)

        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);
        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testBorderOcclusion3(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = -3; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z <= 3; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(0, 40, 8, Block.STONE);
        // Appelle une méthode
        instance.setBlock(1, 40, 8, Block.STONE);
        // Appelle une méthode
        instance.setBlock(0, 41, 7, Block.STONE);
        // Appelle une méthode
        instance.setBlock(1, 41, 7, Block.STONE);
        // Appelle une méthode
        instance.setBlock(2, 40, 7, Block.STONE);
        // Appelle une méthode
        instance.setBlock(1, 40, 6, Block.STONE);
        // Appelle une méthode
        instance.setBlock(0, 40, 6, Block.STONE);

        // Appelle une méthode
        instance.setBlock(1, 40, 7, Block.TORCH);
        // Appelle une méthode
        instance.setBlock(0, 40, 7, Block.SANDSTONE_SLAB.withProperty("type", "bottom"));
        // Appelle une méthode
        instance.setBlock(-1, 40, 7, Block.SANDSTONE_SLAB.withProperty("type", "top"));

        // Affecte une valeur
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(-2, 40, 7), 0)

        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);
        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testBorderCrossing(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = -3; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z <= 3; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        for (int x = -2; x <= 1; ++x) {
            // Boucle : répète un bloc
            for (int z = 5; z <= 20; ++z) {
                // Appelle une méthode
                instance.setBlock(x, 42, z, Block.STONE);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        for (int z = 5; z <= 20; ++z) {
            // Boucle : répète un bloc
            for (int y = 40; y <= 42; ++y) {
                // Appelle une méthode
                instance.setBlock(1, y, z, Block.STONE);
                // Appelle une méthode
                instance.setBlock(-2, y, z, Block.STONE);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Boucle : répète un bloc
        for (int y = 40; y <= 42; ++y) {
            // Appelle une méthode
            instance.setBlock(-1, y, 6, Block.STONE);
            // Appelle une méthode
            instance.setBlock(0, y, 8, Block.STONE);
            // Appelle une méthode
            instance.setBlock(-1, y, 10, Block.STONE);
            // Appelle une méthode
            instance.setBlock(0, y, 12, Block.STONE);
            // Appelle une méthode
            instance.setBlock(-1, y, 14, Block.STONE);
            // Appelle une méthode
            instance.setBlock(0, y, 16, Block.STONE);
            // Appelle une méthode
            instance.setBlock(-1, y, 18, Block.STONE);
            // Appelle une méthode
            instance.setBlock(0, y, 20, Block.STONE);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(-1, 40, 11, Block.TORCH);

        // Affecte une valeur
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(-1, 40, 19), 2),
                // Instruction de code
                entry(new Vec(0, 40, 19), 3),
                // Instruction de code
                entry(new Vec(-1, 40, 16), 7),
                // Instruction de code
                entry(new Vec(-1, 40, 13), 12),
                // Instruction de code
                entry(new Vec(-1, 40, 7), 8),
                // Instruction de code
                entry(new Vec(-3, 40, 4), 1),
                // Instruction de code
                entry(new Vec(-3, 40, 5), 0),
                // Instruction de code
                entry(new Vec(-1, 40, 20), 1)

        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);
        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testBorderOcclusionRemoval(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = -3; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z <= 3; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(-1, 41, 4, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-1, 40, 3, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-2, 40, 3, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-3, 40, 3, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-3, 40, 4, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-3, 40, 5, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-2, 40, 5, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-1, 40, 5, Block.MAGMA_BLOCK);
        // Appelle une méthode
        instance.setBlock(-2, 41, 4, Block.STONE);


        // Appelle une méthode
        instance.setBlock(-2, 40, 4, Block.TORCH);

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Appelle une méthode
        instance.setBlock(-2, 40, 4, Block.STONE);

        // Affecte une valeur
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(-2, 42, 4), 1),
                // Instruction de code
                entry(new Vec(-2, 40, 2), 2),
                // Instruction de code
                entry(new Vec(-4, 40, 4), 2)

        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void chunkIntersection(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = 4; x <= 7; x++) {
            // Boucle : répète un bloc
            for (int z = 6; z <= 8; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(94, -35, 128, Block.GLOW_LICHEN.withProperties(Map.of("west", "true")));

        // Appelle une méthode
        LightingChunk.relight(instance, instance.getChunks());

        // Appelle une méthode
        var val = instance.getChunk(5, 8).getSection(-2).blockLight().getLevel(14, 0, 0);
        // Appelle une méthode
        assertEquals(4, val);

        // Appelle une méthode
        var val2 = instance.getChunk(5, 8).getSection(-3).blockLight().getLevel(14, 15, 0);
        // Appelle une méthode
        assertEquals(5, val2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void lightLookupTest(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = 4; x <= 7; x++) {
            // Boucle : répète un bloc
            for (int z = 6; z <= 8; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(94, -35, 128, Block.GLOW_LICHEN.withProperties(Map.of("west", "true")));

        // Appelle une méthode
        var val = instance.getBlockLight(94, -35, 128);
        // Appelle une méthode
        assertEquals(7, val);

        // Appelle une méthode
        var val2 = instance.getBlockLight(94, -36, 128);
        // Appelle une méthode
        assertEquals(6, val2);

        // Appelle une méthode
        var val3 = instance.getSkyLight(94, -34, 128);
        // Appelle une méthode
        assertEquals(0, val3);

        // Appelle une méthode
        var val4 = instance.getSkyLight(94, 41, 128);
        // Appelle une méthode
        assertEquals(15, val4);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void lightLookupTestCrossBorder(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = 4; x <= 7; x++) {
            // Boucle : répète un bloc
            for (int z = 6; z <= 8; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(94, -35, 128, Block.GLOWSTONE);

        // Appelle une méthode
        var val = instance.getBlockLight(94, -35, 128);
        // Appelle une méthode
        assertEquals(15, val);

        // Appelle une méthode
        var val2 = instance.getBlockLight(97, -36, 135);
        // Appelle une méthode
        assertEquals(4, val2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void skylight(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = 4; x <= 7; x++) {
            // Boucle : répète un bloc
            for (int z = 6; z <= 8; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(94, 50, 128, Block.STONE);

        // Appelle une méthode
        LightingChunk.relight(instance, instance.getChunks());

        // Appelle une méthode
        var val = lightValSky(instance, new Vec(94, 41, 128));
        // Appelle une méthode
        assertEquals(14, val);
    // Fin d'un bloc/d'une expression
    }


    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void skylightShortGrass(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = 4; x <= 7; x++) {
            // Boucle : répète un bloc
            for (int z = 6; z <= 8; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(94, 50, 128, Block.SHORT_GRASS);

        // Appelle une méthode
        LightingChunk.relight(instance, instance.getChunks());

        // Appelle une méthode
        var val = lightValSky(instance, new Vec(94, 50, 128));
        // Appelle une méthode
        assertEquals(15, val);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void skylightContained(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = 4; x <= 7; x++) {
            // Boucle : répète un bloc
            for (int z = 6; z <= 8; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(94, 50, 128, Block.STONE);
        // Appelle une méthode
        instance.setBlock(94, 52, 128, Block.STONE);

        // Appelle une méthode
        instance.setBlock(94, 51, 127, Block.STONE);
        // Appelle une méthode
        instance.setBlock(94, 51, 129, Block.STONE);
        // Appelle une méthode
        instance.setBlock(93, 51, 128, Block.STONE);
        // Appelle une méthode
        instance.setBlock(95, 51, 128, Block.STONE);

        // Appelle une méthode
        LightingChunk.relight(instance, instance.getChunks());

        // Appelle une méthode
        var val = lightValSky(instance, new Vec(94, 51, 128));
        // Appelle une méthode
        var val2 = lightValSky(instance, new Vec(94, 52, 128));
        // Appelle une méthode
        assertEquals(0, val2);
        // Appelle une méthode
        assertEquals(0, val);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDiagonalRemoval(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = -3; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z <= 3; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(-2, 40, 14, Block.TORCH);

        // Affecte une valeur
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(-2, 40, 14), 14),
                // Instruction de code
                entry(new Vec(-2, 40, 18), 10),
                // Instruction de code
                entry(new Vec(2, 40, 18), 6)

        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);
        // Appelle une méthode
        assertLightInstance(instance, expectedLights);

        // Appelle une méthode
        instance.setBlock(-2, 40, 14, Block.AIR);

        // Affecte une valeur
        expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(-2, 40, 14), 0),
                // Instruction de code
                entry(new Vec(-2, 40, 18), 0),
                // Instruction de code
                entry(new Vec(2, 40, 18), 0)

        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);
        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDiagonalRemoval2(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = -3; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z <= 3; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(1, 40, 1, Block.TORCH);
        // Appelle une méthode
        instance.setBlock(1, 40, 17, Block.TORCH);

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Appelle une méthode
        instance.setBlock(1, 40, 17, Block.AIR);

        // Affecte une valeur
        var expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(-3, 40, 2), 9)
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testDouble(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = -3; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z <= 3; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(-2, 40, 14, Block.TORCH);
        // Appelle une méthode
        instance.setBlock(1, 40, 27, Block.TORCH);

        // Affecte une valeur
        var expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(-4, 40, 25), 7),
                // Instruction de code
                entry(new Vec(-4, 40, 18), 8)
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Appelle une méthode
        assertLightInstance(instance, expectedLights);

        // Appelle une méthode
        instance.setBlock(-2, 40, 14, Block.AIR);

        // Affecte une valeur
        expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(-4, 40, 25), 7),
                // Instruction de code
                entry(new Vec(-4, 40, 18), 0)
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testBlockRemoval(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Boucle : répète un bloc
        for (int x = -3; x <= 3; x++) {
            // Boucle : répète un bloc
            for (int z = -3; z <= 3; z++) {
                // Appelle une méthode
                instance.loadChunk(x, z).join();
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        instance.setBlock(0, 40, 0, Block.STONE);
        // Appelle une méthode
        instance.setBlock(1, 40, -1, Block.STONE);
        // Appelle une méthode
        instance.setBlock(0, 40, -2, Block.STONE);
        // Appelle une méthode
        instance.setBlock(-1, 40, -1, Block.STONE);
        // Appelle une méthode
        instance.setBlock(0, 41, -1, Block.STONE);
        // Appelle une méthode
        instance.setBlock(0, 40, -1, Block.GLOWSTONE);

        // Affecte une valeur
        var expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(-2, 40, -1), 0)
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Appelle une méthode
        assertLightInstance(instance, expectedLights);

        // Appelle une méthode
        instance.setBlock(-1, 40, -1, Block.AIR);

        // Affecte une valeur
        expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(-2, 40, -1), 13)
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        LightingChunk.relightSection(instance, 0, 2, 0);

        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static byte lightVal(Instance instance, Vec pos) {
        // Appelle une méthode
        final Vec modPos = new Vec(((pos.blockX() % 16) + 16) % 16, ((pos.blockY() % 16) + 16) % 16, ((pos.blockZ() % 16) + 16) % 16);
        // Appelle une méthode
        Chunk chunk = instance.getChunkAt(pos.blockX(), pos.blockZ());
        // Renvoie une valeur à l'appelant
        return (byte) chunk.getSectionAt(pos.blockY()).blockLight().getLevel(modPos.blockX(), modPos.blockY(), modPos.blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static byte lightValSky(Instance instance, Vec pos) {
        // Appelle une méthode
        final Vec modPos = new Vec(((pos.blockX() % 16) + 16) % 16, ((pos.blockY() % 16) + 16) % 16, ((pos.blockZ() % 16) + 16) % 16);
        // Appelle une méthode
        Chunk chunk = instance.getChunkAt(pos.blockX(), pos.blockZ());
        // Renvoie une valeur à l'appelant
        return (byte) chunk.getSectionAt(pos.blockY()).skyLight().getLevel(modPos.blockX(), modPos.blockY(), modPos.blockZ());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static void assertLightInstance(Instance instance, Map<Vec, Integer> expectedLights) {
        // Affecte une valeur
        List<String> errors = new ArrayList<>();
        // Boucle : répète un bloc
        for (var entry : expectedLights.entrySet()) {
            // Appelle une méthode
            final Integer expected = entry.getValue();
            // Appelle une méthode
            final Vec pos = entry.getKey();

            // Appelle une méthode
            final byte light = lightVal(instance, pos);

            // Embranchement : vérifie une condition
            if (light != expected) {
                // Appelle une méthode
                String errorLine = String.format("Expected %d at [%d,%d,%d] but got %d", expected, pos.blockX(), pos.blockY(), pos.blockZ(), light);
                // Appelle une méthode
                System.err.println();
                // Appelle une méthode
                errors.add(errorLine);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (!errors.isEmpty()) {
            // Appelle une méthode
            StringBuilder sb = new StringBuilder();
            // Boucle : répète un bloc
            for (String s : errors) {
                // Appelle une méthode
                sb.append(s).append("\n");
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            System.err.println(sb);
            // Appelle une méthode
            fail();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}