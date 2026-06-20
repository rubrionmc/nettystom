// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.instance.generator.GenerationUnit;
// Import d'une classe nécessaire
import net.minestom.server.world.biome.Biome;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class GeneratorForkIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void local(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Affecte une valeur
        var block = Block.STONE;
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> {
            // Appelle une méthode
            var u = unit.fork(unit.absoluteStart(), unit.absoluteEnd());
            // Appelle une méthode
            assertEquals(unit.absoluteStart(), u.absoluteStart());
            // Appelle une méthode
            assertEquals(unit.absoluteEnd(), u.absoluteEnd());
            // Appelle une méthode
            u.modifier().setRelative(0, 0, 0, Block.STONE);
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        assertEquals(block, instance.getBlock(0, -64, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void size(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Set the Generator
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> {
            // Appelle une méthode
            Point start = unit.absoluteStart();
            // Appelle une méthode
            GenerationUnit fork = unit.fork(start, start.add(18, 18, 18));
            // Appelle une méthode
            assertDoesNotThrow(() -> fork.modifier().setBlock(start.add(17, 17, 17), Block.STONE));
        // Fin d'un bloc/d'une expression
        });
        // Load the chunks
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        instance.setGenerator(null);
        // Appelle une méthode
        instance.loadChunk(1, 1).join();
        // Appelle une méthode
        assertEquals(Block.STONE, instance.getBlock(17, -64 + 17, 17));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void signal(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Affecte une valeur
        var block = Block.STONE;
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> {
            // Appelle une méthode
            var u = unit.fork(unit.absoluteStart(), unit.absoluteEnd().add(16, 0, 16));
            // Appelle une méthode
            assertEquals(unit.absoluteStart(), u.absoluteStart());
            // Appelle une méthode
            assertEquals(unit.absoluteEnd().add(16, 0, 16), u.absoluteEnd());
            // Appelle une méthode
            u.modifier().setRelative(16, 0, 0, Block.STONE);
            // Appelle une méthode
            u.modifier().setRelative(16, 33, 0, Block.STONE);
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        instance.setGenerator(null);
        // Appelle une méthode
        instance.loadChunk(1, 0).join();
        // Appelle une méthode
        assertEquals(block, instance.getBlock(16, -64, 0));
        // Appelle une méthode
        assertEquals(block, instance.getBlock(16, -31, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void air(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> {
            // Appelle une méthode
            var u = unit.fork(unit.absoluteStart(), unit.absoluteEnd().add(16, 0, 16));
            // Appelle une méthode
            u.modifier().setRelative(16, 39 + 64, 0, Block.AIR);
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        instance.setGenerator(unit -> unit.modifier().fillHeight(0, 40, Block.STONE));
        // Appelle une méthode
        instance.loadChunk(1, 0).join();
        // Appelle une méthode
        assertEquals(Block.AIR, instance.getBlock(16, 39, 0));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void fillHeight(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();
        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> {
            // Appelle une méthode
            var u = unit.fork(unit.absoluteStart(), unit.absoluteEnd().add(16, 0, 16));
            // Appelle une méthode
            u.modifier().fillHeight(0, 40, Block.STONE);
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
        // Appelle une méthode
        instance.setGenerator(null);
        // Appelle une méthode
        instance.loadChunk(1, 0).join();
        // Boucle : répète un bloc
        for (int y = 0; y < 40; y++) {
            // Appelle une méthode
            assertEquals(Block.STONE, instance.getBlock(16, y, 0), "y=" + y);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void biome(Env env) {
        // Appelle une méthode
        var manager = env.process().instance();

        // Appelle une méthode
        var instance = manager.createInstanceContainer();
        // Début d'une méthode/d'un bloc
        instance.setGenerator(unit -> {
            // Appelle une méthode
            var u = unit.fork(unit.absoluteStart(), unit.absoluteEnd().add(16, 0, 16));
            // Appelle une méthode
            assertThrows(IllegalStateException.class, () -> u.modifier().setBiome(16, 0, 0, Biome.PLAINS));
            // Appelle une méthode
            assertThrows(IllegalStateException.class, () -> u.modifier().fillBiome(Biome.PLAINS));
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        instance.loadChunk(0, 0).join();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
