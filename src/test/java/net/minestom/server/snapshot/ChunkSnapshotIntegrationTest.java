// Déclaration du paquet de ce fichier
package net.minestom.server.snapshot;

// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class ChunkSnapshotIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void blocks(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        instance.setBlock(0, 0, 0, Block.STONE);
        // Appelle une méthode
        var snapshot = ServerSnapshot.update();

        // Appelle une méthode
        var inst = snapshot.instances().iterator().next();
        // Appelle une méthode
        assertEquals(Block.STONE, inst.getBlock(0, 0, 0));

        // Appelle une méthode
        assertEquals(1, inst.chunks().size());
        // Appelle une méthode
        var chunk = inst.chunks().iterator().next();
        // Appelle une méthode
        assertEquals(Block.STONE, chunk.getBlock(0, 0, 0));
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
