// Déclaration du paquet de ce fichier
package net.minestom.server.instance.light;

// Import d'une classe nécessaire
import net.minestom.server.ServerProcess;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.instance.LightingChunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static java.util.Map.entry;
// Import statique d'un membre
import static net.minestom.server.instance.BlockLightMergeIntegrationTest.assertLightInstance;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class WorldRelightIntegrationTest {
    // Début d'une méthode/d'un bloc
    private Instance createLightingInstance(ServerProcess process) {
        // Appelle une méthode
        var instance = process.instance().createInstanceContainer();
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> {
            // Appelle une méthode
            unit.modifier().fillHeight(39, 40, Block.STONE);
            // Appelle une méthode
            unit.subdivide().forEach(u -> u.modifier().setBlock(0, 10, 0, Block.GLOWSTONE));
            // Appelle une méthode
            unit.modifier().fillHeight(50, 51, Block.STONE);
        // Fin d'un bloc/d'une expression
        });
        // Renvoie une valeur à l'appelant
        return instance;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testBorderLava(Env env) {
        // Appelle une méthode
        Instance instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setChunkSupplier(LightingChunk::new);
        // Appelle une méthode
        instance.loadChunk(6, 16).join();
        // Appelle une méthode
        instance.loadChunk(6, 15).join();

        // Appelle une méthode
        instance.setBlock(106, 70, 248, Block.LAVA);
        // Appelle une méthode
        instance.setBlock(106, 71, 249, Block.LAVA);

        // Affecte une valeur
        Map<Vec, Integer> expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(105, 72, 256), 6)
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        LightingChunk.relight(instance, instance.getChunks());
        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testBlockRemoval(Env env) {
        // Appelle une méthode
        Instance instance = createLightingInstance(env.process());
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
        LightingChunk.relight(instance, instance.getChunks());

        // Affecte une valeur
        var expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(-1, 40, 0), 12),
                // Instruction de code
                entry(new Vec(-9, 40, 8), 0),
                // Instruction de code
                entry(new Vec(-1, 40, -16), 12),
                // Instruction de code
                entry(new Vec(-1, 37, 0), 3),
                // Instruction de code
                entry(new Vec(-8, 37, -8), 0)
        // Fin d'un bloc/d'une expression
        );
        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testJackOLantern(Env env) {
        // Appelle une méthode
        Instance instance = createLightingInstance(env.process());
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
        instance.setBlock(10, 60, 10, Block.JACK_O_LANTERN);
        // Appelle une méthode
        LightingChunk.relight(instance, instance.getChunks());

        // Affecte une valeur
        var expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(11, 60, 10), 14),
                // Instruction de code
                entry(new Vec(10, 61, 10), 14),
                // Instruction de code
                entry(new Vec(15, 60, 10), 10)
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testRedstoneLamp(Env env) {
        // Appelle une méthode
        Instance instance = createLightingInstance(env.process());
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
        instance.setBlock(10, 60, 10, Block.REDSTONE_LAMP.withProperty("lit", "true"));
        // Appelle une méthode
        LightingChunk.relight(instance, instance.getChunks());

        // Affecte une valeur
        var expectedLights = Map.ofEntries(
                // Instruction de code
                entry(new Vec(11, 60, 10), 14),
                // Instruction de code
                entry(new Vec(10, 61, 10), 14),
                // Instruction de code
                entry(new Vec(15, 60, 10), 10)
        // Fin d'un bloc/d'une expression
        );

        // Appelle une méthode
        assertLightInstance(instance, expectedLights);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
