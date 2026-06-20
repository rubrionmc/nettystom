// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class ChunkHeightmapIntegrationTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testChunkHeightmap(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        var chunk = instance.getChunk(0, 0);

        // Appelle une méthode
        var heightmap = chunk.motionBlockingHeightmap().getHeight(0, 0);
        // Appelle une méthode
        assertEquals(heightmap, 39);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void heightMapPlaceTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        var chunk = instance.getChunk(0, 0);

        // Début d'un bloc
        {
            // Appelle une méthode
            instance.setBlock(0, 40, 0, Block.STONE);
            // Appelle une méthode
            var heightmap = chunk.motionBlockingHeightmap().getHeight(0, 0);
            // Appelle une méthode
            assertEquals(heightmap, 40);
        // Fin d'un bloc/d'une expression
        }

        // Début d'un bloc
        {
            // Appelle une méthode
            instance.setBlock(0, 45, 0, Block.STONE);
            // Appelle une méthode
            var heightmap = chunk.motionBlockingHeightmap().getHeight(0, 0);
            // Appelle une méthode
            assertEquals(heightmap, 45);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void heightMapRemoveTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        var chunk = instance.getChunk(0, 0);

        // Début d'un bloc
        {
            // Appelle une méthode
            instance.setBlock(0, 45, 0, Block.STONE);
            // Appelle une méthode
            var heightmap = chunk.motionBlockingHeightmap().getHeight(0, 0);
            // Appelle une méthode
            assertEquals(heightmap, 45);
        // Fin d'un bloc/d'une expression
        }

        // Début d'un bloc
        {
            // Appelle une méthode
            instance.setBlock(0, 45, 0, Block.AIR);
            // Appelle une méthode
            var heightmap = chunk.motionBlockingHeightmap().getHeight(0, 0);
            // Appelle une méthode
            assertEquals(heightmap, 39);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
